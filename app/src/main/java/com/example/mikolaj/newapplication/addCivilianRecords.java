package com.example.mikolaj.newapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Tomek on 2018-02-06.
 */

public class addCivilianRecords extends AppCompatActivity implements AsyncResponse, View.OnClickListener {

    EditText etDescription;
    Spinner spReportID, spCivilID, spCivilStatus;
    Button btnLogin;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "user_name";
    public static final String Name = "nameKey";
    public static String sDescription, sReportID, sCivilID, sCivilStatus;
    public static myAccount account;
    public static Integer[] civilianIDs;//List<Integer>  civilianIDs = new ArrayList<>();
    public static Integer[] reportIDs;//List<Integer>  reportIDs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getReportIDs();
        getCivilIDs();
        setContentView(R.layout.activity_add_civil);
        etDescription = (EditText) findViewById(R.id.rcr_description);
        spCivilID = (Spinner) findViewById(R.id.civilian_id);
        spReportID = (Spinner) findViewById(R.id.reportId);
        spCivilStatus = (Spinner) findViewById(R.id.civilian_status);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        //Adapters
        ArrayAdapter<Integer> civilID =
                new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, civilianIDs);
        spCivilID.setAdapter(civilID);

        ArrayAdapter<Integer> reportID =
                new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, reportIDs);
        spReportID.setAdapter(reportID);
    }

    @Override
    public void processFinish(String result) {
        if (result.equals("success")) {
            Toast.makeText(this, "Register Successfully", Toast.LENGTH_LONG).show();
            Intent in = new Intent(this, userMenu.class);
            sCivilID = spCivilID.getSelectedItem().toString();
            sReportID = spReportID.getSelectedItem().toString();
            sCivilStatus = spCivilStatus.getSelectedItem().toString();
            sDescription = etDescription.getText().toString();

            DownloadDataBase.reportCivilianRecords.add(new ReportCivilianRecords(DownloadDataBase.reportCivilianRecords
                    .get(DownloadDataBase.reportCivilianRecords.size()-1).getRcrID()+1,
                    Integer.parseInt(sReportID),Integer.parseInt(sCivilID),sCivilStatus,sDescription));

            startActivity(in);
        } else {
            Toast.makeText(this, "Register Failed", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onClick(View view) {
        HashMap postData = new HashMap();
        //postData.put("mobile", "android");
        postData.put("txtReportID", spReportID.getSelectedItem().toString());
        postData.put("txtCivilianID", spCivilID.getSelectedItem().toString());
        postData.put("txtCivilianStatus", spCivilStatus.getSelectedItem().toString());
        postData.put("txtDescription", etDescription.getText().toString());

        PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
        task.execute("http://wilki.kylos.pl/PSI/_registerCivilianRecords.php");
    }

    public void login(View view) {
    }

    private void getCivilIDs()
    {
        civilianIDs = new Integer[DownloadDataBase.civilians.size()];
        for(int i=0; i<DownloadDataBase.civilians.size(); i++)
        {
            civilianIDs[i]=DownloadDataBase.civilians.get(i).getCivilianID();
        }
    }

    private void getReportIDs()
    {
        reportIDs = new Integer[DownloadDataBase.offenses.size()];
        for(int i=0; i<DownloadDataBase.offenses.size(); i++)
        {
            reportIDs[i]= DownloadDataBase.offenses.get(i).getOffenseId();
        }
    }
}
