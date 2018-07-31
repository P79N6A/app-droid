package com.runtoinfo.youxiao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.runtoinfo.youxiao.R;
import com.runtoinfo.youxiao.entity.BoutiqueRecycler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/7/26 0026.
 */

public class BoutiqueInChildRecyclerAdapter extends RecyclerView.Adapter {

    public Context context;
    public LayoutInflater inflater;
    public List<BoutiqueRecycler> recyclerList = new ArrayList<>();
    public OnItemClickListener onItemClickListener;

    public BoutiqueInChildRecyclerAdapter(Context context, List<BoutiqueRecycler> recyclerList){
        this.context = context;
        this.recyclerList = recyclerList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater =LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.boutique_in_child_recycler_item, parent,false);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        BoutiqueRecycler recycler = recyclerList.get(position);
        viewHolder.imageView.setBackground(recycler.getDrawable());
        viewHolder.title.setText(recycler.getTitle());
        viewHolder.time.setText(recycler.getTime());
        viewHolder.price.setText(recycler.getPrice());
        viewHolder.number.setText(recycler.getNumber());

    }

    @Override
    public int getItemCount() {
        return recyclerList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, OnItemClickListener{

        public ImageView imageView;
        public TextView title, time, price, number;
        public OnItemClickListener listener;
        public ViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            this.listener = listener;
            imageView = itemView.findViewById(R.id.in_child_recycler_imageView);
            title = itemView.findViewById(R.id.boutique_recycler_title);
            price = itemView.findViewById(R.id.boutique_recycler_price);
            time = itemView.findViewById(R.id.boutique_recycler_time);
            number = itemView.findViewById(R.id.boutique_recycler_number);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //ARouter.getInstance().build("/course/boutiqueCourseDetails").navigation();
            listener.onItemClick(v, getAdapterPosition());
        }



        @Override
        public void onItemClick(View view, int position) {
            switch (position){
                case 0:
                    ARouter.getInstance().build("/course/boutiqueCourseDetails").navigation();
                    break;
                case 1:
                    ARouter.getInstance().build("/electronic/electronicScore").navigation();
                    break;
                default:
                    ARouter.getInstance().build("/course/boutiqueCourseDetails").navigation();
                    break;
            }
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }
}
