package com.example.medrec_1.slider_demo;
import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.medrec_1.slider_demo.model.CreateUserResponse;
import com.example.medrec_1.slider_demo.utils.Constant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;




public class FragmentRajasthan extends Fragment implements RecycleAdapter.OnItemClickListener{

    APIInterface apiInterface;
    private ArrayList<CreateUserResponse> createUserResponses =  new ArrayList<>();
    private ArrayList<CreateUserResponse> createUserResponses2 =  new ArrayList<>();
    private ArrayList<CreateUserResponse> createUserResponses3 =  new ArrayList<>();


    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    private RecycleAdapter  recycleAdapter;
    private View view;
    private ProgressDialog mProgressDialog;
    private TextView vedTitle,vedDesc,vedViews,vedLong;
    private RelativeLayout rl;
    String imgStrRaj;
    ImageView imgRaj;
    public FragmentRajasthan() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_fragment_rajasthan, container, false);
         recyclerView=view.findViewById(R.id.myRecyclerRajasthan);
    imgRaj=view.findViewById(R.id.first_imagerajasthan);

        // recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
         //recycleAdapter = new RecycleAdapter(createUserResponses,getContext());
       // recycleAdapter.onOfferClickListener(this);

        mProgressDialog = new ProgressDialog(getContext());

        linearLayoutManager=new LinearLayoutManager(getContext());
         recyclerView.setHasFixedSize(true);
         recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        apiInterface = APIClient.getClient().create(APIInterface.class);

        vedTitle=view.findViewById(R.id.VedTitleRajasthan);
       // vedDesc=view.findViewById(R.id.VedDescRajasthan);
        vedViews=view.findViewById(R.id.VedViewsRajasthan);
        vedLong=view.findViewById(R.id.VedHowLongRajasthan);
        rl=view.findViewById(R.id.relRajasthan);
        rl.setVisibility(View.INVISIBLE);
        // RecycleAdapter recycleAdapter=new RecycleAdapter(getContext(),moviewPoster,movienames);
        //   recyclerView.setAdapter(recycleAdapter);
        getList();
        imgRaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getBaseContext(),MediaActivity.class);
                i.putExtra("data",createUserResponses3.get(0));
                getActivity().startActivity(i);
            }
        });
        return view;


    }

    private void getList() {
        // userList.clear();
        Log.d("inside","retro");
        mProgressDialog.setIcon(R.drawable.loader);
        mProgressDialog.show();
        // Call<ResponseBody> call2=apiInterface.doGetListResources();

        Call<List<CreateUserResponse>> call = apiInterface.doCreateUserWithField(1,1000,30);
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
                    imgStrRaj=Constant.VIDEO_URL+createUserResponses2.get(0).getStandardThumbnailUrl();
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
        if(data!=null) {
            recyclerView.setAdapter(new RecycleAdapter(data, this, getContext()));
            rl.setVisibility(View.VISIBLE);
        }
        Picasso.get()
                .load(imgStrRaj)
                .placeholder(R.drawable.dummyvideo)
                .error(R.drawable.dummyvideo)
                .into(imgRaj);
        vedTitle.setText(String.valueOf(createUserResponses3.get(0).getVideoTitle()));
      //  vedDesc.setText(String.valueOf(createUserResponses3.get(0).getVideoDescription()));
        int viewers=createUserResponses3.get(0).getTotalViews();
        double viewr=(double) viewers/1000;
        vedViews.setText("Views "+new DecimalFormat("##.#").format( viewr)+"k");

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


//    @Override
//    public void RecycleOnClick(int position) {
//        String str="http://stageprogram.com/"+createUserResponses.get(position).getMediaUrl();
//        Intent i = new Intent(getActivity().getBaseContext(),
//                MediaActivity.class);
//        //PACK DATA
//        i.putExtra("SENDER_KEY", str);
//        getActivity().startActivity(i);
//    }



    @Override
    public void onItemClick(CreateUserResponse item) {
        Intent i = new Intent(getActivity().getBaseContext(),
                MediaActivity.class);
        i.putExtra("data",item);
        getActivity().startActivity(i);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}