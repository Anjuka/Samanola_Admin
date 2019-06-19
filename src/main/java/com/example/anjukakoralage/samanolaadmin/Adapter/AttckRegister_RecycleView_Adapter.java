package com.example.anjukakoralage.samanolaadmin.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.anjukakoralage.samanolaadmin.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;

public class AttckRegister_RecycleView_Adapter extends RecyclerView.Adapter<AttckRegister_RecycleView_Adapter.AttcktRegisterViewHolder> {
    private ArrayList<HashMap<String, String>> alRegisterItems;

    public class AttcktRegisterViewHolder extends RecyclerView.ViewHolder {
        private final View mView;
        private TextView userID;
        private TextView userName;
        private TextView userMobile;
        private TextView userEmail;
        private RoundedImageView userImage;


        AttcktRegisterViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            userID = (TextView) itemView.findViewById(R.id.userId);
            userName = (TextView) itemView.findViewById(R.id.userName);
            userMobile = (TextView) itemView.findViewById(R.id.userMobile);
            userEmail = (TextView) itemView.findViewById(R.id.userEmail);
            userImage = (RoundedImageView) itemView.findViewById(R.id.ivUserImage);

        }
    }

    public AttckRegister_RecycleView_Adapter(ArrayList<HashMap<String, String>> alRegisterItems) {
        this.alRegisterItems = alRegisterItems;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public AttcktRegisterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.register_listitem_layout, viewGroup, false);
        AttcktRegisterViewHolder pvh = new AttcktRegisterViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final AttcktRegisterViewHolder AttckRegisterViewHolder, final int i) {
        HashMap<String, String> hmProjectRegisterItem = alRegisterItems.get(i);
        final String Id = hmProjectRegisterItem.get("Id");
        final String Name = hmProjectRegisterItem.get("Name");
        final String PhoneNo = hmProjectRegisterItem.get("PhoneNo");
        final String Email = hmProjectRegisterItem.get("Email");
        final String ImageSt = hmProjectRegisterItem.get("ImageStr");



/*
        Bitmap bitmap = BitmapFactory.decodeFile("/path/images/image.jpg");
        ByteArrayOutputStream blob = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 */
/* Ignored for PNGs *//*
, blob);
        byte[] bitmapdata = blob.toByteArray();
*/



        AttckRegisterViewHolder.userID.setText(hmProjectRegisterItem.get("Id"));
        AttckRegisterViewHolder.userName.setText(hmProjectRegisterItem.get("Name"));
        AttckRegisterViewHolder.userMobile.setText(hmProjectRegisterItem.get("PhoneNo"));
        AttckRegisterViewHolder.userEmail.setText(hmProjectRegisterItem.get("Email"));
       // AttckRegisterViewHolder.userImage.setImageBitmap(bitmapGet);


    }

    @Override
    public int getItemCount() {
        return alRegisterItems.size();
    }

}