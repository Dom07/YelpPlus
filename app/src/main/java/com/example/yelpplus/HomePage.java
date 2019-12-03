package com.example.yelpplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomePage extends AppCompatActivity
        implements Fragment_Home_Page.OnFragmentInteractionListener,
        Fragment_Search_View.OnFragmentInteractionListener,
        FragmentProfile.OnFragmentInteractionListener,
        FragmentViewBusiness.OnFragmentInteractionListener,
        FragmentWriteReview.OnFragmentInteractionListener,
        FragmentConfirmAction.OnFragmentInteractionListener,
        FragmentUploadPhoto.OnFragmentInteractionListener,
        FragmentBookEvent.OnFragmentInteractionListener,
        FragmentViewPhotos.OnFragmentInteractionListener,
        FragmentReviewPage.OnFragmentInteractionListener{

    public EditText et;
    boolean isLoggedIn;
    SharedPreferences pref;
    Menu menu;
    BottomNavigationView bvn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        checkStoragePermission();

        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.frame_layout_home, new Fragment_Home_Page());
        ft.commit();

        pref = getApplicationContext().getSharedPreferences("Authentication",0);
        isLoggedIn = pref.getBoolean("isLoggedIn", false);
        Log.d("LOGIN", "Login: "+pref.getBoolean("isLoggedIn", false));

        bvn = findViewById(R.id.bottom_navigation_view);
        bvn.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = null;
                switch (menuItem.getItemId()){
                    case R.id.menu_search:{
                        selectedFragment = new Fragment_Home_Page();
                        break;
                    }
                    case R.id.menu_profile:{
                        selectedFragment = new FragmentProfile();
                        break;
                    }
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_home, selectedFragment).commit();
                return true;
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
                    editor.remove("name");
                    editor.remove("user_Id");

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

    public void checkStoragePermission(){
        int result = getApplicationContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 10);
        }else{
            Log.d("READ STORAGE PERMISSION STATUS", "Granted");
        }

        result = getApplicationContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(result != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 11);
        }else{
            Log.d("WRITE STORAGE PERMISSION STATUS", "Granted");
        }
    }
}
