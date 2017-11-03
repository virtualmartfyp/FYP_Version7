package com.example.captainhumza.fyp_version3.Classes;

import android.graphics.Bitmap;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.captainhumza.fyp_version3.MainActivity;
//import com.example.captainhumza.fyp_version3.Customer.CustomerHomeTwoActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Captain Humza on 10/11/2017.
 */

public class ProductCategory {

    public int ProductSubCatId ;
    public String ProductSubCat ;
    public String ProductSubImage ;
    public int ProductCategoryId;
    public  String CategoryDescription;
    public Bitmap decodedByte;
    String test = "";

    //String showUrl = "http://192.168.10.8/FYP_Version1Entity/api/Product";
    //String insert = "http://10.0.2.2/captain/addform.php";
    RequestQueue referenceQueue;
    public List<ProductCategory>  GetAllProductsCategory() {
        final List<ProductCategory> productCategoriesLs = new ArrayList<ProductCategory>() {
        };
        try {
            referenceQueue = Volley.newRequestQueue(MainActivity.instance().getApplicationContext());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ConstantClass.subProduct
                    , null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        JSONArray st = response.getJSONArray("Value");
                        for (int i = 0; i < st.length(); i++) {
                            ProductCategory pc = new ProductCategory();
                            JSONObject str = st.getJSONObject(i);
                            pc.ProductSubCatId = str.getInt("ProductSubCatId");
                            pc.ProductSubCat = str.getString("ProductSubCat");
                            pc.ProductSubImage = str.getString("ProductSubImage");
                            pc.ProductCategoryId = str.getInt("ProductCategoryId");
                            pc.CategoryDescription = str.getString("CategoryDescription");
                            //String ab = str.getString("productTitleImage");
                            /*pc.category = str.getString("category");
                            if (ab.length() > 6)
                            {
                                byte[] decodedString = Base64.decode(ab,0);
                                pc.decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                //pc.productTitleImage = decodedString;
                            }*/

                            productCategoriesLs.add(pc);

                        }

                    } catch (JSONException e) {
                        test = test + (e.getMessage());
                        e.printStackTrace();
                    }


                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            referenceQueue.add(jsonObjectRequest);
        } catch (Exception ex) {
            test = test + (ex.getMessage());
        }
        return productCategoriesLs;

    }
    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }


}
