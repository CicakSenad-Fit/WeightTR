package com.weighttr.sicha.weighttr;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import control.XmlReaderWriter;
import model.User;


public class CreateNewUserActivity extends ActionBarActivity {

    ArrayList<User> _users = new ArrayList<User>();

    String userName;
    String password;
    RadioButton gender;
    NumberPicker numberPickerAge;
    NumberPicker numberPickerHeight;
    Button createButton;

    User newUser = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_user);

        Intent receivedIntent = getIntent();
        _users = (ArrayList<User>)receivedIntent.getSerializableExtra("allUsers");

        numberPickerAge = (NumberPicker) findViewById(R.id.npAge);
        numberPickerHeight = (NumberPicker) findViewById(R.id.npHeight);

        numberPickerAge.setMinValue(4);
        numberPickerAge.setMaxValue(70);
        numberPickerAge.setWrapSelectorWheel(false);

        numberPickerAge.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                newUser.setAge(newVal);
            }
        });

        numberPickerHeight.setMinValue(150);
        numberPickerHeight.setMaxValue(210);
        numberPickerHeight.setWrapSelectorWheel(false);

        numberPickerHeight.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                newUser.setHeight(newVal);
            }
        });

        createButton = (Button) findViewById(R.id.btnCreateUser);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView userNameTextView = (TextView) findViewById(R.id.txtNewUser_UserName);
                userName = userNameTextView.getText().toString();
                newUser.setUsername(userName);

                TextView passwordTextView = (TextView) findViewById(R.id.txtNewUser_Password);
                password = passwordTextView.getText().toString();
                newUser.setPass(password);

                gender = (RadioButton) findViewById(R.id.rbtn_male);
                newUser.setSex(gender.isChecked());

                _users.add(newUser);

                XmlReaderWriter xmlWriter = new XmlReaderWriter(_users);
                xmlWriter.writeXML(_users);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_new_user, menu);
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

    public void CreateNewUser()
    {

    }
}
