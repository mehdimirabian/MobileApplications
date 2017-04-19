package cmps121.finalproject.iplan;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;

import cmps121.finalproject.iplan.contacts.Contact;
import cmps121.finalproject.iplan.contacts.ContactList;



/**
 * Created by katy021teh on 3/7/16.
 */
public class ContactsLoader extends AsyncTask<String, ContactList, ContactList>  {

    ContactListAdapter clAdapter;





    ContactsLoader(ContactListAdapter adap) {
        //init AsyncLoader with the ListView Adapter

        clAdapter = adap;
    }



    //loading a contact
    @Override
    protected ContactList doInBackground(String... filters) {
        ContactList guestsList = null;

        //Filter = text in search textbox
        String filter = filters[0];
        ContentResolver cr =this.clAdapter.context.getContentResolver();
        int count = 0;
        //Code to fetch contacts.
        Uri uri = ContactsContract.Contacts.CONTENT_URI;


        //Fields to select from database
        String[] projection = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.HAS_PHONE_NUMBER
        };
        /*Querying database (Select fields in projection from database where contact name like 'filter%', sort by name, in ascending order)*/
        Cursor cursor = cr.query(uri, projection,  ContactsContract.Contacts.DISPLAY_NAME +
                " LIKE ?", new  String[] {filter.toString()},
                ContactsContract.Contacts.DISPLAY_NAME+ " ASC");

        if(cursor.getCount()>0) {

            guestsList = new ContactList();

            while (cursor.moveToNext()) {
                //Filtering Contacts with Phone Numbers

                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {

                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    //Phone numbers lies in a separate table. Querying that table with Contact ID

                    Cursor ph_cur = cr.query(CommonDataKinds.Phone.CONTENT_URI, null,
                            CommonDataKinds.Phone.CONTACT_ID +
                            "=?", new String[]{id}, null);
                    while (ph_cur.moveToNext())
                    {

                        String phId = ph_cur.getString(ph_cur.getColumnIndex(CommonDataKinds.Phone._ID));


                        String ph_no = ph_cur.getString(ph_cur.getColumnIndex(CommonDataKinds.Phone.NUMBER));
                        Contact tmp = new Contact(phId,name,ph_no);
                        guestsList.addContact(tmp);
                        count++;

                        //Refresh ListView upon loading 100 Contacts
                        if(count==100)
                        {
                            publishProgress(guestsList);
                            count=0;
                        }

                    }
                    ph_cur.close();
                }


            }
            cursor.close();

        }


        return guestsList;
    }



    //Code to refresh list view
    @Override
    protected void onProgressUpdate(ContactList... glists )
    {

        clAdapter.allContacts = glists[0];
        clAdapter.notifyDataSetChanged();

    }


    @Override
   //Loading contacts finished, refresh list view to load any missed out contacts
    protected void onPostExecute(ContactList result)
    {

        clAdapter.allContacts=result;
        clAdapter.notifyDataSetChanged();

    }



}
