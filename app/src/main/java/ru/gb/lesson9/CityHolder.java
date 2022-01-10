package ru.gb.lesson9;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

public class CityHolder extends RecyclerView.ViewHolder implements PopupMenu.OnMenuItemClickListener {
    private City city;

    private TextView cityName;
    private TextView cityPopulation;
    private ImageView cityMenu;

    private PopupMenu popupMenu;

    private PopupMenuItemClickListener listener;

    public CityHolder(@NonNull View itemView, PopupMenuItemClickListener listener) {
        super(itemView);
        this.listener = listener;
        cityName = itemView.findViewById(R.id.city_name);
        cityPopulation = itemView.findViewById(R.id.city_population);
        cityMenu = itemView.findViewById(R.id.city_menu);

        popupMenu = new PopupMenu(itemView.getContext(), cityMenu);
        popupMenu.inflate(R.menu.context);

        cityMenu.setOnClickListener(v -> popupMenu.show());
        popupMenu.setOnMenuItemClickListener(this);
    }

    void bind(City city)
    {
        this.city  = city;
        cityName.setText(city.getName());
        cityPopulation.setText("" + city.getPopulation());
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.context_delete:
                listener.click(R.id.context_delete, city, getAdapterPosition());
                return true;

            case R.id.context_modify:
                listener.click(R.id.context_modify, city, getAdapterPosition());
                return true;
            default:
                return false;
        }
    }

    public City getCity() {
        return city;
    }
}
