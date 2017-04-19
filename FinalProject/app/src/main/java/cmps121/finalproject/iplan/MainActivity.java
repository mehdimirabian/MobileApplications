package cmps121.finalproject.iplan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton newB;

    ExpandableListView listView;
    MyExpandableListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



         listView = (ExpandableListView)findViewById(R.id.eventList);
         adapter = new MyExpandableListAdapter(this, Eventdisplay.groups);
        listView.setAdapter(adapter);

        newB = (ImageButton)findViewById(R.id.newB);

        newB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //request_code to identify between activities
                final int request_code = 1010;
                // Start ContackPicker Activity with the Contact ListView Result
                startActivityForResult(new Intent(MainActivity.this, ContactPicker.class), request_code);

            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            // setting onItemLongClickListener and passing the position to the function
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int position, long arg3) {
                removeItemFromList(position);

                return true;
            }
        });
    }
    // method to remove list item
    protected void removeItemFromList(int position) {
        final int deletePosition = position;

        AlertDialog.Builder alert = new AlertDialog.Builder(
                MainActivity.this);

        alert.setTitle("Delete");
        alert.setMessage("Do you want delete this event entirely?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TOD O Auto-generated method stub

                // main code on after clicking yes
                Eventdisplay.groups.remove(deletePosition);
                adapter.notifyDataSetChanged();
                adapter.notifyDataSetInvalidated();

            }
        });
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        alert.show();



    }




}

