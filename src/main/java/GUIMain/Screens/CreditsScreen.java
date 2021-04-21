package GUIMain.Screens;

import GUIMain.GUI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import org.lwjgl.opengl.GL20;

public class CreditsScreen extends SimpleScreen {


    public CreditsScreen(GUI gui){
        super(gui);
    }

    @Override
    public void show() {
        super.show();
        parameter.size = Gdx.graphics.getHeight()/27;
        parameter.borderWidth = 2f;
        style.font = generator.generateFont(parameter);
        Table table = new Table();
        table.setBounds(0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Label title = new Label("Credits", style);
        table.add(title).spaceBottom(25f).row();
        Table temp = new Table();
        Table left = new Table();
        Table right = new Table();
        Table thanks = new Table();
        parameter.size = Gdx.graphics.getHeight()/47;
        parameter.borderWidth = 1f;
        style.font = generator.generateFont(parameter);
        String[] titlesLeft = new String[]{
                "Production Manager: Steinar Simonnes",
                "Office Administrator: Duki",
                "Special Projects Handyman: Sander Sigmundstad",
                "Meeting Coordinator: Ilyas Abukar Ali",
                "Head of Quality Assurance: Sander Sigmundstad",
                "Head of AI Development: Ilyas Abukar Ali",
                "Head of Graphical Development: Samuel James Cook",
                "Head of Animation: Sander Sigmundstad",
                "Head of Network Development : Duki",
                "Head of Finance: Samuel James Cook",
                "Head of Human Relations: Ilyas Abukar Ali",
                "Head of Auditing (Tests): Sander Sigmundstad",
                "Head of Planni..: Samuel James Cook",
                "Developer: Steinar Simonnes",
                "Developer: Sander Sigmundstad",
                "Developer: Dusan Nikolic",
                "Developer: Ilyas Abukar Ali",
                "Developer: Samuel James Cook",
        };
        String[] titlesRight = new String[]{
                "Technical Lead: Steinar Simonnes",
                "Dr. Jekyll: Duki",
                "Mr. Hyde: Dusan Nikolic",
                "Art: Samuel James Cook",
                "Art: Liv Eichner",
                "Lead Game Engine Developer: Steinar Simonnes",
                "Assistant to the Game Engine Developer: Duki",
                "Assistant to the Production Manager: Dusan Nikolic",
                "Author of Issues: Steinar Simonnes",
                "Winning Developer: Duki",
                "Button Masher: Samuel James Cook",
                "Tile Mason: Duki",
                "Number Theory Apprentice: Steinar Simonnes",
                "Git-Merge Conflict Creator: Dusan Nikolic",
                "Git-Merge Conflict Resolver: Duki",
                "Creator of Bullshit Titles: Samuel James Cook",
                "High Powered Optical Cannon Developer: Sander SigmundStad",
                "Robot Baptizer: Steinar Simonnes",
        };
        String[] spThanks = new String[]{
                "Special Thanks to:",
                "Steinar Simoness",
                "Dusan Nikolic",
                "Sander Sigmundstad",
                "Ilyas Abukar Ali",
                "Samuel James Cook",
                "Also Special Thanks to:",
                "Liv Eichner for Robot Design and Logo"
        };
        for (int i=0; i<titlesLeft.length;i++){
            Label temp1 = new Label(titlesLeft[i],style);
            if (i == titlesLeft.length -1){
                left.add(temp1);
            }
            else{
                left.add(temp1).row();
            }
        }
        for (int m=0; m<titlesRight.length;m++){
            Label temp2 = new Label(titlesRight[m],style);
            if (m == titlesRight.length -1){
                right.add(temp2);
            }
            else{
                right.add(temp2).row();
            }
        }
        for (int n=0; n<spThanks.length;n++){
            Label temp3 = new Label(spThanks[n],style);
            if (n == spThanks.length -1){
                thanks.add(temp3);
            }
            else{
                thanks.add(temp3).row();
            }
        }

        temp.add(left);
        temp.add(right);
        table.add(temp).spaceBottom(25f).row();
        table.add(thanks).spaceBottom(25f).row();
        TextButton menu = new TextButton("Menu", gui.getSkin());
        menu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                gui.setScreen(new MenuScreen(gui));
            }
        });
        table.add(menu).size(Gdx.graphics.getWidth()/7f,Gdx.graphics.getHeight()/22f).spaceBottom(Gdx.graphics.getHeight()/44f);

        stage.addActor(table);
    }

    @Override
    public void render(float v) {
        super.render(v);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
}