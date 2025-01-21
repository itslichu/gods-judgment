import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class World {
    int height;
    int width;
    String nameOfVillage;
    int numPerson, totalVillagersAdded, totalDeaths, totalInfected, totalMurders, totalMurderers, totalRains; 
    //arrays representing the living objects, elements, etc.
    Person people[];
    Plant plants[];
    Cow cows[];
    Pig pigs[];
    Blood bloodstains[];
    Chicken chickens[];
    Thing elements[];
    Structures structure;
    Time theTime;
    boolean raining;
    JTextArea textArea; 
    JScrollPane labelOfEvents; 
    int[][] grid = new int[77][104];
    AudioInputStream audioForBackground, audioForChicken, audioForPlague, audioForMurder, audioForWolf, audioForRain, audioForDeath; 
    Clip backgroundClip, chickenClip, plagueClip, murderClip, wolfClip, rainClip, deathClip;
    Random randomObject;   
    boolean clinicHasVisitor = false;


    public World(int initWidth, int initHeight, int initNumPerson, String name, Time time) {
        //declaring the grid for movement collision
        for (int i = 0; i < 77; i++) {
            for (int j = 0; j < 104; j++) {
                grid[i][j] = 0;
            }
        }
        width = initWidth;
        height = initHeight;
        numPerson = initNumPerson;
        people = new Person[36]; //array of length 36; 36 is the max number of villagers
        nameOfVillage = name;
        plants = new Plant[100];
        cows = new Cow[100];
        pigs = new Pig[100];
        chickens = new Chicken[100];
        elements = new Thing[500];
        structure = new Structures(this);
        bloodstains = new Blood[100];//Here, we initialized arrays that will hold our cows, chickens, bloodstains, plants, people.
        int index = 0;
        //this is for the final screen -- summary of everything
        totalVillagersAdded= initNumPerson; //when constructing, the total number of villagers is just the initial number of villagers 
        totalDeaths=0; 
        totalInfected=0; 
        totalMurders=0; 
        totalMurderers=0; 
        totalRains=0;
        theTime = time;
        raining = false; 
        textArea= new JTextArea(); //allows for text to appear on our JPanel
        labelOfEvents= new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); //assigning textArea as a part of our JScrollPane allows textArea (or the event log) to be scrollable
        textArea.setEditable(false); //user cannot affect the event log
        labelOfEvents.setBounds(1270, 270, 150, 400);
        randomObject= new Random(); 

        //here, we initialize all the elements we have

        for (int i = 0; i < 500; i++) {
            elements[i] = new Thing();
        }

        for (int i = index; i < numPerson; i++) { //creating instances of our 5 main Person subclasses, with probabilities based on how we thought the village population should be arranged based on fucntionality.
            if (i < (numPerson/5)) {
                people[i] = new Farmer(i, this);
            }
            else if (i < (2*numPerson/5)) {
                people[i] = new Caretaker(i, this);
            }
            else if (i < (3*numPerson/5)) {
                people[i] = new Herbalist(i, this);
            }
            else if (i < (4*numPerson/5)) {
                people[i] = new Carpenter(i, this);
            }
            else {
                people[i] = new Guard(i, this);
            }
        }
        for (int i = 0; i < 100; i++) {
            plants[i] = new Plant();//creating 100 plants
        }
        for (int i = 0; i < 5; i++) {
            cows[i] = new Cow();
            pigs[i] = new Pig();
            chickens[i] = new Chicken(); //creating 15 animals
        }
        for (int i = 0; i < numPerson; i++) {
            bloodstains[i] = new Blood(); //creating a bloodstain for each person, in case they are murdered. 
        }
        //Initializing all of the sounds (until line 203)
        File musicFile= new File("resources/WorldBackground.wav"); 
        try {
            audioForBackground = AudioSystem.getAudioInputStream(musicFile);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        } 
        try {
            backgroundClip= AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } 
        try {
            backgroundClip.open(audioForBackground);
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
        backgroundClip.loop(10);
        File chickenFile= new File("resources/ChickenSound.wav"); 
        try {
            audioForChicken = AudioSystem.getAudioInputStream(chickenFile);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        } 
        try {
            chickenClip= AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } 
        try {
            chickenClip.open(audioForChicken);
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
        File coughFile= new File("resources/Cough.wav"); 
        try {
            audioForPlague = AudioSystem.getAudioInputStream(coughFile);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        } 
        try {
            plagueClip= AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } 
        try {
            plagueClip.open(audioForPlague);
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
        File KnifeFile= new File("resources/Knife.wav"); 
        try {
            audioForMurder = AudioSystem.getAudioInputStream(KnifeFile);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        } 
        try {
            murderClip= AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } 
        try {
           murderClip.open(audioForMurder);
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
        File wolfFile= new File("resources/Wolf.wav"); 
        try {
            audioForWolf = AudioSystem.getAudioInputStream(wolfFile);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        } 
        try {
            wolfClip= AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } 
        try {
           wolfClip.open(audioForWolf);
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
        File deathFile= new File("resources/Death.wav"); 
        try {
            audioForDeath = AudioSystem.getAudioInputStream(deathFile);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        } 
        try {
            deathClip= AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } 
        try {
           deathClip.open(audioForDeath);
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

        public void playSound (Clip clip) {
            clip.setMicrosecondPosition(0); //resets the sound to the beginning
            clip.start();} //plays the sound 

        

    public void drawWorld(Graphics g) {
        if (theTime.hour() >= 0 && theTime.hour() <= 4) {
            g.setColor(new Color(97, 80, 82));
        }
        if (theTime.hour() > 4 && theTime.hour() < 6) {
            playSound(chickenClip);
            for (int i = 0; i <= 6 - theTime.hour(); i++) {
                g.setColor(new Color(97, 80 + (i * 10), 87));
            }
        }
        if (theTime.hour() <= 12 && theTime.hour() >= 6) {
            for (int i = 0; i <= 12 - theTime.hour(); i++)
                g.setColor(new Color(97, 130 - (i * 5), 82));
        }
        if (theTime.hour() > 12 && theTime.hour() <= 18) {
            g.setColor(new Color(97, 130, 82));
        }
        if (theTime.hour() > 18 && theTime.hour() <= 24) {
            playSound(wolfClip);
            for (int i = 0; i <= 24 - theTime.hour(); i++) {
                g.setColor(new Color(97, 100 + (i * 5), 82));
            } //for the lines above, we changed the color of our grass darker at night to give the illusion of a dynamic, changing night-and-day cycle in our village
        } 
        g.fillRect(200, 0, width, height);

        for (int i = 0; i < 100; i++) {
            plants[i].draw(g); //draw the plants
        }
        structure.drawCrops(g);
        structure.drawStructures(g);


        for (int i = 0; i < 5; i++) {
            cows[i].draw(g);
            pigs[i].draw(g);
            chickens[i].draw(g); //draw all the animals
        }
        

        for (int i = 0; i < numPerson; i++) {
            people[i].draw(g);
            bloodstains[i].draw(g); //draw the people, and the bloodstains if murdered
        }

        for (int i = 0; i < 500; i++) {
            elements[i].draw(g); //falling elements
        }

        //we wrote this to check whether our grid was working right
        // for (int i = 0; i < 77; i++) {
        //     for (int j = 0; j < 104; j++) {
        //         if (grid[i][j] == 1) {
        //             g.fillRect(200 + j * 10, i * 10, 10, 10);
        //         }
        //     }
        // }
    }

    public void updateWorld(double time, long hour, int month) {
        for (int i = 0; i < 5; i++) {
            cows[i].update(this, time); 
            pigs[i].update(this, time);
            chickens[i].update(this, time);
        }

        if (hour == 0) {
            //elements don't fall every day, but on random days
            double random = Math.random();
            for (int i = 0; i < 500; i++) {
                if (random > 0.5) {
                    elements[i].velocity = elements[i].vel;
                }
                else {
                    elements[i].velocity = new Pair(0,0);
                    elements[i].position = new Pair (200 + Math.random() * 1040, -50);
                }
            }
        }
        for (int i = 0; i < numPerson; i++) {
            people[i].update(this, time, hour);
            bloodstains[i].update(this, time);
            people[i].workInteraction(this);
            //the thief's interaction with the world - if they pass by a flower, they burn it
            if (people[i].isThief) {
                for (int j = 0; j < 100; j++) {
                    if (Math.abs(plants[j].position.x - people[i].position.x) < 10
                            && Math.abs(plants[j].position.y - people[i].position.y) < 10 && plants[j].isTree == false) {
                        plants[j].flowerNum = 1; 
                    }
                }
            }
            //a murderer's interaction with the world - if they pass by a person, they kill them
            if (people[i].isMurderer) {
                for (int j = 0; j < numPerson; j++) {
                    if (i != j) {
                        if (Math.abs(people[j].position.x - people[i].position.x) < 10
                                && Math.abs(people[j].position.y - people[i].position.y) < 10) { //these if statements first check if people[i] is a murderer, and if someone gets sufficiently close to the murderer, the will be murdered.
                                //person disappears, and a blood stain appears there instead of them for a while
                                bloodstains[j].position = people[j].position;
                                Person tempPerson = people[numPerson - 1];
                                people[j] = null; 
                                people[j] = tempPerson;
                                playSound(deathClip);
                                numPerson--; 
                                //this is to update the summary of the events that happened
                                totalDeaths++;
                                totalMurders++; 
                            textArea.setText(textArea.getText()+ "\n" + people[j].name+ " has been murdered by " + people[i].name + "!"); //for the event log
                            playSound(deathClip);
                        }
                    }
                }
            }
            if (people[i].health <= 0) { //dying - can happen to people who are Infected, but didn't get Healed
                textArea.setText(textArea.getText()+ "\n" + people[i].name+ " has died! :(");
                Person tempPerson = people[numPerson - 1];
                people[i] = null; 
                people[i] = tempPerson;
                playSound(deathClip);
                numPerson--;
                totalDeaths++; 
            }
            //an infected person's interaction with the world --> can infect others aside from the herbalist
            if (people[i].isInfected) {
                for (int j = 0; j < numPerson; j++) {
                    if (i != j) {
                        if (Math.abs(people[j].position.x - people[i].position.x) < 10
                                && Math.abs(people[j].position.y - people[i].position.y) < 10 && people[j].isInfected==false && people[j].attributes.persontype != Person.HERBALIST) { 
                            //If someone gets sufficiently close to an infected person, and that someone is not a herbalist (who have immunity to the plague), they will become afflicted with the plague as well.
                            people[j].isInfected = true; 
                            totalInfected++; 
                            people[j].health = 80;
                            playSound(plagueClip);
                            textArea.setText(textArea.getText()+ "\n" + people[j].name+ " has just gotten the plague");
                            
                        }
                    }
                }
            }

        }

        for (int i = 0; i < 100; i++) {
            plants[i].update(this, time);
        }
        if (month >= 3 && month <= 5) {
            Thing.changeImage("spring");
        }
        if (month >= 6 && month <= 8) {
            Thing.changeImage("summer");
        }
        if (month >= 9 && month <= 11) {
            Thing.changeImage("autumn");
        }
        if (month >= 12 || month <= 2) {
            Thing.changeImage("winter");
        }
        for (int i = 0; i < 500; i++) {
            elements[i].update(this, time);
        }
    }

    // EVENTS
    public void Plague() { //event for prompting the plague onto someone random in the village
        playSound(plagueClip); //plays the coughing sounds
        int random = randomObject.nextInt(numPerson); 
        if (people[random].attributes.persontype == Person.HERBALIST) {
            textArea.setText(textArea.getText()+ "\n" + people[random].name + " has resisted the plague, as they are a HERBALIST!"); //Herbalists cannot get infected, appears in the event log
        }
        else {
        people[random].changeInfectedStatus(true); //becomes infected
        people[random].health = 80; //loses some initial health
        totalInfected++; 
        textArea.setText(textArea.getText()+ "\n" + people[random].name + " has just gotten the plague!"); //new person has the plague, saved on the event log textArea
        }
    }

    public void addCriminal() {
        int random = randomObject.nextInt(numPerson); 
        textArea.setText(textArea.getText()+ "\n" + people[random].name + " has just become a murderer!!!");
        playSound(murderClip);
        totalMurderers++; 
        people[random].changeMurdererStatus(true);
    }

    public void addForeigner() {
        int i = (int) (5 * Math.random());
       if (i == Person.FARMER) {
            people[numPerson] = new Farmer(i, this);
        } else if (i==Person.CARETAKER) {
            people[numPerson] = new Caretaker(i, this);
        } else if (i==Person.GUARD) {
            people[numPerson]= new Guard(i, this); 
        } else if (i==Person.HERBALIST) {
            people[numPerson]= new Herbalist(i, this);}
         else if (i==Person.CARPENTER) {
            people[numPerson]= new Carpenter(i, this);  
        } //adding a new person into our village, with their oocupation being random
        bloodstains[numPerson]= new Blood(); //new bloodstain needs to be created as well
        textArea.setText(textArea.getText()+ "\n" + people[numPerson].name + " has just entered the village!");
        numPerson++;
        totalVillagersAdded++; 
}

    public void vanish() {
        textArea.setText(textArea.getText()+ "\n" + people[numPerson-1].name + " has just been killed by you!");
        people[numPerson - 1] = null; //last Person who is not null in the people array gets removed
        numPerson--;
        totalDeaths++; 
    }

    public void rain() {
        raining = true;  //starts raining
        totalRains++; 
        textArea.setText(textArea.getText()+ "\n" + "You decided to start some rain!");
    }

}
