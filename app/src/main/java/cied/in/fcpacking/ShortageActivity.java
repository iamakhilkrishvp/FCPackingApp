package cied.in.fcpacking;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
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

public class ShortageActivity extends AppCompatActivity {
    TextView summaryButton,goButton,spinnerTextview;
    RecyclerView recyclerView;
    FCDatabase database;
    Spinner cycleSpinner;
    String cycleId;
    URL url;
    double aDouble = 0;
    SpinnerAdapter spinnerAdapter;
    ArrayList<Items> vegetableOrderTableList = new ArrayList<Items>();
    ArrayList<Items> vegeOrderTableList = new ArrayList<Items>();
    ArrayList<Items> deliveryCycleDetails = new ArrayList<Items>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinnerTextview = (TextView) findViewById(R.id.textView66);
        summaryButton = (TextView) findViewById(R.id.summary);
        goButton = (TextView) findViewById(R.id.textView65);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        cycleSpinner = (Spinner) findViewById(R.id.spinner2);
        database = new FCDatabase(this);
        LinearLayoutManager layoutManagaer
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
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
        else
        {
            deliveryCycleDetails = (ArrayList<Items>) database.getDeliveryCycleTable();
            cycleId = deliveryCycleDetails.get(0).getSubscriptionDeliveryId();
            spinnerTextview.setText(deliveryCycleDetails.get(0).getDeliveryNumber()+" ( "+
                    deliveryCycleDetails.get(0).getDeliveryDate()+"  )");
            spinnerAdapter = new SpinnerAdapter(ShortageActivity.this,deliveryCycleDetails);
            cycleSpinner.setAdapter(spinnerAdapter);
            getShortageList(cycleId);
        }
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getShortageList(cycleId);

            }
        });

    }
    public void getDeliveryCycleDetails(){
        //String getDeliveryCycleUrl = url.liveRootUrl+"subscription-delivery-cycle/";
        String getDeliveryCycleUrl = url.rootUrl +"subscription-delivery-cycle/";
        final ProgressDialog dialog = new ProgressDialog(ShortageActivity.this,R.style.AppCompatAlertDialogStyle);
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
                                jsonObject.getString("delivery_no"), jsonObject.getString("delivery_date")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                deliveryCycleDetails = (ArrayList<Items>) database.getDeliveryCycleTable();
                cycleId = deliveryCycleDetails.get(0).getSubscriptionDeliveryId();
                spinnerTextview.setText(deliveryCycleDetails.get(0).getDeliveryNumber()+" ( "+
                        deliveryCycleDetails.get(0).getDeliveryDate()+"  )");
                spinnerAdapter = new SpinnerAdapter(ShortageActivity.this,deliveryCycleDetails);
                cycleSpinner.setAdapter(spinnerAdapter);
                getShortageList(cycleId);

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

    public void getShortageList(String cycleId){
        vegetableOrderTableList.clear();
        //String getDeliveryCycleUrl = url.liveRootUrl+"subscription/get-vegetable-ordering-details/?subscription_cycle_id="+cycleId;
        String getDeliveryCycleUrl = url.rootUrl +"subscription/get-vegetable-sourcing-details/?subscription_cycle_id="+cycleId;
        final ProgressDialog dialog = new ProgressDialog(ShortageActivity.this,R.style.AppCompatAlertDialogStyle);
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
                    JSONArray jsonArray = response.getJSONArray("ordered_vegetables");
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        vegetableOrderTableList.add(new Items(aDouble,jsonObject.getString("vegetable_name"),
                                jsonObject.getString("total_quantity"),jsonObject.getString("received_amount")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                CustomOrderList customOrderList = new CustomOrderList(getApplicationContext(),vegetableOrderTableList);
                recyclerView.setAdapter(customOrderList);

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
                    cycleSpinner.setVisibility(View.GONE);
                    spinnerTextview.setVisibility(View.VISIBLE);
                    spinnerTextview.setText(deliveryCycleDetails.get(position).getDeliveryNumber()+" ( "+
                            deliveryCycleDetails.get(position).getDeliveryDate()+"  )");
                    cycleId = deliveryCycleDetails.get(position).getSubscriptionDeliveryId();

                }
            });

            return view;

        }

    }
    public class CustomOrderList extends RecyclerView.Adapter<CustomOrderList.MyViewHolder> {

        private List<Items> vegetableOrder;
        Context mcontext;
        FCDatabase database;
        float orderedQty,receivedQty,shortage;
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
            holder.vegetableNameText.setText(vegetableOrder.get(position).getVegetableName());
            orderedQty = Float.parseFloat(vegetableOrder.get(position).getOrderedQuantity());
            receivedQty = Float.parseFloat(vegetableOrder.get(position).getReceivedQuantity());
            shortage = orderedQty - receivedQty;
            if(shortage == 0){
                holder.vegetableNameText.setVisibility(View.GONE);
                holder.qtyText.setVisibility(View.GONE);
            }
            holder.qtyText.setText(""+shortage);

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

}
