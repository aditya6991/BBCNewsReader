package com.news.bbcnewsreader.ui.createFavourites;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.news.bbcnewsreader.BBCListAdaptor;
import com.news.bbcnewsreader.R;
import com.news.bbcnewsreader.dto.BBCEntry;
import com.news.bbcnewsreader.service.BBCReaderDbHelper;

import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;


public class CreateFavouritesFragment extends Fragment {

    private final Logger log = Logger.getLogger(CreateFavouritesFragment.class.getName());
    private  BBCReaderDbHelper bbcReaderDbHelper = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        log.info("jddddddddddddddddddddddd");
        bbcReaderDbHelper = new BBCReaderDbHelper(container.getContext());
        final View view = inflater.inflate(R.layout.fragment_home,container,false);
        final EditText editTextBox = new EditText(getContext());
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle("Favourites")
                .setMessage("Enter a name of your favourites list !!!")
                .setView(editTextBox)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences = getContext().getSharedPreferences("selectedObjs", Context.MODE_PRIVATE);
                        Set<String> lst = sharedPreferences.getStringSet("set",null);
                        if(lst==null || lst.size()==0) {
                            Toast toast = Toast.makeText(getContext(),"Please select some items to save !!!",Toast.LENGTH_SHORT);
                            toast.show();
                        }else{
                            Iterator<String> stringIterator = lst.iterator();
                            while (stringIterator.hasNext()){
                                String str = stringIterator.next();
                                String arr[] = str.split("=");
                                BBCEntry entry = new BBCEntry();
                                entry.setTitle(arr[0]);
                                entry.setDescription(arr[1]);
                                entry.setGuid(arr[2]);
                                entry.setLink(arr[3]);
                                entry.setPubDate(arr[4]);
                                bbcReaderDbHelper.insertIntoDatabase(entry, editTextBox.getText().toString());
                            }
                            Toast.makeText(getContext(),"List created !!!",Toast.LENGTH_SHORT);
                        }
                    }
                }).setNegativeButton("Cancel", null)
                .create();
        dialog.show();
        return  view;
    }
}
