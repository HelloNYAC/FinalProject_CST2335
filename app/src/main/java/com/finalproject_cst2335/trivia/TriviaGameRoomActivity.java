package com.finalproject_cst2335.trivia;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.finalproject_cst2335.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class TriviaGameRoomActivity extends AppCompatActivity {
    private ArrayList<TriviaQuestion> questList = new ArrayList<>();
    private TriviaAdapter myAdapter;
    SQLiteDatabase db;
    private ListView myList;
    public static final String ITEM_SELECTED = "ITEM";
    public static final String ITEM_POSITION = "POSITION";
    public static final String ITEM_ID = "ID";
    public static final String ANSWER_LIST = "ANSWERS";
    private String ques_number, ques_type, ques_level;
    private String TriviaURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tg_activity_game_room_layout);

        EditText qust_num = findViewById(R.id.q_n_text);
        EditText qust_typ = findViewById(R.id.q_t_text);
        EditText qust_lv = findViewById(R.id.q_l_text);

        Intent fromGamePick = getIntent();
        ques_number = fromGamePick.getStringExtra("question_num");
        ques_type = fromGamePick.getStringExtra("question_type");
        ques_level = fromGamePick.getStringExtra("question_level");

        qust_num.setText(ques_number);
        qust_typ.setText(ques_type);
        qust_lv.setText(ques_level);

        myList = (ListView) findViewById(R.id.tg_thelistview);

        myAdapter = new TriviaAdapter();
        myList.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

        if (ques_type.equals("True and False")) {
            TriviaURL = "https://opentdb.com/api.php?amount=" + ques_number + "&difficulty=" + ques_level.toLowerCase() + "&type=boolean";
        }
        if (ques_type.equals("Multiple Choice")) {
            TriviaURL = "https://opentdb.com/api.php?amount=" + ques_number + "&difficulty=" + ques_level.toLowerCase() + "&type=multiple";
        }
        if (ques_type.equals("Mixed")) {
            TriviaURL = "https://opentdb.com/api.php?amount=" + ques_number + "&difficulty=" + ques_level.toLowerCase();
        }
        TriviaGameQuery req = new TriviaGameQuery();
        req.execute(TriviaURL);

        boolean isTablet = findViewById(R.id.tg_fragmentLocation) != null;
        myList.setOnItemClickListener((parent, view, row, id) -> {
            Bundle dataToPass = new Bundle();
            dataToPass.putString(ITEM_SELECTED, questList.get(row).getQuestion());
            dataToPass.putLong(ITEM_ID, id);
            dataToPass.putString(ITEM_POSITION, questList.get(row).getType());
            dataToPass.putStringArrayList(ANSWER_LIST, questList.get(row).getAnswer());

            if (isTablet) {
                //add a DetailFragment
                TriviaDetailsFragment dFragment = new TriviaDetailsFragment();
                dFragment.setArguments(dataToPass); //pass it a bundle for information
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.tg_fragmentLocation, dFragment, Long.toString(id)) //Add the fragment in FrameLayout
                        .addToBackStack(null)
                        .commit(); //actually load the fragment. Calls onCreate() in DetailFragment
            } else //isPhone
            {
                Intent nextActivity = new Intent(TriviaGameRoomActivity.this, TriviaEmptyActivity.class);
                nextActivity.putExtras(dataToPass); //send data to next activity
                startActivity(nextActivity); //make the transition
            }
        });

        myList.setOnItemLongClickListener((parent, view, row, id) -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(getString(R.string.del_title))
                    .setMessage(getString(R.string.del_msg1) + row + "\n"
                            + getString(R.string.del_msg2) + id)
                    .setPositiveButton(getString(R.string.yes), (click, arg) -> {
                        questList.remove(row);
                        if (isTablet) {
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .remove(getSupportFragmentManager().findFragmentByTag(Long.toString(id)))
                                    .commit();
                        }
                        myAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton(getString(R.string.no), (click, arg) -> {
                    })
                    .create().show();
            return true;
        });

//        RadioGroup radioGroup = findViewById(R.id.tg_qust_radioGroup);
//        radioGroup.setOnClickListener(click->{
//               // get selected radio button from radioGroup
//                int selectedId = radioGroup.getCheckedRadioButtonId();
//
//                // find the radiobutton by returned id
//                RadioButton radioButton = (RadioButton) findViewById(selectedId);
//                Toast.makeText(this,
//                        radioButton.getText(),
//                        Toast.LENGTH_LONG).show();
//            });
    }

    public class TriviaGameQuery extends AsyncTask<String, Integer, String> {

        ProgressBar loadProgress = findViewById(R.id.tg_progressBar);

        @Override
        protected String doInBackground(String... args) {
            publishProgress(25);

            try {
                URL tg_url = new URL(args[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) tg_url.openConnection();
                InputStream response = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String result = sb.toString(); //

                publishProgress(50);

                JSONObject gameDB = new JSONObject(result);
                JSONArray jArray = gameDB.getJSONArray("results");
                ArrayList incorrctArray =new ArrayList();

                for (int i=0; i < jArray.length(); i++) {

                    try {
                        JSONObject anObject = (JSONObject) jArray.get(i);
                        String qus = anObject.getString("question");
                        String corr = anObject.getString("correct_answer");
                        String typ = anObject.getString("type");
                        JSONArray incorr = anObject.getJSONArray("incorrect_answers");
                        for (int j = 0; j < incorr.length(); j++) {
                            incorrctArray.add(incorr.getString(j));
                        }
                        TriviaQuestion tg_qust = new TriviaQuestion((i+1), qus, typ, corr, incorrctArray);
                        questList.add(tg_qust);
                        myAdapter.notifyDataSetChanged();

                        publishProgress(75);
                    } catch (JSONException e) {
                        Log.i("JSONException", "JSONException!! ");
                    }
                }
                for (int i = 0; i < questList.size(); i++) {
                    System.out.println(questList.get(i).getId());
                }
            } catch (Exception e) {
                Log.i("Exception", "Exception!! " );
            }
            publishProgress(100);
            return "Done";
        }

        public void onProgressUpdate(Integer ... args)
        {
            super.onProgressUpdate(args);
            //Update GUI stuff only:
            try {
                loadProgress.setVisibility(View.VISIBLE);
                loadProgress.setProgress(args[0]);
                Thread.sleep(1000);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        //Type3
        public void onPostExecute(String fromDoInBackground)
        {
            super.onPostExecute(fromDoInBackground);
            Log.i("HTTP", fromDoInBackground);
            loadProgress.setVisibility(View.VISIBLE);
            System.out.println(questList.size());
        }
    }


    public class TriviaAdapter extends BaseAdapter {
        
        @Override
        public int getCount() {
            return questList.size();
        }

        @Override
        public TriviaQuestion getItem(int position) {
            return questList.get(position);
        }

        @Override
        public long getItemId ( int position){
            return questList.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TriviaQuestion qust = getItem(position);
            LayoutInflater inflater = getLayoutInflater();
            View qustView;

            if(qust.getType().equals("boolean")){
                qustView = inflater.inflate(R.layout.tg_acitivity_true_and_false, parent, false);
                TextView tg_id = qustView.findViewById(R.id.qustls_id);
                tg_id.setText(""+qust.getId());
                TextView question = qustView.findViewById(R.id.qustls_qust);
                question.setText(qust.getQuestion());
                TextView answer1 = qustView.findViewById(R.id.qustls_ans1);
                answer1.setText(qust.getCorrAns());
                TextView answer2 = qustView.findViewById(R.id.qustls_ans2);
                answer2.setText(qust.getIncorrArray().get(0));
            }
            else{
                qustView = inflater.inflate(R.layout.tg_activity_multiple, parent, false);
                TextView tg_id = qustView.findViewById(R.id.qustls_id);
                tg_id.setText(""+qust.getId());
                TextView question = qustView.findViewById(R.id.qustls_qust);
                question.setText(qust.getQuestion());
                TextView answer1 = qustView.findViewById(R.id.qustls_ans1);
                answer1.setText(qust.getCorrAns());
                TextView answer2 = qustView.findViewById(R.id.qustls_ans2);
                answer2.setText(qust.getIncorrArray().get(0));
                TextView answer3 = qustView.findViewById(R.id.qustls_ans3);
                answer3.setText(qust.getIncorrArray().get(1));
                TextView answer4 = qustView.findViewById(R.id.qustls_ans4);
                answer4.setText(qust.getIncorrArray().get(2));
            }
            return qustView;

            }
        }

    }
