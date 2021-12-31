package com.example.mindgames;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.mindgames.utils.InfoCard;
import com.example.mindgames.utils.Storage;


public class InfoFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createCards();
    }

    private void createCards(){
        LinearLayout container = getView().findViewById(R.id.main_frame);

        Storage storage = Storage.getStorage();

        container.addView(InfoCard.newCard(container, "Reaction",
                getString(R.string.reaction_info),
                "max score: " + storage.getData(getString(R.string.reaction)) + " ms"));

        container.addView(InfoCard.newCard(container, "Cards",
                getString(R.string.cards_info),
                "max score: " + storage.getData(getString(R.string.cards))));

        container.addView(InfoCard.newCard(container, "Numbers",
                getString(R.string.numbers_info),
                "max score: " + storage.getData(getString(R.string.numbers))));

        container.addView(InfoCard.newCard(container, "Match",
                getString(R.string.match_info),
                "max score: " + storage.getData(getString(R.string.match))));
    }
}