package com.example.mikolaj.newapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class contact extends AppCompatActivity {
    public Button btnSendContactForm;
    EditText emailField, feedbackField;
    Spinner feedbackSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_contact);
        feedbackField = findViewById(R.id.EditTextFeedbackBody);
        emailField = findViewById(R.id.EditTextEmail);
        feedbackSpinner = findViewById(R.id.SpinnerFeedbackType);
        btnSendContactForm = findViewById(R.id.ButtonSendFeedback);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ButtonSendFeedback:
                String email = emailField.getText().toString();
                String feedback = feedbackField.getText().toString();
                String feedbackType = feedbackSpinner.getSelectedItem().toString();
                sendEmail(email,feedback,feedbackType);
                break;
        }
    }
    protected void sendEmail(String email, String feedback, String feedbackType) {
        Log.i("Send email", "");

        String[] TO = {"mikolajkrol93@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");

        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, feedbackType);
        emailIntent.putExtra(Intent.EXTRA_TEXT, feedback);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email.", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(contact.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }




}
