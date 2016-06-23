package com.example.oscarrojas.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvlItems;
    EditText txtNewItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        lvlItems = (ListView)findViewById(R.id.lvItems);
        lvlItems.setAdapter(itemsAdapter);
        txtNewItem = (EditText) findViewById(R.id.lblEditText);
        setupListViewListener();
    }

    private void populateArrayItems(){
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
    }

    private void setupListViewListener() {
        lvlItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
        lvlItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                i.putExtra("selectedItem", (String)parent.getItemAtPosition(position));
                startActivityForResult(i, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Intent refresh = new Intent(this, MainActivity.class);
            startActivity(refresh);
            this.finish();
        }
    }

    private void readItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try{
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        }catch(IOException e){
            items = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void onAddItem(View view) {
        itemsAdapter.add(txtNewItem.getText().toString());
        txtNewItem.setText("");
        writeItems();
    }
}
