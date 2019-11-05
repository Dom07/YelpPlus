package com.example.yelpplus;

import androidx.annotation.NonNull;
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

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePage extends AppCompatActivity
        implements Fragment_Home_Page.OnFragmentInteractionListener,
        Fragment_Search_View.OnFragmentInteractionListener{
    public EditText et;
    boolean isLoggedIn;
    SharedPreferences pref;
    Menu menu;
    BottomNavigationView bvn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout_home, new Fragment_Home_Page());
        ft.commit();

        pref = getApplicationContext().getSharedPreferences("Authentication",0);
        isLoggedIn = pref.getBoolean("isLoggedIn", false);
        Log.d("LOGIN", "Login: "+pref.getBoolean("isLoggedIn", false));

        bvn = findViewById(R.id.bottom_navigation_view);
        bvn.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.menu_search:{
                        ft.replace(R.id.frame_layout_home, new Fragment_Home_Page());
                        ft.commit();
                        break;
                    }
                    case R.id.menu_profile:{
//                        ft.replace(R.id.frame_layout_home, new FragmentProfile());
                        ft.commit();
                    }
                    case R.id.menu_nearby:{
//                        ft.replace(R.id.frame_layout_home, new Fragment_Nearby());
                        ft.commit();
                    }
                }
            }
        });
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
