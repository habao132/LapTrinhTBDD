package com.example.myapplication.Activity;

import androidx.appcompat.app.ActionBar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.AcvitityExtra.LoadingDialog;
import com.example.myapplication.Constant.SystemConstant;
import com.example.myapplication.R;
import com.example.myapplication.model.User;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {
    private Button btnDK,btnHuy;
    private EditText user, pass, fullname, SDT,email;
    private AlertDialog.Builder builder;
    //tuong tu Mainactivity
    final LoadingDialog dialogS = new LoadingDialog(Registration.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Tạo tài khoản");




        init();
        //Khoi tao  AlertDialog
        builder = new AlertDialog.Builder(this);
        //Click nut dang ky
        btnDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Dieu Kien dang ky tai khoan
                if(TextUtils.isEmpty(user.getText().toString()) || TextUtils.isEmpty(pass.getText().toString())
                ||TextUtils.isEmpty(fullname.getText().toString()) || TextUtils.isEmpty(SDT.getText().toString())
                || TextUtils.isEmpty(email.getText().toString())){
                    Toast.makeText(Registration.this,"Bạn cần phải nhập đầy đủ thông tin",Toast.LENGTH_LONG).show();
                }
                else {
                    //Kiem tra dinh dang mail
                    if(!CheckMail(email.getText().toString())){
                        dialogS.startLoadingDialog();
                        resetText();
                        Toast.makeText(Registration.this,"Mail không hợp lệ",Toast.LENGTH_LONG).show();

                    }
                    //Kiem tra dinh dang username
                    else if(user.getText().length() <=6){
                        dialogS.startLoadingDialog();
                        resetText();
                        Toast.makeText(Registration.this,"Username phải có tối thiểu 6 ký tự",Toast.LENGTH_LONG).show();

                    }
                    //Kiem tra dinh dang password
                    else if(pass.getText().length() <=6){
                        dialogS.startLoadingDialog();
                        resetText();
                        Toast.makeText(Registration.this,"Password phải có tối thiểu 6 ký tự",Toast.LENGTH_LONG).show();

                    }
                    //Neu moi thong tin deu on
                    else{
                        builder.setMessage("Bạn có muốn đăng ký tài khoản này không ?").
                                setCancelable(false).
                                setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //Tien hanh anomation loading
                                        dialogS.startLoadingDialog();

                                        DangKy(SystemConstant.KEY_URL_Registration);
                                    }
                                })
                                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        Toast.makeText(Registration.this,"Không tạo tài khoản", Toast.LENGTH_LONG).show();
                                    }
                                });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.setTitle("Xác nhận tạo tài khoản");
                        alertDialog.show();

                    }

                }

            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void DangKy(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(Registration.this);
        //Tuong tu MainActivity
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String message = "";
                        try {
                            //Tuong tu main Activity
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getInt("success") == 1) {
                                User account = new User();
                                account.setUserName(jsonObject.getString("user_name"));
                                account.setEmail(jsonObject.getString("email"));
                                account.setFullname(jsonObject.getString("fullname"));
                                account.setSDT(jsonObject.getString("SDT"));

                                message = jsonObject.getString("message");
                                resetText();
                                Toast.makeText(Registration.this, message, Toast.LENGTH_LONG).show();

                                //Gui thong tin tai khoan mat khau ve Mainactivity sau khi dang ky
                                Intent intent = new Intent(Registration.this, MainActivity.class);
                                Bundle mBundle = new Bundle();
                                mBundle.putString("taikhoan",account.getUserName());
                                mBundle.putString("matkhau",account.getPassWord());
                                intent.putExtras(mBundle);
                                startActivity(intent);
                            } else {
                                message = jsonObject.getString("message");
                                Toast.makeText(Registration.this, message, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException error) {
                            error.getMessage();
                        } finally {
                            waitForLoading();
                        }
//                        if(response.trim().equals("Registration Successfully")) {
//                            Toast.makeText(Registration.this, "Registration Successfully", Toast.LENGTH_LONG).show();
//                            finish();
//                        }
//                        else if(response.trim().equals("failed")){
//                            Toast.makeText(Registration.this,"failed", Toast.LENGTH_LONG).show();
//                            resetText();
//                        }
//                        else if(response.trim().equals("User Already Exist")){
//                            Toast.makeText(Registration.this,"User Already Exist", Toast.LENGTH_LONG).show();
//                            resetText();
//                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Registration.this,"loi ket noi", Toast.LENGTH_LONG).show();
                dialogS.dismissDialog();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put(SystemConstant.KEY_USERNAME,user.getText().toString().trim());
                params.put(SystemConstant.KEY_PASSWORD,pass.getText().toString().trim());
                params.put(SystemConstant.KEY_FULLNAME,fullname.getText().toString().trim());
                params.put(SystemConstant.KEY_SDT,SDT.getText().toString().trim());
                params.put(SystemConstant.KEY_EMAIL,email.getText().toString().trim());
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }

    private void init(){
        btnDK = (Button) findViewById(R.id.btnDK);
        btnHuy = (Button) findViewById(R.id.btnHuy);
        user = (EditText) findViewById(R.id.txtUsername);
        pass = (EditText) findViewById(R.id.txtPassword);
        fullname = (EditText) findViewById(R.id.txtHovaTen);
        SDT = (EditText) findViewById(R.id.txtSDT);
        email = (EditText) findViewById(R.id.txtEmail);
    }

    private void resetText(){
        user.setText("");
        pass.setText("");
        fullname.setText("");
        SDT.setText("");
        email.setText("");
        waitForLoading();
    }

    //Method kiem tra mail
    private boolean CheckMail(String target) {
        if (target.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+"))
            return true;
        return false;
    }

    //Ket thuc dialog loading
    private void waitForLoading(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialogS.dismissDialog();
            }
        },2000);
    }
}
