package com.vango.testownik.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.vango.testownik.MainActivity;
import com.vango.testownik.R;
import com.vango.testownik.ui.main.MainFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.preference.PreferenceFragmentCompat;
import androidx.recyclerview.widget.RecyclerView;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preference);
        ActionBar actionBar =((MainActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            ((MainActivity)getActivity()).replaceFragment(MainFragment.class,"");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
