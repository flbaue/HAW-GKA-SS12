/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package start;

import java.util.*;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.GraphPathImpl;

/**
 *
 * @author NO
 */
public class Dijkstra<V extends Comparable<? super V>, E> {

    Graph<V, E> myGraph;
    // Start Ecke
    V start;
    // um Graphen mit negativen Kanten zu merken
    boolean negativeKanten = false;
    // Liste alle Ecken
    List<V> vertexList;
    // Die Map enthält alle Ecken und ihren entfernungen von start Ecke 
    Map<V, Double> minDistance = new HashMap();
    // Vorgänger-Ecke nach Dijkstra algorithmus
    Map<V, V> previous = new HashMap();

    // Konstruktor (Graph, Start Ecke)
    public Dijkstra(Graph<V, E> myGraph, V start) {
        // setzt die Werte für die instance Variable
        this.myGraph = myGraph;
        this.start = start;

        // Liste alle Ecken füllen
        vertexList = new ArrayList(myGraph.vertexSet());

        // Die Ecken-Entfernungen von der start Ecke auf unendlich setzen
        for (V vertex : vertexList) {
            minDistance.put(vertex, Double.POSITIVE_INFINITY);
        }

        // Berechnung der Pfade aus gehen von der start Ecke  
        computePaths(start);

    }

    public void computePaths(V start) {

        // start Ecke Entfernung von sich selbst auf 0 setzen
        minDistance.put(start, 0.0);

        // Queue erstellen für die Bearbeitung aller Ecken
        PriorityQueue<V> vertexQueue = new PriorityQueue<V>();

        // Start Ecke in Queue hinzufügen
        vertexQueue.add(start);

        //Schleife die alle Ecken im Queue abarbeiten soll
        while (!vertexQueue.isEmpty()) {

            // Start Ecke von Queue rausnehmen " als 'OK' gearbeitet markieren"
            V vertex = vertexQueue.poll();

            // alle Kanten aus einer Ecke durchlaufen
            for (E edge : myGraph.edgesOf(vertex)) {

                // End-ecke speicheren für gerichtete Graphen
                V targetVertex = myGraph.getEdgeTarget(edge);

                // End-ecke speicheren für ungerichtete Graphen, kanten in andere Richtung prüfen (Richtung spielt beim ungerichteten Graphen keine Rolle)
                if (targetVertex.equals(vertex) && myGraph instanceof UndirectedGraph) {
                    targetVertex = myGraph.getEdgeSource(edge);
                }

                // Gewicht der Kante speicheren
                double weight = myGraph.getEdgeWeight(edge);

                // negative Kanten merken und die Berechnung abbrechen, damit keine endlöse Schleife auftrit
                if (weight < 0) {
                    this.negativeKanten = true;
                    break;
                }

                //Entfernung aus der Start-ecke neu berechnen
                double distanceThroughVertex = minDistance.get(vertex) + weight;

                // vergleich kurzeste Weg bis zur End-ecke
                if (distanceThroughVertex < minDistance.get(targetVertex)) {

                    //End-ecke als bearbeitet "OK" markieren (aus Queue entfernen)
                    vertexQueue.remove(targetVertex);

                    //Entefrnung der End-ecke von der Start-ecke ersetzen
                    minDistance.put(targetVertex, distanceThroughVertex);

                    //Vorgängerecke der Endecke merken 
                    previous.put(targetVertex, vertex);

                    //Endecke neu hinzufügen falls andere mögliche kurzeste Wege existiert 
                    vertexQueue.add(targetVertex);
                }
            }
        }
    }

    // kurzeste Wege berechnen
    public GraphPath getShortestPathTo(V target) {

        // Liste enthält der Weg zwichen Start und Ziel Ecke
        List<V> path = new ArrayList<V>();

        // Alle vorgänger Ecken der Ziel Ecke in eine Liste speicheren
        for (V vertex = target; vertex != null; vertex = previous.get(vertex)) {
            path.add(vertex);
        }

        // Reinfolge umdrehen
        Collections.reverse(path);

        //Liste enthält alle Kanten zwichen Start und Ziel Ecke
        List<E> edgesList = new ArrayList();
        
        //Alle Kanten zwichen Start und Ziel Ecke in eine Liste speicheren
        for (int i = 0; i < path.size() - 1; i++) {
            edgesList.add(myGraph.getEdge(path.get(i), path.get(i + 1)));
        }
        
        // Weg züruckgeben
        return new GraphPathImpl(myGraph, start, target, edgesList, minDistance.get(target));
    }

    @Override
    public String toString() {
        
        String result = "";
        
        // holt der Weg zwichen die Start Ecke und alle rest Ecken der Graph
        for (V v : vertexList) {
            GraphPath path = getShortestPathTo(v);
            result += "From " + path.getStartVertex() + " To " + path.getEndVertex() + ":\n" + path + " Distance is: " + path.getWeight() + "\n";
        }
        
        // Beim Graphen mit negativen Kanten Warning zeigen
        if (negativeKanten) {
            result += "Negative Kanten werden nicht unterstützt bei Dijkstra.. Warnning!! falshe Ergibnisse ";
        }
        
        //kurzeste Wege von Start Ecke nach alle restliche Ecken des Graphs aus geben
        return result;
    }
}