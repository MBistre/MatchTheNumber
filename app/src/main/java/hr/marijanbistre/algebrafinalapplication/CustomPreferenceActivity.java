package hr.marijanbistre.algebrafinalapplication;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by Marijan Bistre on 6.8.2017..
 */

public class CustomPreferenceActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new CustomPreferenceFragment())
                .commit();
    }

}
