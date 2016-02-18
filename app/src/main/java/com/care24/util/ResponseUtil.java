package com.care24.util;

import android.support.design.widget.Snackbar;
import android.util.Log;

import com.care24.model.Photo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vishal on 18/2/16.
 */
public class ResponseUtil {
    private static final String TAG = ResponseUtil.class.getSimpleName();

    public static ArrayList<Photo> parsePhotos(String response) {
//        Log.d(TAG, "Response: "+response);
        Type type = new TypeToken<List<Photo>>(){}.getType();
        ArrayList<Photo> photoList = new ArrayList<>();
        try {
            JSONObject jsonResponse = new JSONObject(response);
            photoList = new Gson().fromJson(jsonResponse.getString("data"),type);

        } catch (JSONException e) {
            Log.e(TAG, "Updated count :" + e.getStackTrace());
        }catch (Exception e){
            Log.e(TAG, "Updated count :" + e.getStackTrace());
        }
        return photoList;
    }
}
