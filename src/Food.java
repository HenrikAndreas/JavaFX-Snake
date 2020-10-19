import java.util.Random;

public class Food {
    int blockSize;
    int width;
    int height;
    int xPos;
    int yPos;
    Random random;

    public Food() {
        random = new Random();
        blockSize = 20;
        width = 1080;
        height = 720;
        xPos = 20 * random.nextInt(width / blockSize);
        yPos = 20 * random.nextInt(height / blockSize);
    }

    public void respawnFood() {
        xPos = 20 * random.nextInt(width / blockSize);
        yPos = 20 * random.nextInt(height / blockSize);
        


    }
    public int getPosX() {
        return xPos;
    }
    public int getPosY() {
        return yPos;
    }
    public int getBlockSize() {
        return blockSize;
    }
}
