package com.runto.cources;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;


/**
 * Created by Administrator on 2018/6/20 0020.
 */
@SuppressWarnings("all")
public class CourseApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        initARouter();
    }

    public void initARouter() {
        ARouter.openLog();     // 打印日志
        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }
}