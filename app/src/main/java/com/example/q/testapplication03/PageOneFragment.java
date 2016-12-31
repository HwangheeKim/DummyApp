package com.example.q.testapplication03;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.renderscript.ScriptGroup;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;
import com.koushikdutta.ion.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PageOneFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PageOneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PageOneFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PageOneFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PageOneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PageOneFragment newInstance() {
        PageOneFragment fragment = new PageOneFragment();
        Bundle args = new Bundle();
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

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_page_one, container, false);
        ImageAdapter adapter = new ImageAdapter();

        GridView gridView = (GridView) view.findViewById(R.id.gallery_gridview);
        gridView.setAdapter(adapter);

        adapter.addItem(R.drawable.img01, "test1");
        adapter.addItem(R.drawable.img02, "test2");
        adapter.addItem(R.drawable.img03, "test3");
        adapter.addItem(R.drawable.img04, "test4");
        adapter.addItem(R.drawable.img05, "test5");
        adapter.addItem(R.drawable.img06, "test6");


        return view;
    }

    public void postPicture(final View view) {
        String url = "http://52.78.52.132:3000/images";     // AWS
//        String url = "http://143.248.49.122:3000/images";   // N1

        final String filePath = Environment.getExternalStorageDirectory().toString() + "/DCIM/Camera";
        final String fileName = "2016-12-25-12-16-43.jpg";
        final File fileToUpload = new File(filePath + "/" + fileName);

        Toast.makeText(getContext(), "" + fileToUpload.exists(), Toast.LENGTH_SHORT).show();

        Ion.with(view.getContext())
                .load(url)
                .setHeader("name", "something")
                .setHeader("last_update", "I don't know")
                .setMultipartFile("userFile", "image/jpeg", fileToUpload)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if(e != null) {
                            Toast.makeText(view.getContext(), "Error uploading file", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                            return;
                        }
                        Toast.makeText(view.getContext(), "File upload complete", Toast.LENGTH_SHORT).show();
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
}
