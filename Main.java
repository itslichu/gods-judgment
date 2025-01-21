import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JPanel {
    public static final int WIDTH = 1440;
    public static final int HEIGHT = 770;
    public static final int FPS = 60;
    String Name; //village Name 
    World world; //where villager/farm/village interactions occurr
    Time timer;//allows for a calendar, as well as helps determine the timing of events
    double villageHappiness; //average village happiness (sum all of the happiness/number of villagers)
    double villageHealth; //average village health (sum of all of the healths/number of villagers)
    long timeBeginningRain=0;//when the rain event is activated, this variable allows for a benchmark of the starting time of the rain so that the rain lasts a day
    Font fontForStatsButton; //font for when clicking on a villager and their stats appear
    JButton eventNumberOne, eventNumberTwo, eventNumberThree, eventNumberFour, eventNumberFive, doneButton; //buttons needed to activate events/end the game 
    boolean isDoneWithTheGame; //more explanation on this further down; but, will determine when we should enter our Final Screen (which is not a JPanel itself, as the Initial Screens were, due to complications arising with the Runner)
    GenericNodeStructure<String> stringsForFinalScreen; //Our generic data structure that creates a generic LinkedList; in this case, we are doing a linked list of Strings
    GenericNodeStructure<Integer> integersForFinalScreen;   //Our generic data structure that creates a generic LinkedList; in this case, we are doing a linked list of ints
    int totalVillagersAdded, totalDeaths, totalInfected, totalMurders, totalMurderers, totalRains; //keeps a counter of the total times these occurrances occur, in order to present the stats in the final screen 


    class Runner implements Runnable {
        public void run() {
            //This boolean isDoneWithTheGame determines whether the user wants to continue playing the game; until the user clicks the "QuitGame" button, this runs as if it were a while(true)
            while (isDoneWithTheGame==false) {
                if (world.numPerson==36) {//if maximum number of villagers is reached (36), the button that gives the user the ability to add a villager is removed.
                    remove(eventNumberFour);
                } else {add(eventNumberFour);} //restores the Add Person button when there are less than 36 people in the village
                long hour = timer.hour();
                int month = timer.monthNumber();//calendar
                villageHappiness=0;
                villageHealth=0; 
                for (int i=0; i<world.numPerson; i++) {
                villageHappiness+= world.people[i].happiness;
                villageHealth+= world.people[i].health;}//calculating total happiness and total health of all the villagers
                villageHappiness= villageHappiness/world.numPerson; 
                villageHealth= villageHealth/world.numPerson; //dividing by numPerson to obtain the average health
                totalVillagersAdded= world.totalVillagersAdded; //since world does the heavy duty of implementing events, deaths, infections and murders, we are simply importing this value from world in order to use in our final screen.
                totalDeaths= world.totalDeaths; 
                totalInfected= world.totalInfected;  
                totalMurderers= world.totalMurderers;
                totalMurders= world.totalMurders; 
                totalRains= world.totalRains; 
                addButtons(); //adds the invisible buttons present within each villager, that causes a click on a villager to display the villager's statistics
                world.updateWorld(1.0 / (double) FPS, hour, month); 
                repaint();
                try{
                    Thread.sleep(1000/FPS);
                }
                catch(InterruptedException e) {}
            }
        }
    }

    public void addNotify() {
        super.addNotify();
        requestFocus();
    }
 
    public Main(int initialNumberOfVillagers, String villageName) {
    Name= villageName;
    totalVillagersAdded=initialNumberOfVillagers; 
    this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    this.setLayout(null);//allows for components to be added in specific ways
    timer = new Time(); //initiates timer
    timer.start();//begins timer
    stringsForFinalScreen= new GenericNodeStructure<String>();
    integersForFinalScreen= new GenericNodeStructure<Integer>();//initialized our two generic linked lists.  
    world = new World(WIDTH-400, HEIGHT, initialNumberOfVillagers, Name, timer); //world= the actual village, and everything that occurs there
    eventNumberOne= new JButton("Add Murderer"); 
    eventNumberTwo= new JButton("Add Plague"); 
    eventNumberThree= new JButton("Kill Person"); 
    eventNumberFour= new JButton("Add Person"); 
    eventNumberFive= new JButton("Rain");  
    doneButton= new JButton("QUIT GAME"); //Initialized JButtons corresponding to events
    //from here until line 118, we set the size, position and color of our JButtons
    eventNumberOne.setBounds(50, 100, 100, 50);
    eventNumberTwo.setBounds(50, 200, 100, 50);
    eventNumberThree.setBounds(50, 300, 100, 50);
    eventNumberFour.setBounds(50, 400, 100, 50);
    eventNumberFive.setBounds(50, 500, 100, 50); 
    doneButton.setBounds(1270, 685, 100, 50);
    doneButton.setBackground(Color.RED);
    doneButton.setOpaque(true);
    eventNumberOne.setBackground(Color.BLACK);
    eventNumberTwo.setBackground(Color.BLACK);
    eventNumberThree.setBackground(Color.BLACK);
    eventNumberFour.setBackground(Color.BLACK);
    eventNumberFive.setBackground(Color.BLACK);
    eventNumberOne.setOpaque(true);
    eventNumberTwo.setOpaque(true);
    eventNumberThree.setOpaque(true);
    eventNumberFour.setOpaque(true);
    eventNumberFive.setOpaque(true);
    this.add(eventNumberOne); 
    this.add(eventNumberTwo); 
    this.add(eventNumberThree); 
    this.add(eventNumberFour); 
    this.add(eventNumberFive);
    this.add(world.labelOfEvents); 
    this.add(doneButton); 
    isDoneWithTheGame= false; //we are not done with the game when we initialize an instance of Main, so this is initially set false.


    eventNumberOne.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            world.addCriminal(); //adds a murderer to the world when clicked
        }});
    eventNumberTwo.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            world.Plague();//adds a plague-afflicted person to the world when clicked
        }});
    eventNumberThree.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            world.vanish(); //removes a villager when clicked
    }});
    eventNumberFour.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
         world.addForeigner(); //adds a villager when clicked
        }});
    eventNumberFive.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //causes rain to appear if it is not raining yet, or rain to stop if it is raining already
            if (world.raining==true) {world.raining=false;
            world.textArea.setText(world.textArea.getText() + "\n" + "You decided to stop the rain.");}
            else {world.rain();
            timeBeginningRain= timer.elapsedTime()/625; } 
        }});
    doneButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            gettingAlLStatsThroughTheGame();//records the total/current village/game stats at the moment the user clicks to quit the game; 
            //these stats are saved within generic linked lists of either int or String types. 
            isDoneWithTheGame= true; 
            repaint();
                 //when you hit the Quit Game button, we are done with the game, converting the boolean into true. 
            //we repaint() in order to make our village dissapear within the JPanel and make the Final screen appear.
        }
        
    });
    Thread mainThread = new Thread(new Runner());
    mainThread.start();
    }

    public void addButtons () {//adds the invisible buttons of each villager
        for (int i=0; i<world.numPerson; i++) {
            this.add(world.people[i].statsButton); 
        }
    }

    public void paintComponent(Graphics g) {
        //if we are not done with the game (i.e. button has not been clicked yet), we draw our village, buttons, game as a whole
        if (isDoneWithTheGame==false) { 
            //drawing the UI for button clicking, event prompting, and information on the village (until line 233)
        Color colorForSidebars= new Color(227, 199, 168); 
        g.setColor(colorForSidebars);
        g.fillRect(0, 0, 200, HEIGHT);
        g.fillRect(1240, 0, 200, HEIGHT);
        g.setColor(Color.WHITE);
        g.fillRect(1270, 5, 150, 35); 
        g.fillRect(1270, 65, 150, 35);
        g.fillRect(1270, 185, 150, 35);
        g.fillRect(1270, 230, 150, 35);
        g.setColor(new Color(97, 130, 82));
        g.fillRect(10, 30, 185, 40);
        g.setColor(Color.BLACK);
        g.drawRect(1270, 5, 150, 35); 
        g.drawRect(1270, 65, 150, 35);
        g.drawRect(1270, 185, 150, 35);
        g.drawRect(1270, 230, 150, 35);
        Font FontForVillageName= new Font("Big Caslon", Font.BOLD, 14); 
        g.setFont(FontForVillageName);
        g.drawString("Village name:" , 1290, 20);
        g.drawString(Name, 1290, 35);
        g.drawString("Average Health: ", 1290, 80);
        g.drawString(Integer.toString( (int) villageHealth), 1290, 95);
        g.drawString("Average Happiness: ", 1290, 200);
        g.drawString(Integer.toString((int) villageHappiness), 1290, 215);
        Font FontForEventTitle = new Font("Big Caslon", Font.BOLD, 25); 
        g.setFont(FontForEventTitle); 
        g.drawString("Events available: ", 20, 60);
        Font fontForAnnouncement= new Font("Big Caslon", Font.BOLD, 20); 
        g.setFont(fontForAnnouncement);
        if (villageHealth>=0 && villageHealth<=30) {
            g.setColor(Color.RED);
            g.fillRect(1270, 120, 150, 50);
            g.setColor(Color.BLACK);
            g.drawRect(1270, 120, 150, 50);
            g.drawString("Village Health is", 1278, 140);
            g.drawString("Extremely Low!", 1282, 165);}
        if (villageHealth>30 && villageHealth<=50) {
            g.setColor(Color.PINK);
            g.fillRect(1270, 120, 150, 50);
            g.setColor(Color.BLACK);
            g.drawRect(1270, 120, 150, 50);
            g.drawString("Village Health is", 1278, 140);
            g.drawString("Low", 1325, 165);}
        if (villageHealth>50 && villageHealth<=80) {
            g.setColor(Color.GREEN);
            g.fillRect(1270, 120, 150, 50);
            g.setColor(Color.BLACK);
            g.drawRect(1270, 120, 150, 50);
            g.drawString("Village Health is", 1278, 140);
            g.drawString("Good", 1320, 165);}
        if (villageHealth>80) {
            g.setColor(Color.YELLOW);
            g.fillRect(1270, 120, 150, 50);
            g.setColor(Color.BLACK);
            g.drawRect(1270, 120, 150, 50);
            g.drawString("Village Health is", 1278, 140);
            g.drawString("Extremely Good!", 1278, 165);}
        g.drawString("Event Log", 1300, 255);
        world.drawWorld(g);//draws the current iteration of world
        timer.drawTime(g); //draws the current time
        if (world.raining) {
            long hoursPassed= timer.elapsedTime()/625;
            g.setColor(Color.WHITE); 
            if (hoursPassed<=timeBeginningRain+3) {//initially, for the first three hours, its a soft rain
                for (int i=0; i<30; i++) {
                g.fillOval( (int) (200+ (Math.random()*1040)), (int) (Math.random()*HEIGHT),2, 5);}}
            if (hoursPassed<=timeBeginningRain+ 18 && hoursPassed> timeBeginningRain+ 3){//then, until 6 hours before the stop of the rain, the rain gets harder
            for (int i=0; i<100; i++) {
            g.fillOval( (int) (200+ (Math.random()*1040)), (int) (Math.random()*HEIGHT),2, 5);}}
            if (hoursPassed <=timeBeginningRain+ 24 && hoursPassed>timeBeginningRain+ 18){//lastly, rain quiets down again
                for (int i=0; i<20; i++) {
                g.fillOval( (int) (200+ (Math.random()*1040)), (int) (Math.random()*HEIGHT),2, 5);}}
            if (hoursPassed>=timeBeginningRain+ 24) { world.raining=false;}} //after a day, rain is stopped
            for (int i=0; i<world.numPerson; i++) {//checks through all the villagers in the world to see if their statsButton is pressed
                if (world.people[i].statsButtonPressed) {//if so, draw the their statistics
                    g.setColor(Color.WHITE);
                    g.fillRect((int) world.people[i].position.x+10, (int) world.people[i].position.y-50, 100, 50);
                    g.setColor(Color.BLACK);
                    g.setFont(fontForStatsButton);
                    g.drawString("Name: " + world.people[i].name, (int) world.people[i].position.x+12, (int) world.people[i].position.y-40);//draws their name onto the statsDisplay
                    String occupation= ""; 
                    if (world.people[i].attributes.persontype== Person.FARMER) {//determines the occuption of that specific villager for their stats display
                        occupation= "Farmer"; 
                    } else if (world.people[i].attributes.persontype== Person.CARETAKER) {
                        occupation= "Caretaker"; 
                    } else if (world.people[i].attributes.persontype== Person.CARPENTER) {
                        occupation= "Carpenter"; 
                    } else if (world.people[i].attributes.persontype== Person.GUARD) {
                        occupation= "Guard"; 
                    } else if (world.people[i].attributes.persontype== Person.HERBALIST) {
                        occupation= "Herbalist"; 
                    }
                    g.drawString("Job: " + occupation, (int) world.people[i].position.x+12, (int) world.people[i].position.y-30); //adds their job to the stats display
                    g.drawString("Health: " + world.people[i].health, (int) world.people[i].position.x+12, (int) world.people[i].position.y-20); //adds their health to the display
                    g.drawString("Happiness: " + world.people[i].happiness, (int) world.people[i].position.x+12, (int) world.people[i].position.y-10); //adds their happiness to the display

                    
                }}
            if (world.numPerson==36) {
                //if there are 36 people, instead of the button, a swing drawing appears indicating that they have reached 36 villagers 
                g.setColor(Color.RED);
                g.fillRect(50, 400, 100, 50);
                g.setColor(Color.BLACK);
                g.drawRect(50, 400, 100, 50);
                g.setFont(new Font("Big Calson", Font.BOLD, 12));
                g.drawString("Maximum", 55, 410);
                g.drawString("number of", 55, 425);
                g.drawString("villagers is 36", 55, 440);
            }}
            //this is called after the user clicks to end the game; none of the village/UI is drawn, instead, a final screen appears
            else if (isDoneWithTheGame==true) {
                world.backgroundClip.close();
                world.chickenClip.close();
                world.wolfClip.close();//closing the audios
                g.setColor(new Color(168, 159, 108));
                g.fillRect(0, 0, WIDTH, HEIGHT); 
                g.setColor(Color.BLACK);
                g.fillRect(50, 10, WIDTH-100, HEIGHT-20); //setting the background
                this.remove(doneButton);
                this.remove(eventNumberOne);
                this.remove(eventNumberTwo); 
                this.remove(eventNumberThree); 
                this.remove(eventNumberFour); 
                this.remove(eventNumberFive); //removing the buttons on the JPanel
                int [] arrayOfInts= new int[7]; //initialized this array of ints to easily access the elements wrapped within the linked list of ints. 
                String [] arrayOfStrings= new String [3];  //initialized this array of Strings to easily access the elements wrapped within the linked list of ints. 
                GenericNode<String> beginningNode= stringsForFinalScreen.endNode; 
                for (int i=0; i<3; i++) {
                    arrayOfStrings[i]= beginningNode.contents; 
                    beginningNode= beginningNode.previousNode; }//transfers the elements of the String linkedList into the String array
                GenericNode<Integer> beginningIntegerNode= integersForFinalScreen.endNode; 
                for (int i=0; i<7; i++) {
                        arrayOfInts[i]= beginningIntegerNode.contents; 
                        beginningIntegerNode= beginningIntegerNode.previousNode;} //transfers the elements of the Integer linkedList into the integer array
                world.labelOfEvents.setBounds(200, 100, 500, 600);
                String allTheEvents= world.textArea.getText(); 
                Font biggerFontForLabel= new Font("Big Caslon", Font.BOLD, 30); 
                world.textArea.setFont(biggerFontForLabel);
                world.textArea.setText(allTheEvents); //makes the scrollable text field present within world larger, and with a larger font, as we want to use that same information for our final screen
                g.setColor(Color.WHITE);//from here until line 383, the program draws the final screen, and displays the screen with the statistics found within the arrays. 
                g.fillRect(200, 50, 200, 30);
                g.fillRect(800, 100, 200, 30);
                g.fillRect(800, 150, 250, 30);
                g.fillRect(800, 200, 200, 30);
                g.fillRect(800, 250, 200, 30);
                g.fillRect(800, 300, 200, 30);
                g.fillRect(800, 350, 200, 30);
                g.fillRect(800, 400, 200, 30);
                g.fillRect(800, 450, 200, 30);
                g.fillRect(800, 500, 200, 30);
                g.fillRect(800, 550, 200, 30);
                g.fillRect(1100, 100, 200, 30);
                g.fillRect(1100, 150, 300, 30);
                g.fillRect(1100, 200, 300, 30);
                g.fillRect(1100, 250, 50, 30);
                g.fillRect(1100, 300, 50, 30);
                g.fillRect(1100, 350, 50, 30);
                g.fillRect(1100, 400, 50, 30);
                g.fillRect(1100, 450, 50, 30);
                g.fillRect(1100, 500, 50, 30);
                g.fillRect(1100, 550, 50, 30);
                g.setColor(Color.YELLOW);
                g.drawRect(200, 50, 200, 30);
                g.drawRect(800, 100, 200, 30);
                g.drawRect(800, 150, 250, 30);
                g.drawRect(800, 200, 200, 30);
                g.drawRect(800, 250, 200, 30);
                g.drawRect(800, 300, 200, 30);
                g.drawRect(800, 350, 200, 30);
                g.drawRect(800, 400, 200, 30);
                g.drawRect(800, 450, 200, 30);
                g.drawRect(800, 500, 200, 30);
                g.drawRect(800, 550, 200, 30);
                g.drawRect(1100, 100, 200, 30);
                g.drawRect(1100, 150, 300, 30);
                g.drawRect(1100, 200, 300, 30);
                g.drawRect(1100, 250, 50, 30);
                g.drawRect(1100, 300, 50, 30);
                g.drawRect(1100, 350, 50, 30);
                g.drawRect(1100, 400, 50, 30);
                g.drawRect(1100, 450, 50, 30);
                g.drawRect(1100, 500, 50, 30);
                g.drawRect(1100, 550, 50, 30);
                g.setColor(Color.BLACK);
                g.setFont(new Font("Big Caslon", Font.BOLD, 20));
                g.drawString("Your List Of Events", 207, 72);
                g.drawString(" Village Name", 807, 122);
                g.drawString(" Final Happiness Grade", 807, 172);
                g.drawString(" Final Health Grade", 807, 222);
                g.drawString("Total Villagers Added", 807, 272);
                g.drawString("Total Rains", 807, 322);
                g.drawString("Total Murders", 807, 372);
                g.drawString("Total Murderers", 807, 422);
                g.drawString("Total Infected", 807, 472);
                g.drawString("Total Deaths", 807, 522);
                g.drawString("Total Hours Passed", 807, 572);
                g.drawString(arrayOfStrings[2], 1107, 122);
                g.drawString(arrayOfStrings[1], 1107, 172);
                g.drawString(arrayOfStrings[0], 1107, 222);
                g.drawString(Integer.toString(arrayOfInts[0]), 1107, 272);
                g.drawString(Integer.toString(arrayOfInts[1]), 1107, 322);
                g.drawString(Integer.toString(arrayOfInts[2]), 1107, 372);
                g.drawString(Integer.toString(arrayOfInts[3]),  1107, 422);
                g.drawString(Integer.toString(arrayOfInts[4]), 1107, 472);
                g.drawString(Integer.toString(arrayOfInts[5]), 1107, 522);
                g.drawString(Integer.toString(arrayOfInts[6]), 1107, 572);
            }
        } 
        //this method is called when the user wants to quit the game, recording both total, and specific ending states of the variables in world into 
        //the generic linkedList
        //it also gives the "outcome" of the village as graded by God
        public void gettingAlLStatsThroughTheGame () {

            int totalHoursPassed= (int) timer.elapsedTime()/625;
            integersForFinalScreen.append(totalHoursPassed);//appending ints to my integer linked list
            integersForFinalScreen.append(totalDeaths);
            integersForFinalScreen.append(totalInfected);
            integersForFinalScreen.append(totalMurderers);
            integersForFinalScreen.append(totalMurders);
            integersForFinalScreen.append(totalRains);
            integersForFinalScreen.append(totalVillagersAdded);
            stringsForFinalScreen.append(Name); //appending strings
            String villageHappinessInString= ""; 
            if (villageHappiness<=10) {villageHappinessInString= "Your village ended up miserable."; }
            if (villageHappiness>10 && villageHappiness<30) {villageHappinessInString= "Your village is moderately happy.";}
            if (villageHappiness>=30) {villageHappinessInString= "Your village is pretty happy!";}
            stringsForFinalScreen.append(villageHappinessInString);
            String villageHealthInString= ""; 
            if (villageHealth<=30) {villageHealthInString= "Your village ended up unhealthy."; }
            if (villageHealth>30 && villageHealth<70) {villageHealthInString= "Your village ended moderately healthy.";}
            if (villageHealth>=70) {villageHealthInString= "Your village ended up super healthy!";}
            stringsForFinalScreen.append(villageHealthInString);
        }
     
    //main program    
    public static void main(String[] args){
        //Through this whole main, we purposely make the program enter while loops it gets stuck at, until a certain condition within 
        //the classes that extend JPanel is met
    JFrame frame = new JFrame("God's Judgement");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    InitialScreen mainScreenInstance = new InitialScreen(); 
    frame.setContentPane(mainScreenInstance);
    frame.pack();
    frame.setVisible(true);
    boolean InitialScreenIsDone= false; 
    while (InitialScreenIsDone==false) {
        //stuck in a loop until the start button from the Initial screen is pressed; once mainScreenInstance.startButtonPressed is true, the loop ends.
        //sleep for less iterations+ debugging
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        InitialScreenIsDone= mainScreenInstance.startButtonPressed; 
        mainScreenInstance.enterButtonPrompt();}
    InitialStoryScreen instance= new InitialStoryScreen(); 
    frame.setContentPane(instance);
    frame.pack();
    frame.setVisible(true);
    int numberOfClicks=0; 
    boolean InitialStoryScreenIsDone= false; 
    while (InitialStoryScreenIsDone==false) {
        try {
            //debugging+ less iterations
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        numberOfClicks= instance.numOfClicks;//the moment the button in InitialStoryScreen is clicked a third time, the loop ends
        if (numberOfClicks==3) {
            instance.speakingClip3.close();
            instance.backgroundMusicClip.close();
            InitialStoryScreenIsDone= true; 
        } }
    Main mainInstance = new Main(mainScreenInstance.numOfVillagers, mainScreenInstance.nameOfVillage);
    frame.add(mainInstance); 
    frame.setContentPane(mainInstance);
    frame.pack();
    frame.setVisible(true);
    }
 

}
