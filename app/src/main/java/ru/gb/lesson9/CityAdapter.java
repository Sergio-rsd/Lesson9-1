package ru.gb.lesson9;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityHolder> {

    private PopupMenuItemClickListener listener;

    public void setOnPopupMenuItemClickListener(PopupMenuItemClickListener listener)
    {
        this.listener = listener;
    }


    private List<City> cityList = new ArrayList<>();

    public void delete(List<City> all, int position) {
        this.cityList = all;
        notifyItemRemoved(position);
    }

    public void setCities(List<City> cityList)
    {
        this.cityList = cityList;
        notifyDataSetChanged();
        // notifyItemInserted(position);
        // notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public CityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_item, parent, false);
        return new CityHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CityHolder holder, int position) {
        holder.bind(cityList.get(position));
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }


}
