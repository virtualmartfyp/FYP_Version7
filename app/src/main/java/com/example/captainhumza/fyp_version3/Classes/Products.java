package com.example.captainhumza.fyp_version3.Classes;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Captain Humza on 10/11/2017.
 */

public class Products {
    public int ProductId ;
    public String ProductName;
    public int productTitleId ;
    public String productTitle ;
    public double ProductRate ;
    public String productCategory;
    public String subCategory ;
    public int Weight ;
    public double totalPrice;
    public String UnitDEscription ;
    public String ProductImage ;
    public int quantity;
    public byte[] productTitleImage ;
    public int ProductCategoryId ;


    public void MakeOrder(List<Products> ls)
    {
        HashMap<String , String> params  = new HashMap<String , String>();
        for (int i = 0; i < ls.size();i++)
        {

        }
    }


}
