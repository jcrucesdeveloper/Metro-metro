package com.jorgecruces.metrometro.activities;


import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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

        String lineaName = metroMenu.get(position).getMetroName();
        holder.lineaMetroName.setText(lineaName);

        int color = Color.parseColor(metroMenu.get(position).getColor());
        holder.backgroundMetroMenu.setColorFilter(color);

        holder.cardView.setOnClickListener(view -> {
            Intent intent = new Intent(this.context.getApplicationContext(), PlayGameActivity.class);
            intent.putExtra("LINEA", lineaName);
            this.context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return metroMenu.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // On createMethod
        TextView lineaMetroName;
        ImageView backgroundMetroMenu;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            lineaMetroName = itemView.findViewById(R.id.lineaName);
            backgroundMetroMenu = itemView.findViewById(R.id.backgroundMetroMenu);
            cardView = itemView.findViewById(R.id.cardViewMenu);
        }
    }
}
