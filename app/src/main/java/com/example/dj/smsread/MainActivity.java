package com.example.dj.smsread;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Uri inboxURI = Uri.parse("content://sms/inbox");
            String[] reqCols = new String[]{"_id", "address", "date", "subject", "body"};

            ContentResolver cr = getContentResolver();
            Cursor cursor = cr.query(inboxURI, reqCols, null, null, null);

            //if(Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {

                File smsFile = new File("/sdcard/sms.txt");
                smsFile.createNewFile();
                FileOutputStream fo = new FileOutputStream(smsFile);
                OutputStreamWriter out = new OutputStreamWriter(fo);

                if (cursor.moveToFirst()) {
                    do {
                        String data = "_id : " + cursor.getString(cursor.getColumnIndex("_id")) + "\n" +
                                "address : " + cursor.getString(cursor.getColumnIndex("address")) + "\n" +
                                "date : " + cursor.getString(cursor.getColumnIndex("date")) + "\n" +
                                "subject : " + cursor.getString(cursor.getColumnIndex("subject")) + "\n" +
                                "body : " + cursor.getString(cursor.getColumnIndex("body")) + "\n" +
                                "------------------------------------------------------\n";

                        //Toast.makeText(getApplicationContext(), data, Toast.LENGTH_LONG).show();

                        // do what ever you want here
                        out.append(data);

                    } while (cursor.moveToNext());
                }
                cursor.close();
                out.close();
                fo.close();
            //}
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "ERROR : " + e.getMessage() , Toast.LENGTH_LONG).show();
        }
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

    public void ReadFile(View view)
    {

    }

}
