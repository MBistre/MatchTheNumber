package hr.marijanbistre.algebrafinalapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private TextView tvOriginal;
    private TextView tvSelectionOne;
    private TextView tvSelectionTwo;
    private TextView tvSelectionThree;
    private TextView tvSelectionFour;
    private TextView tvScore;
    private TextView tvLives;
    private CountDownTimer countDownTimer;
    int lives = 3;
    int score = 0;
    int timer;
    private static final int CORRECT_SOUND_ID = 1;
    private static final int WRONG_SOUND_ID = 2;
    private SoundPool soundPool;
    private Map<Integer, Integer> soundMap;
    private static final int SOUND_PRIORITY = 1;
    private static final int SOUND_QUALITY = 100;
    private static final int MAX_STREAMS = 4;
    private int volume;
    Context context;
    private static final String HIGH_SCORE = "HIGH SCORE";
    private int highscore;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        tvOriginal = (TextView) findViewById(R.id.tvOriginal);
        tvSelectionOne = (TextView) findViewById(R.id.tvSelectionOne);
        tvSelectionTwo = (TextView) findViewById(R.id.tvSelectionTwo);
        tvSelectionThree = (TextView) findViewById(R.id.tvSelectionThree);
        tvSelectionFour = (TextView) findViewById(R.id.tvSelectionFour);
        tvScore = (TextView) findViewById(R.id.tvScoreList);
        tvLives = (TextView) findViewById(R.id.tvLives);

        tvSelectionOne.setOnClickListener(clickListener);
        tvSelectionTwo.setOnClickListener(clickListener);
        tvSelectionThree.setOnClickListener(clickListener);
        tvSelectionFour.setOnClickListener(clickListener);


        context = getApplicationContext();

        // HASHMAP WITH SOUNDS FOR CORRECT AND WRONG ANSWER
        soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, SOUND_QUALITY);
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        soundMap = new HashMap<Integer, Integer>();
        soundMap.put(CORRECT_SOUND_ID, soundPool.load(context, R.raw.correct_sound, SOUND_PRIORITY));
        soundMap.put(WRONG_SOUND_ID, soundPool.load(context, R.raw.wrong_sound, SOUND_PRIORITY));

