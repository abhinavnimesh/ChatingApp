package com.example.animatorabhi.chatingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.animatorabhi.chatingapp.R;
import com.example.animatorabhi.chatingapp.chat.ChatConModel;

import java.util.List;


public class MyAdapterNew extends RecyclerView.Adapter<MyAdapterNew.MyViewHolder> {
        //private String[] mDataset;
        private Context mContext;
        private List<ChatConModel> conList;

        public static class MyViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public ImageView avator;
            public TextView name;
            public TextView description;
           //public TextView mTextView;
            public MyViewHolder(View v) {
                super(v);
               // mTextView = v;
                avator= (ImageView) v.findViewById(R.id.list_avatar);
                name= (TextView) v.findViewById(R.id.list_title);
                description= (TextView) v.findViewById(R.id.list_desc);
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapterNew(Context mContext, List<ChatConModel> conList) {
           this.mContext=mContext;
            this.conList=conList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            // create a new view
            View v =  LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list, parent, false);
            // set the view's size, margins, paddings and layout parameters

            return  new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
           // holder.mTextView.setText(mDataset[position]);
            ChatConModel chatConModel=conList.get(position);
           // holder.avator.setImageResource(Integer.parseInt(chatConModel.getProfilePic()));
            holder.description.setText(chatConModel.getLatestactivity());
            holder.name.setText(chatConModel.getDisplayName());
            Glide.with(mContext).load(chatConModel.getProfilePic()).into(holder.avator);
        }

        @Override
        public int getItemCount() {
          //  return mDataset.length;
            return  conList.size();
        }


}
