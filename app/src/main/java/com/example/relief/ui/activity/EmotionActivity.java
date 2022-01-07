package com.example.relief.ui.activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.hardware.camera2.CameraDevice;
import android.os.Bundle;
import android.os.SystemClock;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.renderscript.Type;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.example.relief.BR;
import com.example.relief.R;
import com.example.relief.api.model.emotion.EmotionEnum;
import com.example.relief.base.BaseActivity;
import com.example.relief.camera2.Camera2Helper;
import com.example.relief.camera2.Camera2Listener;
import com.example.relief.databinding.ActivityEmotionBinding;
import com.example.relief.listener.OnMultiClickListener;
import com.example.relief.thread.CustomThreadPool;
import com.example.relief.ui.viewmodel.EmotionViewModel;
import com.example.relief.utils.BitmapUtils;
import com.example.relief.utils.ContextUtils;
import com.example.relief.utils.ImageUtils;
import com.example.relief.utils.ListenerUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EmotionActivity extends BaseActivity<ActivityEmotionBinding, EmotionViewModel>
        implements Camera2Listener {

    private static final String TAG = EmotionActivity.class.getSimpleName();

    private static final CustomThreadPool THREAD_POOL_TRACK = new CustomThreadPool(Thread.NORM_PRIORITY);
    private static final CustomThreadPool THREAD_POOL_DETECT = new CustomThreadPool(Thread.MAX_PRIORITY);

    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();

    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 0);
        ORIENTATIONS.append(Surface.ROTATION_90, 90);
        ORIENTATIONS.append(Surface.ROTATION_180, 180);
        ORIENTATIONS.append(Surface.ROTATION_270, 270);
    }

    private static class CompareSizeByArea implements Comparator<Size> {
        @Override
        public int compare(Size lhs, Size rhs) {
            return Long.signum(((long) lhs.getWidth() * lhs.getHeight())
                    - ((long) rhs.getWidth() * rhs.getHeight()));
        }
    }

    private Camera2Helper camera2Helper;
    // 显示的旋转角度
    private int displayOrientation;
    // 是否手动镜像预览
    private boolean isMirrorPreview;
    // 实际打开的cameraId
    private String openedCameraId;
    // 图像帧数据，全局变量避免反复创建，降低gc频率
    private byte[] mCameraTrackNv21;
    private byte[] mCameraDetectNv21;

    private volatile boolean mIsCameraTrackReady;
    private volatile boolean mIsCameraDetectReady;
    private volatile boolean mIsAnalyticsReady;

    private int mPreviewW = -1;
    private int mPreviewH = -1;

    private final List<Face> mFaces = new ArrayList<>();
    private Face mFace;
    private FaceDetector faceDetector;
    private Paint mPaint;

    private ScriptIntrinsicYuvToRGB mScriptIntrinsicYuvToRGB;
    private Allocation mInAllocation, mOutAllocation;
    private Bitmap mSourceBitmap;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_emotion;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public Class<EmotionViewModel> getViewModelClazz() {
        return EmotionViewModel.class;
    }

    @Override
    public void initData() {
        super.initData();
        initCamera();
        initOverlap();
        initFaceDetection();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        setObserveListener();
        setOnClickListener();
        doIsShowLoading();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIsCameraTrackReady = false;
        mIsCameraDetectReady = false;
        startCamera();
    }

    @Override
    protected void onStop() {
        stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        releaseFaceDetection();
        releaseCamera();
        super.onDestroy();
    }

    @Override
    public void onCameraOpened(CameraDevice cameraDevice, String cameraId, Size previewSize, int displayOrientation, boolean isMirror) {
        Log.i(TAG, "onCameraOpened: previewSize = " + previewSize.getWidth() + " x " + previewSize.getHeight()
                + ", displayOrientation = " + displayOrientation);
        this.displayOrientation = displayOrientation;
        this.isMirrorPreview = isMirror;
        this.openedCameraId = cameraId;
    }

    @Override
    public void onPreview(byte[] y, byte[] u, byte[] v, Size previewSize, int stride) {
        if (mCameraTrackNv21 == null) {
            mCameraTrackNv21 = new byte[stride * previewSize.getHeight() * 3 / 2];
            mCameraDetectNv21 = new byte[stride * previewSize.getHeight() * 3 / 2];
            mPreviewW = previewSize.getWidth();
            mPreviewH = previewSize.getHeight();
        }
        byte[] data = new byte[stride * previewSize.getHeight() * 3 / 2];
        if (y.length / u.length == 2) { // 回传数据是YUV422
            ImageUtils.yuv422ToYuv420sp(y, u, v, data, stride, previewSize.getHeight());
        } else if (y.length / u.length == 4) { // 回传数据是YUV420
            ImageUtils.yuv420ToYuv420sp(y, u, v, data, stride, previewSize.getHeight());
        }
        if (!mIsCameraTrackReady) {
            System.arraycopy(data, 0, mCameraTrackNv21, 0, data.length);
            mIsCameraTrackReady = true;
            startTrack();
        }
    }

    @Override
    public void onCameraClosed() {
        Log.i(TAG, "onCameraClosed");
    }

    @Override
    public void onCameraError(Exception e) {
        Log.i(TAG, "onCameraError: " + e.fillInStackTrace());
    }

    private void setObserveListener() {
        getViewModel().getFaceBitmap().observe(this, bitmap -> {
            // getViewModel().uploadFace();
            getViewModel().analysisByStream();
        });
        getViewModel().isAnalysing().observe(this, isAnalysing -> {
            mIsAnalyticsReady = false;
        });
        getViewModel().getSmile().observe(this, smile -> {
            getBinding().tvSmile.setText("" + smile);
        });
        getViewModel().getEmotion().observe(this, emotion -> {
            if (EmotionEnum.ANGER.getValue().equals(emotion)) {
                getBinding().tvEmotion.setTextColor(getResources().getColor(R.color.red, this.getTheme()));
            } else if (EmotionEnum.CONTEMPT.getValue().equals(emotion)) {
                getBinding().tvEmotion.setTextColor(getResources().getColor(R.color.light_red, this.getTheme()));
            } else if (EmotionEnum.DISGUST.getValue().equals(emotion)) {
                getBinding().tvEmotion.setTextColor(getResources().getColor(R.color.light_green, this.getTheme()));
            } else if (EmotionEnum.FEAR.getValue().equals(emotion)) {
                getBinding().tvEmotion.setTextColor(getResources().getColor(R.color.purple, this.getTheme()));
            } else if (EmotionEnum.HAPPINESS.getValue().equals(emotion)) {
                getBinding().tvEmotion.setTextColor(getResources().getColor(R.color.golden_yellow, this.getTheme()));
            } else if (EmotionEnum.NEUTRAL.getValue().equals(emotion)) {
                getBinding().tvEmotion.setTextColor(getResources().getColor(R.color.light_yellow, this.getTheme()));
            } else if (EmotionEnum.SADNESS.getValue().equals(emotion)) {
                getBinding().tvEmotion.setTextColor(getResources().getColor(R.color.gray, this.getTheme()));
            } else if (EmotionEnum.SURPRISE.getValue().equals(emotion)) {
                getBinding().tvEmotion.setTextColor(getResources().getColor(R.color.light_blue, this.getTheme()));
            }
            getBinding().tvEmotion.setText(emotion);
        });
        getViewModel().getSuggestion().observe(this, suggestion -> {
            getBinding().tvSuggestion.setText(suggestion);
        });
    }

    private void setOnClickListener() {
        ListenerUtils.setOnClickListener(getBinding().ivBack, new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                finish();
            }
        });
    }

    private void initCamera() {
        if (camera2Helper != null) {
            return;
        }
        Camera2Helper.Builder cameraBuilder = new Camera2Helper.Builder()
                .context(ContextUtils.getContext())
                .specificCameraId(Camera2Helper.getCameraIdFront())
                .previewOn(getBinding().tvFrontCamera)
                .cameraListener(this);
        camera2Helper = cameraBuilder.build();
    }

    private void startCamera() {
        if (camera2Helper == null) {
            return;
        }
        camera2Helper.start();
    }

    private void stopCamera() {
        if (camera2Helper == null) {
            return;
        }
        camera2Helper.stop();
    }

    private void releaseCamera() {
        if (camera2Helper == null) {
            return;
        }
        camera2Helper.release();
        camera2Helper = null;
    }

    private void initOverlap() {
        ViewGroup.LayoutParams layoutParams = getBinding().tvFrontCamera.getLayoutParams();
        getBinding().sfvOverlap.setLayoutParams(layoutParams);
        getBinding().sfvOverlap.setZOrderOnTop(true);
        getBinding().sfvOverlap.getHolder().setFormat(PixelFormat.TRANSPARENT);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(10);
        mPaint.setTextSize(90);
    }

    private void initFaceDetection() {
        if (faceDetector != null) {
            return;
        }
        FaceDetectorOptions realTimeOpts =
                new FaceDetectorOptions.Builder()
                        .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
                        .build();
        faceDetector = FaceDetection.getClient(realTimeOpts);
    }

    private void releaseFaceDetection() {
        if (faceDetector == null) {
            return;
        }
        faceDetector.close();
        faceDetector = null;
    }

    private void startTrack() {
        THREAD_POOL_TRACK.execute(() -> {
            if (mIsCameraTrackReady) {
                InputImage image = InputImage.fromByteArray(mCameraTrackNv21, mPreviewW, mPreviewH,
                        // 对于前置数据，镜像处理；若手动设置镜像预览，则镜像处理；若都有，则不需要镜像处理
                        Camera2Helper.getCameraIdFront().equals(openedCameraId) ^ isMirrorPreview
                                ? 360 - displayOrientation : displayOrientation,
                        InputImage.IMAGE_FORMAT_NV21
                );
                faceDetector.process(image).addOnSuccessListener(new OnSuccessListener<List<Face>>() {
                    @Override
                    public void onSuccess(@NonNull List<Face> faces) {
                        if (faces.size() == 0) {
                            clearCanvas();
                            mIsCameraTrackReady = false;
                            return;
                        }
                        mFaces.addAll(faces);
                        drawFaces(mFaces);
                        if (mFaces.size() > 0 && !mIsCameraDetectReady) {
                            mFace = mFaces.get(0);
                            System.arraycopy(mCameraTrackNv21, 0, mCameraDetectNv21, 0, mCameraTrackNv21.length);
                            mIsCameraDetectReady = true;
                            startDetect();
                        }
                        mFaces.clear();
                        mIsCameraTrackReady = false;
                    }
                })
                .addOnFailureListener(
                    new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mIsCameraTrackReady = false;
                        }
                    });
            }
        });
    }

    private void startDetect() {
        THREAD_POOL_DETECT.execute(() -> {
            if (mIsCameraDetectReady) {
                try {
                    Bitmap sceneBtm = getSceneBtm(mCameraDetectNv21, mPreviewW, mPreviewH);
                    Bitmap rotatedBtm = BitmapUtils.rotateBitmap(
                            sceneBtm,
                            // 对于前置数据，镜像处理；若手动设置镜像预览，则镜像处理；若都有，则不需要镜像处理
                            Camera2Helper.getCameraIdFront().equals(openedCameraId) ^ isMirrorPreview
                                    ? 360 - displayOrientation : displayOrientation,
                            true,
                            false);
                    Bitmap faceBtm = BitmapUtils.getCropBitmap(
                            rotatedBtm, flipFace(rotatedBtm.getWidth(), mFace), 1, 1);
                    if (!mIsAnalyticsReady) {
                        mIsAnalyticsReady = true;
                        getViewModel().getFaceBitmap().postValue(faceBtm);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mIsCameraDetectReady = false;
            }
        });
    }

    private Rect flipFace(int width, Face face) {
        return new Rect(width - face.getBoundingBox().right,
                face.getBoundingBox().top,
                width - face.getBoundingBox().left,
                face.getBoundingBox().bottom);
    }

    private void drawFaces(List<Face> faces) {
        if (getBinding() == null) {
            return;
        }
        if (faces != null && faces.size() > 0) {
            Canvas canvas = getBinding().sfvOverlap.getHolder().lockCanvas();
            if (canvas == null) {
                return;
            }
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
            // Only take face 0
            Face face = faces.get(0);
            canvas.save();
            canvas.setMatrix(getBinding().tvFrontCamera.getMatrix());

            int centerX = face.getBoundingBox().centerX();
            int centerY = face.getBoundingBox().centerY();
            int width = face.getBoundingBox().width();
            int height = face.getBoundingBox().height();

            // scale
            float scaledCenterX = centerX * canvas.getHeight() * 1.0f / mPreviewW;
            float scaledCenterY = centerY * canvas.getWidth() * 1.0f / mPreviewH;
            float scaledWidth = width * canvas.getHeight() * 1.0f / mPreviewW;
            float scaledHeight = height * canvas.getWidth() * 1.0f / mPreviewH;

            Log.i(TAG, "scaledX: " + (canvas.getHeight() * 1.0f / mPreviewW));
            Log.i(TAG, "scaledY: " + (canvas.getWidth() * 1.0f / mPreviewH));

            int extraWidth = 20;
            int extraBottom = 50;
            canvas.drawRect(
                    canvas.getWidth() - scaledCenterX - scaledWidth / 2 + extraWidth,
                    scaledCenterY  - scaledHeight / 2,
                    canvas.getWidth() - scaledCenterX + scaledWidth / 2 - extraWidth,
                    scaledCenterY  + scaledHeight / 2 - extraBottom,
                    mPaint);
            canvas.drawText("Detecting...",
                    canvas.getWidth() - scaledCenterX - scaledWidth / 2 + extraWidth,
                    scaledCenterY  - scaledHeight / 2 - 30, mPaint);
            canvas.restore();
            getBinding().sfvOverlap.getHolder().unlockCanvasAndPost(canvas);
        } else {
            clearCanvas();
        }
    }

    private void clearCanvas() {
        if (getBinding() == null) {
            return;
        }
        Canvas canvas = getBinding().sfvOverlap.getHolder().lockCanvas();
        if (canvas != null) {
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
            getBinding().sfvOverlap.getHolder().unlockCanvasAndPost(canvas);
        }
    }

    /**
     * 根据nv21数据生成bitmap
     * 8ms左右
     */
    private Bitmap getSceneBtm(byte[] nv21Bytes, int width, int height) {

        if (nv21Bytes == null) {
            return null;
        }

        if (mInAllocation == null) {
            initRenderScript(width, height);
        }
        long s = SystemClock.uptimeMillis();
        mInAllocation.copyFrom(nv21Bytes);
        mScriptIntrinsicYuvToRGB.setInput(mInAllocation);
        mScriptIntrinsicYuvToRGB.forEach(mOutAllocation);
        if (mSourceBitmap == null || mSourceBitmap.getWidth() * mSourceBitmap.getHeight() != width * height) {
            mSourceBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }
        mOutAllocation.copyTo(mSourceBitmap);
        Log.i(TAG, "getSceneBtm = " + Math.abs(SystemClock.uptimeMillis() - s));
        return mSourceBitmap;
    }

    private void initRenderScript(int width, int height) {

        RenderScript mRenderScript = RenderScript.create(ContextUtils.getContext());
        mScriptIntrinsicYuvToRGB = ScriptIntrinsicYuvToRGB.create(mRenderScript,
                Element.U8_4(mRenderScript));

        Type.Builder yuvType = new Type.Builder(mRenderScript, Element.U8(mRenderScript))
                .setX(width * height * 3 / 2);
        mInAllocation = Allocation.createTyped(mRenderScript,
                yuvType.create(),
                Allocation.USAGE_SCRIPT);

        Type.Builder rgbaType = new Type.Builder(mRenderScript, Element.RGBA_8888(mRenderScript))
                .setX(width).setY(height);
        mOutAllocation = Allocation.createTyped(mRenderScript,
                rgbaType.create(),
                Allocation.USAGE_SCRIPT);
    }

    /**
     * 控制进度圈显示
     */
    public void doIsShowLoading() {
        getViewModel().isShowLoading().observe(this, isShowing -> {
            if (isShowing) {
                showLoading(false);
            } else {
                stopLoading();
            }
        });
    }
}
