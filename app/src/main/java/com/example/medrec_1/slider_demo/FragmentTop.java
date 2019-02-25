package com.example.medrec_1.slider_demo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.DecimalFormat;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medrec_1.slider_demo.utils.Constant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentTop extends Fragment implements RecycleAdapter.OnItemClickListener {
    RecyclerView recyclerView;
    String first="";

    LinearLayoutManager linearLayoutManager;
    AppBarLayout abl;
    private View view;
    APIInterface apiInterface;
    private RecycleAdapter  recycleAdapter;
    private ArrayList<CreateUserResponse> createUserResponses =  new ArrayList<>();
    private ArrayList<CreateUserResponse> createUserResponses2 =  new ArrayList<>();
    private ArrayList<CreateUserResponse> createUserResponses3 =  new ArrayList<>();
    GridLayoutManager grid;
    Boolean isScrolling=false;
    private ProgressDialog mProgressDialog;
    private ImageView fimg;
    private TextView vedTitle,vedDesc,vedViews,vedLong;

    public FragmentTop() {
       //apiInterface=null;
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
          view = inflater.inflate(R.layout.fragment_fragment_one, container, false);
          //abl=view.findViewById(R.id.app_bar);
          vedTitle=view.findViewById(R.id.VedTitleTop);
         // vedDesc=view.findViewById(R.id.VedDescTop);
          vedViews=view.findViewById(R.id.VedViewsTop);
          vedLong=view.findViewById(R.id.VedHowLongTop);
         // updateView();
        Log.e("getList()", String.valueOf(createUserResponses.size()));
        //boolean aa=new Constant().isNetworkConnected();
//        if(Constant.isNetworkConnected())//|| Constant.isWifiConnected())
//        {
            updateView();
            getList();

            //setAdapter(createUserResponses);
//        }
//        else
//        {
//            Toast.makeText(getContext(), "Check internet connection first...", Toast.LENGTH_SHORT).show();
//        }

        fimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getBaseContext(),MediaActivity.class);
                i.putExtra("data",createUserResponses3.get(0));
                getActivity().startActivity(i);
            }
        });
        return view;
    }



    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
    private void updateView() {
       // recycleAdapter = new RecycleAdapter(createUserResponses,getContext());
       // recycleAdapter.onOfferClickListener(this);
        fimg=view.findViewById(R.id.first_image);
        recyclerView=view.findViewById(R.id.myRecyclerTop);

        mProgressDialog = new ProgressDialog(getContext());

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        apiInterface = APIClient.getClient().create(APIInterface.class);
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("getList()","onCreate");

    }




    @Override
    public void onItemClick(CreateUserResponse item) {

      //  Toast.makeText(getContext(), "hiiiii", Toast.LENGTH_SHORT).show();
       // createUserResponses=item;
        Intent i = new Intent(getActivity().getBaseContext(),
                  MediaActivity.class);
               // i.putStringArrayListExtra("userData",item);
        i.putExtra("data",item);
        /*    i.putExtra("mediaUrl","http://stageprogram.com/"+item.getMediaUrl());
            i.putExtra("likesVed",item.getTotalLike());
            i.putExtra("dislikeVed",item.getTotalDislike());*/
        getActivity().startActivity(i);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void getList() {
        // userList.clear();
        Log.d("inside","retro");
        // Call<ResponseBody> call2=apiInterface.doGetListResources();
        mProgressDialog.setMessage("Loading");
        mProgressDialog.show();
        Call<List<CreateUserResponse>> call = apiInterface.doCreateUserWithField(1,1000);
        call.enqueue(new Callback<List<CreateUserResponse>>() {
            @Override
            public void onResponse(Call<List<CreateUserResponse>> call, Response<List<CreateUserResponse>> response) {

                if (response.isSuccessful() && response.code()==200) {
                    for (int i = 0; i < response.body().size(); i++) {
                        createUserResponses.add(response.body().get(i));
                    }
                }
                for(int j=0;j<createUserResponses.size();j++)
                {
                    int minno=0;
                    int maxSize=createUserResponses.size()-1;
                    Random r = new Random();
                    int ii= r.nextInt((maxSize - minno) + 1) + minno;
                    createUserResponses2.add(createUserResponses.get(ii));
                    first= "http://stageprogram.com/"+createUserResponses2.get(0).getStandardThumbnailUrl();

                }
                createUserResponses3.add(createUserResponses2.get(0));
                createUserResponses2.remove(0);
                setAdapter(createUserResponses2);
                mProgressDialog.dismiss();
            }
            @Override
            public void onFailure(Call<List<CreateUserResponse>> call, Throwable t) {
                Log.d("onFailure",t.getMessage());
                call.cancel();
            }
        });
    }

    private void setAdapter(ArrayList<CreateUserResponse> data) {
        recyclerView.setAdapter(new RecycleAdapter(data,this, getContext()));
        mProgressDialog.dismiss();

        Picasso.get()
                .load(first)
                .placeholder(R.drawable.dummyvideo)
                .error(R.drawable.dummyvideo)
                .into(fimg);

//        String days=createUserResponses3.get(0).getHowLong();
//        String day[]=days.split(" ");
//        int tdays=Integer.parseInt(day[0]);
        vedTitle.setText(String.valueOf(createUserResponses3.get(0).getVideoTitle()));
       // vedDesc.setText(String.valueOf(createUserResponses3.get(0).getVideoDescription()));
        int viewers=createUserResponses3.get(0).getTotalViews();
        double viewr=(double) viewers/1000;
        vedViews.setText(new DecimalFormat("##.#").format( viewr)+"k views");


        String ago="";
        String Sdays=createUserResponses3.get(0).getHowLong();
        String[] parts = Sdays.split(" ");
        String tDays = parts[0];
        int tYears= Integer.parseInt(tDays)/365;
        int rDays=Integer.parseInt(tDays)%365;
        int month=rDays/30;
        int rrDays=rDays%30;
        if(tYears>0)
        {
            ago=String.valueOf(tYears)+" year ago";
        }
        else if(month>0)
        {
            ago=String.valueOf(month)+" month ago";
        }
        else if(rrDays>0)
        {
            ago=String.valueOf(rrDays)+" days ago";
        }
        vedLong.setText(ago);
    }
}
