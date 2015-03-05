package com.dweb_x.movielist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    private Spinner ratingSpinner, typeSpinner;
    private EditText title, outline, language, runTime;
    private Button btn;
    private boolean[] isValid = new boolean[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);
        Bundle extras = getIntent().getExtras();
        key = extras.getString("key");

        //Form elements
        title =(EditText)findViewById(R.id.addEditTextTitle);
        outline =(EditText)findViewById(R.id.addEditTextOutline);
        language =(EditText)findViewById(R.id.addEditTextLang);
        runTime =(EditText)findViewById(R.id.addEditTextTime);
        btn = (Button)findViewById(R.id.addItmButton);

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

        //Form element change listeners --------------------------------------------------------
        title.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                isValid[0] = validText(title, s);
                Log.v("isValid",Boolean.toString(isValidForm()));
                btn.setEnabled(isValidForm());
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        outline.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                isValid[1] = validText(outline, s);
                btn.setEnabled(isValidForm());
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        language.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                isValid[2] = validText(language, s);
                btn.setEnabled(isValidForm());
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        runTime.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                isValid[3] = validNum(s);
                btn.setEnabled(isValidForm());
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
    }

    /*
     *  returns true if string 1 to 25 chars. Highlights background if invalid
     */
    private boolean validText(EditText text, Editable s) {
        if((s.length() > 0) && (s.length() < 26)) {
            text.setBackgroundColor(getResources().getColor(R.color.trans));
            return true;
        }
        else {
            text.setBackgroundColor(getResources().getColor(R.color.myholored));
            return false;
        }
    }

    /*
     *  returns true if non zero int. Highlights background if invalid
     */
    private boolean validNum(Editable s){
        if((s.length() > 0) && (!s.toString().equals("0"))) {
            runTime.setBackgroundColor(getResources().getColor(R.color.trans));
            return true;
        }
        else {
            runTime.setBackgroundColor(getResources().getColor(R.color.myholored));
            return false;
        }
    }

    /*
     *  returns true if every form element contains valid data. used to enable button.
     */
    private boolean isValidForm(){
        return isValid[0] && isValid[1] &&
                (isValid[2] || language.getText().length() > 0)&& isValid[3];
    }

    /**
     * Can only be called when form data is valid and button has been enabled.
     * Throws NumberFormatException should be impossible so try block omitted.
     * @param view button that generates event
     */
    public void addItemClick(View view){
        MovieEntry entry = new MovieEntry(
                title.getText().toString(),
                typeSpinner.getItemAtPosition(typeSpinner.getSelectedItemPosition()).toString(),
                outline.getText().toString(),
                ratingSpinner.getItemAtPosition(ratingSpinner.getSelectedItemPosition()).toString(),
                language.getText().toString(),
                Integer.parseInt(runTime.getText().toString())
        );
        MovieList.getInstance().newEntry(key, entry);

        //restarts after delete all.
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
