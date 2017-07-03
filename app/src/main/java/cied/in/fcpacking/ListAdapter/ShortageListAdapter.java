package cied.in.fcpacking.ListAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cied.in.fcpacking.Database.FCDatabase;
import cied.in.fcpacking.Model.Items;
import cied.in.fcpacking.R;

/**
 * Created by cied on 7/5/17.
 */

public class ShortageListAdapter  extends RecyclerView.Adapter<ShortageListAdapter.MyViewHolder> {

    private List<Items> vegetableDetails;
    Context mcontext;
    String userType;
    int cycleId;
    FCDatabase database;
    float shortage,orderedQty,receivedQty,totalRecievedQty = 0;
    ArrayList<Items> vegOrderList = new ArrayList<Items>();
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView farmNameText,itemText;
        RecyclerView recyclerView;
        public MyViewHolder(View view) {
            super(view);

            itemText = (TextView) view.findViewById(R.id.textView63);
        }
    }

    public ShortageListAdapter(Context context,List<Items> list,int cycleid) {
        this.vegetableDetails = list;
        this.mcontext = context;
        database = new FCDatabase(mcontext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_shortage_list, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        totalRecievedQty = 0;
        orderedQty = Float.parseFloat(vegetableDetails.get(position).getOrderedQuantity());
        cycleId = Integer.parseInt(vegetableDetails.get(position).getSubscriptionDeliveryId());
        Log.e("orderedQty....",""+orderedQty);
        Log.e("cycleId....",""+cycleId);
        vegOrderList = (ArrayList<Items>) database.getVegetableOrderTableDetails
                (cycleId,vegetableDetails.get(position).getVegetableId());
        Log.e("ddddd..",""+vegOrderList.size());
        for (int i = 0; i<vegOrderList.size(); i++){
            totalRecievedQty =totalRecievedQty+ Float.parseFloat(vegOrderList.get(i).getReceivedQuantity());
        }

        if(orderedQty> totalRecievedQty){
            shortage = orderedQty - totalRecievedQty;
            if (shortage < 0.2 ){
                holder.itemText.setVisibility(View.GONE);
            }
            else {
              //  String text = vegetableDetails.get(position).getVegetableName()+" <font color='red'>( "+shortage+" Kg)</font>";
                holder.itemText.setText(vegetableDetails.get(position).getVegetableName()+" --- "+
                        shortage+"  Kg ");
            }

        }
        else {
            holder.itemText.setVisibility(View.GONE);
        }

    }


    @Override
    public int getItemCount() {
        return vegetableDetails.size();
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

