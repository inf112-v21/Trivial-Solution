package GUIMain;

import Player.Robot;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


import java.util.ArrayList;
import java.util.Scanner;

import static com.badlogic.gdx.graphics.Color.*;

public class MenuScreen extends InputAdapter implements Screen {

    private Stage stage;
    private Skin skin;
    private Table table;
    private SpriteBatch batch;
    private BitmapFont font;
    private Label title;
    private Label undertitle;
    protected TextButton singleplayer;
    protected TextButton multiplayer;
    protected TextButton options;
    protected TextButton quit;
    private ArrayList<Robot> robots = new ArrayList<>();
    private GUI gui;
    private int numberOfRobots = 4; // TODO: 04.03.2021 La brukeren velge antall spillere
    private TextField textField;
    private static String defaultMapName = "TestMap.tmx";

    public MenuScreen(GUI gui){
        super();
        this.gui = gui;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(RED);

        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("assets/default/skin/uiskin.json"));
        table = new Table();
        table.setFillParent(true);
        title = new Label("Robo-Rally", skin);
        title.setAlignment(Align.top);
        title.setFontScale(3);
        table.add(title);
        table.row();
        undertitle = new Label("A Trivial Solution", skin);
        undertitle.setFontScale(2);
        table.add(undertitle);
        table.row();

        singleplayer = new TextButton("Singleplayer", skin);
        multiplayer = new TextButton("Multiplayer", skin);
        options = new TextButton("Options", skin);
        quit = new TextButton("Quit", skin);

        singleplayer.setLabel(getButtonLabel("Singleplayer"));
        multiplayer.setLabel(getButtonLabel("Multiplayer"));
        options.setLabel(getButtonLabel("Options"));
        quit.setLabel(getButtonLabel("Quit"));

        textField = new TextField("", skin);

        singleplayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                robots.clear();
                createRobot3();
                robots.add(new Robot("Gilgamesh", RED, true));
                robots.add(new Robot("Andromeda", GREEN, true));
                robots.add(new Robot("Ashurbarnipal", YELLOW, true));
            }
        });
        //multiplayer.addListener(new MenuListener());
        //options.addListener(new ChangeListener());
        quit.addListener(new ChangeListener() {
             @Override
             public void changed(ChangeEvent changeEvent, Actor actor) {
                 System.exit(0);
             }
         });

        table.add(singleplayer).prefWidth(200.0f).prefHeight(100.0f);
        table.row();
        table.add(multiplayer).prefWidth(200.0f).prefHeight(100.0f);
        table.row();
        table.add(options).prefWidth(200.0f).prefHeight(100.0f);
        table.row();
        table.add(quit).prefWidth(200.0f).prefHeight(100.0f);

        stage.addActor(table);



    }

    public void createRobot2() {
        Dialog dia = new Dialog("Create your robot:", skin) {
            @Override
            protected void result(Object object) {
                robots.add(new Robot(textField.getText(), BLUE, false));
            }
        };
        dia.setScale(2.5f);

        Table diatable = new Table();

        //diatable.addActor(new Label("Name of robot: ", skin));
        //diatable.row();

        TextField textfield = new TextField("", skin);
        diatable.add(textfield);
        diatable.row();

        //CheckBox AI = new CheckBox("AI", skin);
        //diatable.add(AI);
        //diatable.row();

        //dia.setPosition(500, 500);

        dia.add(diatable).align(Align.top);
        dia.row();
        dia.button("Confirm").align(Align.bottom);


        stage.addActor(dia);
    }

    public void createRobot(){
        Gdx.input.getTextInput(new Input.TextInputListener() {
            @Override
            public void input(String s) {
                robots.add(new Robot(s, BLACK, false));
            }
            @Override
            public void canceled() { }
        }, "Create your robot", "", "name");
    }

    // TODO: 04.03.2021 Temp, vennligst slett når vi får til popupvinduer
    public void createRobot3(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Name of your robot: ");
        String name = scan.nextLine();
        robots.add(new Robot(name,BLUE, false));
    }


    @Override
    public void render(float v) {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.getBatch().begin();
        stage.getBatch().end();
        stage.draw();
        if (robots.size() == numberOfRobots){
            gui.startGame(robots, defaultMapName);
        }

    }

    public Label getButtonLabel(String text){
        Label l = new Label(text, skin);
        l.setAlignment(Align.center);
        return l;
    }

    @Override
    public void resize(int i, int i1) {

    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }
}
