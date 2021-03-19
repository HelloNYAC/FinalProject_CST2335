package com.finalproject_cst2335.trivia;

public class TriviaMessage {
    private String msg;
    private boolean isSend;
    protected long id;

    public TriviaMessage(String msg, boolean isSend, long id){
        this.msg = msg;
        this.isSend = isSend;
        this.id = id;
    }

    public boolean getIsSend(){
        return isSend;
    }

    public long getId(){
        return id;
    }

    public String getMsg() {
        return msg;
    }
}
