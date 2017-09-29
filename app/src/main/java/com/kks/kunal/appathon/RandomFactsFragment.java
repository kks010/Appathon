package com.kks.kunal.appathon;

import android.graphics.Color;
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
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

/**
 * Created by Kunal on 26-09-2017.
 */

public class RandomFactsFragment extends Fragment implements TextLoader.TextLoaderListener {

    View inflatedView;
    Spinner s;
    public String[] arraySpinner;
    TextSwitcher fetchedText;
    String url="http://numbersapi.com/random/";
    String finalUrl="";
    String result="";

    TextLoader.TextLoaderListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        this.inflatedView = inflater.inflate(R.layout.fragment_random, container, false);
        setRetainInstance(true);

        this.arraySpinner = new String[] {"<Select>","trivia","math", "date", "year"};
        s = inflatedView.findViewById(R.id.spinner_random);
        fetchedText=inflatedView.findViewById(R.id.fetched_text_random);

        fetchedText.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView () {
                TextView textView = new TextView(getContext());
                textView.setLayoutParams(new TextSwitcher.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                textView.setTextSize(20);
                textView.setTextColor(Color.WHITE);

                return textView;
            }
        });

        fetchedText.setInAnimation(getContext(), R.anim.in_animation);
        fetchedText.setOutAnimation(getContext(), R.anim.out_animation);

        if(savedInstanceState!=null){
            String fetched=savedInstanceState.getString("FetchedFactRandom");
            setTitle(fetched);
        }
        listener = this;

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, arraySpinner);
        s.setAdapter(adapter);

        s.post(new Runnable() {
            public void run() {
                s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if(i==0){
                            finalUrl="";
                        }else if(i==1){
                            finalUrl=url+adapterView.getItemAtPosition(i).toString();
                            adapterView.setSelection(0);
                        }else if(i==2){
                            finalUrl=url+adapterView.getItemAtPosition(i).toString();
                            adapterView.setSelection(0);
                        }else if(i==3){
                            finalUrl=url+adapterView.getItemAtPosition(i).toString();
                            adapterView.setSelection(0);
                        }else if(i==4){
                            finalUrl=url+adapterView.getItemAtPosition(i).toString();
                            adapterView.setSelection(0);
                        }

                        new TextLoader(finalUrl,listener).execute();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        setTitle("");
                    }
                });
            }
        });

        return inflatedView;
    }

    @Override
    public void getText(String result) {
        this.result=result;
        setTitle(result);
    }

    public void setTitle (String actionBarTitle) {

        fetchedText.setText(actionBarTitle);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("FetchedFactRandom",result);
    }
}
