package com.example.lennont.gankclientdemo.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.lennont.gankclientdemo.R;
import com.example.lennont.gankclientdemo.bean.Image;
import com.example.lennont.gankclientdemo.view.RadioImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by LennonT on 2016/7/7.
 */
public abstract class BenefitAdapter extends ArrayRecyleAdapter<Image, BenefitAdapter.ViewHolder> {

    private final Context context;
    private final LayoutInflater inflater;

    public BenefitAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(R.layout.benefit_item_layout, parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Image image = get(position);

        holder.imageView.setOriginalSize(image.getWidth(), image.getHeight());
        loadGoodsImage(holder, image);
        ViewCompat.setTransitionName(holder.imageView, image.getUrl());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public RadioImageView imageView;   //定宽不定高的ImageView

        public ViewHolder(@LayoutRes int resource, ViewGroup parent) {
            super(inflater.inflate(resource, parent, false));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick(v, getAdapterPosition());
                }
            });
        }

    }

    protected abstract void onItemClick(View v, int adapterPosition);

    private void loadGoodsImage(ViewHolder holder, Image imgGoods) {
        if (null == imgGoods || TextUtils.isEmpty(imgGoods.getUrl())) {
            Glide.with(context)
                    .load(R.drawable.item_default_img)
                    .into(holder.imageView);
        } else {
            Glide.with(context)
                    .load(imgGoods.getUrl())
                    .into(holder.imageView);
        }
    }

}
