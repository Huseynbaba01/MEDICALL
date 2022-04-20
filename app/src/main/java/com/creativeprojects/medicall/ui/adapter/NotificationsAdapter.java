package com.creativeprojects.medicall.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeprojects.medicall.R;
import com.creativeprojects.medicall.model.NotificationModel;

import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder>{
    List<NotificationModel> notificationList;
    Context mContext;

    public NotificationsAdapter(List<NotificationModel> notificationList,Context context) {
        this.notificationList = notificationList;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_notification,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mainImage.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(),notificationList.get(position).getImage()));
        holder.mainText.setText(notificationList.get(position).getNotificationMessage());
        holder.date.setText(notificationList.get(position).getNotificationDate());
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
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
