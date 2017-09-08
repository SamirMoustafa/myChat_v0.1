package com.example.abdelrahmanhesham.mychat;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Chat extends ListFragment {
    private RequestQueue requestQueue;
    String myEmail;
    String myName;
    ArrayList<String> emails = new ArrayList<>();
    ArrayList<String> images = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();
    public Chat() {
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(getActivity(),ConverstaionActivity.class);
        intent.putExtra("friend_name",names.get(position));
        intent.putExtra("friend_image",images.get(position));
        intent.putExtra("friend_email",emails.get(position));
        intent.putExtra("myEmail",myEmail);
        intent.putExtra("myName",myName);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        userAccount userAccount = (userAccount) intent.getSerializableExtra("data");
        myEmail = userAccount.getEMail();
        myName = userAccount.getName();
        requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST,"http://192.168.43.86/myChat/_messages/getAccounts.php",new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    //JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String email = jsonObject.getString("email");
                        String photoUrl = "http://192.168.43.86/myChat/_android/_androidGetPhoto.php?email="+email;
                        if (name.compareTo(myName)!=0) {
                            names.add(name);
                            images.add(photoUrl);
                            emails.add(email);
                        }
                    }
                    ChatSlider character = new ChatSlider(getActivity(), images, names);
                    setListAdapter(character);
                } catch (JSONException e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(jsonArrayRequest);
        return inflater.inflate(R.layout.fragment_my_chat, container, false);
    }

}
