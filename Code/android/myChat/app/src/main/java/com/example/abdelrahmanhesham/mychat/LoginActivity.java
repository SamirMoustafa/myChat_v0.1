package com.example.abdelrahmanhesham.mychat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText mEtEmail;
    private EditText mEtPassword;
    private Button mBtLogin;
    private TextView mTvRegister;
    private TextInputLayout mTiEmail;
    private TextInputLayout mTiPassword;
    private RequestQueue requestQueue;
    String email;
    String password;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");
        mEtEmail = (EditText) findViewById(R.id.et_email);
        mEtPassword = (EditText) findViewById(R.id.et_password);
        mBtLogin = (Button) findViewById(R.id.btn_login);
        mTiEmail = (TextInputLayout) findViewById(R.id.ti_email);
        mTiPassword = (TextInputLayout) findViewById(R.id.ti_password);
        mTvRegister = (TextView) findViewById(R.id.tv_register);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        mBtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTiEmail.setErrorEnabled(false);
                mTiPassword.setErrorEnabled(false);
                email = mEtEmail.getText().toString();
                password = mEtPassword.getText().toString();
                if (!validateEmail(email)) {
                    mTiEmail.setError("Email should be valid !");
                }

                if (!validateFields(password)) {
                    mTiPassword.setError("Password should not be empty !");
                }
                if (!validateEmail(email) && !validateFields(password)) {
                    showSnackBarMessage("Enter Valid Details !");
                }
                if (validateEmail(email) && validateFields(password)) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.43.86/myChat/_android/_androidConnection.php", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = (JSONObject) jsonArray.get(0);
                                String name = jsonObject.getString("name");
                                double age = jsonObject.getDouble("age");
                                String id = jsonObject.getString("_id");
                                String phone = jsonObject.getString("phone");
                                String email = jsonObject.getString("email");
                                String password = jsonObject.getString("password");
                                String photo = jsonObject.getString("picture");
                                userAccount userAccount = new userAccount(name, age, id, phone, email, password, photo);
                                Intent intent = new Intent(getApplicationContext(), MyAccount.class);
                                intent.putExtra("data",userAccount);
                                startActivity(intent);
                            } catch (JSONException e) {
                                showSnackBarMessage("Wrong Data");
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            showSnackBarMessage("Connection Error");
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("email", email);
                            parameters.put("password", password);
                            return parameters;
                        }
                    };
                    requestQueue.add(stringRequest);
                    mEtEmail.setText("");
                    mEtPassword.setText("");
                    mEtPassword.clearFocus();
                    mEtEmail.clearFocus();
                }
            }
        });
        mTvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showSnackBarMessage(String message) {

        if (findViewById(android.R.id.content) != null) {

            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
        }
    }

    public static boolean validateFields(String name) {

        if (TextUtils.isEmpty(name)) {

            return false;

        } else {

            return true;
        }
    }

    public static boolean validateEmail(String string) {

        if (TextUtils.isEmpty(string) || !Patterns.EMAIL_ADDRESS.matcher(string).matches()) {

            return false;

        } else {

            return true;
        }
    }
}
