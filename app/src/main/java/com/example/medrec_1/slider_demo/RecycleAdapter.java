package com.example.medrec_1.slider_demo;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;

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

//
    }
    //commit to github

    @Override
    public int getItemCount() {

        return items.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {

        RelativeLayout l1;
        CardView cardView;
        ImageView img,imgfirst;
        TextView cardVedTital,cardVedDescription,cardVedViews,cardViewHowLong;

        public MyHolder( View itemView) {
            super(itemView);
            //l1=itemView.findViewById(R.id.linear_mv_Rel);
            imgfirst=itemView.findViewById(R.id.first_image);
            img = itemView.findViewById(R.id.mimageView);
            cardVedTital=(TextView)itemView.findViewById(R.id.cardVedTitle);
            //cardView=(CardView) itemView.findViewById(R.id.card_view);
            cardVedDescription=itemView.findViewById(R.id.cardVedDesc);
          //  cardVedViews=itemView.findViewById(R.id.cardVedViews);
          //  cardViewHowLong=itemView.findViewById(R.id.cardVedHowLong);
        }
        public void bind(final CreateUserResponse item, final OnItemClickListener mlistener) {
            cardVedTital.setText(item.getVideoTitle());
            cardVedDescription.setText(item.getVideoDescription());
           // cardVedViews.setText(item.getTotalViews()+"  views");
           // cardViewHowLong.setText(item.getHowLong());
            //Picasso.load("http://stageprogram.com/"+item.getMediaUrl()).into(img);
//            Picasso.get()
//                .load("http://stageprogram.com/"+item.getStandardThumbnailUrl())
//                .placeholder(R.drawable.chak_de_india)
//                .error(R.drawable.chak_de_india)
//                .into(MyHolder.img);
            img.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    mlistener.onItemClick(item);
                }
            });
        }
    }
}
