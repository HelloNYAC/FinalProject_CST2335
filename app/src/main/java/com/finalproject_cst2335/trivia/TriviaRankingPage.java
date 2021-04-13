package com.finalproject_cst2335.trivia;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.finalproject_cst2335.HomePage;
import com.finalproject_cst2335.R;
import com.finalproject_cst2335.Song.SongMainActivity;
import com.finalproject_cst2335.car.CarMainActivity;
import com.finalproject_cst2335.soccergames.SoccerMainActivity;

import java.util.ArrayList;
import java.util.Collections;

public class TriviaRankingPage extends AppCompatActivity{

    private ArrayList<TriviaRankData> rankingList = new ArrayList<>();
    SQLiteDatabase db;

    /**
     * oncreate method to load data, if else to determine where the data bundle is coming from and set activity accordingly
     * load data from database to listview
     * insert game data from the game on submit
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tg_activity_ranking);

        ListView ranking_lv = findViewById(R.id.tg_ranking_lv);

        TriviaDatabase dbOpener = new TriviaDatabase(this);
        db = dbOpener.getWritableDatabase();

        Intent fromPlayRoom = getIntent();
        Bundle bundleFromAnywhere = fromPlayRoom.getExtras();
        if(bundleFromAnywhere.containsKey("fromPlayRoom")) {
            String player_name = bundleFromAnywhere.getString("Player_Name");
            String game_Lv = bundleFromAnywhere.getString("Game_Level");
            int game_Score = bundleFromAnywhere.getInt("Game_Score");

            ContentValues newRowValues = new ContentValues();
            newRowValues.put(TriviaDatabase.PLAYER_NAME, player_name);
            newRowValues.put(TriviaDatabase.GAME_LEVEL, game_Lv);
            newRowValues.put(TriviaDatabase.GAME_SCORE, game_Score);
            Log.e("====", "PLAYER_NAME: " + player_name);
            Log.e("====", "GAME_SCORE: " + game_Score);
            Log.e("====", "GAME_LEVEL: " + game_Lv);
            long newId = db.insert(TriviaDatabase.TABLE_NAME, null, newRowValues);
        }

        TriviaRankAdapter rankAdapter = new TriviaRankAdapter();
        ranking_lv.setAdapter(rankAdapter);
        loadDataFromDatabase();
        rankAdapter.notifyDataSetChanged();

        ranking_lv.setOnItemLongClickListener((parent, view, row, id) -> {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                    alertDialogBuilder.setTitle(getString(R.string.del_title))
                            .setMessage(getString(R.string.del_msg1) + row + "\n"
                                    + getString(R.string.del_msg2) + id)
                            .setPositiveButton(getString(R.string.yes), (click, arg) -> {
                                rankingList.remove(row);
                                rankAdapter.notifyDataSetChanged();
                                db.delete(TriviaDatabase.TABLE_NAME, TriviaDatabase.COL_ID+"=?", new String[] {Long.toString(rankAdapter.getItemId(row))});
                            })
                            .setNegativeButton(getString(R.string.no), (click, arg) -> {
                            })
                            .create().show();
                    return true;
        });

        Toolbar tg_myToolbar = (Toolbar)findViewById(R.id.tg_my_toolbar_rk);
        setSupportActionBar(tg_myToolbar);

    }

    /**
     * loadDataFromDatabase is to load data from database
     */
    private void loadDataFromDatabase(){
//        TriviaDatabase dbOpener = new TriviaDatabase(this);
//        db = dbOpener.getWritableDatabase();

        String[] columns = {TriviaDatabase.COL_ID, TriviaDatabase.PLAYER_NAME, TriviaDatabase.GAME_LEVEL,TriviaDatabase.GAME_SCORE};
        Cursor results = db.query(false, TriviaDatabase.TABLE_NAME, columns,null,null,null,null, null,"20");
//        results.moveToFirst();
        int nameColumnIndex = results.getColumnIndex(TriviaDatabase.PLAYER_NAME);
        int levelColumnIndex = results.getColumnIndex(TriviaDatabase.GAME_LEVEL);
        int scoreColumnIndex = results.getColumnIndex(TriviaDatabase.GAME_SCORE);

        while (results.moveToNext()) {

            String player_nm = results.getString(nameColumnIndex);
            String game_lv = results.getString(levelColumnIndex);
            int player_sc = results.getInt(scoreColumnIndex);

            rankingList.add(new TriviaRankData(player_nm, game_lv, player_sc));
        }
        Collections.sort(rankingList,(a, b)->{
            return b.getGameScore() - a.getGameScore();
        });
    }

    /**
     * Class TriviaAdapter is trying running at background.
     * It is getting all useful information from json url and stock in TrivialQuestion.
     *
     */
    public class TriviaRankAdapter extends BaseAdapter {

        /**
         * get the size of the questList
         * @return int size
         */
        @Override
        public int getCount() {
            return rankingList.size();
        }

        /**
         * getItem
         * @param position
         * @return
         */
        @Override
        public TriviaRankData getItem(int position) {
            return rankingList.get(position);
        }

        /**
         * get the item id in the questList
         * @param position
         * @return id
         */
        @Override
        public long getItemId(int position) {
            return rankingList.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TriviaRankData rankV = getItem(position);
            LayoutInflater inflater = getLayoutInflater();
            View rankView = inflater.inflate(R.layout.tg_activity_ranking_details, parent, false);
                TextView tg_rank_id = rankView.findViewById(R.id.tg_ranking_id);
                tg_rank_id.setText("" + (position+1));
                TextView rk_Name = rankView.findViewById(R.id.tg_ranking_name);
                rk_Name.setText(rankV.getPlayerName());
                TextView rk_Lv = rankView.findViewById(R.id.tg_ranking_lv);
                rk_Lv.setText(rankV.getGameLevel());
                TextView rk_Sc = rankView.findViewById(R.id.tg_ranking_score);
                rk_Sc.setText(Integer.toString(rankV.getGameScore()));
            return rankView;
        }
    }

    /**
     * inflater of the menu
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tg_main_activity_actions, menu);
        return true;
    }

    /**
     * opoption selected on toolbar
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String message = null;
        //Look at your menu XML file. Put a case for every id in that file:
        switch(item.getItemId())
        {
            case R.id.hp_homepage:
                message = getResources().getString(R.string.tg_backtomainpage);
                startActivity(new Intent(this, HomePage.class));
                break;
            case R.id.hp_song:
                message = getResources().getString(R.string.zz_gotosong);
                startActivity(new Intent(this, SongMainActivity.class));
                break;
            case R.id.hp_soccer:
                message = getResources().getString(R.string.zz_gotosoccer);
                startActivity(new Intent(this, SoccerMainActivity.class));
                break;
            case R.id.hp_car:
                message = getResources().getString(R.string.zz_gotocar);
                startActivity(new Intent(this, CarMainActivity.class));
                break;
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        return true;
    }

    }
