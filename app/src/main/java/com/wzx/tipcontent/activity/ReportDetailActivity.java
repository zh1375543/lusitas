package com.wzx.tipcontent.activity;

import android.content.Intent;
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

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wzx.tipcontent.R;
import com.wzx.tipcontent.adapter.onclicklistener.OnItemClickLitener;
import com.wzx.tipcontent.adapter.report_comment_Adapter;
import com.wzx.tipcontent.adapter.report_recommend_Adapter;
import com.wzx.tipcontent.adapter.workAdapter;
import com.wzx.tipcontent.base.BaseActivity;
import com.wzx.tipcontent.bean.reportDetailBean.DataBean.RecommendLineReportBean;
import com.wzx.tipcontent.bean.reportDetailBean;
import com.wzx.tipcontent.bean.workBean;
import com.wzx.tipcontent.custom.RecycleViewDivider;
import com.wzx.tipcontent.custom.ServerRequest;
import com.wzx.tipcontent.kit.AppConstants;
import com.wzx.tipcontent.utils.Header;
import com.wzx.tipcontent.utils.LocalJsonResolutionUtils;
import com.wzx.tipcontent.utils.Tool;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

public class ReportDetailActivity extends BaseActivity implements ServerRequest.InterfaceCode, OnItemClickLitener {


    @InjectView(R.id.iv_report_detail)
    ImageView imageView;
    @InjectView(R.id.tv_report_content)
    TextView mContent;
    @InjectView(R.id.rv_report_list)
    RecyclerView mRecycle;
    @InjectView(R.id.tv_recomment_tip)
     TextView mTip;
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

    @InjectView(R.id.swipeCommentLaout)
    SmartRefreshLayout mSflout;

    @InjectView(R.id.rv_comment_list)
    RecyclerView mCommentRecycle;

