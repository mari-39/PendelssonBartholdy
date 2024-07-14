package src;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Felix extends Application {
    static Canvas canvas = new Canvas(600, 600);
    static Particle selectedParticle;
    static Vector mousePos;
    static boolean dragging = false;

    static boolean paused = false;
    static boolean doStep = false;

    private Parent createContent() {
        HBox layout = new HBox();
        VBox sidebar = new VBox();

        Button setup1 = new Button("Double pendulum");
        Button setup2 = new Button("Collisions");
        Button setup5 = new Button("More collisions");
        Button setup3 = new Button("Angleconstraint !?");
        Button setup4 = new Button("Rope");

        Button pause = new Button("Pause");
        Button step = new Button("Step");

        pause.setPrefWidth(150);
        step.setPrefWidth(150);

        pause.setOnAction((event -> {
            paused = !paused;
        }));

        step.setOnAction((event -> {
            doStep = true;
        }));

        setup1.setOnAction(Physics::setup1);
        setup2.setOnAction(Physics::setup2);
        setup3.setOnAction(Physics::setup3);
        setup4.setOnAction(Physics::setup4);
        setup5.setOnAction(Physics::setup5);

        setup1.setPrefWidth(150);
        setup2.setPrefWidth(150);
        setup3.setPrefWidth(150);
        setup4.setPrefWidth(150);
        setup5.setPrefWidth(150);

        sidebar.getChildren().add(setup1);
        sidebar.getChildren().add(setup2);
        sidebar.getChildren().add(setup5);
        sidebar.getChildren().add(setup3);
        sidebar.getChildren().add(setup4);

        sidebar.getChildren().add(new Separator());

        sidebar.getChildren().add(pause);
        sidebar.getChildren().add(step);

        sidebar.setSpacing(5.0);
        sidebar.setPadding(new Insets(5));

        layout.getChildren().add(sidebar);
        layout.getChildren().add(canvas);

        canvas.setOnMouseDragged((event) -> {
            if (selectedParticle == null)
                return;
            mousePos = new Vector((float)event.getX()-selectedParticle.radius, (float)event.getY()-selectedParticle.radius);
        });

        canvas.setOnMousePressed((event) -> {
            dragging = true;
            for (Particle particle: Physics.particles)
            {
                if (particle.posn.dist(new Vector((float)event.getX()-particle.radius, (float)event.getY()-particle.radius)) < 10)
                {
                    selectedParticle = particle;
                    break;
                }
            }
            selectedParticle.color = "#1144ff";
        });

        canvas.setOnMouseReleased((event) -> {
            dragging = false;
            selectedParticle.color = "#990077";
        });

        return layout;
    }

    @Override
    public void start(Stage stage) {
        stage.setScene(new Scene(createContent(), 900, 600));
        stage.show();

        Physics.setup1(null);

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
        if (dragging && mousePos != null && selectedParticle != null) {
            Vector dir = mousePos.sub(selectedParticle.posn);
            selectedParticle.force = selectedParticle.force.add(dir.times(100));
        }

        if (!paused || doStep)
        {
            doStep = false;
            for (int i = 0; i < 30; i++)
            {
                Physics.method();
            }
        }
        render(Physics.particles, Physics.constraints);
    }

    public static void render(ArrayList<Particle> particles, ArrayList<Constraint> constraints) {
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.clearRect(0, 0, 600, 600);

        for (Constraint constraint: constraints)
        {
            if (constraint instanceof DistanceConstraint)
            {
                DistanceConstraint distConst = (DistanceConstraint)constraint;
                Vector pos1 = distConst.p1.posn;
                Vector pos2 = distConst.p2.posn;
                float rad1 = distConst.p1.radius;
                float rad2 = distConst.p2.radius;
                context.strokeLine(pos1.x+rad1, pos1.y+rad1, pos2.x+rad2, pos2.y+rad2);
            }
            if (constraint instanceof BoxConstraint)
            {
                BoxConstraint boxConst = (BoxConstraint) constraint;
                float radius = boxConst.particle.radius;
                float minX = Math.min(boxConst.corner1.x, boxConst.corner2.x);
                float maxX = Math.max(boxConst.corner1.x, boxConst.corner2.x) + 2*radius;
                float minY = Math.min(boxConst.corner1.y, boxConst.corner2.y);
                float maxY = Math.max(boxConst.corner1.y, boxConst.corner2.y) + 2*radius;

                context.strokeLine(minX, minY, maxX, minY);
                context.strokeLine(minX, maxY, maxX, maxY);
                context.strokeLine(minX, minY, minX, maxY);
                context.strokeLine(maxX, minY, maxX, maxY);
            }
        }

        for (Particle particle : particles) {
            context.setFill(Color.valueOf(particle.color));
            context.fillOval(particle.posn.x, particle.posn.y, particle.radius * 2, particle.radius * 2);
        }
    }
}