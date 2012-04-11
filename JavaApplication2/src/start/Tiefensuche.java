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
public class Tiefensuche<V, E> {
    //=true setzten, um erweiterte Infos auf der Konsole auszugeben
    private final static boolean LOG = false;
    private final static Boolean FALSE = Boolean.FALSE;
    private final static Boolean TRUE = Boolean.TRUE;
    
    //Liste mit allen Ecken, in der Reienfolge, wie sie besucht wurden
    private List<V> vertexList;
    private final String output;
            
    public Tiefensuche(Graph<V,E> graph){
        if (LOG) System.out.println("##TIEFENSUCHE##");
        
        //Diese Liste enthält alle Ecken in genau der Reihenfolge, in der sie vom Algorithmus abgelaufen werden
        vertexList = new ArrayList<>();
        
        //Anzahl der Zusammenhangskomponenten
        // Nice to have. Ist nicht erforderlich, lässt sich aufgrund der Implementierung aber leicht ermitteln.
        int komponenten = 0;
        
        //Map, welche alle Ecken enhält und speichert, ob diese bereits besucht wurden (true) oder nicht (false)
        //Map<Vertex, Boolean> visited = new HashMap<>();
        
        //Es wird über alle Ecken des Graphs iterriert
        for (V v : graph.vertexSet()) {
            
            //Wenn eine Ecke noch nicht in der vertexList steht, dann wird diese als Start-Ecke für
            // den Algorithmus verwendet. Dies ist nur dann der Fall, wenn eine neue Zusammenhangs-
            // komponente des Graph abgearbeitet wird.
            if (!vertexList.contains(v)) {
                Stack<V> tmpStack = new Stack<>();
                tmpStack.add(v);
                if (LOG) System.out.println(v);
                vertexList=dfs(tmpStack,vertexList, graph );
                if (LOG) System.out.println(vertexList.size());
                komponenten++;
            } else {
                //Ist die Ecke bereits in der Ergebnisliste, so muss diese Ecke nicht erneut für
                // den Start des Algorithmus verwendet werden
            }
        }
        
        //Hier wird die Ausgabe für toString zusammengebastelt. Dies passiert im Konstruktor, da
        // diese Klasse immutable ist und sich die Ausgabe im nachhinein nicht mehr ändert.
        int counter = 1;
        StringBuilder sb = new StringBuilder("Tiefensuche! \nDer Graph besteht aus ");
        sb.append(Integer.toString(komponenten));
        sb.append(" Komponenten.\n");
        String nl = "\n";
        String x = "- ";
        for (V vertex : vertexList) {
            sb.append(String.format("%2d ", counter++));
            sb.append(x);
            sb.append(vertex);
            sb.append(nl);
        }
        output = sb.toString();
        
    };
    
    /**
     * Rekursive Methode, welche den eigentlichen Tiefensuch-Algoithmus enthält
     * 
     * @param s: Stack, welcher die noch abzuarbeitenden noch zu besuchenden Ecken enthält
     * @param l: Liste mit besuchten Ecken, in genau der Reihenfolge, in der sie besucht wurden
     * @param g: Graph, der traversiert werden soll
     */
    private List<V> dfs(Stack<V> s, List<V> l, Graph<V,E> g){
        
        // Liste mit den besuchten Ecken
        List<V> result;
        
        if (LOG) System.out.println("Stack:   "+s);
        if (LOG) System.out.println("Besucht: "+l);
        
        if (s.empty()) {
            //Wenn der Stack leer ist, müssen keine weiteren Ecken bearbeitet werden
            // Die Liste l enthält somit das Ergebnis und wird zurück gegeben
            result = l;
        } else {
            //Die oberste Ecke wird vom Stack genommen. Von dieser Ecke aus werden die weiteren
            // Möglichkeiten zur Traversierung untersucht
            V v=s.pop();
            //Diese Ecke wurde nun Besucht und wird somit der Ergebnisliste hinzugefügt.
            l.add(v);
            //Alle Nachbarn der aktuellen Liste werde auf den Stack gepackt
            List<V> nList = getAllNeigbors(g,v);
            Collections.shuffle(nList);
            s.addAll(nList);
            
            //Nun werden alle Ecken, die bereits besucht wurden vom Stack gelöscht, damit diese
            // nicht erneut bearbeitet werden.
            // Das ist nicht üblich für Stacks, da man auch mitten heraus Elemente löscht und nicht 
            // nur von oben. Dies wirkt sich jedoch lediglich auf die Reihenfolge der abbarbeiteung
            // der Ecken aus. Alternativ könnte man aus dem Nachbar-Set nSet alle Elemente, die
            // auf dem Stack liegen, und alle Elemente, die in der Ergebnisliste liegen, etfernen.
            //So hat man jedoch eine Operation weniger.
            s.removeAll(l);
            
            //Rekursiver Aufruf der Methode mit aktuellem Stack und Liste, sowie dem Graphen
            result = dfs(s, l, g);
        }
        
        return result;
    }
    
    private List<V> getAllNeigbors(Graph<V,E> g, V v){
        if (LOG) System.out.println("#########getAllNeigbors-START" + v);
        
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
                if (edgesOfOtherVertex.contains(edge)){
                    result.add(otherVertex);
                }
            }
        }
                
        //Eine Ecke ist nicht mit sich selbst benachbart und wird deshalb entfernt
        result.remove(v);
        if (LOG) System.out.println(v + " hat folgende Nachbarn: " +result);
        if (LOG) System.out.println("#########getAllNeigbors-End");
        return result;
    }

    @Override
    public String toString(){
        return output;
    }
    
    public List<V> vertices(){
        return new ArrayList(vertexList);
    }
}
