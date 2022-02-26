package com.exploratory.fact_o_pedia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.exploratory.fact_o_pedia.Models.Claims;
import com.exploratory.fact_o_pedia.Models.FactApiResponse;

import java.util.List;

public class FactListActivity extends AppCompatActivity implements SelectListener{
    public static String q;
    RecyclerView recyclerView;
    CustomAdaptor adaptor;
    ProgressDialog dialog;
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fact_list);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Fetching claims...");
        dialog.show();

        Intent intent = getIntent();
        query = intent.getStringExtra(MainActivity.Q);

        RequestManager manager = new RequestManager(this);
        manager.getClaimsList(listener, query);
    }

    private final OnFetchDataListener<FactApiResponse> listener = new OnFetchDataListener<FactApiResponse>() {
        @Override
        public void onFetchData(List<Claims> claims, String message) {
            if(claims == null){
                Intent intent = new Intent(FactListActivity.this, FactcheckRequest.class);
                intent.putExtra("q", query);
                startActivity(intent);
                dialog.dismiss();
            }
            else{
                String claim_text = "";
                for(int i=0; i<claims.size(); i++){
                    claim_text += claims.get(i).getText();
                    claim_text += claims.get(i).getClaimReview().get(0).getTitle();
                }
                Log.d("claim_text", claim_text);
                showFacts(claims);
                dialog.dismiss();
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