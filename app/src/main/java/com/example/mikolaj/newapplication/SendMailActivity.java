package com.example.mikolaj.newapplication;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SendMailActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);
        final Button send = (Button) this.findViewById(R.id.button1);

        send.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Log.i("SendMailActivity", "Send Button Clicked.");

                String toEmails = ((TextView) findViewById(R.id.editText3))
                        .getText().toString();
                List toEmailList = Arrays.asList(toEmails
                        .split("\\s*,\\s*"));
                Log.i("SendMailActivity", "To List: " + toEmailList);
                String emailSubject = ((Spinner) findViewById(R.id.editText4))
                        .getSelectedItem().toString();
                String emailBody = ((TextView) findViewById(R.id.editText5))
                        .getText().toString();
                new SendMailTask(SendMailActivity.this).execute("mlodewilki2018@gmail.com",
                        "mlodewilki", toEmails, emailSubject, emailBody);
                Intent toy = new Intent(SendMailActivity.this, MainActivity.class);
                startActivity(toy);
                Toast.makeText(getApplicationContext(),
                        "Email został wyslany", Toast.LENGTH_SHORT).show();
            }
        });
    }
}