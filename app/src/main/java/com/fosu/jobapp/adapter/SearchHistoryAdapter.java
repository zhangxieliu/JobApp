package com.fosu.jobapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fosu.jobapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/18.
 */

public class SearchHistoryAdapter extends BaseAdapter {
    private Context context;

    private List<String> data;

    public SearchHistoryAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    public List<String> getData() {
        return data;
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.layout_history_search_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        if (!TextUtils.isEmpty(data.get(position)))
            holder.tvHistory.setText(data.get(position));
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_history)
        TextView tvHistory;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
