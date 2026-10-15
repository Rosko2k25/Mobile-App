package com.example.proekt_razrabotvane_na_mobilni_prilojeniq;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String DBNAME = "Sellers.db";
    public static final int DBVERSION = 1;
    private SQLiteDatabase _myDB;

    public class Sellers {
        protected String Name, ID, Address, Email, Phone,Product,Price;
        public Sellers(){
            Name=""; ID=""; Address=""; Email=""; Phone = "";Product = "";Price = "";
        }
        public Sellers(String ID, String Name, String Address, String Email, String Phone,String Product,String Price){
            this.ID = ID; this.Name=Name; this.Address=Address;
            this.Email = Email; this.Phone = Phone;this.Product =Product ;this.Price = Price;

        }
        public String getName(){
            return Name;
        }
        public String getID(){
            return ID;
        }
        public String getAddress(){
            return Address;
        }
        public String getEmail(){
            return Email;
        }

        public String getPhone(){ return Phone; }
        public String getProduct(){
            return Product;
        }
        public String getPrice(){
            return Price;
        }

    }

    public static String DBCREATE = "" +
            "CREATE TABLE sellers(" +
            "ID integer PRIMARY KEY AUTOINCREMENT, " +
            "Name text not null, " +
            "Address text not null, " +
            "Email text not null, " +
            "Phone text not null, " +
            "Product text not null, " +
            "Price text not null, " +
            "UNIQUE(Name, Phone)" +
            ")";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DBNAME, null, DBVERSION);
        _myDB = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBCREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion>oldVersion){
            db.execSQL("DROP TABLE sellers; ");
            db.execSQL(DBCREATE);
        }
    }

    public void insert(String Name, String Phone, String Email, String Address,String Product, String Price){
        _myDB.execSQL(
                "INSERT INTO sellers(Name, Phone, Email, Address, Product, Price) " +
                        "VALUES(?, ?, ?, ?,?,?)",
                new Object[]{
                        Name, Phone, Email, Address,Product,Price

                }

        );
    }

    public void update(String Name, String Phone, String Email, String Address, String Product, String Price,String ID){
        _myDB.execSQL(
                "UPDATE sellers SET Name = ?, Phone = ?, Email = ?, Address = ?, Product = ?, Price = ? " +
                        " WHERE ID = ? ",
                new Object[]{
                        Name, Phone, Email, Address,Product,Price, ID

                }

        );
    }

    public void delete(String ID){
        _myDB.execSQL(
                "DELETE FROM sellers " +
                        " WHERE ID = ? ",
                new Object[]{
                        ID

                }

        );
    }

    public List<Sellers> select(){
        Cursor c = _myDB.rawQuery("SELECT * FROM sellers ORDER BY Name; ", null);
        List<Sellers> l = new ArrayList<>();
        while (c.moveToNext()){
            @SuppressLint("Range") Sellers c1 = new Sellers(
                    c.getString(c.getColumnIndex("ID")),
                    c.getString(c.getColumnIndex("Name")),
                    c.getString(c.getColumnIndex("Address")),
                    c.getString(c.getColumnIndex("Email")),
                    c.getString(c.getColumnIndex("Phone")),
                    c.getString(c.getColumnIndex("Product")),
                    c.getString(c.getColumnIndex("Price"))
            );
            l.add(c1);

        }
        return l;
    }


}
