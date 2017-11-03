package com.example.captainhumza.fyp_version3.Customer.Fragments.ExpandableListDirectory;

import com.example.captainhumza.fyp_version3.Classes.ProductCategory;
import com.example.captainhumza.fyp_version3.Classes.Products;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Captain Humza on 10/5/2017.
 */

public class ExpandableListDataPump {
    public  List<ProductCategory> productCategories = new ArrayList<ProductCategory>();
    public  HashMap<ProductCategory, List<Products>> expandableListDetail = new HashMap<ProductCategory, List<Products>>();
    public  HashMap<ProductCategory, List<Products>> getData() {


        String[] product = {"Pepsi" , "Prince" , "pasta" , "Choclato" , "ponam" , "Shan Masla" , "National Masala" , "Habib cooking oil"};
        for(int a = 0; a < productCategories.size();a++)
        {
            List<Products> basketball1 = new ArrayList<Products>();
            /*Products products = new Products();
            basketball1.add(products);*/
            expandableListDetail.put(productCategories.get(a), basketball1);
        }
        return expandableListDetail;
    }
}
