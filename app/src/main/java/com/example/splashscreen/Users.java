package com.example.splashscreen;

public class Users {
    int _id;
String _Name;
String _Pass;
String _Email;
}
public Users(){

}

public Users(int id, String name, String pass, String email){
this._id = id;
    this._Name = name;
this._Pass = pass;
this._Email = email;
}

public Users(String name, String pass, String email){
    this._Name = name;
    this._Pass = pass;
    this._Email = email;
}

public int GetID(){return this._id; }
public void SetID(int id){this._id = id;}
    public  String GetName(){return this._Name;}
    public void SetName(String name){this._Name = name;}
    public  String GetPass(){return this._Pass;}
    public void SetPass(String pass){this._Pass = pass;}
    public  String GetEmail(){return this._Email;}
    public void SetEmail(String email){this._Email = email;}


}
