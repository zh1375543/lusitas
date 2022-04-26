package com.wzx.tipcontent.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.loopj.android.http.RequestParams;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.stx.xhb.xbanner.XBanner;
import com.wzx.tipcontent.R;
import com.wzx.tipcontent.activity.ReportDetailActivity;
import com.wzx.tipcontent.activity.ReportTypeActivity;
import com.wzx.tipcontent.activity.ShoppActivity;
import com.wzx.tipcontent.adapter.onclicklistener.OnItemClickLitener;
import com.wzx.tipcontent.adapter.workAdapter;
import com.wzx.tipcontent.base.BaseFragment;
import com.wzx.tipcontent.bean.imageDateBean;
import com.wzx.tipcontent.bean.workBean;
import com.wzx.tipcontent.custom.RecycleViewDivider;
import com.wzx.tipcontent.custom.ServerRequest;
import com.wzx.tipcontent.kit.AppConstants;
import com.wzx.tipcontent.utils.LocalJsonResolutionUtils;
import com.wzx.tipcontent.utils.Tool;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;

/**
 * on 2021/3/3
 *
 * @Author zhanghui
 * @Description  首页
 */
public class workFragment extends BaseFragment implements ServerRequest.InterfaceCode, OnItemClickLitener {

    private int page=1;
    private int classifyId=0;
    private String keyword="";
    private List<imageDateBean> beanList;
    private List<workBean.DataBean> dataList=new ArrayList<>();
    @InjectView(R.id.banner)
    XBanner mBanner;
    @InjectView(R.id.swipeLaout)
    SmartRefreshLayout mSflout;
    @InjectView(R.id.rv_list)
    RecyclerView mRecycle;
    @InjectView(R.id.tv_all)
    TextView mAll;
    @InjectView(R.id.tv_online)
    TextView mOnline;
    @InjectView(R.id.tv_pany)
    TextView mPany;
    @InjectView(R.id.tv_shopp)
    TextView mShopp;
    workAdapter adapter;
    private int state;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work, container, false);
        ButterKnife.inject(this, view);
        initView();

        return view;
    }

    private void initView() {
        beanList=new ArrayList<>();
        beanList.add(new imageDateBean("https://file.alapi.cn/image/comic/215610-154116697054cd.jpg"));
        beanList.add(new imageDateBean("https://file.alapi.cn/image/comic/215610-154116697054cd.jpg"));
        beanList.add(new imageDateBean("https://file.alapi.cn/image/comic/215610-154116697054cd.jpg"));

        mBanner.setBannerData(beanList);
        //设置广告图片点击事件
        mBanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                initGetInfo();
            }
        });
        //加载广告图片
        mBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Glide.with(getActivity()).load(beanList.get(position).getUrl()).placeholder(R.drawable.default_image).error(R.drawable.default_image).into((ImageView) view);
            }
        });
        RecycleViewDivider viewDivider = new RecycleViewDivider(getActivity(), DividerItemDecoration.VERTICAL);
        viewDivider.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.shape_line));
        mRecycle.addItemDecoration(viewDivider);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecycle.setLayoutManager(manager);
        adapter=new workAdapter(getActivity(),dataList);
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

    }

    @OnClick({R.id.tv_all,R.id.tv_online,R.id.tv_pany,R.id.tv_shopp})
    public void Onclick(View view){
        switch (view.getId()){
            case  R.id.tv_all:
                IntentDate(0);
                break;
            case  R.id.tv_online:
                IntentDate(1);
                break;
            case  R.id.tv_pany:
                IntentDate(2);
                break;
            case  R.id.tv_shopp:
                Intent intent=new Intent(getActivity(), ShoppActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void IntentDate(int index){
        Intent intent=new Intent(getActivity(), ReportTypeActivity.class);
        intent.putExtra("classifyId",index);
        startActivity(intent);
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

    @Override
    public void Fail(String failContent) {
        closeProgressDialog();
        if(failContent.contains(AppConstants.requestStr)){
            Tool.showToast(getActivity(),"网络不给力,请重试",3000);
        }else {
            try {
                JSONObject jsonObject = new JSONObject(failContent);
                Tool.showToast(getActivity(),jsonObject.optString("msg"),3000);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private void bindAdapter() {
        switch (state) {
            case AppConstants.STATE_NORMAL:
                if (dataList != null && dataList.size() > 0) {
                    adapter.clearData();
                    adapter.addData(dataList);
                    adapter.notifyDataSetChanged();
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
    public void onItemClick(View view, int position) {
        workBean.DataBean  dataBean=dataList.get(position);
        Intent intent=new Intent(getActivity(), ReportDetailActivity.class);
        intent.putExtra("bean",dataBean.getId());
        startActivity(intent);
    }
}
