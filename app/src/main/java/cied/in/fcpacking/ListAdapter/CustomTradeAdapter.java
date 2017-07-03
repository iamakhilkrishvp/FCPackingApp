package cied.in.fcpacking.ListAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cied.in.fcpacking.Model.Items;
import cied.in.fcpacking.R;
import cied.in.fcpacking.TradeCustomerDetails;

/**
 * Created by cied on 11/4/17.
 */
public class CustomTradeAdapter extends RecyclerView.Adapter<CustomTradeAdapter.MyViewHolder> {

    private List<Items> customerDetailsList;
    Context mcontext;
    String userType;
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView fsidText,customerNameText, filledStatusText, zoneText, orderedQuantity,packingStatus;
        RelativeLayout relativeLayout;
        LinearLayout headerLayout;
        public MyViewHolder(View view) {
            super(view);

            fsidText = (TextView) view.findViewById(R.id.fsidText);
            customerNameText = (TextView) view.findViewById(R.id.nameText);
            filledStatusText = (TextView) view.findViewById(R.id.text2);
            zoneText = (TextView) view.findViewById(R.id.zoneText);
            orderedQuantity = (TextView) view.findViewById(R.id.qtyText);
            packingStatus = (TextView) view.findViewById(R.id.packingStatusText);
            relativeLayout = (RelativeLayout) view.findViewById(R.id.relativelayout);
            headerLayout = (LinearLayout) view.findViewById(R.id.linearLayout);

        }
    }

    public CustomTradeAdapter(Context context,List<Items> list) {
        this.customerDetailsList = list;
        this.mcontext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_trade_list, parent, false);



        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.fsidText.setText(customerDetailsList.get(position).getFsid());
        holder.customerNameText.setText(customerDetailsList.get(position).getCustomerName());
        holder.zoneText.setText(customerDetailsList.get(position).getSubGroup());
        holder.orderedQuantity.setText(customerDetailsList.get(position).getOrderedQuantity());
        holder.filledStatusText.setText(customerDetailsList.get(position).getStatus());
        if(customerDetailsList.get(position).getPackingStatus().equals("false"))
        {
            holder.packingStatus.setText("Not Completed");
            holder.packingStatus.setTextColor(Color.parseColor("#E22727"));
        }
        else {
            holder.packingStatus.setText("Completed");
            holder.packingStatus.setTextColor(Color.parseColor("#FF02B736"));
        }
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mcontext, TradeCustomerDetails.class);
                intent.putExtra("fsid",customerDetailsList.get(position).getFsid());
                intent.putExtra("customerId",""+customerDetailsList.get(position).getCustomerId());
                Log.e("customerId........",""+customerDetailsList.get(position).getCustomerId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return customerDetailsList.size();
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