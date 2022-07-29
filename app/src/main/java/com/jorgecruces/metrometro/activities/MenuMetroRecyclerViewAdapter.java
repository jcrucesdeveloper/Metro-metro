package com.jorgecruces.metrometro.activities;


import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jorgecruces.metrometro.R;
import com.jorgecruces.metrometro.model.MetroMenu;

import java.util.ArrayList;

public class MenuMetroRecyclerViewAdapter extends RecyclerView.Adapter<MenuMetroRecyclerViewAdapter.MyViewHolder> {

    private final Context context;
    private final ArrayList<MetroMenu> metroMenu;

    public MenuMetroRecyclerViewAdapter(Context context, ArrayList<MetroMenu> metroMenu) {
        this.context = context;
        this.metroMenu = metroMenu;
    }

    @NonNull
    @Override
    public MenuMetroRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.menu_row, parent, false);
        return new MenuMetroRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuMetroRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.lineaMetroName.setText(metroMenu.get(position).getMetroName());
        int color = Color.parseColor(metroMenu.get(position).getColor());
        holder.backgroundMetroMenu.setColorFilter(color);
    }

    @Override
    public int getItemCount() {
        return metroMenu.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // On createMethod
        TextView lineaMetroName;
        ImageView backgroundMetroMenu;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            lineaMetroName = itemView.findViewById(R.id.lineaName);
            backgroundMetroMenu = itemView.findViewById(R.id.backgroundMetroMenu);
        }
    }
}
