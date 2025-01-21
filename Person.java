import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.Color;
import javax.swing.JButton;

public abstract class Person {
    //integers representing occupation --> this is used when we are checking what persontype a Person instance is
    public static final int FARMER = 0;
    public static final int CARETAKER = 1;
    public static final int HERBALIST = 2;
    public static final int CARPENTER = 3;
    public static final int GUARD = 4; 
    //integers matching to gender --> to make sure that the gender is chosen randomly when the villagers are spawned
    public static final int MALE = 0;
    public static final int FEMALE = 1;

    //variables used for calculating movement;
    Pair position;
    Pair velocity;

    //variables for movement collision and path generation
    Pair homeLocation, workLocation, pathInfo, bedLocation;
    Node path;

    //variables representing identity change (--> additional actions/effects) based on the events
    boolean isThief; //assigned randomly --> if true go around burning flowers
    boolean isMurderer; //addMurderer event --> if true will go around killing people
    boolean isInfected; //addPlague event --> if true will slowly lose health, infect others, and quarantine
    boolean isLocked;  //if isMurderer and caught by a guard will be locked in the prison

    //information(stats) variables
    String name, occupationString;
    int gender;
    JButton statsButton; 
    boolean statsButtonPressed;
    double happiness;
    double health;
     
    //these variables are used to implement sprite animation
    public int playerCounter = 0; // playerCounter updates every frame, playerNum shows frame changes
    public int playerNum = 1;

    //appearance variables
    Pair face = new Pair(0,0);
    Pair hair = new Pair(0,0);
    Appearance attributes = new Appearance();
    public BufferedImage[] appearance = new BufferedImage[6];

    public Person () {
        //initializing all the variables
        position = new Pair(380, 720);
        velocity = new Pair(100, 100);
        
        double probabOfThief = Math.random(); //probability of becoming a Thief
        if (probabOfThief <= 0.2) {
            isThief = true;
        }
        else {
            isThief = false;
        }
        isMurderer = false;
        isLocked = false;
        isInfected = false;

        happiness = 50;
        health = 100;
        gender = (int) (2 * Math.random());
        attributes.gender = this.gender;
        statsButtonPressed= false; 
        statsButton= new JButton();
        statsButton.setContentAreaFilled(false);
        statsButton.setBorderPainted(false);
        statsButton.setBounds((int) position.x+10, (int) position.y, 15, 25);
        statsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (statsButtonPressed) statsButtonPressed=false; 
                else statsButtonPressed= true; 
            }
            
        });
    }

    public Person (int i) {
        //overloading the constructor: can choose the gender of the villager
        this();
        if ((i % 2) == 0) {
            gender = Person.MALE;
        }
        else {
            gender = Person.FEMALE;
        }
        attributes.gender = this.gender;
    }

    public void resethead() { // this assists in the movement of eyes, mouth, and hair when drawing the layers of sprites
        face.x = 0;
        face.y = 0;            // this specific method resets the offset of the face and hair
        hair.x = 0;
        hair.y = 0;
    }

    public void update(World w, double time, long hour) {
        statsButton.setBounds((int) position.x+10, (int) position.y, 15, 25);
        if (isInfected) {
            //the villager is slowly losing health and happiness
            health -= 0.0001;
            happiness-= 0.01; 
            //if they go to the herbalist and restore their health, their infection is treated
            if (health >= 100) {
                isInfected = false;
            }
        }
        //this is for implementing sprite animation
        playerCounter++; //playerCounter --> # of frames/how many should pass before switching, playerNum --> which of the movement sprites
        if (playerCounter > 10) {
            playerNum += 1;
            if (playerNum == 61) {
                playerNum = 1;
            }
            playerCounter = 0;
        }
    } 


    //this method sets the appearance to sprites corresponding to walking
    public void walk() {
        resethead();
        if (velocity.x > 0) {
            face.x = 1;
            if ((playerNum % 2) == 1) {
                appearance = attributes.walkright; // every 10 frames, the drawn appearance of the person switches between walking and standing
            }
            else if ((playerNum % 2) == 0) {
                appearance = attributes.stand;
            }
        } 
        else if (velocity.x < 0) {
            face.x = -1;
            if ((playerNum % 2) == 1) {         // same here
                appearance = attributes.walkleft;
            }
            else if ((playerNum % 2) == 0) {
                appearance = attributes.stand;
            }
        }
        else {
            appearance = attributes.stand;
        }
    }

    public void eat() { // eating animation follows same principle
        resethead();
        if ((playerNum % 2) == 1) {
            appearance = attributes.eat1;
        }
        else if ((playerNum % 2) == 0) {
            appearance = attributes.eat2;
        }
    }

    public void sleep() { // sleeping animation
        resethead();
        appearance = attributes.sleep;
    }

    public void moveTo() {      // traverses through a path (found with linked lists and BFS), updating the position, toward the goal location
        if (path.parent != null) {
            path = path.parent;
            position = path.s.currentValue;
        }
    }

    //this changes the animation according to a specific work-related movement - abstract because unique to the subclasses
    public abstract void work(int type);

    //represents some interaction with the environment (world) due to work --> used to make code more structured in the World class
    public abstract void workInteraction(World w); 

    //for the plague event
    public void changeInfectedStatus (boolean status) { 
        this.isInfected= status; 
    }

    //for the add Murderer event
    public void changeMurdererStatus (boolean status) {
        this.isMurderer = status; 
    }

    public void getPlayerImage() { // method calls instance of Appearance class called in constructor, going through all class methods
        attributes.getImages();
        attributes.decideAppearance();
        attributes.decideName();
        attributes.decideBed();
    }


    //drawing the people
    public void draw(Graphics g) {
        //the infected, thieves, and murderers have different auras representing by encompassing circles of different colors - if have more than one of these identities, can only see one
        if (isThief) {
            g.setColor(Color.ORANGE); //thief - orange
            g.fillOval((int) position.x, (int) position.y, 32, 32);
        }
        if (isInfected) {
            g.setColor(Color.GREEN); //infected - green
            g.fillOval((int) position.x, (int) position.y, 32, 32);
        }
        if (isMurderer) {
            g.setColor(Color.RED); //murderer - red
            g.fillOval((int) position.x, (int) position.y, 32, 32);
        }

        for (int i = 0; i < appearance.length; i++) { // draws whatever the current apearance is set to: the face is adjusted according to the face offsets, and the same for the hair
            if (i == 2 || i == 3) {
                g.drawImage(appearance[i], (int) (position.x + face.x), (int) (position.y + face.y), null);
            }
            else if (i == 4) {
                g.drawImage(appearance[i], (int) (position.x + hair.x), (int) (position.y + hair.y), null);
            }
            else {
                g.drawImage(appearance[i], (int) (position.x), (int) (position.y), null);
            }
        }
    }

    //returns the name of the person
    public String toString () {
        return "Name: " + this.name; 
    }
}
