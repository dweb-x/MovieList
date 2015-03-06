package com.dweb_x.movielist;

import android.app.AlertDialog;
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
    private static MovieList list;
    private static String[] keyList;
    private static int listIndex = 0;
    private static boolean isHorizontal;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //initialize movie list
        list = MovieList.getInstance(getActivity());

        keyList = list.keys();

        final ArrayAdapter<String> adapter = new
                ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,// fix layout ------------------ todo
                keyList
        );
        setListAdapter(adapter);
        adapter.notifyDataSetChanged();
        View detailsFrame = getActivity().findViewById(R.id.details);

        isHorizontal = detailsFrame != null &&
                detailsFrame.getVisibility() == View.VISIBLE;

        if(savedInstanceState != null){
            listIndex = savedInstanceState.getInt("listIndex");
        }

        if(isHorizontal){
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            if(!list.isEmpty())
                showDetails(listIndex);
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
        final AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.edit:
                // Allow user to edit items ------------------------------------------------- todo
                return true;
            case R.id.delete:
                //confirm dialog
                AlertDialog.Builder deleteConfirmDialog = new AlertDialog.Builder(getActivity());
                deleteConfirmDialog.setMessage("Are you sure?");
                deleteConfirmDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                list.removeItem(keyList[info.position]);
                                reload();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                });

                deleteConfirmDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                deleteConfirmDialog.show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("listIndex", listIndex);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.v("ListClick", "pos:" + position);
        showDetails(position);
    }

    private void showDetails(int index) {
        listIndex = index;
        if(isHorizontal){
            getListView().setItemChecked(index, true);
            DetailsFragment details = (DetailsFragment)
                    getFragmentManager().findFragmentById(R.id.details);
            if(details == null || details.getShownIndex() != index){
                details = DetailsFragment.newInstance(index);

                android.support.v4.app.FragmentTransaction ft =
                        getFragmentManager().beginTransaction();
                ft.replace(R.id.details, details);
                //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
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
