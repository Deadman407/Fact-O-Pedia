package com.exploratory.fact_o_pedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String Q = "com.exploratory.fact_o_pedia.Q";
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
    }

    public void search(View view) {
        Intent intent = new Intent(this, FactListActivity.class);

        String query = editText.getText().toString();

        intent.putExtra(Q, query);
        startActivity(intent);
    }
}