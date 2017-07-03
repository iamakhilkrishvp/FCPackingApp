package cied.in.fcpacking;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

public class AddFarmActivity extends AppCompatActivity {
    TextView doneButton;
    EditText searchtext;
    RecyclerView recyclerView;
    ArrayList<Items> farmDetails = new ArrayList<Items>();
    FCDatabase database;
    ImageView backIcon,refreshIcon;
    String getVegOrderUrl;
    URL url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_farm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        doneButton = (TextView) findViewById(R.id.textView68);
        searchtext = (EditText) findViewById(R.id.editText14);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        backIcon = (ImageView) toolbar.findViewById(R.id.imageView);
        refreshIcon = (ImageView) toolbar.findViewById(R.id.imageView3);
        database = new FCDatabase(this);
        LinearLayoutManager layoutManagaer
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManagaer);
        farmDetails = (ArrayList<Items>) database.getFarmDetailsTableDetails();
        if(farmDetails.size() == 0){
            getFarmDetailsRequest();
        }
        else {
            farmDetails = (ArrayList<Items>) database.getFarmDetailsTableDetails();
            CustomFarmListAdapter adapter = new CustomFarmListAdapter
                    (getApplicationContext(),farmDetails);
            recyclerView.setAdapter(adapter);
        }
        searchtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<Items> farmDetailsList = new ArrayList<Items>();
                farmDetailsList = (ArrayList<Items>) database.getFarmDetailsTableDetails(1,""+s);
                 CustomFarmListAdapter adapter = new CustomFarmListAdapter
                        (getApplicationContext(),farmDetailsList);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        refreshIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.deleteFarmDetailsTable();
                Intent intent = new Intent(getApplicationContext(),AddFarmActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),VegetableOrderActivity.class);
        startActivity(intent);
    }

    public void getFarmDetailsRequest() {
        String getFarmDetailsUrl = url.getFarmDetrailsUrl;
        final ProgressDialog dialog = new ProgressDialog(AddFarmActivity.this,R.style.AppCompatAlertDialogStyle);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Loading... ");
        dialog.show();

        final JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,getFarmDetailsUrl,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

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
                farmDetails = (ArrayList<Items>) database.getFarmDetailsTableDetails();
                CustomFarmListAdapter adapter = new CustomFarmListAdapter
                        (getApplicationContext(),farmDetails);
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
    public class CustomFarmListAdapter extends RecyclerView.Adapter<CustomFarmListAdapter.MyViewHolder> {

        private List<Items> farmDetails;
        Context mcontext;
        String vegId;
        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView farmName;
            CheckBox checkBox;
            RelativeLayout relativeLayout;
            public MyViewHolder(View view) {
                super(view);
                farmName = (TextView) view.findViewById(R.id.textView16);
                checkBox = (CheckBox) view.findViewById(R.id.checkBox13);
                relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
                this.setIsRecyclable(false);
            }
        }

        public CustomFarmListAdapter(Context context, List<Items> list) {
            this.farmDetails = list;
            this.mcontext = context;
        }

        @Override
        public CustomFarmListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.custom_veg_list, parent, false);



            return new CustomFarmListAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final int pos = position;
            holder.farmName.setText(farmDetails.get(pos).getFarmName());
            if(farmDetails.get(pos).getStatus().equals("true")) {
                holder.checkBox.setChecked(true);
            }
            else {
                holder.checkBox.setChecked(false);
            }
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if(holder.checkBox.isChecked()){
                        holder.checkBox.setChecked(false);
                        farmDetails.get(position).setStatus("false");
                        database.updateStatusFarmDetailsTable(new Items("false",1,1),
                                farmDetails.get(position).getFarmId());

                    }
                    else {
                        holder.checkBox.setChecked(true);
                        farmDetails.get(position).setStatus("true");
                        database.updateStatusFarmDetailsTable(new Items("true",1,1),
                                farmDetails.get(position).getFarmId());
                    }

                }
            });

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(holder.checkBox.isChecked()){

                        farmDetails.get(position).setStatus("true");
                        database.updateStatusFarmDetailsTable(new Items("true",1,1),
                                farmDetails.get(position).getFarmId());
                    }
                    else {
                        farmDetails.get(position).setStatus("false");
                        database.updateStatusFarmDetailsTable(new Items("false",1,1),
                                farmDetails.get(position).getFarmId());
                    }

                }
            });
        }
        @Override
        public int getItemCount() {
            return farmDetails.size();
        }
    }

}
