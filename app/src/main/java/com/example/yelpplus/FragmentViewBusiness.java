package com.example.yelpplus;

import android.content.Context;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.net.Inet4Address;
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

    private ImageView imageView;
    private String images[];

    private List<Reviews_profile> reviews;

    private TextView businessTitle;

    private RatingBar averageRating;
    private RatingBar serviceRating;
    private RatingBar productRating;
    private RatingBar ambienceRating;

    private TextView subCategories;

    private ListView informationList;
    private Boolean isOwner = false;
    private String userID;

    private ImageView owned_tick_mark;

    private TextView reviewNumbers;

    private Button reviewPageButton;

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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_view_business, container, false);

        imageView = rootView.findViewById(R.id.businessImages);

        businessTitle = rootView.findViewById(R.id.businessTitle);
        averageRating = rootView.findViewById(R.id.ratingBarAverage);
        serviceRating = rootView.findViewById(R.id.ratingBarService);
        productRating = rootView.findViewById(R.id.ratingBarProduct);
        ambienceRating = rootView.findViewById(R.id.ratingBarAmbience);

        owned_tick_mark = rootView.findViewById(R.id.owned_tick_mark);

        informationList = rootView.findViewById(R.id.informationList);

        reviewNumbers = rootView.findViewById(R.id.reviewNumbers);


        reviewPageButton = rootView.findViewById(R.id.btn_review_page);

        final SharedPreferences pref = getContext().getSharedPreferences("Authentication",0);
        userID = pref.getString("userId", "");

        final GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<Business> call = service.getBusinessInformation(business_id);
        call.enqueue(new Callback<Business>() {
            @Override
            public void onResponse(Call<Business> call, Response<Business> response) {
                final Business business = response.body();
                businessTitle.setText(business.getName());
                images = business.getPhoto();
                serviceRating.setRating(Integer.valueOf(business.getAvg_service_rating()));
                ambienceRating.setRating(Integer.valueOf(business.getAvg_ambience_rating()));
                productRating.setRating(Integer.valueOf(business.getAvg_product_rating()));;
                averageRating.setRating(Integer.valueOf(business.getAvg_rating()));

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle args = new Bundle();
                        args.putStringArray("images", images);
                        args.putString("businessName", business.getName());
                        FragmentViewPhotos fragment = new FragmentViewPhotos();
                        fragment.setArguments(args);
                        getActivity()
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frame_layout_home, fragment)
                                .addToBackStack(null)
                                .commit();
                    }
                });
                
                Picasso.Builder builder = new Picasso.Builder(getContext());
                builder.downloader(new OkHttp3Downloader(getContext()));
                builder.build().load(images[0])
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)
                        .into(imageView);


                if(business.getClaimed()){
                    owned_tick_mark.setVisibility(View.VISIBLE);
                }else{
                    owned_tick_mark.setVisibility(View.INVISIBLE);
                }

                reviewNumbers.setText(""+business.getReview().size());
                generateDataList(business.getReview(), rootView);

                //Logic to figure out what text to display in List View
                String displayAction = null;
                if (business.getClaimed()) {
                    if (business.getRegistered()) {
                        displayAction = "Available for event booking";
                    }
                    else {
                        if (!userID.isEmpty() && userID.equals(business.getOwner_id())) {
                            isOwner = true;
                            displayAction = "Register for event booking";
                        }
                        else {
                            displayAction = "Not Available for event booking";
                        }
                    }
                }
                else {
                    displayAction = "Claim business";
                }

                //Populate List View
                String[] listItems = {"Phone: " + business.getPhone_number(), "Address: " + business.getAddress(), displayAction, "Upload photos", "Write Review"};
                ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, listItems);
                informationList.setAdapter(adapter);

                //What to do when listView is clicked. Send user to appropriate page.
                informationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                        //Address. Go to Google Maps
                        if (position == 1)
                        {
                            String lat = business.getLatitude();
                            String lon = business.getLongitude();
                            if (lat.isEmpty() || lon.isEmpty())
                            {
                                lat = "40.737636";
                                lon = "-74.172404";
                            }
                            String uri = "http://maps.google.com/maps?&daddr=" + lat + "," + lon;
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                            startActivity(intent);
                        }
                        //Third listView with action items
                        if (position == 2)
                        {
                            //Arguments for confirm Action page
                            Bundle argsConfirmPage = new Bundle();
                            argsConfirmPage.putBoolean("claimed", business.getClaimed());
                            argsConfirmPage.putBoolean("registered", business.getRegistered());
                            argsConfirmPage.putBoolean("follow_reviewer", false);
                            argsConfirmPage.putString("business_id", business_id);
                            argsConfirmPage.putString("name", business.getName());
                            argsConfirmPage.putString("reviewer_id", null);

                            //Navigate to pages based on the text displayed in listView. Same if statement logic as before.
                            if (business.getClaimed()) {
                                if (business.getRegistered()) {
                                    Bundle args = new Bundle();
                                    args.putString("business_id", business_id);
                                    args.putString("name", business.getName());
                                    FragmentBookEvent fragment = new FragmentBookEvent();
                                    fragment.setArguments(args);
                                    getActivity()
                                            .getSupportFragmentManager()
                                            .beginTransaction()
                                            .replace(R.id.frame_layout_home, fragment)
                                            .addToBackStack(null)
                                            .commit();
                                }
                                else {
                                    if (pref.getString("userId", "").equals(business.getOwner_id()) ) {
                                        goToConfirmActionPage(argsConfirmPage);
                                    }
                                    else {
                                        //do nothing
                                    }
                                }
                            }
                            else {
                                goToConfirmActionPage(argsConfirmPage);
                            }
                        }

                        //Upload Photo
                        if (position == 3) {
                            Bundle args = new Bundle();
                            args.putString("business_id", business_id);
                            FragmentUploadPhoto fragment = new FragmentUploadPhoto();
                            fragment.setArguments(args);
                            getActivity()
                                    .getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.frame_layout_home, fragment)
                                    .addToBackStack(null)
                                    .commit();
                        }

                        //Write Review
                        if (position == 4){
                            if(pref.getBoolean("isLoggedIn", false)){
                                Bundle args = new Bundle();
                                args.putString("business_id", business_id);
                                args.putString("business_title", businessTitle.toString());
                                FragmentWriteReview fragment = new FragmentWriteReview();
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
                                Toast toast = Toast.makeText(getContext(), "You need to Log In to write review", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }
                    }
                });
            }
            @Override
            public void onFailure(Call<Business> call, Throwable t) {
                Log.e("ERROR", ""+t);
            }
        });
        reviewPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putString("business_id", business_id);
                FragmentReviewPage fragment = new FragmentReviewPage();
                fragment.setArguments(args);
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout_home, fragment)
                        .addToBackStack(null)
                        .commit();
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
        adaptor = new ViewBusinessAdaptor(reviews, getContext(), business_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adaptor);
    }

    // Go to confirm action fragment
    public void goToConfirmActionPage (Bundle args)
    {
        FragmentConfirmAction fragment = new FragmentConfirmAction();
        fragment.setArguments(args);
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout_home, fragment)
                .addToBackStack(null)
                .commit();
    }
}
