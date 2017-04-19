package cmps121.finalproject.iplan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import cmps121.finalproject.iplan.contacts.Contact;
import cmps121.finalproject.iplan.contacts.ContactList;

/**
 * Created by katy021teh on 3/6/16.
 */
public class ContactListAdapter extends BaseAdapter {

    Context context;
    ContactList allContacts;
    ContactList selectedContacts;

    public ContactListAdapter(Context context, ContactList allContacts) {
        super();
        this.context = context;
        this.allContacts = allContacts;
        selectedContacts = new ContactList();

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view_row = inflater.inflate(R.layout.contact_list, parent, false);

        CheckBox checkContact = (CheckBox) view_row.findViewById(R.id.chkbxContact);
        checkContact.setId(Integer.parseInt(allContacts.getContacts().get(position).id));

        // Text to display near checkbox for Contact name and number
        checkContact.setText(allContacts.getContacts().get(position).name.toString() +
                "   [Phone:  "+ allContacts.getContacts().get(position).number.toString() + "]");


        if(alreadySelected(allContacts.getContacts().get(position)))
        {
            checkContact.setChecked(true);
        }

        //Code to get Selected Contacts.
        checkContact.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {


                Contact t = allContacts.getContact(arg0.getId());
                if (t != null && arg1) {

                    if (!alreadySelected(t))
                        selectedContacts.addContact(t);

                } else if (!arg1 && t != null) {
                    selectedContacts.removeContact(String.valueOf(arg0.getId()));
                }


            }

        });

        return view_row;
    }



    public boolean alreadySelected(Contact t) {
        boolean ret = false;

        if(selectedContacts.getContact(Integer.parseInt(t.id))!=null)
            ret=true;

        return ret;
    }

    @Override
    public int getCount() {
        return allContacts.getCount();
    }

    @Override
    public Contact getItem(int arg0) {
        return allContacts.getContacts().get(arg0);
    }

    @Override
    public long getItemId(int arg0) {

       return Long.parseLong(allContacts.getContacts().get(arg0).id);
    }


}
