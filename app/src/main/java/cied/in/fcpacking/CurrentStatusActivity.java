package cied.in.fcpacking;

import android.app.Activity;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
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
import java.util.List;
import java.util.Map;

import cied.in.fcpacking.Database.FCDatabase;
import cied.in.fcpacking.ListAdapter.CustomVegetableStockAdapter;
import cied.in.fcpacking.Model.Items;
import cied.in.fcpacking.Model.URL;

public class CurrentStatusActivity extends AppCompatActivity {

    TextView updateButton, refreshBtn, summaryButton,spinnerText,goButton;
    EditText filterText;
    Spinner spinner;
    RecyclerView recyclerView;
    String filterWord, getStockDetailsUrl,cycleId,farmId,id;
    FCDatabase database;
    URL url;
    ArrayList<Items> stockVegetableList = new ArrayList<Items>();
    ArrayList<Items> deliveryCycleDetails = new ArrayList<Items>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vegetable_stock);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinner = (Spinner) findViewById(R.id.spinner6);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        updateButton = (TextView) findViewById(R.id.updateButton);
        summaryButton = (TextView) findViewById(R.id.textView72);
        spinnerText = (TextView) findViewById(R.id.textView82);
        goButton = (TextView) findViewById(R.id.textView83);
        refreshBtn = (TextView) toolbar.findViewById(R.id.textView9);
        filterText = (EditText) findViewById(R.id.editText);
        database = new FCDatabase(this);
        LinearLayoutManager layoutManagaer
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManagaer);
        spinnerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerText.setVisibility(View.INVISIBLE);
                spinner.setVisibility(View.VISIBLE);
                spinner.performClick();
            }
        });

        deliveryCycleDetails = (ArrayList<Items>) database.getDeliveryCycleTable();
        if(deliveryCycleDetails.size() == 0){

            getDeliveryCycleDetails();
        }
        else {
            cycleId = deliveryCycleDetails.get(0).getSubscriptionDeliveryId();
            spinnerText.setText(deliveryCycleDetails.get(0).getDeliveryNumber()+" ( "+
                    deliveryCycleDetails.get(0).getDeliveryDate()+"  )");
            SpinnerAdapter spinnerAdapter = new SpinnerAdapter(CurrentStatusActivity.this,deliveryCycleDetails);
            spinner.setAdapter(spinnerAdapter);
            stockVegetableList = (ArrayList<Items>) database.getVegetableStockTableDetails(
                    Integer.parseInt(cycleId));
            if(stockVegetableList.size() == 0){
                getVegetableStockDetailsRequest(cycleId);
            }
            else {
                stockVegetableList = (ArrayList<Items>) database.getVegetableStockTableDetails(Integer.parseInt(cycleId));
                CustomVegetableStockAdapter stockAdapter = new CustomVegetableStockAdapter
                        (getApplicationContext(),stockVegetableList);
                recyclerView.setAdapter(stockAdapter);
            }
        }

        filterText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                stockVegetableList = (ArrayList<Items>) database.getVegetableStockTableDetails(""+charSequence,Integer.parseInt(cycleId));
                CustomVegetableStockAdapter stockAdapter = new CustomVegetableStockAdapter
                        (getApplicationContext(),stockVegetableList);
                recyclerView.setAdapter(stockAdapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder = new AlertDialog.Builder(CurrentStatusActivity.this);
                builder.setMessage("Are you want Refresh data?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                database.deleteVegetableStockTable();
                                database.deleteDeliveryDetailsTable();
                                Intent intent = new Intent(getApplicationContext(),CurrentStatusActivity.class);
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
        summaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),ShortageActivity.class);
                startActivity(intent);

            }
        });
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // Log.e("cycle ..id....",cycleId);
                stockVegetableList = (ArrayList<Items>) database.getVegetableStockTableDetails
                        (Integer.parseInt(cycleId));
                if(stockVegetableList.size() == 0){
                    getVegetableStockDetailsRequest(cycleId);
                }
                else {
                    stockVegetableList = (ArrayList<Items>) database.getVegetableStockTableDetails(Integer.parseInt(cycleId));
                    CustomVegetableStockAdapter stockAdapter = new CustomVegetableStockAdapter
                            (getApplicationContext(),stockVegetableList);
                    recyclerView.setAdapter(stockAdapter);
                    stockAdapter.notifyDataSetChanged();
                }

            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(CurrentStatusActivity.this);
                builder.setMessage("Are you want sync data?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Log.e("cycle id........",cycleId);
                                JSONArray jsonArray = new JSONArray();
                                stockVegetableList = (ArrayList<Items>) database.getVegetableStockTableDetails(Integer.parseInt(cycleId));

                                for (int i=0; i<stockVegetableList.size() ; i++){

                                    JSONObject jsonObject = new JSONObject();
                                        try {

                                            jsonObject.put("wastage",Float.parseFloat(stockVegetableList.get(i).getWastageQuantity()));
                                            jsonObject.put("total_quantity",Float.parseFloat(stockVegetableList.get(i).getReceivedQuantity()));
                                            jsonObject.put("excess_quantity",Float.parseFloat(stockVegetableList.get(i).getExcessQuantity()));
                                            jsonObject.put("rating",Float.parseFloat(stockVegetableList.get(i).getVegetableQuality()));
                                            jsonObject.put("vegetable_id",Integer.parseInt(stockVegetableList.get(i).getVegetableId()));
                                            jsonObject.put("farm_id",Integer.parseInt(stockVegetableList.get(i).getFarmId()));
                                            jsonObject.put("ordered_quantity",0);
                                            jsonObject.put("id",Integer.parseInt(stockVegetableList.get(i).getOrderId()));
                                            jsonObject.put("total_price",Float.parseFloat(stockVegetableList.get(i).getVegRate()));
                                            jsonObject.put("non_preference_veg_status",stockVegetableList.get(i).getNonPreferanceStatus());


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        jsonArray.put(jsonObject);


                                }
                                Log.e("post...values...",jsonArray.toString());
                                  postStockRequest(jsonArray);
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
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

    public void postStockRequest(JSONArray jsonArray) {

        //String postStockDetailsUrl  = url.liveRootUrl+"farm/update-sourced-vegetables/";
        String postStockDetailsUrl  = url.rootUrl +"farm/update-sourced-vegetables/";
        final ProgressDialog dialog = new ProgressDialog(CurrentStatusActivity.this,R.style.AppCompatAlertDialogStyle);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Loading... ");
        dialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, postStockDetailsUrl, jsonArray, new Response.Listener<JSONArray>() {
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

                     Toast.makeText(getApplicationContext(),"No internet connection",Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    dialog.dismiss();
                    Log.e("ParseError>>>>>>>>>", "ParseError.......");

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
    public void getVegetableStockDetailsRequest(final String cycleId) {

        getStockDetailsUrl = url.rootUrl +"/farm/get-sourced-vegetables/?subscription_cycle_id="+cycleId;
        final ProgressDialog dialog = new ProgressDialog(CurrentStatusActivity.this,R.style.AppCompatAlertDialogStyle);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Loading... ");
        dialog.show();


        final JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,getStockDetailsUrl,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                dialog.dismiss();
                Log.e(" Response ",response.toString());
                try {

                    if(response.length() == 0){
                        Toast.makeText(getApplicationContext(),"Sourced Vegetables not found.. ",Toast.LENGTH_SHORT).show();

                    }
                    else {
                        JSONArray jsonArray = response.getJSONArray("ordered_vegetables");
                        for (int i= 0 ; i<jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            JSONArray farmJsonArray = jsonObject.getJSONArray("farm");
                            for (int j= 0 ; j<1; j++){
                                JSONObject object = farmJsonArray.getJSONObject(j);

                                database.addToVegetableStockTable(new Items(jsonObject.getString("vegetable_id"),
                                        jsonObject.getString("vegetable_name"),jsonObject.getString("sourced_vegetable_id"),
                                        jsonObject.getString("trade_quantity"), jsonObject.getString("sourced_total_quantity"),
                                        jsonObject.getString("sourced_total_quantity"), jsonObject.getString("excess_quantity"),
                                        jsonObject.getString("wastage"),jsonObject.getString("total_price"),
                                        object.getString("farm_name"),object.getString("farm"),
                                        jsonObject.getString("rating"),jsonObject.getString("is_available_for_weekly_once"),
                                        cycleId,jsonObject.getString("preferenceless_quantity"),
                                        jsonObject.getString("preference_given_subscription_quantity"),1,
                                        jsonObject.getString("custom_quantity")));


                            }

                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                stockVegetableList = (ArrayList<Items>) database.getVegetableStockTableDetails(Integer.parseInt(cycleId));
                CustomVegetableStockAdapter stockAdapter = new CustomVegetableStockAdapter
                        (getApplicationContext(),stockVegetableList);
                recyclerView.setAdapter(stockAdapter);
                dialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                int statusCode;
                NetworkResponse networkResponse = error.networkResponse;
                statusCode = networkResponse.statusCode;
                Log.e("code...",""+networkResponse.statusCode);
                if(statusCode == 404){
                    Toast.makeText(getApplicationContext(),"Cycle id doesn't exist..",
                            Toast.LENGTH_SHORT).show();
                }
                else if(statusCode == 500){
                    Toast.makeText(getApplicationContext(),"Please contact Sreehari",
                            Toast.LENGTH_SHORT).show();
                }
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

                     Toast.makeText(getApplicationContext(),"No internet connection",Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    dialog.dismiss();
                    Log.e("ParseError>>>>>>>>>", "ParseError.......");

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

    public void getDeliveryCycleDetails(){
        String getDeliveryCycleUrl = url.getDeliveryCycleDetailsUrl;
        final ProgressDialog dialog = new ProgressDialog(CurrentStatusActivity.this,R.style.
                AppCompatAlertDialogStyle);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Loading... ");
        dialog.show();

        final JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, getDeliveryCycleUrl,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                dialog.dismiss();
                Log.e("cycle Response",response.toString());
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for (int i = 0; i < 4; i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        database.addToDeliveryCycleTable(new Items(jsonObject.getString("id"),
                                jsonObject.getString("delivery_no"),
                                jsonObject.getString("delivery_date")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                deliveryCycleDetails = (ArrayList<Items>) database.getDeliveryCycleTable();
                cycleId = deliveryCycleDetails.get(0).getSubscriptionDeliveryId();
                spinnerText.setText(deliveryCycleDetails.get(0).getDeliveryNumber()+" ( "+
                        deliveryCycleDetails.get(0).getDeliveryDate()+"  )");
                SpinnerAdapter spinnerAdapter = new SpinnerAdapter(CurrentStatusActivity.this,deliveryCycleDetails);
                spinner.setAdapter(spinnerAdapter);
                stockVegetableList = (ArrayList<Items>) database.getVegetableStockTableDetails(
                        Integer.parseInt(cycleId));
                if(stockVegetableList.size() == 0){
                    getVegetableStockDetailsRequest(cycleId);
                }
                else {
                    stockVegetableList = (ArrayList<Items>) database.getVegetableStockTableDetails(Integer.parseInt(cycleId));
                    CustomVegetableStockAdapter stockAdapter = new CustomVegetableStockAdapter
                            (getApplicationContext(),stockVegetableList);
                    recyclerView.setAdapter(stockAdapter);
                }

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
    public class SpinnerAdapter extends BaseAdapter {

        Context mContext;
        Activity activity;
        LayoutInflater inflater;
        private List<Items> deliveryCycleDetails = null;
        private ArrayList<Items> arraylist;
        TextView criteriaTextview;
        public SpinnerAdapter(Activity a, ArrayList<Items> arraylist) {
            mContext = a;
            activity = a;
            this.deliveryCycleDetails = arraylist;
            inflater = LayoutInflater.from(mContext);
            this.arraylist = new ArrayList<Items>();
            this.arraylist.addAll(deliveryCycleDetails);

        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub

            return deliveryCycleDetails.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {

            return position;
        }

        public class ViewHolder {

            TextView spinnerText,positionText;
        }

        public View getView(final int position, View view, ViewGroup parent) {
            final int positions = position;
            ViewHolder holder;
            holder = new ViewHolder();
            if (view == null) {

                view = inflater.inflate(R.layout.custom_spinner, null);

                holder.spinnerText = (TextView) view.findViewById(R.id.textView44);

                view.setTag(holder);/**/

            } else {
                holder = (ViewHolder) view.getTag();
            }

            holder.spinnerText.setText(deliveryCycleDetails.get(position).getDeliveryNumber()+" ( "+
                    deliveryCycleDetails.get(position).getDeliveryDate()+"  )");
            holder.spinnerText.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    spinner.setVisibility(View.INVISIBLE);
                    spinnerText.setVisibility(View.VISIBLE);
                    spinnerText.setText(deliveryCycleDetails.get(position).getDeliveryNumber()+" ( "+
                            deliveryCycleDetails.get(position).getDeliveryDate()+"  )");
                    cycleId = deliveryCycleDetails.get(position).getSubscriptionDeliveryId();

                }
            });

            return view;

        }

    }


}


