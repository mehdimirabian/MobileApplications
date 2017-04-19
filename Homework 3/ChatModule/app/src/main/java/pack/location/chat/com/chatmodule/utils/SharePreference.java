package pack.location.chat.com.chatmodule.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ankur on 8/6/2015.
 */
public class SharePreference {

    private String PREFS_NAME = "SettingsFile";

    private SharedPreferences sharePreference;

    public String nickName = "nickName";
    public String userId = "userId";
    public String lat = "lat";
    public String lng = "lng";

    public SharePreference(Context ctx)
    {
        if(sharePreference==null)
            sharePreference = ctx.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
    }

    public void setString(String key,String value)
    {
        SharedPreferences.Editor editor = sharePreference.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key)
    {
        return sharePreference.getString(key, null);
    }
}
