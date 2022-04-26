package com.wzx.tipcontent.custom;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wzx.tipcontent.kit.AppConstants;
import com.wzx.tipcontent.utils.HttpUtil;

import org.apache.http.Header;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author WIN 项目名：功能： 公共的接口请求方法
 * wpw.com Inc All Rights Reserved
 */
public class ServerRequest {

    // 请求地址
    private String requestUrl;
    // 请求参数
    private RequestParams requestParams;
    private  Map<String, String> hashMap;
    private String jsonUrl;
    // 返回的json字符串
    private InterfaceCode interfaceCode;
    private Context context;
    public final String ONFAILURE = "1";// onFailure

    public InterfaceCode getInterfaceCode() {
        return interfaceCode;
    }

    public void setInterfaceCode(InterfaceCode interfaceCode) {
        this.interfaceCode = interfaceCode;
    }

    public ServerRequest(String requestUrl, RequestParams requestParams) {
        super();
        this.requestUrl = requestUrl;
        this.requestParams = requestParams;
    }
    public ServerRequest(String requestUrl,  Map<String, String> hashMap) {
        super();
        this.requestUrl = requestUrl;
        this.hashMap = hashMap;
    }
    public ServerRequest(String requestUrl, String jsonUrl) {
        super();
        this.requestUrl = requestUrl;
        this.jsonUrl = jsonUrl;
    }

    /**
     * 有参有返回值
     *
     * @param
     */
    public void requestHaveReturn(Context context) {
        HttpUtil.get(requestUrl, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] response) {
                if (arg0 == 200 && response != null) {
                    Gson gson = new Gson();
                    String jsonData;
                    try {
                        jsonData = new String(response, "UTF-8");
                        if ((jsonData != null) && (jsonData.contains("{"))) {
                            JSONObject jsonObject = new JSONObject(jsonData);
                            JSONObject system_result = new JSONObject(jsonObject.optString("system_result"));
                            if (system_result.optString("code").equals("Success")) {
                                JSONObject business_retsult = new JSONObject(jsonObject.optString("business_retsult"));
                                if (business_retsult.optInt("code") == 1) {
                                    if (interfaceCode != null) {
                                        interfaceCode.Success(jsonData);
                                    }
                                } else {
                                    if (interfaceCode != null) {
                                        interfaceCode.Fail(jsonData);
                                    }
                                }
                            } else {
                                if (interfaceCode != null) {
                                    interfaceCode.Fail(jsonData);
                                }
                            }
                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    if (interfaceCode != null) {
                        interfaceCode.Fail(AppConstants.requestStr + arg0);
                    }
                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
                // TODO Auto-generated method stub
                if (interfaceCode != null) {
                    interfaceCode.Fail(AppConstants.requestStr + arg0);
                }
            }
        }, context);
    }


    /**
     * 有参有返回值
     *
     * @param
     */
    public void requestLoginReturn() {
        HttpUtil.get(requestUrl, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] response) {
                if (arg0 == 200 && response != null) {
                    Gson gson = new Gson();
                    String jsonData;
                    try {
                        jsonData = new String(response, "UTF-8");
                        if ((jsonData != null) && (jsonData.contains("{"))) {
                            JSONObject jsonObject = new JSONObject(jsonData);
                            if (jsonObject.optInt("code") == 200) {
                                if (interfaceCode != null) {
                                    interfaceCode.Success(jsonData);
                                }

                            } else {
                                if (interfaceCode != null) {
                                    interfaceCode.Fail(jsonData);
                                }
                            }
                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    if (interfaceCode != null) {
                        interfaceCode.Fail(AppConstants.requestStr + arg0);
                    }
                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
                // TODO Auto-generated method stub
                if (interfaceCode != null) {
                    interfaceCode.Fail(AppConstants.requestStr + arg0);
                }
            }
        });
    }

    /**
     * 有参有返回值post
     *
     * @param
     */
    public void requestPostReturn() {
        HttpUtil.post(requestUrl, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] response) {
                if (arg0 == 200 && response != null) {
                    Gson gson = new Gson();
                    String jsonData;
                    try {
                        jsonData = new String(response, "UTF-8");
                        if ((jsonData != null) && (jsonData.contains("{"))) {
                            JSONObject jsonObject = new JSONObject(jsonData);
                            if (jsonObject.optInt("code") == 200) {
                                if (interfaceCode != null) {
                                    interfaceCode.Success(jsonData);
                                }

                            } else {
                                if (interfaceCode != null) {
                                    interfaceCode.Fail(jsonData);
                                }
                            }
                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    if (interfaceCode != null) {
                        interfaceCode.Fail(AppConstants.requestStr + arg0);
                    }
                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
                // TODO Auto-generated method stub
                if (interfaceCode != null) {
                    interfaceCode.Fail(AppConstants.requestStr + arg0);
                }
            }
        });
    }


    /**
     * 有参有返回值post
     *
     * @param
     */
    public void requestPostReturn(Context context) {
        ByteArrayEntity entity = null;
        try {
            entity = new ByteArrayEntity(jsonUrl.getBytes("UTF-8"));
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpUtil.post(context,requestUrl, entity,"application/json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] response) {
                if (arg0 == 200 && response != null) {
                    Gson gson = new Gson();
                    String jsonData;
                    try {
                        jsonData = new String(response, "UTF-8");
                        if ((jsonData != null) && (jsonData.contains("{"))) {
                            JSONObject jsonObject = new JSONObject(jsonData);
                            if (jsonObject.optInt("code") == 200) {
                                if (interfaceCode != null) {
                                    interfaceCode.Success(jsonData);
                                }

                            } else {
                                if (interfaceCode != null) {
                                    interfaceCode.Fail(jsonData);
                                }
                            }
                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    if (interfaceCode != null) {
                        interfaceCode.Fail(AppConstants.requestStr + arg0);
                    }
                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
                // TODO Auto-generated method stub
                if (interfaceCode != null) {
                    interfaceCode.Fail(AppConstants.requestStr + arg0);
                }
            }
        });
    }


    public interface InterfaceCode {
        public void Success(String jsonData);

        public void Fail(String failContent);
    }

}
