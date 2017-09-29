package com.incon.connect.ui.addoffer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.incon.connect.R;
import com.incon.connect.custom.view.AddOfferViewHolder;
import com.incon.connect.dto.addoffer.AddOffer;

import java.util.List;

/**
 * Created by PC on 9/28/2017.
 */

public class AddOfferAdapter  extends ArrayAdapter<AddOffer> {
    private LayoutInflater inflater;



    public AddOfferAdapter(Context context, List<AddOffer> addOfferList) {
        super(context, R.layout.custom_checkbox, R.id.text_offers, addOfferList);
        inflater = LayoutInflater.from(context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AddOffer addOffer = (AddOffer) this.getItem(position);

        CheckBox checkBox;
        TextView textView;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_checkbox, null);

            textView = (TextView) convertView
                    .findViewById(R.id.text_offers);
            checkBox = (CheckBox) convertView.findViewById(R.id.checkbox_offers);

            convertView.setTag(new AddOfferViewHolder(textView, checkBox));
            checkBox.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    AddOffer planet = (AddOffer) cb.getTag();
                    planet.setChecked(cb.isChecked());
                }
            });
        }
        else {
            AddOfferViewHolder viewHolder = (AddOfferViewHolder) convertView
                    .getTag();
            checkBox = viewHolder.getCheckBox();
            textView = viewHolder.getTextView();
        }

        checkBox.setTag(addOffer);

        checkBox.setChecked(addOffer.isChecked());
        textView.setText(addOffer.getName());

        return convertView;
    }

}
