package com.example.passwordmanager.data;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.passwordmanager.R;
import com.example.passwordmanager.data.model.LoggedInUser;
import com.example.passwordmanager.data.model.Acount;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class AcountAdapter extends RecyclerView.Adapter<AcountAdapter.AcountViewHolder> {
    private List<Acount> acountList;
    private int currentSortMode = 0;
    private List<Acount> masterList;
    private int currentFilterMode = 0;
    private String currentUser;

    private double totalCosts = 0;

    public AcountAdapter(List<Acount>aList){
        this.acountList = aList;
        this.masterList = new ArrayList<>(aList);
    }
    



    public class AcountViewHolder extends RecyclerView.ViewHolder{

        public CardView containerView;
        TextView tvService, tvDate, tvCost;

        View vIndicator;

        public AcountViewHolder(@NonNull View itemView) {
            super(itemView);
            tvService = itemView.findViewById(R.id.tvService);
            tvDate = itemView.findViewById(R.id.tvRowDate);
            tvCost = itemView.findViewById(R.id.tvRowAmount);
            vIndicator = itemView.findViewById(R.id.vIndicator);
            containerView = itemView.findViewById(R.id.acount_card_layout);
        }
    }
    @NonNull
    @Override
    public AcountAdapter.AcountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //this says which xml files are for the recycler view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_acount,parent,false);
        return new AcountViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull AcountAdapter.AcountViewHolder holder, int position) {
        Acount t = acountList.get(position);
        holder.containerView.setTag(t); //field variable that holds the data
        Log.d("Acount list--OnBind", t.toString());

        String serviceName = t.getService() != null ? t.getService() : "Unknown";
        String[] service = serviceName.split(" ");

        if (service.length > 0 && service[0] != null && !service[0].isEmpty()) {
            service[0] = service[0].substring(0, 1).toUpperCase() + service[0].substring(1);
        }

        for (int i = 0; i < service.length; i++) {
            if (service[i] != null && !service[i].isEmpty() && !"a^an^the^and^but^or^for^nor^so^yet^at^by^for^in^of^on^to^up".contains(service[i].toLowerCase())) {
                service[i] = service[i].substring(0, 1).toUpperCase() + service[i].substring(1);
            }
        }
        holder.tvService.setText("Service: " + String.join(" ", service));
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        holder.tvDate.setText("Acount made on: "+sdf.format(new Date(t.getMadeOn())));

        holder.tvCost.setText(String.format("$%.2f", t.getCost())+" per "+t.getPaymentInterval());
    }
    @Override
    public int getItemCount() {
        return acountList.size();
    }
    public void importFirebaseData(){
        CRUDManager.readAllAcount(new CRUDManager.AcountListCallback() {
            @Override
            public void onAcountsLoaded(List<Acount> acounts) {
                masterList = new ArrayList<>(acounts);
                acountList=masterList;
                notifyDataSetChanged();
                Log.d("Acount list", acountList.toString());
            }
            @Override
            public void onAcountsLoadedError(String errorMessage) {
                Log.e("Error", errorMessage);
            }

        });}
}
