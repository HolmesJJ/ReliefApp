package com.example.relief.init;

import android.content.Intent;
import android.os.Bundle;
import com.example.relief.BR;
import com.example.relief.R;
import com.example.relief.base.BaseActivity;
import com.example.relief.constants.Constants;
import com.example.relief.databinding.ActivityInitBinding;
import com.example.relief.utils.ContextUtils;
import com.example.relief.utils.PermissionsUtils;
import pub.devrel.easypermissions.EasyPermissions;

public class InitActivity extends BaseActivity<ActivityInitBinding, InitViewModel> {

    private static final String TAG = InitActivity.class.getSimpleName();

    private static final String[] PERMISSIONS = new String[]{};

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_init;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public Class<InitViewModel> getViewModelClazz() {
        return InitViewModel.class;
    }

    @Override
    public void initData() {
        super.initData();
        PermissionsUtils.doSomeThingWithPermission(this, () -> {
            if (getViewModel() != null) {
                getViewModel().initData();
            }
        }, PERMISSIONS, Constants.PERMISSION_REQUEST_CODE, R.string.rationale_init);
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        showLoading(false);
        getViewModel().getActivityAction().observe(this, activityAction -> {
            Intent intent = new Intent(ContextUtils.getContext(), activityAction);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLoading();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected boolean onHasPermissions() {
        return EasyPermissions.hasPermissions(ContextUtils.getContext(), PERMISSIONS);
    }

    @Override
    protected void onPermissionSuccessCallbackFromSetting() {
        super.onPermissionSuccessCallbackFromSetting();
        if (getViewModel() != null) {
            getViewModel().initData();
        }
    }
}
