package src;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.ArrayList;

public class Felix extends Application {
    static Canvas canvas = new Canvas(600, 600);
    Particle selectedParticle;

    private Parent createContent() {
        HBox layout = new HBox();
        VBox sidebar = new VBox();

        Label label = new Label("hi");
        sidebar.getChildren().add(label);

        //layout.getChildren().add(sidebar);
        layout.getChildren().add(canvas);

        canvas.setOnMouseDragged((event) -> {
            if (selectedParticle == null)
                return;
            Vector mousePos = new Vector((float)event.getX()-selectedParticle.radius, (float)event.getY()-selectedParticle.radius);
            RubberbandConstraint constraint = new RubberbandConstraint(selectedParticle, mousePos, 0.1f);
            Physics.dynamicConstraints.add(constraint);
        });

        canvas.setOnMousePressed((event) -> {
            for (Particle particle: Physics.particles)
            {
                if (particle.posn.dist(new Vector((float)event.getX()-particle.radius, (float)event.getY()-particle.radius)) < 10)
                {
                    selectedParticle = particle;
                    break;
                }
            }
        });

        return layout;
    }

    @Override
    public void start(Stage stage) {
        stage.setScene(new Scene(createContent(), 600, 600));
        stage.show();

        Physics.initParticles();

        new AnimationTimer() {
            @Override public void handle(long currentNanoTime) {
                loop();
            }
        }.start();// open window
    }

    public static void main(String[] args) {
        launch(args);
    }


    public static void loop()
    {
        for (int i = 0; i < 30; i++)
        {
            Physics.method();
            render(Physics.particles, Physics.constraints);
        }
    }

    public static void render(ArrayList<Particle> particles, ArrayList<Constraint> constraints) {
        GraphicsContext contxt = canvas.getGraphicsContext2D();
        contxt.clearRect(0, 0, 600, 600);

        for (Constraint constraint: constraints)
        {
            if (constraint instanceof DistanceConstraint)
            {
                DistanceConstraint distConst = (DistanceConstraint)constraint;
                Vector pos1 = distConst.p1.posn;
                Vector pos2 = distConst.p2.posn;
                float rad1 = distConst.p1.radius;
                float rad2 = distConst.p2.radius;
                contxt.strokeLine(pos1.x+rad1, pos1.y+rad1, pos2.x+rad2, pos2.y+rad2);
            }
            if (constraint instanceof BoxConstraint)
            {
                BoxConstraint boxConst = (BoxConstraint) constraint;
                float radius = boxConst.particle.radius;
                float minX = Math.min(boxConst.corner1.x, boxConst.corner2.x);
                float maxX = Math.max(boxConst.corner1.x, boxConst.corner2.x) + 2*radius;
                float minY = Math.min(boxConst.corner1.y, boxConst.corner2.y);
                float maxY = Math.max(boxConst.corner1.y, boxConst.corner2.y) + 2*radius;

                contxt.strokeLine(minX, minY, maxX, minY);
                contxt.strokeLine(minX, maxY, maxX, maxY);
                contxt.strokeLine(minX, minY, minX, maxY);
                contxt.strokeLine(maxX, minY, maxX, maxY);
            }
        }

        for (Particle particle : particles) {
            contxt.setFill(Color.valueOf(particle.color));
            contxt.fillOval(particle.posn.x, particle.posn.y, particle.radius * 2, particle.radius * 2);
        }
    }
}