package com.fosu.jobapp;

import android.app.Application;

import com.blankj.utilcode.utils.CrashUtils;
import com.blankj.utilcode.utils.LogUtils;
import com.blankj.utilcode.utils.Utils;
import com.fosu.jobapp.controller.ChatMessageHandler;
import com.orhanobut.logger.Logger;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import cn.bmob.newim.BmobIM;

/**
 * Created by Administrator on 2017/2/18.
 */

public class MainApplication extends Application {
    private static MainApplication appContext;

    public static MainApplication getInstance() {
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化二维码扫描
        ZXingLibrary.initDisplayOpinion(this);
        Logger.init("JobApp");
        //只有主进程运行的时候才需要初始化
        if (getApplicationInfo().packageName.equals(getMyProcessName())){
            //im初始化
            BmobIM.init(this);
            //注册消息接收器
            BmobIM.registerDefaultMessageHandler(new ChatMessageHandler(this));
        }
        // 内存泄露检查工具
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
        appContext = this;
        Utils.init(appContext);
        CrashUtils.getInstance().init();
        LogUtils.getBuilder().setTag("MyTag").setLog2FileSwitch(true).create();

        // 开启友盟调试
        Config.DEBUG = true;
        // 初始化友盟SDK
        UMShareAPI.get(this);
    }

    {
        // 配置QQ平台appkey
        PlatformConfig.setQQZone("1105983855","ZGO4WJqjue8r8lvb");
    }

    /**
     * 获取当前运行的进程名
     * @return
     */
    public static String getMyProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
