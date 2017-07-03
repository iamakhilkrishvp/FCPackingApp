package cied.in.fcpacking;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cied.in.fcpacking.Database.FCDatabase;
import cied.in.fcpacking.ListAdapter.SelectedCustomVegetableListAdapater;
import cied.in.fcpacking.Model.Items;

public class CustomerDetailsActivity extends AppCompatActivity {
    int subCount = 0,customCount = 0,customerId;
    String fsid,complaintStatus="NO",cycleId;
    CardView subTopCard,subscriptionCard,customTopCard,customCard;
    TextView subText,customText,fsidText,customerNameText,filledStatusText,
            statusText, boxQuantityText, orderedQuantityText, packedQuantityText,notesText,
            saveBtn,extraVegQuatityText,zoneText;
    EditText complaintNoteText;
    ImageView imageView;
    CheckBox cancelledCheckBox,quantityCheckBox,shortageCheckBox;
    ArrayList<Items> subcriptionVegetableDetailsList = new ArrayList<Items>();
    ArrayList<Items> customVegetableDetailsList = new ArrayList<Items>();
    ArrayList<Items> customerDetailsList = new ArrayList<Items>();
    ArrayList<Items> complaintDetailsList = new ArrayList<Items>();
    ArrayList<Items> deliveredCustomVegetableDetailsList = new ArrayList<Items>();
    FCDatabase database ;
    RecyclerView subRecyclerView,customRecyclerView;
    RelativeLayout relativeLayout,headerLayout;
    double distributedQuantity = 0;
    ArrayList<Items> prefYESVegetableDetailsList = new ArrayList<Items>();
    ArrayList<Items> prefMAYBEVegetableDetailsList = new ArrayList<Items>();
    float quantitySum = 0,extraVegQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        subTopCard = (CardView) findViewById(R.id.second);
        customTopCard = (CardView) findViewById(R.id.third);
        subscriptionCard = (CardView) findViewById(R.id.subscriptionCard);
        customCard = (CardView) findViewById(R.id.customCard);
        database = new FCDatabase(this);
        headerLayout = (RelativeLayout) toolbar.findViewById(R.id.headerLayout);
        subText = (TextView) findViewById(R.id.textView15);
        customText = (TextView) findViewById(R.id.text6);
        fsidText = (TextView) toolbar.findViewById(R.id.textView14);
        customerNameText = (TextView) toolbar.findViewById(R.id.textView3);
        imageView = (ImageView) findViewById(R.id.imageView2);
        boxQuantityText = (TextView) findViewById(R.id.groupNameText);
        extraVegQuatityText = (TextView) findViewById(R.id.textView39);
        packedQuantityText = (TextView) findViewById(R.id.textView7);
        orderedQuantityText = (TextView) findViewById(R.id.textView6);
        filledStatusText = (TextView) findViewById(R.id.textView11);
        zoneText = (TextView) findViewById(R.id.zoneText);

        saveBtn = (TextView) findViewById(R.id.textView13);
        notesText = (TextView) findViewById(R.id.noteText);
        complaintNoteText = (EditText) findViewById(R.id.complaintNoteText);
        relativeLayout = (RelativeLayout) findViewById(R.id.topCard);


        cancelledCheckBox = (CheckBox) findViewById(R.id.checkBox);
        quantityCheckBox = (CheckBox) findViewById(R.id.checkBox3);
        shortageCheckBox = (CheckBox) findViewById(R.id.checkBox2);

        subRecyclerView = (RecyclerView) findViewById(R.id.subcriptionRecyclerView);
        customRecyclerView = (RecyclerView) findViewById(R.id.customRecyclerView);
        LinearLayoutManager layoutManagaer
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManagaer1
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        subRecyclerView.setLayoutManager(layoutManagaer);
        customRecyclerView.setLayoutManager(layoutManagaer1);
        final Intent intent = getIntent();
        fsid = intent.getStringExtra("fsid");
        cycleId = intent.getStringExtra("CYCLE_ID");
        Log.e("ccccccccccc",cycleId);
        customerDetailsList = (ArrayList<Items>) database.getCustomerDetailsTable(fsid,cycleId);
        fsidText.setText(fsid);
        customerId = customerDetailsList.get(0).getCustomerId();
        customerNameText.setText(customerDetailsList.get(0).getCustomerName());