    report_recommend_Adapter adapter;
    report_comment_Adapter mCommentAdapter;
    private  int reportId;
    private List<RecommendLineReportBean> dataList=new ArrayList<>();
    private List<reportDetailBean.DataBean.CommentsdataBean> commentsdataBeanList=new ArrayList<>();
    private  int netWork=1;
    private int page=1;
    private int state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        reportId= getIntent().getIntExtra("bean",0);
        header=new Header(this,"");
        initGetInfo();
        initView();
    }

    private void initView() {
        //推荐
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecycle.setLayoutManager(manager);
        adapter=new report_recommend_Adapter(this,dataList);
        adapter.setOnItemClickLitener(this);
        mRecycle.setAdapter(adapter);

        //评论
        RecycleViewDivider viewDivider = new RecycleViewDivider(this, DividerItemDecoration.VERTICAL);
        viewDivider.setDrawable(ContextCompat.getDrawable(ReportDetailActivity.this, R.drawable.shape_line));
        LinearLayoutManager managers = new LinearLayoutManager(this);
        mCommentRecycle.addItemDecoration(viewDivider);
        mCommentRecycle.setLayoutManager(managers);
        mCommentAdapter=new report_comment_Adapter(this,commentsdataBeanList);
        mCommentRecycle.setAdapter(mCommentAdapter);

        mSflout.setEnableRefresh(true);//启用刷新
        mSflout.setEnableLoadMore(true);//启动加载
        mSflout.setEnableAutoLoadMore(false);
        mSflout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page = 1;
                state = AppConstants.STATE_REFREH;
                initGetInfo();
            }
        });
        mSflout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page = page + 1;
                state = AppConstants.STATE_MORE;
                initGetInfo();
            }
        });

    }
    //获取某条线报详情
    private void initGetInfos() {
        showDialogs();
        netWork=1;
        String url = "http://139.224.110.190:91/Roustavi/GetLineReportDetail";
        Map<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put("pageIndex",page+"");
        hashMap.put("lineReportId", reportId+"");
        ServerRequest request = new ServerRequest(url, Tool.jsonUrl(hashMap));
        request.requestPostReturn(this);
        request.setInterfaceCode(this);
    }
    //获取某条线报详情
    private void initGetInfo() {
        showDialogs();
        netWork=1;
        String url = "http://139.224.110.190:91/Roustavi/GetLineReportDetail";
        Map<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put("pageIndex",page+"");
        hashMap.put("lineReportId", reportId+"");
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
        hashMap.put("lineReportId",reportId+"");
        hashMap.put("content",etDiscuss.getText().toString().trim());
        ServerRequest request = new ServerRequest(url, Tool.setRequestParams(hashMap));
        request.requestPostReturn();
        request.setInterfaceCode(this);
    }

    @Override
    public void Success(String jsonData) {
        closeProgressDialog();
        if(netWork==1) {
            reportDetailBean workBean = LocalJsonResolutionUtils.JsonToObject(jsonData, reportDetailBean.class);
            dataList = workBean.getData().getRecommendLineReport();
            commentsdataBeanList=workBean.getData().getCommentsdata();
            header.setTitle(workBean.getData().getClassIfyName());
            if(!TextUtils.isEmpty(workBean.getData().getImage())){
                String imageurl=workBean.getData().getImage().substring(workBean.getData().getImage().lastIndexOf("/")+1);
                Glide.with(this).load(AppConstants.Url+imageurl).placeholder(R.drawable.default_image).error(R.drawable.default_image).into(imageView);
            }
            mContent.setText(workBean.getData().getContents());
            if (dataList != null && dataList.size() > 0) {
                adapter.clearData();
                adapter.addData(dataList);
                adapter.notifyDataSetChanged();
            } else {
                mTip.setVisibility(View.GONE);
            }
            bindAdapter();
        }else{
            comment(false);
            Tool.showToast(this,"评论成功",3000);
            List<reportDetailBean.DataBean.CommentsdataBean> addcommentsdataBeanList=new ArrayList<>();
            reportDetailBean.DataBean.CommentsdataBean  addBean=new reportDetailBean.DataBean.CommentsdataBean();
            Date d = new Date();
            SimpleDateFormat sbf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
           String time=sbf.format(d);
            addBean.setCreateTime(time);
            addBean.setContents(etDiscuss.getText().toString().trim());
            addcommentsdataBeanList.add(addBean);
            mCommentAdapter.addData(addcommentsdataBeanList);
            mCommentAdapter.notifyDataSetChanged();
            etDiscuss.setHint("写评论....");
            etDiscuss.setText("");
        }
    }
    private void bindAdapter() {
        switch (state) {
            case AppConstants.STATE_NORMAL:
                if (commentsdataBeanList != null && commentsdataBeanList.size() > 0) {
                    mCommentAdapter.clearData();
                    mCommentAdapter.addData(commentsdataBeanList);
                    mCommentAdapter.notifyDataSetChanged();
                }
                break;
            case AppConstants.STATE_REFREH://刷新
                mCommentAdapter.clearData();
                mCommentAdapter.addData(commentsdataBeanList);
                mCommentRecycle.scrollToPosition(0);
                mCommentAdapter.notifyDataSetChanged();
                mSflout.finishRefresh();
                break;
            case AppConstants.STATE_MORE:
                mCommentAdapter.addData(mCommentAdapter.getDatas().size(), commentsdataBeanList);
                mCommentRecycle.scrollToPosition(mCommentAdapter.getDatas().size());
                mSflout.finishLoadMore();
                break;
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

    @OnClick({R.id.tv_content_discuss,R.id.dialog_comment_bt})
    public void Onclick(View view){

        switch (view.getId()){
            case R.id.tv_content_discuss:
                etDiscuss.setHint("写评论....");
                comment(true);
                break;
            case R.id.dialog_comment_bt:
                if(TextUtils.isEmpty(etDiscuss.getText().toString().trim())){
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
                        ReportDetailActivity.this.getSystemService(INPUT_METHOD_SERVICE);
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
            RecommendLineReportBean  dataBean=dataList.get(position);
            Intent intent=new Intent(this, ReportDetailActivity.class);
            intent.putExtra("bean",dataBean.getId());
            startActivity(intent);

    }
}
