    import java.awt.*;

    import java.awt.geom.*;
    import java.awt.image.BufferedImage;
    import java.io.IOException;
    import java.sql.SQLOutput;
    import java.util.Random;

    import javafx.animation.AnimationTimer;
    import javafx.application.Application;

    import static javafx.application.Application.launch;

    import javafx.scene.Group;
    import javafx.scene.Scene;
    import javafx.scene.input.MouseEvent;
    import javafx.scene.input.MouseDragEvent;
    import javafx.scene.layout.BorderPane;
    import javafx.stage.Stage;

    import javax.imageio.ImageIO;

    import org.jfree.fx.FXGraphics2D;
    import org.jfree.fx.ResizableCanvas;

    public class Spotlight extends Application {
        private ResizableCanvas canvas;
        private Shape shape ;
        BorderPane mainPane = new BorderPane();

        @Override
        public void start(Stage stage) throws Exception
        {



            mainPane.setCenter(canvas);
            FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());


            canvas.setOnMouseDragged(event -> mouseDragged(event,g2d));
            new AnimationTimer() {
                long last = -1;

                @Override
                public void handle(long now)
                {
                    if (last == -1)
                        last = now;
                    update((now - last) / 1000000000.0);
                    last = now;
                    draw(g2d);
                }
            }.start();

            stage.setScene(new Scene(mainPane));
            stage.setTitle("Spotlight");
            stage.show();
        }


        public void draw(FXGraphics2D graphics)
        {
            graphics.setTransform(new AffineTransform());

            graphics.setBackground(Color.white);
            graphics.setColor(Color.BLACK);
            graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());



//            graphics.draw(shape);
            graphics.clip(shape);

            Random r = new Random();
            for(int i = 0; i < 1000; i++) {
                graphics.setPaint(Color.getHSBColor(r.nextFloat(),1,1));
    //            System.out.println(canvas.getWidth() + "    " + canvas.getHeight());
                graphics.drawLine(r.nextInt((int)canvas.getWidth()+1), r.nextInt((int)canvas.getHeight()+1),
                        r.nextInt((int)canvas.getWidth()+1) , r.nextInt((int)canvas.getHeight()+1));
            }
            graphics.setClip(null);
        }

        public void init()
        {
            canvas = new ResizableCanvas(g -> draw(g), mainPane);
            shape = new Ellipse2D.Double(canvas.getWidth()/2-100, canvas.getHeight()/2-100, 200, 200);
        }

        public void update(double deltaTime)
        {

        }

        public void mouseDragged(MouseEvent e,FXGraphics2D g2d){
            shape = new Ellipse2D.Double(e.getX(),e.getY(), 200, 200);
            draw(g2d);
        }

        public static void main(String[] args)
        {
            launch(Spotlight.class);
        }

    }
