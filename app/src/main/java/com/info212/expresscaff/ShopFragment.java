package com.info212.expresscaff;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShopFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String TAG = "main";
    MapView mapView;
    GoogleMap map;
    String markerTitle;
    String shopAddress;
    int shopPhone;
    ListView shopview;
    List<ParseObject> ob;
    ArrayAdapter<String> adapter;






    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShopFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShopFragment newInstance(String param1, String param2) {
        ShopFragment fragment = new ShopFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ShopFragment() {
        // Required empty public constructor
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_shop, container, false);



        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) v.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        map = mapView.getMap();
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.setMyLocationEnabled(true);

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        /*try {
            MapsInitializer.initialize(this.getActivity());
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }*/

        // Updates the location and zoom of the MapView


        return v;


    }



    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        createMarkers();
        setMapCamera();
        new RemoteDataTask().execute();

/*
        TextView phone = (TextView) getActivity().findViewById(R.id.phoneNumber);
        ParseUser currentUser = ParseUser.getCurrentUser();
        String strPhone = currentUser.getString("phone");
        phone.setText("phone number is " + strPhone);
*/

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker waynes) {

                Toast.makeText(getActivity(),
                        "infowindows clicked",
                        Toast.LENGTH_LONG).show();

            }
        });


    }



    public void createMarkers() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("shop");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, com.parse.ParseException e) {
                if (e == null) {

                    for (int i = 0; i < parseObjects.size(); i++) {
                        markerTitle = parseObjects.get(i).getString("name");
                        Double lat = parseObjects.get(i).getDouble("latitude");
                        Double lng = parseObjects.get(i).getDouble("longitude");
                        shopAddress = parseObjects.get(i).getString("address");
                        shopPhone = parseObjects.get(i).getInt("phone");


                        map.addMarker(new MarkerOptions()
                                .position(new LatLng(lat, lng))
                                .title(markerTitle)
                                .snippet(shopAddress));


                    }

                } else {
                    Log.d("ERROR:", "" + e.getMessage());
                }
            }
        });
    }

    public void setMapCamera() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (location != null)
        {
            final int REQUEST_CODE_LOCATION = 2;

            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                // Request missing location permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION);
                Log.d("NOT WORKING", "LOCATION");
            } else {
                // Location permission has been granted, continue as usual.
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15);
                map.moveCamera(cameraUpdate);
                Log.d("WORKING", "LOCATION");


            }
        } else {
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(60.959707, 7.940651), 5);
            map.animateCamera(cameraUpdate);
        }

   /* CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13);
    map.animateCamera(cameraUpdate);*/
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {

        protected Void doInBackground(Void... params) {
            // Locate the class table named "shop" in Parse.com
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                    "shop");
            try {
                ob = query.find();
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            // Locate the listview in listview_main.xml
            shopview = (ListView) getActivity().findViewById(R.id.shopList_main);
            // Pass the results into an ArrayAdapter
            adapter = new ArrayAdapter<String>(getActivity(), R.layout.shopview_item);
            // Retrieve object "name" from Parse.com database
            for (ParseObject shop : ob) {
                adapter.add((String) shop.get("name"));
            }
            // Binds the Adapter to the ListView
            shopview.setAdapter(adapter);
            // Capture button clicks on ListView items
            shopview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // Send single item click data to SingleItemView Class

                    Intent i = new Intent(getActivity(),OrderActivity.class);
                    // Pass data "name" followed by the position
                    i.putExtra("name", ob.get(position).getString("name"));
                    i.putExtra("address", ob.get(position).getString("address"));
                    i.putExtra("latitude", ob.get(position).getDouble("latitude"));
                    i.putExtra("longitude", ob.get(position).getDouble("longitude"));
                    i.putExtra("phone", ob.get(position).getString("phone"));
                    // Open SingleItemView.java Activity
                    startActivity(i);
                }
            });


        }

    }
}


