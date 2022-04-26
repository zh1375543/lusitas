package com.wzx.tipcontent.utils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wzx.tipcontent.R;
import com.wzx.tipcontent.base.BaseActivity;


/**
 * 项目名:  for Android 功能: 自定义头部
 *
 */
public class Header implements OnClickListener{

	// 组成header的views的引用
	class Units {
		RelativeLayout header;
		ImageView mBack;
		TextView title;
		TextView right;
		 FrameLayout frame;
	}

	Units units = new Units();
	private BaseActivity a;

	public Header(BaseActivity a, String type) {
		this.a = a;
		setType(type);
	}


	// 设置标题
	public void setTitle(String title) {
		if (units.title != null)
			units.title.setText(title);
	}

	// 设置类型
	@SuppressLint("NewApi")
	public void setType(String type) {
		units.frame= (FrameLayout) a.findViewById(R.id.frag_main);
		units.header = units.frame.findViewById(R.id.ll_navheader);
		units.mBack=(ImageView)a.findViewById(R.id.header_back);
		units.title=(TextView)a.findViewById(R.id.header_title);
		units.right=(TextView)a.findViewById(R.id.header_right);
		if(type.equals("Vedio")){
			units.right.setVisibility(View.VISIBLE);
			units.right.setText("预警统计");
		}else if(type.equals("chooseCity")){
		}else if(type.equals("default")){
		}else if(type.equals("mine")){
		}else if(type.equals("login")){
			units.mBack.setVisibility(View.GONE);
			units.title.setVisibility(View.GONE);
		}else if(type.equals("showpic")){
			units.frame.setVisibility(View.GONE);
		}else if(type.equals("register")){
			units.mBack.setVisibility(View.GONE);
		}
		if (units.mBack != null)
			units.mBack.setOnClickListener(this);
		if (units.right != null)
			units.right.setOnClickListener(this);
	}








	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.header_back:
				a.finish();
				KeyboardUtils.hideSoftkeyboard(a);
				break;
			case R.id.header_right:
				onClickLitener.onRightClick(v);
				break;
		default:
			break;
		}
	}

	public interface OnClickLitener {
		void onRightClick(View view);
	}

	private OnClickLitener onClickLitener;

	public void  setOnClickLitener(OnClickLitener onClickLitener){

		this.onClickLitener=onClickLitener;
	}
}