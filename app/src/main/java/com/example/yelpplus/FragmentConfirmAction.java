package com.example.yelpplus;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentConfirmAction.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentConfirmAction#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentConfirmAction extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView title;
    private TextView warningText;
    private Button confirmYes;
    private Button confirmNo;

    private String warningMessage;
    private Boolean claimed;
    private Boolean registered;
    private String business_id;
    private String user_id;
    private String name;
    private Boolean followReviewer;
    private String reviewerID;

    private OnFragmentInteractionListener mListener;

    public FragmentConfirmAction() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentConfirmAction.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentConfirmAction newInstance(String param1, String param2) {
        FragmentConfirmAction fragment = new FragmentConfirmAction();
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
            claimed = getArguments().getBoolean("claimed");
            registered = getArguments().getBoolean("registered");
            followReviewer = getArguments().getBoolean("follow_reviewer");
            business_id = getArguments().getString("business_id");
            name = getArguments().getString("name");
            reviewerID = getArguments().getString("reviewer_id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_confirm_action, container, false);

        title = rootView.findViewById(R.id.confirmBusinessName);
        warningText = rootView.findViewById(R.id.confirmWarningText);
        confirmYes = rootView.findViewById(R.id.confirmYesButton);
        confirmNo = rootView.findViewById(R.id.confirmNoButton);



        final SharedPreferences pref = getContext().getSharedPreferences("Authentication",0);
        user_id = pref.getString("userId","");

        //Register Business for event booking
        if (!registered) {
            warningMessage = "Are you sure you want to register business for event booking?";
            title.setText(name);
            warningText.setText(warningMessage);

            confirmYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GetDataService dataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
                    Call<Business> call = dataService.registerBusiness(business_id);
                    call.enqueue(new Callback<Business>() {
                        @Override
                        public void onResponse(Call<Business> call, Response<Business> response) {
                            if(response.isSuccessful()) {
                                changeFragment(business_id);
                            }
                        }
                        @Override
                        public void onFailure(Call<Business> call, Throwable t) {
                            Toast toast = Toast.makeText(getContext(), "Failure", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    });
                }
            });
        }
        //Claim Business
        if (!claimed) {
            warningMessage = "Are you sure you want to claim the business";
            title.setText(name);
            warningText.setText(warningMessage);

            confirmYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GetDataService dataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
                    Call<Business> call = dataService.businessClaim(business_id, user_id);
                    call.enqueue(new Callback<Business>() {
                        @Override
                        public void onResponse(Call<Business> call, Response<Business> response) {
                            if(response.isSuccessful()) {
                                changeFragment(business_id);
                            }
                            else {
                                Toast toast = Toast.makeText(getContext(), "There was an error in claiming the business.", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Business> call, Throwable t) {
                            Toast toast = Toast.makeText(getContext(), "Failure", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    });
                }
            });
        }

        if (followReviewer)
        {
            warningMessage = "Do you want to follow the mentioned reviewer?";
            title.setText(name);
            warningText.setText(warningMessage);

            confirmYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GetDataService dataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
                    Call<User> call = dataService.followUser(user_id, reviewerID);
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if(response.isSuccessful()) {
                                changeFragment(business_id);
                            }
                            else {
                                Toast toast = Toast.makeText(getContext(), "There was an error in following the reviewer.", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast toast = Toast.makeText(getContext(), "Failure", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    });
                }
            });
        }

        confirmNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(business_id);
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

    //Switch to View Business page
    public void changeFragment (String business_id)
    {
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
}
