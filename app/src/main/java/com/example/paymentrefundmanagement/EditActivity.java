package com.example.paymentrefundmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditActivity extends AppCompatActivity {
    private static final String LOG_TAG = EditActivity.class.getName();
    Data currentData = new Data();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    EditText nameET;
    EditText toWho;
    EditText amountET;
    EditText taxET;
    EditText dateET;
    EditText statusdateET;
    RadioButton radioButtonRefund;
    RadioButton radioButtonPayment;
    RadioButton radioButtonParty;
    RadioButton radioButtonFinancial;
    RadioButton radioButtonCredit;
    RadioButton radioButtonPaypal;
    RadioGroup radioGroupMethod;
    RadioGroup radioGroupType;
    RadioGroup radioGroupAccount;
    Map<String, Object> data = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        nameET = findViewById(R.id.editTextTextPersonNameEDIT);
        toWho = findViewById(R.id.toWhoEditTextEDIT);
        amountET = findViewById(R.id.AmountEditTextEDIT);
        taxET = findViewById(R.id.TaxEditTextEDIT);
        dateET = findViewById(R.id.DateEditTextEDIT);
        statusdateET = findViewById(R.id.StatusDateEditTextEDIT);

        radioButtonRefund = findViewById(R.id.RefundRadioButtonEDIT);
        radioButtonPayment = findViewById(R.id.PaymentRadioButtonEDIT);
        radioButtonParty = findViewById(R.id.PartyAccountRadioButtonEDIT);
        radioButtonFinancial = findViewById(R.id.FinancialRadioButtonEDIT);
        radioButtonCredit = findViewById(R.id.CreditCardRadioButtonEdit);
        radioButtonPaypal = findViewById(R.id.PaypalRadioButtonEdit);

        radioGroupType = findViewById(R.id.TypeGroupEDIT);
        radioGroupMethod = findViewById(R.id.MethodGroupEdit);
        radioGroupAccount = findViewById(R.id.AccountGroupEDIT);
        getData();
    }
    public void getData(){
        db.collection("edit")
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot doc: queryDocumentSnapshots){
                Data data2 = doc.toObject(Data.class);
                currentData = data2;
            }

        });


        nameET.setText(currentData.getName());
        toWho.setText(currentData.getToWho());
        amountET.setText(currentData.getAmount());
        taxET.setText(currentData.getTax());
        dateET.setText(currentData.getDate());
        statusdateET.setText(currentData.getStatusDate());
/*
        if (currentData.getpOrR().toString().equals("Refund")){
            radioButtonRefund.setSelected(true);
        }
        else{
            radioButtonPayment.setSelected(true);
        }

        if (currentData.getAccountType().equals("Financial")){
            radioButtonFinancial.setSelected(true);
        }
        else{
            radioButtonParty.setSelected(true);
        }

        if (currentData.getMethod().equals("Paypal")){
            radioButtonPaypal.setSelected(true);
        }
        else{
            radioButtonCredit.setSelected(true);
        }
*/
    }

    public void resendData(View view) {
        data.put("Name", nameET.getText().toString());
        data.put("ToWho", toWho.getText().toString());
        data.put("Amount", amountET.getText().toString());
        data.put("Tax", taxET.getText().toString());
        data.put("Date", dateET.getText().toString());
        data.put("StatusDate", statusdateET.getText().toString());
        //AccountCheck
        int selectedIDAccount = radioGroupAccount.getCheckedRadioButtonId();
        if (selectedIDAccount == R.id.PartyAccountRadioButtonEDIT){
            data.put("AccountType", "Party");
        }
        else{
            data.put("AccountType", "Financial");
        }

        //MethodCheck
        int selectedIDMethod = radioGroupMethod.getCheckedRadioButtonId();
        if (selectedIDMethod == R.id.PaypalRadioButtonEdit){
            data.put("Method", "Paypal");
        }
        else{
            data.put("Method", "Credit Card");
        }

        //TypeCheck
        int selectedIDType = radioGroupType.getCheckedRadioButtonId();
        if (selectedIDType == R.id.RefundRadioButtonEDIT){
           data.put("Type", "Payment");
        }
        else{
            data.put("Type", "Refund");
        }

        db.collection("payment").document(currentData.getId()).update(data);
        db.collection("edit").document(currentData.getId()).delete();

        Intent intent = new Intent(this, List.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        db.collection("edit").document(currentData.getId()).delete();
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.collection("edit").document(currentData.getId()).delete();
    }
    public void backTolist(View view) {
        db.collection("edit").document(currentData.getId()).delete();
        Intent intent = new Intent(this, List.class);
        startActivity(intent);
    }
}