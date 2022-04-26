package com.wzx.tipcontent.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.wzx.tipcontent.fragment.MainActivity;
import com.wzx.tipcontent.kit.AppConstants;
import com.wzx.tipcontent.utils.Header;
import com.wzx.tipcontent.weight.MyProgressDialog;

import java.util.HashMap;
import java.util.Iterator;

import butterknife.ButterKnife;

/**
 * 项目名: 所有的Activity基类
 * 
 * @author
 *
 */
public  abstract class BaseActivity extends AppCompatActivity {
	public static String CCB = "";// 与V赋值参数
	private boolean isExit = false;
	protected Header header;
	private MyProgressDialog progressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	};
	public void showDialogs() {
		progressDialog = MyProgressDialog.createDialog(this);
		progressDialog.setMessage("");
		progressDialog.show();
	}


	public void closeProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}
	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		ButterKnife.inject(this);
	}

	// *
	// 获取参数
	// 这里可以获取打我打开过的网页参数
	public String   args(String k, String defaultValue) {
		Intent intent = this.getIntent();
		String v = intent.getStringExtra(k);
		if (v == null)
			v = defaultValue;
		if (!v.equals("default")) {
			CCB = v;
		}
		return v;

	}
	// 封装ToQuitTheApp方法
	public void ToQuitTheApp() {
		if (isExit) {
			// 存储退出信息
			SharedPreferences sp = this.getSharedPreferences(AppConstants.USER_INFO, MODE_PRIVATE);
			SharedPreferences.Editor editor = sp.edit();
			editor.putBoolean("signoutFromBack", true);
			editor.commit();
			isExit = false;
			finish();
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			System.exit(0);

		} else {
			isExit = true;
			Toast.makeText(this, "再按一次退出APP", Toast.LENGTH_SHORT).show();
			mHandler.sendEmptyMessageDelayed(0, 3000);// 3秒后发送消息
		}
	}

	// 创建Handler对象，用来处理消息
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {// 处理消息
			super.handleMessage(msg);
			isExit = false;
		}
	};
	// log数据
	public void log(String log) {
		Log.e("live", log);
	}

	// 跳转activity
	public void go(Activity a, HashMap<String, String> args) {
		Intent intent = new Intent(a, MainActivity.class);
		if (args != null) {
			Iterator iter = args.entrySet().iterator();
			while (iter.hasNext()) {
				HashMap.Entry entry = (HashMap.Entry) iter.next();
				String k = (String) entry.getKey();
				String v = (String) entry.getValue();

			}
		}
		startActivity(intent);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ButterKnife.reset(this);
	}
}
