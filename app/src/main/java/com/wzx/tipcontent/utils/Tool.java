package com.wzx.tipcontent.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.wzx.tipcontent.kit.AppConstants;
import com.wzx.tipcontent.kit.MyApplication;

import org.apache.commons.codec.binary.Base64;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;



/**
 * ?????????:for Android ??????: ?????????
 *
 */

public class Tool {
    public static final String FILE_NAME = "app";
    private static Toast mToast;
    private static List<String> listPer;
    /**
     * ???dp???????????????px
     *
     * @param context
     * @param
     * @return
     */
    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int getSDKVersion() {
        return Build.VERSION.SDK_INT;
    }

    public static void showToast(Context context, CharSequence text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, duration);
        } else {
            mToast.setText(text);
            mToast.setDuration(duration);
        }
        mToast.show();
    }

    /**
     * ?????????px?????????dp
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * ?????????px?????????sp
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * ???????????????
     */
    public static String[] getSplitMsg(String resultMsg) {
        String[] results = null;
        if (resultMsg != null) {
            results = resultMsg.split("\\|");
        }
        return results;
    }

    /**
     * ???md5???????????????
     *
     * @param txt
     * @return
     */
    public static String md5(String txt) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(txt.getBytes("utf-8"));
            StringBuffer buf = new StringBuffer();
            for (byte b : md.digest()) {
                buf.append(String.format("%02x", b & 0xff));
            }
            return buf.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ???Base64???????????????
     *
     * @param string
     * @return
     */
    public static String encryptBase64(String string) {
        String encryptString = "";
        if (string.equals("")) {
            return encryptString;
        }
        encryptString = new String(Base64.encodeBase64(string.getBytes()));
        return encryptString;
    }

    /**
     * ???Base64???????????????
     *
     * @param string
     * @return
     */
    public static String decoderBase64(String string) {
        String decodeString = "";
        if (string.equals("")) {
            return decodeString;
        }
        decodeString = new String(Base64.decodeBase64(string.getBytes()));
        return decodeString;
    }

    /**
     * ???????????????????????????????????? ?????????+
     *
     * @param string
     * @return
     */
    public static String decodeString(String string) {
        String decodeString = "";
        if (string == null) {
            return decodeString;
        }
        try {
            decodeString = new String(URLDecoder.decode(URLEncoder.encode(string, "utf-8"), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decodeString;
    }

    /**
     * ???????????????
     *
     * @param string
     * @return
     */
    public static String decodeStrings(String string) {
        String decodeString = "";
        if (string == null) {
            return decodeString;
        }
        try {
            decodeString = new String(URLDecoder.decode(string, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decodeString;
    }

    /**
     * ???url??????Map??????????????????
     *
     * @param param Map<String, String>
     * @return String
     */
    public static String urlMapToString(Map<String, String> param) {
        if (param == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        Set<String> keys = param.keySet();
        boolean first = true;

        for (String key : keys) {
            String value = param.get(key);
            if (!TextUtils.isEmpty(value)) {
                if (first) {
                    first = false;
                } else {
                    sb.append("&");
                }
                try {
                    sb.append(URLEncoder.encode(key, "UTF-8")).append("=").append(URLEncoder.encode(value, "UTF-8"));
                } catch (UnsupportedEncodingException e) {

                }
            }
        }

        return sb.toString();
    }

    /**
     * ???????????????????????????
     *
     * @param string
     * @return boolean
     */
    public static boolean isEmptyString(String string) {
        return string == null || string.trim().equals("") ? true : false;
    }

    /**
     * ????????????
     *
     * @param closeable
     */
    public static void closeSilently(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException ignored) {

            }
        }
    }

    /**
     * ??????????????????????????????WiFi
     *
     * @return boolean
     */
    public static boolean isWiFi() {
        boolean result = false;
        // ????????????
        try {
            String service = Context.CONNECTIVITY_SERVICE;
            ConnectivityManager connectivityManager = (ConnectivityManager) MyApplication.getInstance()
                    .getSystemService(service);
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            if (activeNetwork != null) {
                result = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI ? true : false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * ??????Bitmap
     *
     * @param source ???????????????bitmap
     * @param angle  ???????????????
     * @return bitmap
     */
    public static Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    /**
     * ??????????????????????????????????????????bitmap
     *
     * @param path   ????????????
     * @param width  ??????
     * @param height ??????
     * @return bitmap
     */
    public static Bitmap getBitmapFromFile(String path, int width, int height) {
        File dst = new File(path);
        if (null != dst && dst.exists()) {
            BitmapFactory.Options opts = null;
            if (width > 0 && height > 0) {
                opts = new BitmapFactory.Options(); // ??????inJustDecodeBounds???true??????decodeFile???????????????????????????????????????????????????????????????
                opts.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(dst.getPath(), opts);
                // ????????????????????????
                final int minSideLength = Math.min(width, height);
                opts.inSampleSize = computeSampleSize(opts, minSideLength, width * height);
                opts.inJustDecodeBounds = false;// ??????????????????????????????false???????????????????????????????????????true
                opts.inInputShareable = true;
                opts.inPurgeable = true;
            }
            try {
                return BitmapFactory.decodeFile(dst.getPath(), opts);
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128
                : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    /**
     * Get image from network
     *
     * @param path The path of image
     * @return InputStream
     * @throws Exception
     */
    public static InputStream getImageStream(String path) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            return conn.getInputStream();
        }
        return null;
    }

    /**
     * ??????url???????????????????????????????????????
     *
     * @param strURL url??????
     * @return url??????????????????
     */
    private static String TruncateUrlPage(String strURL) {
        String strAllParam = null;
        String[] arrSplit = null;
        strURL = strURL.trim();
        arrSplit = strURL.split("[?]");
        if (strURL.length() > 1) {
            if (arrSplit.length > 1) {
                if (arrSplit[1] != null) {
                    strAllParam = arrSplit[1];
                }
            }
        }
        return strAllParam;
    }

    /**
     * ?????????url????????????????????? ??? "index.jsp?Action=del&id=123"????????????Action:del,id:123??????map???
     *
     * @param URL url??????
     * @return url??????????????????
     */
    public static Map<String, String> urlStringToMap(String URL) {
        Map<String, String> mapRequest = new HashMap<String, String>();
        String[] arrSplit = null;
        String strUrlParam = TruncateUrlPage(URL);
        if (strUrlParam == null) {
            return mapRequest;
        }
        // ?????????????????????
        arrSplit = strUrlParam.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("[=]", 2);

            if (arrSplitEqual != null) {
                // ???????????????
                if (arrSplitEqual.length == 1) {
                    mapRequest.put(arrSplitEqual[0], "");

                } else if (arrSplitEqual.length > 1 && !arrSplitEqual[0].trim().equals("")) {
                    // ????????????
                    mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

                }
            }

        }
        return mapRequest;
    }

    /**
     * ??????????????????????????????
     *
     * @param date1
     * @param date2
     * @return boolean
     */
    public static boolean isSame(Date date1, Date date2) {
        if ((date1.getYear() == date2.getYear()) && (date1.getMonth() == date2.getMonth())
                && (date1.getDay() == date2.getDay())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * ??????????????????????????????????????????????????????????????????????????????scale????????? ??????????????????????????????????????????
     *
     * @param v1
     *            ?????????
     * @param v2
     *            ??????
     * @param scale
     *            ???????????????????????????????????????????????????
     * @return ??????????????????
     */
    public static float div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).floatValue();
    }


    /**
     * ????????????IP??????
     *
     * @return
     */
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * ??????????????????SDCard
     *
     * @return
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * ???????????????????????????????????????
     */
    public static boolean isColorValue(String colorStr) {
        boolean bool = false;
        boolean flg = Pattern.matches("\\#[0-9a-fA-F]{6}", colorStr);
        if (flg)
            bool = true;
        return bool;
    }

    /**
     * ??????????????????
     *
     * @param account
     * @param password
     */
    public static String PasswordEncryption(String account, String password) {
        String pass64 = URLEncoder.encode(encryptBase64(password));
        String strmd5 = md5(account + "+" + password);
        String str = strmd5.substring(0, 16) + pass64 + strmd5.substring(16, strmd5.length());
        System.out.println(str);
        return str;
    }

    /**
     * ??????????????????????????????(IMIE )
     *
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String fetch_status(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);//
        String str = "";
        str += "DeviceId(IMEI) = " + tm.getDeviceId() + "\n";
        str += "DeviceSoftwareVersion = " + tm.getDeviceSoftwareVersion() + "\n";
        str += "Line1Number = " + tm.getLine1Number() + "\n";
        str += "NetworkCountryIso = " + tm.getNetworkCountryIso() + "\n";
        str += "NetworkOperator = " + tm.getNetworkOperator() + "\n";
        str += "NetworkOperatorName = " + tm.getNetworkOperatorName() + "\n";
        str += "NetworkType = " + tm.getNetworkType() + "\n";
        str += "PhoneType = " + tm.getPhoneType() + "\n";
        str += "SimCountryIso = " + tm.getSimCountryIso() + "\n";
        str += "SimOperator = " + tm.getSimOperator() + "\n";
        str += "SimOperatorName = " + tm.getSimOperatorName() + "\n";
        str += "SimSerialNumber = " + tm.getSimSerialNumber() + "\n";
        str += "SimState = " + tm.getSimState() + "\n";
        str += "SubscriberId(IMSI) = " + tm.getSubscriberId() + "\n";
        str += "VoiceMailNumber = " + tm.getVoiceMailNumber() + "\n";
        return str;
    }

    /**
     * ???????????????????????????
     *
     * @param phoneNumber
     * @return
     */
    public static boolean isPhoneNumber(String phoneNumber) {
        boolean bool = false;
        boolean flg = Pattern.matches("^[1][3578][0-9]{9}$", phoneNumber);
        if (flg)
            bool = true;
        return bool;
    }

    /**
     * ?????????????????????????????????????????????
     *
     * @return
     */
    public static String getUniqueID1() {
        String m_szLongID = AppConstants.IMEI + AppConstants.ICCID + AppConstants.MAC;
        // compute md5
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
        }
        m.update(m_szLongID.getBytes(), 0, m_szLongID.length());
        // get md5 bytes
        byte p_md5Data[] = m.digest();
        // create a hex string
        String m_szUniqueID = new String();
        for (int i = 0; i < p_md5Data.length; i++) {
            int b = (0xFF & p_md5Data[i]);
            // if it is a single digit, make sure it have 0 in front (proper
            // padding)
            if (b <= 0xF)
                m_szUniqueID += "0";
            // add number to string
            m_szUniqueID += Integer.toHexString(b);
        } // hex string to uppercase
        m_szUniqueID = m_szUniqueID.toUpperCase();
        return m_szUniqueID;
    }

    public static float dip2px(float dipValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (dipValue * scale + 0.5f);
    }

    /**
     * ??????????????????????????? dp ????????? ????????? px(??????)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * ????????????????????????
     *
     * @param context
     * @param brightness
     */
    public static void setLight(Activity context, int brightness) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.screenBrightness = Float.valueOf(brightness) * (1f / 255f);
        context.getWindow().setAttributes(lp);
    }


    static long strDate = 0;
    static Thread t;

    public static long getIntentDates() {
        t = new Thread(new Runnable() {
            public void run() {
                URL url;
                Date date = null;
                try {
                    url = new URL("https://www.baidu.com/");
                    URLConnection uc = url.openConnection();// ??????????????????
                    uc.connect(); // ????????????
                    long ld = uc.getDate(); // ????????????????????????
                    date = new Date(ld); // ???????????????????????????
                    strDate = date.getTime();
                } catch (Exception e) {
                    // e.printStackTrace();
                    strDate = 0;
                }
            }
        });
        t.start();
        return strDate;
    }


    static boolean isSuccessful = false;

    /***
     * ??????JSON?????? ??????????????????????????????{???[ ??????????????????????????????JSON???????????????
     *
     * @param str
     * @return
     */
    public static int getJSONType(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }

        final char[] strChar = str.substring(0, 1).toCharArray();
        final char firstChar = strChar[0];

        if (firstChar == '{') {
            return 1;
        } else if (firstChar == '[') {
            return 2;
        } else {
            return 3;
        }
    }

    /**
     * ?????????????????????
     *
     * @param activity ???????????????activity
     * @param
     */
    @SuppressLint("NewApi")
    public static void setColor(Activity activity, int view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // ?????????????????????
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // ????????????????????????????????????
            View statusView = createStatusView(activity, view);
            // ?????? statusView ????????????
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.addView(statusView);
            // ????????????????????????
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }

    /**
     * ????????????????????????????????????
     *
     * @param activity
     */
    @SuppressLint("NewApi")
    public static void setTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // ?????????????????????
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // ????????????????????????
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(false);
            rootView.setClipToPadding(true);
        }
    }


    /**
     * @param activity
     * @param view     ???????????????
     * @return
     */
    private static View createStatusView(Activity activity, int view) {
        // ?????????????????????
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);

        // ??????????????????????????????????????????
        View statusView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                statusBarHeight);
        statusView.setLayoutParams(params);
        statusView.setBackgroundColor(view);
        return statusView;
    }

    /**
     * ????????????????????????
     */
    public static void stopCountdownTimer(Timer CountdownTimer, TimerTask CountdownTask) {
        if (CountdownTimer != null) {
            CountdownTimer.cancel();
            CountdownTimer = null;
        }
        if (CountdownTask != null) {
            CountdownTask.cancel();
            CountdownTask = null;
        }
    }

    /**
     * ????????????????????????
     */
    public static void startCountdownTimer(Timer CountdownTimer, TimerTask CountdownTask, final Handler handler1) {
        stopCountdownTimer(CountdownTimer, CountdownTask);
        if (CountdownTimer == null) {
            CountdownTimer = new Timer();
        }

        if (CountdownTask == null) {
            CountdownTask = new TimerTask() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    Message message = new Message();
                    message.what = 1;
                    handler1.sendMessage(message);
                }
            };
        }
        if (CountdownTimer != null && CountdownTask != null)
            CountdownTimer.schedule(CountdownTask, 1000, 1000);

    }


    /**
     * ??????????????????????????????
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * ??????????????????????????????
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }


    /**
     * ????????????
     *
     * @param nameStr
     */
    public static void setAddressList(String nameStr) {
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences("addresslist", Context.MODE_PRIVATE);
        ArrayList array = new ArrayList<String>();
        String strs = "";
        String str[] = sp.getString("city", "").split(",");
        for (int i = 0; i < str.length; i++) {
            if (!str[i].equals("") && !nameStr.equals(str[i])) {
                array.add(str[i]);
            }
        }
        if (array.size() >= 4) {
            array.remove(array.size() - 1);
        }
        array.add(0, nameStr);

        for (int i = 0; i < array.size(); i++) {
            if (i == array.size() - 1) {
                strs = strs + array.get(i);
            } else {
                strs = strs + array.get(i) + ",";
            }
        }

        SharedPreferences.Editor editor = sp.edit();
        editor.putString("city", strs);
        editor.commit();
    }




    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("TLS");
            // trustAllCerts?????????????????????
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }




    public static String getTime(Date date) {//???????????????????????????????????????
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    public static String getYear(Date date) {//???????????????????????????????????????
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
    public static String getHour(Date date) {//???????????????????????????????????????
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }
    public static RequestParams setRequestParams(Map<String, String> hashMap) {
        RequestParams params = new RequestParams();
        params.put("sign", signs(hashMap));
        for (String key : hashMap.keySet()) {
            params.put(key, hashMap.get(key));
        }
        return params;
    }
    public static String jsonUrl(Map<String, String> hashMap) {
        Map<String, String> hashMaps=hashMap;
        hashMaps.put("sign",signs(hashMap));
        Gson gson = new Gson();
        String postString = gson.toJson(hashMaps);
        return postString;
    }



    public static String signs(Map<String, String> hashMap) {
        return Tool
                .md5(SignUtils.formatUrlMap(hashMap, false, false) + "drlyjetjsdljgpl");
    }
    /**
     * @param rex ??????????????????
     * @param str ?????????????????????
     * @return ??????????????????????????????????????????????????????true???????????????false
     */
    public static boolean match(String rex, String str) {
        // TODO Auto-generated method stub
        Pattern pattern = Pattern.compile(rex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
