package com.example.abdelrahmanhesham.mychat;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Abdelrahman Hesham on 5/15/2017.
 */

public class MessagesAdapter extends BaseAdapter {
    ArrayList<Messages> messages;
    Context context;

    public MessagesAdapter(ArrayList<Messages> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }
    public void delete(){
        messages.clear();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chat_bubble, parent, false);
        }
        TextView msg = (TextView) convertView.findViewById(R.id.message_text);
        LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.bubble_layout);
        LinearLayout parent_layout = (LinearLayout) convertView.findViewById(R.id.bubble_layout_parent);
        if (messages.get(position).isMine()) {
            layout.setBackgroundResource(R.drawable.bubble2);
            parent_layout.setGravity(Gravity.RIGHT);
            parent_layout.setPadding(0,0,0,0);
        } else {
            layout.setBackgroundResource(R.drawable.bubble1);
            parent_layout.setGravity(Gravity.LEFT);
            parent_layout.setPadding(0,0,0,0);
        }
        msg.setTextColor(Color.BLACK);
        msg.setText(messages.get(position).getText());
        return convertView;
    }

    public void add (Messages object){
        messages.add(object);
    }
}
