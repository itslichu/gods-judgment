public class Carpenter extends Person {
    Pair chopLocation, statueLocation;
    //because carpenter doesn't go to random positions, we just record all the paths right away
    Node pathHomeToChop, pathChopToWork, pathWorkToStatue, pathStatueToHome;

    public Carpenter(World w) {
        this((int) (Math.random() * 2), w);
    }

    public Carpenter(int i, World w) {
        super();
        this.attributes.persontype = Person.CARPENTER;
        getPlayerImage();
        this.name = attributes.name;
        this.gender = attributes.gender;
        this.bedLocation = attributes.bed;
        homeLocation = bedLocation;
        //random position at carpenter workspace
        workLocation = new Pair(830 + Math.random() * 300, 410 + Math.random() * 80);
        //because BFC moves by 5 pixels
        workLocation = new Pair(workLocation.x - workLocation.x % 5, workLocation.y - workLocation.y % 5);
        //???????????????????????//
        chopLocation = new Pair(870, 430);
        //similar to carpenter workspace
        statueLocation = new Pair(680 + Math.random() * 100, 350 + Math.random()*90);
        statueLocation = new Pair(statueLocation.x - statueLocation.x % 5, statueLocation.y - statueLocation.y % 5);
        //initial position
        position = homeLocation;
        //path informations
        pathInfo = chopLocation;
        pathHomeToChop = Movement.BFS(new Problem (new Pair((int) chopLocation.x, (int) chopLocation.y), new Pair((int) homeLocation.x, (int) homeLocation.y)), w.grid);
        pathChopToWork = Movement.BFS(new Problem (new Pair((int) workLocation.x, (int) workLocation.y), new Pair((int) chopLocation.x, (int) chopLocation.y)), w.grid);
        pathWorkToStatue = Movement.BFS(new Problem (new Pair((int) statueLocation.x, (int) statueLocation.y), new Pair((int) workLocation.x, (int) workLocation.y)), w.grid);
        pathStatueToHome = Movement.BFS(new Problem (new Pair((int) homeLocation.x, (int) homeLocation.y), new Pair((int) statueLocation.x, (int) statueLocation.y)), w.grid);
        path = pathHomeToChop;
    }

    public void update(World w, double time, long hour) {
        super.update(w, time, hour);
        //similar to other classes
        if (!isLocked) {
            if (isInfected) {
                if (hour >= 16 && hour < 20) {
                    position = new Pair (950, 150);
                    health += 0.05;
                    w.clinicHasVisitor = true;
                }
                else {
                    position = homeLocation;
                    w.clinicHasVisitor = false;
                }
            } 
            //allocating movements according to timeframes
            else {
                if ((hour >= 0) && (hour < 6)) {
                    sleep();
                } 
                else if ((hour >= 6) && (hour < 7)) {
                    eat();
                } 
                else if ((hour >= 7) && (hour < 9)) {
                    walk();
                    if (!pathInfo.equals(chopLocation)) {
                        path = pathHomeToChop;
                        pathInfo = chopLocation;
                    }
                    if (!position.equals(chopLocation)) {
                        moveTo();
                    }
                    else {
                        appearance = attributes.stand;
                    }
                } 
                else if (hour >= 9 && hour < 12){
                    work(0);
                }
                else if (hour >= 12 && hour < 13) {
                    eat();
                }
                else if (hour >= 13 && hour < 15) {
                    walk();
                    if (!pathInfo.equals(workLocation)) {
                        path = pathChopToWork;
                        pathInfo = workLocation;
                    }
                    if (!position.equals(workLocation)) {
                        moveTo();
                    }
                    else {
                        appearance = attributes.stand;
                    }
                }
                else if ((hour >= 15) && (hour < 18)) {
                    work(2);
                }
                else if ((hour >= 18) && (hour < 19)) {
                    eat();
                }
                else if (hour >= 19 && hour < 21) {
                    walk();
                    if (!pathInfo.equals(statueLocation)) {
                        path = pathWorkToStatue;
                        pathInfo = statueLocation;
                    }
                    if (!position.equals(statueLocation)) {
                        moveTo();
                    }
                }
                else if (hour >= 21 && hour < 22) {
                    work(4);
                }
                else if (hour >= 22) {
                    walk();
                    if (!pathInfo.equals(homeLocation)) {
                        path = pathStatueToHome;
                        pathInfo = homeLocation;
                    }
                    if (!position.equals(homeLocation)) {
                        moveTo();
                    }
                }
            }
        }
    }

    
    // for carpenter, working is ...
    // 0 = chop1, 1 = chop2, 2 = build1, 3 = build2, 4 = gift
    public void work(int type) {
        resethead();
        appearance = attributes.Work[type];
        if (type == 0) {
            face.x = 0;
            face.y = 0;
            hair.x = 0;
            hair.y = 0;
            if ((playerNum % 2) == 0) {
                work(1);
            }
        }
        if (type == 1) {
            face.x = 0;
            face.y = 1;
            hair.x = 0;
            hair.y = 0;
            if ((playerNum % 2) == 1) {
                work(0);
            }
        }
        if (type == 2) {
            face.x = 0;
            face.y = 1;
            hair.x = 0;
            hair.y = 0;
            if ((playerNum % 2) == 0) {
                work(3);
            }
        }
        if (type == 3) {
            face.x = 0;
            face.y = 1;
            hair.x = 0;
            hair.y = 0;
            if ((playerNum % 2) == 1) {
                work(2);
            }
        }
        if (type == 4) {
            face.x = 0;
            face.y = 0;
            hair.x = 0;
            hair.y = 0;
        }
    }

    public void workInteraction(World w) {
        //no interaction with the environment;
    }
}