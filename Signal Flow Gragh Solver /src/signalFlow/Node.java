package signalFlow;

import java.util.ArrayList;

public class Node{
    private int value;
    private ArrayList<Edge> list;
    
    public Node(int value){
        this.value = value;
        list = new ArrayList<Edge>();
    }
    
    public int getValue(){
        return value;
    }
    
    public Edge getEdge(Node node) {
    	for (Edge temp: list) {
    		if(temp.getDest().equals(node)) {
    			return temp;
    		}
    	}
    	return null;
    }
    
    public void addEdge(Edge edge){
        list.add(edge);
    }
    
    public void addNeighbour(Node node, double weight, String label){
    	if (getEdge(node) != null) {
    		getEdge(node).updateWeight(weight);
    		return;
    	}
        list.add(new Edge(weight, node, label));
    }

    public final ArrayList<Edge> getEdges(){
        return list;
    }
}