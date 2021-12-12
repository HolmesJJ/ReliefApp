package com.example.relief;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import com.example.relief.base.BaseViewModel;
import com.example.relief.network.http.ResponseCode;
import com.example.relief.network.http.Result;

public class MainViewModel extends BaseViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {

    }

    /**
     * 处理网络请求结果的错误信息
     *
     * @param result
     */
    private void doResultErrorMsg(Result result) {
        if (result.getCode() == ResponseCode.NETWORK_ERROR) {
            Log.i(TAG, "Network Error");
        } else if (result.getCode() == ResponseCode.NOT_FOUND) {
            Log.i(TAG, "Not Found");
        } else {
            Log.i(TAG, "Unknown Error");
        }
    }
}
