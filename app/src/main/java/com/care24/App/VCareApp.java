package com.care24.App;

import android.app.Application;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.care24.Service.DataReceiver;
import com.care24.datasource.PhotoDataSource;
import com.care24.model.Photo;
import com.care24.util.ResponseUtil;
import com.care24.util.UrlConstants;
import com.care24.vcare.HomeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vishal on 18/2/16.
 */
public class VCareApp extends Application {
    private final String TAG = VCareApp.class.getSimpleName();
    public DataReceiver dataReceiver;
    @Override
    public void onCreate() {
        super.onCreate();
        getPhotos();
    }

    private void getPhotos() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, UrlConstants.GET_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                parsePhotos(response);
                saveToLocalPhoto(ResponseUtil.parsePhotos(response));
                dataReceiver.sendData();
//                setPhotoToAdapter(parsePhotos(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "error :" + error.getStackTrace());
//                Snackbar.make(getWindow().getDecorView().getRootView(), error.getMessage(), Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

            }
        });
        queue.add(request);
    }

    private void saveToLocalPhoto(List<Photo> photoList){
        PhotoDataSource photoDataSource = new PhotoDataSource(this);
        photoDataSource.open();
        ArrayList<Integer> updateCount = photoDataSource.insertAllPhotos(photoList);
        photoDataSource.close();
        Log.d(TAG, "Updated count :" + updateCount.toString());
    }
}
