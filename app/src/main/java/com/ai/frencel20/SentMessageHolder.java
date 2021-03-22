package com.ai.frencel20;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;

public class SentMessageHolder extends RecyclerView.ViewHolder {
    TextView messageText, timeText,date;

    SentMessageHolder(View itemView) {
        super(itemView);
        date=(TextView)itemView.findViewById(R.id.date_me);
        messageText = (TextView) itemView.findViewById(R.id.message_me);
        timeText = (TextView) itemView.findViewById(R.id.time_me);
    }

    void bind(HashMap<String,Object> message) {
        messageText.setText(message.get("message").toString());

        // Format the stored timestamp into a readable String using method.
        timeText.setText(message.get("time").toString());
    }
}