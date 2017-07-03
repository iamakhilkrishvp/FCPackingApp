package cied.in.fcpacking.ListAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cied.in.fcpacking.Model.Items;
import cied.in.fcpacking.R;

/**
 * Created by cied on 18/5/17.
 */

public class NonPreferanceVegetableListAdapter extends RecyclerView.Adapter<NonPreferanceVegetableListAdapter.MyViewHolder> {

    private List<Items> nonPreferanceList;
    Context mcontext;
    String userType;
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView vegetableNameText, quantityText;
        public MyViewHolder(View view) {
            super(view);

            vegetableNameText = (TextView) view.findViewById(R.id.fsidText);
            quantityText = (TextView) view.findViewById(R.id.nameText);

        }
    }

    public NonPreferanceVegetableListAdapter(Context context,List<Items> list) {
        this.nonPreferanceList = list;
        this.mcontext = context;
    }

    @Override
    public NonPreferanceVegetableListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_nonpreference_vegetable_list, parent, false);



        return new NonPreferanceVegetableListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final NonPreferanceVegetableListAdapter.MyViewHolder holder, final int position) {

        holder.vegetableNameText.setText(nonPreferanceList.get(position).getVegetableName());
        holder.quantityText.setText(nonPreferanceList.get(position).getVegetableQuantity());


    }

    @Override
    public int getItemCount() {
        return nonPreferanceList.size();
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