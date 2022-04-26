package com.wzx.tipcontent.kit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.hardware.usb.UsbDevice;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import com.wzx.tipcontent.utils.Tool;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;


/**
 * 项目名:  Android 功能: 定义一些全局的静态变量
 * 
 * @author
 *         Reserved
 */
public class AppConstants {
	private static AppConstants CONSTANTS = null;

	public static AppConstants getInstance() {
		if (CONSTANTS == null) {
			CONSTANTS = new AppConstants();
		}
		return CONSTANTS;
	}

	// 应用名
	public static String APP_NAME = "chengdudayun";
	public static String Domain = "jytest.net";
	// SQLite数据库名
	public static String SQLITE_DB_NAME = "experientialsystem";
	public static int ISFAIL = 0;
	public static int ISSUCCESS = 1;
	public static int countDown = 120;
	public final static int PID11 = 8211;
	public final static int PID13 = 8213;
	public final static int PID15 = 8215;
	public static int noprint;
	public static boolean isBack = false;
	public static boolean ISCHECKIMG = false;
	public final static int VENDORID = 1305;
	public static String APP_ID = "";
	public static String successUrlStr = "";
	public static String failUrlStr = "";
	// SQLite 用户表名
	public static String DB_TABLE_USER = "user";
	public static String DB_TABLE_NEWSINFO = "NewsInfo";
	public static String DB_TABLE_USERINFO = "UserInfo";
	public static String REGNAME="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{5,8}$"; //用户名
	public static String REGPHONE="^((13[0-9])|(14[57])|(15[0-35-9])|(16[6])|(17[0135-8])|(18[0-9])|(19[189]))\\d{8}$"; //手机
	public static String REGPWD="^.{6,18}$";
	/**
	 * 接口和webview地址根节点名称
	 **/
	public static String APK_CONFIG_APIURL = "apiurl";
	public static String APK_CONFIG_OFFSET = "weburl";
	public static String APK_CONFIG_APKMESSAGE = "apkmessage";
	public static int FRAMGNETINDEX = 0;// onSuccess 调用接口成功
	public static final int ONSUCCESS = 0;// onSuccess 调用接口成功
	public static final int ONFAILURE = 1;// onFailure 接口返回错误
	public static final int ONLOGININTERFACEERROR = 2; // 登录信息json error
	public static final int ONSUCCESSPAPERTICKET = 3;
	public static final String requestStr = "请求状态码";
	public static final int STATE_NORMAL = 0;
	public static final int STATE_REFREH = 1;
	public static final int STATE_MORE = 2;
	public static final String SAMPLE_URL = "http://192.168.0.142:801/rtp/04182E89.flv";
	public static final String Url="http://139.224.110.190:93/";
	/**
	 * sharedpreference文件名
	 **/
	// 用户信息
	public static String USER_INFO = "account";
	// 网络地址
	public static String APP_WEB_CONFIG = "config";
	// 应用安装信息
	public static String APP_INFO = "appInformation";
	// 经纬度信息
	public static String COORDINATES = "coordinates";
	public static String TICKETSINFO = "ticketsinfo";

	public static final int FILECHOOSER_RESULTCODE = 1;
	public static final int REQ_CAMERA = FILECHOOSER_RESULTCODE + 1;
	public static final int REQ_CHOOSE = REQ_CAMERA + 1;
	/**
	 * 是否读取到配置文件信息
	 */
	public static boolean isReadConfig = false;
	// public static iDRHIDDev mHIDRealNameDev;
	public static UsbDevice mRealNameDevice;
	public static boolean isClickCalendar = false;
	public static boolean isScrollCalendar = false;
	public static float scale;
	public static int width = 0;
	/************************/
	/**
	 * 日志文件名称
	 **/
	// 安装日志
	public static String LOG_NAME_MOBILE = "mobile";
	public static String LOG_NAME_CRASH = "crash";

	/**
	 * 日志文件存放目录
	 **/
	public static String OTHER_LOG_DIR = "/sdcard/" + APP_NAME + "/log/other/";
	public static String COMMON_LOG_DIR = "/sdcard/" + APP_NAME + "/log/common/";

