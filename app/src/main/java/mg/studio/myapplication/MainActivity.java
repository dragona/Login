package mg.studio.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

/***
 * The code implements a MainActivity class in an Android application.
 * This class extends the AppCompatActivity class and provides a screen
 * that is accessible only to logged-in users. The activity retrieves
 * data from the Register activity if it was just registered and displays it.
 * The logout function logs out the user and redirects to the Login activity.
 */

// MainActivity class is a subclass of AppCompatActivity
public class MainActivity extends AppCompatActivity {
    // Declare private variables for the text view and session manager
    private TextView tvName;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize the session manager
        session = new SessionManager(this);
        // If the user is not logged in, log them out
        if (!session.isLoggedIn()) {
            logout();
        }
        // Set the content view to the activity_main layout
        setContentView(R.layout.activity_main);
        // Initialize the text view
        tvName = findViewById(R.id.user_name);

        // Check if there is a bundle of extras passed from the Register class
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            // Retrieve the Feedback parcelable from the extras
            Feedback feedback = bundle.getParcelable("feedback");
            // Get the user name from the Feedback object
            String userName = feedback.getName();
            // Set the user name in the text view
            tvName.setText(userName);
        }
    }

    // Method to handle the logout button click event
    public void btnLogout(View view) {
        logout();
    }

    // Logout method to log the user out
    public void logout() {
        // Update the session to set the user as not logged in
        session.setLogin(false);
        // Start the Login activity
        startActivity(new Intent(this, Login.class));
        // Finish the MainActivity
        finish();
    }
}
