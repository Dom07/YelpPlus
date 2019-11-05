package com.example.yelpplus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class HomePage extends AppCompatActivity
        implements Fragment_Home_Page.OnFragmentInteractionListener,
        Fragment_Search_View.OnFragmentInteractionListener{
    public EditText et;
    boolean isLoggedIn;
    SharedPreferences pref;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout_home, new Fragment_Home_Page());
        ft.commit();

        pref = getApplicationContext().getSharedPreferences("Authentication",0);
        isLoggedIn = pref.getBoolean("isLoggedIn", false);
        Log.d("LOGIN", "Login: "+pref.getBoolean("isLoggedIn", false));
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_menu, menu);
        this.menu = menu;
        if(isLoggedIn){
            menu.findItem(R.id.menu_login).setTitle("Logout");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_login:{
                if(isLoggedIn){
                    Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_SHORT).show();
                    menu.findItem(R.id.menu_login).setTitle("Login");
                    SharedPreferences.Editor editor = pref.edit();
                    editor.remove("isLoggedIn");
                    editor.remove("emailId");
                    editor.commit();
                    isLoggedIn = false;
                }else{
                    Intent intent = new Intent(HomePage.this, Login.class);
                    startActivity(intent);
                }
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
