/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package start;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.jgrapht.Graph;

/**
 *
 * @author Nidal
 */
public class Breitensuche<V, E> {
    //=true setzten, um erweiterte Infos auf der Konsole auszugeben

    private final static boolean LOG = false;
    //Liste mit allen Ecken, in der Reienfolge, wie sie besucht wurden
    private List<V> vertexList;
    private final String output;

    public Breitensuche(Graph<V, E> myGraph) {
        if (LOG) {
            System.out.println("##BREITENSUCHE##");
        }

        //Diese Liste enthält alle Ecken in genau der Reihenfolge, in der sie vom Algorithmus abgelaufen werden
        vertexList = new ArrayList<>();

        //Anzahl der Zusammenhangskomponenten
        // Nice to have. Ist nicht erforderlich, lässt sich aufgrund der Implementierung aber leicht ermitteln.
        int komponenten = 0;

        //Es wird über alle Ecken des Graphs iterriert     
        for (V v : myGraph.vertexSet()) {

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
        for (V vertex : vertexList) {
            sb.append(x++ +"- ");
            sb.append(vertex);
            sb.append(nl);
        }
        output = sb.toString();
    }

    public List<V> breitensuche(Graph<V, E> graph, V startNode) {
        // Liste alle Nachbarn-Ecken die noch zu bearbeiten sind
        List<V> openList = new ArrayList();
        // Liste alle Ecken die schon gearbeitet sind  
        List<V> closedList = new ArrayList();

        // Start Ecke in die Bearbeitung Liste hinzufuegen 
        openList.add(startNode);

        //loop wahrend es noch Ecken, in der Bearbeitung Liste gibt.
        //in der Liste wird immer das erste Element genommen und die neue Elemente am Ende der Liste hinzugefuegt werden, damit erricht man ein Queue Struktur        
        while (!openList.isEmpty()) {
            //Erste Ecke markieren 
            V temp = openList.get(0);

            //Erste Ecke (temp) als bearbeitet markieren ( in die fertige Liste hinzufugen) 
            closedList.add(temp);

            // Nachbarn Ecken addieren am Ende der Liste.. queue strategy
            List<V> nLst =getAllNeigbors(graph, temp);
            Collections.shuffle(nLst);
            openList.addAll(nLst);

            // Die Ecke, die gerade gearbeitet ist, (Erste Element) und die Ecken die schon besucht sind, entfernen.
            openList.removeAll(closedList);

        }
        //Liste alle besuchten Ecken zuruckgeben
        return closedList;
    }

    private List<V> getAllNeigbors(Graph<V, E> g, V v) {
        if (LOG) {
            System.out.println("#########getAllNeigbors-START" + v);
        }

        //Set, welches alle Nachbarn enthalten soll
        List<V> result = new ArrayList<>();

        //Set mit den angrenzenden Kanten der zu bearbeitenden Ecke
        Set<E> edgesOfMainVertex = g.edgesOf(v);

        // Nun wird über alle Ecken des Graphs traversiert
        for (V otherVertex : g.vertexSet()) {
            //Von jeder Ecke werden die angrenzenden Kanten gesammelt
            Set<E> edgesOfOtherVertex = g.edgesOf(otherVertex);
            //Wenn beide Ecken gemeinsame Kanten haben, sind diese Ecken Nachbarn.
            // Deshalb wird die aktuelle Ecke zum Set der Nachbarn hinzugefügt
            for (E edge : edgesOfMainVertex) {
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
