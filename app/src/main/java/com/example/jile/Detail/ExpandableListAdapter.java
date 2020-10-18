package com.example.jile.Detail;
/*
 * Copyright (C) 2019 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jile.Bean.Bill;
import com.example.jile.R;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.widget.layout.ExpandableLayout;

import java.util.Collection;

/**
 * 可伸缩布局适配器
 *
 * @author xuexiang
 * @since 2019-11-22 15:38
 */
public class ExpandableListAdapter extends BaseRecyclerAdapter<LineElement> {

    private RecyclerView mRecyclerView;
    private Collection<LineElement> mData;
    public ExpandableListAdapter(RecyclerView recyclerView, Collection<LineElement> data) {
        super(data);
        mData = data;
        mRecyclerView = recyclerView;
    }

    /**
     * 适配的布局
     *
     * @param viewType
     * @return
     */
    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.adapter_expandable_list_item;
    }

    /**
     * 绑定数据
     *
     * @param holder
     * @param position 索引
     * @param item     列表项
     */
    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, LineElement item) {
        ExpandableLayout expandableLayout = holder.findViewById(R.id.expandable_layout);
        AppCompatImageView ivIndicator = holder.findViewById(R.id.iv_indicator);
        expandableLayout.setInterpolator(new OvershootInterpolator());
        expandableLayout.setOnExpansionChangedListener((expansion, state) -> {
            if (mRecyclerView != null && state == ExpandableLayout.State.EXPANDING) {
                mRecyclerView.smoothScrollToPosition(position);
            }
            if (ivIndicator != null) {
                ivIndicator.setRotation(expansion * 90);
            }
        });

        boolean isSelected = position == mSelectPosition;
        expandableLayout.setExpanded(isSelected, false);
        holder.select(R.id.fl_title, isSelected);
        holder.text(R.id.tvTitle, item.getTitle());
        holder.text(R.id.tvBalance, item.getBalance());
        LinearLayout ll = (LinearLayout) holder.findView(R.id.ll);
        for(View b:item.getBills()){
            if(b.getParent()!=null){
                ((ViewGroup) b.getParent()).removeView(b);
            }
            ll.addView(b);
        }
        holder.click(R.id.fl_title, v -> onClickItem(v, expandableLayout, position));
    }

    private void onClickItem(View view, final ExpandableLayout expandableLayout, final int position) {
        RecyclerViewHolder holder = (RecyclerViewHolder) mRecyclerView.findViewHolderForAdapterPosition(mSelectPosition);
        if (holder != null) {
            holder.select(R.id.fl_title, false);
            ((ExpandableLayout) holder.findViewById(R.id.expandable_layout)).collapse();
        }

        if (position == mSelectPosition) {
            mSelectPosition = -1;
        } else {
            view.setSelected(true);
            expandableLayout.expand();
            mSelectPosition = position;
        }
    }


}
