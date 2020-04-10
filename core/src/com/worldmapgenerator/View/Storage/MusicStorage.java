package com.worldmapgenerator.View.Storage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.worldmapgenerator.GlobalValues;

public class MusicStorage {

    /**
     * Данный объект хранит ссылки на музыку по именам, чтобы другие классы имели к ей доступ.
     */
    public MusicStorage() {
        loadMusic();
    }

    private void loadMusic() {
        setVolume();
    }

    public void setVolume() {
    }
}
