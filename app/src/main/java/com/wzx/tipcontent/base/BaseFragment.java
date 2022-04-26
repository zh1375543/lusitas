package com.wzx.tipcontent.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.wzx.tipcontent.weight.MyProgressDialog;


/**
 * Created by Quill on
 */
public class BaseFragment extends Fragment {
    @Nullable
    private MyProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    public void showDialogs() {
        progressDialog = MyProgressDialog.createDialog(getActivity());
        progressDialog.setMessage("");
        progressDialog.show();
    }


    public void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
    public void openActivity(Class clazz){
        getActivity().startActivity(new Intent(getActivity(),clazz));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //反注册
    }
}
