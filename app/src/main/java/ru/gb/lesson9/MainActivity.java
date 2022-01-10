package ru.gb.lesson9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements PopupMenuItemClickListener,
        CityDialog.CityDialogController {

    // private TextView textHello;
    private RecyclerView recyclerView;
    private CityAdapter adapter = new CityAdapter();
    private Repo repo = InMemoryCityRepository.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter.setOnPopupMenuItemClickListener(this);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        adapter.setCities(repo.getAll());


        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                // ItemTouchHelper.UP

                return makeMovementFlags(0, swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                CityHolder holder = (CityHolder) viewHolder;
                City city = holder.getCity();
                repo.delete(city);
                adapter.delete(repo.getAll(), position);
            }
        });
        helper.attachToRecyclerView(recyclerView);




//        textHello = findViewById(R.id.text_hello);
//
//        registerForContextMenu(textHello);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.main_create:
                CityDialog.getInstance(null)
                        .show(
                                getSupportFragmentManager(),
                                CityDialog.CITY
                        );
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void click(int command, City city, int position) {
        switch (command)
        {
            case R.id.context_delete:
                repo.delete(city);
                adapter.delete(repo.getAll(), position);
                return;

            case R.id.context_modify:
                CityDialog.getInstance(city)
                        .show(
                                getSupportFragmentManager(),
                                CityDialog.CITY
                        );
                return;
        }
    }

    @Override
    public void update(City city) {
        repo.update(city);
        adapter.setCities(repo.getAll());
    }

    @Override
    public void create(String name, int population) {
        repo.create(name, population);
        adapter.setCities(repo.getAll());
    }
/*
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.context_delete:
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.context_modify:
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

 */
}


