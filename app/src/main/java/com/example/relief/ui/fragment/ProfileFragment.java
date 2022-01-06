package com.example.relief.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.example.relief.BR;
import com.example.relief.MainActivity;
import com.example.relief.R;
import com.example.relief.base.BaseFragment;
import com.example.relief.config.Config;
import com.example.relief.databinding.FragmentProfileBinding;
import com.example.relief.listener.OnMultiClickListener;
import com.example.relief.ui.viewmodel.ProfileViewModel;
import com.example.relief.ui.widget.ItemTextView;
import com.example.relief.ui.widget.dialog.TextDialog;
import com.example.relief.utils.ListenerUtils;
import com.example.relief.utils.ToastUtils;

public class ProfileFragment extends BaseFragment<FragmentProfileBinding, ProfileViewModel> {

    private static final String TAG = ProfileFragment.class.getSimpleName();

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_profile;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public Class<ProfileViewModel> getViewModelClazz() {
        return ProfileViewModel.class;
    }

    @Override
    public void initParam() {
        super.initParam();
    }

    @Override
    public void initData() {
        super.initData();

        String avatar = "https://cdn.discordapp.com/attachments/499918830999699470/928643427694940160/holmesjj.png";
        Glide.with(this).load(avatar).circleCrop().into(getBinding().ivAvatar);

        getBinding().itvStudentId.setLeftText(R.string.student_id).setRightText("A1234567B").setBottomLineVisible(false);
        getBinding().itvName.setLeftText(R.string.name).setRightText("benjamin").setBottomLineVisible(false);
        getBinding().itvGender.setLeftText(R.string.gender).setRightText("Male").setBottomLineVisible(false);
        getBinding().itvPhone.setLeftText(R.string.phone).setRightText("98765432").setBottomLineVisible(false);
        getBinding().itvEmail.setLeftText(R.string.email).setBottomLineVisible(false);
        getBinding().itvAcademicYear.setLeftText(R.string.academic_year).setRightText("2").setBottomLineVisible(false);
        getBinding().itvDepartment.setLeftText(R.string.department).setRightText("Computing").setBottomLineVisible(false);
        getBinding().itvCourse.setLeftText(R.string.course).setRightText("Computer Science");

        getBinding().itvEmergencyContact.setLeftText(R.string.emergency_contact).setRightText("65167777").setBottomLineVisible(false);
        getBinding().itvChatWithUs.setLeftText(R.string.chat_with_us);
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        setObserveListener();
        setOnClickListener();
    }

    private void setObserveListener() {
        getViewModel().getActivityAction().observe(this, activityAction -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).stopLoading();
            }
            if (activityAction != null) {
                try {
                    Intent intent = new Intent(getActivity(), activityAction);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } catch (Exception e) {
                    ToastUtils.showShortSafe(e.getMessage());
                }
            } else {
                Log.e(TAG, "activityAction is null");
            }
        });
    }

    private void setOnClickListener() {
        getBinding().itvEmail.setItemClickListener(new ItemTextView.ItemClickListener() {
            @Override
            public void click() {
                showEmailDialog();
            }
        });
        getBinding().itvChatWithUs.setItemClickListener(new ItemTextView.ItemClickListener() {
            @Override
            public void click() {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).showLoading(false);
                }
                getViewModel().toChatbot();
            }
        });
        ListenerUtils.setOnClickListener(getBinding().btnSignOut, new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                Config.setUserId(-1);
                Config.setPhqDone(false);
                Config.setEmotionDone(false);
                Config.setSentimentDone(false);
                Config.setMonitorDone(false);
                Config.setLogin(false);
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).exitApp();
                }
            }
        });
    }

    private void showEmailDialog() {
        if (getActivity() == null) {
            return;
        }
        TextDialog textDialog = new TextDialog(getActivity());
        textDialog.show();
        textDialog.setDialogTitle(R.string.email).setText("benjamin123@gmail.com");
    }
}
