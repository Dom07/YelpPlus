package com.example.yelpplus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    // Variable initialization
    public EditText ETLoginEmail;
    public EditText ETLoginPassword;
    public TextView TVLoginRegisterLink;
    public Button btnLoginSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Declaring the variables
        // Edit Texts
        ETLoginEmail = findViewById(R.id.ETLoginEmail);
        ETLoginPassword = findViewById(R.id.ETLoginPassword);

        // Text Views
        TVLoginRegisterLink = findViewById(R.id.TVLoginRegisterLink);
        TVLoginRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        // Buttons
        btnLoginSignIn = findViewById(R.id.btnLogin);
        btnLoginSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }
        });
    }

    public void login(View v){
        String email = ETLoginEmail.getText().toString().trim();
        String password = ETLoginPassword.getText().toString().trim();
        if(email != "" && password != ""){
            GetDataService dataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<User> call = dataService.getUser(email, password);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful() && response.code() == 200) {
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
                            Intent intent = new Intent(Login.this, HomePage.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(), "User not found,\n Please try again!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                        Log.e("Error", t.toString());
                }
            });
        }else{
            Toast.makeText(getApplicationContext(), "No fields can be empty", Toast.LENGTH_SHORT).show();
        }
    }
}
