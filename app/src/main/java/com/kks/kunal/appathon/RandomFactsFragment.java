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

    TextLoader.TextLoaderListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        this.inflatedView = inflater.inflate(R.layout.fragment_random, container, false);

        listener = this;

        this.arraySpinner = new String[] {"<Select>","trivia","math", "Date", "Year"};
        Spinner s = inflatedView.findViewById(R.id.spinner);
        fetchedText=inflatedView.findViewById(R.id.fetched_text);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, arraySpinner);
        s.setAdapter(adapter);

        s.setOnItemSelectedListener(this);

        return inflatedView;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if(i==1){
            url=url+adapterView.getItemAtPosition(i).toString();
        }else if(i==2){
            url=url+adapterView.getItemAtPosition(i).toString();
        }else if(i==3){
            url=url+adapterView.getItemAtPosition(i).toString();
        }else if(i==4){
            url=url+adapterView.getItemAtPosition(i).toString();
        }

        new TextLoader(url,listener).execute();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void getText(String result) {
        fetchedText.setText(result);
    }
}
