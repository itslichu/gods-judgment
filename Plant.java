import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

public class Plant{
    Pair position;
    public BufferedImage flower, fire_1, fire_2, treeautumn, treespring, treesummer, treewinter; // buffered images
    int flowerCounter = 0; // flowerCounter goes up each frame, flowerNum changes every 10 frames for fire animation
    int flowerNum = 0;
    boolean isTree = false;
    static int counter; // decides the number of trees

    public Plant() {
        if (counter % 30 == 0) {
            isTree = true;
        }
        counter++;
        if (isTree) {
            position = new Pair(1070 + Math.random()*100, 30 + Math.random()*300); // trees are in a specified clearing
        }
        else {
            position = new Pair(200 + Math.random() * 880, 0 + Math.random() * 330); // flowers are everywhere on the screen
        }
        getFlowerImage();
    }

    public void getFlowerImage() { // reads in all buffered images for this class
        try {
            flower = ImageIO.read(new File("resources/flower.png"));
            fire_1 = ImageIO.read(new File("resources/fire_1.png"));
            fire_2 = ImageIO.read(new File("resources/fire_2.png"));
            treewinter = ImageIO.read(new File("resources/treewinter.png"));
            treespring = ImageIO.read(new File("resources/treespring.png"));
            treesummer = ImageIO.read(new File("resources/treesummer.png"));
            treeautumn = ImageIO.read(new File("resources/treeautumn.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) {
        BufferedImage image = null;
        if (flowerNum == -1) { // these lines are for the fire animation, as flowerNum *= -1 every few frames
            image = fire_1;
        }
        else if (flowerNum == 1) {
            image = fire_2;
        }
        else { // a normal flower or tree has flowerNum = 0, meaning it is not going through an animation
            if (isTree) {
                if (Time.season == Time.WINTER) {
                    image = treewinter;
                }
                else if (Time.season == Time.SPRING) {
                    image = treespring;
                }
                else if (Time.season == Time.SUMMER) {
                    image = treesummer;
                }
                else if (Time.season == Time.AUTUMN) {
                    image = treeautumn; // depending on the season, the tree looks differently!
                }
            }
            else {
                image = flower; // if the isTree boolean is false, the plant is a flower
            }
            }
        g.drawImage(image, (int) position.x, (int) position.y, null);
    }

    public void update (World w, double time) {
        if (flowerNum != 0) {
            flowerCounter++;
            if (flowerCounter % 10 == 0) { // frame counter for animation
                flowerNum *= -1;
            }
            if (flowerCounter > 100) { // if a flower has been burning for a certain amount of frames, it "disappears", aka moves off-screen
                this.position.x = -40;
                this.position.y = -40;
            }
        }
    }
}
