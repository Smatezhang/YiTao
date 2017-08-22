package com.zhuoxin.zhang.yitao.medol.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zhuoxin.zhang.yitao.R;
import com.zhuoxin.zhang.yitao.medol.entity.ImageItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/22.
 */

public class GoodsUploadAdapter extends RecyclerView.Adapter {

    private List<ImageItem> mList;

    private enum ITEMTYPE {
        NORMAL,
        ADD
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mList.size()) {
            return ITEMTYPE.ADD.ordinal();
        } else {
            return ITEMTYPE.NORMAL.ordinal();
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView;
        if (viewType == ITEMTYPE.ADD.ordinal()) {
            mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_recyclerviewlast, null);
            return new LastViewHolder(mView);
        } else {
            mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_recyclerview, null);
            return new LastViewHolder(mView);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LastViewHolder){
            if (position == 8){
                ((LastViewHolder) holder).mIbRecycleAdd.setVisibility(View.GONE);
            }else {
                ((LastViewHolder) holder).mIbRecycleAdd.setVisibility(View.VISIBLE);
                ((LastViewHolder) holder).mIbRecycleAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener !=null){
                            listener.onAddClicked();
                        }
                    }
                });
            }
        }else if (holder instanceof ViewHolder){
            /*ImageItem mImageItem = mList.get(position);
            ((ViewHolder) holder).mBg.setImageBitmap(mImageItem.getBitmap());
            */

        }
    }

    @Override
    public int getItemCount() {
        return mList.size() <8 ? mList.size() + 1 : 8;
    }



    static class  LastViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ib_recycle_add)
        ImageButton mIbRecycleAdd;

        LastViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }



    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_photo)
        ImageView mIvPhoto;
        @BindView(R.id.bg)
        ImageView mBg;
        @BindView(R.id.cb_check_photo)
        CheckBox mCbCheckPhoto;
        @BindView(R.id.layout_photo)
        RelativeLayout mLayoutPhoto;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    //item点击事件 (接口回调)
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        //点击加号图片回调
        void onAddClicked();
        //点击商品图片回调
        void onPhotoClicked(ImageItem imageItem);
        //商品图片长按回调
        void onLongClicked();
    }
    //设置监听器
    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }



}
