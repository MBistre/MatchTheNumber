package hr.marijanbistre.algebrafinalapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScoreActivity extends AppCompatActivity {

    Intent intent;
    String scoreForTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        ButterKnife.bind(this);

        TextView tvCongratulations = (TextView) findViewById(R.id.tvCongratulation);
        TextView tvHighscoreText = (TextView) findViewById(R.id.tvHighscoreText);
        TextView tvHighscore = (TextView) findViewById(R.id.tvHighscore);
        TextView tvNewHighscore = (TextView) findViewById(R.id.tvNewHighscore);
        TextView tvScore = (TextView) findViewById(R.id.tvScoreList);
        TextView tvScoreValue = (TextView) findViewById(R.id.tvScoreValue);

        tvCongratulations.setVisibility(View.INVISIBLE);
        tvHighscoreText.setVisibility(View.INVISIBLE);
        tvHighscore.setVisibility(View.INVISIBLE);
        tvNewHighscore.setVisibility(View.INVISIBLE);
        tvScoreValue.setVisibility(View.INVISIBLE);
        tvScore.setVisibility(View.INVISIBLE);

        // CHECK IF PLAYER HAS SET HIGHSCORE
        intent = getIntent();
        if (intent.hasExtra("score")) {
            String score = intent.getStringExtra("score");
            tvScore.setVisibility(View.VISIBLE);
            tvScoreValue.setVisibility(View.VISIBLE);
            tvScoreValue.setText(score);
            scoreForTable = score;
        } else if (intent.hasExtra("highscore")) {
            String highscore = intent.getStringExtra("highscore");
            tvCongratulations.setVisibility(View.VISIBLE);
            tvHighscoreText.setVisibility(View.VISIBLE);
            tvHighscore.setVisibility(View.VISIBLE);
            tvNewHighscore.setVisibility(View.VISIBLE);
            tvHighscore.setText(highscore);
            scoreForTable = highscore;
        }
    }

    // RESTART BUTTON
    @OnClick(R.id.ibRestart)
    public void restart() {
        startActivity(new Intent(ScoreActivity.this, InstructionActivity.class));
        finish();
    }

    // HOME BUTTON
    @OnClick(R.id.ibHome)
    public void goHome() {
        Intent intent = new Intent(ScoreActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    // SEE ALL SCORES BUTTON
    @OnClick(R.id.ibScore)
    public void score() {
        Intent intent = new Intent(ScoreActivity.this, ShowScoresActivity.class);
        intent.putExtra("score", scoreForTable);
        startActivity(intent);
    }

    // DISABLED BACK BUTTON
    @Override
    public void onBackPressed() {
    }
}
