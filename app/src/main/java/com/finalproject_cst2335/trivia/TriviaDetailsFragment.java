package com.finalproject_cst2335.trivia;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.finalproject_cst2335.R;

public class TriviaDetailsFragment extends Fragment{

        private Bundle dataFromActivity;
        private int id;
        private AppCompatActivity parentActivity;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            dataFromActivity = getArguments();
            id = dataFromActivity.getInt(TriviaGameRoomActivity.ITEM_ID);
            // Inflate the layout for this fragment
            View result = inflater.inflate(R.layout.tg_fragment_details, container, false);

            TextView message = result.findViewById(R.id.frag_msg);
            message.setText(dataFromActivity.getString(TriviaGameRoomActivity.ITEM_SELECTED));

            TextView idView = result.findViewById(R.id.frag_id);
            idView.setText("ID = " + id);

            CheckBox isSent = result.findViewById(R.id.frag_ckbx);
            isSent.setChecked(dataFromActivity.getBoolean(TriviaGameRoomActivity.ITEM_POSITION));

            Button hideButton = result.findViewById(R.id.frag_hdbtn);
            hideButton.setOnClickListener( click -> {
                //Tell the parent activity to remove
                parentActivity.getSupportFragmentManager().beginTransaction().remove(this).commit();
            });

            return result;
        }

        public void onAttach(Context context) {
            super.onAttach(context);
            //context will either be FragmentExample for a tablet, or EmptyActivity for phone
            parentActivity = (AppCompatActivity)context;
        }


}
