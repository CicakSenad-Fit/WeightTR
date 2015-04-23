package com.weighttr.sicha.weighttr;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;

import java.util.Date;

import model.MeasureRecord;
import model.User;


public class AddMeasureActivity extends ActionBarActivity {

    private User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_measure);

        Intent receivedIntent = getIntent();
        user = (User)receivedIntent.getSerializableExtra("user");

        NumberPicker _npWeight = (NumberPicker)findViewById(R.id.npAddMeasureWeight);

        _npWeight.setMinValue(30);
        _npWeight.setMaxValue(150);
        _npWeight.setValue(80);
        _npWeight.setWrapSelectorWheel(false);
    }

    public void onClickAddMeasureConfirm(View view)
    {
        MeasureRecord _newMeasurerecord = new MeasureRecord();
        NumberPicker _npWeight = (NumberPicker)findViewById(R.id.npAddMeasureWeight);

        _newMeasurerecord.setId(user.getMeasuresLenght() + 1);
        _newMeasurerecord.setDate(new Date());
        _newMeasurerecord.setValue(_npWeight.getValue());

        user.addNewMeasure(_newMeasurerecord);

        //TODO: Write it to xml!!!

        Intent intent = new Intent(getApplicationContext(), ProfilePageActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_measure, menu);
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
