package com.example.administrator.gongda.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.gongda.R;
import com.example.administrator.gongda.bean.newsBean;
import com.example.administrator.gongda.ui.activity.Web;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/27.
 */

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ContentViewHolder> {
    List<newsBean> newsList=new ArrayList<>();
    private Context context;

    public NewsListAdapter(Context context,List<newsBean> list) {
        this.context = context;
        this.newsList=list;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news,parent,false));
    }

    @Override
    public void onBindViewHolder(ContentViewHolder holder, int position) {
                   newsBean temp=newsList.get(position);
        if(temp==null)
            return;
        else{
            setInfo(holder,temp);
        }
    }

    private void setInfo(ContentViewHolder holder, final newsBean temp) {
        holder.title.setText(temp.getTitle());
        holder.time.setText(temp.getTime());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url=temp.getUrl();
                Intent intent=new Intent(v.getContext(), Web.class);
                intent.putExtra("url",url);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }
    public void upData(List<newsBean> lits){
        this.newsList=lits;
        notifyDataSetChanged();
    }

    class ContentViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_title)
        TextView title;
        @Bind(R.id.newcard)
        CardView cardView;
        @Bind(R.id.tv_time)
        TextView time;
        public ContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
