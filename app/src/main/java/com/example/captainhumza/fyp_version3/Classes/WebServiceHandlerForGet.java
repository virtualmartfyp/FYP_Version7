package com.example.captainhumza.fyp_version3.Classes;

import android.os.AsyncTask;
import android.widget.Toast;

import com.example.captainhumza.fyp_version3.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Abdul Rehman on 11/5/2017.
 */

public class WebServiceHandlerForGet extends AsyncTask <String , Void , String>
{

    String val = "";
    String url;
    public AsycResponseForGet delefate= null;
    public  WebServiceHandlerForGet (String _url ,String _val)
    {
        url= _url;
        val = _val;


    }


    @Override
    protected String doInBackground(String... params) {

            String response = null;
            String register;
            HttpURLConnection urlConnection = null;
            try {
                register = url +"email=" + val;
                URL url = new URL(register);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader isw = new InputStreamReader(in);

                java.util.Scanner s = new java.util.Scanner(isw).useDelimiter("\\A");
                response = s.hasNext() ? s.next() : "";

                int data = isw.read();
                while (data != -1) {
                    char current = (char) data;
                    data = isw.read();
                    //viewById.setText(data);
                    System.out.print(current);
                    response += data;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

            return response;
    }

    @Override
    protected void onPostExecute(String result) {

        super.onPostExecute(result);
        try {
            JSONArray st = new JSONArray(result);
            HashMap<String , String> map = new HashMap<String ,String>();

             //  Person person = new Person();
                JSONObject str = st.getJSONObject(0);
                    map.put("PersonName" , str.getString("PersonName") );
                    map.put("PersonEmail" ,str.getString("PersonEmail") );
                    map.put("PersonCell" ,str.getString("PersonCell") );
                    delefate.processFinish1(map);


        } catch (JSONException e) {
            Toast.makeText(MainActivity.instance(),e.getMessage(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


    }
    public interface AsycResponseForGet {
    void processFinish1(HashMap<String , String> output);
    }


}
