/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package start;

import java.util.*;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.GraphPathImpl;
import utils.DoubleSquareMatrix;
import utils.IntSquareMatrix;

/**
 *
 * @author Tobi
 */
public class FloydWarshall<V, E> {
    //Map, welche die int-Werte aus den Matritzen auf die eigentlichen Ecken mappt
    final Map<Integer, V> id_vertex = new HashMap<>();
    
    //Map, welche die Ecken zu den IDs mappt
    final Map<V, Integer> vertex_id = new HashMap<>();
    
    //Anzahl der Vertices, wird seperat gespeichert, da mehrfach benötigt, v = |V|
    final int v;
    
    //Distanz- und Transitmatrix
    final IntSquareMatrix transit;
    final DoubleSquareMatrix distanz;
    
    final Graph<V, E> graph;
    
    public FloydWarshall(Graph<V, E> g){
        
        //################
        //# VORBEREITUNG #
        //################
        
        //
        this.graph=g;
        
        //Füllen der id_vertex-Map
        int x=0;
        for (V vertex : graph.vertexSet()) {
            id_vertex.put(x, vertex);
            vertex_id.put(vertex, x++);
        }
        
        //Anzahl der
        v=id_vertex.size();
        
        //Erzeugen von Distanz- und Transitmatrix
        distanz = new DoubleSquareMatrix(id_vertex.size());
        transit = new IntSquareMatrix(id_vertex.size(),-1);
        
        //Füllen der Distanzmatrix
        for (int i = 0; i < v; i++) {
            //Vertex zu i suchen
            V iVertex = id_vertex.get(i);
            for (int j = 0; j < v; j++) {
                
                if (i==j){                                  //0 für i = j
                    distanz.set(i, j, 0);
                } else {
                    //Vertex zu i suchen
                    V jVertex = id_vertex.get(j);
                    
                    //Edge zwischen i und j auslesen
                    E ijEdge = graph.getEdge(iVertex, jVertex);
                    
                    if (ijEdge!=null){                      //lij für vi_vj Element von E und i != j
                        distanz.set(i, j, graph.getEdgeWeight(ijEdge));
                    } else {
                        distanz.set(i,j,Integer.MAX_VALUE);
                    }
                }
                
            }
        }
//        System.out.println("Vertices:\n"+id_vertex+"\nDistanzmatrix:\n"+distanz+"\nTransitmatrix:\n"+transit);
        
        
        //###############
        //# ALGORITHMUS #
        //###############
        
        //Für j = 0, ..., |V|-1 
        for (int j = 0; j < v; j++) {
            
            //Für i = 0, ..., |V|-1 
             for (int i = 0; i < v; i++) {
                
                // i != j
                if (i!=j) {
                     for (int k = 0; k < v; k++) {
                         
                        // k != j
                        if (k!=j){
                            System.out.println("Hier mal Werte für Konkrete Problemecken (if k = ? und i = ? und j = ?, then...) ausgeben lassen. Schlägt eventuell beim min etwas fehl?");
                            double oldDik = distanz.get(i, k);
                            
                            //Setze dik := min(dik, dij+djk)
                            distanz.set(i, k,Math.min(oldDik, distanz.get(i, j)+distanz.get(j, k)));
                            
                            //Falls dik verändert wurde, setze tik := j
                            if (oldDik!=distanz.get(i, k)) {
                                transit.set(i, k, j);
                            }
//                            System.err.println("j="+j+"; i="+i+"; k="+k+"\nVertices:\n"+id_vertex+"\nDistanzmatrix:\n"+distanz+"\nTransitmatrix:\n"+transit);
//                            System.err.println(oldDik);
//                            System.err.println(distanz.get(i, k));
                        } else {
                            //k =j, also do nothing
                        }
                        
                    }
                     
                    //Falls dij < 0 ist, brich den Algorithmuss vorzeitig ab. (Es wurde ein
                    // Kreis negativer Länge gefunden.
                    if (distanz.get(i, i)<0.0d){
                        throw new Error("Negative circle found for Vertex "+id_vertex.get(i));
                    }
                } else {
                    //i = j, also do nothing
                }
             }
        }
//        System.out.println("Vertices:\n"+id_vertex+"\nDistanzmatrix:\n"+distanz+"\nTransitmatrix:\n"+transit);
        
     
        
    }
    
    public GraphPath getShortestPath(V start, V end){
        System.out.println("Vertices:\n"+id_vertex+/**"\nDistanzmatrix:\n"+distanz+"**/"\nTransitmatrix:\n"+transit);

        checkVertex(start);
        checkVertex(end);
        
        
        //Rückgabewert
        GraphPath result;
        
        //Kanten, die auf dem Weg abgelaufen werden
        List<E> pathEdges = new LinkedList<>();
        
        
        if (start.equals(end)) {//Trivialfall, wenn Start = Ende
            //GraphPath Objekt erzeugen
            //result = new GraphPathImpl(graph, start, end,pathEdges, getShortestDistance(start,end));
            result = null;
        }else{
            
            //Ecken, die auf dem Weg abgelaufen werden
            List<V> pathVertices = new ArrayList<>();
            
            //Start und Ziel hinzufügen
            pathVertices.add(start);
            pathVertices.add(end);
            
            
            //IDs von Start- und Ziel-Ecke für Transitmatrix-Suche ermitteln
            int startVertexID=vertex_id.get(start);
            int endVertexId=vertex_id.get(end);
            
            //ID für die als nächste zu untersuchende Ecke ermittln
            int currentV = transit.get(startVertexID, endVertexId);
//            System.out.println(id_vertex);
//            System.out.println(transit);
//            System.out.println(pathVertices);
//            System.out.println(currentV);
            //Solange noch nicht alle Ecken emittelt...
            while(currentV!=-1){
                V elem = id_vertex.get(currentV);
//                System.out.println(elem);
                //... wird die aktuelle Ecke zum Pfad hinzugefügt...
                pathVertices.add(1, elem);
//                System.out.println(pathVertices);
                //...und currentV = der nächsten zu untersuchenden Ecke gesetzt.
                currentV = transit.get(startVertexID,vertex_id.get(elem));
//                System.out.println("currentID: "+currentV+" Vertex: "+elem);
               
            }
            
            //System.out.println("Vertices:\n"+id_vertex+"\nDistanzmatrix:\n"+distanz+"\nTransitmatrix:\n"+transit);
        
            System.out.println(pathVertices);
            //Zu den Ecken des kürzesten Weges werden die zugehöriigen Kanten gesucht
            for (int i = 0; i < pathVertices.size()-1; i++) {
                pathEdges.add(graph.getEdge(pathVertices.get(i), pathVertices.get(i+1)));
            }
            //GraphPath Objekt erzeugen
            result = new GraphPathImpl(graph, start, end, pathEdges, getShortestDistance(start,end));

        }
        //GraphPath als Ergebnis zurückliefern
        return result;
    }
    
    public double getShortestDistance(V start, V end){
        checkVertex(start);
        checkVertex(end);
        return distanz.get(vertex_id.get(start), vertex_id.get(end));
    }
    
    private int getIDofVertex(V vertex){
        checkVertex(vertex);
        int result = -1;
        for (Map.Entry<Integer, V> entry : id_vertex.entrySet()) {
            if (entry.getValue().equals(vertex)){
                result=entry.getKey();
            }  
        }
        return result;
    }
    
    private void checkVertex(V vertex){
        if (!vertex_id.containsKey(vertex)){
            throw new Error("Vertex "+vertex+" is invalid");
        }
    }
}
