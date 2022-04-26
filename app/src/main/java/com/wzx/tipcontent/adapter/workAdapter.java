package com.wzx.tipcontent.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wzx.tipcontent.R;
import com.wzx.tipcontent.adapter.onclicklistener.OnItemClickLitener;
import com.wzx.tipcontent.bean.workBean;
import com.wzx.tipcontent.utils.Tool;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class workAdapter extends RecyclerView.Adapter{

    Context context;
    List<workBean.DataBean> listData;
    private OnItemClickLitener onItemClickLitener;
    public void setOnItemClickLitener(OnItemClickLitener onItemClickLitener){
        this.onItemClickLitener= onItemClickLitener;
    }
    public workAdapter(Context context, List<workBean.DataBean> listData) {
        this.context = context;
        this.listData = listData;

    }
    public List<workBean.DataBean> getDatas() {
        return listData;
    }
    public void clearData(){
        listData.clear();
    }
    public void addData(List<workBean.DataBean> datas){

        addData(0,datas);
    }

    public void addData(int position,List<workBean.DataBean> datas){

        if(datas !=null && datas.size()>0) {
            listData.addAll(datas);
        }else {
            Tool.showToast(context, "没有更多数据", 3000);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.work_item,viewGroup,false);
        return new VedioHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {
        VedioHolder holder= (VedioHolder) viewHolder;
        workBean.DataBean bean=listData.get(i);
        holder.mTitle.setText(bean.getTitle());
        holder.mType.setText(bean.getClassIfyName());
        holder.mComment.setText(bean.getCommentCount()+"评");
        holder.mTime.setText(bean.getCreateTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickLitener.onItemClick(v,i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class VedioHolder extends RecyclerView.ViewHolder{


        @InjectView(R.id.tv_title)
        TextView mTitle;
        @InjectView(R.id.tv_type)
        TextView mType;
        @InjectView(R.id.tv_comment)
        TextView mComment;
        @InjectView(R.id.tv_time)
        TextView mTime;
        public VedioHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.inject(this,itemView);
        }
    }
}
