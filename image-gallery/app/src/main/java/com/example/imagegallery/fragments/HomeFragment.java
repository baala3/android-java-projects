package com.example.imagegallery.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.imagegallery.FlickerApi;
import com.example.imagegallery.R;
import com.example.imagegallery.adapter.ImageAdapter;
import com.example.imagegallery.model.ImageItem;
import com.example.imagegallery.model.JsonData;
import com.example.imagegallery.model.photo;
import com.example.imagegallery.model.photos;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    ArrayList<ImageItem> imageItemArrayList;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    ProgressBar progressBar;
    //TextView error;
    RecyclerView.LayoutManager layoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_home, container, false);
        imageItemArrayList=new ArrayList<>();

        recyclerView=v.findViewById(R.id.recyclerview);
        recyclerView.setVisibility(View.GONE);
        progressBar=v.findViewById(R.id.progree_bar);
        progressBar.setVisibility(View.VISIBLE);
        //error=v.findViewById(R.id.error);
        recyclerView.setHasFixedSize(true);
        layoutManager=new GridLayoutManager(getContext(),3);
        LoadDate();
        return v;
    }

    private void LoadDate() {
      /*  imageItemArrayList.add(new ImageItem(R.drawable.atg,"ATG"));
        imageItemArrayList.add(new ImageItem(R.drawable.atg,"home"));
        imageItemArrayList.add(new ImageItem(R.drawable.atg,"about"));
        imageItemArrayList.add(new ImageItem(R.drawable.atg,"profile"));
        imageItemArrayList.add(new ImageItem(R.drawable.atg,"ATG"));
        imageItemArrayList.add(new ImageItem(R.drawable.atg,"home"));
        imageItemArrayList.add(new ImageItem(R.drawable.atg,"about"));
        imageItemArrayList.add(new ImageItem(R.drawable.atg,"profile"));
        imageItemArrayList.add(new ImageItem(R.drawable.atg,"ATG"));
        imageItemArrayList.add(new ImageItem(R.drawable.atg,"home"));
        imageItemArrayList.add(new ImageItem(R.drawable.atg,"about"));
        imageItemArrayList.add(new ImageItem(R.drawable.atg,"profile"));
        imageItemArrayList.add(new ImageItem(R.drawable.atg,"ATG"));
        imageItemArrayList.add(new ImageItem(R.drawable.atg,"home"));
        imageItemArrayList.add(new ImageItem(R.drawable.atg,"about"));
        imageItemArrayList.add(new ImageItem(R.drawable.atg,"profile"));*/
        final Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.flickr.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FlickerApi flickerApi=retrofit.create(FlickerApi.class);
        Call<JsonData> call=flickerApi.getData();
        call.enqueue(new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                if(!response.isSuccessful())
                {

                //    error.setText(response.code());
                    Toast.makeText(getContext(),response.code(),Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    return;
                }
           //     error.setText("success");
                Toast.makeText(getContext(),"Success",Toast.LENGTH_LONG).show();
              JsonData jsonData=response.body();
                photos photos=jsonData.getPhotos();
                ArrayList<photo> photoArrayList=photos.getPhoto();
                for(photo p:photoArrayList)
                {
                    imageItemArrayList.add(new ImageItem(p.getUrl_s(),p.getTitle()));
                }
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                adapter=new ImageAdapter(imageItemArrayList);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {
            //    error.setText(t.getMessage().toString());
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(),t.getMessage().toString(),Toast.LENGTH_LONG).show();

            }
        });

    }
}