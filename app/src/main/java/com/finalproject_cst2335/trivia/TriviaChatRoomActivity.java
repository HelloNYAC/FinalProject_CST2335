package com.finalproject_cst2335.trivia;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.finalproject_cst2335.R;

import java.util.ArrayList;
import java.util.Arrays;

public class TriviaChatRoomActivity extends AppCompatActivity {
    private ArrayList<TriviaMessage> messageList = new ArrayList<>();
    private TriviaAdapter myAdapter;
    SQLiteDatabase db;
    private ListView myList;
    private Button send;
    private Button receive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tg_activity_game_page);

        EditText typed = findViewById(R.id.typeline);
        String textMessage = typed.getText().toString();

        send = findViewById(R.id.sendbtn);
        receive = findViewById(R.id.rcvbtn);
        myList = (ListView) findViewById(R.id.thelistview);

        myAdapter = new TriviaAdapter();
        myList.setAdapter(myAdapter);

        loadDataFromDatabase();

        send.setOnClickListener(click ->{
            ContentValues newRowValues = new ContentValues();
            newRowValues.put(TriviaMyOpener.TEXT_MESSAGE, typed.getText().toString());
            newRowValues.put(TriviaMyOpener.SEND_TYPE, 0);
            long newId = db.insert(TriviaMyOpener.TABLE_NAME, null, newRowValues);

            TriviaMessage msgSend = new TriviaMessage(typed.getText().toString(), true, newId);
            messageList.add(msgSend);
            typed.setText("");
            Toast.makeText(this, "Inserted item id:"+newId, Toast.LENGTH_LONG).show();
            myAdapter.notifyDataSetChanged();

        });

        receive.setOnClickListener(click ->{
            ContentValues newRowValues = new ContentValues();
            newRowValues.put(TriviaMyOpener.TEXT_MESSAGE, typed.getText().toString());
            newRowValues.put(TriviaMyOpener.SEND_TYPE, 1);
            long newId = db.insert(TriviaMyOpener.TABLE_NAME, null, newRowValues);
            TriviaMessage msgRcv = new TriviaMessage(typed.getText().toString(),false, newId);
            messageList.add(msgRcv);
            typed.setText("");

            Toast.makeText(this, "Inserted item id:"+newId, Toast.LENGTH_LONG).show();
            myAdapter.notifyDataSetChanged();
        });

        myList.setOnItemLongClickListener((parent, view, row, id) -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(getString(R.string.del_title))
                    .setMessage(getString(R.string.del_msg1) + row + "\n"
                            + getString(R.string.del_msg2) + id)
                    .setPositiveButton(getString(R.string.yes), (click, arg) -> {
                        messageList.remove(row);
                        db.delete(TriviaMyOpener.TABLE_NAME, TriviaMyOpener.COL_ID + "= ?",
                                new String[] {Long.toString(myAdapter.getItemId(row))});
                        myAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton(getString(R.string.no), (click, arg) -> {
                    })
                    .create().show();
            return true;
        });
    }


    private void loadDataFromDatabase()
    {
        TriviaMyOpener dbOpener = new TriviaMyOpener(this);
        db = dbOpener.getWritableDatabase();
        String[] columns = {TriviaMyOpener.TEXT_MESSAGE, TriviaMyOpener.SEND_TYPE, TriviaMyOpener.COL_ID};
        Cursor results = db.query(false, TriviaMyOpener.TABLE_NAME, columns, null, null,null, null, null, null);

        printCursor(results, db.getVersion());

        int textMessageColumnIndex = results.getColumnIndex(TriviaMyOpener.TEXT_MESSAGE);
        int isSentIndex = results.getColumnIndex(TriviaMyOpener.SEND_TYPE);
        int idColIndex = results.getColumnIndex(TriviaMyOpener.COL_ID);

        while (results.moveToNext()) {
            String msg = results.getString(textMessageColumnIndex);
            int typeInt = results.getInt(isSentIndex);
            Long dataId  = results.getLong(idColIndex);

            boolean msgType;
            if(typeInt == 0) {
                msgType = true;
            } else {
                msgType = false;
            }

            messageList.add(new TriviaMessage(msg, msgType, dataId));
        }
    }

    public void printCursor(Cursor c, int version){
        Log.e("Version", Integer.toString(db.getVersion()));
        Log.e("Count of Columns", Integer.toString(c.getColumnCount()));
        String[] columnNames = c.getColumnNames();
        Log.e("Column Names", Arrays.deepToString(columnNames));
        Log.e("Count of Row", Integer.toString(c.getCount()));
        c.moveToFirst();
        for(int i=1; i < c.getCount(); i++){
            Log.e("row", Integer.toString(i)
                    + ";  message  " + c.getString(c.getColumnIndex(TriviaMyOpener.TEXT_MESSAGE))
                    + ";  isSend:  " + Integer.toString(c.getInt(c.getColumnIndex(TriviaMyOpener.SEND_TYPE)))
                    + ";  _id: " + Integer.toString(c.getInt(c.getColumnIndex(TriviaMyOpener.COL_ID))));
            c.moveToNext();
        }
        c.moveToPosition(-1);
    }



}