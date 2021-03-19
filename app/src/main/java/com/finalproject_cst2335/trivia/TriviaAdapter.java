package com.finalproject_cst2335.trivia;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;


public class TriviaAdapter extends BaseAdapter {

    private ArrayList<TriviaMessage> messageList = new ArrayList<>();

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public TriviaMessage getItem(int position) {
        return messageList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TriviaMessage msg = (TriviaMessage) getItem(position);
//        LayoutInflater inflater = getLayoutInflater();
//        if(msg.getIsSend()){
//            View sendView = inflater.inflate(R.layout.tg_row_send_layout, parent, false);
//            TextView thisRowText = sendView.findViewById(R.id.row_item);
//            thisRowText.setText(msg.getMsg());
//            return sendView;
//        }
//        else{
//            View rcvView = inflater.inflate(R.layout.tg_row_rcv_layout, parent, false);
//            TextView thisRowText = rcvView.findViewById(R.id.row_item);
//            thisRowText.setText(msg.getMsg());
//            return rcvView;
//        }
//    }
        return null;
    }
    @Override
    public long getItemId ( int position){
        return messageList.get(position).getId();
    }
}
