package com.example.davidos;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class AddFragment extends Fragment {

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView listView;
    private Button button;
    private View realView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.add, container, false);
        return (ViewGroup) view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.listView);
        button = view.findViewById(R.id.addButton);
        realView = view;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(view);
            }
        });

        items = Days.allEDU;
        System.out.println("hash code in addfrag");
        System.out.println(items.hashCode());
        itemsAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);
        setUpViewListener();
    }


    private void setUpViewListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Context context = view.getContext();
                Toast.makeText(context, "Item Removed", Toast.LENGTH_SHORT).show();
                itemsAdapter.notifyDataSetChanged();
                Days.removeEDU(i);
                return true;
            }
        });
    }



    private void addItem(View view) {

        EditText input = realView.findViewById(R.id.addEDU);
        String text = input.getText().toString();


        if(!(text.equals(""))) {

            Days.addEDU(text);
            itemsAdapter.notifyDataSetChanged();
            input.setText("");

        }


    }


    private void notify(String message) {
        Context context = getContext();
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}