package com.creativeprojects.medicall.ui.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeprojects.medicall.R;

import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder>{
    List<Bitmap> myPictures;
    List<String> myNotificationMessages;
    List<String> dateFormat;

    public NotificationsAdapter(List<Bitmap> myPictures, List<String> myNotificationMessages, List<String> dateFormat) {
        this.myPictures = myPictures;
        this.myNotificationMessages = myNotificationMessages;
        this.dateFormat = dateFormat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_notification,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mainImage.setImageBitmap(myPictures.get(position));
        holder.mainText.setText(myNotificationMessages.get(position));
        holder.date.setText(dateFormat.get(position));
    }

    @Override
    public int getItemCount() {
        return myNotificationMessages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mainImage;
        TextView mainText,date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mainImage = itemView.findViewById(R.id.viewCircle);
            mainText = itemView.findViewById(R.id.mainText);
            date = itemView.findViewById(R.id.date);
        }
    }
}
