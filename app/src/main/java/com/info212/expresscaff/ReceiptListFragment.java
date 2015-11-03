package com.info212.expresscaff;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReceiptListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReceiptListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReceiptListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String TAG = "receiptList";

    ParseUser currentUser = ParseUser.getCurrentUser();
    String struser = currentUser.getUsername();

    ListView receiptView;
    List<ParseObject> parseReceiptObject;
    private List<Receipt> ReceiptListClass = null;

    ReceiptListAdapter adapter;



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
     * @return A new instance of fragment ReceiptListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReceiptListFragment newInstance(String param1, String param2) {
        ReceiptListFragment fragment = new ReceiptListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ReceiptListFragment() {
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
        return inflater.inflate(R.layout.fragment_receipt_list, container, false);
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        new RemoteDataTask().execute();

    }

    @Override
    public void onResume() {

        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){

                   /* Fragment frag = new ShopFragment();


                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.container, frag);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.addToBackStack(null);
                    ft.commit();*/
                    Intent intent = new Intent(getActivity(), ShopActivity.class);
                    startActivity(intent);




                    return true;

                }

                return false;
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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
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
            asyncDialog.setMessage("Getting archived receipts...");
            //show dialog
            //TODO
            asyncDialog.show();
            super.onPreExecute();
        }

        protected Void doInBackground(Void... params) {
             ReceiptListClass = new ArrayList<Receipt>();
            try {
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("receipt");
                query.whereEqualTo("username", struser);
                parseReceiptObject = query.find();
                for (ParseObject receipt: parseReceiptObject){
                Receipt r = new Receipt();
                    r.setNameShop((String) receipt.get("shop_name"));
                    r.setAddress((String) receipt.get("shop_address"));
                    r.setExpire((String) receipt.get("expired"));
                    r.setDate((String) receipt.get("bought_at"));
                    r.setCoffeeType1((String) receipt.get("coffee_type1"));
                    r.setCoffeePrice((Integer) receipt.get("sum_cost"));
                    r.setTotalSum((Integer) receipt.get("sum_cost"));
                    r.setBarcode((Integer) receipt.get("barcode_nr"));
                    r.setExpireDate((String) receipt.get("expire_date"));
                    ReceiptListClass.add(r);
                }
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;

        }
        protected void onPostExecute(Void result) {
            // Locate the listview in listview_main.xml
            receiptView = (ListView) getActivity().findViewById(R.id.receiptList);
            // Pass the results into an ArrayAdapter
            adapter =  new ReceiptListAdapter(getActivity(), ReceiptListClass);
            // Binds the Adapter to the ListView
            receiptView.setAdapter(adapter);



           /* // Capture button clicks on ListView items
            receiptView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
            });*/

            asyncDialog.dismiss();

        }

    }

}
