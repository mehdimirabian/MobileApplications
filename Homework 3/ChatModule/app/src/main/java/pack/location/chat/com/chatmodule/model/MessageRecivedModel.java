package pack.location.chat.com.chatmodule.model;

import java.util.ArrayList;

/**
 * Created by ankur on 2/17/16.
 */
public class MessageRecivedModel {

    private ArrayList<Message> result_list;
    private String result;


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ArrayList<Message> getResult_list() {
        return result_list;
    }

    public void setResult_list(ArrayList<Message> result_list) {
        this.result_list = result_list;
    }
}
