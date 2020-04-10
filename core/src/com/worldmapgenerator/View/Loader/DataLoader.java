package com.worldmapgenerator.View.Loader;

import com.worldmapgenerator.View.RenderEngine.Renderer;
import com.worldmapgenerator.View.Storage.MusicStorage;
import com.worldmapgenerator.View.Storage.SpriteStorage;

public interface DataLoader {

    /**
     * Загружает набор ресурсов и помещает их в два хранилища: графики и звука (последнее пока не используется)
     *
     * @param renderer - объект-рендерер, который получит ссылки на загруженную графику
     * @param spriteStorage - объект-хранилище, который будет выполнять загрузку элементов графики
     * @param musicStorage - объект-хранилище, который будет выполнять загрузку элементов звука
     */
    public void load(Renderer renderer, SpriteStorage spriteStorage, MusicStorage musicStorage);

}
