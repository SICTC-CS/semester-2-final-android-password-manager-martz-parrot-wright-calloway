package com.example.passwordmanager.data;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.passwordmanager.DetailsActivity;
import com.example.passwordmanager.R;
import com.example.passwordmanager.data.model.Acount;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class AcountAdapter extends RecyclerView.Adapter<AcountAdapter.AcountViewHolder> {
    private List<Acount> acountList;
    private List<Acount> masterList;
    private CRUDManager crud = new CRUDManager();

    private String currentQuery = "";
    private String currentCategory = "All";

    public AcountAdapter(List<Acount> aList) {
        this.acountList = aList;
        this.masterList = new ArrayList<>(aList);
    }

    public class AcountViewHolder extends RecyclerView.ViewHolder {
        public CardView containerView;
        TextView tvService, tvDate, tvCost, tvType;
        Button tvURL;
        View vIndicator;

        public AcountViewHolder(@NonNull View itemView) {
            super(itemView);
            tvService = itemView.findViewById(R.id.tvService);
            tvURL = itemView.findViewById(R.id.tvURL);
            tvDate = itemView.findViewById(R.id.tvRowDate);
            tvCost = itemView.findViewById(R.id.tvRowAmount);
            vIndicator = itemView.findViewById(R.id.vIndicator);
            tvType = itemView.findViewById(R.id.tvCatagory);
            containerView = itemView.findViewById(R.id.acount_card_layout);

            containerView.setOnClickListener(v -> {
                Acount a = (Acount) containerView.getTag();
                Intent i = new Intent(v.getContext(), DetailsActivity.class);
                i.putExtra("account", (Serializable) a);
                v.getContext().startActivity(i);
            });

            containerView.setOnLongClickListener(v -> {
                Acount a = (Acount) containerView.getTag();
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Confirm Deletion");
                builder.setMessage("Are you sure you want to proceed?");
                builder.setIcon(android.R.drawable.ic_menu_delete);
                builder.setPositiveButton("Yes, Delete.", (dialog, which) -> {
                    crud.removeAcount(a, (success, errorMessage) -> {
                        if (!success) {
                            Snackbar.make(v, errorMessage, Snackbar.LENGTH_SHORT).show();
                        }
                    });
                    Toast.makeText(v.getContext(), a.getService() + " Account Deleted", Toast.LENGTH_LONG).show();
                });
                builder.setNegativeButton("No, Nevermind.", (dialog, which) -> dialog.dismiss());
                builder.create().show();
                return true;
            });
        }
    }

    @NonNull
    @Override
    public AcountAdapter.AcountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_acount, parent, false);
        return new AcountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AcountAdapter.AcountViewHolder holder, int position) {
        Acount t = acountList.get(position);
        holder.containerView.setTag(t);

        String serviceName = t.getService() != null ? t.getService() : "Unknown";
        holder.tvService.setText("Service: " + serviceName);

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        holder.tvDate.setText("Acount made on: " + sdf.format(new Date(t.getMadeOn())));

        if (t.getLink() != null && Patterns.WEB_URL.matcher(t.getLink()).matches()) {
            holder.tvURL.setVisibility(View.VISIBLE);
            holder.tvURL.setOnClickListener(v -> {
                Intent toWebsite = new Intent(Intent.ACTION_VIEW, Uri.parse(t.getLink()));
                v.getContext().startActivity(toWebsite);
            });
        } else {
            holder.tvURL.setVisibility(View.GONE);
        }

        holder.tvType.setText(t.getCategory());
        holder.tvCost.setText(String.format("$%.2f", t.getCost()) + "/" + t.getPaymentInterval());
        holder.tvCost.setVisibility(t.isSubscription() ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return acountList.size();
    }

    public void filter(String query, String category) {
        this.currentQuery = query.toLowerCase();
        this.currentCategory = category;
        applyFilters();
    }

    private void applyFilters() {
        acountList = masterList.stream()
                .filter(a -> {
                    boolean matchesQuery = a.getName().toLowerCase().contains(currentQuery) ||
                            a.getService().toLowerCase().contains(currentQuery) ||
                            a.getEmail().toLowerCase().contains(currentQuery);
                    boolean matchesCategory = currentCategory.equals("All") || a.getCategory().equals(currentCategory);
                    return matchesQuery && matchesCategory;
                })
                .collect(Collectors.toList());
        notifyDataSetChanged();
    }

    public void importFirebaseData() {
        CRUDManager.readAllAcount(new CRUDManager.AcountListCallback() {
            @Override
            public void onAcountsLoaded(List<Acount> acounts) {
                masterList = new ArrayList<>(acounts);
                applyFilters();
            }

            @Override
            public void onAcountsLoadedError(String errorMessage) {
                Log.e("Error", errorMessage);
            }
        });
    }
}