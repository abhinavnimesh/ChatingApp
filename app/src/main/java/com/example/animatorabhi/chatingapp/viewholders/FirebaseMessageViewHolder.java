package com.example.animatorabhi.chatingapp.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.animatorabhi.chatingapp.R;
import com.example.animatorabhi.chatingapp.chat.ChatMessage;
import com.example.animatorabhi.chatingapp.global.Constant;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by ANIMATOR ABHI on 27/04/2017.
 */

public class FirebaseMessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;
    View mView;
    Context mContext;

    ChatMessage messageModel;
    private LinearLayout llOpt;

    public FirebaseMessageViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindUser(final ChatMessage messageModel) {
        TextView tvSenderView = (TextView) mView.findViewById(R.id.tvSenderView);
        TextView tvReciverView = (TextView) mView.findViewById(R.id.tvReciverView);
        TextView tvTimeStampSender = (TextView) mView.findViewById(R.id.tvTimeStampSender);
        TextView tvTimeStampReciver = (TextView) mView.findViewById(R.id.tvTimeStampReciver);
      ImageView imgDelete = (ImageView) mView.findViewById(R.id.imgDelete);
       llOpt = (LinearLayout) mView.findViewById(R.id.llOpt);
        LinearLayout llSenderView = (LinearLayout) mView.findViewById(R.id.llSenderView);
        LinearLayout llReciverView = (LinearLayout) mView.findViewById(R.id.llReciverView);
        if (messageModel.getSender().equals("" + Constant.USER_ID)) {
            tvSenderView.setText(messageModel.getMessage());
            tvTimeStampSender.setText("" + getLastFromTimestamp(messageModel.getTimestamp()));
            llSenderView.setVisibility(View.VISIBLE);
            llReciverView.setVisibility(View.GONE);

        } else {
            tvTimeStampReciver.setText("" + getLastFromTimestamp(messageModel.getTimestamp()));
            tvReciverView.setText(messageModel.getMessage());
            llReciverView.setVisibility(View.VISIBLE);
            llSenderView.setVisibility(View.GONE);

        }
        tvSenderView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                llOpt.setVisibility(View.VISIBLE);
                return false;
            }
        });
      imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llOpt.setVisibility(View.GONE);
                Log.d("imgDelete--->", "imgDelete....." + messageModel.getMessage() + "getAdapterPosition();---" + getAdapterPosition() + "getItemId()---->" + getItemId());
            }
        });

    }

    @Override
    public void onClick(View view) {
    }

    private String getLastFromTimestamp(long timestamp) {
        try {
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            calendar.setTimeInMillis(timestamp * 1000);
            Calendar cal = Calendar.getInstance();
            Date date1 = new Date(cal.getTimeInMillis());
            Date date2 = new Date(calendar.getTimeInMillis());
            long diff = date1.getTime() - date2.getTime();
            long seconds = diff / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;
            long months = 0;
            if (days > 30) {
                months = days / 30;
            }
            long years = 0;
            if (months > 11) {
                years = months / 12;
            }

            if (years == 1) {
                return "1 Year ago";
            } else if (years > 1) {
                return years + " Years ago";
            } else if (months == 1) {
                return "1 Month ago";
            } else if (months > 1) {
                return months + " Months ago";
            } else if (days == 1) {
                return "1 Day ago";
            } else if (days > 1) {
                return days + " Days ago";
            } else if (hours == 1) {
                return "1 Hour ago";
            } else if (hours > 1) {
                return hours + " Hours ago";
            } else if (minutes == 1) {
                return "1 Minute ago";
            } else if (minutes > 1) {
                return minutes + " Minutes ago";
            } else if (seconds == 1) {
                return "1 Second ago";
            } else if (seconds <= 0) {
                return "0 Second ago";
            } else {
                return seconds + " Seconds ago";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
