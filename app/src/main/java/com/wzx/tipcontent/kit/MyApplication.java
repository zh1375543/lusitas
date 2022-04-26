package com.wzx.tipcontent.kit;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.wzx.tipcontent.utils.Tool;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import me.jessyan.autosize.AutoSizeConfig;


/**
 * 项目名:or Android 功能： 应用实例
 *
 */
public class MyApplication extends Application implements Thread.UncaughtExceptionHandler {
	private static final String TAG = "CrashHandler";
	private static Stack<Activity> activityStack;
	private static MyApplication singleton;
	private static final String TAG_JPUSH = "JPush";
	// 用来存储设备信息和异常信息
	private Map<String, String> infos = new HashMap<String, String>();

	@Override
	public void onCreate() {
		super.onCreate();
		Tool.handleSSLHandshake();
		// 注册异常处理类
//		 ExceptionHandler exceptionHandler = ExceptionHandler.getInstance();
//		 exceptionHandler.init(getApplicationContext());
		Thread.setDefaultUncaughtExceptionHandler(this);
		singleton = this;
		AutoSizeConfig.getInstance().setCustomFragment(true);


       //取消严格模式  FileProvider
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
			StrictMode.setVmPolicy( builder.build() );
		}
	}

	static{
		//设置全局的Header构建器
		SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
			@Override
			public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
				return new MaterialHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
			}
		});
	}
	// Return the application instance
	public static MyApplication getInstance() {
		return singleton;
	}
	/*
	 * add Activity 添加Activity到栈
	 */
	public void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}

	/**
	 * get current Activity 获取当前Activity(栈中最后一个压入的)
	 */
	public Activity currentActivity() {
		Activity activity = activityStack.lastElement();
		return activity;
	}

	/**
	 * 结束当前Activity(栈中最后一个压入的)
	 */
	public void finishActivity() {
		Activity activity = activityStack.lastElement();
		finishActivity(activity);
	}

	/**
	 * 结束当前Activity(栈中最后一个压入的)
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * 结束指定类名的Activity
	 */
	public void finishActivity(Class<?> cls) {
		for (Activity activity : activityStack) {
			if (activity.getClass().equals(cls)) {
				finishActivity(activity);
			}
		}
	}

	/**
	 * 结束所有Activity
	 */
	public void finishAllActivity() {
		for (int i = 0, size = activityStack.size(); i < size; i++) {
			if (null != activityStack.get(i)) {
				activityStack.get(i).finish();
			}
		}
		activityStack.clear();
	}

	/**
	 * 退出应用程序
	 */
	public void AppExit() {
		try {
			finishAllActivity();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void uncaughtException(Thread thread, Throwable exception) {
		handleException(exception);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("Exit", true);
		startActivity(intent);
		System.exit(1);
	}

	/**
	 * 自定义错误处理，收集错误信息， 发送错误报告等操作均在此完成
	 * 
	 * @param ex
	 *            return true:如果处理了该异常信息；否则返回false
	 */
	private boolean handleException(final Throwable ex) {
		if (ex == null) {
			return false;
		}

		// 使用Toast来显示异常信息
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				Toast.makeText(singleton, "很抱歉，程序出现异常，即将退出", Toast.LENGTH_LONG).show();
				Looper.loop();
			}
		}.start();
		// 收集设备参数信息
		collectDeviceInfo(singleton);
		String errorText = saveCrashInfo2String(ex);
		saveThrowableMessage(errorText);
		// 发送错误报告
//		CrashLogBean crashLogBean = getCrashLog(ex);
//		CrashLogDao crashLogTask = new CrashLogDao(crashLogBean,this);
//		crashLogTask.sendLog();
		return true;
	}


	/**
	 * 获得异常日志
	 *
	 */
//	private CrashLogBean getCrashLog(Throwable ex) {
//		String errorText = saveCrashInfo2String(ex);
//		CrashLogBean crashLogBean = new CrashLogBean();
//		crashLogBean.setImei(AppConstants.IMEI);
//		crashLogBean.setIccid(AppConstants.ICCID);
//		crashLogBean.setMac(AppConstants.MAC);
//		crashLogBean.setErrorText(errorText);
//		crashLogBean.setAppType(2);
//		return crashLogBean;
//	}

	/**
	 * 收集设备信息
	 * 
	 * @param
	 */
	public void collectDeviceInfo(Context ctx) {
		try {
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				String versionName = pi.versionName == null ? "null" : pi.versionName;
				String versionCode = pi.versionCode + "";
				infos.put("versionName", versionName);
				infos.put("versionCode", versionCode);
			}
		} catch (NameNotFoundException e) {
			Log.e(TAG, "an error occured when collect package info", e);
		}
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				infos.put(field.getName(), field.get(null).toString());
				Log.d(TAG, field.getName() + " : " + field.get(null));
			} catch (Exception e) {
				Log.e(TAG, "an error occured when collect crash info", e);
			}
		}
	}

	/**
	 * 保存错误信息到文件中
	 * 
	 * @param ex
	 * @return
	 */
	private String saveCrashInfo2String(Throwable ex) {

		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> entry : infos.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append(key + "=" + value + "\n");
		}
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String result = writer.toString();
		sb.append(result);
		return sb.toString();
	}


	private String logFilePath = Environment.getExternalStorageDirectory() + File.separator + "Android" +
			File.separator + "data" + File.separator + "crashLog";

	private void saveThrowableMessage(String errorMessage) {
		if (TextUtils.isEmpty(errorMessage)) {
			return;
		}
		File file = new File(logFilePath);
		if (!file.exists()) {
			boolean mkdirs = file.mkdirs();
			if (mkdirs) {
				writeStringToFile(errorMessage, file);
			}
		} else {
			writeStringToFile(errorMessage, file);
		}
	}

	private void writeStringToFile(final String errorMessage, final File file) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				FileOutputStream outputStream = null;
				Date d = new Date();
				SimpleDateFormat s = new SimpleDateFormat("MM-dd HH:mm:ss");
				String dateStr = s.format(d);
				try {
					ByteArrayInputStream inputStream = new ByteArrayInputStream(errorMessage.getBytes());
					outputStream = new FileOutputStream(new File(file, dateStr + ".txt"));
					int len = 0;
					byte[] bytes = new byte[1024];
					while ((len = inputStream.read(bytes)) != -1) {
						outputStream.write(bytes, 0, len);
					}
					outputStream.flush();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (outputStream != null) {
						try {
							outputStream.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}).start();
	}

}
