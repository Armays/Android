package com.example.parents.alerteseisme;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class PreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
    }

    public void onBackPressed(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.boutonMagnitude:
                if (checked) {
                    editor.putBoolean("magnitude", true);

                    // Commit the edits!
                    editor.commit();
                    break;
                }
            case R.id.boutonLocalisation:
                if (checked) {
                    editor.putBoolean("magnitude", false);

                    // Commit the edits!
                    editor.commit();
                    break;
                }
        }
    }
}
