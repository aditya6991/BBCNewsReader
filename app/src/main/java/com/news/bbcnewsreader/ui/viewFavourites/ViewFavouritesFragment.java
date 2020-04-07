package com.news.bbcnewsreader.ui.viewFavourites;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;

import com.news.bbcnewsreader.FavListView;
import com.news.bbcnewsreader.R;
import com.news.bbcnewsreader.service.BBCReaderDbHelper;

import java.util.ArrayList;

public class ViewFavouritesFragment extends ListFragment implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {

    private BBCReaderDbHelper bbcReaderDbHelper;
    private ArrayList<String> lst;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        bbcReaderDbHelper = new BBCReaderDbHelper(container.getContext());
        final View view = inflater.inflate(R.layout.fragment_viewfavourites, container, false);
        lst = bbcReaderDbHelper.readFavourites();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, lst);
        setListAdapter(arrayAdapter);
        getListView().setOnItemClickListener(this);
        getListView().setOnItemLongClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent =  new Intent(getActivity().getApplicationContext(), FavListView.class);
        String name = (String) getListView().getAdapter().getItem(position);
        intent.putExtra("name",name);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getActivity().getApplicationContext().startActivity(intent);
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        String name = (String) getListView().getAdapter().getItem(position);
        String idFav = bbcReaderDbHelper.getFavIdByName(name);
        bbcReaderDbHelper.deleteFavouritesList(Integer.parseInt(idFav));
        Toast toast = Toast.makeText(getActivity().getApplicationContext(),"Deletion Done !!",Toast.LENGTH_SHORT);
        toast.show();
        return true;
    }
}
