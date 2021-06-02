package com.example.seiaudioplayer.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seiaudioplayer.R;
import com.example.seiaudioplayer.data.model.Music;
import com.example.seiaudioplayer.presenter.songPresenter.MusicAdapter;
import com.example.seiaudioplayer.service.MusicService;

import java.util.ArrayList;


public class SongFragment extends Fragment {

    RecyclerView recyclerView;
    MusicAdapter musicAdapter;
    MusicService musicService = new MusicService();

    public SongFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_song, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        ArrayList<Music> musicFiles = musicService.getAllMusic(getContext());

        if (musicFiles.size() > 0) {

            musicAdapter = new MusicAdapter(getContext(), musicFiles);
            recyclerView.setAdapter(musicAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,
                    false));

        }

        return view;

    }


}