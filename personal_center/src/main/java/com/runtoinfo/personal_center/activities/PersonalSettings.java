package com.runtoinfo.personal_center.activities;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.runtoinfo.personal_center.R;
import com.runtoinfo.personal_center.databinding.ActivityPersonalSettingsBinding;
import com.runtoinfo.youxiao.globalTools.utils.Entity;

@Route(path = "/personal/personalSettings")
public class PersonalSettings extends BaseActivity {

    public ActivityPersonalSettingsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    public void initView(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_personal_settings);
        binding.personalSettingPersonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/personal/personalMain").navigation();
            }
        });
        binding.personalSettingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.exitNowSystem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                spUtils.setString(Entity.TOKEN, "");
                ARouter.getInstance().build("/main/LoginActivity").navigation();
                PersonalSettings.this.finish();
            }
        });
    }
}
