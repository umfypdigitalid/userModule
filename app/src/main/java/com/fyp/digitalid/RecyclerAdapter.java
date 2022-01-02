package com.fyp.digitalid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.ImageView;
import android.widget.LinearLayout;
//import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private Context mContext;
    private List<History> historys = new ArrayList<>();


    public RecyclerAdapter (Context context,List<History> historys){
        this.mContext = context;
        this.historys = historys;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mName, mScannedon, mIc, mFullname;
        //private ImageView mImageView;
        //private RatingBar mRate;
        private LinearLayout mContainer;

        public MyViewHolder (View view){
            super(view);

            mName = view.findViewById(R.id.name);
            mScannedon = view.findViewById(R.id.scannedon);
            mIc = view.findViewById(R.id.ic);
            mFullname = view.findViewById(R.id.fullname);
            mContainer = view.findViewById(R.id.history_container);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.historys_list_item_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final History history = historys.get(position);

        holder.mName.setText(history.getName());
        holder.mScannedon.setText(history.getScannedon());
        /*holder.mIc.setText(history.getIc());
        holder.mFullname.setText(history.getFullname());*/
        //Glide.with(mContext).load(history.getImage()).into(holder.mImageView);

        holder.mContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Intent intent = new Intent(mContext,DetailedHistorysActivity.class);
                System.out.println("Name: "+history.getName());
                System.out.println("Scannedon: "+history.getScannedon());
                System.out.println("Ic: "+history.getIc());
                System.out.println("Fullname: "+history.getFullname());
                intent.putExtra("name",history.getName());
                intent.putExtra("scannedon",history.getScannedon());
                intent.putExtra("fullname",history.getFullname());
                intent.putExtra("ic",history.getIc());
                /*intent.putExtra("rate",product.getRating());
                intent.putExtra("price",product.getPrice());*/
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return historys.size();
    }
}