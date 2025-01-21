import java.awt.Graphics;
import java.util.Random;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

class Livestock {
    Random rand = new Random();
    Pair position = new Pair(612 + Math.random() * 170, 50 + Math.random()* 220); 
    Pair velocity = new Pair(-100 + Math.random() * 200, -100 + Math.random()*200);
    public BufferedImage left1, left2, right1, right2; //the walking animations with alternating feet
    //this is to implement animation;
    public int animalCounter = 0;
    public int animalNum = 1;
    //this is for cow collision (to manipulate the velocity)
    public boolean isMoving = true;

    //updates the position of the animals
    public void update(World w, double time){
        if (isMoving) {
        position = position.add(velocity.times(time));
        navigate(w);
        animalCounter++;
        if (animalCounter > 10) {
            animalNum *= -1;
            animalCounter = 0;
        }
    }
    }

    //draws the sprites
    public void draw(Graphics g) {
        BufferedImage image = null;
        if (velocity.x > 0) {
            if (animalNum == 1) {
                image = right1;
            }
            else if (animalNum == -1) {
                image = right2;
            }
        } 
        else {
            if (animalNum == 1) {
                image = left1;
            }
            else if (animalNum == -1) {
                image = left2;
            }
        }
        g.drawImage(image, (int) position.x, (int) position.y, null);
    }

    //navigates the barn
    public void navigate(World w) {
        if ((position.x < 590) || (position.x> 830 - 32)) {
            velocity.flipX();
        }
        else if ((position.y < 60) || (position.y > 300 - 32)) {
            velocity.flipY();
        }
    }
}

public class Cow extends Livestock {
    public Cow() {
        getCowImage();
    }

    //gets the animal sprites - similar for all animal classes
    public void getCowImage() {
        try {
            left1 = ImageIO.read(new File("resources/cow_left2.png"));
            left2 = ImageIO.read(new File("resources/cow_left1.png"));
            right1 = ImageIO.read(new File("resources/cow_right2.png"));
            right2 = ImageIO.read(new File("resources/cow_right1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

class Sheep extends Livestock {
    public Sheep() {
        getSheepImage();
    }

    public void getSheepImage() {
        try {
            left1 = ImageIO.read(new File("resources/sheep_left1.png"));
            left2 = ImageIO.read(new File("resources/sheep_left2.png"));
            right1 = ImageIO.read(new File("resources/sheep_right1.png"));
            right2 = ImageIO.read(new File("resources/sheep_right2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

class Pig extends Livestock{
    public Pig() {
        getPigImage();
    }

    public void getPigImage() {
        try {
            left1 = ImageIO.read(new File("resources/pig_left1.png"));
            left2 = ImageIO.read(new File("resources/pig_left2.png"));
            right1 = ImageIO.read(new File("resources/pig_right1.png"));
            right2 = ImageIO.read(new File("resources/pig_right2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

class Chicken extends Livestock{
    public Chicken() {
        getChickenImage();
    }

    public void getChickenImage() {
        try {
            left1 = ImageIO.read(new File("resources/chicken_left1.png"));
            left2 = ImageIO.read(new File("resources/chicken_left2.png"));
            right1 = ImageIO.read(new File("resources/chicken_right1.png"));
            right2 = ImageIO.read(new File("resources/chicken_right2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
