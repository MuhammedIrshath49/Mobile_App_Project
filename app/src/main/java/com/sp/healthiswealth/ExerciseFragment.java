package com.sp.healthiswealth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ExerciseFragment extends Fragment {

    ListView listView;

 public ExerciseFragment() {
     // empty constructor
 }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     View view = inflater.inflate(R.layout.fragment_exercise, container, false);

      final String[] exercises = new String[]{"Sit Ups","Push Ups","Burpees","Crunches","Lunges","Leg Raises"};

     listView = (ListView) view.findViewById(R.id.exercise_list);

     ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,exercises);

     listView.setAdapter(listViewAdapter);

     listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             if(position==0){
                 Intent intent = new Intent(view.getContext(),SitUps.class);
             startActivityForResult(intent,0);
             }
             if(position==1){
                 Intent intent = new Intent(view.getContext(),PushUps.class);
                 startActivityForResult(intent,1);
             }
             if(position==2){
                 Intent intent = new Intent(view.getContext(),Burpees.class);
                 startActivityForResult(intent,2);
             }
             if(position==3){
                 Intent intent = new Intent(view.getContext(),Crunches.class);
                 startActivityForResult(intent,3);
             }
             if(position==4){
                 Intent intent = new Intent(view.getContext(),Lunges.class);
                 startActivityForResult(intent,4);
             }
             if(position==5){
                 Intent intent = new Intent(view.getContext(),LegRaises.class);
                 startActivityForResult(intent,5);
             }

         }
     });

     return view;


    }


}
