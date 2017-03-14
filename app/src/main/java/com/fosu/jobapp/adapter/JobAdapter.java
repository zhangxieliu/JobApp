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
import com.fosu.jobapp.bean.Job;
import com.fosu.jobapp.listener.OnItemClickListener;
import com.fosu.jobapp.listener.OnNODoubleClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/8.
 * 职位列表的Adapter
 */

public class JobAdapter extends SwipeMenuAdapter<JobAdapter.Holder> {
    private Context context;
    private List<Job> jobs = new ArrayList<>();

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private View mHeaderView;
    private OnItemClickListener mListener;

    public JobAdapter(Context context) {
        this.context = context;
    }

    public JobAdapter(Context context, List<Job> datas) {
        this.context = context;
        jobs = datas;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
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
     * 添加职位信息在原有集合中，并通知Adapter更新数据
     * @param jobs 添加的职位信息
     */
    public void addDatas(List<Job> jobs) {
        this.jobs.clear();
        this.jobs.addAll(jobs);
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
        View view = LayoutInflater.from(context).inflate(R.layout.layout_job_list_item, null);
        return view;
    }

    @Override
    public Holder onCompatCreateViewHolder(View realContentView, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER)
            return new Holder(mHeaderView);
        return new Holder(realContentView);
    }

    @Override
    public void onBindViewHolder(JobAdapter.Holder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER)
            return;
        final int pos = getRealPosition(holder);
        final Job job = jobs.get(pos);
        if (holder instanceof Holder) {
            Company company = job.getCompany();
            Glide.with(context).load(company.getCompanyLogo().getUrl())
                    .asBitmap().into(holder.companyLogo);
            holder.tvCompany.setText(company.getCompanyName());
            holder.tvJobSalary.setText(job.getJobSalary().get(0) + "K-" + job.getJobSalary().get(1) + "K");
            holder.tvCompanyScale.setText(company.getCompanyScale().getScale());
            holder.tvCompanyType.setText(company.getCompanyType().getType());
            holder.tvCompanyTag.setText(company.getCompanyIndustry().toString());
            holder.tvEducation.setText(job.getJobEducation().getEducation());
            holder.tvExperience.setText(job.getJobExperience().getExperience());
            holder.tvJobCity.setText(job.getJobCity());
            holder.tvJobCityLocation.setText(job.getJobCityLocation());
            holder.tvJobName.setText(job.getJobName());
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
            try {
                Date date = sdf.parse(job.getJobPublicDate().getDate());
                holder.tvPublicDate.setText(sdf.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (mListener != null) {
                holder.itemView.setOnClickListener(new OnNODoubleClickListener() {
                    @Override
                    public void onNoDoubleClick(View v) {
                        mListener.onItemClick(pos, job);
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
        return mHeaderView == null ? jobs.size() : jobs.size() + 1;
    }

    class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.company_logo)
        ImageView companyLogo;
        @BindView(R.id.tv_job_name)
        TextView tvJobName;
        @BindView(R.id.tv_job_salary)
        TextView tvJobSalary;
        @BindView(R.id.tv_company)
        TextView tvCompany;
        @BindView(R.id.tv_job_city)
        TextView tvJobCity;
        @BindView(R.id.tv_job_city_location)
        TextView tvJobCityLocation;
        @BindView(R.id.tv_experience)
        TextView tvExperience;
        @BindView(R.id.tv_education)
        TextView tvEducation;
        @BindView(R.id.tv_public_date)
        TextView tvPublicDate;
        @BindView(R.id.tv_company_type)
        TextView tvCompanyType;
        @BindView(R.id.tv_company_scale)
        TextView tvCompanyScale;
        @BindView(R.id.tv_company_tag)
        TextView tvCompanyTag;

        public Holder(View itemView) {
            super(itemView);
            if (itemView == mHeaderView) return;
            ButterKnife.bind(this, itemView);
        }
    }
}