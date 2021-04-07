package com.finalproject_cst2335.trivia;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.finalproject_cst2335.R;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Class QuestionList is used to load questions
 *
 *
 */
public class TriviaPlayRoomActivity extends AppCompatActivity {

    ArrayList<TriviaQuestion> triviaQustlist =new ArrayList<>();
    MylistAdapter triviaAdapter;
    ListView triviaList;
    ProgressBar tg_progressBar;
    FrameLayout tg_frameLayout;
    public static final String QUESTION_SELECTED = "ITEM";
    public static final String QUESTION_ID = "ID";
    public static final String QUESTION_TYPE = "TYPE";
    public static final String TOTAL_QUESTION_NUMBER = "QUESTION_NUMBER";
    public static final String QUESTION_ANSWERED_COUNT = "ANSWERED_COUNT";
    public static final String UNANSWERED_COUNT ="UNANSWERED_COUNT";
    public static final String GAME_SCORE ="SCORE";
    public static final String QUESTION_LEVEL ="LEVEL";
    public static final String PLAYER_NAME ="PLAYER_NAME";
    private TextView tg_QuestionCount;
    private TextView tg_corrCount;
    private TextView tg_incorrCount;
    private TextView tg_unansweredCount;
    private TextView tg_ScoreCount;
    public int totalQuesNum;
    public int correctAnswered;
    public int incorrectAnswered;
    public int unAnswered;
    public int tg_score;
    private String quest_Level;


    /**
     * onCreate method is used to when loading question layout
     * checking device is tablet or phone
     *Hello
     * @param savedInstanceState save all usable button
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tg_activity_game_room_layout);

        triviaList = findViewById(R.id.tg_theListView);

        tg_frameLayout = findViewById(R.id.tg_fragmentLocation);
        boolean isTablet = tg_frameLayout != null;

        Intent fromTrivia= getIntent();
        String triviaURL = fromTrivia.getStringExtra("TriviaURL");
        Log.e("====", "triviaURL:--- "+triviaURL);
        tg_progressBar = findViewById(R.id.tg_progressBar);
        tg_progressBar.setVisibility(View.VISIBLE);

        quest_Level = fromTrivia.getStringExtra("question_level");
        Log.e("====", "quest_Level: " + quest_Level);

        TextView player_name = findViewById(R.id.tg_grt_top);
        String pl_name = fromTrivia.getStringExtra("player_name");
        player_name.setText("Welcome to your game, " + pl_name);

        QuestionQuery req = new QuestionQuery();
        req.execute(triviaURL);

        triviaAdapter = new MylistAdapter();
        triviaList.setAdapter(triviaAdapter);


        Button backBtn = findViewById(R.id.tg_leaveBtn);
        backBtn.setOnClickListener(v->{
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Do you want to return to main menu without saving data?")
                    .setPositiveButton("Yes", (click, arg)->{
                        Intent goBackToTrivia = new Intent(this, TriviaGamePickActivity.class);
                        startActivity(goBackToTrivia);
                    })
                    .setNegativeButton("No", (click, arg)->{ })
                    .setView(getLayoutInflater().inflate(R.layout.tg_acitivity_empty, null))
                    .create().show();
        });

        TextView snackMsg = findViewById(R.id.tg_textSpace);
        triviaList.setOnItemLongClickListener((list, item, position, id)->{
            Snackbar.make(snackMsg, "Checking details of the question", Snackbar.LENGTH_SHORT).show();
            return true;
        });

        triviaList.setOnItemClickListener((list, item, position, id)->{
            Bundle dataToPass = new Bundle();
            dataToPass.putString(QUESTION_SELECTED, triviaQustlist.get(position).getQuestion());
            dataToPass.putString(QUESTION_TYPE, triviaQustlist.get(position).getQuestType());
            dataToPass.putString(QUESTION_LEVEL, triviaQustlist.get(position).getQuestLevel());
            dataToPass.putLong(QUESTION_ID, triviaQustlist.get(position).getId());
            dataToPass.putString(TOTAL_QUESTION_NUMBER, String.valueOf(triviaQustlist.size()));
            dataToPass.putString(QUESTION_ANSWERED_COUNT, (totalQuesNum-unAnswered)+"");
            dataToPass.putString(UNANSWERED_COUNT, unAnswered+"");

            if(isTablet){
                TriviaDetailsFragment triviaDetailsFragment = new TriviaDetailsFragment();
                triviaDetailsFragment.setArguments(dataToPass);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.tg_fragmentLocation, triviaDetailsFragment)
                        .commit();
            } else {
                Intent nextActivity = new Intent(this, TriviaEmptyActivity.class);
                nextActivity.putExtras(dataToPass);
                startActivity(nextActivity);
            }
            updateCount();
            triviaAdapter.notifyDataSetChanged();
        });

        Button nextPage = findViewById(R.id.tg_nextBtn);
        nextPage.setOnClickListener(v->{
            updateCount();
            triviaAdapter.notifyDataSetChanged();
            Bundle bundleToRank = new Bundle();
            bundleToRank.putString("Player_Name", pl_name);
            bundleToRank.putString("Game_Level", quest_Level);
            bundleToRank.putInt("Game_Score", tg_score);
            bundleToRank.putString("fromPlayRoom", "fromPlayRoom");

            Intent GoToRank = new Intent();
            GoToRank.putExtras(bundleToRank);
            GoToRank.setClass(TriviaPlayRoomActivity.this, TriviaRankingPage.class);
            startActivity(GoToRank);
        });
    }

    /**
     * update count is to update the question count of incorrect count, correct count, unanswered etc
     * start from the total of questions, and update while user clicking on different answers
     */
    public void updateCount() {
        int tg_size = triviaQustlist.size();
        tg_QuestionCount.setText(tg_size + "");
        correctAnswered = 0;
        incorrectAnswered = 0;
        unAnswered = tg_size;
        tg_score = 0;

        for (int i = 0; i < tg_size; i++) {
            if (triviaQustlist.get(i).getUserSelectAns() != null) {
                unAnswered--;

                if (triviaQustlist.get(i).getCorrAns().equals(triviaQustlist.get(i).getUserSelectAns())) {
                    correctAnswered++;
                    Log.e("TAG===", "correctAnswered===: "+correctAnswered );
                } else {
                    incorrectAnswered++;
                }
            }
        }
//        double temp = correctAnswered + incorrectAnswered + unAnswered;
        tg_score = (int)((correctAnswered / ((correctAnswered + incorrectAnswered + unAnswered)* 1.0))*100);
//        Log.e("TAG===", "correctAnswered===: "+correctAnswered );
//        Log.e("TAG===", "unAnswered===: "+unAnswered );
//        Log.e("TAG===", "incorrectAnswered===: "+incorrectAnswered );
//        Log.e("TAG===", "tg_score===: "+ tg_score );
    }

