import java.awt.*;
import java.awt.geom.*;
import java.util.Random;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

public class Spirograph extends Application {
    private TextField v1;
    private TextField v2;
    private TextField v3;
    private TextField v4;
    private Canvas canvas = new Canvas(1280, 960);
    private FXGraphics2D graphics = new FXGraphics2D(canvas.getGraphicsContext2D());
    @Override
    public void start(Stage primaryStage) throws Exception {
        AffineTransform affineTransform = this.graphics.getTransform();// de 0,0 naar linksboven zetten
        graphics.translate(this.canvas.getWidth() / 2, this.canvas.getHeight() / 2);
        graphics.scale(1, -1);
        VBox mainBox = new VBox();
        HBox topBar = new HBox();


        mainBox.getChildren().add(topBar);
        mainBox.getChildren().add(new Group(canvas));
        
        topBar.getChildren().add(v1 = new TextField("300"));
        topBar.getChildren().add(v2 = new TextField("1"));
        topBar.getChildren().add(v3 = new TextField("300"));
        topBar.getChildren().add(v4 = new TextField("10"));

        Button start = new Button("Create Spinograph");
        Button clear = new Button("Clear Canvas");// nieuwe buttons
        Button random = new Button("Random");
        topBar.getChildren().add(start);
        topBar.getChildren().add(clear);
        start.setOnAction(event -> {
            draw(graphics);// de methode oproepen
        });
        clear.setOnAction(event -> {
            graphics.setTransform(affineTransform);// 0,0 linksboven zetten in canvas
            graphics.setBackground(Color.white);//om een schone canvas te hebben zetten we de background op wit
            graphics.clearRect(0,0,(int) canvas.getWidth(), (int) canvas.getHeight());// begint van 0,0 en gaat naar de max van canvas. creert een vierkant.tekent het over de

            graphics.translate(this.canvas.getWidth() / 2, this.canvas.getHeight() / 2);
            graphics.scale(1, -1);// Hij moet weer terug gezet worden anders heb je een spirograaf die op de hoek begint van de canvas
        });


        primaryStage.setScene(new Scene(mainBox));
        primaryStage.setTitle("Spirograph");
        primaryStage.show();
    }
    
    
    public void draw(FXGraphics2D graphics) {
        Random random = new Random();
        this.graphics.setColor(new Color(random.nextInt(1999999999)));
        double resolution = 0.01;
        double scaler = 1;

        double a =Double.parseDouble(v1.getText());
        double b =Double.parseDouble(v2.getText());
        double c =Double.parseDouble(v3.getText());
        double d =Double.parseDouble(v4.getText());// gets the text

        double lastX  = a * Math.cos(b * 0) + c * Math.cos(d * 0);//hij moet van teta beginnen dat is in dit geval nul
        double lastY = 0;//

        for (double teta = 0; teta <10 ; teta += resolution) {
            double x = a * Math.cos(b * teta) + c * Math.cos(d * teta);
            double y = a * Math.sin(b * teta) + c * Math.sin(d * teta);
            System.out.println(y);//debug
            graphics.draw(new Line2D.Double((int) (x * scaler), (int) (y * scaler), (int) (lastX * scaler), (int) (lastY * scaler)));
            lastY = y;
            lastX = x;
        }

        //you can use Double.parseDouble(v1.getText()) to get a double value from the first textfield
        //feel free to add more textfields or other controls if needed, but beware that swing components might clash in naming
    }

    public static void main(String[] args) {
        launch(Spirograph.class);
    }

}
