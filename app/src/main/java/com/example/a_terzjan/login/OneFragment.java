package com.example.a_terzjan.login;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.a_terzjan.login.orders.OrderAdapter;
import com.example.a_terzjan.login.orders.Orders;
import com.example.a_terzjan.login.sqlite.SQLiteHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by a_terzjan on 28.06.2018.
 */

public class OneFragment extends Fragment {


    // Progress Dialog
    private ProgressDialog pDialog;

    private String info;
    private String status;
    private String user_id;

    private SQLiteHandler db;
    private ArrayList<Orders> orders_array = new ArrayList();
    private ListView orderList;
    private RequestQueue mRequestQueue;
    // products JSONArray
    private JSONArray orders = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View view = inflater.inflate(R.layout.fragment_one, container, false);




        orderList = (ListView) view.findViewById(R.id.productList);
        orders_array.clear();


        // task start
         new LoadDetails().execute();

        return view;

    }

 //================================================================================================
 //-----------------------------ASYNC TASK
 //================================================================================================
    /**
     * Background Async Task to Load all orders by making HTTP Request
     * */
    class LoadDetails extends AsyncTask<Integer, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Загрузка заказов. Пожалуйста подождите...");

            pDialog.show();
        }


        /**
         * getting All products from url
         * */
        protected Void doInBackground(Integer... intergers) {

            mRequestQueue = Volley.newRequestQueue(getActivity());

            StringRequest postRequest = new StringRequest(Request.Method.POST, "https://fit-exzample.000webhostapp.com/json/ph/get_orders_from_user.php",
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Log.i("asd", response.toString());

                            try {


                                // getting JSON string from URL
                                JSONObject jObj = new JSONObject(response);
                                orders = jObj.getJSONArray("orders");
                                for(int i = 0; i < orders.length(); i++) {// 8 - кол-во заказов

                                    JSONObject c = orders.getJSONObject(i);
                                    info = c.getString("info");
                                    status = c.getString("status");
                                    orders_array.add(new Orders(info, status));
                                }


                                OrderAdapter adapter = new OrderAdapter(getActivity(), R.layout.list_item_orders, orders_array);
                                orderList.setAdapter(adapter);

                            } catch (JSONException e) {
                                // JSON error
                                e.printStackTrace();
                                Toast.makeText(getActivity(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error

                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {

                    //   SqLite database handler
                    db = new SQLiteHandler(getActivity());
                    // Fetching user details from sqlite
                    HashMap<String, String> user = db.getUserDetails();
                    user_id = user.get("user_id");
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user_id", user_id);
                    return params;
                }
            };

            mRequestQueue.add(postRequest);


            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(Void avoid) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */




                }
            });

        }

    }

//================================================================================================
//================================================================================================
//================================================================================================





}