    /**
     * Class QuestionQuery is trying running at background.
     * It is getting all useful information from json url and stock in TrivialMsg msg.
     *
     */
    private class QuestionQuery extends AsyncTask<String, Integer, String> {

        ProgressBar tg_progressBar = findViewById(R.id.tg_progressBar);

        @Override
        protected String doInBackground(String... args) {
            publishProgress(25);
            try {
                URL url = new URL(args[0]);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream response = urlConnection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                //read json file
                String line = null;

                while ((line = reader.readLine()) != null){

                    sb.append(line +"\n");
                }

                //stock all useful information from json file to questionList
                String result = sb.toString();
                JSONObject gameDB = new JSONObject(result);

                JSONArray jArray = gameDB.getJSONArray("results");

                publishProgress(50);

                for(int i=0; i<jArray.length(); i++){
                    JSONObject questionObject=jArray.getJSONObject(i);
                    TriviaQuestion tg_question = new TriviaQuestion();
                    tg_question.setID(i);
                    tg_question.setQuestion(questionObject.getString("question"));
//                    Log.e("TAG====", "question: " + questionObject.getString("question"));
                    tg_question.setQuestType(questionObject.getString("type"));
//                    Log.e("TAG====", "question Type: " + questionObject.getString("type"));
                    tg_question.setQuestLevel(questionObject.getString("difficulty"));
//                    Log.e("TAG====", "question level: " + questionObject.getString("difficulty"));

                    tg_question.setCorrAns(questionObject.getString("correct_answer"));
//                    Log.e("TAG====", "Correct Answer: " + questionObject.getString("correct_answer"));
                    JSONArray icorr = questionObject.getJSONArray("incorrect_answers");
                    List<String> answerList = new ArrayList<>();
                    for(int j=0; j< icorr.length();j++){
                        answerList.add(icorr.getString(j));
                    }
                    answerList.add(tg_question.getCorrAns());
                    Collections.shuffle(answerList); //random answer list
                    tg_question.setAnswerSetArray(answerList);
                    triviaQustlist.add(tg_question);

                    publishProgress(75);
                }
                // Test if get correct results or not
                for(int i = 0; i < triviaQustlist.size(); i++) {

                    System.out.println(i+1 + ": " + triviaQustlist.get(i).getQuestType());
                    System.out.println(i+1 + ": " + triviaQustlist.get(i).getQuestion());
                }



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            publishProgress(100);
            return "Done";

        }

        /**
         * onProgressUpdate is an override method when loading the data, information requires for questionlist
         * such as if  checking loading process, progressBar attribute need to been set as visable.
         * @param args store arguments
         */

        @Override
        protected void onProgressUpdate(Integer ... args)
        {
            super.onProgressUpdate(args);
            try {
            tg_progressBar.setVisibility(View.VISIBLE);
            tg_progressBar.setProgress(args[0]);
            Thread.sleep(1000);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * onPostExecute is an override method which after the program after execute.
         * ProgressBar attribute set as invisible
         * loading all questions.
         * @param fromDoingBackground
         */
        @Override
        protected void onPostExecute(String fromDoingBackground){
            super.onPostExecute(fromDoingBackground);
            tg_progressBar.setVisibility(View.INVISIBLE);
            triviaAdapter.notifyDataSetChanged();
        }
    }

    /**
     * class MylistAdapter help to build message that it can display the information in listview
     *
     */
    class MylistAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return triviaQustlist.size();
        }

        @Override
        public TriviaQuestion getItem(int position) {
            return triviaQustlist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return triviaQustlist.get(position).getId();
        }


        @Override
        public View getView(int position, View oldView, ViewGroup parent) {
            View qustView;
            LayoutInflater inflater = getLayoutInflater();
            TriviaQuestion tg_qust = getItem(position);

            tg_QuestionCount = findViewById(R.id.tg_ques_num_cnt);
            tg_unansweredCount =findViewById(R.id.tg_unanswered_cnt);
            tg_incorrCount = findViewById(R.id.tg_incorr_cnt);
            tg_corrCount = findViewById(R.id.tg_corr_cnt);
            tg_ScoreCount = findViewById(R.id.tg_score_cnt);
            updateCount();
            tg_unansweredCount.setText(unAnswered + "");
            tg_corrCount.setText(correctAnswered + "");
            tg_incorrCount.setText(incorrectAnswered + "");
            tg_ScoreCount.setText(tg_score + "");

            if (tg_qust.getQuestType().equals("boolean")) {
                qustView = inflater.inflate(R.layout.tg_acitivity_true_and_false, parent,false);
                RadioGroup radioGroupTnF =qustView.findViewById(R.id.tg_qust_radioGroup_tf);
                switch (triviaQustlist.get(position).getAnswerSetArray().indexOf(triviaQustlist.get(position).getUserSelectAns())){
                    case 0:radioGroupTnF.check(R.id.qustls_ans1);break;
                    case 1:radioGroupTnF.check(R.id.qustls_ans2);break;
                    default:;
                }
                radioGroupTnF.setOnCheckedChangeListener((group, checkedId) -> {
                    RadioButton userSelected = qustView.findViewById(checkedId);
                    String userAnswer = userSelected.getText().toString();
                    triviaQustlist.get(position).setUserSelectAns(userAnswer);
                    Log.e("======", "getView: "+ userAnswer);
                    Log.e("/////", "answer is "+ triviaQustlist.get(position).getCorrAns());
                    updateCount();
                    triviaAdapter.notifyDataSetChanged();
                });
//                TextView qustID = qustView.findViewById(R.id.qustls_id);
//                qustID.setText(position+1);
                TextView trueFalseView = qustView.findViewById(R.id.qustls_qust);
                trueFalseView.setText((position+1)+"  "+tg_qust.getQuestion());
                Log.i("question=====", tg_qust.getQuestion());
                Button trueBtn = qustView.findViewById(R.id.qustls_ans1);
                trueBtn.setText(tg_qust.getAnswerSetArray().get(0));
                Button falseBtn = qustView.findViewById(R.id.qustls_ans2);
                falseBtn.setText(tg_qust.getAnswerSetArray().get(1));

            } else {
                qustView = inflater.inflate(R.layout.tg_activity_multiple, parent,false);
                RadioGroup radioGroupMultiple =qustView.findViewById(R.id.tg_answer_group_mul);
                switch (triviaQustlist.get(position).getAnswerSetArray().indexOf(triviaQustlist.get(position).getUserSelectAns())){
                    case 0:radioGroupMultiple.check(R.id.qustls_ans1);break;
                    case 1:radioGroupMultiple.check(R.id.qustls_ans2);break;
                    case 2:radioGroupMultiple.check(R.id.qustls_ans3);break;
                    case 3:radioGroupMultiple.check(R.id.qustls_ans4);break;
                    default:;
                }
                radioGroupMultiple.setOnCheckedChangeListener((group,  checkedId) -> {
                    RadioButton userSelected = (RadioButton) qustView.findViewById(checkedId);
                    String userAnswer = userSelected.getText().toString();
//                    listItems.get(position).setAnswered(true);
                    triviaQustlist.get(position).setUserSelectAns(userAnswer);
                    Log.e("======", "getView: "+ userAnswer);
                    Log.e("/////", "answer is "+ triviaQustlist.get(position).getCorrAns());
                    updateCount();
                    triviaAdapter.notifyDataSetChanged();
                });
                TextView multiView = qustView.findViewById(R.id.qustls_qust);
                multiView.setText((position+1)+"  "+tg_qust.getQuestion());
                Button tg_answer1 = qustView.findViewById(R.id.qustls_ans1);
                tg_answer1.setText(tg_qust.getAnswerSetArray().get(0));
                Button tg_answer2 = qustView.findViewById(R.id.qustls_ans2);
                tg_answer2.setText(tg_qust.getAnswerSetArray().get(1));
                Button tg_answer3 = qustView.findViewById(R.id.qustls_ans3);
                tg_answer3.setText(tg_qust.getAnswerSetArray().get(2));
                Button tg_answer4 = qustView.findViewById(R.id.qustls_ans4);
                tg_answer4.setText(tg_qust.getAnswerSetArray().get(3));
            }
            triviaAdapter.notifyDataSetChanged();
            return qustView;
        }
    }
}