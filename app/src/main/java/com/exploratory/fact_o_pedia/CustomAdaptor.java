package com.exploratory.fact_o_pedia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exploratory.fact_o_pedia.Models.Claims;

import java.util.List;

public class CustomAdaptor extends RecyclerView.Adapter<CustomViewHolder> {
    private Context context;
    private List<Claims> claims;

    public CustomAdaptor(Context context, List<Claims> claims) {
        this.context = context;
        this.claims = claims;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.fact_list_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.claim_title.setText(claims.get(position).getClaimReview().get(0).getTitle());
        holder.text_claimant.setText(claims.get(position).getClaimant());
        holder.claim_publisher.setText(claims.get(position).getClaimReview().get(0).getPublisher().getName());
        holder.claim_rating.setText(claims.get(position).getClaimReview().get(0).getTextualRating());
    }

    @Override
    public int getItemCount() {
        return claims.size();
    }
}
