package com.example.abdelrahmanhesham.mychat;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConverstaionActivity extends AppCompatActivity {
    ListView messages;
    String myEmail;
    String friendEmail;
    MessagesAdapter messagesAdapter;
    EditText textMessage;
    private RequestQueue requestQueue;
    ImageButton sendButton;
    ArrayList<Messages> chatMessages = new ArrayList<>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_items, menu);
        Drawable yourdrawable = menu.getItem(0).getIcon();
        yourdrawable.mutate();
        yourdrawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.refresh) {
            messagesAdapter.delete();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.43.86/myChat/_messages/receive.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String senderEmail=jsonObject.getString("sender");
                            String message=jsonObject.getString("text");
                            if (senderEmail.trim().equals(myEmail)){
                                chatMessages.add(new Messages(true,message));
                                messagesAdapter.notifyDataSetChanged();
                            }
                            else {
                                chatMessages.add(new Messages(false,message));
                                messagesAdapter.notifyDataSetChanged();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parameters = new HashMap<String, String>();
                    parameters.put("sender", myEmail);
                    parameters.put("receiver", friendEmail);
                    return parameters;
                }
            };
            requestQueue.add(stringRequest);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        setContentView(R.layout.activity_converstaion);
        Intent intent = getIntent();
        final String name = intent.getStringExtra("friend_name");
        String image = intent.getStringExtra("friend_image");
        myEmail = intent.getStringExtra("myEmail");
        friendEmail = intent.getStringExtra("friend_email");
        final String myName = intent.getStringExtra("myName");
        messages = (ListView) findViewById(R.id.messages);
        textMessage = (EditText) findViewById(R.id.text_message);
        sendButton = (ImageButton) findViewById(R.id.send_buuton);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.43.86/myChat/_messages/receive.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String senderEmail=jsonObject.getString("sender");
                        String message=jsonObject.getString("text");
                        if (senderEmail.trim().equals(myEmail)){
                            chatMessages.add(new Messages(true,message));
                            messagesAdapter.notifyDataSetChanged();
                        }
                        else {
                            chatMessages.add(new Messages(false,message));
                            messagesAdapter.notifyDataSetChanged();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("sender", myEmail);
                parameters.put("receiver", friendEmail);
                return parameters;
            }
        };
        requestQueue.add(stringRequest);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!textMessage.getText().toString().trim().isEmpty()) {
                    final String message = textMessage.getText().toString();
                    messagesAdapter.add(new Messages(true, message));
                    messagesAdapter.notifyDataSetChanged();
                    textMessage.setText("");
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.43.86/myChat/_messages/send.php", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("sender", myEmail);
                            parameters.put("receiver", friendEmail);
                            parameters.put("senderName", myName);
                            parameters.put("receiverName", name);
                            parameters.put("message", message);
                            return parameters;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
            }
        });
        messages.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        messages.setStackFromBottom(true);
        android.support.v7.app.ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.action_bar, null);
        TextView textView = (TextView) mCustomView.findViewById(R.id.name);
        textView.setText(name);
        ImageView imageView = (ImageView) mCustomView.findViewById(R.id.image_view);
        Picasso.with(getApplicationContext()).load(image).resize(200,200).into(imageView);
        ImageButton imageView1 = (ImageButton) mCustomView.findViewById(R.id.back_button);
        imageView1.setColorFilter(Color.argb(255, 255, 255, 255));
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mActionBar.setCustomView(mCustomView);
        textMessage.clearFocus();
        mActionBar.setDisplayShowCustomEnabled(true);
        Toolbar parent = (Toolbar) mCustomView.getParent();
        parent.setPadding(0, 0, 0, 0);
        parent.setContentInsetsAbsolute(0, 0);
        messagesAdapter = new MessagesAdapter(chatMessages, getApplicationContext());
        messages.setAdapter(messagesAdapter);

    }
}
