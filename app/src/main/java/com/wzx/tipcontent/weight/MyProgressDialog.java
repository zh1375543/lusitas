package com.wzx.tipcontent.weight;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wzx.tipcontent.R;


/**
 *  Android 自定义的ProgressDialog
 * 
 * @author 
 * 
 */
public class MyProgressDialog extends Dialog {
	private Context context = null;
	private static MyProgressDialog customProgressDialog = null;

	public MyProgressDialog(Context context) {
		super(context);
		this.context = context;
	}

	public MyProgressDialog(Context context, int theme) {
		super(context, theme);
	}

	public static MyProgressDialog createDialog(Context context) {
		customProgressDialog = new MyProgressDialog(context, R.style.CustomProgressDialog);
		customProgressDialog.setContentView(R.layout.customprogressdialog);
		customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		return customProgressDialog;
	}
	
	
	public static MyProgressDialog createDialog(Context context,String str) {
		customProgressDialog = new MyProgressDialog(context, R.style.CustomProgressDialog);
		customProgressDialog.setContentView(R.layout.customprogressdialog);
		customProgressDialog.setMessage(str);
		customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		return customProgressDialog;
	}


	public void onWindowFocusChanged(boolean hasFocus) {
		if (customProgressDialog == null) {
			return;
		}
		ImageView imageView = (ImageView) customProgressDialog.findViewById(R.id.loadingImageView);
		AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
		animationDrawable.start();
	}

	/**
	 * setTitle����
	 * 
	 * @param strTitle
	 * @return
	 */
	public MyProgressDialog setTitle(String strTitle) {
		return customProgressDialog;
	}

	/**
	 * setMessage ��ʾ����
	 * 
	 * @param strMessage
	 * @return
	 */
	public MyProgressDialog setMessage(String strMessage) {
		
		TextView tvMsg = (TextView) customProgressDialog.findViewById(R.id.id_tv_loadingmsg);
		tvMsg.setVisibility(View.GONE);
		if (!strMessage.equals("")) {
			tvMsg.setVisibility(View.VISIBLE);
			tvMsg.setText(strMessage);
		}
		return customProgressDialog;
	}
}
