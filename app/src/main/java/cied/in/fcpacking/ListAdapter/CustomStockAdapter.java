package cied.in.fcpacking.ListAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import cied.in.fcpacking.Database.FCDatabase;
import cied.in.fcpacking.Model.Items;
import cied.in.fcpacking.R;

/**
 * Created by anujith47 on 14/06/17.
 */

public class CustomStockAdapter extends RecyclerView.Adapter<CustomStockAdapter.MyViewHolder> {

    private List<Items> stockDetails;
    Context mcontext;
    String userType;
    String stockQuantity;
    FCDatabase database;
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView vegetableNameText, saveButton;
        EditText stockText;
        Switch aSwitch;
        RelativeLayout relativeLayout;


        public MyViewHolder(View view) {
            super(view);

            vegetableNameText = (TextView) view.findViewById(R.id.textView98);
            saveButton = (TextView) view.findViewById(R.id.textView101);
            relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout4);
            stockText = (EditText) view.findViewById(R.id.editText9);
            aSwitch = (Switch) view.findViewById(R.id.switch2);

        }
    }

    public CustomStockAdapter(Context context,List<Items> list) {
        this.stockDetails = list;
        this.mcontext = context;
        database = new FCDatabase(mcontext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_stock_list, parent, false);



        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.vegetableNameText.setText(stockDetails.get(position).getVegetableName());
        holder.stockText.setText(stockDetails.get(position).getVegetableQuantity());
        if(stockDetails.get(position).getNonPreferanceStatus().equals("true")){
            holder.relativeLayout.setVisibility(View.VISIBLE);
            holder.aSwitch.setChecked(true);
        }
        else {
            holder.relativeLayout.setVisibility(View.GONE);
            holder.aSwitch.setChecked(false);

        }
        holder.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.stockText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.stockText.setCursorVisible(true);
            }
        });
        holder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean status) {
                String nonPrefStatus = ""+status;
                stockDetails.get(position).setNonPreferanceStatus(nonPrefStatus);
                database.updateStatusForStockTable(new Items(""+status),stockDetails.get(position).
                        getVegetableId());
                if(nonPrefStatus.equals("false")){
                    holder.relativeLayout.setVisibility(View.GONE);
                }
                else {
                    holder.relativeLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        holder.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.stockText.setCursorVisible(false);
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)mcontext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(holder.stockText.getWindowToken(), 0);
                }
                stockQuantity = holder.stockText.getText().toString();
                stockDetails.get(position).setVegetableQuantity(stockQuantity);
                database.updateStockTable(new Items(stockQuantity),stockDetails.get(position).
                        getVegetableId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return stockDetails.size();
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
