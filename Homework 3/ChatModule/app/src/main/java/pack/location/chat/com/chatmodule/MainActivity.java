package pack.location.chat.com.chatmodule;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Random;
import java.util.UUID;

import pack.location.chat.com.chatmodule.utils.CalculatePosition;
import pack.location.chat.com.chatmodule.utils.SharePreference;

public class MainActivity extends AppCompatActivity {


    private TextInputLayout tINicKName;
    private Button btnStartChat;

    private Context mContext;

    private CalculatePosition position;

    private SharePreference sharePreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = MainActivity.this;

       sharePreference = new SharePreference(mContext);

        startChat();

        position = new CalculatePosition(mContext);

        tINicKName = (TextInputLayout)findViewById(R.id.md_editTextNickName);
        btnStartChat = (Button)findViewById(R.id.btnStartChat);

        tINicKName.setErrorEnabled(true);

        btnStartChat.setOnClickListener(startChat);

    }

    private void startChat()
    {
        if(sharePreference.getString(sharePreference.nickName)!= null)
        {
            startActivity(new Intent(mContext, ChatActivity.class));
            finish();
        }
    }


    View.OnClickListener startChat = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (!isNotNullOrBlank(tINicKName.getEditText().getText().toString())) {

                tINicKName.setError("Enter your nick name");

            } else {

                sharePreference.setString(sharePreference.nickName, tINicKName.getEditText().getText().toString());
                sharePreference.setString(sharePreference.userId, getUID());
                sharePreference.setString(sharePreference.lat,position.latStr );
                sharePreference.setString(sharePreference.lng,position.lngStr );

                startChat();

            }

        }
    };

    public String getUID()
    {
        return UUID.randomUUID().toString();
    }

    public  boolean isNotNullOrBlank(String s) {
        return !(s == null || s.trim().equals(""));
    }


}
