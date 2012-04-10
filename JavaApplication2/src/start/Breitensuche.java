/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package start;

import gComponents.Edge;
import gComponents.Vertex;
import org.jgrapht.Graph;
import java.util.*;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 *
 * @author Nidal
 */
public class Breitensuche {
    //=true setzten, um erweiterte Infos auf der Konsole auszugeben

    private final static boolean LOG = false;
    //Liste mit allen Ecken, in der Reienfolge, wie sie besucht wurden
    private List<Vertex> vertexList;
    private final String output;

    public Breitensuche(Graph<Vertex, Edge> myGraph) {
        if (LOG) {
            System.out.println("##BREITENSUCHE##");
        }

        //Diese Liste enthält alle Ecken in genau der Reihenfolge, in der sie vom Algorithmus abgelaufen werden
        vertexList = new ArrayList<>();

        //Anzahl der Zusammenhangskomponenten
        // Nice to have. Ist nicht erforderlich, lässt sich aufgrund der Implementierung aber leicht ermitteln.
        int komponenten = 0;

        //Es wird über alle Ecken des Graphs iterriert     
        for (Vertex v : myGraph.vertexSet()) {

            //Wenn eine Ecke noch nicht in der vertexList steht, dann wird diese als Start-Ecke für
            // den Algorithmus verwendet. Dies ist nur dann der Fall, wenn eine neue Zusammenhangs-
            // komponente des Graph abgearbeitet wird.
            if (!vertexList.contains(v)) {
                if (LOG) {
                    System.out.println(v);
                }
                vertexList.addAll(breitensuche(myGraph, v));

                if (LOG) {
                    System.out.println(vertexList.size());
                }
                komponenten++;
            } else {
                //Ist die Ecke bereits in der Ergebnisliste, so muss diese Ecke nicht erneut für
                // den Start des Algorithmus verwendet werden
            }
        }
        
        //Hier wird die Ausgabe für toString zusammengebastelt. Dies passiert im Konstruktor, da
        // diese Klasse immutable ist und sich die Ausgabe im nachhinein nicht mehr ändert.
        StringBuilder sb = new StringBuilder("Breitensuche! \nDer Graph besteht aus ");
        sb.append(Integer.toString(komponenten));
        sb.append(" Komponenten.\n");
        String nl = "\n";
        int x = 1 ;
        for (Vertex vertex : vertexList) {
            sb.append(x++ +"- ");
            sb.append(vertex);
            sb.append(nl);
        }
        output = sb.toString();
    }

    public List<Vertex> breitensuche(Graph<Vertex, Edge> graph, Vertex startNode) {
        // Liste alle Nachbarn-Ecken die noch zu bearbeiten sind
        List<Vertex> openList = new ArrayList();
        // Liste alle Ecken die schon gearbeitet sind  
        List<Vertex> closedList = new ArrayList();

        // Start Ecke in die Bearbeitung Liste hinzufuegen 
        openList.add(startNode);

        //loop wahrend es noch Ecken, in der Bearbeitung Liste gibt.
        //in der Liste wird immer das erste Element genommen und die neue Elemente am Ende der Liste hinzugefuegt werden, damit erricht man ein Queue Struktur        
        while (!openList.isEmpty()) {
            //Erste Ecke markieren 
            Vertex temp = openList.get(0);

            //Erste Ecke (temp) als bearbeitet markieren ( in die fertige Liste hinzufugen) 
            closedList.add(temp);

            // Nachbarn Ecken addieren am Ende der Liste.. queue strategy
            openList.addAll(getAllNeigbors(graph, temp));

            // Die Ecke, die gerade gearbeitet ist, (Erste Element) und die Ecken die schon besucht sind, entfernen.
            openList.removeAll(closedList);

        }
        //Liste alle besuchten Ecken zuruckgeben
        return closedList;
    }

    private static Set<Vertex> getAllNeigbors(Graph<Vertex, Edge> g, Vertex v) {
        if (LOG) {
            System.out.println("#########getAllNeigbors-START" + v);
        }

        //Set, welches alle Nachbarn enthalten soll
        Set<Vertex> result = new HashSet<>();

        //Set mit den angrenzenden Kanten der zu bearbeitenden Ecke
        Set<Edge> edgesOfMainVertex = g.edgesOf(v);

        // Nun wird über alle Ecken des Graphs traversiert
        for (Vertex otherVertex : g.vertexSet()) {
            //Von jeder Ecke werden die angrenzenden Kanten gesammelt
            Set<Edge> edgesOfOtherVertex = g.edgesOf(otherVertex);
            //Wenn beide Ecken gemeinsame Kanten haben, sind diese Ecken Nachbarn.
            // Deshalb wird die aktuelle Ecke zum Set der Nachbarn hinzugefügt
            for (Edge edge : edgesOfMainVertex) {
                if (edgesOfOtherVertex.contains(edge)) {
                    result.add(otherVertex);
                }
            }
        }

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

    @Override
    public String toString() {
        return output;
    }
}
