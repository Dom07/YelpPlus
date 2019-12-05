package com.example.yelpplus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentWriteReview.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentWriteReview#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentWriteReview extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String business_id;
    private String user_id;
    private String business_title;

    private TextView businessName;

    private EditText serviceRating;
    private EditText productRating;
    private EditText ambienceRating;
    private EditText priceRating;

    private TextInputEditText reviewTitle;
    private TextInputEditText reviewDescription;

    private Button submitReview;

    private String userServiceRating;
    private String userProductRating;
    private String userAmbienceRating;
    private String userPriceRating;
    private String userReviewTitle;
    private String userReviewDescription;

    private OnFragmentInteractionListener mListener;


    public FragmentWriteReview() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentWriteReview.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentWriteReview newInstance(String param1, String param2) {
        FragmentWriteReview fragment = new FragmentWriteReview();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            business_id = getArguments().getString("business_id");
            business_title = getArguments().getString("buisness_title");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_write_review, container, false);

        businessName = rootView.findViewById(R.id.writeReviewBusinessName);
        serviceRating = rootView.findViewById(R.id.writeServiceRating);
        productRating = rootView.findViewById(R.id.writeProductRating);
        ambienceRating = rootView.findViewById(R.id.writeAmbienceRating);
        priceRating = rootView.findViewById(R.id.writePriceRating);

        reviewTitle = rootView.findViewById(R.id.writeReviewTitle);
        reviewDescription = rootView.findViewById(R.id.writeReviewDescription);

        submitReview = rootView.findViewById(R.id.submitReview);

        SharedPreferences pref = getContext().getSharedPreferences("Authentication",0);
        if(pref.getBoolean("isLoggedIn", false)){
            user_id = pref.getString("userId", "");
        }
        businessName.setText(business_title);
        serviceRating.setText("0");
        productRating.setText("0");
        ambienceRating.setText("0");
        priceRating.setText("0");

        submitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userServiceRating = serviceRating.getText().toString().trim();
                userProductRating = productRating.getText().toString().trim();
                userAmbienceRating = ambienceRating.getText().toString().trim();
                userPriceRating = priceRating.getText().toString().trim();
                userReviewTitle = reviewTitle.getText().toString().trim();
                userReviewDescription = reviewDescription.getText().toString().trim();

                GetDataService dataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
                Call<Reviews_profile> call = dataService.postNewReview(business_id, user_id, userServiceRating, userProductRating, userAmbienceRating, userPriceRating,  userReviewTitle, userReviewDescription);
                call.enqueue(new Callback<Reviews_profile>() {
                    @Override
                    public void onResponse(Call<Reviews_profile> call, Response<Reviews_profile> response) {
                        if(response.code()== 200)
                        {
                            Toast toast = Toast.makeText(getContext(), "Review has been successfully created.", Toast.LENGTH_LONG);
                            toast.show();
                            Bundle args = new Bundle();
                            args.putString("business_id", business_id);
                            FragmentViewBusiness fragment = new FragmentViewBusiness();
                            fragment.setArguments(args);
                            getActivity()
                                    .getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.frame_layout_home, fragment)
                                    .addToBackStack(null)
                                    .commit();
                        }
                        else
                        {
                            Toast toast = Toast.makeText(getContext(), "There was error in creating the review.", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Reviews_profile> call, Throwable t) {
                        Toast toast = Toast.makeText(getContext(), "Failure", Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
            }
        });

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if(data!=null && data.getData()!=null){
//            try{
//                Uri uri = data.getData();
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
//                switch (requestCode){
//                    case PICK_IMAGE_REQUEST_ONE:{
//                        upload_image_one.setImageBitmap(bitmap);
//                        break;
//                    }
//                    case PICK_IMAGE_REQUEST_TWO:{
//                        upload_image_two.setImageBitmap(bitmap);
//                        break;
//                    }
//                    case PICK_IMAGE_REQUEST_THREE:{
//                        upload_image_three.setImageBitmap(bitmap);
//                        break;
//                    }
//                    case PICK_IMAGE_REQUEST_FOUR:{
//                        upload_image_four.setImageBitmap(bitmap);
//                        break;
//                    }
//                }
//            }catch (IOException e){
//                Log.e("Bitmap ERROR",""+e);
//            }
//        }
    }
}
