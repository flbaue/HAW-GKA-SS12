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
                
                if (i==j){          //0 für i = j
                    distanz.set(i, j, 0);
                } else {
                    //Vertex zu i suchen
                    V jVertex = id_vertex.get(j);
                    
                    //Edge zwischen i und j auslesen
                    E ijEdge = graph.getEdge(iVertex, jVertex);
                    
                    if (ijEdge!=null){      //lij für vi_vj Element von E und i != j
                        distanz.set(i, j, graph.getEdgeWeight(ijEdge));
                    } else {
                        distanz.set(i,j,Double.POSITIVE_INFINITY);
                    }
                }
                
            }
        }
        //System.out.println("Vertices:\n"+id_vertex+"\nDistanzmatrix:\n"+distanz+"\nTransitmatrix:\n"+transit);
        
        
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
                            
                            double originalDik = distanz.get(i, k);
                            double dij = distanz.get(i, j);
                            double djk = distanz.get(j, k);
                            double alternativeDik = dij+djk;


                            //Setze dik := min(dik, dij+djk)
                            double newDik = Math.min(originalDik, alternativeDik);
                            distanz.set(i, k,newDik);
                            
                            //Falls dik verändert wurde, setze tik := j
                            //if (originalDik!=distanz.get(i, k)) {
                            if (originalDik>newDik){
                                transit.set(i, k, j);
                            }

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
        
    }
    
    //Methode zum Abfragen des Pfads zwischen Start und Ziel
    public GraphPath getShortestPath(V start, V end){
        
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
            List<V> pathVertices;
            
            //Der Pfad zwischen Start und Ziel Ecke wird berechnet
            pathVertices=calcPathVertices(start, end);
        
            //Zu den Ecken des kürzesten Weges werden die zugehöriigen Kanten gesucht
            for (int i = 0; i < pathVertices.size()-1; i++) {
                E edge = graph.getEdge(pathVertices.get(i), pathVertices.get(i+1));

                pathEdges.add(edge);
            }
            //GraphPath Objekt erzeugen
            result = new GraphPathImpl(graph, start, end, pathEdges, getShortestDistance(start,end));

        }
        //GraphPath als Ergebnis zurückliefern
        return result;
    }
    
    
    //Diese Methide ermittelt alle Ecken, die zwischen Start und End auf dem kürzesten Weg liegen
    private List<V> calcPathVertices(V start, V end){
        List<V> result = new ArrayList<>();
        result.add(start);
        result.add(end);
        result=calcPathVertices(result);
        return result;
    }
    
    //Diese Methide ermittelt alle Ecken des kürzesten Weges zwischen allen aufeinanderfolgenden Ecken der Liste
    private List<V> calcPathVertices(List<V> list){  
        if (list.size()>2) throw new Error("List is to short. Need at least two elements.");
        List<V> result = new ArrayList<>(list);

        int  index = 0;
        while (index<result.size()-1) {
            int start=vertex_id.get(result.get(index));
            int end=vertex_id.get(result.get(index+1));
            int middle=transit.get(start, end);
            if (middle!=-1){
                result.add(index+1,id_vertex.get(middle));
            } else {
               index++; 
            }
        }
        return result;
    }
    
    
    //Auslesen der Distanz zwischen start und end
    public double getShortestDistance(V start, V end){
        checkVertex(start);
        checkVertex(end);
        return distanz.get(vertex_id.get(start), vertex_id.get(end));
    }
    
    //ID zur zugehörigen Vertex auslesen
    private int getIdOfVertex(V vertex){
        checkVertex(vertex);
        return vertex_id.get(vertex);
    }
    
    //Vertex zur zugehörigen ID auslesen
    private V getVertexOfId(int id){
        checkId(id);
        return id_vertex.get(id);
    }
    
    private void checkVertex(V vertex){
        if (!vertex_id.containsKey(vertex)){
            throw new Error("Vertex "+vertex+" is invalid");
        }
    }
    
     private void checkId(int id){
        if (!id_vertex.containsKey(id)){
            throw new Error("ID "+id+" is invalid");
        }
    }
}
