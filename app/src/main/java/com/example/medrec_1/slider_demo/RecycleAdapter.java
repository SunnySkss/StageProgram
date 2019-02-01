package com.example.medrec_1.slider_demo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.provider.Settings.System.getString;
import static android.support.v4.content.ContextCompat.startActivity;


public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyHolder>  {

//    ArrayList<CreateUserResponse> userList;
//    Context context;
//    private RecycleAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(CreateUserResponse item);
    }

    private final List<CreateUserResponse> items;
    private  OnItemClickListener listener;
    private Context context;
   // public RecyclerViewClickListener recyclerViewClickListener;


    /*public interface myClickListener {
        void onProductClick(CreateUserResponse resultBean);
    }

    public void onOfferClickListener(RecycleAdapter.myClickListener onItemClickListener) {
        this.myClickListener = onItemClickListener;
    }
*/

    public void onOfferClickListener(RecycleAdapter.OnItemClickListener onItemClickListener) {
        this.listener = onItemClickListener;
    }
//    public interface RecyclerViewClickListener {
//     void RecycleOnClick( int position);
//    }

//    public void notifyAdapter(ArrayList<CreateUserResponse> data) {
//        this.notifyDataSetChanged();
//    }

    public RecycleAdapter(List<CreateUserResponse> items,OnItemClickListener onItemClickListener, Context context){
        this.items=items;
        this.context=context;
        this.listener=onItemClickListener;

    }

    public RecycleAdapter(List<CreateUserResponse> items, Context context){
        this.items=items;
        this.context=context;

    }


//    public interface OnItemClickListener {
//        public void onAdapter1Click(int position);
//    }
//
//    public void setOnItemClickListener(RecycleAdapter.OnItemClickListener onItemClickListener) {
//        this.onItemClickListener = onItemClickListener;
//    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View Layout=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_card_layout,null);
        MyHolder myHolder=new MyHolder(Layout);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder viewHolder, final int i) {


        viewHolder.bind(items.get(i), listener);

        Picasso.get()
                .load("http://stageprogram.com/"+items.get(i).getStandardThumbnailUrl())
                .placeholder(R.drawable.dummyvideo)
                .error(R.drawable.dummyvideo)
                .into(viewHolder.img);
//        viewHolder.textView.setText(userList.get(i).getMediaUrl());
//        String str=userList.get(i).getMediaUrl();
//
//        SpannableString content = new SpannableString(str);
//        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
//        viewHolder.textView.setText(content);
////        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Toast.makeText(context, ""+i, Toast.LENGTH_SHORT).show();
////
////              //  recyclerViewClickListener.RecycleOnClick(i);
////            }
////        });
//        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onItemClickListener.onAdapter1Click(2);
//            }
//        });
    }
    //commit to github
///bgcvasdvjhsaddhasdhfdui
    @Override
    public int getItemCount() {

        return items.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView img;
        TextView cardVedTital,cardVedDescription,cardVedViews,cardViewHowLong;

        public MyHolder( View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.mimageView);
            cardVedTital=(TextView)itemView.findViewById(R.id.cardVedTitle);
            cardView=(CardView) itemView.findViewById(R.id.card_view);
            cardVedDescription=itemView.findViewById(R.id.cardVedDesc);
            cardVedViews=itemView.findViewById(R.id.cardVedViews);
            cardViewHowLong=itemView.findViewById(R.id.cardVedHowLong);
        }
        public void bind(final CreateUserResponse item, final OnItemClickListener mlistener) {
            cardVedTital.setText(item.getVideoTitle());
            cardVedDescription.setText(item.getVideoDescription());
            cardVedViews.setText(item.getTotalViews()+"  views");
            cardViewHowLong.setText(item.getHowLong());
            //Picasso.load("http://stageprogram.com/"+item.getMediaUrl()).into(img);
//            Picasso.get()
//                .load("http://stageprogram.com/"+item.getStandardThumbnailUrl())
//                .placeholder(R.drawable.chak_de_india)
//                .error(R.drawable.chak_de_india)
//                .into(MyHolder.img);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    mlistener.onItemClick(item);
                }
            });
        }
    }
}
