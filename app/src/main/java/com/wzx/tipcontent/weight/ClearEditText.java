package com.wzx.tipcontent.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

import com.wzx.tipcontent.R;


public class ClearEditText extends EditText implements View.OnFocusChangeListener, TextWatcher{

    private Drawable mClearDrawable;
    private boolean hasFocus; //控件是否有焦点

    public ClearEditText(Context context) {
        super(context);
        init(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attr) {
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
            mClearDrawable = getResources().getDrawable(R.drawable.selector_delete);//删除内容的图标
        }
        //drawable.setBounds ：设置Drawable的边界，必须要有
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        this.hasFocus = b;
        if (hasFocus) {
            //焦点存在，而且有输入值，显示右侧删除图标
            setDrawableRightVisible(getText().length() > 0);
        } else {
            //没有焦点，隐藏删除图标
            setDrawableRightVisible(false);
            clearFocus();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                //根据触摸的位置判断是否点击 右侧图标
                boolean isTouchRight = (event.getX() > (getWidth() - getTotalPaddingRight())) &&
                        (event.getX() < (getWidth() - getPaddingRight()));
                //LogUtil.d("isTouchRight： " + isTouchRight);
                if (isTouchRight) {
                    setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }

    private void setDrawableRightVisible(boolean visible) {
        Drawable drawableRight = visible ? mClearDrawable : null;
        //getCompoundDrawables()可以获得一个{DrawableLeft, DrawableTop, DrawableRiht, DrawableBottom}的数组。
        //getCompoundDrawables()[2]表示获取EditText的DrawableRight
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], drawableRight, getCompoundDrawables()[3]);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (mOnTextChangedListener != null) {
            mOnTextChangedListener.beforeTextChanged(charSequence, i, i1, i2);
        }
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (hasFocus) {
            setDrawableRightVisible(charSequence.length() > 0);
        }
        if (mOnTextChangedListener != null) {
            mOnTextChangedListener.onTextChanged(charSequence, i, i1, i2);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (mOnTextChangedListener != null) {
            mOnTextChangedListener.afterTextChanged(editable);
        }
    }

    //文本改变接口监听
    public interface OnTextChangedListener {
        void beforeTextChanged(CharSequence s, int start, int count, int after);

        void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter);

        void afterTextChanged(Editable s);
    }

    private OnTextChangedListener mOnTextChangedListener;

    public void setOnTextChangedListener(OnTextChangedListener onTextChangedListener) {
        this.mOnTextChangedListener = onTextChangedListener;
    }
}
