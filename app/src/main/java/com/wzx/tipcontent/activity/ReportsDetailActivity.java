package com.wzx.tipcontent.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wzx.tipcontent.R;
import com.wzx.tipcontent.adapter.onclicklistener.OnItemClickLitener;
import com.wzx.tipcontent.adapter.report_recommend_Adapter;
import com.wzx.tipcontent.base.BaseActivity;
import com.wzx.tipcontent.bean.workBean;
import com.wzx.tipcontent.custom.ServerRequest;
import com.wzx.tipcontent.kit.AppConstants;
import com.wzx.tipcontent.utils.Header;
import com.wzx.tipcontent.utils.LocalJsonResolutionUtils;
import com.wzx.tipcontent.utils.Tool;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

public class ReportsDetailActivity extends BaseActivity implements ServerRequest.InterfaceCode, OnItemClickLitener {


    @InjectView(R.id.iv_report_detail)
    ImageView imageView;
    @InjectView(R.id.tv_report_content)
    TextView mContent;
    @InjectView(R.id.rv_report_list)
    RecyclerView mRecycle;
    @InjectView(R.id.tv_recomment_tip)
     TextView mTip;
    @InjectView(R.id.btn_submit)
    Button mSubmit;
    @InjectView(R.id.et_comment)
    EditText etComment;
    @InjectView(R.id.laoutInfo)
    LinearLayout lloutInfo;

    @InjectView(R.id.tv_content_discuss)
    TextView mRelease;

    @InjectView(R.id.laout_discuss)
    LinearLayout llDiscuss;

    @InjectView(R.id.dialog_comment_bt)
    TextView mBtnComment;
    @InjectView(R.id.dialog_comment_et)
    EditText etDiscuss;
    report_recommend_Adapter adapter;
    private  workBean.DataBean  dataBean;
    private List<workBean.DataBean> dataList=new ArrayList<>();
    private  int netWork=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        dataBean= (workBean.DataBean) getIntent().getSerializableExtra("bean");
        header=new Header(this,"");
        header.setTitle("推荐");
        initGetInfo();
        initView();
    }

    private void initView() {
        String imageurl=dataBean.getImage().substring(dataBean.getImage().lastIndexOf("/")+1);
        Glide.with(this).load(AppConstants.Url+imageurl).placeholder(R.drawable.default_image).error(R.drawable.default_image).into(imageView);
        mContent.setText(dataBean.getContents());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecycle.setLayoutManager(manager);
//        adapter=new report_recommend_Adapter(this,dataList);
        adapter.setOnItemClickLitener(this);
        mRecycle.setAdapter(adapter);

    }
    //获取推荐数据
    private void initGetInfo() {
        showDialogs();
        netWork=1;
        String url = "http://139.224.110.190:91/Roustavi/GetRecommendLineReport";
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("lineReportId", dataBean.getId()+"");
        ServerRequest request = new ServerRequest(url, Tool.setRequestParams(hashMap));
        request.requestLoginReturn();
        request.setInterfaceCode(this);
    }

    //发布评论
    private void initGetComment(){
        showDialogs();
        netWork=2;
        String url = "http://139.224.110.190:91/Roustavi/AddComments";
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("lineReportId", dataBean.getId()+"");
        hashMap.put("content",etComment.getText().toString().trim());
        ServerRequest request = new ServerRequest(url, Tool.setRequestParams(hashMap));
        request.requestPostReturn();
        request.setInterfaceCode(this);
    }

    @Override
    public void Success(String jsonData) {
        closeProgressDialog();
        if(netWork==1) {
            workBean workBean = LocalJsonResolutionUtils.JsonToObject(jsonData, workBean.class);
            dataList = workBean.getData();
            if (dataList != null && dataList.size() > 0) {
                adapter.clearData();
//                adapter.addData(dataList);
                adapter.notifyDataSetChanged();
            } else {
                mTip.setVisibility(View.GONE);
            }
        }else{
            etDiscuss.setHint("写评论....");
            etDiscuss.setText("");
            comment(false);
            Tool.showToast(this,"评论或留言成功",3000);
        }
    }

    @Override
    public void Fail(String failContent) {
        closeProgressDialog();
        if(failContent.contains(AppConstants.requestStr)){
            Tool.showToast(this,"网络不给力,请重试",3000);
        }else {
            if(netWork==2){
                lloutInfo.setVisibility(View.GONE);
                etDiscuss.setHint("写评论....");
                etDiscuss.setText("");
                comment(false);
            }

            try {
                JSONObject jsonObject = new JSONObject(failContent);
                Tool.showToast(this,jsonObject.optString("result"),3000);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick({R.id.btn_submit,R.id.tv_content_discuss})
    public void Onclick(View view){

        switch (view.getId()){
            case R.id.btn_submit:
                if(TextUtils.isEmpty(etComment.getText().toString().trim())){
                    Tool.showToast(this,"评论或留言内容不能为空",3000);
                    return;
                }
                initGetComment();
                break;
            case R.id.tv_content_discuss:
                etDiscuss.setHint("写评论....");
                comment(true);
                break;
            case R.id.dialog_comment_bt:
                if(TextUtils.isEmpty(etComment.getText().toString().trim())){
                    Tool.showToast(this,"评论或留言内容不能为空",3000);
                    return;
                }
                initGetComment();
                break;
        }


    }

    /**
     *
     * func:弹出回复框
     */
    private void comment(boolean flag) {
        if(flag){
            lloutInfo.setVisibility(View.GONE);
            llDiscuss.setVisibility(View.VISIBLE);
            onFocusChange(flag);
        }else{
            lloutInfo.setVisibility(View.VISIBLE);
            llDiscuss.setVisibility(View.GONE);
            onFocusChange(flag);
        }


    }
    /**
     * 显示或隐藏输入法
     */
    private void onFocusChange(boolean hasFocus) {
        final boolean isFocus = hasFocus;
        (new Handler()).postDelayed(new Runnable() {
            public void run() {
                InputMethodManager imm = (InputMethodManager)
                        ReportsDetailActivity.this.getSystemService(INPUT_METHOD_SERVICE);
                if (isFocus) {
                    //显示输入法
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    etDiscuss.setFocusable(true);
                    etDiscuss.requestFocus();
                } else {
                    //隐藏输入法
                    imm.hideSoftInputFromWindow(etDiscuss.getWindowToken(), 0);
                }
            }
        }, 100);
    }
        @Override
    public void onItemClick(View view, int position) {

    }
}
