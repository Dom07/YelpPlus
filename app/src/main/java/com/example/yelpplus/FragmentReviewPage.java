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
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentReviewPage.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentReviewPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentReviewPage extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String business_id;

    private BarChart barChart;
    private RecyclerView recyclerView;
    private ViewBusinessAdaptor adaptor;

    private OnFragmentInteractionListener mListener;

    public FragmentReviewPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentReviewPage.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentReviewPage newInstance(String param1, String param2) {
        FragmentReviewPage fragment = new FragmentReviewPage();
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
        final View rootView = inflater.inflate(R.layout.fragment_review_page, container, false);
        barChart = rootView.findViewById(R.id.bar_chart);
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<Business> call = service.getBusinessInformation(business_id);
        call.enqueue(new Callback<Business>() {
            @Override
            public void onResponse(Call<Business> call, Response<Business> response) {
                Business business = response.body();
                createGraph(business.getReview());
                generateDataList(business.getReview(), rootView);
            }

            @Override
            public void onFailure(Call<Business> call, Throwable t) {

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

    public void createGraph(List<Reviews_profile> reviews){
        int[] sum = new int[6];
        int[] count = new int[6];
        for(int i = 0; i<6;i++){
            sum[i] = 0;
            count[i] = 0;
        }

        for(int i = 0; i < reviews.size(); i++){
            String[] date = reviews.get(i).getDate().split("T");
            String[] month = date[0].split("-");
            Log.d("Month",""+month[1]);
            switch(month[1]) {
                case "06": {
                    sum[0] = sum[0] + (int) reviews.get(i).getProduct();
                    count[0]++;
                    break;
                }
                case "07": {
                    sum[1] = sum[1] + (int) reviews.get(i).getProduct();
                    count[1]++;
                    break;
                }
                case "08": {
                    sum[2] = sum[2] + (int) reviews.get(i).getProduct();
                    count[2]++;
                    break;
                }
                case "09": {
                    sum[3] = sum[3] + (int) reviews.get(i).getProduct();
                    count[3]++;
                    break;
                }
                case "10": {
                    sum[4] = sum[4] + (int) reviews.get(i).getProduct();
                    count[4]++;
                    break;
                }
                case "11": {
                    sum[5] = sum[5] + (int) reviews.get(i).getProduct();
                    count[5]++;
                    break;
                }
            }
        }

        List<BarEntry> barEntries = new ArrayList<>();
        for(int i =0; i< 6; i++){
            if(count[i]!=0) {
                barEntries.add(new BarEntry(i + 1, sum[i] / count[i]));
            }else{
                barEntries.add(new BarEntry(i+1, 0));
            }
            Log.d("COUNT",i+""+count[i]);
        }

        BarDataSet set = new BarDataSet(barEntries,  "BarDataSet");
        set.setColors(ColorTemplate.JOYFUL_COLORS);

        BarData data = new BarData(set);
        data.setBarWidth(0.9f);
        barChart.setData(data);
        barChart.setFitBars(true);
        barChart.invalidate();

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
        recyclerView = view.findViewById(R.id.rcv_review);
        adaptor = new ViewBusinessAdaptor(reviews, getContext(), business_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adaptor);
    }
}
