package pack.location.chat.com.chatmodule;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import pack.location.chat.com.chatmodule.adapter.ChatAdapter;
import pack.location.chat.com.chatmodule.model.Message;
import pack.location.chat.com.chatmodule.model.MessageRecivedModel;
import pack.location.chat.com.chatmodule.utils.CheckHaveNetworkConnection;
import pack.location.chat.com.chatmodule.utils.JSONParser;
import pack.location.chat.com.chatmodule.utils.SharePreference;

public class ChatActivity extends AppCompatActivity {


    private ListView myList;
    private ChatAdapter mMessageAdapter;

    private EditText mMessageEditText;
    private Button mChatSendButton;
    private SharePreference set;

    private Button mBtnRefresh;

    private ProgressDialog progressDialog;

    private SharePreference sharePreference;

    private Context mContext;

    private MessageRecivedModel messageList;

    public ArrayList<Message> chatMessages=new ArrayList<Message>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);



        mContext = ChatActivity.this;



        sharePreference = new SharePreference(mContext);

        loadMessageFromServer();
        init();
    }

    private void init()
    {

        myList = (ListView) findViewById(R.id.myList);

        mMessageEditText = (EditText)findViewById(R.id.messageEdit);
        mChatSendButton = (Button)findViewById(R.id.chatSendButton);
        mBtnRefresh = (Button)findViewById(R.id.chatRefreshButton);

        mChatSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // new SendMessageTask(mMessageEditText.getText().toString()).execute();

                Random randomGenerator = new Random();


                Message message = new Message();
                message.setMessage(mMessageEditText.getText().toString());
                message.setMessage_id(randomGenerator.nextInt(10000) + "");
                message.setNickname(sharePreference.getString(sharePreference.nickName));
                message.setUser_id(sharePreference.getString(sharePreference.userId));
                String[] formats = new String[] {

                        "yyyy-MM-dd'T'HH:mm:ss.SSSS",
                };
                for (String format : formats) {
                    SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);


                    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

                    message.setTimestamp(sdf.format(new Date()));

                }


                sendMessageToServer(message);

                mMessageEditText.setText("");
            }
        });

        mBtnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMessageFromServer();
            }
        });
    }


    private void loadMessageFromServer()
    {

        new AsyncTask<Void, Void, Integer>()
        {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(mContext, "", "Please wait...\nLoading messages", false);
                progressDialog.setCancelable(false);
            }

            @Override
            protected Integer doInBackground(Void... params) {



                int returnValue = -1;
                try {

                if(CheckHaveNetworkConnection.haveNetworkConnection(mContext)) {

                    String url = "https://luca-teaching.appspot.com/localmessages/default/get_messages?lat=" + sharePreference.getString(sharePreference.lat) +
                            "&lng=" + sharePreference.getString(sharePreference.lng) + "&user_id=" + sharePreference.getString(sharePreference.userId);


                    returnValue = 0;
                    JSONParser parser = new JSONParser();

                    JSONObject response = parser.getJSONFromUrlGetMethod(url);

                    if (response != null) {

                        Gson gson = new Gson();


                        messageList = gson.fromJson(response.toString(), MessageRecivedModel.class);

                        if (messageList != null && messageList.getResult_list() != null) {
                            Collections.reverse(messageList.getResult_list());

                        }

                        returnValue = 1;

                    }
                }
                }
                catch (Exception e)
                {

                }

                return returnValue;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);

                try {
                    progressDialog.cancel();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(integer == -1)
                {
                    showToast("Please enable your mobile data network");
                }
                else if(integer == 0)
                {
                    showToast("No message to this location");
                }
                else
                {
                    mMessageAdapter = new ChatAdapter(mContext, messageList.getResult_list());
                    myList.setAdapter(mMessageAdapter);
                    myList.setVerticalScrollbarPosition(mMessageAdapter.chatMessages.size());
                }
            }
        }.execute();

    }

    private void showToast(String message)
    {
        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();

    }


    private void sendMessageToServer( Message message)
    {

        mMessageAdapter.chatMessages.add(message);
        mMessageAdapter.notifyDataSetChanged();

        chatMessages.add(message);

       // myList.setVerticalScrollbarPosition(mMessageAdapter.chatMessages.size());

        myList.smoothScrollToPosition(mMessageAdapter.chatMessages.size());

        new AsyncTask<Void, Void, Integer>()
        {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();


            }

            @Override
            protected Integer doInBackground(Void... params) {

try {
    if (CheckHaveNetworkConnection.haveNetworkConnection(mContext)) {

        for (int i = 0; i < chatMessages.size(); i++) {

            Message message = chatMessages.get(i);

            String url = "https://luca-teaching.appspot.com/localmessages/default/post_message?lat=" + sharePreference.getString(sharePreference.lat) +
                    "&lng=" + sharePreference.getString(sharePreference.lng) + "&user_id=" + sharePreference.getString(sharePreference.userId) + "&nickname=" +
                    sharePreference.getString(sharePreference.nickName) + "&message=" + message.getMessage() + "&message_id=" + message.getMessage_id();


            JSONParser parser = new JSONParser();

            JSONObject response = parser.getJSONFromUrlGetMethod(url);

            chatMessages.remove(i);

        }
    }
}
catch (Exception e)
{

}


                return null;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);


            }
        }.execute();

    }

}