        boxQuantityText.setText("Box Quantity : "+customerDetailsList.get(0).getPackQuantity());
        packedQuantityText.setText("Packed Quantity : "+
                customerDetailsList.get(0).getDistributedQuantity());
        orderedQuantityText.setText("Ordered Quantity : "+customerDetailsList.get(0).getOrderedQuantity());
        zoneText.setText("Zone : "+customerDetailsList.get(0).getCustomerZone());
        if(customerDetailsList.get(0).getPriorityStatus().equals("true") ||
                customerDetailsList.get(0).getStatus().equals("SAMPLE")){
            imageView.setVisibility(View.VISIBLE);
        }
        else {
            imageView.setVisibility(View.GONE);
        }

        if(customerDetailsList.get(0).getFilledStatus().equals("true")){
            filledStatusText.setText("BOX IS FILLED...");
        }
        else {
            filledStatusText.setText("BOX IS NOT FILLED !!! ");
            filledStatusText.setTextColor(Color.parseColor("#E22727"));
        }
        if(customerDetailsList.get(0).getPreferanceStatus().equals("true")){

           headerLayout.setBackgroundColor(Color.parseColor("#7ccaa9"));
            toolbar.setBackgroundColor(Color.parseColor("#7ccaa9"));
        }
        else {
            headerLayout.setBackgroundColor(Color.parseColor("#f5a64a"));
            toolbar.setBackgroundColor(Color.parseColor("#f5a64a"));
        }
        complaintDetailsList = (ArrayList<Items>) database.getComplaintDetailsTable(fsid,cycleId);
        if(complaintDetailsList.size() !=0) {
            if (complaintDetailsList.get(0).getComplaintStatus().equals("3")) {
                cancelledCheckBox.setChecked(true);
            } else if (complaintDetailsList.get(0).getComplaintStatus().equals("4")) {

                shortageCheckBox.setChecked(false);
            } else if (complaintDetailsList.get(0).getComplaintStatus().equals("5")) {

                quantityCheckBox.setChecked(false);
            }
            else {
                cancelledCheckBox.setChecked(false);
                shortageCheckBox.setChecked(false);
                quantityCheckBox.setChecked(false);
            }
        }

        notesText.setText(customerDetailsList.get(0).getCustomerNote());
        customVegetableDetailsList = (ArrayList<Items>) database.getCustomVegetableDetailsTable(1,fsid,cycleId);
        if(customVegetableDetailsList.size() != 0) {
            customTopCard.setVisibility(View.VISIBLE);
            customCard.setVisibility(View.VISIBLE);
            customText.setBackgroundColor(Color.parseColor("#7ccaa9"));
            customText.setTextColor(Color.parseColor("#ffffff"));
        }
        else {
            customTopCard.setVisibility(View.GONE);
            customCard.setVisibility(View.GONE);
        }

        ArrayList<Items> selectedVegetableDetailsList = new ArrayList<Items>();
        selectedVegetableDetailsList = (ArrayList<Items>) database.
                getVegetableDetailsTable(fsid,"true",cycleId);

        for (int i=0; i<selectedVegetableDetailsList.size(); i++){

            distributedQuantity = distributedQuantity+Float.parseFloat(selectedVegetableDetailsList.get(i).getVegetableQuantity());

        }
        packedQuantityText.setText("Packed Quantity : "+distributedQuantity);
        extraVegQuantity = Float.parseFloat(String.valueOf(distributedQuantity))-Float.parseFloat(customerDetailsList.get(0).getPackQuantity());
        extraVegQuatityText.setText(" Extra Veg. :"+extraVegQuantity);

