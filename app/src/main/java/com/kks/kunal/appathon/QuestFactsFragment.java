package com.kks.kunal.appathon;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Kunal on 26-09-2017.
 */

public class QuestFactsFragment extends Fragment implements TextLoader.TextLoaderListener, AdapterView.OnItemSelectedListener {

    View inflatedView;
    public String[] arraySpinner;
    TextView fetchedText;
    EditText inputNumber;
    Button submitBtn;
    String url="http://numbersapi.com/";
    String finalUrl="";
    String spinnerText="";
    String input="";

    TextLoader.TextLoaderListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflatedView = inflater.inflate(R.layout.fragment_quest, container, false);

        listener=this;

        this.arraySpinner = new String[] {"<Select>","trivia","math", "date", "year"};
        Spinner s = inflatedView.findViewById(R.id.spinner_quest);
        inputNumber=inflatedView.findViewById(R.id.input_edit_text);
        fetchedText=inflatedView.findViewById(R.id.fetched_text_quest);
        submitBtn=inflatedView.findViewById(R.id.submit_btn);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item, arraySpinner);
        s.setAdapter(adapter);

        s.setOnItemSelectedListener(this);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                input=inputNumber.getText().toString();
                finalUrl=url+input+"/"+spinnerText;

                boolean ans= isNumeric(input);

                if(input.equals("")&& spinnerText.equals("")){
                    Toast.makeText(getContext(),"Enter the number and select category",Toast.LENGTH_SHORT).show();
                }else if(input.equals("")){
                    Toast.makeText(getContext(),"Enter the number",Toast.LENGTH_SHORT).show();
                }else if(spinnerText.equals("<Select>")||spinnerText.equals("")) {
                    Toast.makeText(getContext(),"Select Category",Toast.LENGTH_SHORT).show();
                }else if(ans){
                    new TextLoader(finalUrl,listener).execute();
                }else{
                    Toast.makeText(getContext(),"Input the number correctly",Toast.LENGTH_SHORT).show();
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

        fetchedText.setText(result);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if(i==1){
            spinnerText=adapterView.getItemAtPosition(i).toString();
            inputNumber.setHint("Hint: 180");
        }else if(i==2){
            spinnerText=adapterView.getItemAtPosition(i).toString();
            inputNumber.setHint("Hint: 3");
        }else if(i==3){
            spinnerText=adapterView.getItemAtPosition(i).toString();
            inputNumber.setHint("Hint: MM/DD eg\"01/09\"");
        }else if(i==4){
            spinnerText=adapterView.getItemAtPosition(i).toString();
            inputNumber.setHint("Hint: 2017");
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        fetchedText.setText("");
    }
}
