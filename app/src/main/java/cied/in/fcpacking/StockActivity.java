package cied.in.fcpacking;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import cied.in.fcpacking.ListAdapter.CustomStockAdapter;
import cied.in.fcpacking.Model.Items;
import cied.in.fcpacking.Model.URL;

public class StockActivity extends AppCompatActivity {
    EditText filterText;
    TextView syncButton,totalNonPrefQty;
    RecyclerView recyclerView;
    ImageView refreshIcon,backButtonIcon;
    FCDatabase database;
    URL url;
    ArrayList<Items> stockDetails = new ArrayList<Items>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        syncButton = (TextView) findViewById(R.id.textView97);
        totalNonPrefQty = (TextView) findViewById(R.id.textView102);
        filterText = (EditText) findViewById(R.id.editText8);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        refreshIcon = (ImageView) toolbar.findViewById(R.id.imageView4);
        backButtonIcon = (ImageView) toolbar.findViewById(R.id.imageView);
        LinearLayoutManager layoutManagaer
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManagaer);
        database = new FCDatabase(this);
        filterText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                stockDetails = (ArrayList<Items>) database.getStockVegetableTable(""+charSequence);
                totalNonPrefQty.setText("Total Non.Pref.Qty: "+stockDetails.get(0).getNonPreferanceQuantity()+" Kg ");
                CustomStockAdapter adapter = new CustomStockAdapter(getApplicationContext(),stockDetails);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        stockDetails = (ArrayList<Items>) database.getStockVegetableTable();
        if(stockDetails.size() == 0){
            getVegetableStockDetailsRequest();
        }
        else {
            totalNonPrefQty.setText("Total Non.Pref.Qty: "+stockDetails.get(0).getNonPreferanceQuantity()+" Kg ");
            CustomStockAdapter adapter = new CustomStockAdapter(getApplicationContext(),stockDetails);
            recyclerView.setAdapter(adapter);
        }
        backButtonIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        refreshIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(StockActivity.this);
                builder.setMessage("Are you want sync data?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                database.deleteStockVegetableTableTable();
                                Intent intent = new Intent(getApplicationContext(),StockActivity.class);
                                startActivity(intent);
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
        syncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(StockActivity.this);
                builder.setMessage("Are you want sync data?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                stockDetails = (ArrayList<Items>) database.getStockVegetableTableForSync();
                                JSONArray jsonArray = new JSONArray();
                                for (int i=0; i<stockDetails.size(); i++){
                                    JSONObject object = new JSONObject();
                                    try {
                                        object.put("vegetable_id",stockDetails.get(i).getVegetableId());
                                        object.put("is_available_for_weekly_once",stockDetails.get(i).getNonPreferanceStatus());
                                        object.put("preferenceless_customer_stock",Float.parseFloat(stockDetails.get(i).getVegetableQuantity()));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    jsonArray.put(object);
                                }
                                Log.e("post values...",jsonArray.toString());
                                  postStockVegetablesRequest(jsonArray);

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
    public void postStockVegetablesRequest(JSONArray jsonArray) {

        //String postVegetableOrderUrl  = url.liveRootUrl+"sourced-vegetable/";
        String postVegetableOrderUrl  = url.rootUrl +"subscription/update-subscription-vegetables/";
        final ProgressDialog dialog = new ProgressDialog(StockActivity.this,R.style.
                AppCompatAlertDialogStyle);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Loading... ");
        dialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, postVegetableOrderUrl,
                jsonArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("Order response ..", response.toString());
                dialog.dismiss();
                Toast.makeText(getApplicationContext(),"Synced Successfully ",Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    dialog.dismiss();

                    Toast.makeText(getApplicationContext(),"No internet connection.. ",
                            Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof AuthFailureError) {
                    dialog.dismiss();
                    Log.e("AuthFailureError", "AuthFailureError.......");
                } else if (error instanceof ServerError) {
                    dialog.dismiss();

                } else if (error instanceof NetworkError) {
                    dialog.dismiss();

                    Toast.makeText(getApplicationContext(),"No internet connection",
                            Toast.LENGTH_SHORT).show();
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
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.
                DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonArrayRequest.setRetryPolicy(policy);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonArrayRequest);

    }

    public void getVegetableStockDetailsRequest() {
        String getStockUrl = url.rootUrl +"subscription/get-subscription-vegetables/";
        //Log.e("url....",getStockUrl);
        final ProgressDialog dialog = new ProgressDialog(StockActivity.this,R.style.
                AppCompatAlertDialogStyle);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Loading... ");
        dialog.show();

        final JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,getStockUrl,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                dialog.dismiss();
                Log.e(" Response ",response.toString());
                try {
                    JSONArray jsonArray = response.getJSONArray("subscription_vegetables_list");
                    String qty = response.getString("customer_preference_less_count");
                    totalNonPrefQty.setText("otal Non.Pref.Qty: "+response.getString("customer_preference_less_count")+" Kg ");
                for (int i = 0; i< jsonArray.length(); i++){

                        JSONObject object = jsonArray.getJSONObject(i);
                        database.addtoStockVegetableTable(new Items(object.getString("vegetable_id"),
                                object.getString("vegetable_name"),object.getString("is_available_for_weekly_once"),
                                object.getString("preferenceless_customer_stock"),qty));


                }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                stockDetails = (ArrayList<Items>) database.getStockVegetableTable();
                CustomStockAdapter adapter = new CustomStockAdapter(getApplicationContext(),stockDetails);
                recyclerView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    dialog.dismiss();

                    Toast.makeText(getApplicationContext(),"No internet connection.. ",
                            Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof AuthFailureError) {
                    dialog.dismiss();
                    Log.e("AuthFailureError", "AuthFailureError.......");
                } else if (error instanceof ServerError) {
                    dialog.dismiss();
                    Log.e("ServerError>>>>>>>>>", "ServerError.......");
                } else if (error instanceof NetworkError) {
                    dialog.dismiss();

                    Toast.makeText(getApplicationContext(),"No internet connection",
                            Toast.LENGTH_SHORT).show();
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
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.
                DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        req.setRetryPolicy(policy);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(req);

    }

}
