package com.finalproject_cst2335.car;

import android.app.AlertDialog;
import android.app.Fragment;
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
        findcar = view.findViewById(R.id.findcar);
        postal = view.findViewById(R.id.postal);
        linear_View = view.findViewById(R.id.linear_View);
        Add_to_Favorites = view.findViewById(R.id.Add_to_Favorites);
        tv_make_id = view.findViewById(R.id.tv_make_id);
        tv_make_name = view.findViewById(R.id.tv_make_name);
        tv_model_name = view.findViewById(R.id.tv_model_name);
        tv_model_id = view.findViewById(R.id.tv_model_id);
        findcar = view.findViewById(R.id.findcar);
        postal = view.findViewById(R.id.postal);
        linear_View = view.findViewById(R.id.linear_View);
        return view;
    }

    /**
     * to create the activity of detail fragment
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findcar = view.findViewById(R.id.findcar);
        postal = view.findViewById(R.id.postal);
        linear_View = view.findViewById(R.id.linear_View);
        Add_to_Favorites = view.findViewById(R.id.Add_to_Favorites);
        tv_make_id = view.findViewById(R.id.tv_make_id);
        tv_make_name = view.findViewById(R.id.tv_make_name);
        tv_model_name = view.findViewById(R.id.tv_model_name);
        tv_model_id = view.findViewById(R.id.tv_model_id);
        findcar = view.findViewById(R.id.findcar);
        postal = view.findViewById(R.id.postal);
        linear_View = view.findViewById(R.id.linear_View);
        //carsDB = new CarsDB(CarListsDetail.this);

    }
}
