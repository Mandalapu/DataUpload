package com.example.dataupload;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.json.JSONException;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;




import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


public class MainActivity extends Activity {
	
	String user;
	boolean stat=true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	EditText uid=(EditText) findViewById(R.id.editText1);
            	String u=uid.getText().toString();
            	String f = Environment.getExternalStorageDirectory()+"/"+u;
            	File file = new File(f);
            	if(!file.exists()) {
            		Intent intent = new Intent(MainActivity.this, SecondMainActivity.class);
            	    startActivity(intent);
            	}
            	else {
            		//user=Integer.parseInt(u);
					user=u;
            		new UploadTask().execute();
            	}
            		

            }
        });
	}
		class UploadTask extends AsyncTask<Void,Void,Void> {
			@Override
		    protected void onPreExecute(){
				super.onPreExecute();
		        ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar1);
		        pb.setVisibility(View.VISIBLE);
		    }
			@Override
			protected Void doInBackground(Void... params) {
			String f = Environment.getExternalStorageDirectory()+"/"+user;
			
			//STUFF TO CHANGE BELOW
		    //CHANGE SFTPHOST
		    //sftp server name-host
		    String SFTPHOST = "isr-cogusadb.private.isr.umich.edu";
		    //CHANGE SFTPUSER
		    //sftp connection username
		    String SFTPUSER = "isr\\cogusaxfer";
		    //CHANGE SFTPPASS
		    //sftp connection password
		    String SFTPPASS = "mysqlsftp2ISR!";
		    //CHANGE SFTPWORKINGDIR
		    //Directory in the remote server to which files are to be uploaded, if this directory doesnt exist, the code will create one.
		    //String SFTPWORKINGDIR = "uploads/"+user+"_audio";
		    String SFTPWORKINGDIR = "uploads/0000_audio";
		    //CHANGE folder
		    //path to local folder that contains files to upload
		    File folder= new File(f);
		    // STUFF TO CHANGE ABOVE
		    //port 22 for SFTP
		    int SFTPPORT = 22;
		    //creating session to connect to server        
		    Session     session     = null;
		    //creating channel to upload files
		    Channel     channel     = null;
		    ChannelSftp channelSftp = null;
		    //flag to check if the remote directory exists
		    SftpATTRS dir_exists=null;
		    try{
		           
		        JSch jsch = new JSch();
		        //creating session
		        session = jsch.getSession(SFTPUSER,SFTPHOST,SFTPPORT);
		        //password        
		        session.setPassword(SFTPPASS);
		        //configuring the session
		        java.util.Properties config = new java.util.Properties();
		        //server properties
		        config.put("StrictHostKeyChecking", "no");
		        //setting configuration for the session
		        session.setConfig(config);
		        //connection to host
		        session.connect();
		        //opening SFTP channel
		        channel = session.openChannel("sftp");
		        //connecting 
		        channel.connect();
		        channelSftp = (ChannelSftp)channel;
		        //checking if the remote directory exists, if exists dir_exists flag will not be null and we change to that directory and upload files, if it doesnt exist, we create the directory
		        try{
		            dir_exists=channelSftp.stat(channelSftp.pwd()+"/"+SFTPWORKINGDIR);
		        }
		        catch(Exception e){
		            System.out.println("Directory doesnt exist in the server, it will be created");
		            Log.w("status","Directory doesnt exist in the server, it will be created");
		        }
		
		        if(dir_exists!=null){
		            //cd to existing directory on the server
		            channelSftp.cd(SFTPWORKINGDIR);
		            System.out.println("Directory "+SFTPWORKINGDIR+" exists on the server.");
		            Log.w("status","Directory "+SFTPWORKINGDIR+" exists on the server.");
		        }
		        else{
		            //create remote directory
		            channelSftp.mkdir(SFTPWORKINGDIR);
		            System.out.println("Directory "+SFTPWORKINGDIR+" doesn't exist on the server, creating directory.");
		            Log.w("status","Directory "+SFTPWORKINGDIR+" doesn't exist on the server, creating directory.");
		        }
		        //selecting each file from the folder and uploading it
		        int a=0;
		        //terminating the connection when done
				for(File each_file:folder.listFiles()){
					if(each_file.getName().contains(".mp3")) {
						UploadData.setaudio(each_file.getName(), a);a++;
						System.out.println("Uploading " +each_file.toString());
						Log.w("status","Uploading " +each_file.toString());
						//System.out.println();
						channelSftp.put(new FileInputStream(each_file), each_file.getName());
						System.out.println("Done uploading"+each_file.toString());
						Log.w("status","Done uploading"+each_file.toString());}
				}
				channelSftp.exit();
		        session.disconnect();
		        }
		        
		    catch(Exception ex) {
				ex.printStackTrace();
				channelSftp.exit();
				session.disconnect();
				stat = false;
			}
				if( !stat )
				{
					Log.w("Upload audiofile failed","");
				}
		    try{
			    Class.forName("com.mysql.jdbc.Driver").newInstance();
			    
			}catch(Exception e){
				Log.w("jdbc driver failure", "");
				stat=false;
			}
			//isr-cogusadb.private.isr.umich.edu
			//198.162.17.243
				boolean uploadJsonStatus = true;
			try {
				String url = "jdbc:mysql://isr-cogusadb.private.isr.umich.edu:3306/cogusa";

				//String url = "jdbc:mysql://cogweb.usc.edu:3306/project_talent";
				String username = "cogusauser";
				//changed credentials
				//userName = root
				//password = 396Bodz#
				//String username = "cogusauser";
				//dbname = project_talent
				String password = "scarF@ce!";
				//String password ="396Bodz#";
				Connection DbConn = DriverManager.getConnection(url, username, password);
				Log.w("Connection", "open");
				Statement stmt = DbConn.createStatement();
				String loginquery, aquery, dquery, rfquery, vequery, pvquery, nsquery, arquery, spquery, swquery, wquery, trackquery, voquery;
				for (File each_file : folder.listFiles()) {
					if (each_file.getName().contains("final_data")) {
						uploadJsonStatus = true;
						//TODO need to check the value of the filename that is getting genrated.
						System.out.println("File name:" + each_file.getName());
						try {
							UploadData.uploadJson(user, each_file.getName());
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							stat = false;
							uploadJsonStatus = false;
							System.out.println("failed while performing upload JSON action");
						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							stat = false;
							uploadJsonStatus = false;
							System.out.println("failed while performing upload JSON action");
						}
						try {
							loginquery = UploadData.getlogin();
							dquery = UploadData.getd();
							voquery = UploadData.getvo();
							rfquery = UploadData.getrf();
							nsquery = UploadData.getns();
							arquery = UploadData.getar();
							spquery = UploadData.getsp();
							wquery = UploadData.getw();
							trackquery = UploadData.gettrack();
							stmt.executeUpdate(loginquery);
							Log.w("dbstat", "login done");
							stmt.executeUpdate(dquery);
							Log.w("dbstat", "SectionD done");
							stmt.executeUpdate(rfquery);
							Log.w("dbstat", "SectionRF done");
							stmt.executeUpdate(voquery);
							Log.w("dbstat", "SectionVO done");
							stmt.executeUpdate(nsquery);
							Log.w("dbstat", "SectionNS done");
							stmt.executeUpdate(arquery);
							Log.w("dbstat", "SectionAR done");
							stmt.executeUpdate(spquery);
							Log.w("dbstat", "SectionSP done");
							stmt.executeUpdate(wquery);
							Log.w("dbstat", "SectionW done");
							stmt.executeUpdate(trackquery);
							Log.w("dbstat", "Track User");
							DbConn.close();
						}catch(Exception e)
						{
							uploadJsonStatus = false;
							stat = false;
							System.out.println("Executing queries failed. check the logs");
						}
						if( !uploadJsonStatus )
						{
							Log.w("upload json-fail", each_file.getName());
							System.out.println("upload json failed for file name:" +each_file.getName());
						}
						else
						{
							//TODO need to delete the corresponding file from the direcotry.
							each_file.delete();
						}
					}
				}
			}
			catch(Exception e)
			{
				Log.w("Error connection", "" + e.getMessage());
				stat = false;
				uploadJsonStatus = false;
			}
				if( !stat && !uploadJsonStatus )
				{
					//TODO delete all the files in the direcory and make it empty
				}
				return null;
		}
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar1);
	        pb.setVisibility(View.INVISIBLE);
			if(!stat)
				Toast.makeText(getBaseContext(), "DATA UPLOAD UNSUCCESSFUL", Toast.LENGTH_LONG).show();
		}	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
