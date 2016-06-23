package com.example.oscarrojas.simpletodo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class EditItemActivity extends AppCompatActivity {
    EditText txtEditField;
    String selectedItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        selectedItem = getIntent().getExtras().getString("selectedItem");
        txtEditField = (EditText) findViewById(R.id.txtEditItem);
        txtEditField.setText(selectedItem);
    }

    public void onEditItem(View view) {
        replaceLine();
        setResult(RESULT_OK, null);
        this.finish();
    }

    private void replaceLine(){
        File filesDir = getFilesDir();
        File dir = new File(filesDir, "todo.txt");
        try {
                String fileString = FileUtils.readFileToString(dir);
                String finalString = fileString.replace(selectedItem, txtEditField.getText().toString());
                FileUtils.writeStringToFile(dir, finalString);
            }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
