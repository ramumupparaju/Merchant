package com.incon.connect.ui.addoffer;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.incon.connect.R;
import com.incon.connect.databinding.ActivityAddofferBinding;
import com.incon.connect.ui.BaseActivity;

import java.util.ArrayList;

/**
 * Created by PC on 9/27/2017.
 */

public class AddOffer  extends BaseActivity {
    ActivityAddofferBinding addofferBinding;
    ListView listaddoffer;
    ArrayList<String> addanoffermodel = new ArrayList<>();
    String[] selection = {"brand" , "division" , "model" , "date" , "Add an Offer on MRP" ,
            "Offer ecpires on"};


    @Override
    protected int getLayoutId() {
        return R.layout.activity_addoffer;
    }

    @Override
    protected void initializePresenter() {

    }

    public  void onSelectedClick() {
        String selection = "";
        for (String item : addanoffermodel) {
            selection += " " + item + "\n";
        }
        /*Toast.makeText(getApplicationContext(),"tou selected\n" +selection, Toast.LENGTH_LONG)
                .show();*/


    }

    @Override
    protected void onCreateView(Bundle saveInstanceState) {

        addofferBinding = DataBindingUtil.setContentView(this, getLayoutId());
        addofferBinding.setAddoffer(this);
        listaddoffer = (ListView) findViewById(R.id.list_addoffer);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.custom_checkbox ,
                R.id.checkbox_click);
        listaddoffer.setAdapter(adapter);
        listaddoffer.setItemsCanFocus(false);
        // we want multiple clicks
        listaddoffer.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        listaddoffer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectItem = ((TextView) view).getText().toString();
                if (addanoffermodel.contains(selectItem)) {

                    addanoffermodel.remove(selectItem);
                }
                else
                    addanoffermodel.add(selectItem);

            }
        });

    }
}
