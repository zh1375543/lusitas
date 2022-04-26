package com.wzx.tipcontent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wzx.tipcontent.R;
import com.wzx.tipcontent.adapter.onclicklistener.OnItemClickLitener;
import com.wzx.tipcontent.bean.reportDetailBean.DataBean.RecommendLineReportBean;
import com.wzx.tipcontent.utils.Tool;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class report_recommend_Adapter extends RecyclerView.Adapter{

    Context context;
    List<RecommendLineReportBean> listData;
    private OnItemClickLitener onItemClickLitener;
    public void setOnItemClickLitener(OnItemClickLitener onItemClickLitener){
        this.onItemClickLitener= onItemClickLitener;
    }
    public report_recommend_Adapter(Context context, List<RecommendLineReportBean> listData) {
        this.context = context;
        this.listData = listData;

    }
    public List<RecommendLineReportBean> getDatas() {
        return listData;
    }
    public void clearData(){
        listData.clear();
    }
    public void addData(List<RecommendLineReportBean> datas){

        addData(0,datas);
    }

    public void addData(int position,List<RecommendLineReportBean> datas){

        if(datas !=null && datas.size()>0) {
            listData.addAll(datas);
        }else {
            Tool.showToast(context, "没有更多数据", 3000);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.report_detail_item,viewGroup,false);
        return new VedioHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {
        VedioHolder holder= (VedioHolder) viewHolder;
        RecommendLineReportBean bean=listData.get(i);
        holder.mRecommend.setText(bean.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickLitener.onItemClick(v,i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData==null?0:listData.size();
    }

    public class VedioHolder extends RecyclerView.ViewHolder{


        @InjectView(R.id.tv_item_recommend)
        TextView mRecommend;
        public VedioHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.inject(this,itemView);
        }
    }
}
