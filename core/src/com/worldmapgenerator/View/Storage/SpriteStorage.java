package com.worldmapgenerator.View.Storage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.worldmapgenerator.View.RenderEngine.Renderer;

import java.io.BufferedReader;
import java.io.IOException;

import static com.badlogic.gdx.graphics.Texture.TextureWrap.Repeat;

public class SpriteStorage {

    private int terrainTypesNum;
    private String[] terrainNames;
    public Texture[] terrainTextures;

    /**
     * Данный объект хранит ссылки на текстуры, спрайты и анимации по именам, чтобы другие классы имели к ним доступ.
     */
    public SpriteStorage() {}

    /**
     * Создает массивы, хранящие данные о текстурах местности
     *
     * @param terrainTypesNum - количество типов местности
     */
    public void prepareTerrainArrays(int terrainTypesNum) {
        this.terrainTypesNum = terrainTypesNum;
        terrainNames = new String[terrainTypesNum];
        terrainTextures = new Texture[terrainTypesNum];
    }

    /**
     * Загружает названия текстур местностей.
     *
     * @param charStatFile - название файла, из которого считываются названия
     * @param reader - объект, считывающий названия
     * @param i - номер типа текстур
     */
    public void loadTerrainTextureNames(final String charStatFile, BufferedReader reader, int i) {
        try {
            String line;
            line = reader.readLine();
            terrainNames[i] = line;
        } catch (IOException e) {
            System.err.println("Error reading the file: " + charStatFile);
        }
    }

    /**
     * Загружает текстуры местностей.и отправляет ссылки на них отрисовщику
     *
     * @param renderer - объект, который получит ссылки на текстуры
     */
    public void loadTerrains(Renderer renderer) {
        for(int k = 0; k < terrainTypesNum; k++) {
            terrainTextures[k] = renderer.addTexture(terrainNames[k]);
            terrainTextures[k].setWrap(Repeat, Repeat);
        }
    }

}
