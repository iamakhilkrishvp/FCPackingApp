package cied.in.fcpacking.ListAdapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cied.in.fcpacking.Database.FCDatabase;
import cied.in.fcpacking.Model.Items;
import cied.in.fcpacking.R;

/**
 * Created by cied on 23/2/17.
 */
public class SelectedSubcriptionVegitableListAdapter extends RecyclerView.Adapter<SelectedSubcriptionVegitableListAdapter.MyViewHolder> {

    private List<Items> vegetableDetailsList;
    Context mcontext;
    FCDatabase database;
    private Activity activity;
    TextView distributedQuantityText;
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
        baseQuantity = Float.parseFloat(vegetableDetailsList.get(position).
                getVegetableQuantity());
        incrementQuantity = Float.parseFloat(vegetableDetailsList.get(position).
                getIncrementQuantity());
        holder.incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                totalAmount = baseQuantity + incrementQuantity;
                holder.amountText.setText(""+totalAmount+" Kg ");
                distributedQuantityText.setText(""+totalAmount);
               /* database.updateQuantityToVegetableDetailsTable(new Items(""+totalAmount),
                        vegetableDetailsList.get(position).getVegetableId(),
                        vegetableDetailsList.get(position).getFsid());
*/
                baseQuantity = totalAmount;

            }
        });

        holder.decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(totalAmount == 0){

                }
                else{

                    totalAmount = baseQuantity -  incrementQuantity;
                    holder.amountText.setText(totalAmount+" Kg ");
                    distributedQuantityText.setText(""+totalAmount);
                    /*database.updateQuantityToVegetableDetailsTable(new Items(""+totalAmount),
                            vegetableDetailsList.get(position).getVegetableId(),
                            vegetableDetailsList.get(position).getFsid());*/
                    baseQuantity = totalAmount;
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return vegetableDetailsList.size();
    }
}

