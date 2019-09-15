package org.com.mytest.ain;

import org.jsoup.nodes.Node;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

// to traverse the tree
public class AinNodeIterable implements Iterable<Node>{

    private Node root;

    // crates an iterable starting with the given Node
    public AinNodeIterable(Node root) {
        this.root = root;
    }

    public Iterator<Node> iterator() { // get an iterator
        return new AinNodeIterator(root);
    }

    private class AinNodeIterator implements Iterator<Node> {
        // this stack keeps track of the Nodes waiting to be visited
        Deque<Node> stack;

        // initializes the iterator with the root node on the stack
        public AinNodeIterator(Node node) {
            stack = new ConcurrentLinkedDeque<>(); // thread-safe
            stack.push(root);
        }

        public boolean hasNext() {
            return !stack.isEmpty();
        }

        public Node next() {
            // if the stack is empty, we're done
            if (stack.isEmpty()) {
                throw new NoSuchElementException();
            }

            // otherwise pop the next Node off the stack
            Node node = stack.pop();
//            System.out.println(node);

            // push the children onto the stack in reverse order
            List<Node> nodes = Collections.synchronizedList(new ArrayList<>(node.childNodes())); // a synchronized list
            Collections.reverse(nodes);

            for (Node child : nodes) {
                stack.push(child);
            }
            return node;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}