package com.dweb_x.movielist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * @author : D.Carruthers
 * @version 1.0
 *          Date: 03/03/2015.
 *          email: dave@dweb-x.com
 */
public class KeyActivity extends ActionBarActivity {

    private TextView keyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addkey);
        keyView = (TextView)findViewById(R.id.keyCheckText);
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
