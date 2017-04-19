package cmps121.finalproject.iplan;


import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



public class NewEvent extends AppCompatActivity {



    private EditText eventName;
    private EditText eventDate;
    private EditText eventTime;
    private EditText eventAddress;
    private EditText eventDescription;



    public Message objMess = new Message();


    //TextWatcher
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3)
        {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            checkFieldsForEmptyValues();
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);


        eventName = (EditText) findViewById(R.id.editName);
        eventDate = (EditText) findViewById(R.id.editDate);
        eventTime = (EditText) findViewById(R.id.editTime);
        eventAddress = (EditText) findViewById(R.id.editAddress);
        eventDescription = (EditText) findViewById(R.id.editDescription);

        //set listeners
        eventName.addTextChangedListener(textWatcher);
        eventDate.addTextChangedListener(textWatcher);
        eventTime.addTextChangedListener(textWatcher);
        eventAddress.addTextChangedListener(textWatcher);
        eventDescription.addTextChangedListener(textWatcher);


        // run once to disable if empty
        checkFieldsForEmptyValues();

    }


    public void sendClick(View v){
        //here I will write the code for sending the text message
        String textMessage = ("You've been invited to the following event! (Reply with 2 if not interested)\n"
                + objMess.name + "\n"
                + objMess.date + "\n"
                + objMess.time + "\n"
                +objMess.address + "\n"
                + objMess.description);

        //receive the contacts from ContactPicker.java
        Object[] objectArray = (Object[]) getIntent().getExtras().getSerializable("selectedContacts");
        int horizontalLength = 0;
        if (objectArray.length > 0)
            horizontalLength = ((Object[]) objectArray[0]).length; // See explanation to understand the cast
        String[][] my_list = new String[objectArray.length][horizontalLength];
        int i = 0, j;
        for(Object row : objectArray)
        {
            my_list[i][0] = ((String[])row)[0];
            my_list[i][1] = ((String[])row)[1];
            my_list[i][2] = ((String[])row)[2];
            i++;

        }

        for(j=0; j<i; j++) {
            /***************Note: the sendSMS function should be called
             * in a loop as many times as the number of contacts passed by
             * the previous activity
             * This means the previous activity will be intent Get here
             */
            sendSMS(my_list[j][2], textMessage);

        }
        Eventdisplay.displayItems(objMess);
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("message", objMess);
        setResult(RESULT_OK, intent);
        startActivity(intent);
        finish();
    }




    private  void checkFieldsForEmptyValues(){
        Button b = (Button) findViewById(R.id.sendButton);

        objMess.name = eventName.getText().toString();
        objMess.date = eventDate.getText().toString();
        objMess.time = eventTime.getText().toString();
        objMess.address = eventAddress.getText().toString();
        objMess.description = eventDescription.getText().toString();

        if(objMess.name.equals("") || objMess.date.equals("")
                || objMess.time.equals("") || objMess.address.equals("")
                || objMess.description.equals(""))
        {
            b.setEnabled(false);
        }

        else
        {
            b.setEnabled(true);
        }
    }

   private void sendSMS(String phoneNumber, String txt){
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, NewEvent.class), 0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, txt, pi, null);

    }

}


