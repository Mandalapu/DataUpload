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
    private String privateFilePath = Environment.getExternalStorageDirectory()+"/" + "project_talent.pem";
	private String privateKey = "MIIEpQIBAAKCAQEAoyJEjdaGZ6GbQBtbY+WPbvoDedsZPHURyJPcqrlWi9ZYT7bDePw9E/YGp21b" +
			"RrepYmTaqbZ7tju0KWUSpTjIQlB6+V4mheFL3wFTgKu7cdKFYFNfBOdWnSo3UA4lVoKoP4sp20yr" +
			"M8OV/QKmdRjZazdTomLD8vlM6myEbVqdbHhOVaQJP8cqjireDhqQSoPGENpSoCNnSstvP7hkTgK/" +
			"i1vtZsNgMxPQVcMjoojezl8/KD8+ULQ7NZlnQYtcjqBZGa5DMOGpwK5HBXbTF7UYThTklVOXW5VS" +
			"hdgI5klIWcSVosdTETkpT7tFgvGXL8hEEi3cPPfXAeWMoyt2JAAAaQIDAQABAoIBADKSAOZnRisB" +
			"8fqhgGcrVpd0cXdo8B3JvMWM8K8L3URkIeQ0J8xthRGXBuEICW+Wp6+UzCQoKZmIUIU0i98kBxBn" +
			"mneLNC30dWygIJ6Wnfje5fHCZoZCoJr/5z2t5O04e6eXIFaXIcteKjTQiYFrPpr5/7crHstHdQyG" +
			"r1am7H4fHezfzPMDJ8asrXAG4fmUM/bxVPCAbce0Hvdp3G8U/CnMKnQ8HiGooV7eqqyhp+GAHhW2" +
			"U2PCVMXB9KnG3KrsfDQm9pdlgNq2UCBB18sUt/LZ2ET4AwtQf0960nr8oRqwDYvSM2KT6x4KW1m0" +
			"VAOxT95vE2No+Kt0r/eF3FyCDgECgYEA4lGucTP5cbTLWgn39nv7j6OOVpxgm4eTOvxQxibJ7rgi" +
			"kNp90Xsry6n6XEXf/S8tsJyT4PUTKm3RXeYISixV/sJz+kDb6/qIxlm7GkNL7tKXQDIvQfQO4s/z" +
			"N71+PnHMdz5k6r0x5MHk43ZyIoWGABDUhTTHXhPvYuAT+P1U7PECgYEAuIc9McrIF5W5yYONLeME" +
			"12BgjPPQ7jyrFzyW/8Xs0QrDxEYG7ScC8i3gJYZW3kFkeD5/C8dR7NdsK6+Kc0mg6Csij4X41baI" +
			"KDsBh8Z+MUzlLqjL0x6YzPUfnQTqvc4l4dvlA2kOM7612up6vxxoBgMTdSRcRanmSxODTb3mKvkC" +
			"gYEAxn0o5zPURDtyVceEZ/MZEEM75Q5FVGK1Z/g+x2rhk7J+7lgSHRVPtdGgOCk4Z49nHQxWTefj" +
			"JUUnaJkFEDiOO19D5GnZ977FEmFETaZUBGNCMBVTiR6Jp7cF8w8Il6+qybI5Q/8msSC0pCIPu+Mr" +
			"QQeBvEoryIDnZOpt2eQlBaECgYEAr0MT5kT5wxzcdv9RijHDGWVajsXMvVo2Dj3oFUrB4NJemf8A" +
			"+SJCdA8Kg9mSjEqDyIljVaacs1zwSpXR5CocgCboDnVqoGFb9QoGLBVlKiIk0ypTXVQEQTbYLeTL" +
			"qDcyV9DpNVIEwTTzuNdYekfarUsiofJFCY2TQ99mqOIiyNECgYEAuN+AIe86AT+vJN4gPhs1hPfE" +
			"EzxmEf1h2EXBeq7Y1p5Fb6K4K3zCTvX02Vei4LK5aCcCT5J69JrO0ivlYOhkQQLkcOZaOWHyBh/f" +
			"uhtJ38+ghKxrTPYFA1pBUCLmOMFVKds0CiRFqIIM6vAXxZmR4A4kKDd4RBcyKEIpU6dOwQQ=";

    private String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoyJEjdaGZ6GbQBtbY+WP" +
            "bvoDedsZPHURyJPcqrlWi9ZYT7bDePw9E/YGp21bRrepYmTaqbZ7tju0KWUSpTjI" +
            "QlB6+V4mheFL3wFTgKu7cdKFYFNfBOdWnSo3UA4lVoKoP4sp20yrM8OV/QKmdRjZ" +
            "azdTomLD8vlM6myEbVqdbHhOVaQJP8cqjireDhqQSoPGENpSoCNnSstvP7hkTgK/" +
            "i1vtZsNgMxPQVcMjoojezl8/KD8+ULQ7NZlnQYtcjqBZGa5DMOGpwK5HBXbTF7UY" +
            "ThTklVOXW5VShdgI5klIWcSVosdTETkpT7tFgvGXL8hEEi3cPPfXAeWMoyt2JAAA" +
            "aQIDAQAB";
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
                File folder= new File(f);


		    //sftp server name-host
		    String SFTPHOST = "ec2-54-186-119-217.us-west-2.compute.amazonaws.com";
		    //sftp connection username
		    String SFTPUSER = "ec2-user";
		    //String SFTPWORKINGDIR = "uploads/"+user+"_audio";
		    String SFTPWORKINGDIR = "uploads/"+user+"_audio";
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
				jsch.addIdentity(privateFilePath);
		        //creating session
		        session = jsch.getSession(SFTPUSER,SFTPHOST,SFTPPORT);
		        //configuring the session
		        java.util.Properties config = new java.util.Properties();
		        //server properties
		        config.put("StrictHostKeyChecking", "no");
		        //setting configuration for the session
		        session.setConfig(config);
		        //connection to host
		        session.connect();
		        //opening SFTP channel
                Log.d("Status", "Session connected");
		        channel = session.openChannel("sftp");
		        //connecting 
		        channel.connect();
                Log.d("status", "Channel connected");
		        channelSftp = (ChannelSftp)channel;
		        //checking if the remote directory exists, if exists dir_exists flag will not be null and we change to that directory and upload files, if it doesnt exist, we create the directory
		        try{
		            dir_exists=channelSftp.stat(channelSftp.pwd()+"/"+SFTPWORKINGDIR);
		        }
		        catch(Exception e){
		            System.out.println("Directory doesnt exist in the server, it will be created");
		            Log.d("status","Directory doesnt exist in the server, it will be created");
		        }
		
		        if(dir_exists!=null){
		            //cd to existing directory on the server
		            channelSftp.cd(SFTPWORKINGDIR);
		            System.out.println("Directory "+SFTPWORKINGDIR+" exists on the server.");
		            Log.d("status","Directory "+SFTPWORKINGDIR+" exists on the server.");
		        }
		        else{
		            //create remote directory
		            channelSftp.mkdir(SFTPWORKINGDIR);
		            System.out.println("Directory "+SFTPWORKINGDIR+" doesn't exist on the server, creating directory.");
		            Log.d("status","Directory "+SFTPWORKINGDIR+" doesn't exist on the server, creating directory.");
		        }
                //selecting each file from the folder and uploading it
                int a=0;
                //terminating the connection when done
                for(File each_file:folder.listFiles()){
                    if(each_file.getName().contains(".mp3")) {
                        UploadData.setaudio(each_file.getName(), a);a++;
                        System.out.println("Uploading " +each_file.toString());
                        Log.d("status","Uploading " +each_file.toString());
                        //System.out.println();
                        channelSftp.put(new FileInputStream(each_file), each_file.getName());
                        System.out.println("Done uploading"+each_file.toString());
                        Log.d("status","Done uploading"+each_file.toString());}
                }
		        }
		        
		    catch(Exception ex) {
                Log.d("Exception Occurred", "exception occurred while connecting to the server");
                Log.d("Excetion", ex.getLocalizedMessage().toString());
				ex.printStackTrace();

				stat = false;
			}
            finally {
                if( stat ) {
                    channelSftp.exit();
                    channel.disconnect();
                    session.disconnect();
                }
            }
                if( !stat )
				{
					Log.d("Upload Status", "Upload audio file failed");
				}
				else
				{
					Log.d("Status message", "Uploading the audio file successful.");
				}
				// TODO: 1/26/2017 Need to uncomment the SFTP section and need to bring a efficient delete strategy for the files uploaded.
				try{
			    Class.forName("com.mysql.jdbc.Driver").newInstance();
			    
			}catch(Exception e){
				Log.d("jdbc driver failure", "");
				stat=false;
			}
                boolean uploadJsonStatus = true;
			try {

				//String url = "jdbc:mysql://isr-cogusadb.private.isr.umich.edu:3306/project_talent";
				String url = "jdbc:mysql://projecttalent.c6mmpnkgm3pa.us-west-2.rds.amazonaws.com:3306/project_talent";
                //Updated credentials for the DBA.
				String username = "PTuser";
				String password ="PTpasscode";
				Connection DbConn = DriverManager.getConnection(url, username, password);
				Log.d("Database", "Connection established");
				Log.d("Connection", "open");
				Statement stmt = DbConn.createStatement();
				String loginquery, aquery, dquery, rfquery, vequery, pvquery, nsquery, arquery, spquery, swquery, wquery, trackquery, voquery;
				for (File each_file : folder.listFiles()) {
					if (each_file.getName().contains("final_data") && (each_file.getName().contains(".json")) ) {
						// TODO: 1/26/2017  Need to add another condition where only the files from the correspoding user is uploaded.
						uploadJsonStatus = true;
						//TODO need to check the value of the filename that is getting genrated.
						Log.d("MainActivity-file name", "File name:" + each_file.getName());
						try {
                            //This line may cause several exceptions as the queries get constructed.
							UploadData.uploadJson(user, each_file.getName());
						} catch (IOException e) {
							e.printStackTrace();
							stat = false;
							uploadJsonStatus = false;
							Log.d("MainActivity", "failed while performing upload JSON action");
						} catch (JSONException e) {
							e.printStackTrace();
							stat = false;
							uploadJsonStatus = false;
                            Log.d("MainActivity", "failed while performing upload JSON action");
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
							Log.d("dbstat", "login done");
							stmt.executeUpdate(dquery);
							Log.d("dbstat", "SectionD done");
							stmt.executeUpdate(rfquery);
							Log.d("dbstat", "SectionRF done");
							stmt.executeUpdate(voquery);
							Log.d("dbstat", "SectionVO done");
							stmt.executeUpdate(nsquery);
							Log.d("dbstat", "SectionNS done");
							stmt.executeUpdate(arquery);
							Log.d("dbstat", "SectionAR done");
							stmt.executeUpdate(spquery);
							Log.d("dbstat", "SectionSP done");
							stmt.executeUpdate(wquery);
							Log.d("dbstat", "SectionW done");
							stmt.executeUpdate(trackquery);
							Log.d("dbstat", "Track User");
							DbConn.close();
						}catch(Exception e)
						{
							uploadJsonStatus = false;
							stat = false;
							System.out.println("Executing queries failed. check the logs");
						}
						if( !uploadJsonStatus )
						{
							Log.d("upload json-fail", each_file.getName());
							Log.d("MainActivity", "upload json failed for file name:" +each_file.getName());
						}
						else
						{
							//TODO need to delete the corresponding file from the direcotry.
							Log.d("Status", "There are no errors while publishing this json file to the database");
							Log.d("Deletions", "About to delete the file after uploading the current file contents to database");
							each_file.delete();
						}
					}
				}
			}
			catch(Exception e)
			{
				Log.d("Error connection", "" + e.getMessage());
				stat = false;
				uploadJsonStatus = false;
			}
				if( stat && uploadJsonStatus )
				{
					//TODO delete all the files in the direcory and make it empty
					// TODO: 1/26/2017 Have the Deletion activity code here instead of another file.
				}
				return null;
		}
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar1);
	        pb.setVisibility(View.INVISIBLE);
			if(!stat) {
				Toast.makeText(getBaseContext(), "DATA UPLOAD UNSUCCESSFUL", Toast.LENGTH_LONG).show();
				Log.d("message", "the Data upload is unsuccessful");
			}
			else
			{
				Toast.makeText(getBaseContext(), "DATA UPLOAD is SUCCESSFUL!!", Toast.LENGTH_LONG).show();
			}
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
