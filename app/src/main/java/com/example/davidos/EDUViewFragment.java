package com.example.davidos;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class EDUViewFragment extends Fragment {

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView listView;
    private View realView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.view, container, false);
        return (ViewGroup) view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.listViewView);
        realView = view;
        items = Days.today;
//        notify(items.get(0));
        itemsAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);
        setUpViewListener();
    }


    private void setUpViewListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Context context = view.getContext();
                Toast.makeText(context, "Completed", Toast.LENGTH_SHORT).show();
                Days.completeEDU(i);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }


    private void notify(String message) {
        Context context = getContext();
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


}
