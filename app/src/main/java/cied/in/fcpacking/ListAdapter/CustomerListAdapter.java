package cied.in.fcpacking.ListAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cied.in.fcpacking.CustomerDetailsActivity;
import cied.in.fcpacking.Model.Items;
import cied.in.fcpacking.R;

/**
 * Created by cied on 23/2/17.
 */
public class CustomerListAdapter  extends RecyclerView.Adapter<CustomerListAdapter.MyViewHolder> {

    private List<Items> customerDetailsList;
    Context mcontext;
    String userType;
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView fsidText,customerNameText, filledStatusText,prefStatusText,packQuantity,packingStatus;
        RelativeLayout relativeLayout;
        LinearLayout headerLayout;
        public MyViewHolder(View view) {
            super(view);

            fsidText = (TextView) view.findViewById(R.id.fsidText);
            customerNameText = (TextView) view.findViewById(R.id.nameText);
            filledStatusText = (TextView) view.findViewById(R.id.text2);
            prefStatusText = (TextView) view.findViewById(R.id.zoneText);
            packQuantity = (TextView) view.findViewById(R.id.qtyText);
            packingStatus = (TextView) view.findViewById(R.id.packingStatusText);
            relativeLayout = (RelativeLayout) view.findViewById(R.id.relativelayout);
            headerLayout = (LinearLayout) view.findViewById(R.id.linearLayout);

        }
    }

    public CustomerListAdapter(Context context,List<Items> list) {
        this.customerDetailsList = list;
        this.mcontext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_layout_for_list, parent, false);



        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.fsidText.setText(customerDetailsList.get(position).getFsid());
        holder.customerNameText.setText(customerDetailsList.get(position).getCustomerName());
       // holder.filledStatusText.setText(farmDetailsList.get(position).getFilledStatus());
        holder.prefStatusText.setText(customerDetailsList.get(position).getPreferanceStatus());
        holder.packQuantity.setText(customerDetailsList.get(position).getOrderedQuantity()+
                " ( "+customerDetailsList.get(position).getDistributedQuantity()+" ) ");
        holder.packingStatus.setText(customerDetailsList.get(position).getPackingStatus());
        if(customerDetailsList.get(position).getPackingStatus().equals("false"))
        {
            holder.packingStatus.setText("Not Completed");
            holder.packingStatus.setTextColor(Color.parseColor("#E22727"));
        }
        else {
            holder.packingStatus.setText("Completed");
            holder.packingStatus.setTextColor(Color.parseColor("#FF02B736"));
        }

        if( holder.prefStatusText.getText().toString().equals("true")){

            holder.headerLayout.setBackgroundColor(Color.parseColor("#7ccaa9"));
            holder.fsidText.setTextColor(Color.parseColor("#ffffff"));
            holder.customerNameText.setTextColor(Color.parseColor("#ffffff"));
        }
        else {
            holder.headerLayout.setBackgroundColor(Color.parseColor("#f5a64a"));
            holder.fsidText.setTextColor(Color.parseColor("#ffffff"));
            holder.customerNameText.setTextColor(Color.parseColor("#ffffff"));
        }
        if(customerDetailsList.get(position).getFilledStatus().equals("false")){

            holder.filledStatusText.setText("BOX NOT FILLED !!");
            holder.filledStatusText.setTextColor(Color.parseColor("#E22727"));
        }
        else {
            holder.filledStatusText.setText("BOX  FILLED !!");
        }
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(mcontext, CustomerDetailsActivity.class);
                intent.putExtra("fsid",customerDetailsList.get(position).getFsid());
                intent.putExtra("CYCLE_ID",customerDetailsList.get(position).getSubscriptionDeliveryId());
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