package com.jwk.kdjlgp;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import androidx.annotation.NonNull;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.haiwan.lantian.vhaiw.HaiWan;
import com.haiwan.lantian.vhaiw.LTUser;
import com.haiwan.lantian.vhaiw.MKShare;
import com.haiwan.lantian.vhaiw.MKShareDelegate;
import com.haiwan.lantian.vhaiw.QDShareType;
import com.haiwan.lantian.vhaiw.QGLog;
import com.haiwan.lantian.vhaiw.QKInitCallBack;
import com.haiwan.lantian.vhaiw.QYLoginCallBack;
import com.haiwan.lantian.vhaiw.TBLogoutCallBack;
import com.haiwan.lantian.vhaiw.TLGameLevel;
import com.haiwan.lantian.vhaiw.TQGameUser;
import com.haiwan.lantian.vhaiw.ZRError;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;


public class MainActivity
        extends Activity
        implements QKInitCallBack,
        QYLoginCallBack,
        TBLogoutCallBack,
        MKShareDelegate,
        ActivityCompat.OnRequestPermissionsResultCallback{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        final HaiWan aPlatform = HaiWan.shared();
        aPlatform.setInitDelegate(this);


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        // listeners
        aPlatform.setLoginDelegate(this);
        aPlatform.setLogoutDelegate(this);


        // 显示包名，版本号 build号相关
        try {
            String pkName = this.getPackageName();
            String versionName = this.getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
            int versionCode = this.getPackageManager()
                    .getPackageInfo(pkName, 0).versionCode;
            //包名
            TextView pn = (TextView)findViewById(R.id.mosdk_demo_id_pn);
            pn.setText("包名 " + pkName);
            //版本号
            TextView vn = (TextView)findViewById(R.id.mosdk_demo_id_vn);
            vn.setText("version_name " + versionName);
            //编译号
            TextView vc = (TextView)findViewById(R.id.mosdk_demo_id_vc);
            vc.setText("version_code " + versionCode);

        } catch (Exception e) {

        }


        // request products
        Button aRP = (Button)findViewById(R.id.mosdk_demo_id_rp);
        aRP.setEnabled(aPlatform.userHasLogged());
        aRP.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, Product_List.class);

                startActivity(intent);

            }
        });
        mRP = aRP;



        // user center
        Button aUC = (Button)findViewById(R.id.mosdk_demo_id_uc);
        aUC.setEnabled(aPlatform.userHasLogged());
        aUC.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                if (aPlatform.userHasLogged())
                {
                    aPlatform.presentUserCenter(MainActivity.this);
                }
            }
        });
        mUC = aUC;

        // fb
        Button aFB = (Button)findViewById(R.id.mosdk_demo_id_fb);
        aFB.setEnabled(aPlatform.userHasLogged());
        aFB.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                if (aPlatform.userHasLogged())
                {
                    HaiWan aPlatform	= HaiWan.shared();
                    aPlatform.setShareDelegate(MainActivity.this);
                    LTUser aUser			= aPlatform.getUser();
                    MKShare aShare			= aPlatform.getShare(QDShareType.Facebook);
                    String aLocale			= aPlatform.getLocale();
                    String aExtra = "aExtra";//自定义字段，服务器发货会传给游戏服务器

                    String gameRole = "100";
                    String gameServer = "1";

                    //若传自定义字段请使用 MOGameUser aGameUser = new MOGameUser(gameRole, gameServer, aLocale,aExtra);
                    TQGameUser aGameUser = new TQGameUser(gameRole, gameServer, aLocale);

                    aShare.showFB(MainActivity.this,aUser,aGameUser, null);

                }
            }
        });
        mFB = aFB;

        //常见问题
        Button faqBtn = (Button)findViewById(R.id.mosdk_demo_id_faq);
        faqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HaiWan.shared().showAIHelpCenter(MainActivity.this);
            }
        });

        //发起会话
        Button cvtBtn = (Button)findViewById(R.id.mosdk_demo_id_conversation);
        cvtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HaiWan.shared().startAIHelpConversation(MainActivity.this);
            }
        });

        findViewById(R.id.mosdk_demo_id_open_cafe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HaiWan.shared().openCafeHome(MainActivity.this);
            }
        });



    }

    @Override
    public void onDestroy()
    {

        super.onDestroy();
    }

    @Override
    public void onActivityResult(int aRequestCode, int aResultCode, Intent aData)
    {

        final HaiWan aPlatform = HaiWan.shared();
        if (aPlatform.onActivityResult(this, aRequestCode, aResultCode, aData))
            return;

        super.onActivityResult(aRequestCode, aResultCode, aData);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        HaiWan.shared().onConfigurationChanged(newConfig,this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(HaiWan.shared().attachBaseContext(newBase));
    }

    @Override
    public void onStop()
    {
        super.onStop();

        HaiWan platform = HaiWan.shared();
        platform.dismisFloatWindow(this);
        platform.inactive(this);
    }

    @Override
    public void onResume()
    {
        super.onResume();


        HaiWan platform = HaiWan.shared();
        if (platform.userHasLogged())
        {
            String aLocale			= platform.getLocale();

            TQGameUser aGameUser = new TQGameUser("1000", "1", aLocale);
            platform.presentFloatWindow(this,aGameUser);
        }
        HaiWan.shared().active(this);
    }

    //请求权限
    public  void requestUerPermission()
    {
        String[] permisions = {};
        int callbackrequeatCode = 109;
//        PT manager = new PTPermissionManager();
//        Activity activity = this;
//
//        /**
//         * permisions 权限数组
//         * activity 回调Activity，需要实现ActivityCompat.OnRequestPermissionsResultCallback的方法
//         * callbackrequeatCode 在activity 的
//         *
//         * */
//        manager.requestMultiplyPermissions(permisions,activity,callbackrequeatCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                    @NonNull int[] grantResults)
    {
        // TODO: 授权回调，判断是否拿到用户授权 requestCode 发起授权请求传入的返回状态码，permissions 申请的权限， grantResults 用户授权结果
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }


    public void loginCancelled()
    {

        finish();
        System.exit(0);
    }

    public void loginSuccess(LTUser aUser)
    {

        mRP.setEnabled(true);
        mUC.setEnabled(true);
        mFB.setEnabled(true);

        //用户唯一标识 aUser.getID();

        HaiWan aPlatform	= HaiWan.shared();
        String aLocale			= aPlatform.getLocale();

        String gameRole = "1000";
        String gameServerId = "1";
        TQGameUser aGameUser = new TQGameUser(gameRole, gameServerId, aLocale);
        HaiWan.shared().presentFloatWindow(this,aGameUser);
    }

    public void loginFailure(ZRError aError)
    {

        QGLog.info("Demo loginFailure: %s", aError);
    }

    public void logoutSuccess(String aUser)
    {

        QGLog.info("Demo logoutSuccess: %s", aUser);
        //上传服务器标识和角色名
        String aServer = "10";//游戏服标识
        String aRole = "Vayne";//角色名
        HashMap<String,String> aPlayerInfor = new HashMap<String, String>();
        aPlayerInfor.put("server",aServer);
        aPlayerInfor.put("role",aRole);
        HaiWan.shared().submitPlayerInfo(aPlayerInfor);

        TLGameLevel aLevel = HaiWan.shared().getSDkGameLevel();
        if (aLevel == TLGameLevel.ZERO){
            Intent intent = new Intent(this,OfficialActivity.class);
            startActivity(intent);
        }
        else if (aLevel == TLGameLevel.ONE){

            //显示测试服
            Intent intent = new Intent(this,TestServerActivity.class);
            startActivity(intent);
        }

    }

    public void logoutFailure(ZRError aError)
    {

        QGLog.info("Demo logoutFailure: %s", aError);
    }


    @Override
    public void onShareSuccess()
    {

    }

    @Override
    public void onShareError()
    {

    }

    @Override
    public void onShareCancel()
    {

    }

    private boolean 	mInited;
    private Button		mRP;
    //private Button		mBuy;
    private Button		mUC;
    private Button		mFB;






    //初始化代理方法
    @Override
    public void initSuccess(@Nullable HaiWan haiWan)
    {

        mInited = true;

        QGLog.info("Demo initSuccess");

        //发起自动登录
        if (!haiWan.userHasLogged())
        {
            haiWan.automaticLogin(this);
        }

        //魔亚sdk 服务器 切换策略结果（需要在初始化成功访问结果）
        TLGameLevel aLevel = HaiWan.shared().getSDkGameLevel();
        if (aLevel == TLGameLevel.ZERO){
            Intent intent = new Intent(this,OfficialActivity.class);
            startActivity(intent);
        }
        else if (aLevel == TLGameLevel.ONE){

            //显示测试服
            Intent intent = new Intent(this,TestServerActivity.class);
            startActivity(intent);
        }


    }
    @Override
    public void initFailure(@Nullable ZRError zrError) {
        mInited = false;

        QGLog.info("Demo initFailure: %s", zrError);
    }

}
