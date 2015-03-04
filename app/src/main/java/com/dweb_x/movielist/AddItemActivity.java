package com.dweb_x.movielist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * @author : D.Carruthers
 * @version 1.0
 * Date: 03/03/2015.
 * email: dave@dweb-x.com
 */
public class AddItemActivity extends ActionBarActivity {

    private String key;
    private Spinner ratingSpinner;
    private Spinner typeSpinner;
    private EditText title, outline, language, runTime;
    private MovieList list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
          key = extras.getString("key");
          Log.v("Passed key", key);
        } else{
            Log.v("Add Item Error", "NULL Key");
        }

        list = MovieList.getInstance();

        //Form elements
        title =(EditText)findViewById(R.id.addEditTextTitle);
        outline =(EditText)findViewById(R.id.addEditTextOutline);
        language =(EditText)findViewById(R.id.addEditTextLang);
        runTime =(EditText)findViewById(R.id.addEditTextTime);

        //Set up rating spinner
        ratingSpinner = (Spinner) findViewById(R.id.addSpinnerRating);

        ArrayAdapter<CharSequence> rAdapter = ArrayAdapter.createFromResource(this,
                R.array.ratings_array, android.R.layout.simple_spinner_item);
        rAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ratingSpinner.setAdapter(rAdapter);

        //Set up type spinner
        typeSpinner = (Spinner) findViewById(R.id.addTypeSpinner);

        ArrayAdapter<CharSequence> tAdapter = ArrayAdapter.createFromResource(this,
                R.array.types_array, android.R.layout.simple_spinner_item);
        tAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(tAdapter);
    }

    public void addItemClick(View view){
        Log.v("Add item", "Add button clicked");
        // add validation ------------------------------------------------------- todo
        MovieEntry entry = new MovieEntry(
                title.getText().toString(),
                typeSpinner.getItemAtPosition(typeSpinner.getSelectedItemPosition()).toString(),
                outline.getText().toString(),
                ratingSpinner.getItemAtPosition(ratingSpinner.getSelectedItemPosition()).toString(),
                language.getText().toString(),
                Integer.parseInt(runTime.getText().toString())
        );
        list.newEntry(key, entry);
        list.saveList();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
