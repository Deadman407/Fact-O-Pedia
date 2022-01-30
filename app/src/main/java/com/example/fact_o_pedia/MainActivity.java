package com.example.fact_o_pedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String Q = "com.example.fact_o_pedia.Q";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void search(View view) {

        Intent intent = new Intent(this, FactListActivity.class);
        EditText editText = findViewById(R.id.editText);
        String query = editText.getText().toString();
        intent.putExtra(Q, query);
        startActivity(intent);
    }
}