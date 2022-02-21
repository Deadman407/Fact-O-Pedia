package com.exploratory.fact_o_pedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
        if(query.length() == 0) {
            Toast.makeText(this,"Enter the query before search", Toast.LENGTH_LONG).show();
        }
        else{
            intent.putExtra(Q, query);
            startActivity(intent);
        }
    }
}