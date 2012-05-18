/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package start;

import java.util.*;
import org.jgrapht.graph.DefaultWeightedEdge;
import utils.DefaultDirectedWeightedFlowGraph;
import utils.FlowGraph;

/**
 *
 * @author Tobi
 */
public class EdmondsKarp<V, E> {

    private final FlowGraph<V, E> graph;
    private final V source;
    private final V target;
    private final Map<V, Marks> markedVertices;
    private final Set<V> inspectedVertices;
    private final Queue<V> verticesToInspect;
    

    EdmondsKarp(FlowGraph<V, E> flowGraph, V source, V target) {
        this.graph=flowGraph;
        this.source=source;
        this.target=target;
        
        this.markedVertices = new HashMap<>();
        this.verticesToInspect = new LinkedList<>();
        this.inspectedVertices = new HashSet();
        
        
        //####################
        //#1. Initialisierung#
        //####################
        
//        Weise allen Kanten f(e_ij ) als einen (initialen) Wert zu, der die Neben-
//        bedingungen 5 erfüllt. .
        graph.resetAllFlowsTo(0.0);
//        Markiere q mit (undeﬁniert, ∞)
        markedVertices.put(source, new Marks(Marks.FORWARD, null, Double.POSITIVE_INFINITY));
        verticesToInspect.add(source);
        
        
        //##############################
        //#2. Inspektion und Markierung#
        //##############################
        
//        (a) Falls alle markierten Ecken inspiziert wurden, gehe nach 4.
        while(!inspectedVertices.containsAll(markedVertices.keySet())){
//            (b) Wähle aus der Queue die markierte, aber noch nicht inspizierte Ecke v_i
//                und inspiziere sie wie folgt (Berechnung des Inkrements)
            V currentVertex = verticesToInspect.remove();
            
            
            
            
            inspectedVertices.add(currentVertex);
        }
        //###########################
        //#4. Kein Vergrößernder Weg#
        //###########################
    }
    
    private Set<E> output(V v){
        return graph.edgesOf(v);
    }
    
    
    
    private class Marks{
        public static final int FORWARD = 1;
        public static final int BACKWARD = -1;
        private int edgeDirection;
        private V prev;
        private double delta;
        
        public Marks(int edgeDirection,V prev, double delta){
            if ((edgeDirection!=FORWARD)&&(edgeDirection!=BACKWARD)){
                throw new Error("edgeDirection has to be 1 or -1");
            }
            this.edgeDirection = edgeDirection;
            this.prev = prev;
            this.delta = delta;
        }

        public double getDelta() {
            return delta;
        }

        public int getEdgeDirection() {
            return edgeDirection;
        }

        public V getPrev() {
            return prev;
        }

        public void setDelta(double delta) {
            this.delta = delta;
        }

        public void setEdgeDirection(int edgeDirection) {
            this.edgeDirection = edgeDirection;
        }

        public void setPrev(V prev) {
            this.prev = prev;
        }
        
        
    }
}
