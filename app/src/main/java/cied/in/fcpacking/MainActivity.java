package cied.in.fcpacking;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
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
import cied.in.fcpacking.ListAdapter.CustomerListAdapter;
import cied.in.fcpacking.Model.Items;
import cied.in.fcpacking.Model.URL;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    EditText filterText;
    TextView searchButton,syncButton,filterButton,goButton,spinnerText;
    RecyclerView recyclerView;
    Spinner spinner;
    FCDatabase database;
    String getCustomerDetailsUrl,fsid,fsId,cycleId;
    SessionManager manager;
    JSONArray subVegetableJsonArray = new JSONArray();
    String cyclieId;
    NavigationView navigationView;
    int customerId;
    URL url;
    String boxCompletedStatus = "true",boxFilledStatus = "true",zone = "Select zone",zoneId;
    String prefStatus = "'true' or preferanceStatus = 'false'",filledStatus = "'true' or filledStatus = 'false' ",
            Status = "'ACTIVE' or status = 'SAMPLE'";
    ArrayList<Items> customerDetails = new ArrayList<Items>();
    ArrayList<Items> subVegetableDetails = new ArrayList<Items>();
    ArrayList<Items> customVegetableDetails = new ArrayList<Items>();
    ArrayList<Items> complaintsDetails = new ArrayList<Items>();
    ArrayList<Items> deliveryCycleDetails= new ArrayList<Items>();
    ArrayList<Items> farmDetails= new ArrayList<Items>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);
        filterText = (EditText) findViewById(R.id.filterText);
        searchButton = (TextView) findViewById(R.id.searchBtn);
        goButton = (TextView) findViewById(R.id.textView22);
        spinnerText = (TextView) findViewById(R.id.textView84);
        spinner = (Spinner) findViewById(R.id.spinner);
        syncButton = (TextView) findViewById(R.id.syncButton);
        filterButton = (TextView) findViewById(R.id.textView2);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        LinearLayoutManager layoutManagaer
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManagaer);
        database = new FCDatabase(this);
        manager = new SessionManager(this);
        final HashMap<String, String> getCycleId = manager.getCycleId();
        cyclieId = getCycleId.get(SessionManager.KEY_CYCLE_ID);
        spinnerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerText.setVisibility(View.INVISIBLE);
                spinner.setVisibility(View.VISIBLE);
                spinner.performClick();
            }
        });
        farmDetails = (ArrayList<Items>) database.getFarmDetailsTableDetails();
        if(farmDetails.size() == 0){
            getFarmDetailsRequest();
        }
        else {
            deliveryCycleDetails = (ArrayList<Items>) database.getDeliveryCycleTable();
            if(deliveryCycleDetails.size() == 0){
                getDeliveryCycleDetails();
            }
            else {
                cycleId = deliveryCycleDetails.get(1).getSubscriptionDeliveryId();
                spinnerText.setText(deliveryCycleDetails.get(1).getDeliveryNumber()+" ( "+
                        deliveryCycleDetails.get(1).getDeliveryDate()+"  )");
                SpinnerAdapter spinnerAdapter = new SpinnerAdapter(MainActivity.this,deliveryCycleDetails);
                spinner.setAdapter(spinnerAdapter);
                customerDetails = (ArrayList<Items>) database.getCustomerDetailsTable(Integer.parseInt(cycleId));
                if(customerDetails.size() == 0){
                    getCustomerDetailsRequest(cycleId);
                }
                else {
                    CustomerListAdapter adapter = new CustomerListAdapter(MainActivity.this,customerDetails);
                    recyclerView.setAdapter(adapter);
                }
            }

        }

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setFilter();
            }
        });

    /*    goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                customerDetails = (ArrayList<Items>) database.getCustomerDetailsTable(Integer.parseInt(cycleId));
                if(customerDetails.size() == 0){
                    getCustomerDetailsRequest(cycleId);
                }
                else {
                    CustomerListAdapter adapter = new CustomerListAdapter(MainActivity.this,customerDetails);
                    recyclerView.setAdapter(adapter);
                }            }
        });
*/
        syncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Are you want sync data?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //postComplaints();
                                postSubVegDetails();
                               // postCustomVegDatas();
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
                    customerDetails = (ArrayList<Items>) database.getCustomerDetailsTable(Integer.parseInt(cycleId));
                    if(customerDetails.size() == 0) {

                        errorPopup("FSID Not Found");
                    }
                    else{

                        CustomerListAdapter adapter = new CustomerListAdapter(MainActivity.this,customerDetails);
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

                    Log.e("fsid. ", fsid);

                if(digitsOnly){
                    Log.e("asdfas........",fsid);
                    customerDetails = (ArrayList<Items>) database.getCustomerDetailsTable(newFsid,cycleId);
                    if(customerDetails.size() == 0) {

                        errorPopup("FSID Not Found");
                    }
                    else{

                        CustomerListAdapter adapter = new CustomerListAdapter(MainActivity.this,customerDetails);
                        recyclerView.setAdapter(adapter);
                    }
                }
                else {
                    customerDetails = (ArrayList<Items>) database.getCustomerDetailsTable(fsid,Integer.parseInt(cycleId));
                    if(customerDetails.size() == 0) {
                        errorPopup("Name Not Found");
                    }
                    else{

                        CustomerListAdapter adapter = new CustomerListAdapter(MainActivity.this,customerDetails);
                        recyclerView.setAdapter(adapter);
                    }
                }

            }
        });

    }

    public void postSubVegDetails() {
        Log.e("size....",""+cycleId);
        subVegetableDetails = (ArrayList<Items>) database.getVegetableDetailsTable(Integer.parseInt(cycleId));
        Log.e("size....",""+subVegetableDetails.size());
        for (int i=0; i<subVegetableDetails.size() ; i++)
        {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("customer",subVegetableDetails.get(i).getCustomerId());
                jsonObject.put("vegetable",subVegetableDetails.get(i).getVegetableId());
                jsonObject.put("farm",subVegetableDetails.get(i).getFarmId());
                jsonObject.put("quantity",subVegetableDetails.get(i).getVegetableQuantity());
                jsonObject.put("is_verified",subVegetableDetails.get(i).getIsVerified());
                jsonObject.put("cycle_id",Integer.parseInt(subVegetableDetails.get(i).getSubscriptionDeliveryId()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            subVegetableJsonArray.put(jsonObject);
        }
        postSubVegRequest(subVegetableJsonArray);
        Log.e("sent data ",""+subVegetableJsonArray);

    }
    public void postCustomVegDatas() {
        JSONArray jsonArray = new JSONArray();
         customVegetableDetails = (ArrayList<Items>) database.getCustomVegetableDetailsTable(Integer.parseInt(cycleId));
        Log.e("ssss...",""+customVegetableDetails.size());
        for (int i=0; i<customVegetableDetails.size(); i++){
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("status",customVegetableDetails.get(i).getStatus());
                jsonObject.put("id",Integer.parseInt(customVegetableDetails.get(i).getCustomId()));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(jsonObject);
        }
        postCustomVegRequest(jsonArray);
        Log.e("custom data ..",""+jsonArray);

    }
    public void postComplaints() {

        JSONArray jsonArray = new JSONArray();
        complaintsDetails = (ArrayList<Items>) database.getComplaintDetailsTable(cycleId);
        for (int i=0; i<complaintsDetails.size(); i++){
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("cycle",Integer.parseInt(complaintsDetails.get(i).getSubscriptionDeliveryId()));
                jsonObject.put("customer",complaintsDetails.get(i).getCustomerId());
                jsonObject.put("complaint_reason",Integer.parseInt(complaintsDetails.get(i).getComplaintStatus()));
                jsonObject.put("complaint",complaintsDetails.get(i).getComplaintNotes());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(jsonObject);
        }
        Log.e("complaint data",jsonArray.toString());
        postComplaintRequest(jsonArray);

    }

    public void setFilter() {

        final CheckBox completedCheckBox,notCompletedCheckBox,boxNotFilledNoCheckBox;
        final Dialog dialogbox = new Dialog(MainActivity.this);
        dialogbox.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogbox.setContentView(R.layout.filter_popup);
        TextView doneBtn = (TextView) dialogbox.findViewById(R.id.textView29);
        completedCheckBox = (CheckBox) dialogbox.findViewById(R.id.checkBox4);
        notCompletedCheckBox = (CheckBox) dialogbox.findViewById(R.id.checkBox5);
        boxNotFilledNoCheckBox = (CheckBox) dialogbox.findViewById(R.id.checkBox6);

        completedCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notCompletedCheckBox.setChecked(false);
                if(completedCheckBox.isChecked())
                {
                    boxCompletedStatus = "true";
                }

            }
        });
        notCompletedCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                completedCheckBox.setChecked(false);
                if(notCompletedCheckBox.isChecked())
                {
                    boxCompletedStatus = "false";
                }

            }
        });
        boxNotFilledNoCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(boxNotFilledNoCheckBox.isChecked())
                {
                    boxFilledStatus = "false";
                }
            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogbox.dismiss();
                Log.e("boxFilledStatus........",""+boxFilledStatus);
                Log.e("boxCompletedStatus..",""+boxCompletedStatus);

                customerDetails = (ArrayList<Items>)
                        database.getCustomerDetailsTable(boxCompletedStatus,boxFilledStatus,cycleId);
                Log.e("customerDetails........",""+customerDetails.size());
                CustomerListAdapter adapter = new CustomerListAdapter(MainActivity.this,customerDetails);
                recyclerView.setAdapter(adapter);
                boxCompletedStatus = "true";boxFilledStatus = "true";


            }
        });

        dialogbox.show();

    }

    public void postSubVegRequest(JSONArray jsonArray) {

        //String postSubscrptionVegetableUrl  = url.liveRootUrl+"packed-vegetables/";
        String postSubscrptionVegetableUrl = url.rootUrl + "customer/create-packed-vegetables/";
        final ProgressDialog dialog = new ProgressDialog(MainActivity.this,R.style.AppCompatAlertDialogStyle);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Loading... ");
        dialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST,
                postSubscrptionVegetableUrl, jsonArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                        Log.e("sync response ..", response.toString());
                        dialog.dismiss();
                        postCustomVegDatas();

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
                else if(statusCode == 409){
                    Toast.makeText(getApplicationContext(),"Invalid  delivery cycle",
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
                   //b Toast.makeText(getApplicationContext(),"Syncing Failed ... Contact Sreehari..",Toast.LENGTH_SHORT).show();
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

    public void postCustomVegRequest(JSONArray jsonArray) {
       // String postCustomVegetableUrl  = url.rootUrl+"vegetable-orders/";
        String postCustomVegetableUrl  = url.rootUrl +"custom/update-custom-order/";
        //String postCustomVegetableUrl  = url.liveRootUrl+"vegetable-orders/";
        final ProgressDialog dialog = new ProgressDialog(MainActivity.this,R.style.AppCompatAlertDialogStyle);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Loading... ");
        dialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, postCustomVegetableUrl,
                jsonArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                        Log.e("custom response ..", response.toString());
                        dialog.dismiss();
                        postComplaints();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("erorrr",""+error.getMessage());

                if (error instanceof NoConnectionError) {
                    dialog.dismiss();

                    Log.e("sdgsdfg.......","zdfgdsafszadfg");
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
    public void postComplaintRequest(JSONArray jsonArray) {
        //String postComplaintUrl  = url.liveRootUrl+"complaint/";
        String postComplaintUrl  = url.rootUrl +"complaint/";
        final ProgressDialog dialog = new ProgressDialog(MainActivity.this,R.style.AppCompatAlertDialogStyle);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Loading... ");
        dialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, postComplaintUrl, jsonArray, new Response.Listener<JSONArray>() {
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
        System.exit(0);
    }

    public void getCustomerDetailsRequest(final String cycle_Id) {

        getCustomerDetailsUrl = url.rootUrl+"subscription/get-packing-details/?cycle_id="+cycle_Id;
        final ProgressDialog dialog = new ProgressDialog(MainActivity.this,R.style.AppCompatAlertDialogStyle);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Loading... ");
        dialog.show();

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,getCustomerDetailsUrl,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                Log.e(" Response ",response.toString());
                try {
                    manager.setCycleId(response.getString("cycle_id"));

                    JSONArray array = response.getJSONArray("zones");
                    database.addtoCustomerZoneDetailsTable(new Items("0","Select zone"));
                    for (int m = 0; m<array.length(); m++)
                    {
                        JSONObject object = array.getJSONObject(m);
                        database.addtoCustomerZoneDetailsTable(new Items(object.getString("zone_id")
                                ,object.getString("zone")));
                    }

                    JSONArray jsonArray = response.getJSONArray("customers");
                    for (int i=0; i<jsonArray.length() ; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        customerId = Integer.parseInt(jsonObject.getString("id"));
                        fsId = jsonObject.getString("fs_id");
                        database.addtoCustomerDetailsTable(new Items(jsonObject.getString("id"),
                                jsonObject.getString("name"), jsonObject.getString("zone"),
                                jsonObject.getString("status"),jsonObject.getString("ordered_quantity"),
                                jsonObject.getString("is_box_filled"),jsonObject.getString("complaint_count"),
                                jsonObject.getString("fs_id"), jsonObject.getString("pack_quantity"),
                                jsonObject.getString("is_priority"),jsonObject.getString("distributed_quantity"),
                                jsonObject.getString("note"), jsonObject.getString("is_preferece_given"),
                                jsonObject.getString("is_completed"), jsonObject.getString("zone_id"),cycle_Id));


                        JSONArray subVegJsonArray = jsonObject.getJSONArray("subscription_vegetables");
                        for (int j=0; j<subVegJsonArray.length() ; j++) {
                            JSONObject object = subVegJsonArray.getJSONObject(j);
                            database.addtoVegetableDetailsTable(new Items(customerId,fsId,
                                    object.getString("vegetable_id"), object.getString("farm_id"),
                                    object.getString("is_distributed"), object.getString("value"),
                                    object.getString("increment_quantity"), object.getString("vegetable_name"),
                                    object.getString("quantity"),"false",Integer.parseInt(object.getString("rank")),cycle_Id));
                        }
                        JSONArray custoJsonArray = jsonObject.getJSONArray("custom_vegetables");
                        for (int k =0 ; k<custoJsonArray.length() ; k++) {
                            JSONObject object = custoJsonArray.getJSONObject(k);
                            database.addtoCustomVegetableDetailsTable(new Items(customerId,fsId,
                                    object.getString("vegetable_id"),object.getString("vegetable_name"),
                                    object.getString("status"),object.getString("days_due"),
                                    object.getString("rate"),object.getString("quantity"),
                                    object.getString("id"),cycle_Id));
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                customerDetails = (ArrayList<Items>) database.getCustomerDetailsTable(Integer.parseInt(cycle_Id));
                CustomerListAdapter adapter = new CustomerListAdapter(getApplicationContext(),customerDetails);
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

    public void errorPopup(String message)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(getApplicationContext(),CurrentStatusActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.order) {
            Intent intent = new Intent(getApplicationContext(),VegetableOrderActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.refresh) {
            subscriptionRefresh();
        }
        else if (id == R.id.trade) {
            Intent intent = new Intent(getApplicationContext(),TradeCustomers.class);
            startActivity(intent);
        }
        else if (id == R.id.trade_refresh) {
           tradeRefresh();
        }
        else if (id == R.id.stock) {
           Intent intent = new Intent(getApplicationContext(),StockActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void subscriptionRefresh(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("This will clear all data. Are you sure?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        database.deleteCustomerZoneDetailsTable();
                        database.deleteCustomerDetailsTable();
                        database.deleteComplaintDetailsTable();
                        database.deleteCustomVegetableDetailsTable();
                        database.deleteVegetableDetailsTable();
                        database.deleteDeliveryDetailsTable();
                        database.deleteFarmDetailsTable();

                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
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
    public void tradeRefresh(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("This will clear all data. Are you sure?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        database.deleteTradeCustomerDetailsTable();
                        database.deleteTradeOrederTable();
                        Intent intent = new Intent(getApplicationContext(),TradeCustomers.class);
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
    public void getDeliveryCycleDetails(){
        String getDeliveryCycleUrl = url.getDeliveryCycleDetailsUrl;
        final ProgressDialog dialog = new ProgressDialog(MainActivity.this,R.style.
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
                cycleId = deliveryCycleDetails.get(1).getSubscriptionDeliveryId();
                spinnerText.setText(deliveryCycleDetails.get(1).getDeliveryNumber()+" ( "+
                        deliveryCycleDetails.get(1).getDeliveryDate()+"  )");
                SpinnerAdapter spinnerAdapter = new SpinnerAdapter(MainActivity.this,deliveryCycleDetails);
                spinner.setAdapter(spinnerAdapter);
                getCustomerDetailsRequest(cycleId);
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
    public void getFarmDetailsRequest() {
        database.deleteFarmDetailsTable();
        String getVegOrderUrl = url.getFarmDetrailsUrl;
        final ProgressDialog dialog = new ProgressDialog(MainActivity.this,R.style.AppCompatAlertDialogStyle);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Loading... ");
        dialog.show();

        final JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,getVegOrderUrl,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                dialog.dismiss();
                Log.e(" Response farms..",response.toString());
                try {
                    JSONArray array = response.getJSONArray("results");
                    for (int i =0; i<array.length() ; i++){
                        JSONObject object = array.getJSONObject(i);
                        database.addtoFarmDetailsTable(new Items(object.getString("id"),object.getString("name"),"false",1));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getDeliveryCycleDetails();



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

