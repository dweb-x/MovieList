package com.dweb_x.movielist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author : D.Carruthers
 * @version 1.0
 *          Date: 03/03/2015.
 *          email: dave@dweb-x.com
 */
public class KeyActivity extends ActionBarActivity {

    private TextView keyView;
    private MovieList list = MovieList.getInstance(this);
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addkey);
        keyView = (TextView)findViewById(R.id.keyCheckText);
        keyView.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                validText(s.toString());
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
    }
    // Think about having an icon the changes on valid input??? ------------------------ todo
    /*
     *  Enables the button if passed in string is in size range and unique.
     */
    private void validText(String s){
        if(s.length() > 0 && list.isKeyAvailable(s)){
            btn = (Button)findViewById(R.id.keyCheckButton);
            btn.setEnabled(true);
        } else {
            btn.setEnabled(false);
        }

    }

// add method to check key when text view drops focus ------------------------ !!! todo
    /**
     * onClick handler for keyCheck button.
     * Launches AddItem activity passing valid key.
     * @param view called from addkey
     */
    public void btnCheckKey(View view){
        Log.v("checkKey", "Clicked");
        Intent intent = new Intent();
        intent.setClass(this, AddItemActivity.class);
        intent.putExtra("key", keyView.getText().toString());
        startActivity(intent);
    }
}
