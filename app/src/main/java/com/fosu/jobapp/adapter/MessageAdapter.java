package com.fosu.jobapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fosu.jobapp.R;
import com.fosu.jobapp.bean.Message;

import java.util.List;

public class MessageAdapter extends BaseAdapter {
	private List<Message> list_msg;
	private Context context;

	public MessageAdapter(List<Message> list_msg, Context context) {
		super();
		this.list_msg = list_msg;
		this.context = context;
	}

	@Override
	public int getCount() {
		return list_msg.size();
	}

	@Override
	public Object getItem(int position) {
		return list_msg.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Message message = (Message) getItem(position);
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.msg_item, null);
			holder = new ViewHolder();
			holder.message_left = (TextView) convertView
					.findViewById(R.id.left);
			holder.message_right = (TextView) convertView
					.findViewById(R.id.right);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (message.getType() == Message.TYPE_RECEIVED) {
			holder.message_left.setVisibility(View.VISIBLE);
			holder.message_right.setVisibility(View.GONE);
			holder.message_left.setText(message.getContent());
		} else if (message.getType() == Message.TYPE_SENT) {
			holder.message_right.setVisibility(View.VISIBLE);
			holder.message_left.setVisibility(View.GONE);
			holder.message_right.setText(message.getContent());
		}
		return convertView;
	}

	private class ViewHolder {
		TextView message_left;
		TextView message_right;
	}

}
