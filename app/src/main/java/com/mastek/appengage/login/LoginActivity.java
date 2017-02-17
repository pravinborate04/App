package com.mastek.appengage.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mastek.appengage.App;
import com.mastek.appengage.HomeActivity;
import com.mastek.appengage.MA;
import com.mastek.appengage.R;
import com.mastek.appengage.RetrofitManager;
import com.mastek.appengage.Tools;
import com.mastek.appengage.WebServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    Button btnLogin;
    WebServices webServices;
    EditText edtUserName,edtPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(App.getInstance().getLoginUser()!=null){
            Intent intent=new Intent(this,HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }



        setContentView(R.layout.activity_login);
        btnLogin=(Button)findViewById(R.id.btnLogin);

        edtUserName=(EditText)findViewById(R.id.edtUserName);
        edtPassword=(EditText)findViewById(R.id.edtPassword);



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!validate()){
                    return;
                }
                webServices= RetrofitManager.getInstance().call();
                Tools.showProgress(LoginActivity.this);
                webServices.getUserValidated(edtUserName.getText().toString(),edtPassword.getText().toString()).enqueue(new Callback<LoginResponseModel>() {
                    @Override
                    public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                        Tools.hideProgress();
                        Log.e("response",new Gson().toJson(response.body()));
                        if(response.body()!=null && !response.body().getMsg().equals("Failed")){

                            MA.setCustomAppUser(response.body().getLogin());
                            Toast.makeText(LoginActivity.this,"Sucesss",Toast.LENGTH_LONG).show();
                            App.getInstance().setLoginUser(response.body());
                            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                            finish();
                            Log.d("SharedPref",new Gson().toJson(App.getInstance().getLoginUser()));
                        }else {
                            Toast.makeText(LoginActivity.this,"Login Failed",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                        Tools.hideProgress();
                    }
                });
            }
        });
    }



    public boolean validate(){
        edtUserName.setError(null);
        edtPassword.setError(null);
        if(TextUtils.isEmpty(edtUserName.getText().toString())){
            edtUserName.setError("Please Enter Username");
            return false;
        }
        if(TextUtils.isEmpty(edtPassword.getText().toString())){
            edtPassword.setError("Please Enter Password");
            return false;
        }

        return true;
    }
}
