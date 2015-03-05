package com.dweb_x.movielist;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * @author : D.Carruthers
 * @version 1.0
 * Date: 28/02/2015.
 * email: dave@dweb-x.com
 */
public class MainFragment extends android.support.v4.app.ListFragment{
    MovieList list;
    String[] keyList;
    ArrayAdapter<String> adapter;
    MenuItem item;
    boolean mDuelPane;
    int curIndex = 0;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //init movie list
        list = MovieList.getInstance(getActivity());

        keyList = list.keys();

        adapter = new
                ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
                keyList
        );
        setListAdapter(adapter);
        adapter.notifyDataSetChanged();
        View detailsFrame = getActivity().findViewById(R.id.details);

        //detailsFrame.setBackgroundColor(getResources().getColor(R.color.menu_background));
        mDuelPane = detailsFrame != null &&
                detailsFrame.getVisibility() == View.VISIBLE;

        if(savedInstanceState != null){
            curIndex = savedInstanceState.getInt("curIndex");
        }

        if(mDuelPane){
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            if(!list.isEmpty())
                showDetails(curIndex);
        }
        //for delete menu on long click
        registerForContextMenu(getListView());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v == getListView()) {
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.list_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.edit:
                // edit stuff here ------------------------------------------------- todo
                return true;
            case R.id.delete:
                //confirm dialog
                this.item = item;
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    /*
     *  Listener for yes/no delete item confirm dialog.
     */
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                    list.removeItem(keyList[info.position]);
                    reload();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curIndex", curIndex);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.v("ListClick","pos:" + position);
        showDetails(position);
    }

    private void showDetails(int index) {
        curIndex = index;
        if(mDuelPane){
            getListView().setItemChecked(index, true);
            DetailsFragment details = (DetailsFragment)
                    getFragmentManager().findFragmentById(R.id.details);
            if(details == null || details.getShownIndex() != index){
                details = DetailsFragment.newInstance(index);

                android.support.v4.app.FragmentTransaction ft =
                        getFragmentManager().beginTransaction();
                ft.replace(R.id.details, details);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        } else {
            Intent intent = new Intent();
            intent.setClass(getActivity(), DetailsActivity.class);
            intent.putExtra("index", index);
            startActivity(intent);
        }
    }

    /*
     * Reload this when list changes.
     * Probably a much better way of doing this. If you know please let me know.
     */
    public void reload(){
        Intent refresh = new Intent(getActivity(), MainActivity.class);
        startActivity(refresh);
        getActivity().finish();

    }


}
