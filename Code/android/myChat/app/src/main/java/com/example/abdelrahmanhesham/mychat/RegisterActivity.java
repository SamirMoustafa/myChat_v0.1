package com.example.abdelrahmanhesham.mychat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.example.abdelrahmanhesham.mychat.LoginActivity.validateEmail;
import static com.example.abdelrahmanhesham.mychat.LoginActivity.validateFields;

public class RegisterActivity extends AppCompatActivity {
    private EditText mEtName;
    private EditText mEtEmail;
    private EditText mEtPassword;
    private EditText mEtAge;
    private EditText mEtMobileNumber;
    Button mBtRegister;
    TextView mTvLogin;
    String name;
    String email;
    String password;
    String age;
    String mobileNumber;
    private TextInputLayout mTiName;
    private TextInputLayout mTiEmail;
    private TextInputLayout mTiPassword;
    private TextInputLayout mTiAge;
    private TextInputLayout mTiMobileNumber;
    private com.android.volley.RequestQueue requestQueue;
    Bitmap bitmap;
    private ImageView imageView;
    Uri filePath;
    private final int PICK_IMAGE_REQUEST = 1;
    public static final String UPLOAD_KEY = "image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mEtName = (EditText) findViewById(R.id.et_name);
        mEtAge = (EditText) findViewById(R.id.et_age);
        mEtMobileNumber = (EditText) findViewById(R.id.et_mobile);
        mEtEmail = (EditText) findViewById(R.id.et_email);
        mEtPassword = (EditText) findViewById(R.id.et_password);
        mBtRegister = (Button) findViewById(R.id.btn_register);
        mTvLogin = (TextView) findViewById(R.id.tv_login);
        mTiName = (TextInputLayout) findViewById(R.id.ti_name);
        mTiEmail = (TextInputLayout) findViewById(R.id.ti_email);
        mTiPassword = (TextInputLayout) findViewById(R.id.ti_password);
        mTiAge = (TextInputLayout) findViewById(R.id.ti_age);
        mTiMobileNumber = (TextInputLayout) findViewById(R.id.ti_mobile);
        imageView = (ImageView) findViewById(R.id.profile_photo);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        bitmap = drawable.getBitmap();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
        mBtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTiEmail.setErrorEnabled(false);
                mTiPassword.setErrorEnabled(false);
                mTiName.setErrorEnabled(false);
                name = mEtName.getText().toString();
                email = mEtEmail.getText().toString();
                password = mEtPassword.getText().toString();
                age = mEtAge.getText().toString();
                mobileNumber = mEtMobileNumber.getText().toString();
                if (!validateFields(name)) {
                    mTiName.setError("Name should not be empty !");
                }

                if (!validateEmail(email)) {
                    mTiEmail.setError("Email should be valid !");
                }

                if (!validateFields(password)) {
                    mTiPassword.setError("Password should not be empty !");
                }
                if (!validateFields(age)) {
                    mTiAge.setError("Age should not be empty !");
                }

                if (!validateFields(mobileNumber)) {
                    mTiMobileNumber.setError("Mobile number should not be empty !");
                }
                if (!validateFields(name) && !validateEmail(email) && !validateFields(password)&&!validateFields(age)&&!validateFields(mobileNumber)) {

                    showSnackBarMessage("Enter Valid Details !");
                }
                if (validateFields(name) && validateEmail(email) && validateFields(password)&&validateFields(age)&&validateFields(mobileNumber)) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.43.86/myChat/_android/_androidNewAcc.php", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.trim().equals("Done")){
                                uploadImage();
                            }
                            showSnackBarMessage(response);
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
                            parameters.put("name", name);
                            parameters.put("age",age);
                            parameters.put("email", email);
                            parameters.put("password", password);
                            parameters.put("mobile",mobileNumber);
                            return parameters;
                        }
                    };
                    requestQueue.add(stringRequest);
                    mEtEmail.setText("");
                    mEtName.setText("");
                    mEtPassword.setText("");
                    mEtMobileNumber.setText("");
                    mEtAge.setText("");
                    mEtMobileNumber.clearFocus();
                    mEtAge.clearFocus();
                    mEtPassword.clearFocus();
                    mEtEmail.clearFocus();
                    mEtName.clearFocus();
                    imageView.setImageResource(R.drawable.no_avatar);
                }
            }
        });
        mTvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    private void uploadImage() {
        class UploadImage extends AsyncTask<Bitmap, Void, String> {

            RequestHandler rh = new RequestHandler();


            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String, String> data = new HashMap<>();
                data.put(UPLOAD_KEY, uploadImage);
                data.put("email",email);
                String result = rh.sendPostRequest("http://192.168.43.86/myChat/_android/_androidSetPhoto.php",data);
                return result;
            }
        }
        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }
    private void showSnackBarMessage(String message) {

        if (findViewById(android.R.id.content) != null) {

            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
