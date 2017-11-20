package com.example.captainhumza.fyp_version3.Classes;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.captainhumza.fyp_version3.LoginActivity;
import com.example.captainhumza.fyp_version3.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Abdul Rehman on 11/4/2017.
 */

public class WebServiceHandler extends AsyncTask <Void, Void , String> {

    public  AsyncResponse delegate = null;
    String url;
    HashMap<String ,String> parameters1;

    public WebServiceHandler (LoginActivity _getcontext , String _url , HashMap<String, String> parameters)
    {

        delegate = _getcontext;
        url= _url;
        parameters1 = parameters;
    }




    @Override
    protected String doInBackground(Void... params) {

        HttpURLConnection httpURLConnection = null;
        String response = null;
        try {
            URL registerurl = new URL(url);


            httpURLConnection = (HttpURLConnection)registerurl.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            bufferedWriter.write(getPostDataString(parameters1));
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
           /* InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            String result = "";
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                result+=line;

            }

            bufferedReader.close();
            inputStream.close();*/
            InputStream in = httpURLConnection.getInputStream();

            InputStreamReader isw = new InputStreamReader(in);

            java.util.Scanner s = new java.util.Scanner(isw).useDelimiter("\\A");
            response = s.hasNext() ? s.next() : "";
            httpURLConnection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }

        return response;
    }

    @Override
    protected  void onPostExecute (String result )
    {
        super.onPostExecute(result);
        Person person = Person.GetInstance();
            try {
                if(result != null)
                {
                    JSONObject str = new JSONObject(result);

                    person.PersonId = str.getInt("PersonId");
                    person.PersonName = str.getString("PersonName");
                    person.PersonUserName = str.getString("PersonUserName");
                    person.PersonCell = str.getString("PersonCell");
                    person.PersonEmail = str.getString("PersonEmail");
                    person.Token = str.getString("Token");
                    person.PersonCNIC = str.getString("PersonCNIC");
                    person.PersonTypeId = str.getInt("PersonTypeId");
                }
            } catch (JSONException e) {
                Toast.makeText(LoginActivity.instance(), e.getMessage(), Toast.LENGTH_LONG).show();
            }

        delegate.processFinish(person);

    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException{
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }
    public interface  AsyncResponse {
        void processFinish(Person output);
    }
}
