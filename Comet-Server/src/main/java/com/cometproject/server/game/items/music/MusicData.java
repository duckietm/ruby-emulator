package com.cometproject.server.game.items.music;

import com.cometproject.api.game.furniture.types.IMusicData;

public class MusicData implements IMusicData {
    private final int songId;
    private final String name;

    private final String title;
    private final String artist;
    private final String data;

    private final int lengthSeconds;

    public MusicData(int songId, String name, String title, String artist, String data, int lengthSeconds) {
        this.songId = songId;
        this.name = name;
        this.title = title;
        this.artist = artist;
        this.data = data;
        this.lengthSeconds = lengthSeconds;
    }

    @Override
    public int getSongId() {
        return songId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getArtist() {
        return artist;
    }

    @Override
    public String getData() {
        return data;
    }

    @Override
    public int getLengthSeconds() {
        return this.lengthSeconds;
    }

    @Override
    public int getLengthMilliseconds() {
        return lengthSeconds * 1000;
    }
}
