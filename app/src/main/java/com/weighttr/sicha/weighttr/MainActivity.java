package com.weighttr.sicha.weighttr;

import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import control.XmlReader;
import model.User;


public class MainActivity extends ActionBarActivity {
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    private ArrayList<User> users = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoadUsers();
        LoadConfig();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void sendMessage(View view) {
//        Intent intent = new Intent(this, DisplayMessageActivity.class);
//        EditText editText = (EditText) findViewById(R.id.edit_message);
//        String message = editText.getText().toString();
//
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);
    }

    private void LoadConfig()
    {
        ArrayList<User> _users = new ArrayList<User>();
        File configFile = new File("res/config.kne");

        if (DoesFileExist(configFile))
        {

        }
        else
        {
            //TODO: Create config.kne
        }

        try {
            TableLayout tableLayout = (TableLayout)findViewById(R.id.tbly_mainLayout);
            tableLayout.setPadding(0, 50 , 0, 0);

            if (!users.isEmpty())
                _users = users;

            if (_users.size() > 0)
            {
                for (int i = 0; i < _users.size(); i++)
                {
                    TableRow tableRow = new TableRow(this);
                    tableRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 200));
                    tableRow.setPadding(0, 30, 0 , 30);

                    ImageView imgUserAvatar = new ImageView(this);

                    if (_users.get(i).getSex())
                        imgUserAvatar.setImageResource(R.drawable.user_blue);
                    else
                        imgUserAvatar.setImageResource(R.drawable.user_red);

                    imgUserAvatar.setPadding(25, 0, 25, 0);

                    TextView txtViewUserName = new TextView(this);
                    txtViewUserName.setText(_users.get(i).getUsername());
                    txtViewUserName.setTextColor(Color.WHITE);

                    txtViewUserName.setGravity(Gravity.CENTER_VERTICAL);
                    txtViewUserName.setPadding(25, 45, 0, 0);

                    tableRow.addView(imgUserAvatar);
                    tableRow.addView(txtViewUserName);

                    tableLayout.addView(tableRow);
                }
            }
        }
        catch (Exception e)
        {
            Log.e("Loading configuration probably users Array error! Error message: ", e.getMessage());
        }


    }

    private boolean DoesFileExist(File file) {
        if(file.exists()) {
            return true;
        }
        else return false;
    }

    private void LoadUsers()
    {
        XmlPullParserFactory pullParserFactory;
        XmlReader xmlReader = new XmlReader(getUsers());

        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            InputStream in_s = getApplicationContext().getAssets().open("localStorage.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);

            xmlReader.parseXML(parser);

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
