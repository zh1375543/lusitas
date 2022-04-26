package com.wzx.tipcontent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wzx.tipcontent.R;
import com.wzx.tipcontent.fragment.MainActivity;
import com.wzx.tipcontent.utils.AppSP;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 *  Android 功能: 项目启动的activity
 * 
 *
 *         Reserved
 */
public class WelcomeActivity extends AppCompatActivity {
	private ImageView l_welcome;// 动态获取图片
	private final int startImg = R.mipmap.ic_launcher; // 定义默认显示的启动页图片
	private final int TIMING = 2000; // 定时器时间 （2000毫秒）
	Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.appstart);
		l_welcome = (ImageView) findViewById(R.id.l_welcome);
		l_welcome.setImageResource(startImg);
		startTimer();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
	/**
	 * 启动计时器，两秒后跳转到主页（有网络和无网络情况下都跳转）
	 */
	private void startTimer() {
		final Timer timer = new Timer();
		TimerTask tt = new TimerTask() {
			@Override
			public void run() {
				timer.cancel();
				if(TextUtils.isEmpty(AppSP.getString("isLogin"))){
					intent=new Intent(WelcomeActivity.this,LoginAcitivty.class);
				}else{
					intent=new Intent(WelcomeActivity.this, MainActivity.class);
				}
				startActivity(intent);
				WelcomeActivity.this.finish();
			}
		};
		timer.schedule(tt, TIMING);
	}

}
