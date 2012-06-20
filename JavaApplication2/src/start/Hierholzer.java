/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package start;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedGraph;
import utils.Circle;

/**
 *
 * @author Tobi
 */
public class Hierholzer<V, E extends DefaultWeightedEdge> {

    //Change to true, to show trace information.
    // Helpful to debug or understand algorithm
    private final static boolean TRACE = false;
    
    //Change to true, to ensure, diffrent results, if they exists.
    // False will not ensure to get allways the same result because of the use
    // of Java-Sets (not sorted)
    private final static boolean RANDOMIZE = true;
    
    //graph, which got checked by algorithm
    private final UndirectedGraph<V, E> originalgraph;
    
    //True, if Euler tour exists
    private final boolean hasEulertour;
    
    //eulertour
    private final Circle<V, E> circle;

    public Hierholzer(UndirectedGraph<V, E> g) {
        if (TRACE)System.err.println("    __________\n"
                                    +"___/HIERHOLZER\\___\n"
                                    +"|================|");
        this.originalgraph = g;
        UndirectedGraph<V, DefaultWeightedEdge> workingGraph = new ListenableUndirectedGraph<>(DefaultWeightedEdge.class);

        if (TRACE)System.err.println(">START: Überprüfung, ob Graph eulersch ist.<");

        boolean hE = true;
        //Pruefen, ob es Ecken gibt, die einen ungeraden Eckengrad haben,
        // wenn dies zutrifft, existiert kein Eulerkreis und der Algorithmus
        // funktioniert nicht.
        //Alle Ecken werden zum WorkingGraph hinzugefügt, um einen neuen Graph
        // aufzubauen. Dadurch soll vermieden werden, dass der Algorithmus de-
        // struktiv arbeitet und den Original-Graph verändert.
        for (V vertex : originalgraph.vertexSet()) {
            if (g.edgesOf(vertex).size() % 2 != 0) {
                hE = false;
                if (TRACE) System.err.println("-> Graph ist NICHT eulersch!");
                //throw new Error(String.format("Die Ecke '%s' hat einen ungeraden Eckengrad. Es existiert kein Eulerkreis und der Hierholzer-Algorithmus ist somit nicht anwendbar.", vertex.toString()));
            } else {
                workingGraph.addVertex(vertex);
            }
        }
        if (TRACE && hE) System.err.println("-> Graph ist eulersch!");
        if (TRACE) System.err.println(">ENDE: Überprüfung, ob Graph eulersch ist.<");

        //Seting the instance variable to its final value
        this.hasEulertour = hE;

        //List, that contains all vetrices of the euler tour
        List<V> completeCircle = new ArrayList<>();
        
        //we only need to find an euler tour, if one exists...
        if (hasEulertour) {
            
            if (TRACE) System.err.println(">START: Ermitteln des Eulerkreises.<");

            if (TRACE) System.err.println("-> Vorbereitungen...");
            
            //Alle Kanten werden zum WorkingGraph hinzugefügt, um einen neuen Graph
            // aufzubauen. Dadurch soll vermieden werden, dass der Algorithmus de-
            // struktiv arbeitet und den Original-Graph verändert.
            for (E edge : originalgraph.edgeSet()) {
                V source = originalgraph.getEdgeSource(edge);
                V target = originalgraph.getEdgeTarget(edge);
                workingGraph.addEdge(source, target);
            }
            
            
            //Setting the initilize values for the first run of the while loop
            // inside the following while loop
            
            //start is the edge, where the search for some loop begins
            List<V> vertices = new ArrayList<>(workingGraph.vertexSet());
            if (RANDOMIZE) Collections.shuffle(vertices);
            V start = vertices.get(0);
            
            //current vertex, which will be checked
            V current = start;
            
            //The Neigbor of the befor checked current vertex. In the beginning, it's null
            V neighbor;
            

            if (TRACE) System.err.println("-> Wurden bereits alle Kanten besucht?");
            
            //Solange noch nicht über alle Kanten gelaufen wurde (und diese aus dem
            // Graph gelöscht wurden) wird weiter nach Kreisen gesucht.
            //Wurde über alle Kanten gelaufen, so wurde eine EulerTour gefunden.
            while (!workingGraph.edgeSet().isEmpty()) {
                if (TRACE) System.err.println("--> NEIN!");
                
                //list that will contain the "sub-circle"
                List<V> innerCircle = new ArrayList<>();
                innerCircle.add(start);
                
                if (TRACE) System.err.println("--->START: Ermittlen eines 'Unterkreises'<");
                if (TRACE) System.err.println("----> Startecke für nächsten 'Unterkreis': " + start);
                
                //Diese Schleife läuft so lange, bis ein Kreis gefunden wurde, mindestens aber einmal.
                // Alle Ecken, die zu dem Kreis gehören, werden in die Liste
                // circle eingefügt.
                do {
                    if (TRACE) System.err.println("-----> Ecke, die nun unterucht wird: " + current);
                    
                    //Get any edge of the current vertex, to analyze.
                    ArrayList<DefaultWeightedEdge> edges =new ArrayList<>(workingGraph.edgesOf(current));
                    if (RANDOMIZE) Collections.shuffle(edges);
                    DefaultWeightedEdge edge = edges.get(0);
                    
                    //Get the vertex, which adjacent withe current vertex by the edge (neighbor)
                    V source = workingGraph.getEdgeSource(edge);
                    V target = workingGraph.getEdgeTarget(edge);
                    if (current.equals(source)) {
                        neighbor = target;
                    } else {
                        neighbor = source;
                    }
                    
                    //Remove the edge from the working graph, because the edge is allready used 
                    workingGraph.removeEdge(edge);
                    
                    //Add the neighbor to the inner cicle
                    innerCircle.add(neighbor);
                    
                    //set current for the next run of this while-loop
                    current = neighbor;
                    if (TRACE) System.err.println("-----> Gefunde Nachbarecke:: " + neighbor);
                    
                } while (!start.equals(neighbor));
                
                if (TRACE) System.err.println("--->ENDE: Ermittlen eines 'Unterkreises'<");
                if (TRACE) System.err.println("--->START: Zusammmenfügen der Kreise<");
                if (TRACE) System.err.println("----> Aktuelle Eulerkreis-Ecken: " + completeCircle);
                if (TRACE) System.err.println("----> Aktuelle 'Unterkreis'-Ecken: " + innerCircle);
                
                //Insert inner circle in the circle for the euler tour. If the euler tour circle is empty, the
                // inner circle equates to the current euler tour circle state
                if (completeCircle.isEmpty()) {
                    completeCircle.addAll(innerCircle);
                } else {
                    int i = completeCircle.indexOf(start);
                    completeCircle.remove(i);
                    completeCircle.addAll(i, innerCircle);
                }

                if (TRACE) System.err.println("----> Neue Eulerkreis-Ecken: " + completeCircle);
                if (TRACE) System.err.println("---> Ende: Zusammmenfügen der Kreise");
                if (TRACE) System.err.println("-> Wurden bereits alle Kanten besucht? ");

                //Search for the first vertex with a degree != 0 ( <=> >0, beacause degree is never negative)
                // to start with this edge for the next inner-cirle search.
                Iterator<V> it = completeCircle.iterator();
                boolean vertexDegreeNEzeroFound = false;
                while (it.hasNext() && !vertexDegreeNEzeroFound) {
                    V vertex = it.next();
                    if (!workingGraph.edgesOf(vertex).isEmpty()) {
                        start = vertex;
                        current = vertex;
                        vertexDegreeNEzeroFound = true;
                    }
                }
            }
            
            if (TRACE) System.err.println("--> JA!");
            if (TRACE) System.err.println("-> Eckenliste für Eulerkreis: " + completeCircle);
            if (TRACE) System.err.println(">ENDE: Ermitteln des Eulerkreises.<");
            
        }
        
        if (TRACE) System.err.println(">START: Kreis-Pfad erzeugen.<");
        if (TRACE) System.err.println("->START: Kanten ermitteln.<");

        //collect edges of the euler tour (based on the vertices)
        List<E> edges = new ArrayList<>();
        for (int i = 0; i < completeCircle.size() - 1; i++) {
            edges.add(originalgraph.getEdge(completeCircle.get(i), completeCircle.get(i + 1)));
        }
        
        if (TRACE) System.err.println("--> Kanten des Eulerkreises: " + edges);
        
        //get start vertex of the euler tour, if a tour exists
        V v;
        if (completeCircle.isEmpty()) {
            v = null;
        } else {
            v = completeCircle.get(0);
        }

        //create a new circle-object for the instance-variable
        this.circle = new Circle<>(originalgraph, v, edges);
        
        if (TRACE) System.err.println(">ENDE: Kreis-Pfad erzeugen.<");
    }

    public boolean hasEulertour() {
        return hasEulertour;
    }

    public Circle getEulerCircle() {
        return this.circle;
    }

    @Override
    public String toString() {
        String result;
        if (!hasEulertour) {
            result = "No Euler-Tour found!";
        } else {
            result = String.format("Euler-Tour={start/end: %s; edges: %s}", circle.getStartEndVertex().toString(), circle.getEdgeList().toString());
        }
        return result;
    }
}
