import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

//this is to create the blood movement as a person is killed by a murderer
class Blood {
    public Pair position;
    static BufferedImage blood1, blood2;
    int bloodNum = 1;
    int bloodCounter = 0;

    public Blood() {
        position = new Pair(-50, -50);
        getBloodImage();

    }

    public void getBloodImage() {
        try {
            blood1 = ImageIO.read(new File("resources/blood1.png"));
            blood2 = ImageIO.read(new File("resources/blood2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) {
        BufferedImage image = null;
        if (bloodNum == -1) {
            image = blood1;
        }
        else if (bloodNum == 1) {
            image = blood2;
        }
        g.drawImage(image, (int) position.x, (int) position.y, null);
    }

    //represented by an array which is similar to those of world's people; the fire goes out of the screen a while after the person dies so that the blood won't be hanging there indefinitely
    public void update (World w, double time) {
        if (position.x != -50 && position.y != -50) {
            bloodCounter++;
            if (bloodCounter % 10 == 0) {
                bloodNum *= -1;
            }
            if (bloodCounter > 100) {
                this.position.x = -50;
                this.position.y = -50;
            }
        }
    }
}

//this is to implement seasonal changes - elements falling down from the sky depending on the season
class Thing {
    public Pair position;
    public Pair velocity;
    static BufferedImage snow, leaf, petal, sunray;
    static BufferedImage image = null;
    public Pair vel = new Pair(0, Math.random() * 500); //records velocity for each instantiation so that it can be returned after we make it stop depending on a random variable(fall or not fall today)

    public Thing(){
        position = new Pair(200 + Math.random() * 1040, -50); // initially out of the screen
        velocity = vel;
        getImage();
    }

    public void getImage() {
        try {
            snow = ImageIO.read(new File("resources/snow.png"));
            leaf = ImageIO.read(new File("resources/leaf.png"));
            petal = ImageIO.read(new File("resources/petal.png"));
            sunray = ImageIO.read(new File("resources/sunray.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(World w, double time){
        if (position.y > 770) {
            position.y = -50;
        }
        position = position.add(velocity.times(time));
    }

    public void draw(Graphics g) {
        g.drawImage(image, (int) position.x, (int) position.y, null);
    }

    public static void changeImage(String season) {
        if (season == "winter") {
            image = snow;
        }
        if (season == "spring") {
            image = petal;
        }
        if (season == "summer") {
            image = sunray;
        }
        if (season == "autumn") {
            image = leaf;
        }
    }

}
