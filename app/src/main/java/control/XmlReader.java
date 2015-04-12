package control;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import model.User;

/**
 * Created by Sicha on 4/8/2015.
 */
public class XmlReader {
    public volatile boolean parsingComplete = true;
    private ArrayList<User> _users = new ArrayList<User>();

    public XmlReader(ArrayList<User> users) {
        _users = users;
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

    private String createXml(List<User> users){
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

    public void writeXML() {

        try {
            FileOutputStream fileOutputStream = new FileOutputStream("localStorage.xml");
        }
        catch (IOException e) {

        }
    }
}
