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
 */

public class JobAdapter extends SwipeMenuAdapter<JobAdapter.Holder> {
    private Context mContext;
    private List<Job> mJobs = new ArrayList<>();

    public List<Job> getmJobs() {
        return mJobs;
    }

    public void setmJobs(List<Job> mJobs) {
        this.mJobs = mJobs;
    }

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private View mHeaderView;
    private OnItemClickListener mListener;

    public JobAdapter(Context context) {
        this.mContext = context;
    }

    public JobAdapter(Context context, List<Job> datas) {
        this.mContext = context;
        mJobs = datas;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public void addDatas(List<Job> datas) {
        mJobs.clear();
        mJobs.addAll(datas);
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_job_list_item, null);
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
        final Job job = mJobs.get(pos);
        if (holder instanceof Holder) {
            Company company = job.getCompany();
            Glide.with(mContext).load(company.getCompanyLogo().getUrl())
                    .asBitmap().into(holder.companyLogo);
            holder.tvCompany.setText(company.getCompanyName());
            holder.tvJobSalary.setText(job.getJobSalary().get(0) + "K-" + job.getJobSalary().get(1) + "K");
            holder.tvCompanyScale.setText(company.getCompanyScale().getScale());
            holder.tvCompanyType.setText(company.getCompanyType().getType());
            holder.tvCompanyTag.setText(company.getCompanyTag().toString());
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
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onItemClick(pos, job);
                    }
                });
            }
        }
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? mJobs.size() : mJobs.size() + 1;
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

    public interface OnItemClickListener {
        void onItemClick(int postion, Object object);
    }
}