//        tvOriginal.setBackgroundColor(Color.parseColor(color));


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(GameActivity.this);

        if (sharedPreferences.contains(HIGH_SCORE)) {
            highscore = sharedPreferences.getInt(HIGH_SCORE, 0);
        }
        else {
            highscore = 0;
        }


        tvScore.setText("" + score);
        tvLives.setText("" + lives);

        newRound();
    }

    // GET RANDOM TWO DIGITS NUMBER
    public int getRandomNumber() {
        Random random = new Random();
        return random.nextInt(100 - 10) + 10;
    }

    // SET NUMBER ON FIRST SELECTION (TEXTVIEW)
    public void setSelectionOne() {
        int selectionOne = getRandomNumber();
        tvSelectionOne.setText(String.valueOf(selectionOne));
        check(tvSelectionOne, tvSelectionTwo);
        check(tvSelectionOne, tvSelectionThree);
        check(tvSelectionOne, tvSelectionFour);
    }

    // SET NUMBER ON SECOND SELECTION (TEXTVIEW)
    public void setSelectionTwo() {
        int selectionTwo = getRandomNumber();
        tvSelectionTwo.setText(String.valueOf(selectionTwo));
        check(tvSelectionTwo, tvSelectionOne);
    }

    // SET NUMBER ON THIRD SELECTION (TEXTVIEW)
    public void setSelectionThree() {
        int selectionThree = getRandomNumber();
        tvSelectionThree.setText(String.valueOf(selectionThree));
        check(tvSelectionThree, tvSelectionOne);
        check(tvSelectionThree, tvSelectionTwo);

    }

    // SET NUMBER ON FOURTH SELECTION (TEXTVIEW)
    public void setSelectionFour() {
        int selectionFour = getRandomNumber();
        tvSelectionFour.setText(String.valueOf(selectionFour));
        check(tvSelectionFour, tvSelectionOne);
        check(tvSelectionFour, tvSelectionTwo);
        check(tvSelectionFour, tvSelectionThree);

    }

    // CHECK IF OTHER SELECTION (TEXTVIEW) HAS THE SAME NUMBER
    // IF IT DOES, GET ANOUTHER NUMBER
    public void check(TextView tv1, TextView tv2) {
        if(tv1.getText().toString().equals(tv2.getText().toString())) {
            int number = getRandomNumber();
            tv1.setText(String.valueOf(number));
            check(tv1, tv2);
        }
        else {
            tv1.setText(tv1.getText().toString());
        }
    }

    // TAKE ONE OF FOUR NUMBER FROM SELECTIONS AND SET IT AS ORIGINAL (TOP ONE)
    public void setOriginal() {
        Random random = new Random();
        int min = 1;
        int max = 5;
        int number = random.nextInt(max - min) + 1;

        switch (number) {
            case 1:
                tvOriginal.setText(tvSelectionOne.getText().toString());
                break;
            case 2:
                tvOriginal.setText(tvSelectionTwo.getText().toString());
                break;
            case 3:
                tvOriginal.setText(tvSelectionThree.getText().toString());
                break;
            case 4:
                tvOriginal.setText(tvSelectionFour.getText().toString());
                break;
            default:
                Toast.makeText(this, "UNKNOWN ERROR", Toast.LENGTH_SHORT).show();
        }

    }

    // SET NUMBER ON EVERY TEXTVIEW
    private void newRound() {
        setSelectionOne();
        setSelectionTwo();
        setSelectionThree();
        setSelectionFour();
        setOriginal();

        if (score < 100) { timer = 3000; }
        else if (score >= 100 && score < 200) { timer = 2000; }
        else { timer = 1000; }

        countDownTimer = new CountDownTimer(timer, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {  }

            @Override
            public void onFinish() {
                wrongAnswer();
            }
        };
        countDownTimer.start();
    }

    // DETECT WHICH BUTTON WAS CLICKED
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tvSelectionOne:
                    countDownTimer.cancel();
                    clickedSelection(tvSelectionOne);
                    break;
                case R.id.tvSelectionTwo:
                    countDownTimer.cancel();
                    clickedSelection(tvSelectionTwo);
                    break;
                case R.id.tvSelectionThree:
                    countDownTimer.cancel();
                    clickedSelection(tvSelectionThree);
                    break;
                case R.id.tvSelectionFour:
                    countDownTimer.cancel();
                    clickedSelection(tvSelectionFour);
                    break;
                default:
                    throw new RuntimeException("UNKNOWN BUTTON ID");
            }
        }
    };

    // CHECK IF ANSWER IS CORRECT OR WRONG
    private void clickedSelection (TextView textView) {

        if (textView.getText().toString().equals(tvOriginal.getText().toString())) {
            correctAnswer();
        } else {
            wrongAnswer();
        }
    }

    // CORRECT ANSWER --> SCORE + 10
    private void correctAnswer() {
        score = score + 10;
        tvScore.setText("" + score);
        if (soundPool != null) {
            soundPool.play(CORRECT_SOUND_ID, volume, volume, SOUND_PRIORITY, 0, 1F);
        }
        newRound();
    }

    // WRONG ANSWER --> LIVES - 1
    private void wrongAnswer() {
        lives = lives - 1;
        Intent intent = new Intent(GameActivity.this, ScoreActivity.class);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(GameActivity.this);

        if (soundPool != null) {
            soundPool.play(WRONG_SOUND_ID, volume, volume, SOUND_PRIORITY, 0, 1F);
        }
        if (lives > 0) {
            tvLives.setText("" + lives);
            newRound();
        } else {
            if (score > highscore) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt(HIGH_SCORE, score).apply();
                intent.putExtra("highscore", "" + score);
            } else {
                intent.putExtra("score", "" + score);
            }
            startActivity(intent);
            finish();
        }
    }

    // DISABLE RETURNING TO THE GAME WITH BACK BUTTON
    @Override
    public void onPause() {
        super.onPause();
        countDownTimer.cancel();
        finish();
    }

    // DISABLE BACK BUTTON
    @Override
    public void onBackPressed() {
    }
}
