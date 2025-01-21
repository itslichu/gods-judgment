import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;


//this was the class to more easily initialize the grid (changing it depending on the building's four walls but there were errors so we ended uup leaving in out)
class Coordinates {
    int x;
    int y;
    int width;
    int height;

    public Coordinates(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}


//class for buildings, crops, etc.
public class Structures {
    int height;
    int width;
    Pair[] structures = new Pair[18];
    int numStructures = 18;
    static BufferedImage house, barn, carpenterworkplace, guardworkplace, herbalistworkplace, statue, cropunplanted, cropplanted, cropwatered, cropgrown, fire_1, fire_2, chair, reversedchair, table, reversedtable, redcarpet, greencarpet, purplecarpet;
    int[][] crops = new int[30][30];
    //this was to record coordinates to change the grid
    Coordinates[] buildingCoordinates = new Coordinates[11];

    public Structures(World w) {
        //these instances represent the buildings' information
        buildingCoordinates[0] = new Coordinates(230, 30, 300, 300);
        buildingCoordinates[1] = new Coordinates(580, 50, 260, 260);
        buildingCoordinates[2] = new Coordinates(890, 90, 180, 180);
        buildingCoordinates[3] = new Coordinates(280, 380, 180, 180);
        buildingCoordinates[4] = new Coordinates(820, 400, 390, 130);
        buildingCoordinates[5] = new Coordinates(230, 610, 130, 130);
        buildingCoordinates[6] = new Coordinates(400, 610, 130, 130);
        buildingCoordinates[7] = new Coordinates(570, 610, 130, 130);
        buildingCoordinates[8] = new Coordinates(740, 610, 130, 130);
        buildingCoordinates[9] = new Coordinates(910, 610, 130, 130);
        buildingCoordinates[10] = new Coordinates(1080, 610, 130, 130);
        // for (int i = 0; i < 5; i++) {
        //     initializeGrid(buildingCoordinates[i].x, buildingCoordinates[i].y, buildingCoordinates[i].width, buildingCoordinates[i].height, w.grid);
        // }
        // for (int i = 0; i < 77; i++) {
        //     for (int j = 0; j < 104; j++){
        //         System.out.print(w.grid[i][j] + " ");
        //     } 
        //     System.out.println();
        // }
        //locations of structures on the map
        structures[0] = new Pair(230, 610);
        structures[1] = new Pair (400, 610);
        structures[2] = new Pair (570, 610);
        structures[3] = new Pair (740, 610);
        structures[4] = new Pair (910, 610);
        structures[5] = new Pair (1080, 610); // these first six are houses
        structures[6] = new Pair (680, 350); // this is the statue
        structures[7] = new Pair (820, 400); // this is the carpenter workspace
        structures[8] = new Pair (580, 50); // this is the barn
        structures [9] = new Pair (280, 380); // this is the guard workspace
        structures[10] = new Pair (890, 90); // this is the herbalist workspace
        structures[11] = new Pair (980, 490); // chair
        structures[12] = new Pair (1000, 490); // reversed chair
        structures[13] = new Pair (980, 410); // table
        structures[14] = new Pair (1010, 410); // reversed table
        structures[15] = new Pair(340,480); // red carpet for the guards
        structures[16] = new Pair(950,190); // green carpet for the herbalists
        structures[17] = new Pair(1040,435); // purple carpet for the carpenters
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                crops[i][j] = 0;
            }
        }
        getImage();

    }
    public void getImage() {
        try {
            house = ImageIO.read(new File("resources/house.png"));
            barn = ImageIO.read(new File("resources/barn.png"));
            carpenterworkplace = ImageIO.read(new File("resources/carpenterworkplace.png"));
            guardworkplace = ImageIO.read(new File("resources/guardworkplace.png"));
            herbalistworkplace = ImageIO.read(new File("resources/herbalistworkplace.png"));
            statue = ImageIO.read(new File("resources/statue.png"));
            cropunplanted = ImageIO.read(new File("resources/cropunplanted.png"));
            cropwatered = ImageIO.read(new File("resources/cropwatered.png"));
            cropplanted = ImageIO.read(new File("resources/cropplanted.png"));
            cropgrown = ImageIO.read(new File("resources/cropgrown.png"));
            chair = ImageIO.read(new File("resources/chair.png"));
            reversedchair = ImageIO.read(new File("resources/reversedchair.png"));
            table = ImageIO.read(new File("resources/table.png"));
            reversedtable = ImageIO.read(new File("resources/reversedtable.png"));
            redcarpet = ImageIO.read(new File("resources/redcarpet.png"));
            greencarpet = ImageIO.read(new File("resources/greencarpet.png"));
            purplecarpet = ImageIO.read(new File("resources/purplecarpet.png"));
        } catch (IOException e) {
            e.printStackTrace(); // all buffered images
        }
    }

    //this was an attempt to initialize the grid
    public void initializeGrid(int x, int y, int width, int height, int[][] grid) {
        for (int i = y / 10; i < (y + height) / 10; i++) {
            grid[i][(x-200) / 10] =1; 
            grid[i][(x + width - 200) / 10] = 1;
        }
        for (int j =(x-200) / 10; j < (x + width - 200) / 10; j++) {
            grid[ y / 10] [j] = 1;
            grid[(y + height) / 10][j] = 1;
        }
        //doors - different versions (upper and right)
        for (int j = (x + width / 2 - 240) / 10; j < (x + width / 2 - 160) / 10; j++) {
            grid[ y / 10] [j] = 0;
        }
        for (int i = (y + height / 2 - 40) / 10; i < (y + height / 2 + 40) / 10; i++) {
            grid[i][(x + width - 200) / 10] = 0;
        }
    } // this grid is part of our failed implementation of collision, not used within the code

    //drawing buildings on the map
    public void drawStructures(Graphics g) {
        BufferedImage[] todraw = new BufferedImage[]{house, house, house, house, house, house, statue, carpenterworkplace, barn, guardworkplace, herbalistworkplace, chair, reversedchair, table, reversedtable, redcarpet, greencarpet, purplecarpet};
        BufferedImage image = null;
        for (int i = 0; i < numStructures; i++) {
            image = todraw[i];
            if (i == 11 || i == 12) {
                for (int j = 0; j < 4; j++) {
                    g.drawImage(image, (int) structures[i].x + 50*j, (int) structures[i].y, null);
                }
            }
            else if (i == 13 || i == 14) {
                for (int j = 0; j < 3; j++) {
                    g.drawImage(image, (int) structures[i].x + 60*j, (int) structures[i].y, null);
                }
            }
            else {
                g.drawImage(image, (int) structures[i].x, (int) structures[i].y, null);
            }
        } // draws each of the structures on the map according to the structures array
    }

    //drawing crops based on their interaction with instances of the Farmer class
    //we use a grid to represent the changing images/states for the cros and change it as a farmer passes by while doing a compatible work action
    public void drawCrops(Graphics g) {
        BufferedImage image = null;
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                if (crops[i][j] == 0) {
                    image = cropunplanted;
                }
                else if (crops[i][j] == 1) {
                    image = cropwatered;
                }
                else if (crops[i][j] == 2) {
                    image = cropplanted;
                }
                else if (crops[i][j] == 3) {
                    image = cropgrown;
                }
                if (crops[i][j] == 3) {
                    g.drawImage(image, 230 + j * 10, 30 + i * 10 - 22, null);
                }
                else {
                    g.drawImage(image, 230 + j * 10, 30 + i * 10, null);
                } // draws the crops depending on the status of the crops: planted v. unplante v. grown v. watered
            }
        }
    }
}
