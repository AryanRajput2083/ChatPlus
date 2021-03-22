package com.ai.frencel20;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;

class ReceivedMessageHolder extends RecyclerView.ViewHolder {
    TextView messageText, timeText, nameText,date;
    ImageView profileImage;

    ReceivedMessageHolder(View itemView) {
        super(itemView);
        date=(TextView)itemView.findViewById(R.id.date_other);
        messageText = (TextView) itemView.findViewById(R.id.message_other);
        timeText = (TextView) itemView.findViewById(R.id.time_other);
        nameText = (TextView) itemView.findViewById(R.id.username_other);
        //profileImage = (ImageView) itemView.findViewById(R.id.image_gchat_profile_other);
    }

    void bind(HashMap<String,Object> message) {
        messageText.setText(message.get("message").toString());

        // Format the stored timestamp into a readable String using method.
        timeText.setText(message.get("time").toString());
        nameText.setText(message.get("name").toString());

        // Insert the profile image from the URL into the ImageView.
        //Utils.displayRoundImageFromUrl(mContext, message.getSender().getProfileUrl(), profileImage);
    }
}