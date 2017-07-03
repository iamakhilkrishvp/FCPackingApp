package cied.in.fcpacking;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import cied.in.fcpacking.Database.FCDatabase;
import cied.in.fcpacking.ListAdapter.CustomSummaryAdapter;
import cied.in.fcpacking.Model.Items;

public class SummaryActivity extends AppCompatActivity {
    RecyclerView recyclerView ;
    ArrayList<Items> farmDetailsList = new ArrayList<Items>();
    ArrayList<Items> vegetableOrderTableList = new ArrayList<Items>();
    FCDatabase database;
    String cycleId;
    String [] farmIds;
    ImageView imageView;
    TextView shortageListBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView = (ImageView) toolbar.findViewById(R.id.imageView);
        shortageListBtn = (TextView) findViewById(R.id.textView62) ;
        shortageListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ShortageList.class);
                intent.putExtra("CYCLEID",cycleId);
                startActivity(intent);
            }
        });
        database = new FCDatabase(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManagaer
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManagaer);
        Bundle bundle = getIntent().getExtras();
        cycleId = bundle.getString("CYCLEID");
        vegetableOrderTableList = (ArrayList<Items>) database.getVegetableOrderTableDetailsForSync(cycleId);
        farmIds = new String[vegetableOrderTableList.size()];
        for (int i=0; i<vegetableOrderTableList.size(); i++){
            if(vegetableOrderTableList.get(i).getFarmId().equals("")){

            }
            else {
                farmIds[i] = vegetableOrderTableList.get(i).getFarmId();
            }

        }
        farmDetailsList = (ArrayList<Items>) database.getFarmDetailsTableDetails(farmIds);
        CustomSummaryAdapter adapter = new CustomSummaryAdapter(SummaryActivity.this,farmDetailsList,cycleId);
        recyclerView.setAdapter(adapter);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
