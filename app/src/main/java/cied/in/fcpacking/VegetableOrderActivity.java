package cied.in.fcpacking;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
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
import cied.in.fcpacking.Model.Items;
import cied.in.fcpacking.Model.URL;

public class VegetableOrderActivity extends AppCompatActivity {

    TextView updateButton,syncButton,summaryButton,addFarmButton,goButton,spinnerTextview;
    RecyclerView recyclerView;
    EditText filterText;
    FCDatabase database;
    ImageView backIcon,refreshIcon;
    int flag = 0,count = 0;
    String [] FARMID;
    Spinner cycleSpinner;
    ArrayList<Items> orderedVeg = new ArrayList<Items>();
    float totalRecievedQty = 0;
    CustomOrderList customOrderList;
    CustomVegetableOrderAdapter adapter;
    String getDeliveryCycleUrl;
    SessionManager sessionManager;
    ArrayList<Items> vegetableOrderTableList = new ArrayList<Items>();
    ArrayList<Items> vegeOrderTableList = new ArrayList<Items>();
    ArrayList<Items> deliveryCycleDetails = new ArrayList<Items>();
    ArrayList<Items> farmDetails = new ArrayList<Items>();
    SpinnerAdapter spinnerAdapter;
    String cycleId,nonPreferanceStatus;
    URL url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vegetable_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        filterText = (EditText) findViewById(R.id.editText4);
        addFarmButton = (TextView) findViewById(R.id.textView67);
        spinnerTextview = (TextView) findViewById(R.id.textView66);
        summaryButton = (TextView) findViewById(R.id.summary);
        goButton = (TextView) findViewById(R.id.textView65);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        updateButton = (TextView) findViewById(R.id.updateButton);
        backIcon = (ImageView) toolbar.findViewById(R.id.imageView);
        refreshIcon = (ImageView) toolbar.findViewById(R.id.imageView4);
        syncButton = (TextView) toolbar.findViewById(R.id.textView28);
        cycleSpinner = (Spinner) findViewById(R.id.spinner2);
        database = new FCDatabase(this);
        sessionManager = new SessionManager(this);
        LinearLayoutManager layoutManagaer
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManagaer);

        spinnerTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerTextview.setVisibility(View.GONE);
                cycleSpinner.setVisibility(View.VISIBLE);
                cycleSpinner.performClick();
            }
        });
        deliveryCycleDetails = (ArrayList<Items>) database.getDeliveryCycleTable();

        if(deliveryCycleDetails.size() == 0){

            getDeliveryCycleDetails();
        }
        else {
            cycleId = deliveryCycleDetails.get(0).getSubscriptionDeliveryId();
            deliveryCycleDetails = (ArrayList<Items>) database.getDeliveryCycleTable();
            spinnerTextview.setText(deliveryCycleDetails.get(0).getDeliveryNumber()+" ( "+
                    deliveryCycleDetails.get(0).getDeliveryDate()+"  )");
            spinnerAdapter = new SpinnerAdapter(VegetableOrderActivity.this,deliveryCycleDetails);
            cycleSpinner.setAdapter(spinnerAdapter);
            vegeOrderTableList = (ArrayList<Items>) database.getVegetableOrderTableDetails
                    (Integer.parseInt(deliveryCycleDetails.get(0).getSubscriptionDeliveryId()));
            if(vegeOrderTableList.size() == 0){
                getOrderedVegetableDetailsRequest(deliveryCycleDetails.get(0).getSubscriptionDeliveryId());
            }
            else {
                ArrayList<Items> vegetablaDetails = new ArrayList<Items>();
                vegetablaDetails = (ArrayList<Items>) database.getVegetableOrderTableDetails
                        (Integer.parseInt(deliveryCycleDetails.get(0).getSubscriptionDeliveryId()));
                vegeOrderTableList = removeDuplicateValue(vegetablaDetails);
                Log.e("vegeOrderTableList..",""+vegeOrderTableList.size());
                adapter = new CustomVegetableOrderAdapter
                        (getApplicationContext(),vegeOrderTableList);
                recyclerView.setAdapter(adapter);

            }

        }

        filterText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                vegetableOrderTableList.clear();
                vegetableOrderTableList = (ArrayList<Items>) database.getVegetableOrderTableDetails
                        (""+charSequence,cycleId);
                adapter = new CustomVegetableOrderAdapter
                        (getApplicationContext(),vegetableOrderTableList);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vegeOrderTableList = (ArrayList<Items>) database.getVegetableOrderTableDetails
                        (Integer.parseInt(cycleId));
                if(vegeOrderTableList.size() == 0){
                    getOrderedVegetableDetailsRequest(cycleId);
                }
                else {
                    ArrayList<Items> vegetablaDetails = new ArrayList<Items>();
                    vegetablaDetails = (ArrayList<Items>) database.getVegetableOrderTableDetails
                            (Integer.parseInt(cycleId));
                    vegeOrderTableList = removeDuplicateValue(vegetablaDetails);
                    adapter = new CustomVegetableOrderAdapter
                            (getApplicationContext(),vegeOrderTableList);
                    recyclerView.setAdapter(adapter);
                    /*vegeOrderTableList = (ArrayList<Items>) database.getVegetableOrderTableDetails
                            (Integer.parseInt(cycleId));
                    adapter = new CustomVegetableOrderAdapter
                            (getApplicationContext(),vegeOrderTableList);
                    recyclerView.setAdapter(adapter);*/
                    adapter.notifyDataSetChanged();
                }
            }
        });
        addFarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddFarmActivity.class);
                startActivity(intent);
            }
        });
        syncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(VegetableOrderActivity.this);
                builder.setMessage("Are you want sync data?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.e("flag..",""+flag);
                                postvegetableDetails();
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
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        summaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),SummaryActivity.class);
                intent.putExtra("CYCLEID",cycleId);
                startActivity(intent);
            }
        });
        refreshIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(VegetableOrderActivity.this);
                builder.setMessage("Are you want refresh data?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                database.deleteDeliveryDetailsTable();
                                database.deleteVegetableOrderDetailsTable();
                                database.deleteOrderedVegetableTable();
                                Intent intent = new Intent(getApplicationContext(),
                                        VegetableOrderActivity.class);
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
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

    public void getOrderedVegetableDetailsRequest(final String cycleId) {  // =========  add non preference status in database

        //String getVegOrderUrl = url.liveRootUrl+"farm/get-sourced-vegetables/?cycle_id="+cycleId;
        String getVegOrderUrl = url.rootUrl +"subscription/get-vegetable-sourcing-details/?subscription_cycle_id="+cycleId;
        Log.e("url....",getVegOrderUrl);
        final ProgressDialog dialog = new ProgressDialog(VegetableOrderActivity.this,R.style.
                AppCompatAlertDialogStyle);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Loading... ");
        dialog.show();

        final JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,getVegOrderUrl,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                dialog.dismiss();
                Log.e(" Response ",response.toString());
                try {
                    JSONArray jsonArray = response.getJSONArray("ordered_vegetables");
                    for (int i= 0 ; i<jsonArray.length(); i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        JSONArray farmJsonArray = jsonObject.getJSONArray("farm");
                        for (int j= 0 ; j<farmJsonArray.length(); j++){
                            JSONObject object = farmJsonArray.getJSONObject(j);
                            database.addToVegetableOrderTable(new Items(jsonObject.getString("vegetable_id"),
                                    jsonObject.getString("vegetable_name"),
                                    jsonObject.getString("total_quantity"),
                                    object.getString("ordered_quantity"),
                                    object.getString("farm"),
                                    jsonObject.getString("sourced_vegetable_id"),1,cycleId,
                                    jsonObject.getString("is_available_for_weekly_once"),
                                    jsonObject.getString("preferenceless_quantity"),
                                    jsonObject.getString("preference_given_subscription_quantity"),
                                    jsonObject.getString("trade_quantity"),
                                    jsonObject.getString("vegetable_unit"),
                                    jsonObject.getString("custom_quantity")));
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                vegetableOrderTableList.clear();
                vegetableOrderTableList = (ArrayList<Items>) database.getVegetableOrderTableDetails
                        (Integer.parseInt(cycleId));
                vegeOrderTableList = removeDuplicateValue(vegetableOrderTableList);
                Log.e("ww...",""+vegeOrderTableList.size());
                adapter = new CustomVegetableOrderAdapter
                        (getApplicationContext(),vegeOrderTableList);
                recyclerView.setAdapter(adapter);

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

    public void getVegetableOrderDetailsRequest(final String cycleId) {

       /* String getVegOrderUrl = url.liveRootUrl+"subscription/get-vegetable-ordering-details?" +
                "subscription_cycle_id="+cycleId;*/
        String getVegOrderUrl = url.rootUrl +"subscription/get-vegetable-ordering-details?" +
                "subscription_cycle_id="+cycleId;
        Log.e("url.....",getVegOrderUrl);
        final ProgressDialog dialog = new ProgressDialog(VegetableOrderActivity.this,R.style.
                AppCompatAlertDialogStyle);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Loading... ");
        dialog.show();

        final JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,getVegOrderUrl,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                Log.e(" Response ",response.toString());
                try {
                    JSONArray jsonArray = response.getJSONArray("ordered_vegetables");
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                       /* database.addToVegetableOrderTable(new Items(jsonObject.getString("vegetable_id"),
                                jsonObject.getString("vegetable_name"),
                                jsonObject.getString("total_quantity")
                                ,jsonObject.getString("total_quantity"),"39","",1,cycleId));*/
                    }
                    JSONArray jsonArray1 = response.getJSONArray("vegetables");
                    for (int i=0 ; i<jsonArray1.length(); i++){
                        JSONObject object = jsonArray1.getJSONObject(i);
                        database.addtoOrderedVegetableTable(new Items
                                (1,object.getString("vegetable_id"),object.getString("vegetable_name")));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                vegetableOrderTableList.clear();
                vegetableOrderTableList = (ArrayList<Items>) database.getVegetableOrderTableDetails(Integer.parseInt(cycleId));
                adapter = new CustomVegetableOrderAdapter
                        (getApplicationContext(),vegetableOrderTableList);
                recyclerView.setAdapter(adapter);

                dialog.dismiss();

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
                    Log.e("AuthFailureError", "AuthFailureError......");
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
    public void getDeliveryCycleDetails(){
        getDeliveryCycleUrl = url.getDeliveryCycleDetailsUrl;
        final ProgressDialog dialog = new ProgressDialog(VegetableOrderActivity .this,R.style.
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
                spinnerTextview.setText(deliveryCycleDetails.get(0).getDeliveryNumber()+" ( "+
                        deliveryCycleDetails.get(0).getDeliveryDate()+"  )");
                spinnerAdapter = new SpinnerAdapter(VegetableOrderActivity.this,deliveryCycleDetails);
                cycleSpinner.setAdapter(spinnerAdapter);
                vegeOrderTableList = (ArrayList<Items>) database.getVegetableOrderTableDetails
                        (Integer.parseInt(cycleId));
                if(vegeOrderTableList.size() == 0){
                    getOrderedVegetableDetailsRequest(deliveryCycleDetails.get(0).
                            getSubscriptionDeliveryId());
                }
                else {
                    vegeOrderTableList = (ArrayList<Items>) database.getVegetableOrderTableDetails
                            (Integer.parseInt(deliveryCycleDetails.get(0).getSubscriptionDeliveryId()));
                    adapter = new CustomVegetableOrderAdapter
                            (getApplicationContext(),vegeOrderTableList);
                    recyclerView.setAdapter(adapter);
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
                    cycleSpinner.setVisibility(View.INVISIBLE);
                    spinnerTextview.setVisibility(View.VISIBLE);
                    spinnerTextview.setText(deliveryCycleDetails.get(position).getDeliveryNumber()+" ( "+
                            deliveryCycleDetails.get(position).getDeliveryDate()+"  )");
                    cycleId = deliveryCycleDetails.get(position).getSubscriptionDeliveryId();

                }
            });

            return view;

        }

    }
    public ArrayList removeDuplicateValue(ArrayList<Items> vegeOrderTableList){
        int count = vegeOrderTableList.size();
        for (int i=0; i<count ;i++){
            for (int j = i + 1; j < count; j++)
            {
                if (vegeOrderTableList.get(i).getVegetableId().equals(vegeOrderTableList.get(j).
                        getVegetableId())){
                    vegeOrderTableList.remove(j--);
                    count--;
                }
            }

        }

        return vegeOrderTableList;
    }
    public class CustomVegetableOrderAdapter extends RecyclerView.Adapter<CustomVegetableOrderAdapter.MyViewHolder> {

        private List<Items> vegetableOrderList;
        Context mcontext;
        FCDatabase database;
        ArrayList<Items> farmList = new ArrayList<Items>();
        ArrayList<Items> vegOrderList = new ArrayList<Items>();
        String qty;
        int cycleId;
        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView vegetableNameText,qtyText,orderQty,nonPrefQtyText,prefQtyText,
                    tradeQtyText,customQtyText;
            RecyclerView recyclerView1;
            RelativeLayout relativeLayout,demoLayout;
            Switch toggleButton;
            public MyViewHolder(View view) {
                super(view);

                toggleButton = (Switch) view.findViewById(R.id.switch_btn);
                vegetableNameText = (TextView) view.findViewById(R.id.vegNameText);
                customQtyText = (TextView) view.findViewById(R.id.textView103);
                qtyText = (TextView) view.findViewById(R.id.qtyText);
                nonPrefQtyText = (TextView) view.findViewById(R.id.textView85);
                prefQtyText = (TextView) view.findViewById(R.id.textView93);
                tradeQtyText = (TextView) view.findViewById(R.id.textView96);
                orderQty= (TextView) view.findViewById(R.id.textView42);
                recyclerView1 = (RecyclerView) view.findViewById(R.id.recyclerView1);
                relativeLayout = (RelativeLayout) view.findViewById(R.id.topLayout);
                demoLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
                recyclerView1.setHasFixedSize(true);
                recyclerView1.setLayoutManager(new LinearLayoutManager
                        (mcontext, LinearLayoutManager.VERTICAL, false));

            }
        }

        public CustomVegetableOrderAdapter(Context context, List<Items> list) {
            this.vegetableOrderList = list;
            this.mcontext = context;
            database = new FCDatabase(mcontext);

        }

        @Override
        public CustomVegetableOrderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                           int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.custom_order_list, parent, false);


            return new CustomVegetableOrderAdapter.MyViewHolder(itemView);
        }




        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            totalRecievedQty= 0;
            holder.qtyText.setText(vegetableOrderList.get(position).getOrderedQuantity()+
                    " "+vegetableOrderList.get(position).getUnit());
            holder.vegetableNameText.setText(vegetableOrderList.get(position).getVegetableName());
            holder.nonPrefQtyText.setText("Non Pref. Qty : "+vegetableOrderList.get(position).getNonPreferanceQuantity());
            holder.prefQtyText.setText(vegetableOrderList.get(position).getPreferanceQuantity());
            holder.customQtyText.setText(vegetableOrderList.get(position).getCustomQuantity());
            holder.tradeQtyText.setText(vegetableOrderList.get(position).getTradeQuantity());
            cycleId = Integer.parseInt(vegetableOrderList.get(position).getSubscriptionDeliveryId());
            vegOrderList = (ArrayList<Items>) database.getVegetableOrderTableDetails
                    (cycleId,vegetableOrderList.get(position).getVegetableId());
            customOrderList = new CustomOrderList(getApplicationContext(),vegOrderList);
            holder.recyclerView1.setAdapter(customOrderList);
            FARMID = new String[vegOrderList.size()];
            for (int i = 0; i<vegOrderList.size(); i++){
                totalRecievedQty =totalRecievedQty+ Float.parseFloat(vegOrderList.get(i).
                        getReceivedQuantity());
                FARMID[i] = vegOrderList.get(i).getFarmId();
            }
            holder.orderQty.setText(" ( "+ totalRecievedQty+" )");
            if(vegetableOrderList.get(position).getNonPreferanceStatus().equals("true")){
                holder.toggleButton.setChecked(true);
            }
            else {
                holder.toggleButton.setChecked(false);
            }
            if((totalRecievedQty < Float.parseFloat(vegOrderList.get(0).getOrderedQuantity())||
                    (checkFarmId("39",vegetableOrderList.get(position).getVegetableId(),
                            vegetableOrderList.get(position).getSubscriptionDeliveryId())))){
                holder.orderQty.setTextColor(Color.parseColor("#000000"));
                holder.vegetableNameText.setTextColor(Color.parseColor("#000000"));
            }
            else {
                holder.orderQty.setTextColor(Color.parseColor("#FF02B736"));
                holder.vegetableNameText.setTextColor(Color.parseColor("#186A3B"));
            }

            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    setVegetableOrderPopup(vegetableOrderList.get(position).getVegetableId(),
                            holder.recyclerView1,holder.qtyText,vegetableOrderList.get(position).
                                    getVegetableName(),position, vegetableOrderList.get(position).
                                    getOrderedQuantity(),holder.orderQty.getText().toString(),vegetableOrderList.get(position).
                                    getSubscriptionDeliveryId(),FARMID,vegetableOrderList.get(position).
                                    getNonPreferanceStatus(),vegetableOrderList.get(position).getUnit(),
                                    vegetableOrderList.get(position).getNonPreferanceQuantity(),
                                    vegetableOrderList.get(position).getPreferanceQuantity(),
                                    vegetableOrderList.get(position).getTradeQuantity(),
                                    vegetableOrderList.get(position).getCustomQuantity());
                }
            });

            holder.toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    Log.e("nonPref. status.", "......"+isChecked);
                    nonPreferanceStatus = ""+isChecked;
                    vegetableOrderList.get(position).setNonPreferanceStatus(nonPreferanceStatus);
                    database.updateNonPrefStatusToVegetableOrderTable(new Items(nonPreferanceStatus),
                            vegetableOrderList.get(position).getVegetableId(),
                            vegetableOrderList.get(position).getSubscriptionDeliveryId());

                }
            });

            holder.demoLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.relativeLayout.performClick();

                }
            });
        }

        @Override
        public int getItemCount() {
            return vegetableOrderList.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }
    }

    public void setVegetableOrderPopup(final String vegetableId, final RecyclerView list,
                                       final TextView textView, final String vegetableName,
                                       final int position, final String reqQuantity,
                                       String totalQty, final String cycleId,
                                       String[] farmId, final String nonPreferance_Status,
                                       final String unit,final String nonPrefQty,
                                       final String prefQty,final String tradeQty,final String customQty) {

        final String qty;String TotalQuantity = "";
        TotalQuantity = totalQty.replace("(","");
        TotalQuantity = TotalQuantity.replace(")",""); ;
        Log.e("TotalQuantity...",TotalQuantity);
        float totalRecievedQty = Float.parseFloat(TotalQuantity);
        ArrayList<Items> vegOrderList = new ArrayList<Items>();
        ArrayList<Items> farmDetails = new ArrayList<Items>();
        final Dialog dialogbox = new Dialog(VegetableOrderActivity.this);
        dialogbox.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogbox.setContentView(R.layout.vege_order_popup);

        TextView doneButton = (TextView) dialogbox.findViewById(R.id.textView34);
        TextView reqText = (TextView) dialogbox.findViewById(R.id.textView26);
        Log.e("cycleId.....",""+cycleId);
        if(reqQuantity.length()> 3){
            qty = reqQuantity.substring(0,4);
            reqText.setText(qty+" Kg ( "+ TotalQuantity +" ) ");
        }
        else {
            qty = reqQuantity;
            reqText.setText(qty+" Kg ( "+ TotalQuantity +" ) ");
        }

        EditText vegNameText = (EditText) dialogbox.findViewById(R.id.editText12);
        EditText searchText = (EditText) dialogbox.findViewById(R.id.editText7);
        final RecyclerView popupRecyclerView = (RecyclerView) dialogbox.findViewById(R.id.recyclerview) ;
        LinearLayoutManager layoutManagaer
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        popupRecyclerView.setLayoutManager(layoutManagaer);
        ArrayList<Items> farmList = new ArrayList<Items>();
        farmList= (ArrayList<Items>) database.getAddedFarmDetailsTableDetails();
        if(farmList.size() == 0){
            farmDetails = (ArrayList<Items>) database.getFarmDetailsTableDetails(farmId);
            CustomPopupAdapter customPopupAdapter = new CustomPopupAdapter(
                    getApplicationContext(),farmDetails,vegetableId,vegetableName,qty,
                    totalRecievedQty,cycleId,nonPreferance_Status,unit,nonPrefQty,prefQty,tradeQty,customQty);
            popupRecyclerView.setAdapter(customPopupAdapter);
        }
        else {
            CustomPopupAdapter customPopupAdapter = new CustomPopupAdapter(
                    getApplicationContext(),farmList,vegetableId,vegetableName,qty,totalRecievedQty,
                    cycleId,nonPreferance_Status,unit,nonPrefQty,prefQty,tradeQty,customQty);
            popupRecyclerView.setAdapter(customPopupAdapter);
        }

        vegOrderList = (ArrayList<Items>) database.getVegetableOrderTableDetails
                (Integer.parseInt(cycleId),vegetableId);
        for (int i = 0; i<vegOrderList.size(); i++){
            totalRecievedQty =totalRecievedQty+ Float.parseFloat(vegOrderList.get(i).getReceivedQuantity());
        }
        final float finalTotalRecievedQty = totalRecievedQty;
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<Items> searchedFarmList = new ArrayList<Items>();
                searchedFarmList= (ArrayList<Items>) database.getAddedFarmDetailsTableDetails(s.toString());
                CustomPopupAdapter customPopupAdapter = new CustomPopupAdapter(
                        getApplicationContext(),searchedFarmList,vegetableId,vegetableName,qty,
                        finalTotalRecievedQty,cycleId,nonPreferance_Status,unit,nonPrefQty,prefQty,tradeQty,customQty);
                popupRecyclerView.setAdapter(customPopupAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        String text = vegetableName;
        vegNameText.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);
        vegNameText.setKeyListener(null);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vegetableOrderTableList.clear();
                ArrayList<Items> vegOrderList = new ArrayList<Items>();
                orderedVeg = (ArrayList<Items>) database.getVegetableOrderTableDetails
                        (Integer.parseInt(cycleId),vegetableId);
                vegetableOrderTableList = (ArrayList<Items>)
                        database.getVegetableOrderTableDetailsForRemoveDuplicate(cycleId);
                vegeOrderTableList = removeDuplicateValue(vegetableOrderTableList);
                Log.e("vegeOrderTableList..",""+vegeOrderTableList.size());
                adapter = new CustomVegetableOrderAdapter
                        (getApplicationContext(),vegeOrderTableList);
                recyclerView.setAdapter(adapter);
                recyclerView.scrollToPosition(position);
                vegOrderList = (ArrayList<Items>) database.getVegetableOrderTableDetails
                        (Integer.parseInt(cycleId),vegetableId);
                customOrderList = new CustomOrderList(getApplicationContext(),vegOrderList);
                list.setAdapter(customOrderList);
                customOrderList.notifyDataSetChanged();
                list.invalidate();
                dialogbox.dismiss();
            }
        });

        dialogbox.show();

    }
    public class CustomOrderList extends RecyclerView.Adapter<CustomOrderList.MyViewHolder> {

        private List<Items> vegetableOrder;
        Context mcontext;
        FCDatabase database;
        ArrayList<Items> farmDetails = new ArrayList<Items>();

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView vegetableNameText,qtyText;
            public MyViewHolder(View view) {
                super(view);

                vegetableNameText = (TextView) view.findViewById(R.id.textView35);
                qtyText = (TextView) view.findViewById(R.id.textView45);


            }
        }

        public CustomOrderList(Context context, List<Items> list) {
            this.vegetableOrder = list;
            this.mcontext = context;
            database = new FCDatabase(mcontext);

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.custom_order_layout, parent, false);


            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {


            if(vegetableOrder.get(position).getFarmId().equals("")){
                holder.qtyText.setVisibility(View.GONE);
                holder.vegetableNameText.setVisibility(View.GONE);
            }
            else {
                holder.qtyText.setVisibility(View.VISIBLE);
                holder.vegetableNameText.setVisibility(View.VISIBLE);
                farmDetails = (ArrayList<Items>) database.getFarmDetailsTableDetails
                        (vegetableOrder.get(position).getFarmId());
                Log.e("farm details......",""+farmDetails.size());
                holder.qtyText.setText(vegetableOrder.get(position).getReceivedQuantity());
                holder.vegetableNameText.setText(farmDetails.get(0).getFarmName());

            }

        }

        @Override
        public int getItemCount() {
            return vegetableOrder.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }
    }
    public boolean checkFarmId(String farmId,String vegId,String cycleId){
        boolean returnValue = false;
        ArrayList<Items> vegOrderList = new ArrayList<Items>();
        vegOrderList = (ArrayList<Items>) database.getVegetableOrderTableDetails
                (Integer.parseInt(cycleId),vegId);
        for (int i=0; i<vegOrderList.size(); i++){
            if(farmId.equals(vegOrderList.get(i).getFarmId())){
                returnValue =  true;
                break;
            }
            else {
                returnValue = false;
            }
        }
        return returnValue;
    }

    public class CustomPopupAdapter extends RecyclerView.Adapter<CustomPopupAdapter.MyViewHolder> {

        private List<Items> farmDetails;
        Context mcontext;
        String vegetableName, vegetableId,reqQuantity,cycleId,nonPrefStatus,unit,
                nonPrefQty,prefQty,tradeQty,customQty;
        FCDatabase database;
        float totalQty;
        ArrayList<Items> farmDetailsList = new ArrayList<Items>();
        ArrayList<Items> vegOrderList = new ArrayList<Items>();

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView farmNameText;
            EditText qtyText;
            CheckBox checkBox;
            public MyViewHolder(View view) {
                super(view);

                farmNameText = (TextView) view.findViewById(R.id.textView35);
                qtyText = (EditText) view.findViewById(R.id.editText13);
                checkBox = (CheckBox) view.findViewById(R.id.checkBox8);

            }
        }

        public CustomPopupAdapter(Context context, List<Items> list,String vegetableid,
                                  String vegetablename,String qty,float QTY,String cycleid,
                                  String nonPref_status,String veg_unit,String nonPref_Qty,
                                  String pre_fQty,String trade_Qty,String custom_Qty) {
            this.farmDetails = list;
            this.mcontext = context;
            database = new FCDatabase(mcontext);
            vegetableId = vegetableid;
            vegetableName = vegetablename;
            reqQuantity = qty;
            totalQty = QTY;
            cycleId = cycleid;
            nonPrefStatus = nonPref_status;
            unit = veg_unit;
            nonPrefQty = nonPref_Qty;
            prefQty = pre_fQty;
            tradeQty = trade_Qty;
            customQty = custom_Qty;

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.custom_farm_list, parent, false);


            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            holder.farmNameText.setText(farmDetails.get(position).getFarmName());
            vegOrderList = (ArrayList<Items>) database.getVegetableOrderTableDetails
                    (farmDetails.get(position).getFarmId(),1,cycleId);
            holder.qtyText.setText(reqQuantity);
            if(checkFarmId(farmDetails.get(position).getFarmId(),vegetableId,cycleId)){
                holder.checkBox.setChecked(true);
                Log.e("farm id...",farmDetails.get(position).getFarmId());
                vegOrderList = (ArrayList<Items>) database.getVegetableOrderTableDetailsForFarms
                        (farmDetails.get(position).getFarmId(),1,cycleId,vegetableId);
                Log.e("qty...",vegOrderList.get(0).getReceivedQuantity());
                holder.qtyText.setText(vegOrderList.get(0).getReceivedQuantity());
            }
            else {
                holder.checkBox.setChecked(false);}
            if( holder.checkBox.isChecked()){
                holder.qtyText.setEnabled(false);
            }
            else {
                holder.qtyText.setEnabled(true);
            }
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    farmDetailsList = (ArrayList<Items>)
                            database.getVegetableOrderTableDetailsForNotsetFarm(vegetableId);
                    if(farmDetailsList.size() == 0){

                    }
                    else {
                        database.deleteVegetableOrderDetailsTable(vegetableId,"39");
                    }
                    try {
                        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                    if(holder.checkBox.isChecked()){
                        holder.qtyText.setEnabled(false);
                        database.addToVegetableOrderTable(new Items
                                (vegetableId,vegetableName,reqQuantity,holder.qtyText.getText().toString()
                                        ,farmDetails.get(position).getFarmId(),"0",1,cycleId,
                                        nonPrefStatus,nonPrefQty,prefQty,tradeQty,unit,customQty));

                    }
                    else {
                        holder.qtyText.setEnabled(true);
                        database.deleteVegetableOrderDetailsTable(vegetableId,farmDetails.
                                get(position).getFarmId());
                    }

                }
            });

        }

        @Override
        public int getItemCount() {
            return farmDetails.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }
    }
    public void postvegetableDetails(){

        JSONObject vegOrderJsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        vegetableOrderTableList = (ArrayList<Items>) database.getVegetableOrderTableDetailsForSync(cycleId);
        for (int i=0; i<vegetableOrderTableList.size() ; i++){
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("vegetable",Integer.parseInt(vegetableOrderTableList.get(i).getVegetableId()));
                jsonObject.put("cycle_id",Integer.parseInt(vegetableOrderTableList.get(i).getSubscriptionDeliveryId()));
                jsonObject.put("farm",Integer.parseInt(vegetableOrderTableList.get(i).getFarmId()));
                jsonObject.put("non_preference_veg_status",vegetableOrderTableList.get(i).getNonPreferanceStatus());
                jsonObject.put("ordered_quantity",Float.parseFloat(vegetableOrderTableList.get(i)
                        .getReceivedQuantity()));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(jsonObject);
        }
        Log.e("post...values...",jsonArray.toString());
        postVegetableOrderRequest(jsonArray);
    }
    public void patchVegetableDetails(){


        JSONObject vegOrderJsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        vegetableOrderTableList = (ArrayList<Items>) database.getVegetableOrderTableDetails();
        for (int i=0; i<vegetableOrderTableList.size() ; i++){

            JSONObject jsonObject = new JSONObject();
            try {
                if(vegetableOrderTableList.get(i).getCycleId().equals("0.0")){
                    jsonObject.put("id",0);
                }
                else {
                    jsonObject.put("id",Integer.parseInt(vegetableOrderTableList.get(i).getCycleId()));
                }
                jsonObject.put("wastage",0);
                jsonObject.put("excess_quantity",0);
                jsonObject.put("rating",0);
                jsonObject.put("total_quantity",0);
                jsonObject.put("vegetable_id",Integer.parseInt(vegetableOrderTableList.get(i).
                        getVegetableId()));
                jsonObject.put("farm_id",Integer.parseInt(vegetableOrderTableList.get(i).getFarmId()));
                jsonObject.put("ordered_quantity",Float.parseFloat(vegetableOrderTableList.get(i).
                        getReceivedQuantity()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(jsonObject);
        }
        try {
            vegOrderJsonObject.put("",jsonArray);
            Log.e("patch...values...",jsonArray.toString());
            patchVegetableOrderRequest(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void postVegetableOrderRequest(JSONArray jsonArray) {

        //String postVegetableOrderUrl  = url.liveRootUrl+"sourced-vegetable/";
        String postVegetableOrderUrl  = url.rootUrl +"farm/create-sourced-vegetables/";
        final ProgressDialog dialog = new ProgressDialog(VegetableOrderActivity.this,R.style.
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
                    //patchVegetableDetails();

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

    public void patchVegetableOrderRequest(JSONArray jsonArray) {

        String patchVegetableOrderurl  = url.rootUrl +"farm/update-sourced-vegetables/";
        //String patchVegetableOrderurl  = url.liveRootUrl+"farm/update-sourced-vegetables/";
        final ProgressDialog dialog = new ProgressDialog(VegetableOrderActivity.this,R.style.
                AppCompatAlertDialogStyle);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Loading... ");
        dialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, patchVegetableOrderurl,
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
                    // patchVegetableDetails();

                } else if (error instanceof NetworkError) {
                    dialog.dismiss();

                    Toast.makeText(getApplicationContext(),"No internet connection",Toast.LENGTH_SHORT).show();
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

}
