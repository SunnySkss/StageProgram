package com.example.medrec_1.slider_demo;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentOne extends Fragment implements RecycleAdapter.OnItemClickListener {
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    private String movienames[]={"First","Second"};
    private int moviewPoster[]={R.drawable.mv,R.drawable.mvtwo};
    private View view;
    APIInterface apiInterface;
    private RecycleAdapter  recycleAdapter;
    private ArrayList<CreateUserResponse> createUserResponses =  new ArrayList<>();
    private ProgressDialog mProgressDialog;


    public FragmentOne() {
       //apiInterface=null;
        // Required empty public constructor
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("Attach","Called");
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
          view = inflater.inflate(R.layout.fragment_fragment_one, container, false);
         // updateView();
        Log.e("getList()", String.valueOf(createUserResponses.size()));
       updateView();
        getList();
        setAdapter(createUserResponses);
        return view;
    }

    private void updateView() {
        recycleAdapter = new RecycleAdapter(createUserResponses,getContext());
        recycleAdapter.onOfferClickListener(this);
        recyclerView=view.findViewById(R.id.myRecyclerTop);
        linearLayoutManager=new LinearLayoutManager(getContext());
        mProgressDialog = new ProgressDialog(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        apiInterface = APIClient.getClient().create(APIInterface.class);
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("getList()","onCreate");
    }

//    @Override
//    public void onAdapter1Click(int position) {
//        Log.e("getList()","onCreate");
//    }

    @Override
    public void onItemClick(CreateUserResponse item) {

        Toast.makeText(getContext(), "hiiiii", Toast.LENGTH_SHORT).show();
    }

//    @Override
//    public void RecycleOnClick(int position) {
//
//        String str="http://stageprogram.com/"+createUserResponses.get(position).getMediaUrl();
//        Intent i = new Intent(getActivity().getBaseContext(),
//                MediaActivity.class);
//        //PACK DATA
//        i.putExtra("SENDER_KEY", str);
//        getActivity().startActivity(i);
//
//    }


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
        Call<List<CreateUserResponse>> call = apiInterface.doCreateUserWithField(1,20);
        call.enqueue(new Callback<List<CreateUserResponse>>() {
            @Override
            public void onResponse(Call<List<CreateUserResponse>> call, Response<List<CreateUserResponse>> response) {

                if (response.isSuccessful() && response.code()==200) {
                    for (int i = 0; i < response.body().size(); i++) {
                        createUserResponses.add(response.body().get(i));
                    }
                }
                setAdapter(createUserResponses);
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
//        Log.d("onFailure","sunny");
        recyclerView.setAdapter(new RecycleAdapter(data, getContext()));
        mProgressDialog.dismiss();
    }





  }
