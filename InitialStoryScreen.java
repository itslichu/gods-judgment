import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Dimension;
import java.awt.Font;

public class InitialStoryScreen extends JPanel {
    final int height = 770; //according to the size of our JFrame
    final int width = 1440; 
    JButton continueButton; //Clicked to progress through the cutscene 
    BufferedImage God; //Image of God, who will talk to the user  
    AudioInputStream audioInputBackground, audioInputSpeaking1, audioInputSpeaking2, audioInputSpeaking3;//audioInputStreams needed for audio 
    Clip backgroundMusicClip, speakingClip1, speakingClip2, speakingClip3; //CLips needed for audio
    Color frame; //Color of the background
    int numOfClicks;  //will increase every time the button is clicked
    Font font; //font used for our strings

    public InitialStoryScreen () { 
        numOfClicks=0; 
        font= new Font("Big Caslon", Font.BOLD, 30);  
        this.setLayout(null);//in order to add componets to our JPanel and determine their positions
        frame= new Color(168, 159, 108);
        this.setPreferredSize(new Dimension(width, height));//sets size of JPanel
        Font fontForText= new Font("Big Caslon", Font.BOLD, 30); 
        continueButton= new JButton("PROCEED");// 37-42 Initializes our botton, sets its position, size, and color
        continueButton.setBounds(1050, 500, 200, 100);
        continueButton.setFont(fontForText);
        continueButton.setBackground(frame);
        continueButton.setOpaque(true);
        this.add(continueButton); 
        //From here until line 107, we initialize all of our audio files
        File musicFile= new File("resources/StoryMusic.wav"); 
        try {
            audioInputBackground = AudioSystem.getAudioInputStream(musicFile);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        } 
        try {
            backgroundMusicClip= AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } 
        try {
            backgroundMusicClip.open(audioInputBackground);
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
        File GodVoice1= new File("resources/GodSpeaking1.wav"); 
        try {
            audioInputSpeaking1 = AudioSystem.getAudioInputStream(GodVoice1);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        } 
        try {
            speakingClip1= AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } 
        try {
            speakingClip1.open(audioInputSpeaking1);
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
        File GodVoice2= new File("resources/GodSpeaking2.wav"); 
        try {
            audioInputSpeaking2 = AudioSystem.getAudioInputStream(GodVoice2);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        } 
        try {
            speakingClip2= AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } 
        try {
            speakingClip2.open(audioInputSpeaking2);
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
        File GodVoice3= new File("resources/GodSpeaking3.wav"); 
        try {
            audioInputSpeaking3 = AudioSystem.getAudioInputStream(GodVoice3);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        } 
        try {
            speakingClip3= AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } 
        try {
            speakingClip3.open(audioInputSpeaking3);
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
        backgroundMusicClip.start();//Starts playing the background song. 
        try {
        God = ImageIO.read(new File("resources/placeholder.png"));} catch (IOException e) {
        e.printStackTrace();}
        continueButton.addActionListener(new ActionListener() {//when the continueButton is clicked, this is prompted
            @Override
            public void actionPerformed(ActionEvent e) {
                if (numOfClicks==0 || numOfClicks==1) { 
                //if we have yet to progress through one of God's dialogues, or we have only progressed through the first one, 
                //then we increase the numberOfClicks by one, and call repaint() (which calls paintComponent again)
                numOfClicks++; 
                repaint();}
                else if (numOfClicks==2) {numOfClicks++;} //If we have progressed through the third cutscence, then nymberOfClicks is increased one more time, 
                //which on our main method of Main, causes the program to leave the while loop it was stuck in and continue onto the next JPanel.
            }  
        });

    }
    

    @Override public void paintComponent (Graphics g) {
        //Here, we tried to separate paintComponenent into the three dialogue cutscenes of God
        super.paintComponent(g);
        if (numOfClicks==0) {//first dialogue cutscence, we draw the background and draw the first text that God speaks
        draw(g);
        drawFirstText(g, font);}
        else if (numOfClicks==1) {
        speakingClip1.close();//we close the first clip of God talking to continue onto our second clip of him talking
        font= new Font("Big Caslon", Font.BOLD, 24); 
        draw(g);//draw background
        drawSecondText(g, font);//draw second dialogue from God
        }
        else if (numOfClicks==2) {
        speakingClip2.close();//we close the second clip of God talking to continue onto our third one.
        draw(g);
        drawThirdText(g, font);//draw the third dialogue from God
        }
    }

    public void draw (Graphics g) {//draws the background
        g.setColor(frame);
        g.fillRect(0, 0, width, height); 
        g.setColor(Color.BLACK);
        g.fillRect(50, 10, width-100, height-20); 
        g.drawImage(God, 10, 10, null); 
        g.drawRect(800, 100, 450, 200);
    }

    public void drawFirstText (Graphics g, Font font) {
        g.setColor(Color.WHITE);
        g.fillRect(700, 100, 550, 350);
        speakingClip1.start();//causes God to begin saying his first line of dialogue
        g.setColor(Color.BLACK);
        g.setFont(font);
        //draws what God is saying onto the screen
        g.drawString("Welcome, my child, to my domain!", 710, 125);
        g.drawString("I appreciate your conviction in helping me ",710, 165); 
        g.drawString("guide this village. I take it you read the",710, 205);
        g.drawString("training module.", 710, 245);
        g.drawString("Otherwise, we would be in a lot of trouble",710, 285);
        g.drawString("Just to make sure, in case you were LAZY,", 710, 325);
        g.drawString("we will review your duties.", 710, 365);
        Font exclamation= new Font("Big Caslon", Font.BOLD, 50); 
        g.setFont(exclamation);
        g.drawString("PAY ATTENTION!", 710, 425); 
    }

    public void drawSecondText (Graphics g, Font font) {
        g.setFont(font);
        g.setColor(Color.WHITE);
        speakingClip2.start();//causes God to begin saying his second line of dialogue
        g.fillRect(700, 100, 550, 350);
        g.setColor(Color.BLACK);
        g.drawString("As you may know, I am in charge of affecting", 710, 125);
        g.drawString("the lives of everyone in this village.", 710, 155);
        g.drawString("While I often try to be benevolent, I am granting", 710, 185);
        g.drawString("you with a variety of ways to affect the lives of", 710, 215);
        g.drawString("the people herein. There are farmers, carpenters", 710, 245);
        g.drawString("guards, animal caretakers, and herbalists, who will", 710, 275);
        g.drawString("work, sleep, and live life as normal. You will have the",710, 305);
        g.drawString("choice to trigger, whose effects will be felt throughout.", 710, 335);
        g.drawString("Be as kind, or as evil, as you please.", 710, 365);
        g.drawString("I am leaving it all to your enlightened judgement!", 710, 425);
    }

    public void drawThirdText (Graphics g, Font font) {
        g.setFont(font);
        g.setColor(Color.WHITE);
        speakingClip3.start();
        g.fillRect(700, 100, 550, 210);
        g.setColor(Color.BLACK);
        g.drawString("Well, without further ado, I think you are ready", 710, 125);
        g.drawString("to begin this adventure of absolute power. I will keep", 710, 155);
        g.drawString("track of the events that happen throughout your", 710, 185);
        g.drawString("handling of this village." ,710, 215);
        g.drawString("I will grant you a grade at the end!", 710, 245);
        g.drawString("PS: Please don't kill all the villagers. Please...", 710, 295);
    }

}
