package com.wzx.tipcontent.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.wzx.tipcontent.R;
import com.wzx.tipcontent.adapter.FragmentPushAdapter;
import com.wzx.tipcontent.base.BaseActivity;
import com.wzx.tipcontent.bean.MessageEvent;
import com.wzx.tipcontent.fragment.ShoppFragment;
import com.wzx.tipcontent.utils.Header;
import com.wzx.tipcontent.utils.KeyboardUtils;
import com.wzx.tipcontent.weight.ClearEditText;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class ShoppActivity extends BaseActivity {

    @InjectView(R.id.tablaout)
    SlidingTabLayout Tablaout;
    @InjectView(R.id.pager)
    ViewPager viewPager;
    @InjectView(R.id.et_search_shopp)
    ClearEditText mSearch;

    FragmentPushAdapter adapter;
    List<Fragment> mFragmentList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopp);
        initView();
        searchOnclick();
    }

    private void initView() {
        header = new Header(this, "");
        header.setTitle("购物精选");
        String[] title={"为您推荐","有奖征文","i拍","关注"};
        mFragmentList.add(ShoppFragment.newInstance("1"));
        mFragmentList.add(ShoppFragment.newInstance("2"));
        mFragmentList.add(ShoppFragment.newInstance("3"));
        mFragmentList.add(ShoppFragment.newInstance("4"));
        adapter=new FragmentPushAdapter(title,getSupportFragmentManager(),mFragmentList);
        viewPager.setAdapter(adapter);
        Tablaout.setViewPager(viewPager);
    }

    private void searchOnclick() {
        mSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    //先隐藏键盘
                    KeyboardUtils.forcedhideSoftkeyboard(ShoppActivity.this, mSearch);
                    int tab=adapter.getCurrentFragment();
                    EventBus.getDefault().post(new MessageEvent("购物|"+tab+"|"+mSearch.getText().toString().trim()));
                    mSearch.setText("");
                }
                //记得返回false
                return false;
            }
        });
    }
}
