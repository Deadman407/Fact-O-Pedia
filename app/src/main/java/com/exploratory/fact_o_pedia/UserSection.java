package com.exploratory.fact_o_pedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.exploratory.fact_o_pedia.Models.Comments;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserSection extends AppCompatActivity {
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText nme, cmnt;
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

        Bundle bundle = getIntent().getExtras();
        claim_text = bundle.getString("claim_text");
        search_query = bundle.getString("search_query");

        textView = findViewById(R.id.search_query);
        textView.setText(search_query);

        db = FirebaseFirestore.getInstance();
        db.collection("data").document(claim_text.toLowerCase().trim()).collection("comments")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        comments.clear();

                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        if(list.isEmpty()){
                            Toast.makeText(UserSection.this, "No comments available!", Toast.LENGTH_SHORT).show();
                        }
                        for(DocumentSnapshot d:list){
                            Comments obj = d.toObject(Comments.class);
                            comments.add(obj);
                        }
                        adapter = new CommentAdapter(comments);
                        recview.setAdapter(adapter);
                        progressDialog.dismiss();
                    }
                });
    }

    public void add(View view){
        createNewDialogBuilder();
    }

    public void createNewDialogBuilder() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View commentPopupView = getLayoutInflater().inflate(R.layout.popup, null);
        nme = (EditText) commentPopupView.findViewById(R.id.name);
        cmnt = (EditText) commentPopupView.findViewById(R.id.comment);
        post = (Button) commentPopupView.findViewById(R.id.saveButton);
        cancel = (Button) commentPopupView.findViewById(R.id.cancelButton);
        dbroot = FirebaseFirestore.getInstance();

        dialogBuilder.setView(commentPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, String> commentData = new HashMap<>();

                commentData.put("name", nme.getText().toString().trim());
                commentData.put("comment", cmnt.getText().toString().trim());

                dbroot.collection("data").document(claim_text.toLowerCase().trim())
                        .collection("comments").document()
                        .set(commentData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                nme.setText("");
                                cmnt.setText("");

                                Toast.makeText(UserSection.this, "Comment posted!", Toast.LENGTH_SHORT).show();

                                comments.add(adapter.getItemCount(), new Comments(commentData.get("name"), commentData.get("comment")));
                                adapter.notifyDataSetChanged();

                                dialog.dismiss();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UserSection.this, "Failed!", Toast.LENGTH_SHORT).show();
                            }
                        });

//                Map<String, String> items = new HashMap<>();
//                items.put("name", name.getText().toString().trim());
//                items.put("comment", comment.getText().toString().trim());
//                items.put("claim_text", claim_text.toLowerCase().trim());
//
//                dbroot.collection("comments").add(items)
//                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                            @Override
//                            public void onComplete(@NonNull Task<DocumentReference> task) {
//                                name.setText("");
//                                comment.setText("");
//                                Toast.makeText(getApplicationContext(), "Comment posted!", Toast.LENGTH_SHORT).show();
//
//                                comments.add(adapter.getItemCount(), new Comments(items.get("name"), items.get("comment")));
//                                adapter.notifyDataSetChanged();
//
//                                dialog.dismiss();
//                            }
//                        });
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