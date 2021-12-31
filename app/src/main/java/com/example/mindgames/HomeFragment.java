package com.example.mindgames;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mindgames.matching.MatchingActivity;
import com.example.mindgames.numbers.NumbersActivity;
import com.example.mindgames.reaction.ReactionActivity;
import com.example.mindgames.visualmemory.VisualMemoryActivity;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //set a click listener on each card to start a new game

        getView().findViewById(R.id.ReactionCard)
                .setOnClickListener((v) -> startActivity(ReactionActivity.class));

        getView().findViewById(R.id.VisualMemoryCard)
                .setOnClickListener((v) -> startActivity(VisualMemoryActivity.class));

        getView().findViewById(R.id.NumbersCard)
                .setOnClickListener((v) -> startActivity(NumbersActivity.class));

        getView().findViewById(R.id.MatchingCard)
                .setOnClickListener((v) -> startActivity(MatchingActivity.class));
    }

    private void startActivity(Class<?> object){
        Intent i = new Intent(getContext(), object);
        startActivity(i);
    }
}