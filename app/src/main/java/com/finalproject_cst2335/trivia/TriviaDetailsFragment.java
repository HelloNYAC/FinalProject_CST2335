package com.finalproject_cst2335.trivia;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.finalproject_cst2335.R;

public class TriviaDetailsFragment extends Fragment{

    private Bundle tg_dataFromActivity;
    private long id;
    private AppCompatActivity parentActivity;

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        tg_dataFromActivity = getArguments();
        id = tg_dataFromActivity.getLong(TriviaPlayRoomActivity.QUESTION_ID);

        View result = inflater.inflate(R.layout.tg_fragment_details, container, false);

        //display question
        TextView total_Question_Num = result.findViewById(R.id.questionInfo);
        total_Question_Num.setText("Question:  "+ tg_dataFromActivity.getString(TriviaPlayRoomActivity.QUESTION_SELECTED));
        //display id
        TextView idView = result.findViewById(R.id.idHere);
        idView.setText(getResources().getString(R.string.tg_question_num)+"  "+ (id+1));

        TextView ttlQuestionNum = result.findViewById(R.id.ttlQuestionNum);
        ttlQuestionNum.setText(getResources().getString(R.string.tg_total_quest) +"  "+ tg_dataFromActivity.getString(TriviaPlayRoomActivity.TOTAL_QUESTION_NUMBER));

        TextView numQsnAnswered = result.findViewById(R.id.numQsnAnswered);
        numQsnAnswered.setText(getResources().getString(R.string.tg_total_answered)+"  "+tg_dataFromActivity.getString(TriviaPlayRoomActivity.QUESTION_ANSWERED_COUNT));

        TextView numUnAnswered = result.findViewById(R.id.numQsnUnanswered);
        numUnAnswered.setText(getResources().getString(R.string.tg_total_unanswered)+"  "+tg_dataFromActivity.getString(TriviaPlayRoomActivity.UNANSWERED_COUNT));

        Button backBtn = result.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(click -> {
            parentActivity.getSupportFragmentManager().beginTransaction().remove(this).commit();
        });
        return result;

    }

    @Override
    public void onAttach( Context context) {
        super.onAttach(context);
        parentActivity = (AppCompatActivity)context;
    }
}
