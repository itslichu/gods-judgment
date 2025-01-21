import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Appearance {

    public int persontype;
    public BufferedImage plant1, plant2, water1, water2, reap1, reap2, reap3, gather1, gather2, heal, brew, brewglasses,
            build1,
            build2, chop1axe, chop1body, chop2, feed, gift, milk1, milk2, sweep1, sweep2, patrolbody, patrolarm,
            FarmerEat, CaretakerEat, HerbalistEat,
            CarpenterEat, GuardEat, EatBase,
            ClosedEyes, Eating, Sleep, Mouth, HairM1Brown, HairM1Blond, HairM1Black, HairM2Black, HairM2Brown,
            HairM2Blond, HairM3Black, HairM3Brown, HairM3Blond, StopBase, WLeftBase, WRightBase, FarmerStop,
            FarmerWLeft, FarmerWRight, CaretakerStop, CaretakerWLeft, CaretakerWRight, HerbalistStop, HerbalistWLeft,
            HerbalistWRight, CarpenterStop, CarpenterWLeft, CarpenterWRight, GuardStop, GuardWLeft, GuardWRight,
            HairF1Brown, HairF1Blond,
            HairF1Black, HairF2Brown, HairF2Blond, HairF2Black, HairF3Brown, HairF3Blond, HairF3Black, EyesBlack,
            EyesBrown, EyesBlue, EyesGreen; // list of all appearance buffered image
    public BufferedImage[] stand;
    public BufferedImage[] walkleft;
    public BufferedImage[] walkright;
    public BufferedImage[] eat1;
    public BufferedImage[] eat2;
    public BufferedImage[] sleep;
    public BufferedImage[][] Work;
    public String name; // these are used for the instances of each Person
    public static String[] malenameslist = new String[] { "Arthur", "Hawk", "Emil", "Randorf", "Svend", "Theo", "Wolf",
            "Richard", "Njal", "Jon", "Victor", "Lord", "Leif", "Sal", "Steve", "James", "Benedict", "Bjorn", "Conrad",
            "Gregory", "Hamlet", "William", "Pascal" };
    public static String[] femalenameslist = new String[] { "Sybil", "Emma", "Jacqueline", "Beatrice", "Odette",
            "Odile", "Adelina", "Christina", "Sedille", "Holly", "Orella", "Sapphire", "Genevieve", "Nixie", "Viola",
            "Posy", "Lorelle", "Florence", "Yvonne", "Agatha", "Grace", "Danae", "Bronwyn" };
    public static ArrayList<String> malenames = new ArrayList<>(Arrays.asList(malenameslist));
    public static ArrayList<String> femalenames = new ArrayList<>(Arrays.asList(femalenameslist)); // list of male and female names to be paired with each instance of person
    public int gender;
    public Pair bed;
    public static Pair[] bedarray = new Pair[] { new Pair(235, 700), new Pair(265, 700), new Pair(295, 700),
        new Pair(325, 700), new Pair(235, 660), new Pair(325, 660), new Pair(405, 700), new Pair(435, 700),
        new Pair(465, 700), new Pair(495, 700), new Pair(405, 660), new Pair(495, 660), new Pair(575, 700),
        new Pair(605, 700), new Pair(635, 700), new Pair(665, 700), new Pair(575, 660), new Pair(665, 660),
        new Pair(745, 700), new Pair(775, 700), new Pair(805, 700), new Pair(835, 700), new Pair(745, 660),
        new Pair(835, 660), new Pair(915, 700), new Pair(945, 700), new Pair(975, 700), new Pair(1005, 700),
        new Pair(915, 660), new Pair(1005, 660), new Pair(1085, 700), new Pair(1115, 700), new Pair(1145, 700),
        new Pair(1175, 700), new Pair(1085, 660), new Pair(1175, 660) };
    public static ArrayList<Pair> beds = new ArrayList<>(Arrays.asList(bedarray)); // list of possible bed locations, pixel-wise

    public void getImages() {
        try {
            FarmerEat = ImageIO.read(new File("resources/FarmerEat.png"));
            CaretakerEat = ImageIO.read(new File("resources/CaretakerEat.png"));
            HerbalistEat = ImageIO.read(new File("resources/HerbalistEat.png"));
            CarpenterEat = ImageIO.read(new File("resources/CarpenterEat.png"));
            GuardEat = ImageIO.read(new File("resources/GuardEat.png"));
            EatBase = ImageIO.read(new File("resources/EatBase.png"));
            ClosedEyes = ImageIO.read(new File("resources/closedeyes.png"));
            Eating = ImageIO.read(new File("resources/Eat.png"));
            Sleep = ImageIO.read(new File("resources/Sleep.png"));
            Mouth = ImageIO.read(new File("resources/Mouth.png"));
            EyesBlack = ImageIO.read(new File("resources/EyesBlack.png"));
            EyesBrown = ImageIO.read(new File("resources/EyesBrown.png"));
            EyesGreen = ImageIO.read(new File("resources/EyesGreen.png"));
            EyesBlue = ImageIO.read(new File("resources/EyesBlue.png"));
            StopBase = ImageIO.read(new File("resources/StopBase.png"));
            WLeftBase = ImageIO.read(new File("resources/WLeftBase.png"));
            WRightBase = ImageIO.read(new File("resources/WRightBase.png"));
            FarmerStop = ImageIO.read(new File("resources/FarmerStop.png"));
            FarmerWLeft = ImageIO.read(new File("resources/FarmerWLeft.png"));
            FarmerWRight = ImageIO.read(new File("resources/FarmerWRight.png"));
            CaretakerStop = ImageIO.read(new File("resources/CaretakerStop.png"));
            CaretakerWLeft = ImageIO.read(new File("resources/CaretakerWLeft.png"));
            CaretakerWRight = ImageIO.read(new File("resources/CaretakerWRight.png"));
            HerbalistStop = ImageIO.read(new File("resources/HerbalistStop.png"));
            HerbalistWLeft = ImageIO.read(new File("resources/HerbalistWLeft.png"));
            HerbalistWRight = ImageIO.read(new File("resources/HerbalistWRight.png"));
            CarpenterStop = ImageIO.read(new File("resources/CarpenterStop.png"));
            CarpenterWRight = ImageIO.read(new File("resources/CarpenterWRight.png"));
            CarpenterWLeft = ImageIO.read(new File("resources/CarpenterWLeft.png"));
            GuardStop = ImageIO.read(new File("resources/GuardStop.png"));
            GuardWLeft = ImageIO.read(new File("resources/GuardWLeft.png"));
            GuardWRight = ImageIO.read(new File("resources/GuardWRight.png"));
            HairM1Brown = ImageIO.read(new File("resources/HairM1Brown.png"));
            HairM1Blond = ImageIO.read(new File("resources/HairM1Blond.png"));
            HairM1Black = ImageIO.read(new File("resources/HairM1Black.png"));
            HairM2Brown = ImageIO.read(new File("resources/HairM2Brown.png"));
            HairM2Blond = ImageIO.read(new File("resources/HairM2Blond.png"));
            HairM2Black = ImageIO.read(new File("resources/HairM2Black.png"));
            HairM3Brown = ImageIO.read(new File("resources/HairM3Brown.png"));
            HairM3Blond = ImageIO.read(new File("resources/HairM3Blond.png"));
            HairM3Black = ImageIO.read(new File("resources/HairM3Black.png"));
            HairF1Brown = ImageIO.read(new File("resources/HairF1Brown.png"));
            HairF1Blond = ImageIO.read(new File("resources/HairF1Blond.png"));
            HairF1Black = ImageIO.read(new File("resources/HairF1Black.png"));
            HairF2Brown = ImageIO.read(new File("resources/HairF2Brown.png"));
            HairF2Blond = ImageIO.read(new File("resources/HairF2Blond.png"));
            HairF2Black = ImageIO.read(new File("resources/HairF2Black.png"));
            HairF3Brown = ImageIO.read(new File("resources/HairF3Brown.png"));
            HairF3Blond = ImageIO.read(new File("resources/HairF3Blond.png"));
            HairF3Black = ImageIO.read(new File("resources/HairF3Black.png"));
            plant1 = ImageIO.read(new File("resources/plant1.png"));
            plant2 = ImageIO.read(new File("resources/plant2.png"));
            water1 = ImageIO.read(new File("resources/water1.png"));
            water2 = ImageIO.read(new File("resources/water2.png"));
            reap1 = ImageIO.read(new File("resources/reap1.png"));
            reap2 = ImageIO.read(new File("resources/reap2.png"));
            reap3 = ImageIO.read(new File("resources/reap3.png"));
            gather1 = ImageIO.read(new File("resources/gather1.png"));
            gather2 = ImageIO.read(new File("resources/gather2.png"));
            heal = ImageIO.read(new File("resources/heal.png"));
            brew = ImageIO.read(new File("resources/brew.png"));
            brewglasses = ImageIO.read(new File("resources/brewglasses.png"));
            build1 = ImageIO.read(new File("resources/build1.png"));
            build2 = ImageIO.read(new File("resources/build2.png"));
            chop1axe = ImageIO.read(new File("resources/chop1axe.png"));
            chop1body = ImageIO.read(new File("resources/chop1body.png"));
            chop2 = ImageIO.read(new File("resources/chop2.png"));
            feed = ImageIO.read(new File("resources/feed.png"));
            gift = ImageIO.read(new File("resources/gift.png"));
            milk1 = ImageIO.read(new File("resources/milk1.png"));
            milk2 = ImageIO.read(new File("resources/milk2.png"));
            sweep1 = ImageIO.read(new File("resources/sweep1.png"));
            sweep2 = ImageIO.read(new File("resources/sweep2.png"));
            patrolbody = ImageIO.read(new File("resources/patrolbody.png"));
            patrolarm = ImageIO.read(new File("resources/patrolarm.png")); // reading of files of all the previous BufferedImages

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void decideAppearance() {
        BufferedImage[] Eyes = new BufferedImage[] { EyesBlack, EyesBrown, EyesBlue, EyesGreen };
        BufferedImage[] HairsM = new BufferedImage[] { HairM1Brown, HairM1Blond, HairM1Black, HairM2Brown, HairM2Blond,
                HairM2Black, HairM3Brown, HairM3Blond, HairM3Black };
        BufferedImage[] HairsF = new BufferedImage[] { HairF1Brown, HairF1Blond, HairF1Black, HairF2Brown, HairF2Blond,
                HairF2Black, HairF3Brown, HairF3Blond, HairF3Black };
        BufferedImage Eye;
        BufferedImage Hair;
        BufferedImage Stop;
        BufferedImage Eat;
        BufferedImage WLeft;
        BufferedImage WRight; // list of possible bufferedimage values for the person and the bufferedimagees that they get assigned to

        int i = (int) (4 * Math.random());
        Eye = Eyes[i];

        i = (int) (9 * Math.random());
        if (gender == Person.MALE) {
            Hair = HairsM[i];
        } else {
            Hair = HairsF[i];
        }

        if (persontype == Person.FARMER) {
            Stop = FarmerStop;
            Eat = FarmerEat;
            WLeft = FarmerWLeft;
            WRight = FarmerWRight;
            Work = new BufferedImage[][] { { null, plant1, Eye, Mouth, Hair }, { null, plant2, Eye, Mouth, Hair },
                    { null, water1, Eye, Mouth, Hair }, { null, water2, Eye, Mouth, Hair },
                    { null, reap1, Eye, Mouth, Hair }, { null, reap2, Eye, Mouth, Hair }, // list of all layers of work sprites, as multiple exist
                    { null, reap3, Eye, Mouth, Hair } };
        } else if (persontype == Person.CARETAKER) {
            Stop = CaretakerStop;
            Eat = CaretakerEat;
            WLeft = CaretakerWLeft;
            WRight = CaretakerWRight;
            Work = new BufferedImage[][] { { null, feed, Eye, Mouth, Hair }, { null, milk1, Eye, Mouth, Hair },
                    { null, milk2, Eye, Mouth, Hair }, { null, sweep1, Eye, Mouth, Hair },
                    { null, sweep2, Eye, Mouth, Hair } };
        } else if (persontype == Person.HERBALIST) {
            Stop = HerbalistStop;
            Eat = HerbalistEat;
            WLeft = HerbalistWLeft;
            WRight = HerbalistWRight;
            Work = new BufferedImage[][] { { null, gather1, Eye, Mouth, Hair }, { null, gather2, Eye, Mouth, Hair },
                    { null, heal, Eye, Mouth, Hair }, { brew, Eye, Mouth, Hair, brewglasses } };
        } else if (persontype == Person.CARPENTER) {
            Stop = CarpenterStop;
            Eat = CarpenterEat;
            WLeft = CarpenterWLeft;
            WRight = CarpenterWRight;
            Work = new BufferedImage[][] { { chop1body, Eye, Mouth, Hair, chop1axe }, { null, chop2, Eye, Mouth, Hair },
                    { null, build1, Eye, Mouth, Hair }, { null, build2, Eye, Mouth, Hair },
                    { null, gift, Eye, Mouth, Hair } };
        } else {
            Stop = GuardStop;
            Eat = GuardEat;
            WLeft = GuardWLeft;
            WRight = GuardWRight;
            Work = new BufferedImage[][] { { null, patrolbody, Eye, Mouth, Hair, patrolarm } };
        }

        // each array takes the following form: Base[0], ClassBase[1], Eyes[2],
        // Mouth[3], Hair[4], Extra[5]
        stand = new BufferedImage[] { StopBase, Stop, Eye, Mouth, Hair, null };
        sleep = new BufferedImage[] { StopBase, Stop, ClosedEyes, Mouth, Hair, Sleep };
        eat1 = new BufferedImage[] { EatBase, Eat, ClosedEyes, null, Hair, null };
        eat2 = new BufferedImage[] { EatBase, Eat, ClosedEyes, null, Hair, Eating };
        walkleft = new BufferedImage[] { WLeftBase, WLeft, Eye, Mouth, Hair, null }; // eyes must look left
        walkright = new BufferedImage[] { WRightBase, WRight, Eye, Mouth, Hair, null }; // eyes must look right

    }

    public void decideName() {
        ArrayList<String> names = new ArrayList<>();
        if (gender == Person.MALE) {
            names = malenames;
        } else {
            names = femalenames;
        }
        int i = (int) ((names.size()) * Math.random());
        name = names.get(i);
      // assigns name from arraylist to each instance of person 
    }

    public void decideBed() {
        int i = (int) ((beds.size()) * Math.random());
        bed = beds.get(i); 
    } // similar to the name method, but with the beds instead of the names

}
