package com.example.architectural;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.architectural.adapter.MessageAdapter;
import com.example.architectural.model.MessageModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MessageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private ArrayList<MessageModel> messageModelArrayList;
    private TextInputLayout textInputLayout;
    private TextInputEditText textInputEditText;
    private ImageView imageView;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        messageModelArrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        textInputLayout = findViewById(R.id.textInputLayout);
        textInputEditText = findViewById(R.id.textInputEditText);
        imageView = findViewById(R.id.imageView);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = firebaseAuth.getCurrentUser();
        String id = firebaseUser.getUid();
        String email = firebaseUser.getEmail();
        String simpleDateFormat = new SimpleDateFormat("dd-MM-yy HH:mm:ss a").format(Calendar.getInstance().getTime());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = textInputEditText.getText().toString();
                databaseReference.child("Messages").push().setValue(new MessageModel(email, message, simpleDateFormat)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        textInputLayout.getEditText().setText("");
                    }
                });
            }
        });
        messageAdapter = new MessageAdapter(this, messageModelArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(messageAdapter);
    }

    private void receiveMessages() {
        databaseReference.child("Messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageModelArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    MessageModel messageModel = dataSnapshot.getValue(MessageModel.class);
                    messageAdapter.addMessage(messageModel);
                    // messageAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        receiveMessages();
    }
}