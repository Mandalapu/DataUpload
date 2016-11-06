package com.example.dataupload;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

public class LoopedActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looped);

        String user="12345";
        //String f = Environment.getExternalStorageDirectory()+"/"+user+"/"+"final_data_"+user+".json";

        String myDirectory = Environment.getExternalStorageDirectory()+"/"+user+"/";
        File f = new File(myDirectory);
        if (f.exists() && f.isDirectory()){
            final Pattern p = Pattern.compile("final_data_.*\\.json"); // I know I really have a stupid mistake on the regex;

            File[] flists = f.listFiles(new FileFilter(){
                @Override
                public boolean accept(File file) {

                    return p.matcher(file.getName()).matches();
                }
            });
            Log.v("filenamesthatmatch",flists.toString());
            String s = "wait a minute, i'm debugging";
        }
    }

}
