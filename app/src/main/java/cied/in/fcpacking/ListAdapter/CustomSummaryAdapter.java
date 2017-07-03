package cied.in.fcpacking.ListAdapter;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
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
 * Created by cied on 29/3/17.
 */
public class CustomSummaryAdapter extends RecyclerView.Adapter<CustomSummaryAdapter.MyViewHolder> {

    private List<Items> farmDetails;
    Context mcontext;
    String userType;
    FCDatabase database;
    String cycleId;
    ArrayList<Items> vegetableDetailsList = new ArrayList<Items>();
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView farmNameText,itemText;
        RecyclerView recyclerView;
        public MyViewHolder(View view) {
            super(view);

            farmNameText = (TextView) view.findViewById(R.id.textView58);
            itemText = (TextView) view.findViewById(R.id.textView61);

        }
    }

    public CustomSummaryAdapter(Context context,List<Items> list,String cycleid) {
        this.farmDetails = list;
        this.mcontext = context;
        database = new FCDatabase(mcontext);
        this.cycleId = cycleid;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_summary_list, parent, false);




        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.farmNameText.setText(farmDetails.get(position).getFarmName());
        vegetableDetailsList = (ArrayList<Items>) database.getVegetableOrderTableDetails
                (farmDetails.get(position).getFarmId(),1,cycleId);
        Log.e("vegetableDetails....",""+vegetableDetailsList.size());
        StringBuffer sbf = new StringBuffer();
        String[] array = new String[vegetableDetailsList.size()];
        if (vegetableDetailsList.size() > 0) {


            sbf.append(vegetableDetailsList.get(0).getVegetableName()+
                    " ( "+vegetableDetailsList.get(0).getReceivedQuantity()+" Kg )");
            for(int i=1; i < vegetableDetailsList.size(); i++){
                sbf.append(",\n").append(vegetableDetailsList.get(i).getVegetableName()+" " +
                        "( "+vegetableDetailsList.get(i).getReceivedQuantity()+" Kg )");
            }
            holder.itemText.setText("Farming Colors \nOrder for  27 / 04 / 2017 \n"+sbf.toString()+" \nKindly confirm the order as soon as possible !");
        }

    }

    @Override
    public int getItemCount() {
        return farmDetails.size();
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

class CustomVegetableListAdapter extends RecyclerView.Adapter<CustomVegetableListAdapter.MyViewHolder> {

    private List<Items> vegetableDetails;
    Context mcontext;
    String userType;
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView vegNameText,vegQtyText;
        RecyclerView recyclerView;
        public MyViewHolder(View view) {
            super(view);

            vegNameText = (TextView) view.findViewById(R.id.textView59);
            vegQtyText = (TextView) view.findViewById(R.id.textView60);

        }
    }

    public CustomVegetableListAdapter(Context context,List<Items> list) {
        this.vegetableDetails = list;
        Log.e("vegetabl ssss",""+vegetableDetails.size());
        this.mcontext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_veg_list_summary, parent, false);



        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.vegNameText.setText(vegetableDetails.get(position).getVegetableName());
        holder.vegQtyText.setText(vegetableDetails.get(position).getOrderedQuantity() +"Kg");
        Log.e("name.......",vegetableDetails.get(position).getVegetableName());

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