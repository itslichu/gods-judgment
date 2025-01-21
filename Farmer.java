public class Farmer extends Person {
    String workType;
    
    //used when adding villagers
    public Farmer(World w) {
        this((int) (Math.random() * 2), w);
    }

    //used initially when spawning the village
    public Farmer(int i, World w) {
        super();
        this.attributes.persontype = Person.FARMER;
        getPlayerImage();
        this.name = attributes.name;
        this.gender = attributes.gender;
        this.bedLocation = attributes.bed;
        homeLocation = bedLocation;
        //random starting location on the farm
        workLocation = new Pair(250 + Math.random() * 250, 50 + Math.random() * 250);
        //have to do this because BFS moves in 5-pixel steps
        workLocation = new Pair(workLocation.x - workLocation.x % 5, workLocation.y - workLocation.y % 5);
        //initial position
        position = homeLocation;
        //first path information
        pathInfo = workLocation;
        path = Movement.BFS(new Problem (new Pair((int) workLocation.x, (int) workLocation.y), new Pair((int) homeLocation.x, (int) homeLocation.y)), w.grid);
    }

    public void update(World w, double time, long hour) {
        super.update(w, time, hour);
        if (!isLocked) {
            if (isInfected) {
                if (hour >= 16 && hour < 20) {
                    position = new Pair (950, 150); //teleport to the clinic during the open hours
                    health += 0.05; //healing
                    w.clinicHasVisitor = true; //clinic customer status update
                }
                else {
                    position = homeLocation; //quarantine at home
                    w.clinicHasVisitor = false; //clinic customer status update
                }
            }
            //allocating movements to time frames
            else {
                if (hour == 0) {
                    velocity = new Pair (-100 + Math.random() * 100, -100 + Math.random() * 100); //updates velocity every day so that there's difference between days
                }
                if ((hour >= 0) && (hour < 4)) {
                    sleep();
                } 
                else if ((hour >= 4) && (hour < 5)) {
                    eat();
                } 
                else if ((hour >= 5) && (hour < 7)) {
                    walk();
                    if (!pathInfo.equals(workLocation)) { //this so that it doesn't have to call BFS every update, but only when location is changing
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
                else if (hour >= 11 && hour < 12){
                    eat();
                }
                else if ((hour >= 7) && (hour < 19)) {
                    if (hour >= 7 && hour < 11) {
                        work(0);
                        workType = "planting";
                    }
                    else if (hour >= 12 && hour < 15) {
                        work(2);
                        workType = "watering";
                    }
                    else if (hour >= 15 && hour < 19) {
                        work(4);
                        workType = "reaping";
                    }
                    position = position.add(velocity.times(time));
                    navigateAtWork(w);
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

    //navigating movement inside the farm
    private void navigateAtWork(World w) { 
        if ((position.x < 240) || (position.x > 530 - 32)) {
            velocity.flipX();
        }
        if ((position.y < 40) || (position.y > 330 - 32)) {
            velocity.flipY();
        }
    }

    // for farmer, worktype is ...
    // 0 = plant1, 1 = plant2, 2 = water1, 3 = water2, 4 = reap1, 5 = reap2, 6 =
    // reap3;
    public void work(int type) {
        resethead();
        appearance = attributes.Work[type];
        if (type == 0) {
            face.x = 2;
            face.y = 5;
            hair.x = 1;
            hair.y = 4;
            if ((playerNum % 2) == 0) {
                work(1);
            }
        } else if (type == 1) {
            face.x = 1;
            face.y = 5;
            hair.x = 1;
            hair.y = 4;
            if ((playerNum % 2) == 1) {
                work(0);
            }
        } else if (type == 2) {
            face.x = -6;
            face.y = 5;
            hair.x = -7;
            hair.y = 4;
            if ((playerNum % 2) == 0) {
                work(3);
            }
        } else if (type == 3) {
            face.x = -6;
            face.y = 5;
            hair.x = -7;
            hair.y = 4;
            if ((playerNum % 2) == 1) {
                work(2);
            }
        } else if (type == 4) {
            face.x = 0;
            face.y = 4;
            hair.x = 0;
            hair.y = 4;
            if ((playerNum % 3) != 1) {
                work(5);
            }
        } else if (type == 5) {
            face.x = 9;
            face.y = 4;
            hair.x = 8;
            hair.y = 4;
            if ((playerNum % 3) != 2) {
                work(6);
            }
        } else if (type == 6) {
            face.x = -8;
            face.y = 4;
            hair.x = -9;
            hair.y = 4;
            if ((playerNum % 3) != 0) {
                work(4);
            }
        }
    }

    //farmer changes the sprites of the crops when they pass by it depending on their worktype
    public void workInteraction(World w) {
        int i = (int) ((this.position.x - 230) / 10);
        int j = (int) ((this.position.y - 30) / 10);
            if (this.position.x > 240 && this.position.x < 530 && this.position.y > 40 && this.position.y < 330) {
                if (this.workType == "planting" && w.structure.crops[j][i] == 0) {
                    w.structure.crops[j][i] = 2;
                }
                if (this.workType == "watering" && w.structure.crops[j][i] == 2) {
                    w.structure.crops[j][i] = 1;
                }
                if (this.workType == "watering" && w.structure.crops[j][i] == 1) {
                    w.structure.crops[j][i] = 3;
                }
                if (this.workType == "reaping" && w.structure.crops[j][i] == 3) {
                    w.structure.crops[j][i] = 0;
                }
            }
    }
}
