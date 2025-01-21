public class Caretaker extends Person {
    boolean collided = false;
    int workCounter = 0;
    
    //used when adding villagers
    public Caretaker(World w) {
        this((int) (Math.random() * 2), w);
    }
   
    //used when creating the village
    public Caretaker(int i, World w) {
        super();
        this.attributes.persontype = Person.CARETAKER;
        getPlayerImage();
        this.name = attributes.name;
        this.gender = attributes.gender;
        this.bedLocation = attributes.bed;
        homeLocation = bedLocation;
        //certain starting location at the animal barn
        workLocation = new Pair(700, 230);
        //initial position
        position = homeLocation;
        //first path information
        pathInfo = workLocation;
        path = Movement.BFS(new Problem (new Pair((int) workLocation.x, (int) workLocation.y), new Pair((int) homeLocation.x, (int) homeLocation.y)), w.grid);
        
    }

    public void update(World w, double time, long hour) {
        super.update(w, time, hour);
        //if in the prison can't go anywhere
        if (!isLocked) {
            //if sick, either go to clinic to heal or quarantine at home
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
            //allocating movements to timeframes
            else {
                if (hour == 0) {
                    velocity = new Pair (-100 + Math.random() * 100, -100 + Math.random() * 100); //updates velocity every day so that there's difference between days
                }
                if ((hour >= 0) && (hour < 6)) {
                    sleep();
                } 
                else if ((hour >= 6) && (hour < 7)) {
                    eat();
                } 
                else if ((hour >= 7) && (hour < 9)) {
                    walk();
                    if (!pathInfo.equals(workLocation)) {
                        path = Movement.BFS(new Problem(new Pair((int)workLocation.x, (int) workLocation.y), new Pair ((int)(position.x - position.x % 5), (int)(position.y - position.y % 5))), w.grid);
                        pathInfo = workLocation;
                    }
                    if (!position.equals(workLocation)) {
                        moveTo();
                    }
                    else {
                        appearance = attributes.stand;
                    }
                } 
                else if (hour >= 13 && hour < 14){
                    eat();
                }
                else if ((hour >= 9) && (hour < 18)) {
                    //if collide with a cow, milk the cow for a period of time
                    if (collided) {
                        work(1);
                        workCounter++;
                    }
                    else {
                        if (hour >= 9 && hour < 13) {
                            work(0);
                        }
                        else if (hour >= 13 && hour < 18) {
                            work(3);
                        }
                        position = position.add(velocity.times(time));
                        navigateAtWork(w);
                    }
                }
                else if (hour >= 18 && hour < 20) {
                    if (!pathInfo.equals(homeLocation)) {
                        path = Movement.BFS(new Problem(new Pair((int)homeLocation.x, (int) homeLocation.y), new Pair ((int)(position.x - position.x % 5), (int)(position.y - position.y % 5))), w.grid);
                        pathInfo = homeLocation;
                    }
                    if (!position.equals(homeLocation)) {
                        walk();
                        moveTo();
                    }
                    else {
                        appearance = attributes.stand;
                    }
                }
                else if (hour >= 20 && hour < 21) {
                    eat();
                }
                else if (hour >= 21) {
                    sleep();
                }
            }
        }
    }

    //navigating movement inside the barn
    private void navigateAtWork(World w) { 
        if ((position.x < 590) || (position.x > 840 - 32)) {
            velocity.flipX();
        }
        if ((position.y < 60) || (position.y > 310 - 32)) {
            velocity.flipY();
        }
    }

    //when comes into contact with a cow, both stop for a while, the working animation changes to milking
    public void workInteraction(World w) {
        for (int i = 0; i < 5; i++) {
            if (collided && workCounter > 100 || Math.abs(w.cows[i].position.x - this.position.x) >= 5 && Math.abs(w.cows[i].position.y - this.position.y + 10) >= 5) {
                collided = false;
                workCounter = 0;
                w.cows[i].isMoving = true;
            }
            else if (!collided && Math.abs(w.cows[i].position.x - this.position.x) < 5 && Math.abs(w.cows[i].position.y - this.position.y + 10) < 5) {
                collided = true;
                w.cows[i].isMoving = false;
            }
        }
    }

    // for caretaker, working is ...
    // 0 = feed, 1 = milk1, 2 = milk2, 3 = sweep1, 4 = sweep2
    public void work(int type) {
        resethead();
        appearance = attributes.Work[type];
        if (type == 0) {
            face.x = 0;
            face.y = 0;
            hair.x = 0;
            hair.y = 0;
        }
        if (type == 1) {
            face.x = 0;
            face.y = 0;
            hair.x = 0;
            hair.y = 0;
            if ((playerNum % 2) == 0) {
                work(2);
            }
        }
        if (type == 2) {
            face.x = 0;
            face.y = 0;
            hair.x = 0;
            hair.y = 0;
            if ((playerNum % 2) == 1) {
                work(1);
            }
        }
        if (type == 3) {
            face.x = -1;
            face.y = 0;
            hair.x = 0;
            hair.y = 0;
            if ((playerNum % 2) == 0) {
                work(4);
            }
        } 
        if (type == 4) {
            face.x = -1;
            face.y = 1;
            hair.x = 0;
            hair.y = 0;
            if ((playerNum % 2) == 1) {
                work (3);
            }
        }
    }
}
