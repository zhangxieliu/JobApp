package com.fosu.jobapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;
import com.fosu.jobapp.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ConstellationAdapter extends BaseAdapter {

    private Context context;
    private List<String> data = new ArrayList<>();
    private Set<Integer> set = new HashSet<>(); //选中项的集合

    public void setCheckItem(Set<Integer> set) {
        if (set == null) {
            this.set.clear();
            this.set.add(0);
        } else
            this.set = set;
        notifyDataSetChanged();
    }

    public ConstellationAdapter(Context context) {
        this.context = context;
        set.add(0); // 默认选中第一项
    }

    public ConstellationAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
        set.add(0); // 默认选中第一项
    }

    public void setData(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_constellation_layout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        fillValue(position, viewHolder);
        return convertView;
    }

    private void fillValue(int position, ViewHolder holder) {
        holder.mText.setText(data.get(position));
        if (set.contains(position)) {
            holder.mText.setTextColor(context.getResources().getColor(R.color.white));
            holder.mText.setBackgroundResource(R.drawable.check_bg);
        } else {
            holder.mText.setTextColor(context.getResources().getColor(R.color.text_gray_color));
            holder.mText.setBackgroundResource(R.drawable.uncheck_bg);
        }
    }

    static class ViewHolder {
        @BindView(R.id.text)
        TextView mText;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
