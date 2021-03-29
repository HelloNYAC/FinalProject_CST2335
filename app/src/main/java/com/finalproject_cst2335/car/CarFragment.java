package com.finalproject_cst2335.car;

import android.app.AlertDialog;
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

public class CarFragment extends Fragment {

    private View view, linear_View;
    //views and variables declarations
    private String id;
    private String Model_name, Make_name, Model_ID, Make_ID;
    private EditText postal;
    private TextView tv_make_id, tv_make_name, tv_model_id, tv_model_name, Add_to_Favorites;
    private CarsDB carsDB;
    private Button findcar;
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
        return view;
    }
}
