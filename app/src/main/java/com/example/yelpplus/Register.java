package com.example.yelpplus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    public EditText ETRegisterFirstName;
    public EditText ETRegisterLastName;
    public EditText ETRegisterEmailId;
    public EditText ETRegisterPassword;
    public Button BtnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ETRegisterFirstName = findViewById(R.id.ETRegisterFirstName);
        ETRegisterLastName = findViewById(R.id.ETRegisterLastName);
        ETRegisterEmailId = findViewById(R.id.ETRegisterEmailId);
        ETRegisterPassword = findViewById(R.id.ETRegisterPassword);

        BtnRegister = findViewById(R.id.btnRegister);
        BtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(v);
            }
        });
    }

    public void register(View v){
        String first_name = ETRegisterFirstName.getText().toString().trim();
        String last_name = ETRegisterLastName.getText().toString().trim();
        String emailId = ETRegisterEmailId.getText().toString().trim();
        String password = ETRegisterPassword.getText().toString().trim();
        if(first_name != "" && last_name != "" && emailId != "" && password != ""){
            GetDataService dataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<User> call = dataService.registerUser(first_name, last_name, emailId, password);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful() && response.code() == 200){
                        User user = response.body();
                        if(user.getAuthenticationStatus() == null){
                            SharedPreferences pref = getApplicationContext().getSharedPreferences("Authentication", 0);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putBoolean("isLoggedIn", true);
                            editor.putString("emailId", user.getEmailId());
                            editor.putString("name", user.getFirst_name()+" "+user.getLast_name());
                            editor.putString("userId", user.getUser_id());
                            editor.commit();
                            Toast.makeText(getApplicationContext(), "Logged In", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Register.this, HomePage.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(), "User already exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
        }else{
            Toast.makeText(getApplicationContext(), "No fields can be empty", Toast.LENGTH_SHORT);
        }
    }
}
