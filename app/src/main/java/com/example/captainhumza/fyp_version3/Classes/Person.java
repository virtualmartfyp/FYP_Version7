package com.example.captainhumza.fyp_version3.Classes;

import android.content.Context;
import android.content.Intent;
import android.util.Base64;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by Abdul Rehman on 11/4/2017.
 */

public class Person {

    public int PersonId ,PersonTypeId ;
    public String PersonName, PersonEmail, PersonPasswaor , PersonCell , PersonUserName ,PersonCNIC , Token;
    private static Person person = null;
    public Person()
    {

    }
    public static Person GetInstance()
    {
        if(person == null)
        {
            person = new Person();
        }
        return person;
    }
    public static void DestroyInstance()
    {
        if(person != null)
        {
            person = null;
        }
    }

   // public  Person (int personid , String name , String personemail , String personcell , String _userName)
  //  {

   //     this.PersonId = personid;
  //      this.PersonName = name;
  //      this.PersonEmail = personemail;
  //      this.PersonCell = personcell;
  //      this.PersonUserName = _userName;

   // }

    public int getId (){return  this.PersonId;}
    public  String getUsername(){
        return  PersonUserName;
    }
    public String getEmail(){
        return PersonEmail;
    }
    public String getCell(){ return PersonCell;}
   //public  String getPassword(){
      //  return password;
  //  }

    public HashMap<String , String>  AddPerson(final String PersonName , final String PersonCell , final String PersonEmail, final String PersonPassword ,  String _UserName , int _PersonType)
    {
            //String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        byte[] data = PersonPassword.getBytes(UTF_8);
        String base64 = Base64.encodeToString(data, Base64.DEFAULT);
        HashMap<String , String> params  = new HashMap<String , String>();
        params.put("PersonName" , PersonName);
        params.put("PersonUserName" , _UserName);
        params.put("PersonEmail" , PersonEmail);
        params.put("PersonCell" , PersonCell);
        params.put("PersonPasswaor" , base64);
        params.put("PersonTypeId" ,  _PersonType+"");
        return  params;
    }
    public HashMap<String , String> ValidatePerson (final String UserName , final String Password )
    {
        byte[] data = Password.getBytes(UTF_8);
        String base64 = Base64.encodeToString(data, Base64.DEFAULT);
        HashMap <String , String> map = new HashMap<String ,String>();
        map.put("PersonEmail" , UserName);
        map.put("PersonPasswaor" , base64);
        return map;



    }
    /*public HashMap<String , String> ABC (final String UserName , final String Password )
    {
        byte[] data = Password.getBytes(UTF_8);
        String base64 = Base64.encodeToString(data, Base64.DEFAULT);
        HashMap <HashMap<String,String> , HashMap<String,String>> map = new HashMap <HashMap<String,String> , HashMap<String,String>>();
        HashMap<String,String> key = new HashMap<String,String>();
        HashMap<String,String> value =  new HashMap<String,String>();
        key.put("VenderId","1");
        key.put("Customer","18");
        map.put("PersonEmail" , UserName);
        map.put("PersonPasswaor" , base64);
        return map;



    }*/
    public HashMap<String , String> ValidateUserAccountThroughEmail (final  String Email)
    {
        HashMap<String , String> map = new HashMap<String, String>( );
        map.put("PersonEmail" , Email);
        return map;

    }
    public HashMap<String , String> ValidateUserAccountThroughEmaila (final  String Email)
    {
        HashMap<String , String> map = new HashMap<String, String>( );
        map.put("PersonEmail" , Email);
        return map;

    }
    public  HashMap <String , String>UpdatePerson(String personname ,String cell , String Email , String username , String id)
    {
        HashMap <String , String> map = new HashMap<String ,String>();
        map.put("PersonName" , personname);
        map.put("PersonCell" ,cell );
        map.put("PersonEmail" , Email);
        map.put("PersonUserName" ,username );
        map.put("PersonId", id);


        return map;
    }


}
