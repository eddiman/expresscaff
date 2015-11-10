package com.info212.expresscaff;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
    static Location location;

    String markerTitle;
    String shopAddress;
    int shopPhone;
    ListView shopview;
    List<ParseObject> ob;
    ArrayAdapter<String> adapter;

    float[] closestDistance = new float[10];

    Double ownLat;
    Double ownLong;
    String nearestShopName;
    String nearestshopAddress;
    int nearestshopPhone;
    Double nearestShopLat;
    Double nearestShopLong;
    Button nearestButton;

    public static Double radiusShow = 500.0; //TODO: only placeholder;  put later in SettingsActivity.class and refer to it from there
    Double radiusShowMore = 500.0;
    Intent orderIntent;

    Double markerLat;
    Double markerLng;
    float alpha = (float) 0.35;



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

        nearestButton = (Button) v.findViewById(R.id.nearestButton);



        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) v.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        map = mapView.getMap();
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.setMyLocationEnabled(true);

       ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Choose your coffee shop");

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

        setMapCamera();

        new RemoteDataTask().execute();

    }



    public void createMarkers() {
        final ParseQuery<ParseObject> query = ParseQuery.getQuery("shop");


        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, com.parse.ParseException e) {
                if (e == null) {


                    float minDistance = Float.MAX_VALUE;
                    int minIndex = -1;

                    for (int i = 0; i < parseObjects.size(); i++) {

                        markerTitle = parseObjects.get(i).getString("name");
                        markerLat = parseObjects.get(i).getDouble("latitude");
                        markerLng = parseObjects.get(i).getDouble("longitude");
                        shopAddress = parseObjects.get(i).getString("address");
                        shopPhone = parseObjects.get(i).getInt("phone");
                        location.distanceBetween(markerLat, markerLng, ownLat, ownLong, closestDistance);
                        Log.d("CLOSESDISTANCES", closestDistance[0] + "");

                        if (closestDistance[0] < minDistance) {
                            minDistance = closestDistance[0];
                            minIndex = i;
                        }


                        if (closestDistance[0] < SettingsActivity.radius) {
                            Marker marker = map.addMarker(new MarkerOptions()
                                    .position(new LatLng(markerLat, markerLng))
                                    .title(markerTitle)
                                    .snippet(shopAddress)
                                    .icon((BitmapDescriptorFactory.fromResource(R.drawable.marker)))

                            );



                        }

                        if (closestDistance[0] < radiusShow + radiusShowMore) {
                            Marker marker = map.addMarker(new MarkerOptions()
                                    .position(new LatLng(markerLat, markerLng))
                                    .title(markerTitle)
                                    .snippet(shopAddress)
                                    .alpha(alpha)
                                    .icon((BitmapDescriptorFactory.fromResource(R.drawable.marker)))
                            );


                        }


                    }


                    map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {

                            Intent i = new Intent(getActivity(), OrderActivity.class);
                            // Pass data "name" followed by the position
                            i.putExtra("name", marker.getTitle());
                            i.putExtra("shopAddress", marker.getSnippet());
                            i.putExtra("latitude", marker.getPosition().latitude);
                            i.putExtra("longitude", marker.getPosition().longitude);
                            i.putExtra("phone", 0);
                            // Open SingleItemView.java Activity
                            startActivity(i);

                        }
                    });


                    if (minIndex >= 0) {
                        // now nearest maker found:
                        nearestShopName = parseObjects.get(minIndex).getString("name");
                        nearestShopLat = parseObjects.get(minIndex).getDouble("latitude");
                        nearestShopLong = parseObjects.get(minIndex).getDouble("longitude");
                        nearestshopAddress = parseObjects.get(minIndex).getString("address");
                        nearestshopPhone = parseObjects.get(minIndex).getInt("phone");
                        nearestButton.setText("Your nearest coffee shop is: " + nearestShopName);

                    } else {
                        nearestButton.setText("Cannot locate your position");
                    }


                } else {
                    Log.d("ERROR:", "" + e.getMessage());
                }

            }

        });

        nearestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNearestShop();
            }
        });
    }

    public void goToNearestShop(){

        orderIntent = new Intent(getActivity(),OrderActivity.class);

        // Pass data "name" followed by the position
        orderIntent.putExtra("name", nearestShopName);
        orderIntent.putExtra("shopAddress", nearestshopAddress);
        orderIntent.putExtra(("latitude"), nearestShopLat);
        orderIntent.putExtra("longitude", nearestShopLong);
        orderIntent.putExtra("phone", nearestshopPhone);
        // Open SingleItemView.java Activity
        startActivity(orderIntent);

    }




    public void setMapCamera() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));

        final int REQUEST_CODE_LOCATION = 2;

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {


                  if (location != null)
                {

                    ownLat = location.getLatitude();
                    ownLong = location.getLongitude();

                    // Location permission has been granted, continue as usual.
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(ownLat, ownLong), 15);
                    map.moveCamera(cameraUpdate);
                    Log.d("WORKING", "LOCATION");
                    Toast.makeText(getActivity(),
                            "permissions and getLocation are ok",
                            Toast.LENGTH_LONG).show();
                    createMarkers();

                    } else {
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(60.959707, 7.940651), 5);
                    map.animateCamera(cameraUpdate);
                    Toast.makeText(getActivity(),
                            "cant locate",
                            Toast.LENGTH_LONG).show();
                    }
            } else {
            // Request missing location permission.
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION);

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
        ProgressDialog asyncDialog = new ProgressDialog(getActivity());


        @Override
        protected void onPreExecute() {
            //set message of the dialog
            asyncDialog.setMessage("Finding closest coffeeshops...");
            //show dialog
            asyncDialog.show();
            shopview = (ListView) getActivity().findViewById(R.id.shopList_main);

            super.onPreExecute();
        }

        protected Void doInBackground(Void... params) {
            // Locate the class table named "shop" in Parse.com
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("shop");
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

                    Intent i = new Intent(getActivity(), OrderActivity.class);
                    // Pass data "name" followed by the position
                    i.putExtra("name", ob.get(position).getString("name"));
                    i.putExtra("shopAddress", ob.get(position).getString("address"));
                    i.putExtra("latitude", ob.get(position).getDouble("latitude"));
                    i.putExtra("longitude", ob.get(position).getDouble("longitude"));
                    i.putExtra("phone", ob.get(position).getString("phone"));
                    // Open SingleItemView.java Activity
                    startActivity(i);
                }
            });



            asyncDialog.dismiss();

        }

    }
}


