package cmps121.finalproject.iplan;

/**
 * Created by sushilpatel on 3/8/16.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;
import android.content.ContentResolver;
import android.net.Uri;
import android.database.Cursor;
import android.provider.ContactsContract.PhoneLookup;


/**
 * Created by sushilpatel on 3/5/16.
 */
public class SmsReceiver extends BroadcastReceiver {
    String numbers = "";
    String response = "";

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();

        SmsMessage[] msgs = null;
        String str = bundle.getString("format");
        String meh  = "" ;
        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++) {
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i], str);
                numbers = msgs[i].getOriginatingAddress();
                response = msgs[i].getMessageBody().toString();
                meh += "SMS from: "+ numbers;
                meh += response;
            }
            if(response.equals("2")) {
                String name = getContactName(context, numbers);
                name += " will not be attending your event";
                Toast.makeText(context, name, Toast.LENGTH_LONG).show();

            }
        }

    }
    public static String getContactName(Context context, String phoneNumber) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        Cursor cursor = cr.query(uri, new String[]{PhoneLookup.DISPLAY_NAME}, null, null, null);
        if (cursor == null) {
            return null;
        }
        String contactName = null;
        if(cursor.moveToFirst()) {
            contactName = cursor.getString(cursor.getColumnIndex(PhoneLookup.DISPLAY_NAME));
        }

        if(cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return contactName;
    }
    /*
    public void checkMessages(String number, String response){
        if(response.equals("2")){
            ContactList list = new ContactList();
            list.removeContact(number);
        }
    }
    */

}