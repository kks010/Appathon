package com.kks.kunal.appathon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Kunal on 26-09-2017.
 */

public class RandomFactsFragment extends Fragment implements AdapterView.OnItemSelectedListener,TextLoader.TextLoaderListener {

    View inflatedView;
    public String[] arraySpinner;
    TextView fetchedText;
    String url="http://numbersapi.com/random/";
    String finalUrl="";

    TextLoader.TextLoaderListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        this.inflatedView = inflater.inflate(R.layout.fragment_random, container, false);

        listener = this;

        this.arraySpinner = new String[] {"<Select>","trivia","math", "date", "year"};
        Spinner s = inflatedView.findViewById(R.id.spinner_random);
        fetchedText=inflatedView.findViewById(R.id.fetched_text_random);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, arraySpinner);
        s.setAdapter(adapter);

        s.setOnItemSelectedListener(this);

        return inflatedView;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if(i==1){
            finalUrl=url+adapterView.getItemAtPosition(i).toString();
        }else if(i==2){
            finalUrl=url+adapterView.getItemAtPosition(i).toString();
        }else if(i==3){
            finalUrl=url+adapterView.getItemAtPosition(i).toString();
        }else if(i==4){
            finalUrl=url+adapterView.getItemAtPosition(i).toString();
        }

        new TextLoader(finalUrl,listener).execute();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

        fetchedText.setText("");
    }

    @Override
    public void getText(String result) {
        fetchedText.setText(result);
    }
}
