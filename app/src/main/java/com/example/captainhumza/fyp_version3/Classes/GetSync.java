package com.example.captainhumza.fyp_version3.Classes;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.captainhumza.fyp_version3.Customer.Fragments.CartCustomer;
import com.example.captainhumza.fyp_version3.Customer.Fragments.ExpandableListDirectory.ExpandableListDataPump;
import com.example.captainhumza.fyp_version3.DataBase.DataBaseHandler;
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
import java.util.List;

/**
 * Created by Captain Humza on 10/28/2017.
 */

public class GetSync extends AsyncTask<String,Void,String> {

    ExpandableListDataPump expandableListDataPump;
    GetASyncTaskTitleInterface mListner;
    private final ProgressDialog dialog = new ProgressDialog(MainActivity.instance());
    public GetSync(ExpandableListDataPump _expandableListDataPump , GetASyncTaskTitleInterface _mListner)
    {
        mListner = _mListner;
        expandableListDataPump = _expandableListDataPump;
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

            /*int data = isw.read();
            while (data != -1) {
                char current = (char) data;
                data = isw.read();
                response += data;
            }*/
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
        if (CartCustomer.backButtonLock == 0) {
            try {

                DataBaseHandler dt = new DataBaseHandler(MainActivity.instance());
                List<ProductCategory> ls = dt.SelectData();
                for (int i = 0; i < ls.size(); i++) {
                    //if(expandableListDataPump.productCategories.contains(ls.get(i)))
                    expandableListDataPump.productCategories.add(ls.get(i));
                }
                JSONArray st = new JSONArray(result);
                for (int i = 0; i < st.length(); i++) {
                    ProductCategory pc = new ProductCategory();
                    JSONObject str = st.getJSONObject(i);
                    pc.ProductSubCatId = str.getInt("ProductSubCatId");
                    pc.ProductSubCat = str.getString("ProductSubCat");
                    pc.ProductSubImage = str.getString("ProductSubImage");
                    pc.ProductCategoryId = str.getInt("ProductCategoryId");
                    pc.CategoryDescription = str.getString("CategoryDescription");
                    if (!expandableListDataPump.productCategories.contains(pc)) {
                        expandableListDataPump.productCategories.add(pc);
                    }
                    //expandableListDataPump.productCategories.add(pc);
                }
                dialog.hide();
                mListner.UpdateTiteleAdapter();
            } catch (JSONException e) {
                Toast.makeText(MainActivity.instance(), e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        } else {
            dialog.hide();
            mListner.UpdateTiteleAdapter();
        }
    }

    @Override
    protected void onPreExecute() {dialog.show();}

    @Override
    protected void onProgressUpdate(Void... values) {}
    public interface GetASyncTaskTitleInterface
    {
        void UpdateTiteleAdapter();
    }
}
