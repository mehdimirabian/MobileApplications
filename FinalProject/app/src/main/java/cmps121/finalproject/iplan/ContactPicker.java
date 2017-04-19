package cmps121.finalproject.iplan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import cmps121.finalproject.iplan.contacts.ContactList;

public class ContactPicker extends AppCompatActivity {


    ImageButton backBtn;
    Button nextActivity;
    ContactListAdapter adapter;
    ContactListAdapter adap;
    ListView contactsList;
    ListView selectedList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_picker);

        backBtn = (ImageButton) findViewById(R.id.btnHome);
        nextActivity = (Button) findViewById(R.id.btnDone);


        contactsList = (ListView) findViewById(R.id.contactList);
        adapter = new ContactListAdapter(this, new ContactList());
        contactsList.setAdapter(adapter);

        try {
        //Running AsyncLoader with adapter and blank filter
            new ContactsLoader(adapter).execute("%");
        } catch (Exception e) {
            e.printStackTrace();
        }

        EditText srchBox = (EditText) findViewById(R.id.searchContact);
        //Adding text change listener for filtering contacts
        srchBox.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String filter = s.toString().trim() + "%";
                //Running AsyncLoader with adapter and search text as parameters
                try {
                    new ContactsLoader(adapter).execute(filter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



        //Code to return selected contacts and pass it to the next activity
        nextActivity.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    Intent intent = new Intent(ContactPicker.this, NewEvent.class); // replace with next activity once implemented
                    if (adapter.selectedContacts.getCount() > 0) {

                        String[][] sel_cons = new String[adapter.selectedContacts.getCount()][3];
                        for (int i = 0; i < adapter.selectedContacts.getCount(); i++) {
                            sel_cons[i][0] = adapter.selectedContacts.getContacts().get(i).id;
                            sel_cons[i][1] = adapter.selectedContacts.getContacts().get(i).name;
                            sel_cons[i][2] = adapter.selectedContacts.getContacts().get(i).number;



                        }

                        Toast.makeText(ContactPicker.this, "Number of Selected Contacts: " +
                                adapter.selectedContacts.getCount(), Toast.LENGTH_SHORT).show();

                        //Bundling up the contacts to pass
                        Bundle data_to_pass = new Bundle();

                        data_to_pass.putSerializable("selectedContacts", sel_cons);

                        intent.putExtras(data_to_pass);

                        setResult(RESULT_OK, intent);

                        startActivity(intent);

                        //Ending Activity and passing result
                        //finish();

                    } else {

                        //If user presses  button without selecting any contact
                        Toast.makeText(ContactPicker.this, "No contact is selected yet "
                                , Toast.LENGTH_SHORT).show();

                    }

                }
            });

        //code to cancel the activity and go back to home page
        backBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ContactPicker.this, MainActivity.class);
                startActivity(intent);
                setResult(RESULT_CANCELED, intent);
                finish();

            }
        });

    }



    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);


        if(resultCode== this.RESULT_OK && !data.getExtras().isEmpty() && data.getExtras().containsKey("selectedContacts"))
        {

            Object[] objArray = (Object[])data.getExtras().getSerializable("selectedContacts");
            String selectedContacts[][]=null;
            if(objArray!=null)
            {
                // selectedContacts[] contains the selected contacts
                selectedContacts = new String[objArray.length][];
                for(int i=0;i<objArray.length;i++)
                {
                    selectedContacts[i] = (String[]) objArray[i];
                }

            }
        }

    }

}