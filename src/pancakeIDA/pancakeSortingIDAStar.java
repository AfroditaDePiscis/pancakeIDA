package pancakeIDA;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;


public class pancakeSortingIDAStar {
	
    public static void main(String[] args) {
        String input = "cgehdfba";
        pancakeSortIDAStar(input);
    }
    
 
    public static void pancakeSortIDAStar(String input) {
    	int nodes=1;
        char[] pancakes = input.toCharArray();
        Node start = new Node(pancakes, new ArrayList<>());
        int threshold = start.heuristic;
        while (true) {
        	
            int nextThreshold = Integer.MAX_VALUE;
            Stack<Node> stack = new Stack<>();
            Set<String> visited = new HashSet<>();
            stack.push(start);
            while (!stack.isEmpty()) {
            	nodes++;
                Node current = stack.pop();
                if (isSorted(current.state)) {
                	 System.out.println("Pasos: " + current.flips);
                     System.out.println("Volteos: " + current.flipIndices);
                     System.out.println("Nodos visitados: " + nodes);
                    return;
                }
                if (current.flips + current.heuristic > threshold) {
                    nextThreshold = Math.min(nextThreshold, current.flips + current.heuristic);
                } else {
                    for (int i = 2; i <= current.state.length; i++) {
                        char[] childState = flip(current.state, i);
                        String childStateString = new String(childState);
                        if (!visited.contains(childStateString)) {
                            List<Integer> childFlips = new ArrayList<>(current.flipIndices);
                            childFlips.add(i);
                            Node child = new Node(childState, childFlips);
                            visited.add(childStateString);
                            stack.push(child);
                        }
                    }
                }
            }
            threshold = nextThreshold;
        }
    }
    
    
    private static boolean isSorted(char[] pancakes) {
        for (int i = 0; i < pancakes.length - 1; i++) {
            if (pancakes[i] > pancakes[i + 1]) {
                return false;
            }
        }
        return true;
    }

    private static char[] flip(char[] pancakes, int k) {
        char[] result = new char[pancakes.length];
        int cuont =1;
        for (int i = 0; i <= pancakes.length-k; i++) {
            result[i] = pancakes[i];
        }
        for (int i = pancakes.length-k; i < pancakes.length; i++) {
            result[i] = pancakes[pancakes.length - cuont];
            cuont++;
        }
        return result;
    }

    private static class Node {
        public char[] state;
        public List<Integer> flipIndices;
        public int flips;
        public int heuristic;

        public Node(char[] state, List<Integer> flipIndices) {
            this.state = state;
            this.flipIndices = flipIndices;
            this.flips = flipIndices.size();
            this.heuristic = heuristic(state);
        }

        public int heuristic(char[] state) {
            int heuristic = 0;
            for (int i = 0; i < state.length - 1; i++) {
                if (Math.abs(state[i] - state[i+1]) > 1) {
                    heuristic++;
                }
            }
            if (state[0] != '1') {
                heuristic++;
            }
            return heuristic;
        }
    }
}