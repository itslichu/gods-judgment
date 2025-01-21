public class Herbalist extends Person {
    Pair gardenLocation;
    Node pathHomeToGarden, pathWorkToHome;

    public Herbalist(World w) {
        this((int) Math.random(), w);
    }

    public Herbalist(int i, World w) {
        super();
        this.attributes.persontype = Person.HERBALIST;
        getPlayerImage();
        this.name = attributes.name;
        this.gender = attributes.gender;
        this.bedLocation = attributes.bed;
        //homeLocation = bedLocation;
        homeLocation = new Pair(bedLocation.x + 10, bedLocation.y);
        homeLocation = new Pair(530, 560);
        // homeLocation = new Pair((int)200 + Math.random() * 500, (int) 5 + Math.random() * 350);
        // homeLocation = new Pair(homeLocation.x - homeLocation.x % 5, homeLocation.y - homeLocation.y % 5);
        //homeLocation = bedLocation;
        workLocation = new Pair(900 + Math.random() * 160, 100 + Math.random() * 160);
        workLocation = new Pair(workLocation.x - workLocation.x % 5, workLocation.y - workLocation.y % 5);
        gardenLocation = new Pair(1080 + Math.random() * 80, 40 + Math.random()*280);
        gardenLocation = new Pair(gardenLocation.x - gardenLocation.x % 5, gardenLocation.y - gardenLocation.y % 5);
        position = homeLocation;
        pathInfo = gardenLocation;
        pathHomeToGarden = Movement.BFS(new Problem (new Pair((int) gardenLocation.x, (int) gardenLocation.y), new Pair((int) homeLocation.x, (int) homeLocation.y)), w.grid);
        pathWorkToHome = Movement.BFS(new Problem (new Pair((int) homeLocation.x, (int) homeLocation.y), new Pair((int) workLocation.x, (int) workLocation.y)), w.grid);
        path = pathHomeToGarden;
        
    }

    public void update(World w, double time, long hour) {
        super.update(w, time, hour);
        if (!isLocked) {
        if ((hour >= 23) || (hour < 7)) {
            sleep();
        } 
        else if ((hour >= 7) && (hour < 8)) {
            eat();
        } 
        else if ((hour >= 8) && (hour < 10)) {
            walk();
            if (!pathInfo.equals(gardenLocation)) {
                path = pathHomeToGarden;
                pathInfo = gardenLocation;
            }
            if (!position.equals(gardenLocation)) {
                moveTo();
            }
            else {
                appearance = attributes.stand;
            }
        } 
        else if (hour >= 10 && hour < 13) {
            work(0);
            position = position.add(velocity.times(time));
            navigateAtWork(w);
            
        }
        else if (hour >= 13 && hour < 15) {
            walk();
            if (!pathInfo.equals(workLocation)) {
                path = Movement.BFS(new Problem(new Pair((int)workLocation.x, (int) workLocation.y), new Pair ((int)(position.x - position.x % 5), (int)(position.y - position.y % 5))), w.grid);
                pathInfo = workLocation;
            }

            if (!position.equals(workLocation)) {
                moveTo();
            }
        }
        else if (hour >= 15 && hour < 16) {
            eat();
        }
        else if (hour >= 16 && hour < 20) {
            if (w.clinicHasVisitor) {
                work(2);
            }
            else {
                work(3);
            }
        }
        else if (hour >= 20 && hour < 22) {
            if (!pathInfo.equals(homeLocation)) {
            path = pathWorkToHome;
            pathInfo = homeLocation;
        }
        if (!position.equals(homeLocation)) {
            walk();
            moveTo();
        }
    }
    else {
        eat();
    }  
}
    }

    private void navigateAtWork(World w) { 
        if ((position.x < 1080) || (position.x > 1170 - 32)) {
            velocity.flipX();
        }
        if ((position.y < 40) || (position.y > 330 - 32)) {
            velocity.flipY();
        }
        // if (position.x > 705 && position.y > 375) {
        //     velocity.flipX();
        //     velocity.flipY();
        // }
    }

    // for herbalist, working is ... 
    // 0 = gather1, 1 = gather2, 2 = heal, 3 = brew
    public void work(int type) {
        resethead();
        //isMoving = false;
        appearance = attributes.Work[type];
        if (type == 0) {
            face.x = 1;
            face.y = 1;
            hair.x = 0;
            hair.y = 1;
            if ((playerNum % 2) == 0) {
                work(1);
            }
        }
        if (type == 1) {
            face.x = 0;
            face.y = 1;
            hair.x = 0;
            hair.y = 1;
            if ((playerNum % 2) == 1) {
                work(0);
            }
        }
        if (type == 2) {
            face.x = 1;
            face.y = 0;
            hair.x = 0;
            hair.y = 0;
        }
        if (type == 3) {
            face.x = 0;
            face.y = 1;
            hair.x = 0;
            hair.y = 0;
        }
    } 

    public void workInteraction(World w) {
    }
    
}
