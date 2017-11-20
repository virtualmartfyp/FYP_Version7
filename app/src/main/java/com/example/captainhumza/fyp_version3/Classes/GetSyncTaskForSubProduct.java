package com.example.captainhumza.fyp_version3.Classes;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.renderscript.Sampler;
import android.widget.Toast;

import com.example.captainhumza.fyp_version3.Customer.Fragments.ExpandableListDirectory.ExpandableListDataPump;
import com.example.captainhumza.fyp_version3.MainActivity;
//import com.example.captainhumza.fypversion2.Customer.CustomerHomeTwoActivity;
//import com.example.captainhumza.fypversion2.Customer.CustomersFragments.ExpandableListDirectory.ExpandableListDataPump;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Captain Humza on 10/28/2017.
 */

public class GetSyncTaskForSubProduct extends AsyncTask<String,String,String>{
    ProductCategory val = null;
    ExpandableListDataPump expandableListDataPump;
    GetASyncTaskInterface mListner;
    private final ProgressDialog dialog = new ProgressDialog(MainActivity.instance());
    public GetSyncTaskForSubProduct(ProductCategory _val ,ExpandableListDataPump _expandableListDataPump , GetASyncTaskInterface _mListner)
    {
        mListner = _mListner;
        expandableListDataPump = _expandableListDataPump;
        val = _val;
    }
    @Override
    protected String doInBackground(String... params) {

        String response = null;
        URL url;
        HttpURLConnection urlConnection = null;
        try {

            url = new URL(params[0]);

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
            List<Products> basketball1 = new ArrayList<Products>();
            for (int i = 0; i < st.length(); i++) {
                Products pc = new Products();
                JSONObject str = st.getJSONObject(i);
                pc.ProductId = str.getInt("ProductId");
                pc.ProductName = str.getString("ProductName");
                pc.ProductImage = str.getString("ProductImage");
                pc.ProductRate = str.getInt("ProductRate");
                pc.UnitDEscription = str.getString("UnitDEscription");
                pc.Weight = str.getInt("Weight");
                basketball1.add(pc);
            }
            expandableListDataPump.expandableListDetail.put(val,basketball1);
        } catch (JSONException e) {
            Toast.makeText(MainActivity.instance(),e.getMessage(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        dialog.hide();
        mListner.UpdateAdapter();

    }

    @Override
    protected void onPreExecute()
    {
        dialog.show();
    }

    @Override
    protected void onProgressUpdate(String... values)
    {

    }
    public interface GetASyncTaskInterface
    {
        void UpdateAdapter();
    }


}
