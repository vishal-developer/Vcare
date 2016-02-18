package com.care24.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.care24.model.Photo;
import com.care24.vcare.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vishal on 18/2/16.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private final String TAG = PhotoAdapter.class.getSimpleName();
    Context context;
    ArrayList<Photo> photos;
    // Provide a reference to the views for each ata item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public ImageView imageView;
        public ViewHolder(View view) {
            super(view);
            title = (TextView)view.findViewById(R.id.title);
            imageView = (ImageView)view.findViewById(R.id.image);
        }
    }

    public PhotoAdapter(Context context, ArrayList<Photo> photos){
//        Log.d(TAG, "Photos size: " + photos.size());
        this.context = context;
        this.photos = photos;
    }


    @Override
    public PhotoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PhotoAdapter.ViewHolder holder, int position) {
        Photo photo = getItem(position);
        Log.d(TAG, "photo: " + photo.getTitle());
        if(null == photo.getTitle() || photo.getTitle().trim().length() == 0){
            holder.title.setText("Unknown Image");
        }else{
            holder.title.setText(photo.getTitle());
        }
        Picasso.with(context).load(photo.getUri())
                .resize(100,100)
                .centerCrop()
                .into(holder.imageView);

    }

    public void resetData(ArrayList<Photo> photoList){
        photos = photoList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(null != photos){
            return photos.size();
        }
        return 0;
    }

    public Photo getItem(int position){
        return photos.get(position);
    }
}
