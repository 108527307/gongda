package com.example.administrator.gongda.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.gongda.R;
import com.example.administrator.gongda.bean.gpis;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/25.
 */
public class gradeListAdapter extends RecyclerView.Adapter<gradeListAdapter.gradeItemHolder> {
    private List<gpis> data=new ArrayList<>();

    private Context context;
    public gradeListAdapter(Context context,List<gpis> list){
        this.context=context;
        data=list;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public gradeItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new gradeItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.grade_item,parent,false));
    }

    @Override
    public void onBindViewHolder(gradeItemHolder holder, int position) {
                gpis temp=data.get(position);
        if(temp==null)
            return;
        else{
            setInfo(holder,temp);
        }

    }

    private void setInfo(gradeItemHolder holder, gpis temp) {
        holder.className.setText(temp.getKechenmignchen());
        holder.kechenleixin.setText(temp.getKechenleixin());
        holder.xuefen.setText(temp.getShijian()+"分");
        holder.fenshu.setText(temp.getChenji());
          try{
              int cj=Integer.parseInt(temp.getChenji());
              switch (cj/10){
                  case 9: holder.fenshu.setTextColor(context.getResources().getColor(R.color.golden));
                      break;
                  case 8:
                      holder.fenshu.setTextColor(context.getResources().getColor(R.color.purple));
                      break;
                  case 7:
                      holder.fenshu.setTextColor(context.getResources().getColor(R.color.blue));
                      break;
                  case 6:
                      holder.fenshu.setTextColor(context.getResources().getColor(R.color.green));
                      break;
                  case 5:
                  case 4:
                  case 3:
                  case 2:
                  case 1:
                  case 0:
                      holder.fenshu.setTextColor(context.getResources().getColor(R.color.black));
                      break;
              }
          }catch (NumberFormatException e){
              if(temp.getChenji().equals("优秀"))
                  holder.fenshu.setTextColor(context.getResources().getColor(R.color.golden));
              else
                  holder.fenshu.setTextColor(context.getResources().getColor(R.color.purple));
              return;
          }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
public void UpData(List<gpis> list){
    this.data=list;
    notifyDataSetChanged();
}
    class gradeItemHolder extends  RecyclerView.ViewHolder{
          @Bind(R.id.className)
        TextView className;
        @Bind(R.id.kechenleixin)
        TextView kechenleixin;
        @Bind(R.id.xuefen)
        TextView xuefen;
        @Bind(R.id.fenshu)
        TextView fenshu;

        public gradeItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
