package cied.in.fcpacking;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import cied.in.fcpacking.Database.FCDatabase;
import cied.in.fcpacking.ListAdapter.ShortageListAdapter;
import cied.in.fcpacking.Model.Items;

public class ShortageList extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Items> vegetableOrderTableList = new ArrayList<Items>();
    FCDatabase database;
    String cycleId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortage_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManagaer
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManagaer);
        database = new FCDatabase(this);
        Bundle bundle = getIntent().getExtras();
        cycleId = bundle.getString("CYCLEID");
        vegetableOrderTableList = (ArrayList<Items>) database.getVegetableOrderTableDetailsForSync(cycleId);
        ShortageListAdapter listAdapter = new ShortageListAdapter(getApplicationContext(),vegetableOrderTableList,Integer.parseInt(cycleId));
        recyclerView.setAdapter(listAdapter);

    }

}
