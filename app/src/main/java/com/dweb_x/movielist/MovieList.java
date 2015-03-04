package com.dweb_x.movielist;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author : D.Carruthers
 * @version : 1.0
 * Date: 28/02/2015.
 * email: dave@dweb-x.com
 */
public class MovieList { //singleton

    private static MovieList instance;
    private static Map<String,MovieEntry> movies;
    private File CFG_FILE;
    private static Thread loadThread, saveThread;
    private Context context;

    /**
     * Constructor uses singleton pattern. call getInstance() for new instance.
     */
    private MovieList(){
        movies = new LinkedHashMap<>();
    }

    /**
     * Checks if key is contained in movies.
     * @param key to check.
     * @return true if key not contained in movies.
     */
    public boolean isKeyAvailable(String key){
        return !movies.containsKey(key);
    }

    /**
     * Adds movie entry to collection if key is not already in the table.
     * @param key chosen by user to id the movie.
     * @param entry MovieEntry object to be added.
     * @return true if successfully added.
     */
    public boolean newEntry(String key, MovieEntry entry){
        //if key unavailable return
        if(movies.containsKey(key)) return false;
        else {
            movies.put(key, entry);
            return true;
        }
    }

    /**
     * Saves the movie collection to file. Spins if load is in progress. Outputs Serialized
     * map & contents.
     */
    public synchronized void saveList(){
        try {
            saveThread = new Thread(new Runnable() {
                public void run() {
                    try {
                        while(loadThread != null){
                            Thread.sleep(100);
                        }
                        FileOutputStream out = new FileOutputStream(CFG_FILE);
                        ObjectOutputStream os = new ObjectOutputStream(out);
                        os.writeObject(movies);
                        out.close();
                        os.close();
                    } catch (Exception e) {
                        if(e.getMessage() != null)
                        Log.e("Save Error:", e.getMessage());
                    }
                }
            });
            saveThread.start();
        } catch(Exception e){Log.e("Error:", e.getMessage());}
    }

    /**
     * Loads the movie collection from file. spins if save is in progress. Reconstructs previous instance
     * of movies from Serialized object.
     */
    @SuppressWarnings("unchecked")
    public synchronized void loadList(){
        try {
            loadThread = new Thread(new Runnable() {
                public void run() {
                    try{
                        while(saveThread != null){
                            Thread.sleep(100);
                        }
                        FileInputStream in = new FileInputStream(CFG_FILE);
                        ObjectInputStream os = new ObjectInputStream(in);
                        movies = (Map<String,MovieEntry>) os.readObject();
                        in.close();
                        os.close();
                    } catch(Exception e){Log.e("Load Error:", e.getMessage());}
                }
            });

            loadThread .start();
        } catch(Exception e){Log.e("Error:", e.getMessage());}
    }

    /**
     * Removes the item with the passed in key from the collection.
     * @param key of item to be removed
     * @return true if item is in collection
     */
    public boolean removeItem(String key){
        if(!movies.containsKey(key))return false;
        else{
            movies.remove(key);
            return true;
        }
    }

    /**
     * Gets the entry with the passed in key.
     * @param key of item to recover
     * @return MovieItem or null if not in collection
     */
    public MovieEntry getEntry(String key){
        MovieEntry entry;
        if(movies.containsKey(key)){
            entry = movies.get(key);
            return entry;
        } else
            return null;
    }

    /**
     * Use of singleton pattern ensures only one running instance
     * @return instance of MovieList
     */
    public static synchronized MovieList getInstance(){
        if(instance == null){
            instance = new MovieList();
        }
        return instance;
    }

    /**
     * Use of singleton pattern ensures only one running instance.
     * @param context android content context used for file location.
     * @return instance of MovieList
     */
    public static synchronized MovieList getInstance(Context context){
        if(instance == null){
            instance = new MovieList();
            instance.setContext(context);
        }
        return instance;
    }

    /**
     * allows the android context to be set for IO.
     * @param context android content context used for file location.
     */
    public void setContext(Context context){
        instance.context = context;
        CFG_FILE = new File(context.getExternalFilesDir(null), "movlist.cfg");
    }

    /**
     * Collection output as string
     * @return movies list
     */
    @Override
    public String toString() {
        return movies.toString();
    }

    /**
     * Keys to array.
     * @return keys as string array.
     */
    public String[] keys(){
        return movies.keySet().toArray(new String[movies.size()]);
    }

}
