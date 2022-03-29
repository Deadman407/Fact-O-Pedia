package com.exploratory.fact_o_pedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.exploratory.fact_o_pedia.Models.Comments;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserSection extends AppCompatActivity {
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText name, comment;
    private Button post, cancel;
    FirebaseFirestore dbroot;
    String claim_text;
    String search_query;
    TextView textView;
    RecyclerView recview;
    ArrayList<Comments> comments;
    FirebaseFirestore db;
    CommentAdapter adapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_section);
        getSupportActionBar().hide();

        recview = (RecyclerView) findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));
        comments = new ArrayList<>();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching comments...");
        progressDialog.show();

        db = FirebaseFirestore.getInstance();
        db.collection("comments").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list){
                            Comments obj = d.toObject(Comments.class);
                            comments.add(obj);
                        }
                        adapter = new CommentAdapter(comments);
                        recview.setAdapter(adapter);
                        progressDialog.dismiss();
                    }
                });

        Bundle bundle = getIntent().getExtras();
        claim_text = bundle.getString("claim_text");
        search_query = bundle.getString("search_query");

        textView = findViewById(R.id.search_query);
        textView.setText(search_query);
    }

    public void add(View view){
        createNewDialogBuilder();
    }

    public void createNewDialogBuilder() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View commentPopupView = getLayoutInflater().inflate(R.layout.popup, null);
        name = (EditText) commentPopupView.findViewById(R.id.name);
        comment = (EditText) commentPopupView.findViewById(R.id.comment);
        post = (Button) commentPopupView.findViewById(R.id.saveButton);
        cancel = (Button) commentPopupView.findViewById(R.id.cancelButton);
        dbroot = FirebaseFirestore.getInstance();

        dialogBuilder.setView(commentPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, String> items = new HashMap<>();
                items.put("name", name.getText().toString().trim());
                items.put("comment", comment.getText().toString().trim());
                items.put("claim_text", claim_text.trim());

                dbroot.collection("comments").add(items)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                name.setText("");
                                comment.setText("");
                                Toast.makeText(getApplicationContext(), "Inserted Successfully!", Toast.LENGTH_SHORT).show();

                                dialog.dismiss();
                            }
                        });
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}