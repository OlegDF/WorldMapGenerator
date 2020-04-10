package com.worldmapgenerator.View.Loader;

import com.badlogic.gdx.Gdx;
import com.worldmapgenerator.View.RenderEngine.Renderer;
import com.worldmapgenerator.View.Storage.MusicStorage;
import com.worldmapgenerator.View.Storage.SpriteStorage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AtlasLoader implements DataLoader {

    @Override
    public void load(Renderer renderer, SpriteStorage spriteStorage, MusicStorage musicStorage) {
        loadTerrains(renderer, spriteStorage);
    }

    private BufferedReader getBufferedReader(String file) {
        InputStream in = Gdx.files.internal(file).read();
        return new BufferedReader(new InputStreamReader(in));
    }

    private void loadTerrains(Renderer renderer, SpriteStorage spriteStorage) {
        final String terrainsFile = "sprite_descriptions/terrains.txt";
        BufferedReader reader = getBufferedReader(terrainsFile);
        String line;
        try {
            line = reader.readLine();
            int terrainTypesNum = Integer.valueOf(line);
            spriteStorage.prepareTerrainArrays(terrainTypesNum);
            for(int i = 0; i < terrainTypesNum; i++) {
                spriteStorage.loadTerrainTextureNames(terrainsFile, reader, i);
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error reading the file: " + terrainsFile);
        }
        spriteStorage.loadTerrains(renderer);
    }

}
