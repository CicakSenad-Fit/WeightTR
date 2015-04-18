package control;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import com.weighttr.sicha.weighttr.MainActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import model.User;

/**
 * Created by Sicha on 4/8/2015.
 */
public class XmlReaderWriter {
    private Context _context;
    private ArrayList<User> _users = new ArrayList<User>();

    public volatile boolean parsingComplete = true;

    public XmlReaderWriter(ArrayList<User> users, Context context)
    {
        _users = users;
        _context = context;
    }

    public void readXML()
    {
        XmlPullParserFactory pullParserFactory;

        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();
        }
        catch (XmlPullParserException e) {

            e.printStackTrace();
        }
    }

    public void parseXML(XmlPullParser myParser) throws XmlPullParserException,IOException
    {
        int event;
        String text=null; //If xml contains text not "parameters"

        try {
            event = myParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                User currentUser = new User();
                String name=myParser.getName();

                switch (event){
                    case XmlPullParser.START_TAG:
                        break;
                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if(name.equals("user")){
                            currentUser.setId(Integer.parseInt(myParser.getAttributeValue(null,"id")));
                            currentUser.setUsername(myParser.getAttributeValue(null, "username"));
                            currentUser.setPass(myParser.getAttributeValue(null, "pass"));
                            currentUser.setAge(Integer.parseInt(myParser.getAttributeValue(null, "age")));
                            currentUser.setHeight(Integer.parseInt(myParser.getAttributeValue(null, "height")));

                            if (myParser.getAttributeValue(null, "sex").equals("M"))
                                currentUser.setSex(true);
                            else
                                currentUser.setSex(false);

                            if (!currentUser.getUsername().equals("JohnDoe"))
                                _users.add(currentUser);
                        }
                        else{
                        }
                        break;
                }
                event = myParser.next();

            }
            parsingComplete = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String createXml(ArrayList<User> users)
    {
        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();

        try {
            serializer.setOutput(writer);
            serializer.startDocument("UTF-8", true);
            serializer.startTag("", "localStorage");
            serializer.startTag("", "users");
            for (User user: users){
                serializer.startTag("", "user");
                serializer.attribute("", "id", Integer.toString(user.getId()));
                serializer.attribute("", "username", user.getUsername());
                serializer.attribute("", "pass", user.getPass());
                serializer.attribute("", "age", Integer.toString(user.getAge()));
                serializer.attribute("", "sex", user.getSex() ? "M" : "F");
                serializer.attribute("", "height", Integer.toString(user.getHeight()));
                serializer.endTag("", "user");
            }
            serializer.endTag("", "users");
            serializer.endTag("", "localStorage");
            serializer.endDocument();

            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void writeXML(ArrayList<User> users) {

        try {
            if (users.size() > 0)
                writeToFile(createXml(users));
        }
        catch (Exception e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private void writeToFile(String dataToWrite) {
        String xmlFile = MainActivity.APP_STORAGE_FILE_NAME;

        String dirPath = MainActivity.getContext().getFilesDir() + "/weightTR/Storage/";
        File projDir = new File(dirPath);

        if (!projDir.exists())
            projDir.mkdirs();

        try {
            File file = new File(dirPath, MainActivity.APP_STORAGE_FILE_NAME);
            file.createNewFile();

            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);

            myOutWriter.append(dataToWrite);
            myOutWriter.close();

            Toast.makeText(_context, "Done writig SD 'localStorage.xml'", Toast.LENGTH_SHORT).show();
        }
        catch (IOException e) {
            Toast.makeText(_context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
