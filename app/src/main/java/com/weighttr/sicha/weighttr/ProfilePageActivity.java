package com.weighttr.sicha.weighttr;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import model.MeasureRecord;
import model.User;


public class ProfilePageActivity extends ActionBarActivity {

    private User _selectedUser = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        Intent receivedIntent = getIntent();
        _selectedUser = (User)receivedIntent.getSerializableExtra("user");

        if (_selectedUser.getId() > 0)
        {
            try {
                TextView _lblUserName = (TextView)findViewById(R.id.lblProfilePageName);
                _lblUserName.setText("Name: " + _selectedUser.getUsername());

                //-------------------------------------------------

                TextView _lblAge = (TextView)findViewById(R.id.lblProfilePageAge);
                _lblAge.setText("Age: " + String.valueOf(_selectedUser.getAge()));

                //-------------------------------------------------

                TextView _lblHeight = (TextView)findViewById(R.id.lblProfilePageHeight);
                _lblHeight.setText("Height: " + String.valueOf(_selectedUser.getHeight()));

                //-------------------------------------------------
            }
            catch (Exception e)
            {
                Log.e("Error accessing user data! Error message: ", e.getMessage());
            }
        }

        if (_selectedUser.getMeasuresLenght() > 0)
        {
            ArrayList<MeasureRecord> userMeasures = _selectedUser.getMeasures();

            ScrollView measuresContent = (ScrollView)findViewById(R.id.sv_profilePageContent);

            TableLayout measuresTableLayout = new TableLayout(this);
            measuresTableLayout.setId(R.id.tbly_measures_content);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_START);
            measuresTableLayout.setLayoutParams(params);

            measuresContent.addView(measuresTableLayout);

            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyy.");

            for (int i=0; i < userMeasures.size(); i++)
            {
                LinearLayout tmpLinearLayout = new LinearLayout(this);
                tmpLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

                TableRow tmpTableRow = new TableRow(this);
                TextView txtMeasureDate = new TextView(this);
                TextView txtValue = new TextView(this);

                tmpTableRow.setId(i + 1);
                tmpTableRow.setPadding(0, 30, 0, 30);

                txtMeasureDate.setTextAppearance(this, android.R.style.TextAppearance_Large);
                txtMeasureDate.setText(sdf.format(userMeasures.get(i).getDate()) + ": ");
                txtMeasureDate.setTextColor(Color.WHITE);
                txtMeasureDate.setGravity(Gravity.CENTER_VERTICAL);
                txtMeasureDate.setPadding(25, 5, 0, 0);
                tmpLinearLayout.addView(txtMeasureDate);

                txtValue.setTextAppearance(this, android.R.style.TextAppearance_Large);
                txtValue.setText(String.valueOf(userMeasures.get(i).getValue()));
                txtValue.setTextColor(Color.WHITE);
                txtValue.setGravity(Gravity.CENTER_VERTICAL);
                txtValue.setPadding(25, 5, 0, 0);
                tmpLinearLayout.addView(txtValue);

                tmpTableRow.addView(tmpLinearLayout);
                measuresTableLayout.addView(tmpTableRow);
            }
        }
    }

    public void onAddMeasureButtonClicked(View view)
    {
        Intent intent = new Intent(getApplicationContext(), AddMeasureActivity.class);
        intent.putExtra("user", _selectedUser);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_acvty_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
