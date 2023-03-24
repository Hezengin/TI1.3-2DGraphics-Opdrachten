
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import javax.imageio.ImageIO;

public class AngryBirds extends Application {

    private ResizableCanvas canvas;
    private World world;
    private MousePicker mousePicker;
    private Camera camera;
    private boolean debugSelected = false;
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private BufferedImage background;

    @Override
    public void start(Stage stage) throws Exception {

        BorderPane mainPane = new BorderPane();

        // Add debug button
        javafx.scene.control.CheckBox showDebug = new CheckBox("Show debug");
        showDebug.setOnAction(e -> {
            debugSelected = showDebug.isSelected();
        });
        mainPane.setTop(showDebug);

        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

        camera = new Camera(canvas, g -> draw(g), g2d);
        mousePicker = new MousePicker(canvas);

        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1) {
                    last = now;
                }
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();

        stage.setScene(new Scene(mainPane, 1920, 1080));
        stage.setTitle("Angry Birds");
        stage.show();
        draw(g2d);
    }

    public void init() throws IOException {
        world = new World();
        world.setGravity(new Vector2(0, -9.8));

        //BackGround
        background = ImageIO.read(getClass().getResource("/images/background.png"));

        // Vloer
        Body floor = new Body();
        floor.addFixture(Geometry.createRectangle(40, 1));
        floor.getTransform().setTranslation(0, -2);
        floor.setMass(MassType.INFINITE);
        world.addBody(floor);

        //Boundry 1
        Body boundry1 = new Body();
        boundry1.addFixture(Geometry.createRectangle(0.15,20));
        boundry1.getTransform().setTranslation(20,5);
        boundry1.setMass(MassType.INFINITE);
        world.addBody(boundry1);

        //Boundry 2
        Body boundry2 = new Body();
        boundry2.addFixture(Geometry.createRectangle(0.15,20));
        boundry2.getTransform().setTranslation(-20,5);
        boundry2.setMass(MassType.INFINITE);
        world.addBody(boundry2);

        //Boundry 3
        Body boundry3 = new Body();
        boundry3.addFixture(Geometry.createRectangle(40,0.15));
        boundry3.getTransform().setTranslation(0,15);
        boundry3.setMass(MassType.INFINITE);
        world.addBody(boundry3);

        //Blocks
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                createBlocks(i,j);
            }
        }

        //Catapult
        Body catapult = new Body();
        catapult.addFixture(Geometry.createRectangle(0.01,1.5));
        catapult.getTransform().setTranslation(-6.5,-0.75);
        catapult.setMass(MassType.INFINITE);
        world.addBody(catapult);
        gameObjects.add(new GameObject("/images/catapult.png",catapult,new Vector2(0,0),0.2));

        //Angrybird
        Body angryBird = new Body();
        angryBird.addFixture(Geometry.createCircle(0.2));
        angryBird.getTransform().setTranslation(0,2.4);
        angryBird.setMass(MassType.NORMAL);
        angryBird.getFixture(0).setRestitution(0.75);
        world.addBody(angryBird);
        gameObjects.add(new GameObject("/images/angrybird.png",angryBird,new Vector2(0,0),0.05));
    }

    private void createBlocks(double i, double j) {
        Body block = new Body();
        block.addFixture(Geometry.createRectangle(0.5,0.5));
        block.getTransform().setTranslation(3+i*0.25,-1-j*0.25);
        block.setMass(MassType.NORMAL);
        block.getFixture(0).setRestitution(0.5);
        world.addBody(block);
        gameObjects.add(new GameObject("/images/block.png", block, new Vector2(0,0), 0.05));
    }

    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setPaint(new TexturePaint(background, new Rectangle2D.Double(0, 0,canvas.getWidth(), canvas.getHeight())));
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        AffineTransform originalTransform = graphics.getTransform();

        graphics.setTransform(camera.getTransform((int) canvas.getWidth(), (int) canvas.getHeight()));
        graphics.scale(1, -1);

        for (GameObject go : gameObjects) {
            go.draw(graphics);
        }

        if (debugSelected) {
            graphics.setColor(Color.blue);
            DebugDraw.draw(graphics, world, 100);
        }

        graphics.setTransform(originalTransform);
    }

    public void update(double deltaTime) {
        mousePicker.update(world, camera.getTransform((int) canvas.getWidth(), (int) canvas.getHeight()), 100);
        world.update(deltaTime);
    }

    public static void main(String[] args) {
        launch(AngryBirds.class);
    }

}
