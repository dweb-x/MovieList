package com.dweb_x.movielist;

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
        MovieList list = MovieList.getInstance(this);
        MovieEntry test1 = new MovieEntry();
        test1.setTitle("Test");
        test1.setType("ACTION");
        test1.setRating("PG");
        list.newEntry("test", test1);
        MovieEntry test2 = new MovieEntry();
        test2.setTitle("Testing testing");
        test2.setType("ACTION");
        test2.setRating("18");
        list.newEntry("test2", test2);
        list.saveList();
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

        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_new) {
            Log.v("menuClick", "new");
            Intent intent = new Intent();
            intent.setClass(this, KeyActivity.class);
            //intent.setClass(this, AddItemActivity.class);
            //intent.putExtra("index", index);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
