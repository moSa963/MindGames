package com.example.mindgames.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mindgames.R;

public class InfoCard {
    public static View newCard(ViewGroup container, String title, String info, String max){
        return new InfoCard(container, title, info, max).card;
    }

    private final View card;

    private InfoCard(ViewGroup container, String title, String info, String max){
        card = View.inflate(container.getContext(), R.layout.info_card, null);
        setData(title, info, max);
        setAnimation();
    }

    private void setData(String title, String info, String max){
        ((TextView)card.findViewById(R.id.infoCard_title)).setText(title);
        ((TextView)card.findViewById(R.id.infoCard_info)).setText(info);
        ((TextView)card.findViewById(R.id.infoCard_max)).setText(max);
    }

    private void setAnimation(){
        ImageView ic = (ImageView)card.findViewById(R.id.infoCard_ic);
        LinearLayout header = (LinearLayout)card.findViewById(R.id.infoCard_header);
        LinearLayout body = (LinearLayout)card.findViewById(R.id.infoCard_body);
        body.setVisibility(View.GONE);

        header.setOnClickListener(v->{
            if (body.getVisibility() == View.VISIBLE){
                body.setVisibility(View.GONE);
                ic.setRotation(0);
            }else{
                body.setVisibility(View.VISIBLE);
                ic.setRotation(90);
            }
        });
    }
}
