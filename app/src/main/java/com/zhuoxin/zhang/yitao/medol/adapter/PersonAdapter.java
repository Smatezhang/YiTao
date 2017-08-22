package com.zhuoxin.zhang.yitao.medol.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhuoxin.zhang.yitao.R;
import com.zhuoxin.zhang.yitao.medol.entity.ItemShow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/21.
 */

public class PersonAdapter extends BaseAdapter {
    public List<ItemShow> mList;

    public PersonAdapter() {
        mList = new ArrayList<ItemShow>();
    }

    public void add(List<ItemShow> data) {
        mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      ViewHolder mViewHolder;
        if (convertView == null) {

            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_person_info, parent, false);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        }
        mViewHolder= (ViewHolder) convertView.getTag();
        ItemShow mItemShow = mList.get(position);
        mViewHolder.mTvPerson.setText(mItemShow.getItem_content());
        mViewHolder.mTvItemName.setText(mItemShow.getItem_title());


        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_item_name)
        TextView mTvItemName;
        @BindView(R.id.tv_person)
        TextView mTvPerson;
        @BindView(R.id.textView2)
        TextView mTextView2;
        View view;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
