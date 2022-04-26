package com.wzx.tipcontent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.wzx.tipcontent.R;
import com.wzx.tipcontent.base.BaseActivity;
import com.wzx.tipcontent.custom.ServerRequest;
import com.wzx.tipcontent.fragment.MainActivity;
import com.wzx.tipcontent.kit.AppConstants;
import com.wzx.tipcontent.utils.Code;
import com.wzx.tipcontent.utils.Header;
import com.wzx.tipcontent.utils.Tool;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity  implements ServerRequest.InterfaceCode{
    @InjectView(R.id.et_usernickname_re)
    EditText etnickName;
    @InjectView(R.id.et_username_re)
    EditText etName;
    @InjectView(R.id.et_pwd_re)
    EditText etPwd;

    @InjectView(R.id.et_pwd_again_re)
    EditText etPwd_agein;

    @InjectView(R.id.et_Phone)
    EditText etPhone;

    @InjectView(R.id.btn_reigster)
    Button mRegister;

    @InjectView(R.id.et_code_re)
    EditText mEtCode;

    @InjectView(R.id.VerificationCode_re)
    ImageView mCode;
    @InjectView(R.id.tv_login)
    TextView mLogin;

    private String getCode = null;
    private  Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        mCode.setImageBitmap(Code.getInstance().getBitmap());
        getCode = Code.getInstance().getCode();
        header=new Header(this,"register");
        header.setTitle("注册");
        Timers();
    }


    @OnClick({R.id.btn_reigster,R.id.VerificationCode_re,R.id.tv_login})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_reigster:
                if(TextUtils.isEmpty(etnickName.getText().toString().trim())){
                    Tool.showToast(this,"昵称不能为空",3000);
                    return;
                }
                if(TextUtils.isEmpty(etName.getText().toString().trim())){
                    Tool.showToast(this,"用户名不能为空",3000);
                    return;
                }
                if(!Tool.match(AppConstants.REGNAME,etName.getText().toString().trim())){
                    Tool.showToast(this,"昵称为 5-8位的数字和字母的组合",3000);
                    return;
                }
                if(etnickName.getText().toString().trim().equals(etName.getText().toString().trim())){
                    Tool.showToast(this,"昵称和用户名不能相同",3000);
                    return;
                }
                if(TextUtils.isEmpty(etPwd.getText().toString().trim())){
                    Tool.showToast(this,"密码不能为空",3000);
                    return;
                }
                if(!Tool.match(AppConstants.REGPWD,etPwd.getText().toString().trim())){
                    Tool.showToast(this,"密码为6-18位,请重新输入",3000);
                    return;
                }
                if(TextUtils.isEmpty(etPwd_agein.getText().toString().trim())){
                    Tool.showToast(this,"密码不能为空",3000);
                    return;
                }
                if(!etPwd.getText().toString().trim().equals(etPwd_agein.getText().toString().trim())){
                    Tool.showToast(this,"二次输入的密码不一致",3000);
                    return;
                }
                if(TextUtils.isEmpty(etPwd_agein.getText().toString().trim())){
                    Tool.showToast(this,"密码不能为空",3000);
                    return;
                }
                if(TextUtils.isEmpty(etPhone.getText().toString().trim())){
                    Tool.showToast(this,"电话号码不能为空",3000);
                    return;
                }
                if(!Tool.match(AppConstants.REGPHONE,etPhone.getText().toString().trim())){
                    Tool.showToast(this,"手机格式输入错误,请重新输入",3000);
                    return;
                }
                if(!mEtCode.getText().toString().trim().equals(getCode)){
                    Tool.showToast(this,"验证码输入错误",3000);
                    return;
                }
                RegisterInit();
                break;
            case R.id.VerificationCode_re:
                mCode.setImageBitmap(Code.getInstance().getBitmap());
                getCode = Code.getInstance().getCode();
                break;
            case R.id.tv_login:
                intent=new Intent(this, LoginAcitivty.class);
                startActivity(intent);
                this.finish();
                break;
        }

    }
    private void RegisterInit() {
        String url = "http://139.224.110.190:91/Account/Signup";
        Map<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put("username", etName.getText().toString().trim());
        hashMap.put("password", etPwd.getText().toString().trim());
        hashMap.put("userNickName", etnickName.getText().toString().trim());
        hashMap.put("phoneNumber", etPhone.getText().toString().trim());
        ServerRequest request = new ServerRequest(url, Tool.jsonUrl(hashMap));
        request.requestPostReturn(this);
        request.setInterfaceCode(this);

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
        Tool.showToast(this,"注册成功",3000);
        intent=new Intent(this, LoginAcitivty.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void Fail(String failContent) {
        Tool.showToast(this,"注册失败请重试",3000);
    }
}
