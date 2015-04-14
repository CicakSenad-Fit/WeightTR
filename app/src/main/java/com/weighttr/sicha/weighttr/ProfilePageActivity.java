package com.weighttr.sicha.weighttr;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

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
                LinearLayout mainLayout = (LinearLayout)findViewById(R.id.lnrlyt_profilePageLayout);

                LinearLayout _ll_username = new LinearLayout(this);
                TableRow _tr_username = new TableRow(this);
                TextView _tv_username_label = new TextView(this);
                TextView _tv_username = new TextView(this);

                _ll_username.setOrientation(LinearLayout.HORIZONTAL);

                _tr_username.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

                _tv_username_label.setTextAppearance(this, R.style.Base_TextAppearance_AppCompat_Large);
                _tv_username_label.setTextColor(Color.WHITE);
                _tv_username_label.setText("User name: ");

                _tv_username.setTextAppearance(this, R.style.Base_TextAppearance_AppCompat_Large);
                _tv_username.setTextColor(Color.WHITE);
                _tv_username.setText(_selectedUser.getUsername());

                _ll_username.addView(_tv_username_label);
                _ll_username.addView(_tv_username);

                _tr_username.addView(_ll_username);
                //-------------------------------------------------
                mainLayout.addView(_tr_username);
                //-------------------------------------------------

                LinearLayout _ll_age = new LinearLayout(this);
                TableRow _tr_age = new TableRow(this);
                TextView _tv_age_label = new TextView(this);
                TextView _tv_age = new TextView(this);

                _ll_age.setOrientation(LinearLayout.HORIZONTAL);

                _tr_age.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

                _tv_age_label.setTextAppearance(this, R.style.Base_TextAppearance_AppCompat_Large);
                _tv_age_label.setTextColor(Color.WHITE);
                _tv_age_label.setText("Age: ");

                _tv_age.setTextAppearance(this, R.style.Base_TextAppearance_AppCompat_Large);
                _tv_age.setTextColor(Color.WHITE);
                _tv_age.setText(String.valueOf(_selectedUser.getAge()));

                _ll_age.addView(_tv_age_label);
                _ll_age.addView(_tv_age);

                _tr_age.addView(_ll_age);
                //-------------------------------------------------
                mainLayout.addView(_tr_age);
                //-------------------------------------------------

                LinearLayout _ll_height = new LinearLayout(this);
                TableRow _tr_height = new TableRow(this);
                TextView _tv_height_label = new TextView(this);
                TextView _tv_height = new TextView(this);

                _ll_height.setOrientation(LinearLayout.HORIZONTAL);

                _tr_height.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

                _tv_height_label.setTextAppearance(this, R.style.Base_TextAppearance_AppCompat_Large);
                _tv_height_label.setTextColor(Color.WHITE);
                _tv_height_label.setText("Height: ");

                _tv_height.setTextAppearance(this, R.style.Base_TextAppearance_AppCompat_Large);
                _tv_height.setTextColor(Color.WHITE);
                _tv_height.setText(String.valueOf(_selectedUser.getHeight()));

                _ll_height.addView(_tv_height_label);
                _ll_height.addView(_tv_height);

                _tr_height.addView(_ll_height);
                //-------------------------------------------------
                mainLayout.addView(_tr_height);
                //-------------------------------------------------
            }
            catch (Exception e)
            {
                Log.e("Error accessing user data! Error message: ", e.getMessage());
            }
        }
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
