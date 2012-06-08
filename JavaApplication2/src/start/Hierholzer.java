/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package start;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jgrapht.GraphPath;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.GraphPathImpl;
import org.jgrapht.graph.ListenableUndirectedGraph;

/**
 *
 * @author Tobi
 */
public class Hierholzer<V, E extends DefaultWeightedEdge> {
    
    private final static boolean TRACE = true;
    private final UndirectedGraph<V, E> originalgraph;
    private final boolean hasEulertour;
    private final GraphPath<V, E> path;
    
    
    public Hierholzer(UndirectedGraph<V, E> g){
        
        this.originalgraph = g;
        UndirectedGraph<V, DefaultWeightedEdge> workingGraph = new ListenableUndirectedGraph<>(DefaultWeightedEdge.class);

        if (TRACE) System.err.println(">START: Überprüfung, ob Graph eulersch ist. <");
        
        boolean hE = true;
        //Pruefen, ob es Ecken gibt, die einen ungeraden Eckengrad haben,
        // wenn dies zutrifft, existiert kein Eulerkreis und der Algorithmus
        // funktioniert nicht.
        //Alle Ecken werden zum WorkingGraph hinzugefügt, um einen neuen Graph
        // aufzubauen. Dadurch soll vermieden werden, dass der Algorithmus de-
        // struktiv arbeitet und den Original-Graph verändert.
        for (V vertex : originalgraph.vertexSet()) {
            if (g.edgesOf(vertex).size()%2!=0) {
                hE = false;
                if (TRACE) System.err.println("-> Graph ist NICHT eulersch!");
                throw new Error(String.format("Die Ecke '%s' hat einen ungeraden Eckengrad. Es existiert kein Eulerkreis und der Hierholzer-Algorithmus ist somit nicht anwendbar.", vertex.toString()));
            } else {
                workingGraph.addVertex(vertex);
            }
        }
        if (TRACE && hE) System.err.println("-> Graph ist eulersch!");
        if (TRACE) System.err.println(">ENDE: Überprüfung, ob Graph eulersch ist. <");
        
        this.hasEulertour=hE;
        
        List<V> completeCircle = new ArrayList<>();
        
        if (TRACE) System.err.println(">START: Ermitteln des Eulerkreises. <");
        if (hasEulertour){
            
            if (TRACE) System.err.println("-> Vorbereitungen...");
            //Alle Kanten werden zum WorkingGraph hinzugefügt, um einen neuen Graph
            // aufzubauen. Dadurch soll vermieden werden, dass der Algorithmus de-
            // struktiv arbeitet und den Original-Graph verändert.
            for (E edge : originalgraph.edgeSet()) {
                V source = originalgraph.getEdgeSource(edge);
                V target = originalgraph.getEdgeTarget(edge);
                workingGraph.addEdge(source, target);
            }

            
            V start = new ArrayList<>(workingGraph.vertexSet()).get(0);
            V neighbor = null;
            V current = start;
            
            
            
            if (TRACE) System.err.println("-> Wurden bereits alle Kanten besucht? ");        
            //Solange noch nicht über alle Kanten gelaufen wurde (und diese aus dem
            // Graph gelöscht wurden) wird weiter nach Kreisen gesucht.
            //Wurde über alle Kanten gelaufen, so wurde eine EulerTour gefunden.
            while (!workingGraph.edgeSet().isEmpty()) {
                if (TRACE) System.err.println("--> NEIN!");
                List<V> innerCircle = new ArrayList<>();
                innerCircle.add(start);
                //Diese Schleife läuft so lange, bis ein Kreis gefunden wurde.
                // Alle Ecken, die zu dem Kreis gehören, werden in die Liste
                // circle eingefügt.
                if (TRACE) System.err.println("--->START: Ermittlen eines 'Unterkreises'");
                if (TRACE) System.err.println("----> Startecke für nächsten 'Unterkreis': "+start);
                while(!start.equals(neighbor)){
                    if (TRACE) System.err.println("-----> Ecke, die nun unterucht wird: "+current);
                    DefaultWeightedEdge edge = new ArrayList<>(workingGraph.edgesOf(current)).get(0);
                    V source = workingGraph.getEdgeSource(edge);
                    V target = workingGraph.getEdgeTarget(edge);
                    if (current.equals(source)) {
                        neighbor = target;
                    } else {
                        neighbor = source;
                    }
                    workingGraph.removeEdge(edge);
                    innerCircle.add(neighbor);
                    current=neighbor;
                    if (TRACE) System.err.println("-----> Gefunde Nachbarecke:: "+neighbor);
                }
                if (TRACE) System.err.println("--->ENDE: Ermittlen eines 'Unterkreises'");
                if (TRACE) System.err.println("--->START: Zusammmenfügen der Kreise");
                if (TRACE) System.err.println("----> Aktuelle Eulerkreis-Ecken: "+ completeCircle);
                if (TRACE) System.err.println("----> Aktuelle 'Unterkreis'-Ecken: "+ innerCircle);
                if (completeCircle.isEmpty()){
                    completeCircle.addAll(innerCircle);
                } else{
                    int i = completeCircle.indexOf(start);
                    completeCircle.remove(i);
                    completeCircle.addAll(i, innerCircle);
                }
                
                if (TRACE) System.err.println("----> Neue Eulerkreis-Ecken: "+ completeCircle);
                if (TRACE) System.err.println("---> Ende: Zusammmenfügen der Kreise");
                if (TRACE) System.err.println("-> Wurden bereits alle Kanten besucht? ");  

                Iterator<V> it = completeCircle.iterator();
                boolean vertexDegreeNEzeroFound = false;
                while (it.hasNext() && !vertexDegreeNEzeroFound){
                    V vertex = it.next();
                    if (!workingGraph.edgesOf(vertex).isEmpty()){
                        start = vertex;
                        current = vertex;
                        vertexDegreeNEzeroFound = false;
                    }
                }
            }
            if (TRACE) System.err.println("--> JA!");
            if (TRACE) System.err.println("-> Eckenliste für Eulerkreis: "+completeCircle);
            if (TRACE) System.err.println(">ENDE: Ermitteln des Eulerkreises. <");
        }
        if (TRACE) System.err.println(">START: Pfad erzeugen. <");
        if (TRACE) System.err.println("-> START: Kanten ermitteln. <");
        
        List<E> edges = new ArrayList<>();
        for (int i = 0; i < completeCircle.size()-1; i++) {
            edges.add(originalgraph.getEdge(completeCircle.get(i), completeCircle.get(i+1)));
        }
        if (TRACE) System.err.println("--> Kanten des Eulerkreises: "+ completeCircle);
        V v;
        if (completeCircle.isEmpty()){
            v = null;
        } else {
            v = completeCircle.get(0);
        }
        
        this.path= new GraphPathImpl(originalgraph, v, v, edges, 0);
        if (TRACE) System.err.println(">START: Pfad erzeugen. <");
    }
    
    public boolean hasEulertour(){
        return hasEulertour;
    }
    
    public GraphPath getEulerCircle(){
        return this.path;
    }
    
}
