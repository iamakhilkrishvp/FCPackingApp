package cied.in.fcpacking.ListAdapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import cied.in.fcpacking.Database.FCDatabase;
import cied.in.fcpacking.Model.Items;
import cied.in.fcpacking.R;

/**
 * Created by cied on 11/4/17.
 */
public class SelectedTradeVegetableListAdapater extends RecyclerView.Adapter<SelectedTradeVegetableListAdapater.MyViewHolder> {

    private List<Items> vegetableDetailsList;
    Context mcontext;
    FCDatabase database;
    private Activity activity;
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView vegNameText;
        CheckBox checkBox;

        public MyViewHolder(View view) {
            super(view);
            vegNameText = (TextView) view.findViewById(R.id.textView16);
            database = new FCDatabase(mcontext);

        }
    }

    public  SelectedTradeVegetableListAdapater(Context context, List<Items> list) {

        this.vegetableDetailsList = list;
        this.mcontext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.selected_custom, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.vegNameText.setText(vegetableDetailsList.get(position).getVegetableName()+
                " (     "+vegetableDetailsList.get(position).getVegetableQuantity()+" )");


    }

    @Override
    public int getItemCount() {
        return vegetableDetailsList.size();
    }
}