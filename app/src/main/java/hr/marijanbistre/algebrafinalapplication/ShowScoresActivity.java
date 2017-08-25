package hr.marijanbistre.algebrafinalapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.marijanbistre.algebrafinalapplication.database.DatabaseHelper;
import hr.marijanbistre.algebrafinalapplication.providers.ScoreContentProvider;

public class ShowScoresActivity extends AppCompatActivity {

    private SimpleCursorAdapter adapter;
    private String name;
    private String gender;
    private int age;
    private int score;
    SharedPreferences sharedPreferences;

    @BindView(R.id.listView)
    ListView lvScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_scores);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ShowScoresActivity.this);

        // GET NAME
        if (sharedPreferences.contains("name")) {
            name = sharedPreferences.getString("name", null);
        }

        // GET AGE
        if (sharedPreferences.contains("age")) {
            age = Integer.parseInt(sharedPreferences.getString("age", null));
        } else {
            age = 0;
        }

        // GET GENDER VALUE AND SET TEXT
        if (sharedPreferences.contains("gender")) {
            gender = sharedPreferences.getString("gender", null);
            assert gender != null;
            switch (gender) {
                case "1":
                    gender = "Male";
                    break;
                case "2":
                    gender = "Female";
                    break;
                case "3":
                    gender = "Other";
                    break;
                default:
                    Toast.makeText(this, "No gender set", Toast.LENGTH_SHORT).show();
            }
        } else {
            gender = "Other";
        }

        // GET SCORE
        Intent intent = getIntent();
        if (intent.hasExtra("score")) {
            score = Integer.parseInt(intent.getStringExtra("score"));
        }

        ButterKnife.bind(this);
        initList();

        // DON'T ASK ANY DATA IF ENTERING FROM MAIN ACTICITY
        if (intent.hasExtra("activity")) {
            refreshList();
        } else {
            if (inputOK()){
                insertScore(name, age, gender, score);
            }
        }



    }

    // CONNECT COLUMN VALUES WITH TEXTVIEWS IN XML FILE
    private void initList() {
        String[] columns = {
                DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_AGE, DatabaseHelper.COLUMN_GENDER, DatabaseHelper.COLUMN_SCORE };
        int[] viewIds = {R.id.tvName, R.id.tvAge, R.id.tvGender, R.id.tvScore};
        adapter = new SimpleCursorAdapter(this, R.layout.list_score, null, columns, viewIds, 0);
        lvScore.setAdapter(adapter);
    }

    // GET DATA
    private void refreshList() {
        CursorLoader cursorLoader = new CursorLoader(this, ScoreContentProvider.CONTENT_URI,
                null, null, null, DatabaseHelper.COLUMN_SCORE + " DESC");
        Cursor cursor = cursorLoader.loadInBackground();
        adapter.swapCursor(cursor);
    }

    // CHECK IF ALL DATA IS OK
    private boolean inputOK() {
        if (name == null) {
            Toast.makeText(this, "No name", Toast.LENGTH_SHORT).show();
            return false;

        }
        if (age < 0)  {
            Toast.makeText(this, "No age", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (gender.length() == 0){
            Toast.makeText(this, "No gender", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (score < 0) {
            Toast.makeText(this, "No score", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // INSERT DATA IN DATABASE
    private void insertScore(String name, int age, String gender, int score) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, name);
        values.put(DatabaseHelper.COLUMN_AGE, age);
        values.put(DatabaseHelper.COLUMN_GENDER, gender);
        values.put(DatabaseHelper.COLUMN_SCORE, score);
        getContentResolver().insert(ScoreContentProvider.CONTENT_URI, values);

        refreshList();
    }

    // BACK BUTTON
    @OnClick(R.id.ibBack)
    public void goBack() {
        onBackPressed();
        finish();
    }

}
