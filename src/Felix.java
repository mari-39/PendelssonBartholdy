package src;
// import javafx
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

// render the Positions of the Particles
public class Felix extends Application {
    static Canvas canvas = new Canvas(600, 600);

    public static void render(ArrayList<Vector> input) {
        GraphicsContext contxt = canvas.getGraphicsContext2D();
        contxt.clearRect(0, 0, 600, 600);
        contxt.setFill(Color.BLUE);
        int rad = 30;
        for (int i = 0; i < input.size(); i++) {
            contxt.fillOval(input.get(i).x, input.get(i).y, rad / 2, rad / 2);
        }
    }
// make StackPane
    private Parent createContent() {
        StackPane pane = new StackPane(canvas);
        return pane;
    }

    // make GUI and render Positions of Particles from ArrayList<Vector> arrlist
    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(createContent(), 600, 600));
        stage.show();

        Physics.initParticles();

        new AnimationTimer() {
            @Override public void handle(long currentNanoTime) {
                render(Physics.method());
            }
        }.start();// open window
    }

// main -> launch calls start
    public static void main(String[] args) {
        launch(args);
    }
}