package cied.in.fcpacking;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cied.in.fcpacking.Database.FCDatabase;
import cied.in.fcpacking.ListAdapter.SelectedTradeVegetableListAdapater;
import cied.in.fcpacking.Model.Items;

public class TradeCustomerDetails extends AppCompatActivity {
    ArrayList<Items> tradeVegetableDetailsList = new ArrayList<Items>();
    TextView fsidText,customerNameText, statusText, orderedQuantityText,
            saveBtn,zoneText,customerNoteText,groupText,orderText,packStatusText;
    ImageView imageView;
    FCDatabase database ;
    RecyclerView recyclerView;
    String fsid;
    int customerId;
    ArrayList<Items> tradeOrderList = new ArrayList<Items>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_customer_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fsidText = (TextView) toolbar.findViewById(R.id.textView14);
        customerNameText = (TextView) toolbar.findViewById(R.id.textView3);
        imageView = (ImageView) findViewById(R.id.imageView2);
        orderedQuantityText = (TextView) findViewById(R.id.qtyText);
        statusText = (TextView) findViewById(R.id.textView4);
        zoneText = (TextView) findViewById(R.id.textView43);
        saveBtn = (TextView) findViewById(R.id.textView13);
        customerNoteText = (TextView) findViewById(R.id.noteText);
        orderText = (TextView) findViewById(R.id.text6);
        packStatusText = (TextView) findViewById(R.id.textView40);
        groupText = (TextView) findViewById(R.id.groupNameText);
        recyclerView = (RecyclerView) findViewById(R.id.customRecyclerView);
        LinearLayoutManager layoutManagaer
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManagaer);
        database = new FCDatabase(this);
        final Intent intent = getIntent();
        customerId = Integer.parseInt(intent.getStringExtra("customerId"));
        fsid = intent.getStringExtra("fsid");
        tradeVegetableDetailsList = (ArrayList<Items>) database.getTradeCustomerDetailsTable(customerId);

        fsidText.setText(tradeVegetableDetailsList.get(0).getFsid());
        customerNameText.setText(tradeVegetableDetailsList.get(0).getCustomerName());
        statusText.setText(tradeVegetableDetailsList.get(0).getStatus());
        orderedQuantityText.setText(tradeVegetableDetailsList.get(0).getOrderedQuantity());
        zoneText.setText(tradeVegetableDetailsList.get(0).getGroup());
        groupText.setText(tradeVegetableDetailsList.get(0).getSubGroup());
        if(tradeVegetableDetailsList.get(0).getPackingStatus().equals("true")){
            packStatusText.setText("Completed");
            packStatusText.setTextColor(Color.parseColor("#FF02B736"));
        }
        else {
            packStatusText.setText("Not Completed");
            packStatusText.setTextColor(Color.parseColor("#E22727"));
        }
        if(tradeVegetableDetailsList.get(0).getPriorityStatus().equals("true")){
            imageView.setVisibility(View.VISIBLE);
        }
        else {
            imageView.setVisibility(View.INVISIBLE);
        }
        orderText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getAllCustomegetables();
            }
        });

        tradeOrderList = (ArrayList<Items>) database.getTradeOrderDetailsTable(customerId,"DELIVERED");
        if(tradeOrderList.size() == 0){
            recyclerView.setVisibility(View.INVISIBLE);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
            SelectedTradeVegetableListAdapater adapater = new SelectedTradeVegetableListAdapater
                    (getApplicationContext(),tradeOrderList);
            recyclerView.setAdapter(adapater);
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Items> tradeOrderList = new ArrayList<Items>();
                tradeOrderList = (ArrayList<Items>) database.getTradeOrderDetailsTable(customerId,"ORDERED");
                for (int i=0; i<tradeOrderList.size(); i++){
                    database.updateTradeOrderStatus(new Items("CANCELLED"),tradeOrderList.get(i).getId());
                }
                database.updateTradeCustomerDetailsTable(new Items("true"),fsid);
                packStatusText.setText("Completed");
                Intent intent1 = new Intent(getApplicationContext(),TradeCustomers.class);
                startActivity(intent1);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(getApplicationContext(), TradeCustomers.class);
        startActivity(intent);
    }

    public void getAllCustomegetables() {
        ArrayList<Items> tradeOrderList = new ArrayList<Items>();
        final Dialog dialogbox = new Dialog(TradeCustomerDetails.this);
        dialogbox.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogbox.setContentView(R.layout.vegetable_list);
        final RecyclerView orderRecyclerView = (RecyclerView) dialogbox.findViewById(R.id.recyclerview);
        TextView doneBtn = (TextView) dialogbox.findViewById(R.id.textView30);
        EditText filterText = (EditText) dialogbox.findViewById(R.id.editText2);
        LinearLayoutManager layoutManagaer
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        orderRecyclerView.setLayoutManager(layoutManagaer);
        tradeOrderList = (ArrayList<Items>) database.getTradeOrderDetailsTable(customerId);
        CustomTradeOrder customTradeOrder = new CustomTradeOrder(getApplicationContext(),tradeOrderList);
        orderRecyclerView.setAdapter(customTradeOrder);
        filterText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ArrayList<Items> tradeOrderList = new ArrayList<Items>();
                tradeOrderList = (ArrayList<Items>) database.getTradeOrderDetailsTable(""+charSequence);
                CustomTradeOrder customTradeOrder = new CustomTradeOrder(getApplicationContext(),tradeOrderList);
                orderRecyclerView.setAdapter(customTradeOrder);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Items> tradeOrderList = new ArrayList<Items>();
                recyclerView.setVisibility(View.VISIBLE);
                tradeOrderList = (ArrayList<Items>) database.getTradeOrderDetailsTable(customerId,"DELIVERED");
                SelectedTradeVegetableListAdapater adapater = new SelectedTradeVegetableListAdapater
                        (getApplicationContext(),tradeOrderList);
                recyclerView.setAdapter(adapater);
                dialogbox.dismiss();
            }
        });

        dialogbox.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialogbox.dismiss();
                }
                return false;
            }
        });
        dialogbox.show();

    }
    public class CustomTradeOrder extends RecyclerView.Adapter<CustomTradeOrder.MyViewHolder> {

        private List<Items> vegetableDetailsList;
        Context mcontext;
        String vegId;
        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView vegNameText;
            EditText qtyText;
            CheckBox checkBox;
            RelativeLayout relativeLayout;
            public MyViewHolder(View view) {
                super(view);
                vegNameText = (TextView) view.findViewById(R.id.textView47);
                qtyText = (EditText) view.findViewById(R.id.textView46);
                checkBox = (CheckBox) view.findViewById(R.id.checkBox13);
                relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
                this.setIsRecyclable(false);
            }
        }

        public CustomTradeOrder(Context context, List<Items> list) {
            this.vegetableDetailsList = list;
            this.mcontext = context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.custom_trade_item, parent, false);



            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final int pos = position;
            holder.vegNameText.setText(vegetableDetailsList.get(pos).getVegetableName()) ;
            holder.qtyText.setText(vegetableDetailsList.get(pos).getVegetableQuantity()) ;

            if(vegetableDetailsList.get(pos).getStatus().equals("DELIVERED")) {

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
                        vegetableDetailsList.get(position).setStatus("ORDERED");
                        vegetableDetailsList.get(position).setVegetableQuantity(holder.qtyText.getText().toString());
                        database.updateTradeOrderDetailsTable(new Items("ORDERED",holder.qtyText.getText().toString()),
                                vegetableDetailsList.get(position).getId());

                    }
                    else {
                        holder.checkBox.setChecked(true);
                        vegId = vegetableDetailsList.get(position).getVegetableId();
                        vegetableDetailsList.get(position).setStatus("DELIVERED");
                        vegetableDetailsList.get(position).setVegetableQuantity(holder.qtyText.getText().toString());
                        database.updateTradeOrderDetailsTable(new Items("DELIVERED",holder.qtyText.getText().toString()),
                                vegetableDetailsList.get(position).getId());


                    }

                }
            });

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(holder.checkBox.isChecked()){

                        vegId = vegetableDetailsList.get(position).getVegetableId();
                        vegetableDetailsList.get(position).setStatus("DELIVERED");
                        vegetableDetailsList.get(position).setVegetableQuantity(holder.qtyText.getText().toString());
                        database.updateTradeOrderDetailsTable(new Items("DELIVERED",holder.qtyText.getText().toString()),
                                vegetableDetailsList.get(position).getId());
                    }
                    else {
                        vegetableDetailsList.get(position).setStatus("ORDERED");
                        vegetableDetailsList.get(position).setVegetableQuantity(holder.qtyText.getText().toString());
                        database.updateTradeOrderDetailsTable(new Items("ORDERED",holder.qtyText.getText().toString()),
                                vegetableDetailsList.get(position).getId());

                    }

                }
            });


        }

        @Override
        public int getItemCount() {
            return vegetableDetailsList.size();
        }
    }

}
