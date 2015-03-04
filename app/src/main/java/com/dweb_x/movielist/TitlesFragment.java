package com.dweb_x.movielist;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * @author : D.Carruthers
 * @version 1.0
 * Date: 28/02/2015.
 * email: dave@dweb-x.com
 */
public class TitlesFragment extends android.support.v4.app.ListFragment{
    MovieList list;
    String[] keyList;
    boolean mDuelPane;
    int curIndex = 0;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //init movie list
        list = MovieList.getInstance(getActivity());

        keyList = list.keys();

        ArrayAdapter<String> connectArrayToListView = new
                ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
                keyList
        );
        setListAdapter(connectArrayToListView);
        connectArrayToListView.notifyDataSetChanged();
        View detailsFrame = getActivity().findViewById(R.id.details);
        //detailsFrame.setBackgroundColor(getResources().getColor(R.color.menu_background));
        mDuelPane = detailsFrame != null &&
                detailsFrame.getVisibility() == View.VISIBLE;

        if(savedInstanceState != null){
            curIndex = savedInstanceState.getInt("curIndex");
        }

        if(mDuelPane){
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            showDetails(curIndex);
        }
    }

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
}
