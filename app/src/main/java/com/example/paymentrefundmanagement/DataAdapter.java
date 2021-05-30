package com.example.paymentrefundmanagement;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<Data> CurrentData = new ArrayList<>();
    private ArrayList<Data> DataAll = new ArrayList<>();
    private Context mContext;
    private int lastPosition = -1;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        DataAdapter(Context mContext, ArrayList<Data> currentData) {
        this.CurrentData = currentData;
        this.DataAll = currentData;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.listdata, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.ViewHolder holder, int position) {
        Data currentItem = CurrentData.get(position);
        holder.bindTo(currentItem);


        if(holder.getAdapterPosition() > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_row);
            holder.itemView.startAnimation(animation);
            lastPosition = holder.getAdapterPosition();
        }
    }
    @Override
    public int getItemCount() {
        return CurrentData.size();
    }
    public void getData(int position){
            firestore.collection("edit").add(CurrentData.get(position));
            Intent intent = new Intent(mContext, EditActivity.class);
            mContext.startActivity(intent);
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mName;
        private TextView mToWho;
        private TextView mAmount;
        private TextView mTax;
        private TextView mDate;
        private TextView mStatusDate;
        private TextView mTypeData;
        private Button delete;
        ViewHolder(View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.nameCardText);
            mToWho = itemView.findViewById(R.id.toWhoCardText);
            mAmount = itemView.findViewById(R.id.AmountCardText);
            mTax = itemView.findViewById(R.id.TaxCardText);
            mDate = itemView.findViewById(R.id.DateCardText);
            mStatusDate = itemView.findViewById(R.id.StatusDateCardText);
            delete = itemView.findViewById(R.id.delete);
            mTypeData = itemView.findViewById(R.id.TypeDataText);
            itemView.findViewById(R.id.EditButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getData(getPosition());
                }
            });
            itemView.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    remove(getPosition());
                }
            });
            itemView.findViewById(R.id.EditButton);
        }


        private void remove(int position) {
            String id = CurrentData.get(position).getId();
            System.out.println(position +" Position:");
            System.out.println(id +" ID:");
            System.out.println(CurrentData.get(position).getpOrR() + ": VALUE");

                firestore.collection("payment").document(id).delete();
                CurrentData.remove(position);
                notifyItemRemoved(position);
                notifyItemChanged(position, CurrentData.size());
        }


        void bindTo(Data currentItem){
            mName.setText(currentItem.getName());
            mToWho.setText(currentItem.getToWho());
            mAmount.setText(currentItem.getAmount());
            mTax.setText(currentItem.getTax());
            mDate.setText(currentItem.getDate());
            mStatusDate.setText(currentItem.getStatusDate());
            mTypeData.setText(currentItem.getpOrR());
        }
}
}
