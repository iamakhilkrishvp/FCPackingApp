package cied.in.fcpacking;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cied.in.fcpacking.Database.FCDatabase;
import cied.in.fcpacking.ListAdapter.CustomTradeAdapter;
import cied.in.fcpacking.Model.Items;
import cied.in.fcpacking.Model.URL;

public class TradeCustomers extends AppCompatActivity {
    EditText filterText;
    TextView searchButton,syncButton, refreshButton;
    RecyclerView recyclerView;
    FCDatabase database;
    String getTradeCustomerDetailsUrl;
    String fsid,cyclieId;
    int customerId;
    URL url;
    SessionManager manager;
    ArrayList<Items> tradeCustomerDetails = new ArrayList<Items>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_customer_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);
        filterText = (EditText) findViewById(R.id.filterText);
        searchButton = (TextView) findViewById(R.id.searchBtn);
        syncButton = (TextView) findViewById(R.id.syncButton);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManagaer
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManagaer);
        database = new FCDatabase(this);
        manager = new SessionManager(this);
        tradeCustomerDetails = (ArrayList<Items>) database.getTradeCustomerDetailsTable();
        if(tradeCustomerDetails.size() == 0){
            getCustomerDetailsRequest();
        }
        else {
            CustomTradeAdapter adapter = new CustomTradeAdapter(getApplicationContext(),tradeCustomerDetails);
            recyclerView.setAdapter(adapter);
        }
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newFsid = null;
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                ArrayList<Items> customerDetails = new ArrayList<Items>();
                fsid = filterText.getText().toString();
                if(fsid.equals("")) {
                    customerDetails = (ArrayList<Items>) database.getTradeCustomerDetailsTable();
                    if(customerDetails.size() == 0) {

                        errorPopup("FSID Not Found");
                    }
                    else{

                        CustomTradeAdapter adapter = new CustomTradeAdapter(TradeCustomers.this,customerDetails);
                        recyclerView.setAdapter(adapter);
                    }
                }
                boolean digitsOnly = TextUtils.isDigitsOnly(fsid);

                if (fsid.length() == 1) {
                    newFsid = "FS 1 00" + fsid;
                } else if (fsid.length() == 2) {
                    newFsid = "FS 1 0" + fsid;
                } else {
                    newFsid = "FS 1 " + filterText.getText().toString();
                }

                if(digitsOnly){

                    customerDetails = (ArrayList<Items>) database.getTradeCustomerDetailsTable(newFsid);
                    if(customerDetails.size() == 0) {

                        errorPopup("FSID Not Found");
                    }
                    else{

                        CustomTradeAdapter adapter = new CustomTradeAdapter(TradeCustomers.this,customerDetails);
                        recyclerView.setAdapter(adapter);
                    }
                }
                else {
                    customerDetails = (ArrayList<Items>) database.getTradeCustomerDetailsTable(fsid,"");
                    if(customerDetails.size() == 0) {
                        errorPopup("Name Not Found");
                    }
                    else{

                        CustomTradeAdapter adapter = new CustomTradeAdapter(TradeCustomers.this,customerDetails);
                        recyclerView.setAdapter(adapter);
                    }
                }

            }
        });
        syncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(TradeCustomers.this);
                builder.setMessage("Are you want sync data?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                postTradeOrder();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

    public void errorPopup(String message)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(TradeCustomers.this);
        alert.setTitle("Error");
        alert.setMessage(message);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,
                                int which) {
                filterText.setText("");
                dialog.dismiss();
            }
        });
        alert.show();
    }

    public void postTradeOrder(){
        final HashMap<String, String> getCycleId = manager.getTradeCycleId();
        cyclieId = getCycleId.get(SessionManager.KEY_TRADE_CYCLE_ID);
        JSONArray jsonArray = new JSONArray();
        ArrayList<Items> tradeOrderDetails = new ArrayList<Items>();
        tradeOrderDetails = (ArrayList<Items>) database.getTradeOrderDetailsTableForSync();
        for (int i=0; i<tradeOrderDetails.size(); i++){
            JSONObject jsonObject = new JSONObject();
            try {
               // jsonObject.put("cycle",Integer.parseInt(cyclieId));
                jsonObject.put("id",Integer.parseInt(tradeOrderDetails.get(i).getOrderId()));
                jsonObject.put("status",tradeOrderDetails.get(i).getStatus());
               // jsonObject.put("customer",tradeOrderDetails.get(i).getCustomerId());
               // jsonObject.put("vegetable",Integer.parseInt(tradeOrderDetails.get(i).getVegetableId()));
                jsonObject.put("quantity",Float.parseFloat(tradeOrderDetails.get(i).getVegetableQuantity()));
                jsonObject.put("rate",Float.parseFloat(tradeOrderDetails.get(i).getVegRate()));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(jsonObject);
        }
        Log.e("post values.........",jsonArray.toString());
        postTradeOrderRequest(jsonArray);
    }

    public void getCustomerDetailsRequest() {

        //getTradeCustomerDetailsUrl = url.liveRootUrl+"trade-app/get-packing-details/";
        getTradeCustomerDetailsUrl = url.rootUrl +"trade-app/get-packing-details/";
        final ProgressDialog dialog = new ProgressDialog(TradeCustomers.this,R.style.AppCompatAlertDialogStyle);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Loading... ");
        dialog.show();

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, getTradeCustomerDetailsUrl,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                Log.e(" trade Response ",response.toString());
                try {
                    manager.setTradeCycleId(response.getString("cycle_id"));
                    JSONArray array = response.getJSONArray("customers");
                    for (int i = 0; i<array.length(); i++)
                    {
                        JSONObject object = array.getJSONObject(i);
                        fsid = object.getString("fs_id");
                        customerId = Integer.parseInt(object.getString("id"));
                        database.addtoTradeCustomerDetailsTable(new Items(object.getString("id"),object.getString("fs_id"),
                                object.getString("name"),object.getString("group"),object.getString("sub_group"),object.getString("status")
                                ,object.getString("is_priority"),object.getString("ordered_quantity"),object.getString("note"),"false"));
                        JSONArray jsonArray = object.getJSONArray("trade_orders");
                        for (int j=0; j<jsonArray.length(); j++){
                            JSONObject jsonObject = jsonArray.getJSONObject(j);
                            database.addtoTradeOrderDetailsTable(new Items(customerId,fsid,jsonObject.getString("status"),jsonObject.getString("farm_id"),
                                    jsonObject.getString("vegetable_name"),jsonObject.getString("quantity"),jsonObject.getString("vegetable_id")
                                    ,jsonObject.getString("id"),jsonObject.getString("rate"),1));
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                tradeCustomerDetails = (ArrayList<Items>) database.getTradeCustomerDetailsTable();
                CustomTradeAdapter adapter = new CustomTradeAdapter(getApplicationContext(),tradeCustomerDetails);
                recyclerView.setAdapter(adapter);
                dialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    dialog.dismiss();

                    Toast.makeText(getApplicationContext(),"No internet connection.. ",Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof AuthFailureError) {
                    dialog.dismiss();
                    Log.e("AuthFailureError", "AuthFailureError.......");
                } else if (error instanceof ServerError) {
                    dialog.dismiss();
                    Log.e("ServerError>>>>>>>>>", "ServerError.......");
                } else if (error instanceof NetworkError) {
                    dialog.dismiss();

                    // Toast.makeText(getApplicationContext(),"No internet connection",Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    dialog.dismiss();
                    Log.e("ParseError>>>>>>>>>", "ParseError.......");
                            /*finish();
                            startActivity(getIntent());*/
                }else if (error instanceof TimeoutError) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Time out",Toast.LENGTH_SHORT).show();
                }
            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                //  headers.put("access-token", accessToken);
                return headers;
            }
        };
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        req.setRetryPolicy(policy);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(req);

    }
    public void postTradeOrderRequest(JSONArray jsonArray) {

        //String postTradeOrderUrl  = url.liveRootUrl+"organic-product-order/";
        String postTradeOrderUrl  = url.rootUrl +"trade-app/update-organic-product-order/";
        final ProgressDialog dialog = new ProgressDialog(TradeCustomers.this,R.style.AppCompatAlertDialogStyle);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Loading... ");
        dialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.PUT, postTradeOrderUrl, jsonArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("complaint response ..", response.toString());
                dialog.dismiss();
                Toast.makeText(getApplicationContext(),"Synced Successfully ",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    dialog.dismiss();

                    Toast.makeText(getApplicationContext(),"No internet connection.. ",Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof AuthFailureError) {
                    dialog.dismiss();
                    Log.e("AuthFailureError", "AuthFailureError.......");
                } else if (error instanceof ServerError) {
                    dialog.dismiss();
                    Log.e("ServerError>>>>>>>>>", "ServerError.......");
                } else if (error instanceof NetworkError) {
                    dialog.dismiss();

                    // Toast.makeText(getApplicationContext(),"No internet connection",Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    dialog.dismiss();
                    Log.e("ParseError>>>>>>>>>", "ParseError.......");
                            /*finish();
                            startActivity(getIntent());*/
                }else if (error instanceof TimeoutError) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Time out",Toast.LENGTH_SHORT).show();
                }
            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                //  headers.put("access-token", accessToken);
                return headers;
            }
        };

        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonArrayRequest.setRetryPolicy(policy);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonArrayRequest);

    }


}
