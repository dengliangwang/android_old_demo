package com.jwk.kdjlgp;

import android.app.Application;

import com.haiwan.lantian.vhaiw.HaiWan;


/**
 * Created by dengliang.wang on 17/11/7.
 */

public class MOApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        HaiWan.shared().ApplicationInit(this);

    }

    @Override
    public void onTerminate() {

        HaiWan.shared().applicationDestroy();
        super.onTerminate();
    }
}
