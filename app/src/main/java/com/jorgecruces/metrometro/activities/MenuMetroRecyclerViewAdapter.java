package com.jorgecruces.metrometro.activities;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.jorgecruces.metrometro.R;
import com.jorgecruces.metrometro.model.MetroMenu;

import java.util.ArrayList;

public class MenuMetroRecyclerViewAdapter extends RecyclerView.Adapter<MenuMetroRecyclerViewAdapter.MyViewHolder> {

    private final Context context;
    private final ArrayList<MetroMenu> metroMenuList;

    public MenuMetroRecyclerViewAdapter(Context context, ArrayList<MetroMenu> metroMenuList) {
        this.context = context;
        this.metroMenuList = metroMenuList;
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

        MetroMenu currentMetroMenu = metroMenuList.get(position);

        String lineName = currentMetroMenu.getMetroName();
        int color = Color.parseColor(currentMetroMenu.getColor());

        holder.lineaMetroName.setText(lineName);
        holder.backgroundMetroMenu.setColorFilter(color);
        holder.cardView.setOnClickListener(view -> {
            Intent intent = new Intent(this.context.getApplicationContext(), PlayGameActivity.class);
            intent.putExtra("LINEA", lineName);
            this.context.startActivity(intent);
        });

        // Star Level
        Log.d("DEBUG", lineName);
//        SharedPreferences sharedPref = context.getApplicationContext().etActgetPreferences(Context.MODE_PRIVATE);
//        boolean starLevel = sharedPref.getBoolean(lineaName,false);
        if (currentMetroMenu.getLevelStar()) {
            holder.starView.setImageResource(R.drawable.ic_mediamodifier_design_2_);
        }

    }

    @Override
    public int getItemCount() {
        return metroMenuList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // On createMethod
        TextView lineaMetroName;
        ImageView backgroundMetroMenu, starView;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            lineaMetroName = itemView.findViewById(R.id.lineaName);
            backgroundMetroMenu = itemView.findViewById(R.id.backgroundMetroMenu);
            cardView = itemView.findViewById(R.id.cardViewMenu);
            starView = itemView.findViewById(R.id.starView);
        }
    }
}
