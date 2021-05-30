package com.example.paymentrefundmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {
    private static final String LOG_TAG = AddActivity.class.getName();
    EditText nameET;
    EditText toWho;
    EditText amountET;
    EditText taxET;
    EditText dateET;
    EditText statusdateET;
    RadioGroup radioGroupAccount;
    RadioGroup radioGroupType;
    RadioGroup radioGroupMethod;
    RadioButton radioButton;
 Map<String, Object> data = new HashMap<>();
 private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

    }

    private void CheckRadioButtons() {
        radioGroupAccount = findViewById(R.id.AccountGroup);
        radioGroupMethod = findViewById(R.id.MethodGroup);
        radioGroupType = findViewById(R.id.TypeGroup);

        //AccountCheck
        int selectedIDAccount = radioGroupAccount.getCheckedRadioButtonId();
        if (selectedIDAccount == R.id.PartyAccountRadioButton){
            data.put("AccountType", "Party");
        }
        else{
            data.put("AccountType", "Financial");
        }

        //MethodCheck
        int selectedIDMethod = radioGroupMethod.getCheckedRadioButtonId();
        if (selectedIDMethod == R.id.PaypalRadioButton){
            data.put("Method", "Paypal");
        }
        else{
            data.put("Method", "Credit Card");
        }

        //TypeCheck
        int selectedIDType = radioGroupType.getCheckedRadioButtonId();
        if (selectedIDType == R.id.PaymentRadioButton){
            sendPayment();
        }
        else if(selectedIDType == R.id.RefundRadioButton){
            sendRefund();
        }
        else{
            Toast.makeText(AddActivity.this, "Invalid Form!", Toast.LENGTH_LONG).show();
        }
    }

    public void sendPayment() {
        data.put("pOrR", "Payment");
        firestore.collection("payment").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(LOG_TAG,"Completed: "+ documentReference.getId());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(LOG_TAG,"ERROR SENDING DATA");
            }
        });
    }

    public void sendRefund() {
        data.put("pOrR", "Refund");
        firestore.collection("payment").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(LOG_TAG,"Completed: "+ documentReference.getId());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(LOG_TAG,"ERROR SENDING DATA");
            }
        });
    }

    public void sendData(View view) {
        nameET = findViewById(R.id.editTextTextPersonName);
        toWho = findViewById(R.id.toWhoEditText);
        amountET = findViewById(R.id.AmountEditText);
        taxET = findViewById(R.id.TaxEditText);
        dateET = findViewById(R.id.DateEditText);
        statusdateET = findViewById(R.id.StatusDateEditText);

        data.put("Name", nameET.getText().toString());
        data.put("ToWho", toWho.getText().toString());
        data.put("Amount", amountET.getText().toString());
        data.put("Tax", taxET.getText().toString());
        data.put("Date", dateET.getText().toString());
        data.put("StatusDate", statusdateET.getText().toString());
        CheckRadioButtons();
        Intent intent = new Intent(this, ChoseActivity.class);
        startActivity(intent);

    }
}