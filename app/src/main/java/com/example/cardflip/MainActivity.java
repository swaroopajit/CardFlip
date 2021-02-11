package com.example.cardflip;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText value = findViewById(R.id.Ninput);
        Button loadButton = findViewById(R.id.load);
        ListView cardView = findViewById(R.id.cards);

        final String[] status = {""};

        value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(value.getText().toString().equals("")||value.getText().toString().equals("0")){
                    loadButton.setEnabled(false);
                }else{
                    loadButton.setEnabled(true);
                }
            }
        });

        cardView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint({"SetTextI18n", "ShowToast"})
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                status[0] = ((TextView)view.findViewById(R.id.status)).getText().toString();
                if(status[0].equals("0")) {
                    ((TextView) view.findViewById(R.id.status)).setText("1");
                    ((TextView) view.findViewById(R.id.valueText)).setText("Down");
                    ((TextView) view.findViewById(R.id.valueIcon)).setBackgroundResource(R.drawable.baseline_arrow_downward_24);
                    if (position != (Integer.parseInt(value.getText().toString()) - 1)) {
                        View secondCard = parent.getChildAt(position + 1);
                        status[0] = ((TextView) secondCard.findViewById(R.id.status)).getText().toString();
                        if (status[0].equals("0")) {
                            ((TextView) secondCard.findViewById(R.id.status)).setText("1");
                            ((TextView) secondCard.findViewById(R.id.valueText)).setText("Down");
                            ((TextView) secondCard.findViewById(R.id.valueIcon)).setBackgroundResource(R.drawable.baseline_arrow_downward_24);
                        } else {
                            ((TextView) secondCard.findViewById(R.id.status)).setText("0");
                            ((TextView) secondCard.findViewById(R.id.valueText)).setText("Up");
                            ((TextView) secondCard.findViewById(R.id.valueIcon)).setBackgroundResource(R.drawable.baseline_arrow_upward_24);
                        }
                    }
                    String[] cardStatus = new String[Integer.parseInt(value.getText().toString())];
                    for(int i=0;i<Integer.parseInt(value.getText().toString());i++){
                        cardStatus[i] =  ((TextView)parent.getChildAt(i).findViewById(R.id.status)).getText().toString();
                    }
                    checkSuccess(cardStatus,Integer.parseInt(value.getText().toString()));
                }
                else{
                    Toast.makeText(getApplicationContext(),"You can only flip cards that are facing up!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadCards();
            }
        });
    }

    private void checkSuccess(String[] cardStatus,int n) {
        //Todo:check for success
        boolean success = false;
        for (int i = 0; i < n; i++) {
            if(i>0){
                if(cardStatus[i].equals(cardStatus[i - 1])&&cardStatus[i].equals("1")){
                    success = true;
                }else{
                    success = false;
                    break;
                }
            }
        }
        if(success){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Congratulations! You won!")
                    .setPositiveButton("Play a new set", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            LoadCards();
                        }
                    })
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    public void LoadCards() {
        EditText value = findViewById(R.id.Ninput);
        int n = Integer.parseInt(value.getText().toString());
        String[] cardSet = new String[n];
        boolean cardSetWorks = false;
        while(!cardSetWorks) {
            for (int i = 0; i < n; i++) {
                cardSet[i] = String.valueOf(Math.round(Math.random()));
                if(i>0&& !cardSetWorks){
                    if(!cardSet[i].equals(cardSet[i - 1])){
                        cardSetWorks = true;
                    }
                }
            }
        }
        ListView cardView = findViewById(R.id.cards);
        CardAdapter cardAdapter = new CardAdapter(this,cardSet);
        cardView.setAdapter(cardAdapter);
    }
}