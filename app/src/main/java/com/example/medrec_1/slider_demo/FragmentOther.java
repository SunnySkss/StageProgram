package com.example.medrec_1.slider_demo;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentOther extends Fragment implements RecycleAdapter.OnItemClickListener{

    APIInterface apiInterface;
    private ArrayList<CreateUserResponse> createUserResponses =  new ArrayList<>();
    private ArrayList<CreateUserResponse> createUserResponses2 =  new ArrayList<>();

    private ArrayList<CreateUserResponse> createUserResponses3 =  new ArrayList<>();
    RecyclerView recyclerView;
    private View view;
    LinearLayoutManager linearLayoutManager;
    private RecycleAdapter  recycleAdapter;
    ImageView imgOther;
    String imgStr;

    public FragmentOther() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_fragment_other, container, false);
      //  recycleAdapter = new RecycleAdapter(createUserResponses,getContext());
        recyclerView=view.findViewById(R.id.myRecyclerOther);
      //  recycleAdapter.onOfferClickListener(this);
        linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        apiInterface = APIClient.getClient().create(APIInterface.class);
        imgOther=view.findViewById(R.id.first_imageOther);
        // RecycleAdapter recycleAdapter=new RecycleAdapter(getContext(),moviewPoster,movienames);
        //   recyclerView.setAdapter(recycleAdapter);
        getList();
        imgOther.setOnClickListener(new View.OnClickListener() {
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
        // Call<ResponseBody> call2=apiInterface.doGetListResources();

        Call<List<CreateUserResponse>> call = apiInterface.doCreateUserWithField(1,1000,29);
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
                    imgStr="http://stageprogram.com/"+createUserResponses2.get(0).getStandardThumbnailUrl();
                }
                createUserResponses3.add(createUserResponses2.get(0));
                createUserResponses2.remove(0);
                setAdapter(createUserResponses2);

            }

            @Override
            public void onFailure(Call<List<CreateUserResponse>> call, Throwable t) {
                Log.d("onFailure",t.getMessage());
                call.cancel();
            }
        });
    }

    private void setAdapter(ArrayList<CreateUserResponse> data) {
     //   Toast.makeText(getContext(), "hh"+String.valueOf(data.size()), Toast.LENGTH_SHORT).show();
        recyclerView.setAdapter(new RecycleAdapter(data,this,getContext()));

        Picasso.get()
                .load(imgStr)
                .placeholder(R.drawable.dummyvideo)
                .error(R.drawable.dummyvideo)
                .into(imgOther);
    }


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