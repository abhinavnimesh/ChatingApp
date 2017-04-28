package com.example.animatorabhi.chatingapp.adapter;

/**
 * Created by ANIMATOR ABHI on 28/04/2017.
 */


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.animatorabhi.chatingapp.R;
import com.example.animatorabhi.chatingapp.chat.ChatActivity;
import com.example.animatorabhi.chatingapp.chat.ChatConModel;
import com.example.animatorabhi.chatingapp.chat.ChatMessage;
import com.example.animatorabhi.chatingapp.global.Constant;

import java.util.List;

import static com.example.animatorabhi.chatingapp.global.Utils.getLastFromTimestamp;


public class MyChatAdapter extends RecyclerView.Adapter<MyChatAdapter.MyViewHolder> implements View.OnClickListener {
    //private String[] mDataset;
    private Context mContext;
    private List<ChatMessage> chatList;


    @Override
    public void onClick(View v) {
        Context context = v.getContext();
        Intent intent = new Intent(context, ChatActivity.class);
        context.startActivity(intent);
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
     //   public ImageView avator;
       // public TextView name;
       // public TextView time;
        TextView tvSenderView;
        TextView tvReciverView;
        TextView tvTimeStampSender;
        TextView tvTimeStampReciver;
        LinearLayout llOpt;
        LinearLayout llSenderView;
        LinearLayout llReciverView;
        //public TextView mTextView;
        public MyViewHolder(View v) {
            super(v);
            // mTextView = v;
          //  avator= (ImageView) v.findViewById(R.id.list_avatar);
          //  name= (TextView) v.findViewById(R.id.tvSenderView);
           // time= (TextView) v.findViewById(R.id.tvTimeStampSender);
             tvSenderView = (TextView)v.findViewById(R.id.tvSenderView);
            tvReciverView = (TextView) v.findViewById(R.id.tvReciverView);
            tvTimeStampSender = (TextView) v.findViewById(R.id.tvTimeStampSender);
            tvTimeStampReciver = (TextView) v.findViewById(R.id.tvTimeStampReciver);
            llOpt = (LinearLayout) v.findViewById(R.id.llOpt);
            llSenderView = (LinearLayout) v.findViewById(R.id.llSenderView);
           llReciverView = (LinearLayout) v.findViewById(R.id.llReciverView);


        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyChatAdapter(Context mContext, List<ChatMessage> chatList) {
        this.mContext=mContext;
        this.chatList=chatList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_message_list, parent, false);
        // set the view's size, margins, paddings and layout parameters

        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        // holder.mTextView.setText(mDataset[position]);
        ChatMessage chat=chatList.get(position);
        Log.d("onbind1",""+chat.getMessage());
        Log.d("onbind2",""+chat.getSender());
        Log.d("onbind3",""+chat.getName());

//            holder.avator.setImageResource(Integer.parseInt(chatConModel.getProfilePic()));
      //  holder.description.setText(chat.getLatestactivity());
     //   holder.name.setText(chat.getDisplayName());
        //   holder.avator.setImageResource(R.drawable.a_avator);
      //  Glide.with(mContext).load(chat.getProfilePic()).into(holder.avator);

        if (chat.getSender().equals("" + Constant.USER_ID)) {
            holder.tvSenderView.setText(chat.getMessage());
            holder.tvTimeStampSender.setText("" + getLastFromTimestamp(chat.getTimestamp()));
            holder.llSenderView.setVisibility(View.VISIBLE);
            holder.llReciverView.setVisibility(View.GONE);

        } else {
            holder.tvTimeStampReciver.setText("" + getLastFromTimestamp(chat.getTimestamp()));
           holder. tvReciverView.setText(chat.getMessage());
            holder.llReciverView.setVisibility(View.VISIBLE);
            holder.llSenderView.setVisibility(View.GONE);

        }
        //crop image round
          /*  Glide.with(mContext)
                    .load(chatConModel.getProfilePic())
                    .asBitmap().fitCenter()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            holder.avator.setImageBitmap(ImageHelper.getRoundedCornerBitmap(mContext, resource, 200, 200, 200, false, false, false, false));
                        }
                    });
            Log.d("onbindglide", String.valueOf(chatConModel));*/

    }

    @Override
    public int getItemCount() {
        //  return mDataset.length;
        return  chatList.size();
    }


}
