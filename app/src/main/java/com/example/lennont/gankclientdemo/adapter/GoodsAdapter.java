package com.example.lennont.gankclientdemo.adapter;

import android.content.Context;
import android.print.PrintAttributes;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lennont.gankclientdemo.R;
import com.example.lennont.gankclientdemo.bean.Goods;
import com.example.lennont.gankclientdemo.bean.Image;
import com.example.lennont.gankclientdemo.data.ImageGoodsCache;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by LennonT on 2016/7/7.
 */
public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder> {

    //region Field

    private Context context;

    private List<Goods> mGoodsArray;

    //endregion

    //region ctor

    public GoodsAdapter(Context context) {
        this.context = context;

        mGoodsArray = new ArrayList<>();
    }

    //endregion

    //region Override

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.goods_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Goods goods = mGoodsArray.get(position);
        Image image = ImageGoodsCache.getInstance().getRandomImage(position);

        loadImageSource(holder, image);

        holder.goodsAuthor.setText(goods.getWho());
        holder.goodsDesc.setText(goods.getDesc());
        holder.goodsDate.setText("拍摄于" + goods.getPublishedAt());

        holder.goodsLike.setImageResource(goods.isUsed() ? R.drawable.ic_heart_red
                                                         : R.drawable.ic_heart_outline_grey);

        holder.rootView.setTag(position);
        holder.goodsLike.setTag(holder);
    }

    @Override
    public int getItemCount() {
        return mGoodsArray.size();
    }

    //endregion

    //region Inner Class

    public class ViewHolder extends RecyclerView.ViewHolder {

        //region Field

        @Bind(R.id.goods_background)
        ImageView goodsBackground;
        @Bind(R.id.goods_desc)
        TextView goodsDesc;
        @Bind(R.id.goods_author)
        TextView goodsAuthor;
        @Bind(R.id.goods_like)
        ImageView goodsLike;
        @Bind(R.id.goods_date)
        TextView goodsDate;

        private View rootView;   //代表CardView

        //endregion

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            rootView = itemView;
        }
    }

    //endregion

    //region Public

    public void updateGoodsData(ArrayList<Goods> goodses) {
        if(goodses != null && goodses.size() > 0) {
            mGoodsArray.clear();
            mGoodsArray.addAll(goodses);

            notifyDataSetChanged();  //通知整个集合数据已改变
        }
    }

    //endregion

    //region Private

    //加载实际照片
    private void loadImageSource(ViewHolder holder, Image image) {
        if(image == null || TextUtils.isEmpty(image.getUrl())) {  //无法获取有效的照片
            Glide.with(context)
                    .load(R.drawable.item_default_img)
                    .centerCrop()
                    .into(holder.goodsBackground);
        } else {
            Glide.with(context)
                    .load(image.getUrl())
                    .centerCrop()
                    .into(holder.goodsBackground);
        }
    }


    //endregion



}
