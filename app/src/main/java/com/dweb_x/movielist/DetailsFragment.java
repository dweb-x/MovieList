package com.dweb_x.movielist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * @author : D.Carruthers
 * @version 1.0
 * Date: 28/02/2015.
 * email: dave@dweb-x.com
 */
public class DetailsFragment extends android.support.v4.app.Fragment{
    MovieList list = MovieList.getInstance(getActivity());
    String[] keyList = list.keys();

    public static DetailsFragment newInstance(int index){
        DetailsFragment f = new DetailsFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);
        return f;
    }

    public int getShownIndex(){
        return getArguments().getInt("index");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ScrollView scroll = new ScrollView(getActivity());
        TextView text = new TextView(getActivity());
        int padding = (int)
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        4, getActivity().getResources().getDisplayMetrics());
        text.setPadding(padding, padding, padding, padding);
        scroll.addView(text);
        //Output Format here ------------------------------------------------------------ !todo
        text.setText(list.getEntry(keyList[getShownIndex()]).toString());
        return scroll;
    }
}
