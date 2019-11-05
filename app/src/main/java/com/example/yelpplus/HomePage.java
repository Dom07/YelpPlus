package com.example.yelpplus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;

public class HomePage extends AppCompatActivity
        implements Fragment_Business_List.OnFragmentInteractionListener,
        Fragment_Home_Page.OnFragmentInteractionListener,
        FragmentProfile.OnFragmentInteractionListener,
        Fragment_Search_View.OnFragmentInteractionListener{
    public EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout_home, new Fragment_Home_Page());
        ft.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
