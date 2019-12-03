package com.example.yelpplus;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentBookEvent.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentBookEvent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBookEvent extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //XML variables
    private TextView name;
    private EditText totalGuests;
    private Button dateButton;
    private Spinner timeDropDown;
    private ListView menuListView;
    private Button submitButton;

    private String[] finalMenu;
    private String[] databaseMenu;

    private String businessID;
    private String userID;
    private String businessName;

    private Calendar calendar;
    private DatePickerDialog datePicker;
    private Date currnetDate;

    private OnFragmentInteractionListener mListener;

    public FragmentBookEvent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentBookEvent.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentBookEvent newInstance(String param1, String param2) {
        FragmentBookEvent fragment = new FragmentBookEvent();
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
            businessID = getArguments().getString("business_id");
            businessName = getArguments().getString("business_name");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_event_booking, container, false);

        //Connect XML
        name = rootView.findViewById(R.id.businessNameBooking);
        totalGuests = rootView.findViewById(R.id.numberOfGuests);
        dateButton = rootView.findViewById(R.id.pickDate);
        timeDropDown = rootView.findViewById(R.id.timeOptions);
        menuListView = rootView.findViewById(R.id.menuList);
        submitButton = rootView.findViewById(R.id.eventBookingSubmit);

        final SharedPreferences pref = getContext().getSharedPreferences("Authentication",0);
        userID = pref.getString("userId", "");

        name.setText(businessName);

                final GetDataService dataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<Business> call1 = dataService.getEventBookingInfo(businessID);
        call1.enqueue(new Callback<Business>() {
            @Override
            public void onResponse(Call<Business> call, Response<Business> response) {
                final Business business = response.body();
                databaseMenu = business.getMenu();
                final List<EventBooking> events = business.getEvents();

                dateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        calendar = Calendar.getInstance();
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        int month = calendar.get(Calendar.MONTH);
                        int year = calendar.get(Calendar.YEAR);

                        datePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                                dateButton.setText((mMonth+1) + "/" + mDay + "/" + mYear);

                                List<String> spinnerList = new ArrayList<>();
                                spinnerList.add("Breakfast");
                                spinnerList.add("Lunch");
                                spinnerList.add("Dinner");
                                SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy");
                                try {
                                    currnetDate = formatter1.parse(dateButton.getText().toString());
                                    Log.d("Date", "Date from user: " + currnetDate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                for (EventBooking event : events)
                                {

                                    SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
                                    String[] dateStringSplits = event.getDate().toString().split("T");
                                    Date date = null;
                                    //Log.d("Date", "Date from database: " + );
                                    try {
                                        date = formatter2.parse(dateStringSplits[0]);
                                        Log.d("Date", "Date from database: " + date);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    if(date.equals(currnetDate))
                                    {
                                        if(spinnerList.contains(event.getTime()))
                                        {
                                            spinnerList.remove(event.getTime());
                                        }
                                    }
                                }
                                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, spinnerList);
                                timeDropDown.setAdapter(spinnerAdapter);

                            }
                        },year, month, day);
                        datePicker.show();
                    }
                });

                menuListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_multiple_choice, business.getMenu());
                menuListView.setAdapter(listAdapter);

            }

            @Override
            public void onFailure(Call<Business> call, Throwable t) {

            }
        });


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SparseBooleanArray booleanArray = menuListView.getCheckedItemPositions();
                ArrayList<String> itemLists = new ArrayList<String>();
                for (int i = 0; i < databaseMenu.length; i++)
                {
                    if (menuListView.isItemChecked(i))
                    {
                        itemLists.add(menuListView.getItemAtPosition(i).toString());
                    }
                }
                finalMenu = new String[itemLists.size()];
                for (int i = 0; i < itemLists.size(); i++)
                {
                    finalMenu[i] = itemLists.get(i);
                }
                String day = totalGuests.getText().toString();
                Call<EventBooking> call2 = dataService.postNewEvent(businessID, userID, dateButton.getText().toString(), timeDropDown.getSelectedItem().toString(), totalGuests.getText().toString(), finalMenu);
                call2.enqueue(new Callback<EventBooking>() {
                    @Override
                    public void onResponse(Call<EventBooking> call, Response<EventBooking> response) {
                        if(response.isSuccessful()) {
                            Bundle args = new Bundle();
                            args.putString("business_id", businessID);
                            FragmentViewBusiness fragment = new FragmentViewBusiness();
                            fragment.setArguments(args);
                            getActivity()
                                    .getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.frame_layout_home, fragment)
                                    .addToBackStack(null)
                                    .commit();
                        }
                        else {
                            Toast toast = Toast.makeText(getContext(), "There was an error in registering for the event", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<EventBooking> call, Throwable t) {
                        Toast toast = Toast.makeText(getContext(), "Failure", Toast.LENGTH_SHORT);
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
}
