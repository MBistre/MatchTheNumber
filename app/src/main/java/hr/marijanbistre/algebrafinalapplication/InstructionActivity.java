package hr.marijanbistre.algebrafinalapplication;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class InstructionActivity extends AppCompatActivity {

    String [] readySetGo;
    int counter = 0;
    TextView tvInstruction;
    TextView tvNext;
    TextView tvReady;
    ConstraintLayout constraintLayout;
    boolean stopped = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        ButterKnife.bind(this);

        tvInstruction = (TextView) findViewById(R.id.tvInstruction);
        tvNext = (TextView) findViewById(R.id.tvNext);
        tvReady = (TextView) findViewById(R.id.tvReady);
        readySetGo = getResources().getStringArray(R.array.readySetGo);
        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);

        // SHOW TEXTVIWES tvInstruction AND tvNEXT
        // AND HIDE tvReady
        tvInstruction.setVisibility(View.VISIBLE);
        tvNext.setVisibility(View.VISIBLE);
        tvReady.setVisibility(View.INVISIBLE);
    }

    // ON CLICK ANYWHERE ON SCREEN SHOW tvReady AND CALL COUNTDOWN
    @OnClick (R.id.constraintLayout)
    public void nextActivity() {
        constraintLayout.setClickable(false);
        tvInstruction.setVisibility(View.INVISIBLE);
        tvNext.setVisibility(View.INVISIBLE);
        tvReady.setVisibility(View.VISIBLE);

        countdown(tvReady, 4000);
    }

    // COUNTOWN FROM 4 TO 0 BY 1 SECOND
    // EVERY SECOND SHOW NEXT WORD FROM ARRAY LIST
    public void countdown(final TextView tv, int miliSeconds) {

        new CountDownTimer(miliSeconds, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                tv.setText(readySetGo[counter]);
                counter++;
            }

            @Override
            public void onFinish() {
                if (!stopped) {
                    Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    finish();
                }
            }
        }.start();
    }

    // DISABLE RETURNING ON THIS ACTIVITY
    @Override
    public void onPause() {
        super.onPause();
        stopped = true;
        finish();
    }

    // DISABLE BACK BUTTON
    @Override
    public void onBackPressed() {
    }

}