	/**
	 * 记住是否登录用户
	 */
//	public static ArrayList<BaseActivity> baseActivities = new ArrayList<BaseActivity>();
////	public static ArrayList<MainActivity> m_MainActivity = new ArrayList<MainActivity>();
//	public static ArrayList<TabMainActivity> m_TabMainActivity = new ArrayList<TabMainActivity>();

	/**
	 * 记录当前是哪个页面
	 */
	public static String th_Str = "home";

	/**
	 * 获取应用包信息
	 *
	 * @param
	 * @return PackageInfo
	 */
	public static PackageInfo getPackInfo(Context a) {
		try {
			PackageManager packageManager = a.getPackageManager();
			PackageInfo packInfo;
			packInfo = packageManager.getPackageInfo(a.getPackageName(), 0);
			return packInfo;
		} catch (NameNotFoundException e) {
			return null;
		}
	}

	// 应用信息常量
	public static String IMEI = null;
	public static String ICCID = null;
	public static String MAC = null;
	public static String APP_VERSION = null;
	public static String ANDROID_VERSION = null;
	public static String PHONE_MODEL = null;
	public static String PHONE_MANUFACTURER = null;
	public static String PHONE_DISPLAYMETRICS = null;
	public static String PHONE_SIMOPERATOR = null;
	public static int listItemPosition;

	private String imagePaths;
	private Uri cameraUri;
	private String compressPath;
	private String delFilePath;

	public String getDelFilePath() {
		return delFilePath;
	}

	public void setDelFilePath(String delFilePath) {
		this.delFilePath = delFilePath;
	}

	public String getImagePaths() {
		return imagePaths;
	}

	public void setImagePaths(String imagePaths) {
		this.imagePaths = imagePaths;
	}

	public Uri getCameraUri() {
		return cameraUri;
	}

	public void setCameraUri(Uri cameraUri) {
		this.cameraUri = cameraUri;
	}

	public String getCompressPath() {
		return compressPath;
	}

	public void setCompressPath(String compressPath) {
		this.compressPath = compressPath;
	}


	@SuppressLint("MissingPermission")
	public static void loadFromContext(Context context) {
		AppConstants.ANDROID_VERSION = android.os.Build.VERSION.RELEASE;
		AppConstants.PHONE_MODEL = android.os.Build.MODEL;
		AppConstants.PHONE_MANUFACTURER = android.os.Build.MANUFACTURER;

		PackageManager packageManager = context.getPackageManager();
		try {
			PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			AppConstants.APP_VERSION = "" + packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		AppConstants.IMEI = tm.getDeviceId();// 取出IMEI
		AppConstants.ICCID = tm.getSimSerialNumber();
		AppConstants.PHONE_SIMOPERATOR = tm.getSimOperatorName();


		AppConstants.MAC = getIPAddress(context);

		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		AppConstants.PHONE_DISPLAYMETRICS = dm.widthPixels + "X" + dm.heightPixels + "px";
	}


	public static String intIP2StringIP(int ip) {
		return (ip & 0xFF) + "." +
				((ip >> 8) & 0xFF) + "." +
				((ip >> 16) & 0xFF) + "." +
				(ip >> 24 & 0xFF);
	}

	public static String getIPAddress(Context context) {
		NetworkInfo info = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
		if (info != null && info.isConnected()) {
			if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
				try {
					//Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
					for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
						NetworkInterface intf = en.nextElement();
						for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
							InetAddress inetAddress = enumIpAddr.nextElement();
							if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
								return inetAddress.getHostAddress();
							}
						}
					}
				} catch (SocketException e) {
					e.printStackTrace();
				}
			} else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
				WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
				WifiInfo wifiInfo = wifiManager.getConnectionInfo();
				String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
				return ipAddress;
			}
		} else {
			//当前无网络连接,请在设置中打开网络
			Tool.showToast(context,"当前无网络连接,请在设置中打开网络",3000);
		}
		return "";
	}
}