        SelectedSubcriptionVegitableListAdapter adapter1 = new SelectedSubcriptionVegitableListAdapter
                (getApplicationContext(),selectedVegetableDetailsList);
        subRecyclerView.setAdapter(adapter1);
        deliveredCustomVegetableDetailsList = (ArrayList<Items>) database.
                getCustomVegetableDetailsTable(fsid,"DELIVERED",cycleId);

        SelectedCustomVegetableListAdapater adapter = new SelectedCustomVegetableListAdapater
                (getApplicationContext(),deliveredCustomVegetableDetailsList);
        customRecyclerView.setAdapter(adapter);

        subText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                prefYESVegetableDetailsList.clear();
                prefMAYBEVegetableDetailsList.clear();
                subcriptionVegetableDetailsList.clear();
                getAllSubscriptionVegetables();

            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                database.updateNotesComplaintDetailsTable(new Items(1,complaintNoteText.getText().toString()),fsid,cycleId);
                database.updateCustomerDetailsTable(new Items("true"),fsid);
                customVegetableDetailsList = (ArrayList<Items>) database.
                        getCustomVegetableDetailsTable(fsid,Integer.parseInt(cycleId));

                for (int i=0 ; i<customVegetableDetailsList.size(); i++){

                    database.updateStatusToCustomVegetableDetailsTable(new Items(1,2,"CANCELLED"),
                            customVegetableDetailsList.get(i).getId(),cycleId);
                }
                ArrayList<Items> selectedVegetableDetailsList = new ArrayList<Items>();
                selectedVegetableDetailsList = (ArrayList<Items>) database.
                        getVegetableDetailsTable(fsid,"true",cycleId);
                for (int i=0; i<selectedVegetableDetailsList.size(); i++){
                    database.updateIsverifiedStatusVegetableDetailsTable
                            (new Items("true",1,1),selectedVegetableDetailsList.get(i).getVegetableId(),fsid,cycleId);
                }
                Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent1);

            }
        });
        customText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                customVegetableDetailsList = (ArrayList<Items>) database.getCustomVegetableDetailsTable(1,fsid,cycleId);
                if(customVegetableDetailsList.size() == 0){

                    errorPopup();
                }
                else {

                    getAllCustomegetables();
                }

            }
        });
        cancelledCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shortageCheckBox.setChecked(false);
                quantityCheckBox.setChecked(false);
                if(cancelledCheckBox.isChecked()) {
                    complaintStatus = "3";
                    complaintDetailsList = (ArrayList<Items>) database.getComplaintDetailsTable(fsid,cycleId);
                    if(complaintDetailsList.size() == 0){

                        database.addtoComplaintDetailsTable(new Items(customerId,fsid,"3",
                                complaintNoteText.getText().toString(),cycleId));
                    }else {
                        database.updateComplaintComplaintDetailsTable(new Items("3",1),fsid,cycleId);
                    }

                }
                else {
                    database.updateComplaintComplaintDetailsTable(new Items("NO",1),fsid,cycleId);
                    complaintStatus = "NO";

                }

            }
        });

        shortageCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelledCheckBox.setChecked(false);
                quantityCheckBox.setChecked(false);
                if(shortageCheckBox.isChecked()) {
                    complaintStatus = "4";
                    complaintDetailsList = (ArrayList<Items>) database.getComplaintDetailsTable(fsid,cycleId);
                    if(complaintDetailsList.size() == 0){

                        database.addtoComplaintDetailsTable(new Items(customerId,fsid,"4",
                                complaintNoteText.getText().toString(),cycleId));
                    }else {
                        database.updateComplaintComplaintDetailsTable(new Items("4",1),fsid,cycleId);
                    }
                }
                else {
                    database.updateComplaintComplaintDetailsTable(new Items("NO",1),fsid,cycleId);
                    complaintStatus = "NO";
                }

            }
        });

        quantityCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelledCheckBox.setChecked(false);
                shortageCheckBox.setChecked(false);
                if(quantityCheckBox.isChecked()) {
                    complaintStatus = "5";
                    complaintDetailsList = (ArrayList<Items>) database.getComplaintDetailsTable(fsid,cycleId);
                    if(complaintDetailsList.size() == 0){

                        database.addtoComplaintDetailsTable(new Items(customerId,fsid,"5",
                                complaintNoteText.getText().toString(),cycleId));
                    }else {
                        database.updateComplaintComplaintDetailsTable(new Items("5",1),fsid,cycleId);
                    }

                }
                else {
                    database.updateComplaintComplaintDetailsTable(new Items("NO",1),fsid,cycleId);
                    complaintStatus = "NO";
                }

            }
        });

    }
    @Override
    public void onBackPressed() {

        super.onBackPressed();
        finish();
        /*Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);*/
    }



    public void getAllSubscriptionVegetables() {

        Log.e("cycl",cycleId);
        final Dialog dialogbox = new Dialog(CustomerDetailsActivity.this);
        dialogbox.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogbox.setContentView(R.layout.vegetable_list);
        dialogbox.setCancelable(false);

        final RecyclerView recyclerView = (RecyclerView) dialogbox.findViewById(R.id.recyclerview);
        TextView doneBtn = (TextView) dialogbox.findViewById(R.id.textView30);
        EditText filterText = (EditText) dialogbox.findViewById(R.id.editText2);
        LinearLayoutManager layoutManagaer
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManagaer);
        prefYESVegetableDetailsList = (ArrayList<Items>) database.getVegetableDetailsTableList(fsid,"YES",cycleId);
        prefMAYBEVegetableDetailsList = (ArrayList<Items>) database.getVegetableDetailsTableList(fsid,"MAYBE",cycleId);
        subcriptionVegetableDetailsList.addAll(prefYESVegetableDetailsList);
        subcriptionVegetableDetailsList.addAll(prefMAYBEVegetableDetailsList);
        final CustomSubscriptionOrder adapter = new CustomSubscriptionOrder
                (getApplicationContext(),subcriptionVegetableDetailsList);
        recyclerView.setAdapter(adapter);

        filterText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                subcriptionVegetableDetailsList = (ArrayList<Items>) database.
                        getVegetableDetailsTableForFilter(fsid,""+charSequence,cycleId);
                final CustomSubscriptionOrder adapter = new CustomSubscriptionOrder
                        (getApplicationContext(),subcriptionVegetableDetailsList);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantitySum = 0;
                ArrayList<Items> selectedVegetableDetailsList = new ArrayList<Items>();
                selectedVegetableDetailsList = (ArrayList<Items>) database.
                        getVegetableDetailsTable(fsid,"true",cycleId);
                Log.e("size.....",""+selectedVegetableDetailsList.size());
                if(selectedVegetableDetailsList.size() == 0){
                    packedQuantityText.setText("Packed Quantity : 0");
                    filledStatusText.setText("BOX IS NOT FILLED...");
                    filledStatusText.setTextColor(Color.parseColor("#E22727"));
                    database.updateFilledStatusToCustomerDetailsTable(new Items(1,"false"),fsid);
                }
                else{

                }
                SelectedSubcriptionVegitableListAdapter adapter1 = new SelectedSubcriptionVegitableListAdapter
                        (getApplicationContext(),selectedVegetableDetailsList);
                for (int i=0; i< selectedVegetableDetailsList.size(); i++){

                    quantitySum = quantitySum+ Float.parseFloat(selectedVegetableDetailsList.get(i).getVegetableQuantity());
                    extraVegQuantity = quantitySum-Float.parseFloat(customerDetailsList.get(0).getPackQuantity());
                    packedQuantityText.setText("Packed Quantity : "+quantitySum);
//                    orderedQuantityText.setText("Ordered Quantity : "+quantitySum);
                    extraVegQuatityText.setText("Extra Veg. :"+extraVegQuantity);
                    database.updateDistQuantityToCustomerDetailsTable(new Items(quantitySum),fsid);

                    if( quantitySum >= Float.parseFloat(customerDetailsList.get(0).getPackQuantity())){
                        filledStatusText.setText("BOX IS FILLED...");
                        filledStatusText.setTextColor(Color.parseColor("#000000"));
                        database.updateFilledStatusToCustomerDetailsTable(new Items(1,"true"),fsid);
                    }
                    else {
                        filledStatusText.setText("BOX IS NOT FILLED...");
                        filledStatusText.setTextColor(Color.parseColor("#E22727"));
                        database.updateFilledStatusToCustomerDetailsTable(new Items(1,"false"),fsid);
                    }
                }
                subRecyclerView.setAdapter(adapter1);
               // adapter1.setDistributedTextview();
                adapter1.notifyDataSetChanged();
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
    public void getAllCustomegetables() {

        final Dialog dialogbox = new Dialog(CustomerDetailsActivity.this);
        dialogbox.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogbox.setContentView(R.layout.vegetable_list);
        final RecyclerView recyclerView = (RecyclerView) dialogbox.findViewById(R.id.recyclerview);
        TextView doneBtn = (TextView) dialogbox.findViewById(R.id.textView30);
        LinearLayoutManager layoutManagaer
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManagaer);
        final AllCustomVegetableListAdapter adapter = new AllCustomVegetableListAdapter
                (getApplicationContext(),customVegetableDetailsList);
        recyclerView.setAdapter(adapter);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<Items> selectedVegetableDetailsList = new ArrayList<Items>();
                selectedVegetableDetailsList = (ArrayList<Items>) database.
                        getCustomVegetableDetailsTable(fsid,"DELIVERED",cycleId);
                Log.e("size..",""+selectedVegetableDetailsList.size());
                SelectedCustomVegetableListAdapater adapter1 = new SelectedCustomVegetableListAdapater
                        (getApplicationContext(),selectedVegetableDetailsList);
                customRecyclerView.setAdapter(adapter1);
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

    public class AllSubcriptionVegetableListAdapter extends RecyclerView.Adapter<AllSubcriptionVegetableListAdapter.MyViewHolder> {

        private List<Items> vegetableDetailsList;
        Context mcontext;
        String userType;
        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView vegNameText;
            CheckBox checkBox;
            RelativeLayout relativeLayout;
            public MyViewHolder(View view) {
                super(view);
                vegNameText = (TextView) view.findViewById(R.id.textView16);
                checkBox = (CheckBox) view.findViewById(R.id.checkBox13);
                relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);

            }
        }

        public AllSubcriptionVegetableListAdapter(Context context, List<Items> list) {
            this.vegetableDetailsList = list;
            this.mcontext = context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.custom_veg_list, parent, false);



            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            holder.setIsRecyclable(false);
            holder.vegNameText.setText(vegetableDetailsList.get(position).getVegetableName()+
                    " ( "+vegetableDetailsList.get(position).getVegetableQuantity()+" )");
            if(vegetableDetailsList.get(position).getPreferanceValue().equals("YES")){
                holder.vegNameText.setTextColor(Color.parseColor("#015877"));
            }
            else if(vegetableDetailsList.get(position).getPreferanceValue().equals("MAYBE")){
                holder.vegNameText.setTextColor(Color.parseColor("#ff1a1a"));
            }
            else {
                holder.vegNameText.setTextColor(Color.parseColor("#40000000"));
            }

            if(vegetableDetailsList.get(position).getDistributedStatus().equals("true")) {
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
                        vegetableDetailsList.get(position).setDistributedStatus("false");
                        database.updateDistributedStatusVegetableDetailsTable(new Items("false"),
                                vegetableDetailsList.get(position).getVegetableId(),fsid,cycleId);
                    }
                    else {
                        holder.checkBox.setChecked(true);
                        vegetableDetailsList.get(position).setDistributedStatus("true");
                        database.updateDistributedStatusVegetableDetailsTable(new Items("true"),
                                vegetableDetailsList.get(position).getVegetableId(),fsid,cycleId);
                    }
                }
            });

            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    vegetableDetailsList.get(position).setDistributedStatus(""+b);
                    database.updateDistributedStatusVegetableDetailsTable(new Items(""+b),
                            vegetableDetailsList.get(position).getVegetableId(),fsid,cycleId);
                }
            });

        }

        @Override
        public int getItemCount() {
            return vegetableDetailsList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }
    }

    public class AllCustomVegetableListAdapter extends RecyclerView.Adapter<AllCustomVegetableListAdapter.MyViewHolder> {

        private List<Items> vegetableDetailsList;
        Context mcontext;
        String vegId;
        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView vegNameText;
            CheckBox checkBox;
            RelativeLayout relativeLayout;
            public MyViewHolder(View view) {
                super(view);
                vegNameText = (TextView) view.findViewById(R.id.textView16);
                checkBox = (CheckBox) view.findViewById(R.id.checkBox13);
                relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
                this.setIsRecyclable(false);
            }
        }

        public AllCustomVegetableListAdapter(Context context, List<Items> list) {
            this.vegetableDetailsList = list;
            this.mcontext = context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.custom_veg_list, parent, false);



            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final int pos = position;
            holder.vegNameText.setText(vegetableDetailsList.get(pos).getVegetableName()+
            "( "+vegetableDetailsList.get(pos).getVegetableQuantity()+" )"+" Due - "+
                    vegetableDetailsList.get(pos).getNumberOfDays()+" Days.");

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
                        database.updateStatusToCustomVegetableDetailsTable(new Items("ORDERED"),
                                vegetableDetailsList.get(position).getId(),cycleId);

                    }
                    else {
                        holder.checkBox.setChecked(true);
                        vegId = vegetableDetailsList.get(position).getVegetableId();
                        vegetableDetailsList.get(position).setStatus("DELIVERED");
                        database.updateStatusToCustomVegetableDetailsTable(new Items("DELIVERED"),
                                vegetableDetailsList.get(position).getId(),cycleId);


                    }

                }
            });

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(holder.checkBox.isChecked()){

                        vegId = vegetableDetailsList.get(position).getVegetableId();
                        vegetableDetailsList.get(position).setStatus("DELIVERED");
                        database.updateStatusToCustomVegetableDetailsTable(new Items("DELIVERED"),
                                vegetableDetailsList.get(position).getId(),cycleId);
                    }
                    else {
                        vegetableDetailsList.get(position).setStatus("ORDERED");
                        database.updateStatusToCustomVegetableDetailsTable(new Items("ORDERED"),
                                vegetableDetailsList.get(position).getId(),cycleId);

                    }

                }
            });


        }

        @Override
        public int getItemCount() {
            return vegetableDetailsList.size();
        }
    }
    public void errorPopup()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(CustomerDetailsActivity.this);
        alert.setMessage("No Custom Orders!!!!..");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,
                                int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    public class SelectedSubcriptionVegitableListAdapter extends RecyclerView.Adapter<SelectedSubcriptionVegitableListAdapter.MyViewHolder> {

        private List<Items> vegetableDetailsList;
        Context mcontext;
        FCDatabase database;
        private Activity activity;
        TextView distributedQuantityText;
        double amount;
        float baseQuantity,incrementQuantity,totalAmount = 0;

        public void setDistributedTextview(TextView distQuantityText) {
            this.distributedQuantityText = distQuantityText;

        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView vegNameText,incrementButton,decrementButton,amountText;

            public MyViewHolder(View view) {
                super(view);
                vegNameText = (TextView) view.findViewById(R.id.textView17);
                incrementButton  = (TextView) view.findViewById(R.id.textView18);
                decrementButton  = (TextView) view.findViewById(R.id.textView20);
                amountText  = (TextView) view.findViewById(R.id.textView19);
                database = new FCDatabase(mcontext);

            }
        }

        public SelectedSubcriptionVegitableListAdapter(Context context, List<Items> list) {

            this.vegetableDetailsList = list;
            this.mcontext = context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.selected_veg_list, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {


            holder.vegNameText.setText(vegetableDetailsList.get(position).getVegetableName());
            holder.amountText.setText(vegetableDetailsList.get(position).getVegetableQuantity()+" Kg ");
          /*  holder.incrementButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArrayList<Items> subVegList = new ArrayList<Items>();
                    subVegList = (ArrayList<Items>) database.getVegetableDetailsTable(fsid,"true");
                    distributedQuantity = 0;
                    baseQuantity = Float.parseFloat(subVegList.get(position).
                            getVegetableQuantity());
                    incrementQuantity = Float.parseFloat(subVegList.get(position).
                            getIncrementQuantity());
                    totalAmount = baseQuantity + incrementQuantity;
                    database.updateQuantityToVegetableDetailsTable(new Items(""+totalAmount,1),
                            vegetableDetailsList.get(position).getVegetableId(),
                            vegetableDetailsList.get(position).getFsid());
                    holder.amountText.setText(""+totalAmount);
                    baseQuantity = totalAmount;

                    ArrayList<Items> selectedVegetableDetailsList = new ArrayList<Items>();
                    selectedVegetableDetailsList = (ArrayList<Items>) database.
                            getVegetableDetailsTable(fsid,"true");

                    for (int i=0; i<selectedVegetableDetailsList.size(); i++){

                        distributedQuantity = distributedQuantity+Double.parseDouble
                                (selectedVegetableDetailsList.get(i).getVegetableQuantity());
                    }
                    packedQuantityText.setText("Packed Quantity : "+distributedQuantity);
                    database.updateDistQuantityToCustomerDetailsTable(new Items(distributedQuantity)
                            ,vegetableDetailsList.get(position).getFsid());
                    extraVegQuantity = (float) (distributedQuantity-Float.parseFloat(customerDetailsList.get(0).getPackQuantity()));
                    extraVegQuatityText.setText(" Extra Veg. :"+extraVegQuantity);
                    if(Float.parseFloat(customerDetailsList.get(0).getPackQuantity()) <= distributedQuantity){
                        database.updateFilledStatusToCustomerDetailsTable(new Items(1,"true"),fsid);
                        filledStatusText.setText("BOX IS FILLED...");
                        filledStatusText.setTextColor(Color.parseColor("#000000"));
                    }
                    else{
                        database.updateFilledStatusToCustomerDetailsTable(new Items(1,"false"),fsid);
                        filledStatusText.setText("BOX IS NOT FILLED...");
                        filledStatusText.setTextColor(Color.parseColor("#E22727"));
                    }

                }
            });

            holder.decrementButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if(totalAmount == 0){

                    }
                    else{
                        ArrayList<Items> subVegList = new ArrayList<Items>();
                        subVegList = (ArrayList<Items>) database.getVegetableDetailsTable(fsid,"true");
                        distributedQuantity = 0;
                        baseQuantity = Float.parseFloat(subVegList.get(position).
                                getVegetableQuantity());
                        incrementQuantity = Float.parseFloat(subVegList.get(position).
                                getIncrementQuantity());
                        totalAmount = baseQuantity -  incrementQuantity;
                        holder.amountText.setText(totalAmount+" Kg ");
                        database.updateQuantityToVegetableDetailsTable(new Items(""+totalAmount,1),
                                vegetableDetailsList.get(position).getVegetableId(),
                                vegetableDetailsList.get(position).getFsid());
                        baseQuantity = totalAmount;
                        ArrayList<Items> selectedVegetableDetailsList = new ArrayList<Items>();
                        selectedVegetableDetailsList = (ArrayList<Items>) database.
                                getVegetableDetailsTable(fsid,"true");

                        for (int i=0; i<selectedVegetableDetailsList.size(); i++){

                            distributedQuantity = distributedQuantity+Float.parseFloat
                                    (selectedVegetableDetailsList.get(i).getVegetableQuantity());
                        }
                        packedQuantityText.setText("Packed Quantity : "+distributedQuantity);
                        database.updateDistQuantityToCustomerDetailsTable(new Items(distributedQuantity)
                                ,vegetableDetailsList.get(position).getFsid());
                        extraVegQuantity = (float) (distributedQuantity-Float.parseFloat(customerDetailsList.get(0).getPackQuantity()));
                        extraVegQuatityText.setText(" Extra Veg. :"+extraVegQuantity);

                        if(Float.parseFloat(customerDetailsList.get(0).getPackQuantity()) <= distributedQuantity){
                            database.updateFilledStatusToCustomerDetailsTable(new Items(1,"true"),fsid);
                            filledStatusText.setText("BOX IS FILLED...");
                            filledStatusText.setTextColor(Color.parseColor("#000000"));
                        }
                        else{
                            database.updateFilledStatusToCustomerDetailsTable(new Items(1,"false"),fsid);
                            filledStatusText.setText("BOX IS NOT FILLED...");
                            filledStatusText.setTextColor(Color.parseColor("#E22727"));
                        }
                    }

                }
            });*/

        }

        @Override
        public int getItemCount() {
            return vegetableDetailsList.size();
        }
    }
    public class CustomSubscriptionOrder extends RecyclerView.Adapter<CustomSubscriptionOrder.MyViewHolder> {

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

        public CustomSubscriptionOrder(Context context, List<Items> list) {
            this.vegetableDetailsList = list;
            this.mcontext = context;
        }

        @Override
        public CustomSubscriptionOrder.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.custom_trade_item, parent, false);



            return new CustomSubscriptionOrder.MyViewHolder(itemView);
        }

        public void onBindViewHolder(final CustomSubscriptionOrder.MyViewHolder holder, final int position) {
            final int pos = position;
            holder.setIsRecyclable(false);
            holder.qtyText.setText(vegetableDetailsList.get(position).getVegetableQuantity());
            holder.vegNameText.setText(vegetableDetailsList.get(position).getVegetableName());
            if(vegetableDetailsList.get(position).getPreferanceValue().equals("YES")){
                holder.vegNameText.setTextColor(Color.parseColor("#015877"));
            }
            else if(vegetableDetailsList.get(position).getPreferanceValue().equals("MAYBE")){
                holder.vegNameText.setTextColor(Color.parseColor("#ff1a1a"));
            }
            else {
                holder.vegNameText.setTextColor(Color.parseColor("#40000000"));
            }

            if(vegetableDetailsList.get(position).getDistributedStatus().equals("true")) {
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
                        vegetableDetailsList.get(position).setVegetableQuantity(holder.qtyText.getText().toString());
                        vegetableDetailsList.get(position).setDistributedStatus("false");
                        database.updateDistributedStatusVegetableDetailsTable(new Items(12,"false",holder.qtyText.getText().toString()),
                                vegetableDetailsList.get(position).getVegetableId(),fsid,cycleId);
                    }
                    else {
                        holder.checkBox.setChecked(true);
                        vegId = vegetableDetailsList.get(position).getVegetableId();
                        vegetableDetailsList.get(position).setVegetableQuantity(holder.qtyText.getText().toString());
                        vegetableDetailsList.get(position).setDistributedStatus("true");
                        database.updateDistributedStatusVegetableDetailsTable(new Items(12,"true",holder.qtyText.getText().toString()),
                                vegetableDetailsList.get(position).getVegetableId(),fsid,cycleId);


                    }

                }
            });

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(holder.checkBox.isChecked()){

                        vegId = vegetableDetailsList.get(position).getVegetableId();
                        vegetableDetailsList.get(position).setVegetableQuantity(holder.qtyText.getText().toString());
                        vegetableDetailsList.get(position).setDistributedStatus("true");
                        database.updateDistributedStatusVegetableDetailsTable(new Items(12,"true",holder.qtyText.getText().toString()),
                                vegetableDetailsList.get(position).getVegetableId(),fsid,cycleId);

                    }
                    else {
                        vegetableDetailsList.get(position).setVegetableQuantity(holder.qtyText.getText().toString());
                        vegetableDetailsList.get(position).setDistributedStatus("false");
                        database.updateDistributedStatusVegetableDetailsTable(new Items(12,"false",holder.qtyText.getText().toString()),
                                vegetableDetailsList.get(position).getVegetableId(),fsid,cycleId);

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
