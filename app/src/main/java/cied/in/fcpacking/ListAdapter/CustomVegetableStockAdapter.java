package cied.in.fcpacking.ListAdapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import cied.in.fcpacking.Database.FCDatabase;
import cied.in.fcpacking.Model.Items;
import cied.in.fcpacking.R;

/**
 * Created by cied on 17/3/17.
 */
public class CustomVegetableStockAdapter extends RecyclerView.Adapter<CustomVegetableStockAdapter.MyViewHolder> {

    private List<Items> stockVegetableList;
    Context mcontext;
    String subscriptionVegQuantity= "0.0",excessVegQuantity,wastageVegQuantity,vegetableQuality
            ,tradeQuantity,receivedQuantity,vegetableId,vegRate,nonPreferanceStatus = "false";
    FCDatabase database;
    float vegetableRating=0;
    int count = 1;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView vegetableNameText,saveButton,farmNameText,orderedQtyText,textView,
                    nonPrefQtyText,prefQtyText,tradeQtyText, customQtyText;
        EditText receivedQtyText,excessQtyText,wastageQtyText,vegRateText;
        RatingBar ratingBar;
        SwitchCompat toggleButton;
        public MyViewHolder(View view) {
            super(view);

            toggleButton = (SwitchCompat) view.findViewById(R.id.toggleButton);
            vegetableNameText = (TextView) view.findViewById(R.id.vegNameText);
            customQtyText = (TextView) view.findViewById(R.id.textView106);
            textView = (TextView) view.findViewById(R.id.textView64);
            nonPrefQtyText = (TextView) view.findViewById(R.id.textView86);
            prefQtyText = (TextView) view.findViewById(R.id.textView88);
            tradeQtyText = (TextView) view.findViewById(R.id.textView91);
            vegRateText = (EditText) view.findViewById(R.id.rateText);
            saveButton = (TextView) view.findViewById(R.id.textView31);
            farmNameText = (TextView) view.findViewById(R.id.farmNameText);
            orderedQtyText = (TextView) view.findViewById(R.id.orderQtyText);
            receivedQtyText = (EditText) view.findViewById(R.id.editText10);
            excessQtyText = (EditText) view.findViewById(R.id.editText15);
            wastageQtyText = (EditText) view.findViewById(R.id.editText3);
            ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);



        }
    }

    public CustomVegetableStockAdapter(Context context,List<Items> list) {
        this.stockVegetableList = list;
        this.mcontext = context;
        database = new FCDatabase(mcontext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_vegetable_stock, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        count = 0;
        vegetableId = stockVegetableList.get(position).getVegetableId();
        holder.vegetableNameText.setText(stockVegetableList.get(position).getVegetableName());
        holder.vegRateText.setText(stockVegetableList.get(position).getVegRate());
        holder.farmNameText.setText(stockVegetableList.get(position).getFarmName());
        holder.customQtyText.setText(stockVegetableList.get(position).getCustomQuantity());
        holder.orderedQtyText.setText("Ordered Qty : "+stockVegetableList.get(position).getOrderedQuantity());
        holder.ratingBar.setRating(Float.parseFloat(stockVegetableList.get(position).getVegetableQuality()));
        holder.excessQtyText.setText(stockVegetableList.get(position).getExcessQuantity());
        holder.wastageQtyText.setText(stockVegetableList.get(position).getWastageQuantity());
        holder.receivedQtyText.setText(stockVegetableList.get(position).getReceivedQuantity());
        holder.nonPrefQtyText.setText(stockVegetableList.get(position).getNonPreferanceQuantity());
        holder.prefQtyText.setText(stockVegetableList.get(position).getPreferanceQuantity());
        holder.tradeQtyText.setText(stockVegetableList.get(position).getTradeQuantity());

        if(stockVegetableList.get(position).getNonPreferanceStatus().equals("true")){
            holder.toggleButton.setChecked(true);
        }
        else {
            holder.toggleButton.setChecked(false);
        }

        if(Double.parseDouble(stockVegetableList.get(position).getReceivedQuantity())
                < Double.parseDouble(stockVegetableList.get(position).getOrderedQuantity())){
            holder.vegetableNameText.setTextColor(Color.parseColor("#E22727"));
        }
        else {
            holder.vegetableNameText.setTextColor(Color.parseColor("#000000"));
        }

        holder.receivedQtyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.receivedQtyText.setCursorVisible(true);
                holder.excessQtyText.setCursorVisible(true);
                holder.wastageQtyText.setCursorVisible(true);
                holder.vegRateText.setCursorVisible(true);
            }
        });
        holder.excessQtyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.receivedQtyText.setCursorVisible(true);
                holder.excessQtyText.setCursorVisible(true);
                holder.wastageQtyText.setCursorVisible(true);
                holder.vegRateText.setCursorVisible(true);
            }
        });
        holder.excessQtyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.receivedQtyText.setCursorVisible(true);
                holder.excessQtyText.setCursorVisible(true);
                holder.wastageQtyText.setCursorVisible(true);
                holder.vegRateText.setCursorVisible(true);
            }
        });
        holder.wastageQtyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.receivedQtyText.setCursorVisible(true);
                holder.excessQtyText.setCursorVisible(true);
                holder.wastageQtyText.setCursorVisible(true);
                holder.vegRateText.setCursorVisible(true);
            }
        });
        holder.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count == 1){
                    holder.saveButton.setText("SAVE");
                    holder.saveButton.setBackgroundColor(Color.parseColor("#015877"));
                }
                else {
                    holder.saveButton.setText("EDIT");
                    holder.saveButton.setBackgroundColor(Color.parseColor("#7ccaa9"));
                }
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)mcontext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(holder.excessQtyText.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(holder.wastageQtyText.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(holder.receivedQtyText.getWindowToken(), 0);
                }
                    vegetableId = stockVegetableList.get(position).getVegetableId();
                    excessVegQuantity = holder.excessQtyText.getText().toString();
                    wastageVegQuantity = holder.wastageQtyText.getText().toString();
                    receivedQuantity = holder.receivedQtyText.getText().toString();
                    vegetableQuality = String.valueOf(holder.ratingBar.getRating());
                    vegRate  =holder.vegRateText.getText().toString();
                    tradeQuantity = stockVegetableList.get(position).getTradeQuantity();
                    stockVegetableList.get(position).setReceivedQuantity(receivedQuantity);
                    stockVegetableList.get(position).setExcessQuantity(excessVegQuantity);
                    stockVegetableList.get(position).setWastageQuantity(wastageVegQuantity);
                    stockVegetableList.get(position).setVegetableQuality(vegetableQuality);
                    stockVegetableList.get(position).setVegRate(vegRate);
                    database.updateVegetableStockTable(new Items(subscriptionVegQuantity,tradeQuantity,
                            excessVegQuantity,wastageVegQuantity,receivedQuantity,vegetableQuality,vegRate),
                            vegetableId,stockVegetableList.get(position).getSubscriptionDeliveryId());

                   if(Double.parseDouble(receivedQuantity) < Double.parseDouble(stockVegetableList.
                            get(position).getOrderedQuantity())){
                       holder.vegetableNameText.setTextColor(Color.parseColor("#E22727"));
                    }

                    else {
                       holder.vegetableNameText.setTextColor(Color.parseColor("#000000"));

                    }

                if(count == 0){
                    count = 1;
                }
                else {
                    count = 0;
                }

            }
        });
        holder.toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Log.e("nonPref. status.", "......"+isChecked);
                nonPreferanceStatus = ""+isChecked;
                stockVegetableList.get(position).setNonPreferanceStatus(nonPreferanceStatus);
                database.updateNonPrefStatusToVegetableStockTable(new Items(nonPreferanceStatus),
                        stockVegetableList.get(position).getVegetableId(),
                        stockVegetableList.get(position).getSubscriptionDeliveryId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return stockVegetableList.size();
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