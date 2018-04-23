    package loginscreen.solution.example.com.loginscreen;

    import android.content.ContentValues;
    import android.content.Context;
    import android.content.Intent;
    import android.database.Cursor;
    import android.database.sqlite.SQLiteDatabase;
    import android.graphics.Color;
    import android.os.Bundle;
    import android.support.v7.app.AppCompatActivity;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.Toast;
    import android.widget.ViewFlipper;

    import java.util.regex.Matcher;
    import java.util.regex.Pattern;

    public class MainActivity extends AppCompatActivity implements View.OnClickListener{

      Button loginButton;
      Button signUpButton;
      Button signInButton;
      Button createAccountBtn;
      ViewFlipper viewFlipper;

      EditText etName;
      EditText etEmail;
      EditText etPassword;
      EditText etContact;

      String email;
      String contact;
      String password;

      Cursor cursor;
      boolean clicked = false;

        ContentValues values;
      @Override
      public void onCreate(Bundle instanceState) {
        super.onCreate(instanceState);
        setContentView(R.layout.content_main);


        loginButton = (Button)findViewById(R.id.bt_login);

        signUpButton = (Button)findViewById(R.id.bt_signup);

        signInButton = (Button)findViewById(R.id.bt_sign_in);


        createAccountBtn = (Button)findViewById(R.id.bt_create);

        viewFlipper = (ViewFlipper)findViewById(R.id.view_flipper);


        etEmail = (EditText)findViewById(R.id.et_email);
        etPassword = (EditText)findViewById(R.id.et_password);
        etContact = (EditText)findViewById(R.id.et_phone);



        loginButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
        createAccountBtn.setOnClickListener(this);
        signInButton.setOnClickListener(this);
        loginButton.setBackgroundColor(Color.GREEN);


        //updateTheViewBasedOnTag();
        }

        @Override
        public void onClick(View v) {

            CheckCredentials checkCredentials = new CheckCredentials(this);
            email = etEmail.getText().toString();




            password = etPassword.getText().toString();

            contact = etContact.getText().toString();


            switch (v.getId()) {
                case R.id.bt_login:
                    loginButton.setTag(true);
                    signUpButton.setTag(false);
                    viewFlipper.setDisplayedChild(0);

                    signUpButton.setBackgroundColor(Color.WHITE);
                    loginButton.setBackgroundColor(Color.GREEN);

                    break;
                case R.id.bt_sign_in:
                    loginButton.setTag(true);
                    signUpButton.setTag(false);

                    if (checkCredentials.isauthenticate(email, password)) {
                        Intent i = new Intent(MainActivity.this, WelcomeActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(this, "User doesn't exist", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.bt_signup:

                    if (email.isEmpty() || password.isEmpty() || contact.isEmpty()) {
                        Toast.makeText(this, "Enter all fields", Toast.LENGTH_SHORT).show();
                    }
                    signUpButton.setTag(true);
                    loginButton.setTag(false);
                    viewFlipper.setDisplayedChild(1);
                    signUpButton.setBackgroundColor(Color.GREEN);
                    loginButton.setBackgroundColor(Color.WHITE);

                    break;

                case R.id.bt_create:

                    values = new ContentValues();
                    values.put("email", email);
                    values.put("password", password);
                    values.put("phone", contact);

                    DetailsDb detailsDb = new DetailsDb(this);

                    cursor = detailsDb.validate(email, password);

                    if (cursor.getCount() > 0) {
                        Toast.makeText(this, "User exists.Sorry!", Toast.LENGTH_SHORT).show();

                    } else {

                        if (!(contact.length() == 10) || !contact.matches("[0-9]+")) {
                            etContact.setError("Contact is Invalid");
                        } else  if (!(password.length() >= 6) || !password.matches(".*?[A-Z].*") || !password.matches(".*?[a-z].*") || !password.matches(".*?[0-9].*") || !password.matches(".*?[\\W\\-_].*")) {
                            etPassword.setError("Password is Invalid");
                        } else if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
                            etEmail.setError("Invalid Email Address");
                        }else{

                            detailsDb.insert(values);
                            Toast.makeText(this, "Your are registered!", Toast.LENGTH_SHORT).show();


                        }
                        break;
                    }

            }


        }

    }

