package com.exploratory.fact_o_pedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class FactListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fact_list);

        Intent intent = getIntent();
        String query = intent.getStringExtra(MainActivity.Q);

        TextView textView = findViewById(R.id.queryText);
        textView.setText(query);
    }
}