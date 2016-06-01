package com.example.subba.autocompleteproducts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    CustomAutoCompleteView myAutoComplete;

    // adapter for auto-complete
    ArrayAdapter<String> myAdapter;

    // for database operations
    DatabaseHandler databaseProducts;

    // just to add some initial value
    String[] item = new String[] {"Please search..."};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try{

            // instantiate database handler
            databaseProducts = new DatabaseHandler(MainActivity.this);

            // put sample data to database
            insertSampleData();

            // autocompletetextview is in activity_main.xml
            myAutoComplete = (CustomAutoCompleteView) findViewById(R.id.myautocomplete);

            // add the listener so it will tries to suggest while the user types
            myAutoComplete.addTextChangedListener(new CustomAutoCompleteTextChangedListener(this));

            // set our adapter
            myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, item);
            myAutoComplete.setAdapter(myAdapter);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertSampleData(){

        // CREATE
        databaseProducts.create( new MyObject("Ink-Jet Printer") );
        databaseProducts.create( new MyObject("Laser Printer") );
        databaseProducts.create( new MyObject("Laser Beam Printer") );
        databaseProducts.create( new MyObject("Ink Beam Printer") );
        databaseProducts.create( new MyObject("Dot matrix Printer") );
        databaseProducts.create( new MyObject("Dot matrix custom Printer") );
        databaseProducts.create( new MyObject("Dotted matrix Printer") );
        databaseProducts.create( new MyObject("Laser matrix Printer") );
        databaseProducts.create( new MyObject("Daisy Chain Printer") );
        databaseProducts.create( new MyObject("Daisy Printer") );
    }

    // this function is used in CustomAutoCompleteTextChangedListener.java
    public String[] getItemsFromDb(String searchTerm){

        // add items on the array dynamically
        List<MyObject> products = databaseProducts.read(searchTerm);
        int rowCount = products.size();

        String[] item = new String[rowCount];
        int x = 0;

        for (MyObject record : products) {

            item[x] = record.objectName;
            x++;
        }

        return item;
    }

}
