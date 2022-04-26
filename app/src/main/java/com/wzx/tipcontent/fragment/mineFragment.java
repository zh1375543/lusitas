package com.wzx.tipcontent.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.fragment.app.Fragment;

import com.wzx.tipcontent.R;


/**
 * on 2021/3/3
 *
 * @Author zhanghui
 * @Description 我的
 */
public class mineFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mine, container, false);

        return view;
    }

}
