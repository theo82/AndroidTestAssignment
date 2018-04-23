package loginscreen.solution.example.com.loginscreen;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class WelcomeActivity extends AppCompatActivity {
  TextView nameTextView;
  TextView emailTextView;
  TextView phoneTextView;

  String phone;
  String email;
  @Override
  public void onCreate(Bundle instanceState) {
    super.onCreate(instanceState);
    setContentView(R.layout.content_welcome);

    nameTextView = (TextView)findViewById(R.id.et_name);
    emailTextView = (TextView)findViewById(R.id.tv_email);
    phoneTextView = (TextView)findViewById(R.id.tv_phone);


    Cursor cursor = new DetailsDb(this).query();

    if(cursor.moveToFirst()){
      do{
        email = cursor.getString(cursor.getColumnIndex("email"));
        phone = cursor.getString(cursor.getColumnIndex("phone"));
      }while(cursor.moveToNext());
    }

    emailTextView.setText(email);
    phoneTextView.setText(phone);
  }


}
