package lib.SMS;
// The sendsms class can be used to send an SMS message via NowSMS.
//
// For additional information, please see http://blog.nowsms.com/2009/02/nowsms-java-example-send-sms-message.html
// 
// This class supports most of the URL parameters that are defined for NowSMS at the following link
// (plus additional parameters that have been added since that in newer releases of NowSMS):
//
// http://www.nowsms.com/documentation/ProductDocumentation/sending_messages/url_parameters_for_sending_messages.htm
//
// URL parameters are supported as methods for the sendsms class, with method names in all lower case.
//
// The init method initialises the SMS message object.
//
// The server method sets the URL address for the NowSMS server.
//
// The user and pass methods set the name of the user account ("SMS Users") on the NowSMS server.
// 
// The phonenumber method sets the recipient phone number (can be a comma delimited list or distribution list name).
//
// The text method sets the text of the SMS message.
//
// The send method submits the SMS message to NowSMS.
//
// The send method returns a list of message ids assigned for the submitted messages, with one message per line, in the following format:
//
//    MessageID=xxxxxxxxxxxxxx.req, Recipient=xxxxxxxxxxx
// 
//
// Example - Sending a simple text message:
//
//      sendsms.init();
//      sendsms.server = "http://127.0.0.1:8800/";
//      sendsms.user = "test";
//      sendsms.password = "test";
//      sendsms.phonenumber = "+9999999999";
//      sendsms.text = "This is a test message";
//      sendsms.send();
//
// Example - Sending a text message to a specific application port for a Java applet running on the phone:
//
//      sendsms.init();
//      sendsms.server = "http://127.0.0.1:8800/";
//      sendsms.user = "test";
//      sendsms.password = "test";
//      sendsms.phonenumber = "+9999999999";
//      sendsms.text = "This is a test message";
//      sendsms.destport = "9999";
//      sendsms.send();
//
// Example - Sending a WAP Push Message:
// 
//      sendsms.init();
//      sendsms.server = "http://127.0.0.1:8800/";
//      sendsms.user = "test";
//      sendsms.password = "test";
//      sendsms.phonenumber = "+9999999999";
//      sendsms.text = "This is a test message";
//      sendsms.wapurl = "http://www.nowsms.com/";
//      sendsms.send();
//

import java.net.*; 
import java.io.*; 

import org.zkoss.zk.ui.util.Clients;

public class sendsms {
 

    public static String server;
    public static String user;
    public static String password;
    public static String phonenumber;
    public static String text;
    public static String data;
    public static String udh;
    public static String pid;
    public static String dcs;
    public static String sender;
    public static String validity;
    public static String servicetype;
    public static String smscroute;
    public static String receiptrequested;
    public static String sourceport;
    public static String destport;
    public static String delayuntil;
    public static String voicemail;
    public static String wapurl;
    public static String wapsl;

    public static String url_str;
    
    

    public static void init () {
        server = null;
        user = null;
        password = null;
        phonenumber = null;
        text = null;
        data = null;
        udh = null;
        pid = null;
        dcs = null;
        sender = null;
        validity = null;
        servicetype = null;
        smscroute = null;
        receiptrequested = null;
        sourceport = null;
        destport = null;
        delayuntil = null;
        voicemail = null;
        wapurl = null;
        wapsl = null;
    }

    
    
    public static void setvar (String argname, String argvalue) {

       if (argname != null) {
          if (argvalue != null) {
             url_str = url_str + "&" + argname + "=";
             try {
                String encoded = URLEncoder.encode (argvalue, "UTF-8");
                url_str = url_str + encoded;
             }
             catch (UnsupportedEncodingException e) {
                url_str = url_str + argvalue;
             }
          }
       }
           
    }
    
    
    
    

    public static String send () {

    	String manuel = null;
       String returnstring;
   
       returnstring = null;

       if (server == null) {
  	  System.out.println("sendsms.server value not set");
          return returnstring;
       }

       url_str = server + "?";
       setvar("user", user);
       setvar("password", password);
       setvar("phonenumber", phonenumber);
       setvar("text", text);
       setvar("data", data);
       setvar("udh", udh);
       setvar("pid", pid);
       setvar("dcs", dcs);
       setvar("sender", sender);
       setvar("validity", validity);
       setvar("servicetype", servicetype);
       setvar("smscroute", smscroute);
       setvar("receiptrequested", receiptrequested);
       setvar("sourceport", sourceport);
       setvar("destport", destport);
       setvar("delayuntil", delayuntil);
       setvar("voicemail", voicemail);
       setvar("wapurl", wapurl);
       setvar("wapsl", wapsl);

       try {
          URL url2=new URL(url_str); 

          HttpURLConnection connection = (HttpURLConnection) url2.openConnection(); 
          connection.setDoOutput(false); 
          connection.setDoInput(true); 

          String res=connection.getResponseMessage(); 
          manuel=res;
          System.out.println("Response Code  ->"+res); 
	
          int code = connection.getResponseCode () ; 
		
          if ( code == HttpURLConnection.HTTP_OK ) {
             //Get response data.
             BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

             String str;
        
             while( null != ((str = in.readLine()))) {
                if (str.startsWith("MessageID=")) {  
                   returnstring = returnstring + str + "\r\n";
                   System.out.println(str);
                }
             }    
             connection.disconnect() ; 
          }
       }
       catch(IOException e) {
          System.out.println("unable to create new url"+e.getMessage());
       }
       //return returnstring;
       return manuel;
   }
    
    
    
    //inseri apenas o segunte script
    public static boolean mandarSMS(String number, String texto){   
    	try{
        sendsms.init();
        sendsms.server = "http://127.0.0.1:7070/";
        sendsms.user = "test";
        sendsms.password = "test";
        sendsms.phonenumber = number;
        sendsms.text = texto;
        String str=sendsms.send();
        if (str.compareToIgnoreCase("ok")==0)
        	return true;
        else
        	return false;
    	}catch(Exception ex){
    		return false;
    	}
    }  
}
