package com.fosu.jobapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fosu.jobapp.R;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/8.
 */

public class CompanyAdapter extends SwipeMenuAdapter<CompanyAdapter.Holder> {
    private Context context;
    private List<Object> mDatas = new ArrayList<>(10);

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private View mHeaderView;
    private OnItemClickListener mListener;

    public CompanyAdapter(Context context, List<Object> datas) {
        this.context = context;
//        mDatas = datas;
        for (int i= 0; i< 10; i++) {
            mDatas.add(i);
        }
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public void addDatas(ArrayList<String> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener li) {
        mListener = li;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_company_detail, null);
        return view;
    }

    @Override
    public Holder onCompatCreateViewHolder(View realContentView, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER)
            return new Holder(mHeaderView);
        return new Holder(realContentView);
    }

    @Override
    public void onBindViewHolder(CompanyAdapter.Holder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER)
            return;
        final int pos = getRealPosition(holder);
        final Object data = mDatas.get(pos);
        if(holder instanceof Holder) {
//            ((Holder) holder).text.setText(data);
            if(mListener == null) return;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(pos, data);
                }
            });
        }
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? mDatas.size() : mDatas.size() + 1;
    }

    class Holder extends RecyclerView.ViewHolder {

        public Holder(View itemView) {
            super(itemView);
            if (itemView == mHeaderView) return;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int postion, Object object);
    }
}
