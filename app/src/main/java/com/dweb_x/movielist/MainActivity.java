package com.dweb_x.movielist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * @author : D.Carruthers
 * @version : 1.0
 * Date: 28/02/2015.
 * email: dave@dweb-x.com
 */

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case R.id.action_settings:
                return true;
            case R.id.action_new:
                Log.v("menuClick", "new");
                Intent intent = new Intent();
                intent.setClass(this, KeyActivity.class);
                startActivity(intent);
                return true;
            case R.id.deleteall:
                AlertDialog.Builder deleteAllConfirm = new AlertDialog.Builder(this);
                deleteAllConfirm.setMessage("Are you sure?")
                        .setPositiveButton("Yes", deleteAllDialogClickListener)
                        .setNegativeButton("No", deleteAllDialogClickListener).show();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    /*
     *  Listener for yes/no delete all item confirm dialog.
     */
    DialogInterface.OnClickListener deleteAllDialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    MovieList.getInstance(getApplicationContext()).removeAllItems();
                    refresh();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

    private void refresh(){
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        finish();
    }
}
