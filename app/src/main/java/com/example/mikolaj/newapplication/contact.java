package com.example.mikolaj.newapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class contact extends AppCompatActivity {
    public Button btnSendContactForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_contact);

        btnSendContactForm = (Button) findViewById(R.id.ButtonSendFeedback);

        btnSendContactForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText nameField = (EditText) findViewById(R.id.EditTextName);
                String name = nameField.getText().toString();

                final EditText emailField = (EditText) findViewById(R.id.EditTextEmail);
                String email = emailField.getText().toString();

                final EditText feedbackField = (EditText) findViewById(R.id.EditTextFeedbackBody);
                String feedback = feedbackField.getText().toString();

                final Spinner feedbackSpinner = (Spinner) findViewById(R.id.SpinnerFeedbackType);
                String feedbackType = feedbackSpinner.getSelectedItem().toString();

                final CheckBox responseCheckbox = (CheckBox) findViewById(R.id.CheckBoxResponse);
                boolean bRequiresResponse = responseCheckbox.isChecked();

            }
        });
    }
}
