import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeSet;
public class Movement{
    public static void main(String[] args) {
        Problem p = new Problem(new Pair (743,20), new Pair (500, 600));
        int[][] grid = new int[77][104];
        for (int i = 0; i < 77; i++) {
            for (int j = 0; j < 104; j++) {
                grid[i][j] = 0;
            }
        }
    }

    public static Node BFS(Problem p, int[][] map) {
        //System.out.println("Running Breadth First Search");
        return getSolution(p, new MyQueue(), map);
    }

    public static Node getSolution(Problem p, PileLike pile, int[][] map) {
        TreeSet<State> considered = new TreeSet<State>();
        State initialState = p.getStartState();
        //System.out.println(initialState);
        Node initialNode = new Node(null, initialState);
        //System.out.println(initialNode);
        if (p.target.x == initialState.currentValue.x && p.target.y == initialState.currentValue.y) {
            //System.out.println("OK");
            return initialNode;
        }
        pile.push(initialNode);
        considered.add(initialState);
        while (! pile.isEmpty()) {
            Node popped = pile.pop();
            //System.out.println(popped);
            //System.out.println("");
            ArrayList<State> succs = popped.s.getSuccessors(map);
            for (State s: succs) {
                if (p.target.x == s.currentValue.x && p.target.y == s.currentValue.y) {
                    return new Node(popped, s);
                }
                else {
                    if (! considered.contains(s)) {
                        pile.push(new Node(popped, s));
                        considered.add(s);
                    }
                    // else{
                    //     //print
                    //     System.out.println(s + "PRINT");
                    // }
                }
            }
        }
        return null; // no solution
    }
}

class Node {
    Node parent;
    State s;
    public Node (Node parent, State s) {
        this.parent = parent;
        this.s = s;
    }
    public String toString() {
        if (this.parent == null) {
            return this.s.toString();
        }
        else {
            return this.parent.toString() + "->" + this.s.toString();
        }
    }
}

interface PileLike {
    public boolean isEmpty();
    public Node pop();
    public void push(Node s);
}

class MyQueue extends LinkedList<Node> implements PileLike {
    public Node pop() {
        return this.removeLast();
    }
}

class Problem {
    public Pair target;
    public Pair currentValue;
    public Problem(Pair currentValue, Pair target) {
        this.target = target;
        this.currentValue = currentValue;
    }

    public State getStartState() {
        return new State(this.currentValue, this.target);
    }

    public boolean isGoal(State s) {
        return ((State)s).currentValue.x == this.target.x && ((State)s).currentValue.y == this.target.y;
    }

}

class State implements Comparable<State>{
    private Pair target;
    public Pair currentValue;

    public State(Pair currentValue, Pair target) {
        this.currentValue = currentValue;
        this.target = target;
    }

    public ArrayList<State> getSuccessors(int[][] map) {
        Pair left = new Pair(this.currentValue.x - 5, this.currentValue.y);
        Pair right = new Pair (this.currentValue.x + 5, this.currentValue.y);
        Pair up = new Pair (this.currentValue.x, this.currentValue.y - 5);
        Pair down = new Pair (this.currentValue.x, this.currentValue.y + 5);
        ArrayList<State> toR = new ArrayList<State>();
        if (this.currentValue.x - 5 >= 200 && map[(int)(this.currentValue.y/10)][(int)((this.currentValue.x - 5 - 200)/10)] == 0) {
            toR.add(new State(left, this.target));
        }
        if (this.currentValue.x + 5 < 1235 && map[(int)(this.currentValue.y/10)][(int)((this.currentValue.x + 5 - 200)/10)] == 0) {
            toR.add(new State(right, this.target));
        }
        if (this.currentValue.y - 5 >= 5 && map[(int)((this.currentValue.y - 5)/10)][(int)((this.currentValue.x - 200)/10)] == 0) {
            toR.add(new State(up, this.target));
        }
        if (this.currentValue.y + 5 < 765 && map[(int)((this.currentValue.y + 5)/10)][(int)((this.currentValue.x - 200)/10)] == 0) {
            toR.add(new State(down, this.target));
        }
        return toR;
    }

    public String toString() {
        return "[Val: (" + this.currentValue.x + "," + this.currentValue.y + ") (" + this.target.x + "," + this.target.y + ")]";
    }

    public boolean equals(Object s) {
        State other = (State) s;
        
      
        //System.out.println("YAY");
        return other.currentValue.x == this.currentValue.x && other.currentValue.y == this.currentValue.y && other.target.x == this.target.x && other.target.y == this.target.y;
    }

    public int compareTo(State other){
        if (this.currentValue.y >  ((State)other).currentValue.y) {
            return 1;
        }
        else if (this.currentValue.y < ((State)other).currentValue.y) {
            return -1;
        }
        else {
            if (this.currentValue.x == ((State)other).currentValue.x) {
                return 0;
            }
            else if (this.currentValue.x > ((State)other).currentValue.x) {
                return 1;
            }
            else {
                return -1; 
            }
        }
    }
}