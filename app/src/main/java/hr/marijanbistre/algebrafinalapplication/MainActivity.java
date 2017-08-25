package hr.marijanbistre.algebrafinalapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.marijanbistre.algebrafinalapplication.dialog.ChangeNameDialog;
import hr.marijanbistre.algebrafinalapplication.dialog.ExitDialog;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tvName)
    TextView tvName;

    public MediaPlayer mediaPlayer;

    public static final String MUTE = "MUTE";
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // CREATE AND START MEDIA PLAYER
        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.background_game);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        ButterKnife.bind(this);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(MUTE, false).apply();
    }

    // OPEN ChangeNameDialog WHEN CLICKING TextView "change name"
    @OnClick(R.id.tvChange)
    public void changeName() {
        ChangeNameDialog dialog = new ChangeNameDialog();
        dialog.show(getFragmentManager(), "Enter Name Dialog");
    }

    // SAVING ENTERED NAME IN SHARED PREFERENCES USING KEY "name" FROM ChangeNameDialog
    public void setPreferences(String name) {
        tvName.setText(name);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("name", name).apply();
    }

    // FUNCTION THATS GET SHARED PREFERENCES
    private void getPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        if (sharedPreferences.contains("name")) {
            tvName.setText(sharedPreferences.getString("name", null));
        }
        else {
            setPreferences("guest");
            tvName.setText(sharedPreferences.getString("name", null));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // DEALING WITH OPTIONS ITEMS
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        // CLICKING ON SETTINGS
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, CustomPreferenceActivity.class));
        }
        // CLICKING ON CLOSE
        if (id == R.id.action_close) {
            ExitDialog exitDialog = new ExitDialog();
            exitDialog.setCancelable(false);
            exitDialog.show(getFragmentManager(), "Exit dialog");
        }
        // CLICKING ON MUTE/UNMUTE
        if (id == R.id.action_mute) {

            if (String.valueOf(sharedPreferences.getBoolean(MUTE, Boolean.parseBoolean(true+""))) == "false") {
                mediaPlayer.setVolume(0,0);
                item.setIcon(getResources().getDrawable(R.drawable.ic_volume_off_white_24dp));
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(MUTE, true).apply();
            } else {
                mediaPlayer.setVolume(0.5f,0.5f);
                item.setIcon(getResources().getDrawable(R.drawable.ic_volume_up_white_24dp));
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(MUTE, false).apply();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    // START NEW ACTIVITY WHEN CLICKING BUTTON "PLAY"
    @OnClick(R.id.btnPlay)
    public void btnPlay() {
        startActivity(new Intent(MainActivity.this, InstructionActivity.class));
    }

    // SEE ALL SCORES BUTTON
    @OnClick(R.id.ibScore)
    public void score() {
        Intent intent = new Intent(MainActivity.this, ShowScoresActivity.class);
        intent.putExtra("activity", "MainActivity");
        startActivity(intent);
    }

    // GET PREFERENCES WHEN STARTING APPLICATION
    @Override
    protected void onResume() {
        super.onResume();
        getPreferences();
    }

    // SET DIALOG WHEN PRESSED BACK BUTTON
    @Override
    public void onBackPressed () {
        ExitDialog exitDialog = new ExitDialog();
        exitDialog.setCancelable(false);
        exitDialog.show(getFragmentManager(), "Exit dialog");
    }
}
