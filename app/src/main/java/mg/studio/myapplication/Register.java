package mg.studio.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Register activity for a user to sign up.
 */
public class Register extends AppCompatActivity {
    private static final String TAG = Register.class.getSimpleName(); // Tag for logging purposes
    private Button btnRegister; // Register button
    private Button btnLinkToLogin; // Link to login screen button
    private EditText inputFullName; // Input field for full name
    private EditText inputEmail; // Input field for email
    private EditText inputPassword; // Input field for password
    private SessionManager session; // Session manager for keeping track of user login status
    private ProgressDialog pDialog; // Progress dialog for registering the user
    private String name; // Name of the user
    Feedback feedback; // Feedback for the result of registration

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        // Initializing input fields
        inputFullName = findViewById(R.id.name);
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);

        // Initializing buttons
        btnRegister = findViewById(R.id.btnRegister);
        btnLinkToLogin = findViewById(R.id.btnLinkToLoginScreen);

        // Initializing progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Initializing session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in
        if (session.isLoggedIn()) {
            // If the user is already logged in, go to the MainActivity
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        // Set onClickListener for Register button
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Getting input values from the input fields
                name = inputFullName.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                // Check if all the fields are filled
                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    // Disable the register button to avoid repeated clicks
                    btnRegister.setClickable(false);
                    // Register the user
                    registerUser(name, email, password);
                } else {
                    // If the fields are not filled, display a toast message
                    Toast.makeText(getApplicationContext(),
                                    "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        // Set onClickListener for Link to Login Screen button
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Go to the Login activity
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
                finish();
            }
        });
    }


    // The following Java code is a commented version of the original Android code

    /**
     * Register a new user to the server database
     *
     * @param name     username
     * @param email    email address, which should be unique to the user
     * @param password length should be < 50 characters
     */
    private void registerUser(final String name, final String email,
                              final String password) {

        // Display a progress dialog with message "Registering ..."
        pDialog.setMessage("Registering ...");
        if (!pDialog.isShowing()) pDialog.show();

        // Todo: Need to check Internet connection
        // Start a background task to register the user
        new DownloadData().execute(name, email, password);

    }

    // AsyncTask class to handle background processing for registering the user
    class DownloadData extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... strings) {
            // Initialize feedback object
            feedback = new Feedback();

            // Initialize variables
            String response = null;
            OutputStreamWriter request = null;
            int parsingFeedback = feedback.FAIL;

            final String BASE_URL = new Config().getRegisterUrl(); // URL to register the user
            final String NAME = "name";
            final String EMAIL = "email";
            final String PASSWORD = "password";
            final String PARAMS = NAME + "=" + strings[0] + "&" + EMAIL + "=" + strings[1] + "&" + PASSWORD + "=" + strings[2];

            // Initialize URL and HTTP connection objects
            URL url = null;
            HttpURLConnection connection = null;
            try {
                // Open a connection to the URL
                url = new URL(BASE_URL);
                connection = (HttpURLConnection) url.openConnection();

                // Set the request method to POST and add request properties
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setDoOutput(true);

                // Set timeouts for reading InputStream and connecting
                connection.setReadTimeout(9000);
                connection.setConnectTimeout(9000);

                // Output the parameters to the server
                request = new OutputStreamWriter(connection.getOutputStream());
                request.write(PARAMS);
                request.flush();
                request.close();

                // Read the input stream from the connection
                InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                response = readStream(inputStream, 500);
                inputStream.close();

                // Parse the response
                parsingFeedback = parsingResponse(response);

            } catch (MalformedURLException e) {
                Log.e("TAG", "URL - " + e);
                feedback.setError_message(e.toString());
                return feedback.FAIL;
            } catch (IOException e) {
                Log.e("TAG", "openConnection() - " + e);
                feedback.setError_message(e.toString());
                return feedback.FAIL;
            } finally {
                // Disconnect the connection and log the response
                if (connection != null) connection.disconnect();
                Log.d("TAG", "Response " + response);

                return parsingFeedback;
            }


        }


        @Override
        protected void onPostExecute(Integer mFeedback) {
            super.onPostExecute(mFeedback);
            if (pDialog.isShowing()) pDialog.dismiss();
            if (mFeedback == feedback.SUCCESS) {
                Intent intent = new Intent(getApplication(), Login.class);
                intent.putExtra("feedback", feedback);
                startActivity(intent);
                finish();
            } else {
                btnRegister.setClickable(true);
                Toast.makeText(getApplication(), feedback.getError_message(), Toast.LENGTH_SHORT).show();
            }

        }

        /**
         * Converts the contents of an InputStream to a String.
         */
        String readStream(InputStream stream, int maxReadSize)
                throws IOException {
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            char[] rawBuffer = new char[maxReadSize];
            int readSize;
            StringBuffer buffer = new StringBuffer();
            while (((readSize = reader.read(rawBuffer)) != -1) && maxReadSize > 0) {
                if (readSize > maxReadSize) {
                    readSize = maxReadSize;
                }
                buffer.append(rawBuffer, 0, readSize);
                maxReadSize -= readSize;
            }

            Log.d("TAG", buffer.toString());
            return buffer.toString();
        }
    }


    public int parsingResponse(String response) {

        try {
            JSONObject jObj = new JSONObject(response);
            /**
             * If the registration on the server was successful the return should be
             * {"error":false}
             * Else, an object for error message is added
             * Example: {"error":true,"error_msg":"Invalid email format."}
             * Success of the registration can be checked based on the
             * object error, where true refers to the existence of an error
             */
            boolean error = jObj.getBoolean("error");

            if (!error) {
                //No error, return from the server was {"error":false}
                feedback.setName(name);
                return feedback.SUCCESS;
            } else {
                // The return contains error messages
                String errorMsg = jObj.getString("error_msg");
                Log.d("TAG", "errorMsg : " + errorMsg);
                feedback.setError_message(errorMsg);
                return feedback.FAIL;
            }
        } catch (JSONException e) {
            feedback.setError_message(e.toString());
            return feedback.FAIL;
        }

    }

}

