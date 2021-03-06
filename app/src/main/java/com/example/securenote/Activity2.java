package com.example.securenote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.securenote.model.NoteModel;
import com.example.securenote.utils.SharedPrefs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class Activity2 extends AppCompatActivity {

    //shared_prefs
    private EditText titleText;
    private EditText noteText;
    private Button saveButton;
    private int randomID;
    private String title;
    private String description;
    private SharedPrefs prefs;


    public static final String SHARED_PREFS = "sharedprefs";
    public static final String TITLE = "title";
    public static final String NOTE = "note";

    private Boolean isEditing;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);




        Toolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //sharedprefs
        titleText =(EditText) findViewById(R.id.title_Text);
        noteText =(EditText) findViewById(R.id.note_text);
        //saveButton =(Button) findViewById(R.id.saveBtn);
        prefs = new SharedPrefs(this);


        //get extras
        Intent intent = getIntent();
         title = intent.getStringExtra("TITLE");
         description = intent.getStringExtra("DESCRIPTION");
         randomID = intent.getIntExtra("ID",0);

       //isEditing
        if (title != null && description != null){
            isEditing = true;
            titleText.setText(title);
            noteText.setText(description);
        }
        else {
            //Add new note
            isEditing = false;



        }






    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.save){
            saveData();
            onBackPressed();



        }
        if (id == R.id.delete){
            NoteModel note = new NoteModel();
            note.setRandomID(randomID);
            note.setTitle(title);
            note.setDescription(description);

            SimpleDateFormat sdf = new SimpleDateFormat("EEE,d MMM yyyy HH:mm", Locale.getDefault());
            String currentDateandTime = sdf.format(new Date());

            note.setTime(currentDateandTime);





            Dialog alertdialog = new Dialog(note);
            alertdialog.show(getSupportFragmentManager(), "alertdialog");





        }
        return super.onOptionsItemSelected(item);
    }




    public void saveData(){
       /** SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("title", titleText.getText().toString());
        editor.putString(NOTE, noteText.getText().toString());
        editor.apply(); **/

       /** getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE).edit().putString("title", titleText.getText().toString()).commit();
        getSharedPreferences("sharedprefs", Context.MODE_PRIVATE).edit().putString("note", noteText.getText().toString()).commit();**/
       //note model that will contain saved data
        NoteModel note = new NoteModel();
        note.setTitle(titleText.getText().toString());
        note.setDescription(noteText.getText().toString());
        //Set timestamp
        SimpleDateFormat sdf = new SimpleDateFormat("EEE,d MMM yyyy HH:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        note.setTime(currentDateandTime);



        //note.setImg(R.drawable.note_icon);

        SharedPrefs prefs = new SharedPrefs(this);
        if(isEditing==false){
            note.setRandomID(new Random().nextInt());//generate ID for new note

            prefs.addNote(note);

        }
        else{

            note.setRandomID(randomID);
            prefs.updateNote(note);
        }


        Toast.makeText(this,"Note saved",Toast.LENGTH_SHORT).show();





    }




   /* public void loadData(){
       SharedPreferences sharedPreferences = getSharedPreferences("sharedprefs",MODE_PRIVATE);
        text1 = sharedPreferences.getString(TITLE, "");
        text2 = sharedPreferences.getString(NOTE, "");
        text1 = getSharedPreferences("sharedprefs", Context.MODE_PRIVATE).getString("title","");
        text2 = getSharedPreferences("sharedprefs", Context.MODE_PRIVATE).getString("note","");

    }

    public void updateViews(){
        titleText.setText(text1);
        noteText.setText(text2);

    }
*/

}
