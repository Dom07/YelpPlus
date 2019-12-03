package com.example.yelpplus;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CompoundButton;
import android.widget.SearchView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_Search_View.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment_Search_View#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Search_View extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String search_word;
    private String category_id;

    private List<Business> businesses;

    private ToggleButton tb_event_booking;
    private ToggleButton tb_sort_price;

    private SearchView searchView;
    private ViewListOfBusinessAdaptor adaptor;
    private RecyclerView recyclerView;

    private OnFragmentInteractionListener mListener;

    public Fragment_Search_View() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Search_View.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Search_View newInstance(String param1, String param2) {
        Fragment_Search_View fragment = new Fragment_Search_View();
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
            category_id = getArguments().getString("category_id");
            search_word = getArguments().getString("search_word");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_search_view, container, false);

        tb_event_booking = rootView.findViewById(R.id.tb_event_booking);
        tb_sort_price = rootView.findViewById(R.id.tb_sort_price);

        if(category_id!=null){
            getDataByCategory(rootView, category_id);
        }
        if(search_word!=null){
            getDataBySearch(rootView, search_word);
        }

        tb_event_booking.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    tb_sort_price.setChecked(false);
                    List<Business> eventBusiness = new ArrayList<>();
                    for(int i = 0; i < businesses.size(); i++){
                        if(businesses.get(i).getRegistered()){
                            eventBusiness.add(businesses.get(i));
                        }
                    }
                    generateDataList(eventBusiness, rootView);
                }else{
                    generateDataList(businesses, rootView);
                }
            }
        });

        tb_sort_price.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    tb_event_booking.setChecked(false);
                    List<Business> tempBusiness = businesses;
                    Collections.sort(tempBusiness, new Comparator<Business>() {
                        @Override
                        public int compare(Business b1, Business b2) {
                            return Integer.valueOf(b1.getAvg_price_rating()).compareTo(Integer.valueOf(b2.getAvg_price_rating()));
                        }
                    });
                    generateDataList(tempBusiness, rootView);
                }else{
                    generateDataList(businesses, rootView);
                }
            }
        });

        searchView = rootView.findViewById(R.id.fragment_search_view);
        searchView.setQuery(search_word, false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                getDataBySearch(rootView, s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
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

    private void getDataByCategory(final View rootView, String category_id){
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<Business>> call = service.getBusinessByCategory(category_id);
        call.enqueue(new Callback<List<Business>>() {
            @Override
            public void onResponse(Call<List<Business>> call, Response<List<Business>> response) {
                businesses = response.body();
                generateDataList(businesses, rootView);
            }

            @Override
            public void onFailure(Call<List<Business>> call, Throwable t) {
                Log.e("View by Category Error", ""+t);
            }
        });
    }

    private  void getDataBySearch(final View rootView, String word){
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<Business>> call = service.getBusinessBySearch(word);
        call.enqueue(new Callback<List<Business>>() {
            @Override
            public void onResponse(Call<List<Business>> call, Response<List<Business>> response) {
                businesses = response.body();
                generateDataList(businesses, rootView);
            }

            @Override
            public void onFailure(Call<List<Business>> call, Throwable t) {
                Log.e("SEARCH ERROR", ""+t);
            }
        });
    }

    private void generateDataList(List<Business> businesses, View view){
        recyclerView = view.findViewById(R.id.business_search_list_rcv);
        adaptor = new ViewListOfBusinessAdaptor(businesses, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adaptor);
    }
}
