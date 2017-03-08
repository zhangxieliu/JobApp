package com.fosu.jobapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fosu.jobapp.R;
import com.fosu.jobapp.bean.Company;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/8.
 * 公司列表的Adapter
 */

public class CompanyAdapter extends SwipeMenuAdapter<CompanyAdapter.Holder> {
    private Context context;
    private List<Company> companies = new ArrayList<>();

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private View mHeaderView;
    private OnItemClickListener mListener;

    public CompanyAdapter(Context context) {
        this.context = context;
    }

    public CompanyAdapter(Context context, List<Company> companies) {
        this.context = context;
        this.companies = companies;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
        notifyDataSetChanged();
    }

    /**
     * 设置RecyclerView的头部View
     * @param headerView 添加的头部View
     */
    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    /**
     * 得到RecyclerView的头部View
     * @return RecyclerView的头部View
     */
    public View getHeaderView() {
        return mHeaderView;
    }

    /**
     * 添加公司信息在原有集合中，并通知Adapter更新数据
     * @param companies 添加的公司信息
     */
    public void addDatas(List<Company> companies) {
        this.companies.addAll(companies);
        notifyDataSetChanged();
    }

    /**
     * 设置列表项点击监听
     * @param li 需要实现的接口回调
     */
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
        View view = LayoutInflater.from(context).inflate(R.layout.layout_company_detail_item, null);
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
        final Company company = companies.get(pos);
        if (holder instanceof Holder) {
            Glide.with(context).load(company.getCompanyLogo().getUrl()).into(holder.companyLogo);
            holder.tvCompanyName.setText(company.getCompanyName());
            holder.tvCompanyCityLocation.setText(company.getCompanyCityLocation());
            holder.tvCompanyInfo.setText(company.getCompanyTag().get(0) + " | " +
                    company.getCompanyType().getType() + " | " + company.getCompanyScale().getScale());
            if (mListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onItemClick(pos, company);
                    }
                });
            }
        }
    }

    /**
     * 得到列表项真实准确的位置信息
     * @param holder 持有者类，用于返回每项原来位置，需要减去头部加入的View
     * @return 处理过得每项位置
     */
    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? companies.size() : companies.size() + 1;
    }

    class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.company_logo)
        ImageView companyLogo;
        @BindView(R.id.tv_company_name)
        TextView tvCompanyName;
        @BindView(R.id.tv_company_city_location)
        TextView tvCompanyCityLocation;
        @BindView(R.id.tv_company_info)
        TextView tvCompanyInfo;
        @BindView(R.id.arrow_right)
        ImageView arrowRight;

        public Holder(View itemView) {
            super(itemView);
            if (itemView == mHeaderView) return;
            ButterKnife.bind(this, itemView);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(int postion, Object object);
    }
}
