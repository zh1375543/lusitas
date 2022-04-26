package com.wzx.tipcontent.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.wzx.tipcontent.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener{

    @InjectView(R.id.vp_main)
    ViewPager mViewPager;

    @InjectView(R.id.rg_main)
    RadioGroup mRadioGroup;
    @InjectView(R.id.rb_main_work)
    RadioButton rbWrok;
    @InjectView(R.id.rb_main_mine)
    RadioButton rbMine;
    private boolean isExit = false;
    private workFragment workFragment=null;
    private  mineFragment mineFragment=null;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mains);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        mRadioGroup.setOnCheckedChangeListener(this);
        ContentAdapter adapter = new ContentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setCurrentItem(0);


    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int current = -1;
        switch (checkedId) {
            case R.id.rb_main_work:
                current = 0;
                break;
            case R.id.rb_main_mine:
                current = 1;
                break;
        }
        mViewPager.setCurrentItem(current, false);
    }



    class ContentAdapter extends FragmentPagerAdapter {

        public ContentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    if (workFragment == null) {
                        workFragment = new workFragment(); //工作台
                    }
                    fragment = workFragment;

                    break;
                case 1:
                    if (mineFragment == null) {
                        mineFragment = new mineFragment();//我的
                    }
                    fragment = mineFragment;
                    break;


            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                    ToQuitTheApp();
                    return true;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
    // 封装ToQuitTheApp方法
    public void ToQuitTheApp() {
        if (isExit) {

            // 存储退出信息
            isExit = false;
            finish();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            System.exit(0);

        } else {
            isExit = true;
            Toast.makeText(this, "再按一次退出APP", Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(0, 3000);// 3秒后发送消息
        }
    }

    // 创建Handler对象，用来处理消息
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {// 处理消息
            super.handleMessage(msg);
            isExit = false;
        }
    };


}
