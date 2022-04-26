package com.wzx.tipcontent.fragment;

import android.os.Bundle;
import android.view.View;

import com.wzx.tipcontent.R;
import com.wzx.tipcontent.base.LazyLoadFragment;
import com.wzx.tipcontent.bean.MessageEvent;
import com.wzx.tipcontent.kit.AppConstants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ShoppFragment extends LazyLoadFragment {

    static ShoppFragment fragments;
    public static ShoppFragment newInstance(String type) {
        ShoppFragment fragment = new ShoppFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tabIndex", type);
        fragment.setArguments(bundle);
        return fragment;
    }
    public static ShoppFragment getInstance() {
        if (fragments == null) {
            synchronized (ShoppFragment.class) {
                fragments = new ShoppFragment();
            }
        }
        return fragments;
    }
    @Override
    protected int setContentView() {
        return R.layout.fragment_shopp;
    }

    @Override
    protected void finishCreateView(View view) {
        if (!EventBus.getDefault().isRegistered(this))
        {
            EventBus.getDefault().register(this);
        }

    }


    @Override
    protected void lazyLoad() {

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if(messageEvent.getMessage().contains("购物|")){
            String mes=messageEvent.getMessage();
//            keyword=mes.substring(mes.lastIndexOf("|")+1,mes.length());
//            page=1;
//            state = AppConstants.STATE_NORMAL;
//            if(getUserVisibleHint()==true){
//                initGetInfo();
//            }
        }

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
