package com.worldmapgenerator.View.RenderEngine;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;

import java.util.LinkedList;

public class Renderer {

    private LinkedList<TextureAtlas> atlases;

    private LinkedList<Texture> textures;
    private LinkedList<Sprite> sprites;
    private LinkedList<Animation<TextureRegion>> animations;

    private LinkedList<ScheduledEntity> schedule;
    private LinkedList<ScheduledEntity> polygonSchedule;

    private SpriteBatch batch;
    private PolygonSpriteBatch polygonBatch;

    /**
     * Создает объект, выполняющий отрисовку различных элементов графики.
     *
     * @param batch - объект libgdx, выполняющий отрисовку спрайтов, анимаций и текста
     * @param polygonBatch - объект libgdx, выполняющий отрисовку многоугольников с текстурами
     */
    public Renderer(SpriteBatch batch, PolygonSpriteBatch polygonBatch) {
        this.batch = batch;
        this.polygonBatch = polygonBatch;
        this.atlases = new LinkedList<>();
        this.textures = new LinkedList<>();
        this.sprites = new LinkedList<>();
        this.animations = new LinkedList<>();
        this.schedule = new LinkedList<>();
        this.polygonSchedule = new LinkedList<>();
    }

    /**
     * Поочередно отображает сначала все многоугольники в очереди, потом все спрайты/анимации в очереди
     *
     * @param elapsedTime - время в секундах с начала работы программы
     */
    public void render(float elapsedTime) {
        polygonBatch.begin();
        while(!polygonSchedule.isEmpty()) {
            polygonSchedule.remove().draw(batch, polygonBatch, elapsedTime);
        }
        polygonBatch.end();
        batch.begin();
        while(!schedule.isEmpty()) {
            schedule.remove().draw(batch, polygonBatch, elapsedTime);
        }
        batch.end();
    }

    /**
     * Добавляет атлас текстур в сответствующий список.
     *
     * @param atlas - новый атлас
     * @return ссылка на атлас
     */
    public TextureAtlas addAtlas(TextureAtlas atlas) {
        atlases.add(atlas);
        return atlases.pollLast();
    }

    /**
     * Извлекает спрайт из определенного атласа по имени и помещает его в сответствующий список.
     *
     * @param atlas - атлас, из которого извлекается спрайт
     * @param name - название спрайта
     * @return ссылка на спрайт (или null, если спрайта с таким именем в этом атласе нет)
     */
    public Sprite addSprite(TextureAtlas atlas, String name) {
        sprites.add(atlas.createSprite(name));
        if(sprites.peek() == null) {
            System.err.println("Sprite not found: " + name);
        }
        return sprites.pollLast();
    }

    /**
     * Создает текстуру и помещает ее в сответствующий список.
     *
     * @param name - название файла-источника новой текстуры
     * @return ссылка на текстуру
     */
    public Texture addTexture(String name) {
        textures.add(new Texture(name));
        return textures.pollLast();
    }

    /**
     * Создает анимацию из определенного атласа по имени и помещает ее в сответствующий список.
     *
     * @param atlas - атлас, из которого извлекается анимация
     * @param name - название анимации
     * @param frameDuration - длительность каждого кадра анимации
     * @param frameNum - количество кадров в анимации
     * @param playMode - режим проигрывания (PlayMode.NORMAL - одиночная анимация, PlayMode.LOOP - циклическая)
     * @return ссылка на анимацию
     */
    public Animation<TextureRegion> addAnimation(TextureAtlas atlas, String name, float frameDuration, int frameNum,
                                                 Animation.PlayMode playMode) {
        Array<TextureAtlas.AtlasRegion> regions = new Array<TextureAtlas.AtlasRegion>(frameNum);
        for(int i = 1; i <= frameNum; i++) {
            regions.add(atlas.findRegion(name + i));
            if(regions.peek() == null) {
                System.err.println("Animation frame not found: " + name + i);
            }
        }
        animations.add(new Animation<TextureRegion>(frameDuration, regions, playMode));
        return animations.pollLast();
    }

