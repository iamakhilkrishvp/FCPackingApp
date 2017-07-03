package cied.in.fcpacking;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.HashMap;



/**
 * Created by cied on 24/8/16.
 */
public class SessionManager {

    SharedPreferences pref;
    SharedPreferences prefid;

    Editor editor;

    Context _context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "CastorPref";
    public static final String KEY_CYCLE_ID = "cycleId";
    public static final String KEY_TRADE_CYCLE_ID = "tradeCycleId";
    public static final String KEY_SOURCE_DATE = "sourceDate";



    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editor.commit();
    }

    public void setCycleId(String cycleId)
    {
        editor.putString(KEY_CYCLE_ID,cycleId);
        editor.commit();
    }
    public void setTradeCycleId(String cycleId)
    {
        editor.putString(KEY_TRADE_CYCLE_ID,cycleId);
        editor.commit();
    }
    public void setSourceDate(String date)
    {
        editor.putString(KEY_SOURCE_DATE,date);
        editor.commit();
    }
    public HashMap<String, String> getCycleId() {

        HashMap<String, String> session = new HashMap<String, String>();
        session.put(KEY_CYCLE_ID, pref.getString(KEY_CYCLE_ID, null));

        return session;
    }
    public HashMap<String, String> getSourceDate() {

        HashMap<String, String> session = new HashMap<String, String>();
        session.put(KEY_SOURCE_DATE, pref.getString(KEY_SOURCE_DATE, null));

        return session;
    }
    public HashMap<String, String> getTradeCycleId() {

        HashMap<String, String> session = new HashMap<String, String>();
        session.put(KEY_TRADE_CYCLE_ID, pref.getString(KEY_TRADE_CYCLE_ID, null));

        return session;
    }



}