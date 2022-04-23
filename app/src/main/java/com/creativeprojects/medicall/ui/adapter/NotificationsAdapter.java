package com.creativeprojects.medicall.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeprojects.medicall.R;
import com.creativeprojects.medicall.database.roomdb.NotificationDatabase;
import com.creativeprojects.medicall.databinding.FragmentNotificationBinding;
import com.creativeprojects.medicall.event.UpdateReadEvent;
import com.creativeprojects.medicall.model.NotificationModel;
import com.creativeprojects.medicall.utils.mock.DoAsyncTask;
import com.google.android.material.card.MaterialCardView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder>{
    public List<NotificationModel> notificationList;
    Context mContext;
    FragmentNotificationBinding mBinding;

    public NotificationsAdapter(List<NotificationModel> notificationList,Context context,FragmentNotificationBinding binding) {
        this.notificationList = notificationList;
        mContext = context;
        mBinding = binding;
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
        int mPosition = position;

        if(notificationList.get(position).getRead().equals("false")){
//            holder.isRead.setVisibility(View.VISIBLE);
            holder.isRead.setBackground(AppCompatResources.getDrawable(mContext,R.drawable.mark_as_read_gray));
        }else{
//            holder.isRead.setVisibility(View.GONE);
            holder.isRead.setBackground(AppCompatResources.getDrawable(mContext,R.drawable.mark_as_read_blue));

        }
        Log.d("MyTagHere", "onClick: "+notificationList.get(mPosition).getRead()+position);


        holder.mainItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(notificationList.get(mPosition).getRead().equals("false")) {
//                    holder.isRead.setVisibility(View.GONE);
                    holder.isRead.setBackground(AppCompatResources.getDrawable(mContext,R.drawable.mark_as_read_blue));
                    EventBus.getDefault().postSticky(new UpdateReadEvent(mPosition,notificationList.size()));
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mainImage;
        TextView mainText,date;
        RelativeLayout mainItem;
        ImageView isRead;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mainImage = itemView.findViewById(R.id.viewCircle);
            mainText = itemView.findViewById(R.id.mainText);
            date = itemView.findViewById(R.id.date);
            mainItem = itemView.findViewById(R.id.itemOfRecyclerView);
            isRead = itemView.findViewById(R.id.isRead);
        }
    }
}
