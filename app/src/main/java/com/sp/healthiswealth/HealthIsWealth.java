package com.sp.healthiswealth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sp.healthiswealth.multiplealarms.activities.AlarmActivity;

public class HealthIsWealth extends AppCompatActivity {

    private Cursor model = null;
    private ExerciseAdapter adapter = null;
    private ListView list;
    private ExerciseHelper helper = null;
    private TextView empty = null;
    private SharedPreferences prefs = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        empty = (TextView) findViewById(R.id.empty);
        helper = new ExerciseHelper(this);
        list = (ListView) findViewById(R.id.list);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();

        adapter = new ExerciseAdapter(this,model,0);
        list.setOnItemClickListener(onListClick);
        list.setAdapter(adapter);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(prefListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.nav_exercise:
                    selectedFragment = new ExerciseFragment();
                    break;
                case R.id.nav_search:
                    selectedFragment = new SearchFragment();
                    break;
                case R.id.nav_music:
                    selectedFragment = new MusicFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    selectedFragment).commit();

            return true;
        }
    };



    private void initList() {
        if (model != null) {

            model.close();
        }
        model = helper.getAll(prefs.getString("sort_order", "healthiswealthName"));
        adapter.swapCursor(model);
    }

    private SharedPreferences.OnSharedPreferenceChangeListener prefListener = new
            SharedPreferences.OnSharedPreferenceChangeListener() {
                public void onSharedPreferenceChanged(SharedPreferences sharedPrefs,String
                        key) {
                    if (key.equals("sort_order")) {
                        initList();
                    }
                }
            };

    @Override
    protected void onResume() {
        super.onResume();
        initList();
        if (model.getCount() > 0) {
            empty.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.add):
                Intent intent;
                intent = new Intent(HealthIsWealth.this, DetailForm.class);
                startActivity(intent);
                break;

            case (R.id.prefs):
                startActivity(new Intent(this, EditPreferences.class));
                break;

            case (R.id.enable_bluetooth):
                startActivity(new Intent(HealthIsWealth.this, BluetoothActivity.class));
                break;

            case (R.id.add_alarm):
                startActivity(new Intent(HealthIsWealth.this, AlarmActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    class ExerciseAdapter extends CursorAdapter {

        ExerciseAdapter(Context context, Cursor cursor, int flags) {
            super(context, cursor, flags);
        }

        @Override
        public void bindView(View row, Context ctxt, Cursor c) {
            ExerciseHolder holder = (ExerciseHolder) row.getTag();

            holder.populateFrom(c, helper);
        }

        @Override
        public View newView(Context ctxt, Cursor c, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.row, parent, false);
            ExerciseHolder holder = new ExerciseHolder(row);

            row.setTag(holder);
            return (row);
        }
    }

    private AdapterView.OnItemClickListener onListClick = new
            AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    model.moveToPosition(position);
                    String recordID = helper.getID(model);
                    Intent intent;
                    intent = new Intent(HealthIsWealth.this, DetailForm.class);
                    intent.putExtra("ID", recordID);
                    startActivity(intent);
                }
            };


    static class ExerciseHolder {
        private TextView healName = null;
        private TextView exer = null;
        private ImageView icon = null;

        ExerciseHolder(View row) {
            healName = (TextView) row.findViewById(R.id.healName);
            exer = (TextView) row.findViewById(R.id.healExercise);
            icon = (ImageView) row.findViewById(R.id.icon);
        }

        void populateFrom(Cursor c, ExerciseHelper helper) {
            healName.setText(helper.getHealthIsWealthName(c));
            String temp = helper.getHealthIsWealthExercise(c) + ", " +
                    helper.getHealthIsWealthReps(c);
            exer.setText(temp);

            if (helper.getHealthIsWealthReps(c).equals("60")) {
                icon.setImageResource(R.drawable.eagle_red);
            } else if (helper.getHealthIsWealthReps(c).equals("50")) {
                icon.setImageResource(R.drawable.eagle_red);
            }  else if (helper.getHealthIsWealthReps(c).equals("40")) {
                icon.setImageResource(R.drawable.flex);
            } else if (helper.getHealthIsWealthReps(c).equals("30")) {
                icon.setImageResource(R.drawable.flex);
            } else if (helper.getHealthIsWealthReps(c).equals("20")) {
                icon.setImageResource(R.drawable.greenlantern);
            } else {
                icon.setImageResource(R.drawable.greenlantern);
            }
        }
    }
}