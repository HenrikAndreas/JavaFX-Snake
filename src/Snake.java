import java.util.ArrayList;

public class Snake {
    private int initSize;
    private int height;
    private int width;
    private String direction;
    private ArrayList<SnakeBody> snakeBody;

    // 2D ArrayList with x and y coordinates
    // private ArrayList<ArrayList<Integer>> snakeList;

    public Snake(int initSize) {
        this.initSize = initSize;
        height = 20;
        width = 20;
        direction = "right";
        snakeBody = new ArrayList<SnakeBody>();
        for (int j = 0; j < initSize; j++) {
            snakeBody.add(new SnakeBody(width, height));
        }

    }
    // Improve to take a length parameter, then add in for loop
    public void increaseLength() {
        // creating x & y positions that are equal to the last snakeBody's x & y postition | adding the next bodypart at the end of the snake
        int xPos = snakeBody.get(snakeSize() -2).getSnakeBodyX();
        int yPos = snakeBody.get(snakeSize() -2).getSnakeBodyY();
        snakeBody.add(new SnakeBody(xPos, yPos));
        // System.out.println("-->" + snakeBody.get(snakeSize() -1).getSnakeBodyX());
    }
    public ArrayList getSnakeList() {
        return snakeBody;
    }

    public void setDirection(String newDirection) {
        direction = newDirection;
    }

    public int getSnakeHeight() {
        return height;
    }

    public int getSnakeWidth() {
        return width;
    }

    public String getSnakeDirection() {
        return direction;
    }

    public int snakeSize() {
        return snakeBody.size();
    }

    public void reset() {
        height = 20;
        width = 20;
        direction = "right";
        snakeBody = new ArrayList<SnakeBody>();
        for (int j = 0; j < initSize; j++) {
            snakeBody.add(new SnakeBody(width, height));
        }
    }


}
