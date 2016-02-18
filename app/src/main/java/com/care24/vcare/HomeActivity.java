package com.care24.vcare;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.care24.App.VCareApp;
import com.care24.Service.DataReceiver;
import com.care24.adapter.PhotoAdapter;
import com.care24.datasource.PhotoDataSource;
import com.care24.model.Photo;
import com.care24.util.UrlConstants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class HomeActivity extends AppCompatActivity implements DataReceiver{
    private final String TAG = HomeActivity.class.getSimpleName();
    private PhotoAdapter adapter;
    private ArrayList<Photo> photos = new ArrayList<>();
    RecyclerView recyclerView;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ((VCareApp)this.getApplicationContext()).dataReceiver = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new PhotoAdapter(this, photos);
        recyclerView.setAdapter(adapter);
        setPhotoToAdapter();
    }




    private void setPhotoToAdapter(){
        PhotoDataSource photoDataSource = new PhotoDataSource(this);
        photoDataSource.open();
        ArrayList<Photo> photosList = photoDataSource.findAll();
        if(null != photosList && photosList.size() > 0){
            photos = new ArrayList<>(photosList);
            Log.d(TAG, "Response: "+photos.size());
            adapter.resetData(photos);

        }else{
            Snackbar.make(getWindow().getDecorView().getRootView(), "Please wait information synchronizing....", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

    }

    private void saveToLocalPhoto(List<Photo> photoList){
        PhotoDataSource photoDataSource = new PhotoDataSource(this);
        photoDataSource.open();
        ArrayList<Integer> updateCount = photoDataSource.insertAllPhotos(photoList);
        photoDataSource.close();
        Log.d(TAG, "Updated count :"+updateCount.toString());
    }







    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void sendData() {
        setPhotoToAdapter();
    }
}
