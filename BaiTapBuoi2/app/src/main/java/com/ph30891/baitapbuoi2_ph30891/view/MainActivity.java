package com.ph30891.baitapbuoi2_ph30891.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal;
import com.ph30891.baitapbuoi2_ph30891.databinding.ActivityMainBinding;
import com.ph30891.baitapbuoi2_ph30891.view.model.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    RecycleAdapter adapter;
    ArrayList<Message> list = new ArrayList<>();
    DatabaseReference reference;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        reference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        String uId = user.getUid();
        String uEmail = user.getEmail();
        String date = new SimpleDateFormat("dd-MM-yy HH:mm a").format(Calendar.getInstance().getTime());

        binding.btnSend.setOnClickListener(v -> {
            String message = String.valueOf(binding.edMess.getText());
            reference.child("Message").push().setValue(new Message(uEmail, message, date)).addOnCompleteListener(task -> {
              binding.edMess.setText("");
              // push notification

            });
        });
        binding.btnLogOut.setOnClickListener(v -> {

        });

        adapter = new RecycleAdapter(this,list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        binding.rcvMessage.setLayoutManager(linearLayoutManager);
        binding.rcvMessage.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getDataMessage();
    }

    private void getDataMessage(){
        reference.child("Message").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Message message = dataSnapshot.getValue(Message.class);
                    list.add(message);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}