package mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class MyCmtGame extends Game {


    private static int record;
    private BitmapFont font;
    private OrthographicCamera cam;
    private SpriteBatch batch;

    //background
    static private Texture tex;
    static private Sprite sprite;

    //apple
    private Texture texfruit;
    private Array<Rectangle> fruit;
    private long appleTime;

    //basket
    private Texture basketimg;
    private Rectangle basket;

    //muha
    private Texture texMuha;
    private Array<Rectangle> muha;
    private long muhatime;

    //exit
    private Texture exitTexture;
    private Sprite exitSprite;

    private int count;
    private int limit;

    private long time;

    private boolean k;

    private static float BUTTON_RESIZE_FACTOR = 800f;
    private static float EXIT_VERT_POSITION_FACTOR = 4.2f;


    @Override
    public void create() {

        time=490000000;

        if (!Gdx.files.local("rec.txt").exists())write("0");

        record=read();

        cam = new OrthographicCamera();
        cam.setToOrtho(false, 800, 480);

        batch = new SpriteBatch();

        //background
        tex = new Texture(Gdx.files.internal("background.png"));
        sprite = new Sprite(tex);

        sprite.setPosition(0, 0);
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        count = 0;
        limit = 1;

        //fruit
        texfruit = new Texture(Gdx.files.internal("apple.png"));

        fruit = new Array<>();
        apple();

        //muha
        texMuha = new Texture(Gdx.files.internal("muha.png"));

        muha = new Array<>();
        muh();

        //basket
        basketimg = new Texture(Gdx.files.internal("basket.png"));

        basket = new Rectangle();

        basket.setSize((float) (Gdx.graphics.getWidth() / 4.5), Gdx.graphics.getWidth() / 6);
        basket.x = Gdx.graphics.getWidth() / 2f - basket.width / 2f;
        basket.y = basket.getHeight() / 2;

        //exitBtn
        exitTexture = new Texture(Gdx.files.internal("gameisover.png"));
        exitSprite = new Sprite(exitTexture);
        exitSprite.setSize(exitSprite.getWidth() * (Gdx.graphics.getWidth() / BUTTON_RESIZE_FACTOR), exitSprite.getHeight() * (Gdx.graphics.getWidth() / BUTTON_RESIZE_FACTOR));
        exitSprite.setPosition((Gdx.graphics.getWidth() / 2f - exitSprite.getWidth() / 2), Gdx.graphics.getHeight() / EXIT_VERT_POSITION_FACTOR);

        //score
        font = new BitmapFont();
        font.setColor(Color.GRAY);
        font.getData().setScale(3, 3);

    }


    private void apple() {
        Rectangle fruitt = new Rectangle();
        fruitt.setSize(texfruit.getWidth(), texfruit.getHeight());
        fruitt.x = MathUtils.random(100, Gdx.graphics.getWidth() - texfruit.getWidth() - 100);
        fruitt.y = Gdx.graphics.getHeight();
        fruit.add(fruitt);
        appleTime = TimeUtils.nanoTime();
    }


    private Rectangle muhh;
    private void muh() {
        muhh = new Rectangle();
        muhh.setSize(texMuha.getWidth(), texMuha.getHeight());
        muhh.x = MathUtils.random(muhh.getWidth(), Gdx.graphics.getWidth() - texfruit.getWidth() - muhh.getWidth());
        muhh.y = Gdx.graphics.getHeight();
        if (count == limit) {
            muha.add(muhh);
        }
        muhatime = TimeUtils.nanoTime();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {

        cam.update();

        if (Gdx.input.isTouched() && !k) {
            basket.x = (int) (Gdx.input.getX() - basket.getWidth() / 2);
            if (basket.x < 0) {
                basket.x = 0;
            } else if (basket.x > Gdx.graphics.getWidth() - basket.getWidth()) {
                basket.x = Gdx.graphics.getWidth() - basket.getWidth();
            }
        }


        if (count>record){
            write(Integer.toString(count));
            record=count;
        }


        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        sprite.draw(batch);

        for (Rectangle apple : fruit) {
            batch.draw(texfruit, apple.x, apple.y, (float) (basket.width / 1.5), (float) (basket.width / 1.2));
            if (apple.y < 0) {
                k = true;
            }
        }

        for (Rectangle m : muha) {
            batch.draw(texMuha, m.x, m.y, texMuha.getWidth(), texMuha.getHeight());
        }


        batch.draw(basketimg, basket.x, basket.y, basket.width, basket.height);

        font.draw(batch, "APPLES: " + count+"        RECORD: "+ record, 2, Gdx.graphics.getHeight() - 10);


        if (TimeUtils.nanoTime() - appleTime > time) apple();
        Iterator<Rectangle> iterapple = fruit.iterator();

        if (count == limit) {
            muh();
            limit += 10;
            time-=3000000;

        }
        Iterator<Rectangle> iterMuha = muha.iterator();

        while (iterMuha.hasNext()) {
            Rectangle muha = iterMuha.next();
            muha.y -= 900 * Gdx.graphics.getDeltaTime();
            if (muha.overlaps(basket)) {
                iterMuha.remove();
                k = true;
            }


        }



        while (iterapple.hasNext() && !k) {

            Rectangle apple = iterapple.next();
            apple.y -= 900 * Gdx.graphics.getDeltaTime();
            if (apple.overlaps(muhh)) {
                iterapple.remove();
            }
            if (apple.overlaps(basket)){
                iterapple.remove();
                if (!k){
                    count++;
                }
            }


        }


        if (k) {
            exitSprite.draw(batch);
            basket.x = -Gdx.graphics.getWidth();
            fruit.clear();
            muha.clear();
        }
        batch.end();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        tex.dispose();
        texMuha.dispose();
        texfruit.dispose();
        basketimg.dispose();
        exitTexture.dispose();
        font.dispose();
    }
    private static void write(String str){
        FileHandle file=Gdx.files.local("rec.txt");
        file.writeString(str,false);
    }
    private static int read(){
        FileHandle file=Gdx.files.local("rec.txt");
        return Integer.parseInt(file.readString());
    }


}
