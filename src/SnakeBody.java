public class SnakeBody {
    private int xPos;
    private int yPos;

    public SnakeBody(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public int getSnakeBodyX() {
        return xPos;
    }
    public int getSnakeBodyY() {
        return yPos;
    }

    public void setSnakeBodyY(int newY) {
        yPos = newY;
    }
    public void setSnakeBodyX(int newX) {
        xPos = newX;
    }

    public void increaseSnakeBodyY(int newY) {
        yPos = yPos + newY;
    }
    public void increaseSnakeBodyX(int newX) {
        xPos = xPos + newX;
    }


}
