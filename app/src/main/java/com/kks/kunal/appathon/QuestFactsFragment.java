package com.kks.kunal.appathon;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

/**
 * Created by Kunal on 26-09-2017.
 */

public class QuestFactsFragment extends Fragment implements TextLoader.TextLoaderListener{

    View inflatedView;
    Spinner s;
    public String[] arraySpinner;
    TextSwitcher fetchedText;
    EditText inputNumber;
    EditText mm;
    EditText dd;
    TextView slash;
    Button submitBtn;
    String url="http://numbersapi.com/";
    String finalUrl="";
    String spinnerText="";
    String input="";
    String result="";

    TextLoader.TextLoaderListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflatedView = inflater.inflate(R.layout.fragment_quest, container, false);
        setRetainInstance(true);

        this.arraySpinner = new String[] {"<Select>","trivia","math", "date", "year"};
        s = inflatedView.findViewById(R.id.spinner_quest);
        inputNumber=inflatedView.findViewById(R.id.input_edit_text);
        mm=inflatedView.findViewById(R.id.mm_edit_text);
        dd=inflatedView.findViewById(R.id.dd_edit_text);
        slash=inflatedView.findViewById(R.id.slash_text_view);
        fetchedText=inflatedView.findViewById(R.id.fetched_text_quest);
        submitBtn=inflatedView.findViewById(R.id.submit_btn);


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
            String fetched=savedInstanceState.getString("FetchedFactQuest");
            setTitle(fetched);
        }
        listener=this;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item, arraySpinner);
        s.setAdapter(adapter);

        s.post(new Runnable() {
                   public void run() {
                       s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                           @Override
                           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                               if(i==0){

                                   spinnerText="";

                               }else if(i==1){
                                   spinnerText=adapterView.getItemAtPosition(i).toString();
                                   inputNumber.setHint("Hint: 180");

                                   dd.setVisibility(View.INVISIBLE);
                                   mm.setVisibility(View.INVISIBLE);
                                   slash.setVisibility(View.INVISIBLE);
                                   inputNumber.setVisibility(View.VISIBLE);
                               }else if(i==2){
                                   spinnerText=adapterView.getItemAtPosition(i).toString();
                                   inputNumber.setHint("Hint: 3");

                                   dd.setVisibility(View.INVISIBLE);
                                   mm.setVisibility(View.INVISIBLE);
                                   slash.setVisibility(View.INVISIBLE);
                                   inputNumber.setVisibility(View.VISIBLE);
                               }else if(i==3){
                                   spinnerText=adapterView.getItemAtPosition(i).toString();
                                   dd.setHint("DD");
                                   mm.setHint("MM");

                                   dd.setVisibility(View.VISIBLE);
                                   mm.setVisibility(View.VISIBLE);
                                   slash.setVisibility(View.VISIBLE);
                                   inputNumber.setVisibility(View.INVISIBLE);
                               }else if(i==4){
                                   spinnerText=adapterView.getItemAtPosition(i).toString();
                                   inputNumber.setHint("Hint: 2017");

                                   dd.setVisibility(View.INVISIBLE);
                                   mm.setVisibility(View.INVISIBLE);
                                   slash.setVisibility(View.INVISIBLE);
                                   inputNumber.setVisibility(View.VISIBLE);
                               }
                           }

                           @Override
                           public void onNothingSelected(AdapterView<?> adapterView) {
                               setTitle("");
                           }
                       });
                   }
               });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                if(spinnerText.equals("date")){
                    Integer date=Integer.parseInt(dd.getText().toString());
                    Integer month=Integer.parseInt(mm.getText().toString());
                    finalUrl = url + month+slash.getText()+date + "/" + spinnerText;

                    new TextLoader(finalUrl,listener).execute();

                    mm.setText("");
                    dd.setText("");
                }else {
                    input = inputNumber.getText().toString();
                    finalUrl = url + input + "/" + spinnerText;
                    Boolean ans = isNumeric(input);

                    if (input.equals("") && spinnerText.equals("")) {
                        Toast.makeText(getContext(), "Enter the number and select category", Toast.LENGTH_SHORT).show();
                    } else if (input.equals("")) {
                        Toast.makeText(getContext(), "Enter the number", Toast.LENGTH_SHORT).show();
                    } else if (spinnerText.equals("<Select>") || spinnerText.equals("")) {
                        Toast.makeText(getContext(), "Select Category", Toast.LENGTH_SHORT).show();
                    } else if (ans) {
                        new TextLoader(finalUrl, listener).execute();
                    } else {
                        Toast.makeText(getContext(), "Enter the number correctly", Toast.LENGTH_SHORT).show();
                    }

                    inputNumber.setText("");
                }

            }
        });
        return inflatedView;
    }

    public static boolean isNumeric(String str)
    {
        for (char c : str.toCharArray())
        {
            if (!Character.isDigit(c)) return false;
        }
        return true;
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
        outState.putString("FetchedFactQuest",result);
    }
}
