package com.example.quranproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quranproject.R;

import java.util.ArrayList;

public class AyahAdapter extends RecyclerView.Adapter<AyahAdapter.AyahViewHolder>{

    Context context;
    ArrayList<String> arrayList ;
   public AyahAdapter( Context context, ArrayList arrayList){
        this.arrayList = arrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public AyahViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ayah_item,parent,false);

        return new AyahViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AyahViewHolder holder, int position) {
       holder.textView.setText(arrayList.get(position));


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class AyahViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public AyahViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.ayah_TV);
        }
    }

}
