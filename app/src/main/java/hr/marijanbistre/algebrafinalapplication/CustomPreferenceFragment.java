package hr.marijanbistre.algebrafinalapplication;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Marijan Bistre on 6.8.2017..
 */

public class CustomPreferenceFragment extends PreferenceFragment {

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.profile_settings);
    }

}
