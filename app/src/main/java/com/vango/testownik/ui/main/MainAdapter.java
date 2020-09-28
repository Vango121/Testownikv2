package com.vango.testownik.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vango.testownik.R;
import com.vango.testownik.model.MainModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    ArrayList<MainModel> mainModels;
    Context context;
    private OnNoteListener mOnNoteListener;
    public MainAdapter(Context context, ArrayList<MainModel> mainModels, OnNoteListener mOnNoteListener){
        this.context=context;
        this.mainModels=mainModels;
        this.mOnNoteListener=mOnNoteListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row,viewGroup,false);
        return new ViewHolder(view,mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
    viewHolder.imageView.setBackgroundResource(mainModels.get(i).getLogo());
    String to_set = mainModels.get(i).getName();
    switch (to_set){
        case "Po": to_set="P obiektowe";
        break;
        case "Pps2": to_set = "Pps 2"; break;
        case "Pt": to_set = "Podstawy telekomunikacji"; break ;
    }
    viewHolder.textView.setText(to_set);
    }

    @Override
    public int getItemCount() {
        return mainModels.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textView;
        OnNoteListener onNoteListener;
        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener){
            super(itemView);
            this.onNoteListener=onNoteListener;
            imageView=itemView.findViewById(R.id.imageRecycler);
            textView=itemView.findViewById(R.id.txtRecycler);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
        onNoteListener.onNoteClick(getAdapterPosition());
        }
    }
    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}
