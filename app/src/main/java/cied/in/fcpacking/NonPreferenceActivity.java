package cied.in.fcpacking;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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

public class NonPreferenceActivity extends AppCompatActivity {

    TextView nonPrefQtyText,nonPrefCustomerCountText,goButton;
    Spinner spinner;
    RecyclerView recyclerView;
    FCDatabase database;
    ImageView backIcon;
    URL url;
    String cycleId;
    ArrayList<Items> nonPreferenceDetails = new ArrayList<Items>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_preference);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        nonPrefQtyText = (TextView) findViewById(R.id.textView75);
        nonPrefCustomerCountText = (TextView) findViewById(R.id.textView77);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        backIcon = (ImageView) toolbar.findViewById(R.id.imageView);

        database = new FCDatabase(this);
        LinearLayoutManager layoutManagaer
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManagaer);


        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    public void getNonPreferenceDetails(){
        String getNonPreferenceDetailsUrl = url.getDeliveryCycleDetailsUrl;
        final ProgressDialog dialog = new ProgressDialog(NonPreferenceActivity .this,R.style.
                AppCompatAlertDialogStyle);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Loading... ");
        dialog.show();

        final JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, getNonPreferenceDetailsUrl,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                dialog.dismiss();
                Log.e("non pref Response",response.toString());



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
