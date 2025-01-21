public class GenericNodeStructure <E> {
    public GenericNode<E> endNode; //generic node that marks the end of the LinkedList; initiailly null. 

    public GenericNodeStructure () {
        endNode= null;   
    }

    public void append (E toAppend) {//E is either a String or an int in our code
        GenericNode<E> appendex= new GenericNode<E>(toAppend); //wrap the parameters in a node
        appendex.previousNode= endNode; //causes link between endNode and appendex
        endNode= appendex;
    }

}

    class GenericNode<E> {
        GenericNode<E> previousNode; //Link to a previous node
        E contents; 

        public GenericNode (E c) {
            contents=c; //sets the parameter c as the content of GenericNode
        }
    }


