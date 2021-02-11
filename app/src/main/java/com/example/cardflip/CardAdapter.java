package com.example.cardflip;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CardAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] Value;

    public CardAdapter(Activity context, String[] value) {
        super(context, R.layout.card_row, value);

        this.context = context;
        this.Value = value;
    }

    @SuppressLint("SetTextI18n")
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.card_row, null, true);

        TextView status = rowView.findViewById(R.id.status);
        TextView icon = rowView.findViewById(R.id.valueIcon);
        TextView valueText = rowView.findViewById(R.id.valueText);

        status.setText(Value[position]);

        if(Value[position].equals("0")){
            icon.setBackgroundResource(R.drawable.baseline_arrow_upward_24);
            valueText.setText("Up");
        }else if (Value[position].equals("1")){
            icon.setBackgroundResource(R.drawable.baseline_arrow_downward_24);
            valueText.setText("Down");
        }

        return rowView;
    }
}