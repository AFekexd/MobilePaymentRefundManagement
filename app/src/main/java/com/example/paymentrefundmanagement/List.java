package com.example.paymentrefundmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
public class List extends AppCompatActivity {
    private static final String TAG = List.class.getName();
    private static final String PREF_KEY = MainActivity.class.getPackage().toString();
    private FirebaseUser user;
    private RecyclerView mRecyclerView;

    private ArrayList<Data> dataList;

    private DataAdapter dataAdapter;

    private SharedPreferences preferences;
    private boolean view = true;
    private int gridNumber = 1;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            finish();
        }
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, gridNumber));
        dataList = new ArrayList<>();
        dataAdapter = new DataAdapter(this, dataList);
        mRecyclerView.setAdapter(dataAdapter);
        initData();
    }

    private void initData() {
        db.collection("payment")
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
           for (QueryDocumentSnapshot doc: queryDocumentSnapshots){
               Data data = doc.toObject(Data.class);
               dataList.add(data);
               data.setId(doc.getId());
           }
            dataAdapter.notifyDataSetChanged();
        });
    }


}