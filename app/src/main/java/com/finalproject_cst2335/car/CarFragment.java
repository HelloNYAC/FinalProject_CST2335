package com.finalproject_cst2335.car;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.finalproject_cst2335.R;
import com.google.android.material.snackbar.Snackbar;

public class CarFragment extends Fragment {

    private View view, linear_View;
    //views and variables declarations
    private String Model_name, Make_name, Model_ID, Make_ID;
    private EditText postal;
    private TextView tv_make_id, tv_make_name, tv_model_id, tv_model_name;
    private CarsDB carsDB;
    private Button btn_shopcar, btn_view_detail, btn_add_to_favorites;
    private int ide;
    private Toolbar tb;

    public CarFragment() {
    }

    /**
     * to create fragment view of nutrition details
     *
     * @param inflater           Inflater
     * @param container          ViewGroup
     * @param savedInstanceState Bundle
     * @return View
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_car_lists_detail_fragment, container, false);

        //Views Initializer
        btn_add_to_favorites = view.findViewById(R.id.Add_to_Favorites);
        tv_make_id = view.findViewById(R.id.tv_make_id);
        tv_make_name = view.findViewById(R.id.tv_make_name);
        tv_model_name = view.findViewById(R.id.tv_model_name);
        tv_model_id = view.findViewById(R.id.tv_model_id);
        btn_shopcar = view.findViewById(R.id.shopcar);
        //postal = view.findViewById(R.id.postal);
        linear_View = view.findViewById(R.id.linear_View);
        carsDB = new CarsDB(getContext());
        btn_view_detail = view.findViewById(R.id.view_car_detail);

        try {
            Make_name = this.getArguments().getString("makeName");
            Make_ID = this.getArguments().getString("makeID");
            Model_ID = this.getArguments().getString("id");
            Model_name = this.getArguments().getString("name");

            tv_model_id.setText(Model_ID);
            tv_model_name.setText(Model_name);
            tv_make_id.setText(Make_ID);
            tv_make_name.setText(Make_name);
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        btn_view_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://www.google.com/search?q=" + Make_name + "+" + Model_name;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        btn_shopcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                if (postal.getText().toString().isEmpty() || postal.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please Enter postal code!", Toast.LENGTH_SHORT).show();
                    return;
                }
                */
                String url = "https://www.autotrader.ca/cars/?mdl=" + Model_name + "&make=" + Make_name + "&loc=K2G1V8";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        //This Action will send data to the database
        btn_add_to_favorites.setOnClickListener(v -> {
            Snackbar.make(linear_View, R.string.adder, Snackbar.LENGTH_SHORT).show();
            carsDB.insertCar(new Cars(Integer.parseInt(Make_ID), Make_name, Integer.parseInt(Model_ID), Model_name));
        });


        return view;
    }
}