    /**
     * Добавляет спрайт в очередь отрисовки.
     *
     * @param sprite - спрайт, который нужно будет отобразить
     * @param x - место отрисовки спрайта по оси X
     * @param y - место отрисовки спрайта по оси Y
     * @param scale - масштабирование спрайта
     * @param rotation - поворот спрайта в градусах
     * @param flipX - при значении true спрайт отражается по горизонтали
     */
    public void addSpriteToSchedule(Sprite sprite, float x, float y, float scale, float rotation, boolean flipX) {
        schedule.add(new ScheduledSprite(sprite, x, y, scale, rotation, flipX));
    }

    /**
     * Добавляет многоугольник с текстурой в очередь отрисовки.
     *
     * @param texture - текстура, которой должен быть заполнен многоугольник
     * @param points - координаты вершин многоугольника (для n вершин в массиве 2n чисел; points[2*i] - x i-той точки,
     *               points[2*i+1] - y i-той точки
     * @param scale - масштабирование спрайта
     */
    public void addPolygonToSchedule(Texture texture, float[] points, float scale) {
        polygonSchedule.add(new ScheduledPolygon(texture, points, scale));
    }

    /**
     * Добавляет анимацию в очередь отрисовки.
     *
     * @param animation - анимация, которую нужно будет отобразить
     * @param x - место отрисовки анимации по оси X
     * @param y - место отрисовки анимации по оси Y
     * @param scale - масштабирование анимации
     * @param rotation - поворот анимации в градусах
     * @param flipX - при значении true анимация отражается по горизонтали
     * @param startTime - время с начала работы (в секундах), в которое анимация добавлена в очередь
     * @param reverse - при значении true анимация проигрывается в обратном порядке кадров
     */
    public void addAnimationToSchedule(Animation<TextureRegion> animation, float x, float y, float scale, boolean flipX, float rotation,
                                       float startTime, boolean reverse) {
        schedule.add(new ScheduledAnimation(animation, x, y, scale, flipX, rotation, startTime, reverse));
    }

    /**
     * Добавляет текст в очередь отрисовки.
     *
     * @param font - bitmap-шрифт для отрисовки текста
     * @param x - место отрисовки текста по оси X
     * @param y - место отрисовки текста по оси Y
     * @param text - содержимое текста
     * @param r - значение красного цвета от 0 до 1
     * @param g - значение зеленого цвета от 0 до 1
     * @param b - значение синего цвета от 0 до 1
     * @param a - значение зеленого цвета от 0 до 1
     */
    public void addTextToSchedule(BitmapFont font, float x, float y, String text, float r, float g, float b, float a) {
        schedule.add(new ScheduledText(font, x, y, text, r, g, b, a));
    }

    /**
     * Добавляет текст в очередь отрисовки. Заданная позиция будет центром текста, а не углом.
     *
     * @param font - bitmap-шрифт для отрисовки текста
     * @param x - место отрисовки центра текста по оси X
     * @param y - место отрисовки центра текста по оси Y
     * @param text - содержимое текста
     * @param r - значение красного цвета от 0 до 1
     * @param g - значение зеленого цвета от 0 до 1
     * @param b - значение синего цвета от 0 до 1
     * @param a - значение зеленого цвета от 0 до 1
     */
    public void addCenteredTextToSchedule(BitmapFont font, float x, float y, String text, float r, float g, float b, float a) {
        final GlyphLayout layout = new GlyphLayout(font, text);
        schedule.add(new ScheduledText(font, x - layout.width / 2, y + layout.height / 2, text, r, g, b, a));
    }

    /**
     * Удаляет ресурсы, загруженные в рендерере, и освобождает место в памяти.
     */
    public void clear() {
        schedule.clear();
        for(TextureAtlas atlas: atlases) {
            atlas.dispose();
        }
        for(Texture texture: textures) {
            texture.dispose();
        }
        textures.clear();
        sprites.clear();
        animations.clear();
        atlases.clear();
    }

    /**
     * Задает настройки камеры (отступ)
     * @param projection - матрица преобразования проекции
     */
    public void setCamera(Matrix4 projection) {
        batch.setProjectionMatrix(projection);
        polygonBatch.setProjectionMatrix(projection);
    }

}