/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package start;

import java.util.*;
import org.jgrapht.Graph;

/**
 *
 * @author Tobi
 */
public class Dijkstra<V extends Comparable<? super V>, E> {
    
    Graph<V, E> myGraph;
    private final static boolean LOG = false;
    List<V> vertexList;
    Map<V, Double> minDistance = new HashMap();
    // Vorgänger Vertex nach Dijkstra algo.
    Map<V, V> previous = new HashMap();
    
    public Dijkstra(Graph<V, E> myGraph) {
        this.myGraph = myGraph;
        vertexList = new ArrayList(myGraph.vertexSet());
        // Die Entfernung am Anfang auf unendlich setzen
        for (V vertex : vertexList) {
            minDistance.put(vertex, Double.MAX_VALUE);
        }
        computePaths(vertexList.get(0));
        for (V v : vertexList) {
            System.out.println("Distance to " + v + ": " + minDistance.get(v));
            List<V> path = getShortestPathTo(v);
            System.out.println("Path: " + path);
        }
    }
    
    public void computePaths(V source) {
        minDistance.put(source, 0.0);
        PriorityQueue<V> vertexQueue = new PriorityQueue<V>();
        vertexQueue.add(source);
        
        while (!vertexQueue.isEmpty()) {
            V u = vertexQueue.poll();
            if (myGraph== "undirected") {
                // Visit each edge exiting u
                
                List<V> neigbors = getAllNeigbors(myGraph, u);
                for (V e :
                        neigbors) {
                    double weight =
                            myGraph.getEdgeWeight(myGraph.getEdge(u, e));
                    if (weight < 0) {
                        System.out.println("Negative Kanten Gewicht sind nicht ünterstützt in Dijkstra Algorithmus");                        
                        minDistance.clear();
                        vertexList.clear();
                        return;
                    }
                    double distanceThroughU =
                            minDistance.get(u) + weight;
                    if (distanceThroughU
                            < minDistance.get(e)) {
                        vertexQueue.remove(e);
                        minDistance.put(e,
                                distanceThroughU);
                        previous.put(e, u);
                        vertexQueue.add(e);
                    }
                }
            } else {
                for (E e : myGraph.edgesOf(u)) {
                    V v = myGraph.getEdgeTarget(e);
//                if (v.equals(u)){
//                    v = myGraph.getEdgeSource(e);
//                }
                    double weight = myGraph.getEdgeWeight(e);
                    if (weight < 0) {
                        System.out.println("Negative Kanten Gewicht sind nicht ünterstützt in Dijkstra Algorithmus");
                        minDistance.clear();
                        vertexList.clear();
                        return;
                    }
                    
                    double distanceThroughU = minDistance.get(u) + weight;
                    
                    if (distanceThroughU < minDistance.get(v)) {
                        vertexQueue.remove(v);
                        minDistance.put(v, distanceThroughU);
                        previous.put(v, u);
                        vertexQueue.add(v);
                    }
                }
            }
        }
        
    }
    
    public List<V> getShortestPathTo(V target) {
        List<V> path = new ArrayList<V>();
        for (V vertex = target; vertex != null; vertex = previous.get(vertex)) {
            path.add(vertex);
        }
        Collections.reverse(path);
        return path;
    }
    
    private List<V> getAllNeigbors(Graph<V, E> g, V v) {
        if (LOG) {
            System.out.println("#########getAllNeigbors-START" + v);
        }

        //Set, welches alle Nachbarn enthalten soll
        List<V> result = new ArrayList<>();

        //Set mit den angrenzenden Kanten der zu bearbeitenden Ecke
        Set<E> edgesOfMainVertex = g.edgesOf(v);
        
        for (E edge : edgesOfMainVertex) {
            
            if (g.getEdgeTarget(edge).equals(v)) {
                result.add(g.getEdgeSource(edge));
            } else {
                result.add(g.getEdgeTarget(edge));
            }
        }

        /*
         * // Nun wird über alle Ecken des Graphs traversiert for (V
         * otherVertex : g.vertexSet()) { //Von jeder Ecke werden die
         * angrenzenden Kanten gesammelt Set<E> edgesOfOtherVertex =
         * g.edgesOf(otherVertex); //Wenn beide Ecken gemeinsame Kanten haben,
         * sind diese Ecken Nachbarn. // Deshalb wird die aktuelle Ecke zum Set
         * der Nachbarn hinzugefügt for (E edge : edgesOfMainVertex) {
         *
         * if (edgesOfOtherVertex.contains(edge)) { result.add(otherVertex); } }
         * }
         */
        //Eine Ecke ist nicht mit sich selbst benachbart und wird deshalb entfernt
        result.remove(v);
        if (LOG) {
            System.out.println(v + " hat folgende Nachbarn: " + result);
        }
        if (LOG) {
            System.out.println("#########getAllNeigbors-End");
        }
        return result;
    }
}
