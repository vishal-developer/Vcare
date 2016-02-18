package com.care24.datasource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.care24.db.DataBaseHelper;
import com.care24.model.Photo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vishal on 17/2/16.
 */
public class PhotoDataSource {

    private final String TAG = DataBaseHelper.class.getSimpleName();
    private SQLiteDatabase database;
    private DataBaseHelper dbHelper;
    private String[] allColumns = { DataBaseHelper.PHOTOS_ID,
            DataBaseHelper.PHOTOS_URL,
            DataBaseHelper.PHOTOS_TITLE, };

    public PhotoDataSource(Context context){
        dbHelper = new DataBaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long create(Photo photo){
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.PHOTOS_ID, photo.getId());
        values.put(DataBaseHelper.PHOTOS_URL, photo.getUri());
        values.put(DataBaseHelper.PHOTOS_TITLE, photo.getTitle());
        long id = -1;
        try {
            id = database.insert(DataBaseHelper.PHOTOS_TABLE_NAME, null, values);
        }catch (Exception e){
            Log.e(TAG, "Error insertion:"+e.getStackTrace());
        }
        Log.d(TAG, "inserted Id :" + id);
        return id;
    }

    public ArrayList<Photo> findAll(){
        ArrayList<Photo> photoList = new ArrayList<>();
        try{
        Cursor cursor = database.query(DataBaseHelper.PHOTOS_TABLE_NAME, allColumns, null, null, null, null, null);
        while(cursor.moveToNext()){
            Photo photo = cursorToPhoto(cursor);
            photoList.add(photo);
        }
        }catch (Exception e){
            Log.e(TAG, "Error getting photos:"+e.getStackTrace());
        }
        return photoList;
    }

    public int updatePhoto(Photo photo){
        int updateCount = 0;
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.PHOTOS_URL, photo.getUri());
        values.put(DataBaseHelper.PHOTOS_TITLE, photo.getTitle());
        try{
            updateCount = database.update(DataBaseHelper.PHOTOS_TABLE_NAME, values, DataBaseHelper.PHOTOS_ID + " = ?", new String[]{String.valueOf(photo.getId())});
        }catch (Exception e){
            Log.e(TAG, "Error getting photos:"+e.getStackTrace());
        }
        return updateCount;
    }

    public Photo findById(int id){
        Photo photo = null;
        try{
            Cursor cursor = database.query(DataBaseHelper.PHOTOS_TABLE_NAME, allColumns, DataBaseHelper.PHOTOS_ID + " = '"+id+"'", null, null, null, null);
            cursor.moveToFirst();
            photo = cursorToPhoto(cursor);
        }catch (Exception e){
            Log.e(TAG, "Error getting photos:"+e.getStackTrace());
        }
        return photo;
    }


    public ArrayList<Integer> insertAllPhotos(List<Photo> photoList){
        int insertedCount = 0;
        int updatedCount = 0;
        ArrayList<Integer> updateList = new ArrayList<Integer>();
        for(int i = 0; i < photoList.size(); i++){
            Photo photo = findById(photoList.get(i).getId());
            if(photo == null){
                if(create(photoList.get(i))!= -1){
                    insertedCount++;
                }
            }else{
                if(updatePhoto(photo) != 0){
                    updatedCount ++;
                }
            }
        }
        updateList.add(insertedCount);
        updateList.add(updatedCount);
        return updateList;
    }

    private Photo cursorToPhoto(Cursor cursor){
        Photo photo = new Photo();
        photo.setId(cursor.getInt(0));
        photo.setUri(cursor.getString(1));
        photo.setTitle(cursor.getString(2));
        return photo;
    }

}
