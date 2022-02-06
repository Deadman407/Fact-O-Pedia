package com.exploratory.fact_o_pedia;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CustomViewHolder extends RecyclerView.ViewHolder {
    TextView claim_title, text_claimant, claim_publisher, claim_rating;
    CardView cardView;
    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);

        claim_title = itemView.findViewById(R.id.claim_title);
        text_claimant = itemView.findViewById(R.id.text_claimant);
        claim_publisher = itemView.findViewById(R.id.claim_publisher);
        claim_rating = itemView.findViewById(R.id.claim_rating);
        cardView = itemView.findViewById(R.id.main_container);
    }
}
