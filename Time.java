import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class Time {
    long start;
    boolean running;
    public static final int WINTER = 1;
    public static final int SPRING = 2;
    public static final int SUMMER = 3;
    public static final int AUTUMN = 4;
    public static int season = WINTER;

    public Time() {
        start = 0;
        running = false;
    }

    //starts counting time
    public void start() {
        if (!running) {
            start = System.currentTimeMillis(); //the time that has transpired since we ran the program
            running = true;//begins the timer running
        }
    }

    //stops counting time
    public void stop() {
        if (running) {
            running = false; 
        }
    }

    //resets the time
    public void reset() {
        start = 0;
        running = false;
    }

    //returns how much time has passed
    public long elapsedTime() {
        if (running) {
            return System.currentTimeMillis() - start; //in our main method, we need to account for the time that only transpired in Main
        }
        else {
            return 0;
        }
    }

    // converts milliseconds into the world's hours
    public long hour() {
        return (elapsedTime()/4000) % 24;
    }

    //return the month's number - this is used for the seasonal changes (snow, petals, etc.)
    public int monthNumber() {
        long days = ((elapsedTime()/96000) % 365) + 1;
        if (days <= 31) return 1;
        else if (days <= 59) return 2;
        else if (days <= 90) return 3;
        else if (days <= 120) return 4;
        else if (days <= 151) return 5;
        else if (days <= 181) return 6;
        else if (days <= 212) return 7;
        else if (days <= 243) return 8;
        else if (days <= 273) return 9;
        else if (days <= 304) return 10;
        else if (days <= 334) return 11;
        else return 12;
    }

    //represents the calendar -- this is shown on the top of the screen
    public String calendar() {
        long days = ((elapsedTime()/96000) % 365) + 1;
        String month = "";
        long day = 0;
        String suffix = "";
        if (days <= 31) {
            month = "January";   
            day = days;
        }
        else if (days <= 59) {
            month = "February";
            day = days-31;
        }
        else if (days <= 90) {
            month = "March";
            day = days-59;
        }
        else if (days <= 120) {
            month = "April";
            day = days-90;
        }
        else if (days <= 151) {
            month = "May";
            day = days-120;
        }
        else if (days <= 181) {
            month = "June";
            day = days-151;
        }
        else if (days <= 212) {
            month = "July";
            day = days-181;
        }
        else if (days <= 243) {
            month = "August";
            day = days-212;
        }
        else if (days <= 273) {
            month = "September";
            day = days-243;
        }
        else if (days <= 304) {
            month = "October";
            day = days-273;
        }
        else if (days <= 334) {
            month = "November";
            day = days-304;
        }
        else if (days <= 364) {
            month = "December";
            day = days-334;
        }
        if (day/10 != 1 && day%10 == 1) {
            suffix = "st";
        }
        else if (day/10 != 1 && day%10 == 2) {
            suffix = "nd";
        }
        else if (day/10 != 1 && day%10 == 3) {
            suffix = "rd";
        }
        else {
            suffix = "th";
        }//ensures a proper calendar occurs
        return month + " " + day + suffix; 
    }


    //draws the time on the top of the screen
    public void drawTime(Graphics g){
        g.setColor(Color.PINK);
        Font scoreFont = new Font("TimesRoman", Font.BOLD, 30);
        g.setFont(scoreFont);
        if (calendar().equals("December 21st")) {
            season = WINTER;//sets season based on current calendar date
        }
        else if (calendar().equals("March 21st")) {
            season = SPRING; 
        }
        else if (calendar().equals("June 21st")) {
            season = SUMMER;
        }
        else if (calendar().equals("September 21st")) {
            season = AUTUMN;
        }
        g.drawString(calendar() + ", " + hour() + ":00", 600, 30);
    }
}
