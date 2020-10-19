import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;  // A scene is everything within the frame
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage; // A stage is everything including the frame
import java.util.ArrayList;
import java.util.Random;


public class Main extends Application {

    static int windowHeight = 720;
    static int windowWidth = 1080;
    static int speed = 17;
    static int counter = 0;
    static Snake snake = new Snake(5);
    static Food food = new Food();

    static boolean isSuperFood = false; // testing
    static Food superFood = new Food(); //testing
    static Random random = new Random();
    static int spawnHolder = 0;
    static int bound = 300;
    static boolean gameOver = false;
    static int blockSize = 20;
    static ArrayList<Food> badFoodList = new ArrayList<Food>();
    static int moreBadFood = 0;


    @Override
    public void start(Stage stage) throws Exception {
        // Initialization
        Pane mainWindow = new Pane();
        Scene mainScene = new Scene(mainWindow, windowWidth, windowHeight);
        mainScene.getStylesheets().add("stylesheet.css");
        Button mainButton = new Button();
        mainButton.setPrefWidth(100);
        mainButton.setLayoutX(windowWidth / 2 - mainButton.getPrefWidth());
        mainButton.setLayoutY(windowHeight / 2);


        mainButton.setText("Play Game");

        mainWindow.getChildren().addAll(mainButton);

        Pane window = new Pane();
        Scene gameScene = new Scene(window, windowWidth, windowHeight);
        Canvas canvas = new Canvas(windowWidth, windowHeight);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        window.getChildren().add(canvas);

        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.W && !"down".equals(snake.getSnakeDirection())) {
                    snake.setDirection("up");
                }
                if (keyEvent.getCode() == KeyCode.D && !"left".equals(snake.getSnakeDirection())) {
                    snake.setDirection("right");
                }
                if (keyEvent.getCode() == KeyCode.A && !"right".equals(snake.getSnakeDirection())) {
                    snake.setDirection("left");
                }
                if (keyEvent.getCode() == KeyCode.S && !"up".equals(snake.getSnakeDirection())) {
                    snake.setDirection("down");
                }
            }
        });

        // If currentScene != gameScene --> Display mainMeny
        new AnimationTimer() {
            long lastTick = 0;

            public void handle(long now) {
                if(lastTick == 0) {
                    lastTick = now;
                    if (stage.getScene() == gameScene) {
                        render(graphicsContext);
                    }
                    if (gameOver == true) {
                        stage.setScene(mainScene);
                    }
                    return;
                }
                if (now - lastTick > 1000000000 / speed){
                    lastTick = now;
                    if (stage.getScene() == gameScene) {
                        render(graphicsContext);
                    }
                    if (gameOver == true) {
                        stage.setScene(mainScene);
                    }
                }
            }
        }.start();

        mainButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.setScene(gameScene);
                snake.reset();
                gameOver = false;
                counter = 0;
                badFoodList = new ArrayList<Food>();
            }
        });

        stage.setTitle("Slyther");
        stage.setScene(mainScene);
        stage.setResizable(false);
        stage.show();
    }

    public static void render(GraphicsContext graphicsContext) {

        // All parts of body, except head, is set to the position of the next part of the body
        ArrayList<SnakeBody> snakeBodyPositions = snake.getSnakeList();
        for (int i = snake.snakeSize() - 1; i >= 1; i--) {
            snakeBodyPositions.get(i).setSnakeBodyX(snakeBodyPositions.get(i - 1).getSnakeBodyX());
            snakeBodyPositions.get(i).setSnakeBodyY(snakeBodyPositions.get(i - 1).getSnakeBodyY());

        }
        switch (snake.getSnakeDirection()) {
            case "right":
                snakeBodyPositions.get(0).increaseSnakeBodyX(20);
                break;
            case "left":
                snakeBodyPositions.get(0).increaseSnakeBodyX(-20);
                break;
            case "up":
                snakeBodyPositions.get(0).increaseSnakeBodyY(-20);
                break;
            case "down":
                snakeBodyPositions.get(0).increaseSnakeBodyY(20);
                break;
        }

        if (snakeBodyPositions.get(0).getSnakeBodyX() == food.getPosX()) {
            if (snakeBodyPositions.get(0).getSnakeBodyY() == food.getPosY()) {
                snake.increaseLength();
                food.respawnFood();
                counter++;
                moreBadFood++;
                System.out.printf("Current Score --> %d\n", counter);
            }
        }


        graphicsContext.setFill(Color.GREEN);
        graphicsContext.fillRect(0, 0, windowWidth, windowHeight);

        ArrayList<SnakeBody> snakeBodies = snake.getSnakeList();
        for (SnakeBody snakeBlock : snakeBodies) {
            graphicsContext.setFill(Color.DARKGREEN);
            graphicsContext.fillRect(snakeBlock.getSnakeBodyX(), snakeBlock.getSnakeBodyY(), snake.getSnakeWidth(), snake.getSnakeHeight());
        }

        graphicsContext.setFill(Color.RED);
        graphicsContext.fillRect(food.getPosX(), food.getPosY(), food.getBlockSize(), food.getBlockSize());

        // Everything underneath --> Testing
        if (isSuperFood == false){
            if (spawnHolder == random.nextInt(bound)) {
                isSuperFood = true;
            }
        }

        if (isSuperFood) {
            graphicsContext.setFill(Color.YELLOW);
            graphicsContext.fillRect(superFood.getPosX(), superFood.getPosY(), superFood.getBlockSize(), superFood.getBlockSize());
            if (superFood.getPosX() == snakeBodyPositions.get(0).getSnakeBodyX()) {
                if (superFood.getPosY() == snakeBodies.get(0).getSnakeBodyY()) {
                    superFood.respawnFood();
                    isSuperFood = false;
                    counter += 2;
                    moreBadFood++;
                    snake.increaseLength();
                    snake.increaseLength();
                    System.out.println("SUPER FOOD");
                    System.out.printf("Current score --> %d\n", counter);
                }
            }
        }

        if (snakeBodyPositions.get(0).getSnakeBodyX() < 0) {
            gameOver = true;
        }
        if (snakeBodyPositions.get(0).getSnakeBodyX() > windowWidth - blockSize) {
            gameOver = true;
        }
        if (snakeBodyPositions.get(0).getSnakeBodyY() < 0) {
            gameOver = true;
        }
        if (snakeBodyPositions.get(0).getSnakeBodyY() > windowHeight - blockSize)  {
            gameOver = true;
        }

        for (int i = 1; i < snake.snakeSize(); i++) {
            if (snakeBodies.get(i).getSnakeBodyX() == snakeBodies.get(0).getSnakeBodyX()) {
                if (snakeBodies.get(i).getSnakeBodyY() == snakeBodies.get(0).getSnakeBodyY()) {
                    gameOver = true;
                }
            }
        }

        if (moreBadFood == 5) {
            badFoodList.add(new Food());
            moreBadFood = 0;
        }

        for (Food badFood : badFoodList) {
            graphicsContext.setFill(Color.BLACK);
            graphicsContext.fillRect(badFood.getPosX(), badFood.getPosY(), badFood.getBlockSize(), badFood.getBlockSize());
            if (snakeBodies.get(0).getSnakeBodyX() == badFood.getPosX()) {
                if (snakeBodies.get(0).getSnakeBodyY() == badFood.getPosY()) {
                    gameOver = true;
                }
            }
        }



    }


    public static void main(String[] args) {
        launch(args); //Method inside Application, that launches application to sett the application up and calls the start method above
    }
}