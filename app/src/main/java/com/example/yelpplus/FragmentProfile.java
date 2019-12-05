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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentProfile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProfile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView tv_user_name;
    private TextView tv_email_id;
    private TextView tv_login_message;
    private LinearLayout linear_layout_profile;
    private TextView tv_label_your_review;
    private TextView followingLabel;
    private TextView following;

    private ListOfEventsAdaptor adaptor;
    private RecyclerView recyclerView;

    private OnFragmentInteractionListener mListener;

    public FragmentProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentProfile newInstance(String param1, String param2) {
        FragmentProfile fragment = new FragmentProfile();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        tv_login_message = rootView.findViewById(R.id.tv_login_message);
        tv_user_name = rootView.findViewById(R.id.tv_user_name);
        tv_email_id = rootView.findViewById(R.id.tv_user_email_id);
        linear_layout_profile = rootView.findViewById(R.id.linear_layout_profile);
        tv_label_your_review = rootView.findViewById(R.id.tv_label_your_review);
        followingLabel = rootView.findViewById(R.id.profileFollowingLabel);
        following = rootView.findViewById(R.id.profileFollowing);


        SharedPreferences pref = getContext().getSharedPreferences("Authentication",0);
        if(pref.getBoolean("isLoggedIn", false)){
            tv_login_message.setVisibility(View.INVISIBLE);
            linear_layout_profile.setVisibility(View.VISIBLE);
            tv_label_your_review.setVisibility(View.VISIBLE);
            String username = pref.getString("name", "username");
            String email_id = pref.getString("emailId", "email");
            tv_user_name.setText(username);
            tv_email_id.setText(email_id);
            getEvents(rootView, pref.getString("userId",""));
            getFollowing(pref.getString("userId",""));
        }else{
            tv_login_message.setVisibility(View.VISIBLE);
            linear_layout_profile.setVisibility(View.INVISIBLE);
            tv_label_your_review.setVisibility(View.INVISIBLE);
            followingLabel.setVisibility(View.INVISIBLE);
            following.setVisibility(View.INVISIBLE);
        }
        return rootView;
    }

    private void getEvents(final View rootView, String user_id) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<EventBooking>> call = service.getUserEvents(user_id);
        call.enqueue(new Callback<List<EventBooking>>() {
            @Override
            public void onResponse(Call<List<EventBooking>> call, Response<List<EventBooking>> response) {
                List<EventBooking> events = response.body();
                generateDataList(events, rootView);
            }

            @Override
            public void onFailure(Call<List<EventBooking>> call, Throwable t) {

            }
        });
    }

    private void getFollowing(String user_id) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<User> call = service.getFollowers(user_id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User userData = response.body();
                List<User> followingList = userData.getFollowing();
                int i = 0;
                for (User follower:followingList) {
                    if (i == 0) {
                        following.setText(follower.getFirst_name() + " " + follower.getLast_name());
                        i++;
                    }
                    else {
                        following.append(System.getProperty("line.separator"));
                        following.append(follower.getFirst_name() + " " + follower.getLast_name());
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
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

    private void generateDataList(List<EventBooking> events, View view){
        recyclerView = view.findViewById(R.id.rcv_booked_event_list);
        adaptor = new ListOfEventsAdaptor(events, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adaptor);
    }
}
