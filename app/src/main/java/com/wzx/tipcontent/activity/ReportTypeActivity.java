package com.wzx.tipcontent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
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
import com.wzx.tipcontent.adapter.workAdapter;
import com.wzx.tipcontent.base.BaseActivity;
import com.wzx.tipcontent.bean.workBean;
import com.wzx.tipcontent.custom.RecycleViewDivider;
import com.wzx.tipcontent.custom.ServerRequest;
import com.wzx.tipcontent.kit.AppConstants;
import com.wzx.tipcontent.utils.Header;
import com.wzx.tipcontent.utils.KeyboardUtils;
import com.wzx.tipcontent.utils.LocalJsonResolutionUtils;
import com.wzx.tipcontent.utils.Tool;
import com.wzx.tipcontent.weight.ClearEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.InjectView;

public class ReportTypeActivity extends BaseActivity implements ServerRequest.InterfaceCode,OnItemClickLitener {
    @InjectView(R.id.swipeLaoutType)
    SmartRefreshLayout mSflout;
    @InjectView(R.id.rv_list_type)
    RecyclerView mRecycle;

    @InjectView(R.id.et_search)
    ClearEditText mSearch;
    @InjectView(R.id.tv_net_tips)
    TextView mTips;
    @InjectView(R.id.no_net_laout)
    LinearLayout mNetLaout;
    @InjectView(R.id.img_net_tips)
    ImageView mImage;
    @InjectView(R.id.reload)
    ImageButton mLoadData;
    private int state;
    private int page=1;
    private int classifyId=0;
    private String keyword="";
    private List<workBean.DataBean> dataList=new ArrayList<>();
    private workAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_type);
        header=new Header(this,"");
        header.setTitle("全部");
        classifyId=getIntent().getIntExtra("classifyId",0);
        if(classifyId==0){
            header.setTitle("全部");
        }else if(classifyId==1){
            header.setTitle("电商");
        }else if(classifyId==2){
            header.setTitle("银行");
        }
        initView();
    }

    private void initView() {
        RecycleViewDivider viewDivider = new RecycleViewDivider(this, DividerItemDecoration.VERTICAL);
        viewDivider.setDrawable(ContextCompat.getDrawable(this, R.drawable.shape_line));
        mRecycle.addItemDecoration(viewDivider);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecycle.setLayoutManager(manager);
        adapter=new workAdapter(this,dataList);
        adapter.setOnItemClickLitener(this);
        mRecycle.setAdapter(adapter);
        initGetInfo();
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
        mLoadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state = AppConstants.STATE_NORMAL;
                initGetInfo();
            }
        });
        mSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    //先隐藏键盘
                    KeyboardUtils.forcedhideSoftkeyboard(ReportTypeActivity.this, mSearch);
                    //自己需要的操作
                    page = 1;
                    state = AppConstants.STATE_NORMAL;
                    keyword=mSearch.getText().toString().trim();
                    initGetInfo();
                    keyword="";
                    mSearch.setText("");
                }
                //记得返回false
                return false;
            }
        });
    }

    private void initGetInfo() {
        showDialogs();
        String url = "http://139.224.110.190:91/RoustaVI/GetLineReport";
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("pageIndex", page+"");
        hashMap.put("classifyId",classifyId+"");
        hashMap.put("keyword",keyword);
        ServerRequest request = new ServerRequest(url, Tool.setRequestParams(hashMap));
        request.requestLoginReturn();
        request.setInterfaceCode(this);
    }

    @Override
    public void Success(String jsonData) {
        closeProgressDialog();
        workBean workBean= LocalJsonResolutionUtils.JsonToObject(jsonData, workBean.class);
        dataList=workBean.getData();
        bindAdapter();
    }
    private void bindAdapter() {
        switch (state) {
            case AppConstants.STATE_NORMAL:
                if (dataList != null && dataList.size() > 0) {
                    mSflout.setVisibility(View.VISIBLE);
                    mNetLaout.setVisibility(View.GONE);
                    adapter.clearData();
                    adapter.addData(dataList);
                    adapter.notifyDataSetChanged();
                }else{
                    mSflout.setVisibility(View.GONE);
                    mNetLaout.setVisibility(View.VISIBLE);
                    Glide.with(this)
                            .load(R.mipmap.no_attention_img)
                            .override(200,200)
                            .into(mImage);
                    mLoadData.setVisibility(View.GONE);
                    mTips.setText("暂无信息~");
                }
                break;
            case AppConstants.STATE_REFREH://刷新
                adapter.clearData();
                adapter.addData(dataList);
                mRecycle.scrollToPosition(0);
                adapter.notifyDataSetChanged();
                mSflout.finishRefresh();
                break;
            case AppConstants.STATE_MORE:
                adapter.addData(adapter.getDatas().size(), dataList);
                mRecycle.scrollToPosition(adapter.getDatas().size());
                mSflout.finishLoadMore();
                break;
        }
    }
    @Override
    public void Fail(String failContent) {
        closeProgressDialog();
        if(failContent.contains(AppConstants.requestStr)){
            mSflout.setVisibility(View.GONE);
            mNetLaout.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(R.mipmap.no_net_img)
                    .override(200,200)
                    .into(mImage);
            mTips.setText("加载失败,网络不给力");
        }else {
            try {
                JSONObject jsonObject = new JSONObject(failContent);
                Tool.showToast(this,jsonObject.optString("msg"),3000);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        workBean.DataBean  dataBean=dataList.get(position);
        Intent intent=new Intent(this, ReportDetailActivity.class);
        intent.putExtra("bean",dataBean.getId());
        startActivity(intent);
    }
}
