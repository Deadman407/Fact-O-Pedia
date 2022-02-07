package com.exploratory.fact_o_pedia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.exploratory.fact_o_pedia.Models.Claims;
import com.exploratory.fact_o_pedia.Models.FactApiResponse;

import java.util.List;

public class FactListActivity extends AppCompatActivity implements SelectListener{
    RecyclerView recyclerView;
    CustomAdaptor adaptor;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fact_list);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Fetching claims...");
        dialog.show();

        Intent intent = getIntent();
        String query = intent.getStringExtra(MainActivity.Q);

        RequestManager manager = new RequestManager(this);
        manager.getClaimsList(listener, query);
    }

    private final OnFetchDataListener<FactApiResponse> listener = new OnFetchDataListener<FactApiResponse>() {
        @Override
        public void onFetchData(List<Claims> claims, String message) {
            showFacts(claims);
            dialog.dismiss();
            if (claims.isEmpty()){
                Toast.makeText(FactListActivity.this, "No Data Found!", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onError(String message) {
            Toast.makeText(FactListActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private void showFacts(List<Claims> claims) {
        recyclerView = findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        adaptor = new CustomAdaptor(this, claims, this);
        recyclerView.setAdapter(adaptor);
    }

    @Override
    public void OnClaimClicked(Claims claims) {
        startActivity(new Intent(FactListActivity.this, DetailsActivity.class)
        .putExtra("data", claims));
    }
}