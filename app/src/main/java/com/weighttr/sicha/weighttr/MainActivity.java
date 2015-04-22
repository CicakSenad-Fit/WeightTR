package com.weighttr.sicha.weighttr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.util.Log;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import control.XmlReaderWriter;
import model.User;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    public final static String APP_STORAGE_FILE_NAME = "localStorage.xml";

    private static MainActivity instance = new MainActivity();
    private static Context context;
    private ArrayList<User> users = new ArrayList<>();

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
        String dirPath = MainActivity.getContext().getFilesDir() + "/weightTR/Storage/";

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
        File configFile = new File("res/config.kne");

        if (DoesFileExist(configFile))
        {

        }
        else
        {
            //TODO: Create config.kne
        }

        try {
            //ScrollView usersContent = (ScrollView)findViewById(R.id.scrlVwUsersContent);
            RelativeLayout rl_main_view = (RelativeLayout)findViewById(R.id.rl_main_view);

            if (users.size() > 0)
            {
                ScrollView usersContent = new ScrollView(this);
                usersContent.setId(R.id.scrlVwUsersContent);
                RelativeLayout.LayoutParams scrollView_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                scrollView_params.addRule(RelativeLayout.ALIGN_PARENT_START);
                scrollView_params.addRule(RelativeLayout.BELOW, R.id.seperator_top);
                usersContent.setLayoutParams(scrollView_params);

                RelativeLayout footerLayout = (RelativeLayout)findViewById(R.id.rl_footerLayout);
                footerLayout.setVisibility(RelativeLayout.VISIBLE);

                TableLayout usersTableLayout = new TableLayout(context);
                usersTableLayout.setId(R.id.tbly_users_content);

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.BELOW, R.id.seperator_top);
                params.addRule(RelativeLayout.ALIGN_PARENT_START);
                usersTableLayout.setLayoutParams(params);

                usersContent.addView(usersTableLayout);
                rl_main_view.addView(usersContent);
                RefreshUserList();
            }
            else
            {
                //If scroll view with user is visible hide it to prevent interfering with buttons
                ScrollView usersContent = (ScrollView)findViewById(R.id.scrlVwUsersContent);
                if (usersContent.getVisibility() == View.VISIBLE)
                    usersContent.setVisibility(View.GONE);

                //Since button to create new user is already added we can hide the one in footer.
                RelativeLayout footerLayout = (RelativeLayout)findViewById(R.id.rl_footerLayout);
                footerLayout.setVisibility(RelativeLayout.GONE);

                RelativeLayout rl_buttonsContainer = new RelativeLayout(this);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                params.addRule(RelativeLayout.CENTER_VERTICAL);
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);

                rl_buttonsContainer.setLayoutParams(params);


                //Create New User Button
                Button btn_createNewUser = new Button(this);
                btn_createNewUser.setId(R.id.btnCreateNewUser);
                btn_createNewUser.setText(R.string.title_activity_create_new_user);
                btn_createNewUser.setBackgroundResource(R.drawable.my_button);
                btn_createNewUser.setTextColor(Color.BLACK);

                RelativeLayout.LayoutParams params_create_user = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params_create_user.addRule(RelativeLayout.BELOW, R.id.seperator_top);
                params_create_user.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                params_create_user.addRule(RelativeLayout.CENTER_HORIZONTAL);
                params_create_user.addRule(RelativeLayout.CENTER_VERTICAL);


                params_create_user.setMargins(0, 600, 0, 0);

                btn_createNewUser.setLayoutParams(params_create_user);

                btn_createNewUser.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
                btn_createNewUser.getLayoutParams().width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180, getResources().getDisplayMetrics());

                btn_createNewUser.setOnClickListener(this);

                rl_buttonsContainer.addView(btn_createNewUser);


                //Create Sign In Button
                Button btn_signIn = new Button(this);
                btn_signIn.setId(R.id.btnSignIn);
                btn_signIn.setText(R.string.btnSignIn);
                btn_signIn.setBackgroundResource(R.drawable.my_button);
                btn_signIn.setTextColor(Color.BLACK);

                RelativeLayout.LayoutParams params_signIn = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params_create_user.addRule(RelativeLayout.BELOW, R.id.btnCreateNewUser);
                params_signIn.addRule(RelativeLayout.ALIGN_START, R.id.btnCreateNewUser);

                params_signIn.setMargins(0, 800, 0, 0);

                btn_signIn.setLayoutParams(params);

                btn_signIn.setLayoutParams(params_signIn);

                btn_signIn.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
                btn_signIn.getLayoutParams().width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180, getResources().getDisplayMetrics());

                btn_signIn.setOnClickListener(this);

                rl_buttonsContainer.addView(btn_signIn);

                rl_main_view.addView(rl_buttonsContainer);
            }
        }
        catch (Exception e)
        {
            Log.e("Loading configuration probably users Array error! Error message: ", e.getMessage());
        }

    }

    private void RefreshUserList()
    {
        TableLayout usersTableLayout = (TableLayout)findViewById(R.id.tbly_users_content);

        if (usersTableLayout.getChildCount() > 0)
            usersTableLayout.removeAllViews();

        for (int i = 0; i < users.size(); i++)
        {
            TableRow tmpTableRow = new TableRow(this);
            tmpTableRow.setId(users.get(i).getId());
            tmpTableRow.setOnClickListener(this);

            LinearLayout tmpLinearLayout = new LinearLayout(this);
            tmpLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

            tmpTableRow.setPadding(0, 30, 0, 30);

            ImageView imgUserAvatar = new ImageView(this);

            if (users.get(i).getSex())
                imgUserAvatar.setImageResource(R.drawable.user_blue);
            else
                imgUserAvatar.setImageResource(R.drawable.user_red);

            imgUserAvatar.setPadding(25, 0, 25, 0);

            TextView txtViewUserName = new TextView(this);
            txtViewUserName.setText(users.get(i).getUsername());
            txtViewUserName.setTextColor(Color.WHITE);

            txtViewUserName.setGravity(Gravity.CENTER_VERTICAL);
            txtViewUserName.setPadding(25, 45, 0, 0);

            tmpLinearLayout.addView(imgUserAvatar);
            tmpLinearLayout.addView(txtViewUserName);

            //registering View for context menu (long click)
            //http://stackoverflow.com/questions/17207366/creating-a-menu-after-a-long-click-event-on-a-list-view
            registerForContextMenu(tmpTableRow);

            tmpTableRow.addView(tmpLinearLayout);
            usersTableLayout.addView(tmpTableRow);
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
        File f = new File(path);
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

            InputStream in_s = new FileInputStream(MainActivity.getContext().getFilesDir() + "/weightTR/Storage/" + APP_STORAGE_FILE_NAME);
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
            case R.id.btnCreateUser:
            {
                intent = new Intent(getApplicationContext(), CreateNewUserActivity.class);
                intent.putExtra("allUsers", users);
                break;
            }
            case R.id.btnSignIn:
            {
                intent = new Intent(getApplicationContext(), SignInActivity.class);
                intent.putExtra("allUsers", users);
                break;
            }
            default:
            {
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
        }

        startActivity(intent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("User: " + users.get(v.getId() - 1).getUsername());
        menu.add(R.id.context_menu_edit, v.getId(), 0, "Edit");
        menu.add(R.id.context_menu_remove, v.getId(), 0, "Remove");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getGroupId()) {
            case R.id.context_menu_edit:
                Intent intent = new Intent(getApplicationContext(), EditUserActivity.class);
                intent.putExtra("user", users.get(item.getItemId() - 1));
                return true;
            case R.id.context_menu_remove:
                Toast.makeText(context, "Removing: '" + users.get(item.getItemId() - 1).getUsername() + "'!...", Toast.LENGTH_SHORT).show();
                users.remove(item.getItemId() - 1);

                XmlReaderWriter xmlWriter = new XmlReaderWriter(users, context);
                xmlWriter.writeXML(users);

                RefreshUserList();
                LoadConfig();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
