package com.wzx.tipcontent.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.loopj.android.http.RequestParams;
import com.wzx.tipcontent.R;
import com.wzx.tipcontent.base.BaseActivity;
import com.wzx.tipcontent.custom.ServerRequest;
import com.wzx.tipcontent.fragment.MainActivity;
import com.wzx.tipcontent.kit.AppConstants;
import com.wzx.tipcontent.utils.AppSP;
import com.wzx.tipcontent.utils.Code;
import com.wzx.tipcontent.utils.KeyboardUtils;
import com.wzx.tipcontent.utils.Tool;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * on 2021/3/8
 *
 * @Author zhanghui
 * @Description
 */
public class LoginAcitivty extends BaseActivity implements ServerRequest.InterfaceCode{

    @InjectView(R.id.et_username)
    EditText etName;
    @InjectView(R.id.et_pwd)
    EditText etPwd;
    @InjectView(R.id.btn_login)
    Button mLogin;
    @InjectView(R.id.et_code)
    EditText mEtCode;

    @InjectView(R.id.VerificationCode)
    ImageView mCode;
    private String getCode = null;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mCode.setImageBitmap(Code.getInstance().getBitmap());
        getCode = Code.getInstance().getCode();
        Timers();
    }



    @OnClick({R.id.btn_login,R.id.VerificationCode,R.id.tv_register})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_login:

                if(TextUtils.isEmpty(etName.getText().toString().trim())){
                    Tool.showToast(this,"用户名不能为空",3000);
                    return;
                }
                if(TextUtils.isEmpty(etPwd.getText().toString().trim())){
                    Tool.showToast(this,"密码不能为空",3000);
                    return;
                }
                if(TextUtils.isEmpty(mEtCode.getText().toString().trim())){
                    Tool.showToast(this,"验证码不能为空",3000);
                    return;
                }
                if(!mEtCode.getText().toString().trim().equals(getCode)){
                        Tool.showToast(this,"验证码输入错误",3000);
                        return;
                }
                login(etName.getText().toString().trim(),etPwd.getText().toString().trim());

                break;
            case R.id.VerificationCode:
                mCode.setImageBitmap(Code.getInstance().getBitmap());
                getCode = Code.getInstance().getCode();
                break;
            case R.id.tv_register:
                intent=new Intent(this, RegisterActivity.class);
                startActivity(intent);
                finish();
                break;
        }

   }

    private void login(String name,String pwd) {
        String url = "http://139.224.110.190:91/Account/Login";
        Map<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put("username", name);
        hashMap.put("password", pwd);
        ServerRequest request = new ServerRequest(url, Tool.jsonUrl(hashMap));
        request.requestPostReturn(this);
        request.setInterfaceCode(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!Code.getInstance().getBitmap().isRecycled()) {
            Code.getInstance().getBitmap().recycle();
        }
    }

    private void Timers() {
        final Timer timer = new Timer();
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                timer.cancel();
                handler.sendEmptyMessage(1);
            }
        };
        timer.schedule(task, 600000);
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            mCode.setImageBitmap(Code.getInstance().getBitmap());
            getCode = Code.getInstance().getCode();
            Timers();
        };
    };

    @Override
    public void Success(String jsonData) {
        AppSP.putString("isLogin","1");
        KeyboardUtils.hideSoftkeyboard(this);
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void Fail(String failContent) {
        if(failContent.contains(AppConstants.requestStr)){
            Tool.showToast(this,"网络请求失败请重试",3000);
        }else {
            Tool.showToast(this,"登录失败请重试",3000);

        }

    }
}
