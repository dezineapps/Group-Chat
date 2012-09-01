package com.mustafa.testmulti;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TestmultiActivity extends Activity {
    /** Called when the activity is first created. */
	TextView statusmsg;
	Button testcase1;
	Button testcase2;
	Button connect;
	EditText editwindow;
	public static String msg=null;
	public static String messg;
	public static Socket cliesock = null;
	public static String phonenumber;
	public static String phone;

	public static TelephonyManager teleman;
	public static ServerSocket servsock = null;

	public static Socket socket1;
	public static Socket socket2;
	public static Socket socket3;
	public static Socket socket4;
	public static Socket socket5;
	public static int index = 0;
	public static Socket socket_sequence;
	public static int type_message=0;
	public static ArrayList <Object> buffer_array = new ArrayList<Object>();
	public static int sequencer1 =0;
	public static int sequencer2 =0;
	public static int sequencer3 =0;
	public static int num_sequencer;
	public static String initiator;
	public static String phone_test2;
	public static int expected_sequence_number=0;	
	public static String message_sequencer = null;
	public static String phone_copy;
	public static String emulator5554 = "5554";
	public static String emulator5556 = "5556";
	public static String emulator5558 = "5558";
	public static int sequence_number = 0;
	public static int test2_counter = 0;
	public static String Ipaddress = "10.0.2.2";
	public final static int portnum1 = 11108;
	public final static int portnum2 = 11112;
	public final static int portnum3 = 11116;
	public final static int portnum4 = 11120;
	public final static int portnum5 = 11124;
	public static int flag_set=0;
	public static String phone_compare;
	Handler handle = new Handler();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    
        connect = (Button) findViewById(R.id.send);
        statusmsg = (TextView) findViewById(R.id.text1);
		testcase1 = (Button) findViewById(R.id.test1);
		testcase2 = (Button) findViewById(R.id.test2);
		editwindow = (EditText) findViewById(R.id.edit1);
    
		connect.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Thread multi = new Thread(new Multicast(),"send thread");
				multi.start();
			}
		});
		
    testcase1.setOnClickListener(new View.OnClickListener() {
		
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			Thread testing = new Thread(new testcase1(),"test case one thread");
    			testing.start();
    		}
		});
    
    testcase2.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
		Thread test_2 = new Thread(new testcase2(),"test case 2 thread");
		test_2.start();
		flag_set=1;
		}
	});
         
    Thread serverthread = new Thread(new Serverthr(),"server thread");
    serverthread.start();
    
    }
    	Runnable myHandler = new Runnable() {
		
    		public void run() 
    	{
    			// TODO Auto-generated method stub
			try{
				//statusmsg.append(sequence_number);
				statusmsg.append(phone);
				statusmsg.append(":");
				statusmsg.append(sequence_number + msg);
				//statusmsg.append(sequence_number);
				statusmsg.append("\n");
				Log.d("message","reached handler");
				}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
    	};

class Serverthr implements Runnable
{
	static final int server = 10000;

	public void run()
	{
		// TODO Auto-generated method stub
		try {
			servsock = new ServerSocket(server);
			synchronized(servsock)
			{
				Log.d("server listening ", "waiting");
				servsock.wait(1000*10);
				while(true)
				{			
					Log.d("client","before creating socket");
					cliesock = servsock.accept();
					if(cliesock!=null)
					{
						Log.d("accepted", "server accepted");
						Log.d("message","i reached here" + cliesock);
						
						InputStream is = cliesock.getInputStream();
						ObjectInputStream ois = new ObjectInputStream(is);
						Message tom = (Message)ois.readObject();
						
						Log.d("Server","the instance of tom contains "+tom.data);
						Log.d("Server","the integer of tom contains "+tom.value);
						Log.d("Server","the type of message received is"+tom.type);
				
						type_message=tom.type;
						sequence_number = tom.value;
						
						while(tom.type==0||tom.type==10)
						{
						msg = tom.data;
						phone = tom.phone;
						Log.d("buffer", "trying to buffer the message");
						buffer_array.add(tom);
						//Log.d("server","message instance is "+mess.data);
						index++;
						break;
						}
						
						while(tom.type==1)
						{
							message_sequencer=msg;
							Log.d("tom type","the tom type is 1");
							Multicast multi_seq = new Multicast();
							multi_seq.sequencer_run(msg);
							Log.d("sequencer multicast","trying to call messenger number");
							//handle.post(myHandler);
							break;
						}
						while(tom.type==2)
						{
							int i;
							Log.d("tom type ","reached where tom type is 2");
							for(i=0;i<buffer_array.size();i++)
							{
								Log.d("buffer array","size of buffer array is"+buffer_array.size());
								if(buffer_array.size()>0)
								{
								Message temp = (Message)buffer_array.get(i);
								if(expected_sequence_number==tom.value)
									{
									expected_sequence_number++;
									Log.d("tom type","the expected sequence number is recevied");
									handle.post(myHandler);
									buffer_array.remove(i);
									 String DATABASE_NAME = "SampleDB";
								     ContentResolver cr = getContentResolver();
								        
								     ContentValues contentValues = new ContentValues();
								     contentValues.put(Mustafadatabase.provider_key, phone);
						             contentValues.put(Mustafadatabase.provider_value, msg);
								   
						             cr.insert(Mustafaprovider.CONTENT_URI, contentValues);
						             Log.d("after","inserting");
						         
						             /*this piece of code is just to check whether it is getting inserted or not in the content provider */
								      /*  Mustafadatabase db = new Mustafadatabase(getBaseContext(), DATABASE_NAME);
								        
								        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
								        
								        builder.setTables(Mustafadatabase.TABLE_MESSENGER);
								        
								        SQLiteDatabase dqf = db.getReadableDatabase();
								     
								        String projection[] = new String[]
								        {
								        	Mustafadatabase.ID,Mustafadatabase.COL_TITLE,Mustafadatabase.COL_URL
								        };
								        
								        Cursor cursor = builder.query(dqf, projection, Mustafadatabase.COL_TITLE + "=" + "'title'", null, null, null, null);         
								        if(cursor!=null)
								        {
								        	Log.d("sslgn","laude nahi lage");
								        }
									*/
									}
								}
							}
							Log.d("inside ","reached here");
							//handle.post(myHandler);
							break;
						}
						while(tom.type==10)
						{
							teleman = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
							phone_compare = teleman.getLine1Number().substring(teleman.getLine1Number().length() - 4);
							//phone = phone_compare;
							int pho = Integer.parseInt(tom.phone);				
							Log.d("receiver","phone number for comparison is :"+ phone_compare);
							Log.d("sender","phone number of sender is :" + pho);
							Thread multicast1 = new Thread(new Multicast(),"multicast thread of 5556");
							
							switch(pho)
							{
							case 5554:
									Log.d("inside","inside the 5554 switch case");	
									if(phone_compare.equalsIgnoreCase("5556"))
									{
									Log.d("phone comparison == 5556","comparison between the mutlicasted messages");
									flag_set=1;
									multicast1.start();
									//Thread.sleep(3000);
									Log.d("flag_set","setting the flag to 1");		
									}
								break;
							
							case 5556:
								
								Log.d("inside ","inside the swithc case of 5556");
								if(phone_compare.equalsIgnoreCase("5558"))
								{
									Log.d("phone comparison == 5556","comparison between the mutlicasted messages");
									//Thread multicast2 = new Thread(new Multicast(),"multicast thread of 5558");
									flag_set=1;
									multicast1.start();
									//Thread.sleep(3000);
									Log.d("flag_set","setting the flag to 1");					
								}
								break;
								
							case 5558:
								
								if(phone_compare.equalsIgnoreCase("5560"))
								{
									Log.d("phone comparison == 5554","comparison between the mutlicasted messages");
									//Thread multicast3 = new Thread(new Multicast(),"multicast thread of 5558");
									flag_set=1;
									multicast1.start();
									//Thread.sleep(3000);
									Log.d("flag_set","setting the flag to 1");
									
								}
								break;
							
							case 5560:
								
								Log.d("inside ","inside the swithc case of 5560");
								if(phone_compare.equalsIgnoreCase("5562"))
								{
									Log.d("phone comparison == 5560","comparison between the mutlicasted messages");
									//Thread multicast4 = new Thread(new Multicast(),"multicast thread of 5558");
									flag_set=1;
									multicast1.start();
									//Thread.sleep(3000);
									Log.d("flag_set","setting the flag to 1");
								}
								break;
								
							case 5562:
								Log.d("inside ","inside the swithc case of 5562");
								if(phone_compare.equalsIgnoreCase("5554"))
								{
									Log.d("phone comparison == 5556","comparison between the mutlicasted messages");
									//Thread multicast5 = new Thread(new Multicast(),"multicast thread of 5558");
									flag_set=1;
									multicast1.start();
									//Thread.sleep(3000);
									Log.d("flag_set","setting the flag to 1");
								}
								break;
								
							default:
									Log.d("default", "inside the default case");
									break;
						}
							Log.d("switch","end of switch case");
						break;
						}	
						//sequence_number = tom.value;
						//BufferedReader clientdata = new BufferedReader(new InputStreamReader(cliesock.getInputStream()));
						Log.d("Reading","Reading the data");
						//msg= clientdata.readLine();
						//handle.post(myHandler);
						Log.d("message","did the message reach " + msg); 		
					}
					
				}
			}
		}catch(Exception er)
		{
			er.printStackTrace();
		}
	}
}


class Multicast implements Runnable
{

	public void run() {
		// TODO Auto-generated method stub
		try
		{
			
			type_message=0;
			socket1 = null;
			socket2 = null;
			socket3 = null;
			socket4 = null;
			socket5 = null;
			socket_sequence = null;
			
			teleman = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			phonenumber = teleman.getLine1Number().substring(teleman.getLine1Number().length() - 4);
			
		if(flag_set==1)
		{
			type_message=10;
			Thread.sleep(3000);
		}
			phone_copy = phonenumber;

				socket1 = new Socket("10.0.2.2",portnum1);
				Log.d("Socket","socket of emulator 5554 is connected at");
				
				socket2 = new Socket("10.0.2.2",portnum2);
				Log.d("Socket","socket of emulator 5556 is connected at");
				
				socket3 = new Socket("10.0.2.2",portnum3);
				Log.d("Socket","socket of emulator 5558 is connected at");
				socket4 = new Socket("10.0.2.2",portnum4);
				
				socket5 = new Socket("10.0.2.2",portnum5);
				
				socket_sequence = new Socket("10.0.2.2",portnum1);
				
			OutputStream os = socket1.getOutputStream();
			OutputStream os2 = socket2.getOutputStream();
			OutputStream os3 = socket3.getOutputStream();
			OutputStream os4 = socket4.getOutputStream();
			OutputStream os5 = socket5.getOutputStream();
			
			ObjectOutputStream oos = new ObjectOutputStream(os);
			ObjectOutputStream oos2 = new ObjectOutputStream(os2);
			ObjectOutputStream oos3 = new ObjectOutputStream(os3);
			ObjectOutputStream oos4 = new ObjectOutputStream(os4);
			ObjectOutputStream oos5 = new ObjectOutputStream(os5);
			
			messg = editwindow.getText().toString();
			
			Message to1 = new Message(0,messg,phonenumber,type_message);
			
			oos.writeObject(to1);
			oos2.writeObject(to1);
			oos3.writeObject(to1);
			oos4.writeObject(to1);
			oos5.writeObject(to1);
			
			OutputStream os6 = socket_sequence.getOutputStream();
			ObjectOutputStream oos6 = new ObjectOutputStream(os6);
			
			Message to6 = new Message(0,messg,phonenumber,1);

			Log.d("Socket","before writing to sequencer");
			oos6.writeObject(to6);
			Log.d("Socket","after writing to sequencer");
			
			flag_set=0;
			/*PrintWriter out;
			PrintWriter out2;
			PrintWriter out3;
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket1.getOutputStream())),true);
			out.println(messg);
			out2 = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket2.getOutputStream())),true);
			out2.println(messg);
			out3 = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket3.getOutputStream())),true);
			out3.println(messg);*/
			
			Log.d("Client", "Client sent message" +oos);

		}catch(Exception ert)
		{
			ert.printStackTrace();
		}
	}	

	public void sequencer_run(String seq_msg) {
		// TODO Auto-generated method stub
		try
		{		
			socket1 = null;
			socket2 = null;
			socket3 = null;
			socket4 = null;
			socket5 = null;
			final String phone_seq = "5554";
			
			teleman = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			phonenumber = teleman.getLine1Number().substring(teleman.getLine1Number().length() - 4);
			
			phone_copy = phonenumber;
				
				socket1 = new Socket("10.0.2.2",portnum1);
				Log.d("Socket","socket of emulator 5554 is connected at");
				socket2 = new Socket("10.0.2.2",portnum2);
				Log.d("Socket","socket of emulator 5556 is connected at");
				socket3 = new Socket("10.0.2.2",portnum3);
				Log.d("Socket","socket of emulator 5558 is connected at");
				socket4 = new Socket("10.0.2.2",portnum4);
				
				socket5 = new Socket("10.0.2.2",portnum5);
				Log.d("message","Message to be printed is");
			
			OutputStream os = socket1.getOutputStream();
			OutputStream os2 = socket2.getOutputStream();
			OutputStream os3 = socket3.getOutputStream();
			OutputStream os4 = socket4.getOutputStream();
			OutputStream os5 = socket5.getOutputStream();
			
			
			
			ObjectOutputStream oos = new ObjectOutputStream(os);
			ObjectOutputStream oos2 = new ObjectOutputStream(os2);
			ObjectOutputStream oos3 = new ObjectOutputStream(os3);
			ObjectOutputStream oos4 = new ObjectOutputStream(os4);
			ObjectOutputStream oos5 = new ObjectOutputStream(os5);
			
			
			Message to1 = new Message(sequencer1,seq_msg,phone_copy,2);
			
			oos.writeObject(to1);
			oos2.writeObject(to1);
			oos3.writeObject(to1);
			oos4.writeObject(to1);
			oos5.writeObject(to1);
		
			sequencer1++;
			
			/*PrintWriter out;
			PrintWriter out2;
			PrintWriter out3;
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket1.getOutputStream())),true);
			out.println(messg);
			out2 = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket2.getOutputStream())),true);
			out2.println(messg);
			out3 = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket3.getOutputStream())),true);
			out3.println(messg);*/
			
			Log.d("Sequencer ", "Multicast");

		}catch(Exception ere)
		{
			ere.printStackTrace();
		}
	}	
}

class testcase1 implements Runnable{

	public void run() {
		// TODO Auto-generated method stub
		int i;
		for(i=0;i<5;i++)
		{
			Thread client = new Thread(new Multicast(),"please chal jaa");
			client.start();
			try 
			{
		    	Thread.sleep(3000);
			} catch (Exception e)
			{
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
		}
		
	}
	
}

class testcase2 implements Runnable{
	
	public void run() {
	
		// TODO Auto-generated method stub
			test2_counter++;			
			teleman = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			phone_test2 = teleman.getLine1Number().substring(teleman.getLine1Number().length() - 4);
			if(test2_counter==1)
			{
				initiator = phone_test2;
			}
			Log.d("the number ", "of the"+initiator);
			
			Thread testc = new Thread(new Multicast(),"ummmm");
			testc.start();
			try 
			{
			Thread.sleep(3000);
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}

}