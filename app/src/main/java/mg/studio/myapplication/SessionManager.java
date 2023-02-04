package mg.studio.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * SessionManager class is used to manage user session.
 * It uses SharedPreferences to store session information.
 */
public class SessionManager {

    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    SharedPreferences.Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "pref_name";

    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    /**
     * Constructor for SessionManager class.
     * @param context Context of the calling activity.
     */
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * This method sets the user login session.
     * @param isLoggedIn Boolean indicating whether the user is logged in or not.
     */
    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    /**
     * This method returns the user login session.
     * @return Boolean indicating whether the user is logged in or not.
     */
    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }
}
