package com.nicolascarrasco.www.jokeactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 *
 */
public class JokeFragment extends Fragment {

    public JokeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_joke, container, false);
        Intent intent = getActivity().getIntent();
        String joke = intent.getStringExtra(Intent.EXTRA_TEXT);
        TextView jokeTextView = (TextView) root.findViewById(R.id.joke_text_view);
        jokeTextView.setText(joke);
        return root;
    }
}
