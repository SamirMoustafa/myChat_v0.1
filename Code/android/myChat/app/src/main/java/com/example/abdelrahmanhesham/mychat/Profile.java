package com.example.abdelrahmanhesham.mychat;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {
    TextView nameView;
    TextView emailView;
    TextView ageView;
    TextView phoneView;
    ImageView imageView;
    Button exit;
    String id;
    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        nameView = (TextView) view.findViewById(R.id.tv_name);
        emailView = (TextView) view.findViewById(R.id.tv_email);
        ageView = (TextView) view.findViewById(R.id.tv_age);
        imageView = (ImageView) view.findViewById(R.id.photo);
        phoneView = (TextView) view.findViewById(R.id.tv_phone);
        exit = (Button) view.findViewById(R.id.btn_logout);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        Intent intent = getActivity().getIntent();
        userAccount userAccount = (userAccount) intent.getSerializableExtra("data");
        String email = userAccount.getEMail();
        String photoUrl = "http://192.168.43.86/myChat/_android/_androidGetPhoto.php?email="+email;
        Picasso.with(getContext()).load(photoUrl).resize(200,200).into(imageView);
        String name = userAccount.getName();
        int age = (int)userAccount.getAge();
        String phone = userAccount.getPhone();
        nameView.setText("Name : " + name);
        emailView.setText("Email : " + email);
        ageView.setText("Age : " + age);
        phoneView.setText("Phone : " + phone);
        return view;
    }

}
