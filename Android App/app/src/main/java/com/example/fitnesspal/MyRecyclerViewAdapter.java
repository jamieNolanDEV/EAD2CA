package com.example.fitnesspal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;
    private ArrayList<WorkoutDefine> workouts;
    private OnItemClickListener workoutListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        workoutListener = listener;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView myTextView;

        public ViewHolder(@NotNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.workoutname);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);

                        }
                    }
                }
            });


        }

    }
    MyRecyclerViewAdapter(Context context, ArrayList<WorkoutDefine> workouts) {
        this.mInflater = LayoutInflater.from(context);
        this.workouts = workouts;
    }


    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workoutitem, parent, false);
        //View view = mInflater.inflate(R.layout.activity_workouts, parent, false);
        ViewHolder vh = new ViewHolder(view, workoutListener);
        return vh;
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {
        holder.myTextView.setText(workouts.get(position).getWorkoutDate());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return workouts.size();
    }


    // stores and recycles views as they are scrolled off screen

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught

    // parent activity will implement this method to respond to click events
}
