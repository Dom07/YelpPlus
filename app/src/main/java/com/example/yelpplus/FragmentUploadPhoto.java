package com.example.yelpplus;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentUploadPhoto.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentUploadPhoto#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentUploadPhoto extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private final int PICK_IMAGE_REQUEST = 1;

    private ImageView iv_upload_image;
    private Button btn_upload_image;
    private String mediaPath;
    private String business_id;
    private Uri selectedImage;

    private OnFragmentInteractionListener mListener;

    public FragmentUploadPhoto() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentUploadPhoto.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentUploadPhoto newInstance(String param1, String param2) {
        FragmentUploadPhoto fragment = new FragmentUploadPhoto();
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
            business_id = getArguments().getString("business_id", null);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_upload_image, container, false);

        iv_upload_image = rootView.findViewById(R.id.iv_upload_image);
        btn_upload_image = rootView.findViewById(R.id.btn_upload_image);

        iv_upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callImagePicker(PICK_IMAGE_REQUEST);
            }
        });

        btn_upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPath!=null){
                    new uploadImage(mediaPath).execute();
                }else{
                    Toast.makeText(getContext(), "Please select an image", Toast.LENGTH_SHORT).show();
                }
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

    public void callImagePicker(int request_code){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), request_code);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null && data.getData()!=null){
                selectedImage = data.getData();
                String wholeId = DocumentsContract.getDocumentId(selectedImage);
                String id = wholeId.split(":")[1];
                String[] column = {MediaStore.Images.Media.DATA};
                String sel = MediaStore.Images.Media._ID+"=?";
                Cursor cursor = getContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, sel, new String[]{id}, null);
                int columnIndex = 0;
                if(cursor !=null){
                    columnIndex = cursor.getColumnIndex(column[0]);
                    if (cursor.moveToFirst()) {
                        mediaPath = cursor.getString(columnIndex);
                    }
                    cursor.close();
                }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImage);
                iv_upload_image.setImageBitmap(bitmap);
            }catch (IOException e){
                Log.d("ERROR", " "+e);
            }
        }
    }

    private class uploadImage extends AsyncTask<Void, Void, Void>{

        String mediaPath;
        public uploadImage(String mediaPath){
            this.mediaPath = mediaPath;
        }

        // use progress bar to show changes
        @Override
        protected Void doInBackground(Void... voids) {
            if (mediaPath != null) {
                Map config = new HashMap();
                config.put("cloud_name", "dsk5glwyy");
                config.put("api_key", "217275532177363");
                config.put("api_secret", "LDkTcI7QX6BvBQCMTbI_9vATKKs");
                Cloudinary cloudinary = new Cloudinary(config);
                try {
                    Map response = cloudinary.uploader().upload(mediaPath, ObjectUtils.emptyMap());
                    String url = (String) response.get("url");
                    final GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
                    Call<ImageUrl> call = service.uploadImage(business_id, url);
                    call.enqueue(new Callback<ImageUrl>() {
                        @Override
                        public void onResponse(Call<ImageUrl> call, Response<ImageUrl> response) {
                            if(response.isSuccessful()){
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
                            }else{
                                Log.d("Image Upload", "Image upload failed");
                                Toast.makeText(getContext(), "Something went wrong please try again", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ImageUrl> call, Throwable t) {

                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getContext(), "Image Uploaded", Toast.LENGTH_SHORT).show();
        }
    }
}
