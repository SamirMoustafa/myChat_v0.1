package com.example.abdelrahmanhesham.mychat;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Abdelrahman Hesham on 4/30/2017.
 */

public class ChatSlider extends BaseAdapter {
    Context context;
    ArrayList<String> images;
    ArrayList<String> names;

    public ChatSlider(Context context,ArrayList<String> images, ArrayList<String> names) {
        this.context=context;
        this.images = images;
        this.names = names;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        return names.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item,parent,false);
        }
        ImageView image = (ImageView) convertView.findViewById(R.id.image_view);
        Picasso.with(context).load(images.get(position)).resize(200,200).into(image);
        TextView textView = (TextView) convertView.findViewById(R.id.name);
        textView.setText(names.get(position));
        return convertView;
    }
}
