import javax.swing.JPanel;
import javax.swing.JTextField; 
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class InitialScreen extends JPanel{
    final int height = 770; //sizes of the main JFrame
    final int width = 1440;  
    String nameOfVillage; //changed as user inputs a name
    int numOfVillagers; //changed as user inputs a number
    JButton villageEnterButton; 
    JButton villagerNumberEnterButton;
    JButton beginGameButton;   
    JTextField textOfVillage; //Will allow the user to type a name for the village
    JTextField numberOfVillagers; //Will allow the user to type the number of villagers they want initially 
    JTextField inputAgain; //Will be triggered if the user inputs a number greater than 36 for the number of villagers; this, after the constructor, will become uneditable. 
    Clip BackgroundMusicClip; 
    Clip buttonClickingSound; 
    Color colorForBars= new Color(97, 130, 82); 
    public boolean startButtonPressed= false;
    public BufferedImage God, Gods, Judgement, Judgements, welcome1, welcome2, welcome3, welcome4, welcome5;


    public InitialScreen() {
        //Initializing Buttons
        villageEnterButton= new JButton("ENTER"); 
        villagerNumberEnterButton= new JButton("ENTER"); 
        beginGameButton= new JButton("BEGIN GAME"); 
        //Initialized JTextFields
        textOfVillage= new JTextField(10); 
        numberOfVillagers= new JTextField(2); 
        inputAgain= new JTextField("Number cannot exceed 36"); //Informing the user of the limit of possible villagers
        this.setLayout(null);
        this.setPreferredSize(new Dimension(width, height));
        //until line 90, initializing the sounds
        File musicFile= new File("resources/Music.wav"); 
        AudioInputStream audioInput= null;
        try {
            audioInput = AudioSystem.getAudioInputStream(musicFile);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        } 
        try {
            BackgroundMusicClip= AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } 
        try {
            BackgroundMusicClip.open(audioInput);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BackgroundMusicClip.start();
        File buttonPlayingFile= new File("resources/ButtonClick.wav"); 
        AudioInputStream audioInputButton= null;;
        try {
            audioInputButton = AudioSystem.getAudioInputStream(buttonPlayingFile);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } 
        try {
            buttonClickingSound= AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } 
        try {
            buttonClickingSound.open(audioInputButton);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Initializing the pictures used in the InitialScreen
        try {
            God = ImageIO.read(new File("resources/God.png"));
            Gods = ImageIO.read(new File ("resources/Gods.png"));
            welcome1 = ImageIO.read(new File("resources/Welcome1.png"));
            welcome2 = ImageIO.read(new File("resources/Welcome2.png"));
            welcome3 = ImageIO.read(new File("resources/Welcome3.png"));
            welcome4 = ImageIO.read(new File("resources/Welcome4.png"));
            welcome5 = ImageIO.read(new File("resources/Welcome5.png"));
            Judgement = ImageIO.read(new File("resources/Judgement.png"));
            Judgements = ImageIO.read(new File("resources/Judgements.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //From here to line 133, I am adjusting the color, bounds, and Font of the buttons. 
        inputAgain.setBounds(300, 710, 180, 30);
        inputAgain.setFont(new Font("Big Caslon", Font.BOLD, 15));
        inputAgain.setBackground(Color.RED);
        inputAgain.setOpaque(true);
        inputAgain.setEditable(false);
        textOfVillage.setBounds(600, 500, 400,  100);
        textOfVillage.setBackground(colorForBars);
        Font fontForText= new Font("Big Caslon", Font.BOLD, 40); 
        textOfVillage.setFont(fontForText);
        numberOfVillagers.setBounds(600, 630, 100, 80);
        numberOfVillagers.setBackground(colorForBars);
        numberOfVillagers.setFont(fontForText);
        villageEnterButton.setBounds(1050, 530, 100, 50); 
        villageEnterButton.setBackground(colorForBars);
        villageEnterButton.setOpaque(true);
        villagerNumberEnterButton.setBounds(800, 645, 100, 50);
        villagerNumberEnterButton.setBackground(colorForBars);
        villagerNumberEnterButton.setOpaque(true);
        beginGameButton.setBounds(950, 100, 400, 200);
        beginGameButton.setFont(fontForText);
        beginGameButton.setBackground(Color.ORANGE);
        beginGameButton.setOpaque(true);
        this.add(textOfVillage); 
        this.add(villageEnterButton); 
        this.add(villagerNumberEnterButton); 
        this.add(numberOfVillagers); 
        this.add(beginGameButton); 
        this.add(inputAgain); 
        //inputAgain is not visible at first, as it becomes visible the moment the user tries to input a number greater than 36 for numberOfVillagers
        inputAgain.setVisible(false);
        //beginGameButton will appear when the user has entered both a name and an initial number of villagers for their village
        beginGameButton.setVisible(false);
        //When clicked, this button will play a clicking button sound, and set the name of the village as whatever was inputed in textOfVillage
        villageEnterButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    playClickingButton();
                    textOfVillage.setEditable(false) ;
                    textOfVillage.setFocusable(false);
                    nameOfVillage= textOfVillage.getText(); 
                     }});
            //When clicked, this button will play a clicking button sound, and set the number of initial villagers to whatever was inputed in JTextField numberOfVillagers
            //Unless, the number inputed is greater than 36, in which case, the inputAgain un-editable JTextField will appear prompting the user to input a lower number. 
        villagerNumberEnterButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String PLACEHOLDER= numberOfVillagers.getText();
                    numOfVillagers= Integer.parseInt(PLACEHOLDER); 
                    if (numOfVillagers> 36) { 
                        inputAgain.setVisible(true);
                    }
                    else {playClickingButton();
                    numOfVillagers= Integer.parseInt(PLACEHOLDER); 
                    numberOfVillagers.setEditable(false);
                    numberOfVillagers.setFocusable(false);}
                     }
        }); 
    }

    public void playClickingButton () {
        buttonClickingSound.setFramePosition(0);//restarts the clip back to the beginning
        buttonClickingSound.start();
    }
        
    @Override public void paintComponent (Graphics g) {
        //setting background up
        g.setColor(new Color(168, 159, 108));
        g.fillRect(0, 0, width, height); 
        g.setColor(Color.BLACK);
        g.fillRect(50, 10, width-100, height-20); 
        //setting swing drawings to look like name labels
        g.setColor(colorForBars);
        g.fillRect(300, 525, 250, 50);
        g.fillRect(300, 650, 260, 50);
        g.setColor(Color.BLACK);
        g.fillRect(325, 530, 200, 40);
        g.fillRect(325, 655, 210, 40);
        Font fontForTitle= new Font("Big Caslon", Font.BOLD,  50); 
        g.setFont(fontForTitle);
        g.setColor(Color.white);
        g.setFont(new Font("Big Caslon", Font.BOLD, 18));
        g.drawString("Enter Village Name", 350, 555);
        g.drawString("Enter Number of Villagers", 330, 680);
        //drawing the initialized images into the screen
        g.drawImage(God, 20, 10, null);
        g.drawImage(Gods, 395, 245, null);
        g.drawImage(Judgement, 715, 215, null);
        g.drawImage(Judgements, 865, 245, null);
        g.drawImage(welcome1, 250, 80, null);
        g.drawImage(welcome2, 130, 120, null);
        g.drawImage(welcome3, 280, 330, null);
        g.drawImage(welcome4, 130, 300, null);
        g.drawImage(welcome5, 200, 170, null);
    }

    public void enterButtonPrompt() {
        //This first if statement checks if the user has inputed something valid for both textOfVillage and numberOfVillagers
        if (textOfVillage.isEditable()==false && numberOfVillagers.isEditable()==false) 
        beginGameButton.setVisible(true);//if the condition is checked, then the actual begin button becomes visible
        beginGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BackgroundMusicClip.close();//when clicked, the background music changes
                startButtonPressed= true; //this assignment will cause main to be able to move on from the while loop on main in which it is stuck.
            }
            
        });}

    }