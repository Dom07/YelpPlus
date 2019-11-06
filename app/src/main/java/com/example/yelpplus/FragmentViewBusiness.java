package com.example.yelpplus;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentViewBusiness.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentViewBusiness#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentViewBusiness extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String business_id;

    private TextView businessTitle;

    private RatingBar averageRating;
    private RatingBar serviceRating;
    private RatingBar productRating;
    private RatingBar ambienceRating;

    private TextView subCategories;

    private TextView phoneNumber;
    private TextView address;

    private ImageView owned_tick_mark;
    private Button btn_claim_busniess;

    private TextView reviewNumbers;
    private Button writeReviewButton;
    private RecyclerView recyclerView;

    private ViewBusinessAdaptor adaptor;

    private OnFragmentInteractionListener mListener;

    public FragmentViewBusiness() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentViewBusiness.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentViewBusiness newInstance(String param1, String param2) {
        FragmentViewBusiness fragment = new FragmentViewBusiness();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_view_business, container, false);

        businessTitle = rootView.findViewById(R.id.businessTitle);
        averageRating = rootView.findViewById(R.id.ratingBarAverage);
        serviceRating = rootView.findViewById(R.id.ratingBarService);
        productRating = rootView.findViewById(R.id.ratingBarProduct);
        ambienceRating = rootView.findViewById(R.id.ratingBarAmbience);

        owned_tick_mark = rootView.findViewById(R.id.owned_tick_mark);
        btn_claim_busniess = rootView.findViewById(R.id.btn_claim_business);

        phoneNumber = rootView.findViewById(R.id.phoneNumber);
        address = rootView.findViewById(R.id.address);

        reviewNumbers = rootView.findViewById(R.id.reviewNumbers);

        final GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<Business> call = service.getBusinessInformation(business_id);
        call.enqueue(new Callback<Business>() {
            @Override
            public void onResponse(Call<Business> call, Response<Business> response) {
                final Business business = response.body();
                businessTitle.setText(business.getName());
                phoneNumber.setText(business.getPhone_number());
                address.setText(business.getAddress());
                Log.d("REVIEWS",""+business.getReview().size());

                if(business.getClaimed()){
                    owned_tick_mark.setVisibility(View.VISIBLE);
                    btn_claim_busniess.setVisibility(View.INVISIBLE);
                }else{
                    owned_tick_mark.setVisibility(View.INVISIBLE);
                    btn_claim_busniess.setVisibility(View.VISIBLE);
                }

                btn_claim_busniess.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferences pref = getContext().getSharedPreferences("Authentication",0);
                        if(pref.getBoolean("isLoggedIn", false)){
                            owned_tick_mark.setVisibility(View.VISIBLE);
                            btn_claim_busniess.setVisibility(View.INVISIBLE);
                            String email_id = pref.getString("emailId", "Empty");
                            Call<Business> call = service.claimBusiness(business.getBusiness_id(), email_id);
                            call.enqueue(new Callback<Business>() {
                                @Override
                                public void onResponse(Call<Business> call, Response<Business> response) {
                                    if(response.isSuccessful()){
                                        Toast.makeText(getContext(), "You have claimed the business", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getContext(), "Something went wrong. Please try again later", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Business> call, Throwable t) {
                                    Log.e("CLAIM ERROR", ""+t);
                                }
                            });
                        }else{
                            Toast.makeText(getContext(),"You need to be logged in to perform this action", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                reviewNumbers.setText(""+business.getReview().size());
                generateDataList(business.getReview(), rootView);
            }

            @Override
            public void onFailure(Call<Business> call, Throwable t) {
                Log.e("ERROR", ""+t);
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

    private void generateDataList (List<Reviews_profile> reviews, View view)
    {
        recyclerView = view.findViewById(R.id.reviewListRcv);
        adaptor = new ViewBusinessAdaptor(reviews, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adaptor);
    }
}
