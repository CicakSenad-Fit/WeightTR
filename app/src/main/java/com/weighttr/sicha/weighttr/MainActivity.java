package com.weighttr.sicha.weighttr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import control.XmlReaderWriter;
import model.User;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    private static MainActivity instance = new MainActivity();
    private static Context context;
    private ArrayList<User> users = new ArrayList<User>();

    public static MainActivity getInstance()
    {
        return instance;
    }

    public static Context getContext()
    {
        return MainActivity.context;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.context = getApplicationContext();

        setContentView(R.layout.activity_main);
        String dirPath = getFilesDir().getAbsolutePath() + File.separator + "weightStorage";

        if (DoesFolderExist(dirPath))
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
            LinearLayout mainLayout = (LinearLayout)findViewById(R.id.tbly_mainLayout);

            if (!users.isEmpty())
                _users = users;

            if (_users.size() > 0)
            {
                for (int i = 0; i < _users.size(); i++)
                {
                    TableRow tmpTableRow = new TableRow(this);
                    tmpTableRow.setId(_users.get(i).getId());
                    tmpTableRow.setOnClickListener(this);

                    LinearLayout tmpLinearLayout = new LinearLayout(this);
                    tmpLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

                    tmpTableRow.setPadding(0, 30, 0, 30);

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

                    tmpLinearLayout.addView(imgUserAvatar);
                    tmpLinearLayout.addView(txtViewUserName);

                    tmpTableRow.addView(tmpLinearLayout);
                    mainLayout.addView(tmpTableRow);
                }
            }
            else
            {
                //Create New User Button
                RelativeLayout rl_createNewUser = new RelativeLayout(this);

                //converting int into pixels
                //http://stackoverflow.com/questions/6798867/android-how-to-programmatically-set-the-size-of-a-layout
                rl_createNewUser.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics())));

                Button btn_createNewUser = new Button(this);
                btn_createNewUser.setId(R.id.btnCreateNewUser);
                btn_createNewUser.setText(R.string.btnCreateNewUser);
                btn_createNewUser.setBackgroundResource(R.drawable.my_button);
                btn_createNewUser.setTextColor(Color.BLACK);

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);

                params.setMargins(0, 0, 0, 70);

                btn_createNewUser.setLayoutParams(params);

                btn_createNewUser.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
                btn_createNewUser.getLayoutParams().width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180, getResources().getDisplayMetrics());

                btn_createNewUser.setOnClickListener(this);

                rl_createNewUser.addView(btn_createNewUser);
                mainLayout.addView(rl_createNewUser);


                //Create New User Button
                RelativeLayout rl_signIn = new RelativeLayout(this);

                //converting int into pixels
                //http://stackoverflow.com/questions/6798867/android-how-to-programmatically-set-the-size-of-a-layout
                rl_signIn.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics())));

                Button btn_signIn = new Button(this);
                btn_signIn.setId(R.id.btnSignIn);
                btn_signIn.setText(R.string.btnSignIn);
                btn_signIn.setBackgroundResource(R.drawable.my_button);
                btn_signIn.setTextColor(Color.BLACK);

                RelativeLayout.LayoutParams params_signIn = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params_signIn.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                params_signIn.addRule(RelativeLayout.CENTER_HORIZONTAL);

                params_signIn.setMargins(0, 70, 0, 0);

                btn_createNewUser.setLayoutParams(params);

                btn_signIn.setLayoutParams(params_signIn);

                btn_signIn.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
                btn_signIn.getLayoutParams().width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180, getResources().getDisplayMetrics());

                btn_signIn.setOnClickListener(this);

                rl_signIn.addView(btn_signIn);
                mainLayout.addView(rl_signIn);
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

    private boolean DoesFolderExist(String path)
    {
        File f = new File(Environment.getExternalStorageDirectory() + path);
        if(f.isDirectory()) {
            return true;
        }

        return false;
    }

    private void LoadUsers()
    {
        XmlPullParserFactory pullParserFactory;
        XmlReaderWriter xmlReader = new XmlReaderWriter(getUsers(), getApplicationContext());

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

    @Override
    public void onClick(View view)
    {
        User _userToSend = new User();
        Intent intent;

        switch (view.getId())
        {
            case R.id.btnCreateNewUser:
                intent = new Intent(getApplicationContext(), CreateNewUserActivity.class);
                intent.putExtra("allUsers", users);
                break;
            case R.id.btnSignIn:
                intent = new Intent(getApplicationContext(), SignInActivity.class);
                intent.putExtra("allUsers", users);
                break;
            default:
                int userId = view.getId();

                for (int i=0; i < users.size(); i++)
                {
                    if (users.get(i).getId() == userId)
                        _userToSend = users.get(i);
                }

                intent = new Intent(getApplicationContext(), ProfilePageActivity.class);
                intent.putExtra("user", _userToSend);
                break;
        }

        startActivity(intent);
    }


}
