package com.example.a_terzjan.login;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserCabinet extends AppCompatActivity {


    private SQLiteHandler db;
    private Button btnChangeName;
    private EditText changeName;
    // Progress Dialog
    private ProgressDialog pDialog;
    private RequestQueue mRequestQueue;
    private String user_id;
    HashMap<String, String> user;
    private String changeNameStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cabinet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnChangeName = (Button) findViewById(R.id.btnChangeName);
        changeName = (EditText) findViewById(R.id.changeName);




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        //   SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Fetching user details from sqlite
       user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");
        changeName.setText(name);

        toolbar.setTitle(name);

        setSupportActionBar(toolbar);



    }



    public void sendMessage(View view) {


        //-------------------------------------------------------------------------

        mRequestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest postRequest = new StringRequest(Request.Method.POST, "https://fit-exzample.000webhostapp.com/json/ph/change_users.php",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {



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


                 user = db.getUserDetails();
                 user_id = user.get("user_id");
                changeNameStr = String.valueOf(changeName.getText());
                 db.update_name(user_id,changeNameStr);



                Map<String, String> params = new HashMap<String, String>();
                params.put("changeName", changeNameStr);
                params.put("user_id", user_id);
                return params;
            }
        };

        mRequestQueue.add(postRequest);
        Toast.makeText(this, "Данные успешно изменены!",Toast.LENGTH_LONG);
        UserCabinet.this.recreate();

    }
//================================================================================================
    //-----------------------------ASYNC TASK
    //================================================================================================
    /**
     * Background Async Task to Load all orders by making HTTP Request
     * */
    class Change extends AsyncTask<Integer, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getApplicationContext());
            pDialog.setMessage("Загрузка заказов. Пожалуйста подождите...");
   
            pDialog.show();
        }


        /**
         * getting All products from url
         * */
        protected Void doInBackground(Integer... intergers) {

            mRequestQueue = Volley.newRequestQueue(getApplicationContext());

            StringRequest postRequest = new StringRequest(Request.Method.POST, "https://fit-exzample.000webhostapp.com/json/ph/change_users.php",
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {



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


                    user = db.getUserDetails();
                    user_id = user.get("user_id");

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("changeName", String.valueOf(changeName.getText()));
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
            new UserCabinet().runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */




                }
            });

        }

    }



}








