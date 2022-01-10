package ru.gb.lesson9;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputLayout;

public class CityDialog extends DialogFragment {

    public static final String CITY = "CITY";

    interface CityDialogController {
        void update(City city);
        void create(String name, int population);
    }

    private CityDialogController controller;

    @Override
    public void onAttach(@NonNull Context context) {
        controller = (CityDialogController) context;
        super.onAttach(context);
    }

    private City city;


    public static CityDialog getInstance(City city)
    {
        CityDialog dialog = new CityDialog();
        Bundle args = new Bundle();
        args.putSerializable(CITY, city);
        dialog.setArguments(args);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        city = (City) args.getSerializable(CITY);

        String name = "";
        int population = 0;
        if(city != null)
        {
            name = city.getName();
            population = city.getPopulation();
        }

        View dialog = LayoutInflater.from(requireContext()).inflate(R.layout.city_dialog, null);

        TextInputLayout dialogName = dialog.findViewById(R.id.dialog_name);
        TextInputLayout dialogPopulation = dialog.findViewById(R.id.dialog_population);
        dialogName.getEditText().setText(name);
        dialogPopulation.getEditText().setText("" + population);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        String buttonText = "";
        if(city == null)
        {
            buttonText = "Create";
            builder.setTitle("Create a city");
        }
        else {
            buttonText = "Modify";
            builder.setTitle("Modify a city");
        }
        builder
                .setView(dialog)
                .setCancelable(true)
                .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel())
                .setPositiveButton(buttonText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(city == null)
                        {
                            controller.create(
                                    dialogName.getEditText().getText().toString(),
                                    Integer.parseInt(
                                            dialogPopulation.getEditText().getText().toString()
                                    )
                            );
                        }
                        else {
                            city.setName(dialogName.getEditText().getText().toString());
                            city.setPopulation(Integer.parseInt(
                                    dialogPopulation.getEditText().getText().toString()
                            ));
                            controller.update(city);
                        }
                        dialogInterface.dismiss();
                    }
                })
        ;




        return builder.create();
    }
}
