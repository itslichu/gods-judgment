public class Guard extends Person {
    //additional variables to record movement
    Pair target;
    //random spots around the map
    Pair[] locations = {new Pair(490, 350), new Pair(1100, 620), new Pair(760, 520), new Pair(680,570), new Pair(900, 330), new Pair(700, 360), new Pair(580, 330)};

    public Guard(World w) {
        this((int) (Math.random() * 2), w);
    }

    public Guard(int i, World w) {
        super();
        this.attributes.persontype = Person.GUARD;
        getPlayerImage();
        this.name = attributes.name;
        this.gender = attributes.gender;
        this.bedLocation = attributes.bed;
        homeLocation = bedLocation;
        //random starting location at the guard workspace
        workLocation = new Pair(290 + Math.random() * 160, 390 + Math.random() * 160);
        workLocation = new Pair(workLocation.x - workLocation.x % 5, workLocation.y - workLocation.y % 5);
        position = homeLocation;
        //path informations
        target = workLocation;
        pathInfo = workLocation;
        path = Movement.BFS(new Problem (new Pair((int) workLocation.x, (int) workLocation.y), new Pair((int) homeLocation.x, (int) homeLocation.y)), w.grid);
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
            //movements according to timeframes
            else {
                if ((hour >= 23) || (hour < 7)) {
                    sleep();
                } 
                else if ((hour >= 7) && (hour < 8)) {
                    eat();
                } 
                else if ((hour >= 8) && (hour < 13)) {
                    work(0);
                    if (!pathInfo.equals(target)) {
                        path = Movement.BFS(new Problem(new Pair((int)target.x, (int) target.y), new Pair ((int)(position.x - position.x % 5), (int)(position.y - position.y % 5))), w.grid);
                        pathInfo = target;
                    }
                    if (!position.equals(target)) {
                        moveTo();
                    }
                    else {
                        target = locations[(int)(Math.random() * 7)];
                    }
                }    
                else if ((hour >= 13) && (hour < 14)) {
                    eat();
                }
                else if ((hour >= 14) && (hour < 19)) {
                    work(0);
                    if (!pathInfo.equals(target)) {
                        path = Movement.BFS(new Problem(new Pair((int)target.x, (int) target.y), new Pair ((int)(position.x - position.x % 5), (int)(position.y - position.y % 5))), w.grid);
                        pathInfo = target;
                    }
                    if (!position.equals(target)) {
                        moveTo();
                    }
                    else {
                        target = locations[(int)(Math.random() * 7)];
                    }
                } 
                else if (hour >= 19 && hour < 21) {
                    if (!pathInfo.equals(homeLocation)) {
                        path = Movement.BFS(new Problem(new Pair((int)homeLocation.x, (int) homeLocation.y), new Pair ((int)(position.x - position.x % 5), (int)(position.y - position.y % 5))), w.grid);
                        pathInfo = homeLocation;
                    }
                    if (!position.equals(homeLocation)) {
                        walk();
                        moveTo();
                    }
                }
                else if (hour >= 21 && hour < 23) {
                    eat();
                }
                else if (hour >= 23) {
                    sleep();
                }
            }
        }
    }

    // for guard, working is ...
    // 0 = patrol
    public void work(int type) {
        resethead();
        appearance = attributes.Work[type];
        if (type == 0) {
            hair.x = 0;
            hair.y = 0;
            face.y = 0;
            if (((playerNum/4) % 2) == 1) {
                face.x = -1;
            }
            else {
                face.x = 1;
            }
        }
    }


    //when the guard passes by a Thief, the thief ends up in prison
    public void workInteraction(World w) {
        for (int i = 0; i < w.numPerson; i++) {
            if (Math.abs(w.people[i].position.x - this.position.x) < 5 && Math.abs(w.people[i].position.y - this.position.y) < 5 && w.people[i].isMurderer) {
                w.people[i].isLocked = true;
                w.people[i].position = new Pair(290 + Math.random()* 160, 390 + Math.random() * 160);
                w.people[i].position = new Pair(position.x - position.x % 5, position.y - position.y % 5);
            }
        }
    }
}