/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package start;

import java.util.*;
import utils.FlowGraph;
import utils.Marks;

/**
 *
 * @author Tobi
 */
public class EdmondsKarp<V, E> {

    private final boolean TRACE = false;
    private final FlowGraph<V, E> graph;
    private final V source;
    private final V target;
    private final Map<V, Marks<V>> markedVertices;
    private final Set<V> inspectedVertices;
    private final Queue<V> verticesToInspect;
    private final double maxFlow;
    

    EdmondsKarp(FlowGraph<V, E> flowGraph, final V source, final V target) {
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
            V v_i = verticesToInspect.remove();
            
            
//            (Vorwärtskante) Für jede Kante e_ij ∈ O(v_i ) mit unmarkierter
//            Ecke v_j und f(e_ij ) < c(e_ij ) markiere v_j mit (+v_i , δ_j ), wobei δ_j
//            die kleinere der beiden Zahlen c(e_ij ) − f(e_ij ) und δ_i ist.
            Set<E> forwardEdges = graph.outgoingEdgesOf(v_i); //O(v_i )
            if (TRACE) System.err.println("Forward: "+forwardEdges);
            for (E e_ij : forwardEdges) { //e_ij ∈ O(v_i )
                V v_j = graph.getEdgeTarget(e_ij); 
                if (!markedVertices.containsKey(v_j)) {//mit unmarkierter Ecke v_j
                    double e_ij_flow = graph.getEdgeFlow(e_ij);
                    double e_ij_capacity = graph.getEdgeCapacity(e_ij);
                    if (e_ij_flow<e_ij_capacity) { //und f(e_ij ) < c(e_ij )
                        //wobei δ_j die kleinere der beiden Zahlen c(e_ij ) − f(e_ij ) und δ_i ist:
                        double deltaJ = Math.min(e_ij_capacity-e_ij_flow, markedVertices.get(v_i).getDelta());
                        //markiere v_j mit (+v_i , δ_j ):
                        markedVertices.put(v_j, new Marks(Marks.FORWARD, v_i, deltaJ));
                        verticesToInspect.add(v_j);
                    }
                }
            }
//            (Rückwärtskante) Für jede Kante e_ji ∈ I(v_i ) mit unmarkierter
//            Ecke v_j und f(e_ji ) > 0 markiere v_j mit (−v_i , δ_j ), wobei δ_j die
//            kleinere der beiden Zahlen f(e_ij ) und δ_i ist.
            Set<E> backwardEdges = graph.incomingEdgesOf(v_i);
            if (TRACE) System.err.println("Backward: "+backwardEdges);//I(v_i )
            for (E e_ij : forwardEdges) { //e_ji ∈ I(v_i )
                V v_j = graph.getEdgeSource(e_ij); 
                if (!markedVertices.containsKey(v_j)) {//mit unmarkierter Ecke v_j
                    double e_ij_flow = graph.getEdgeFlow(e_ij);
                    //double e_ij_capacity = graph.getEdgeCapacity(e_ij);
                    if (e_ij_flow>0.0) { //und f(e_ji ) > 0
                        //wobei δ_j die kleinere der beiden Zahlen f(e_ij ) und δ_i ist
                        double deltaJ = Math.min(e_ij_flow, markedVertices.get(v_i).getDelta());
                        //markiere v_j mit (−v_i , δ_j )
                        markedVertices.put(v_j, new Marks(Marks.BACKWARD, v_i, deltaJ));
                        verticesToInspect.add(v_j);
                    }
                }
            }
            
            inspectedVertices.add(v_i);
            
//            Falls s markiert ist, gehe zu 3., sonst zu 2.(a).
            if (markedVertices.containsKey(target)) {
                //################################
                //#3. Vergrößerung der Flußstärke#
                //################################
                
                V vertex_j = this.target;
                V vertex_i = markedVertices.get(vertex_j).getPrev();
                double delta = markedVertices.get(target).getDelta();
                
                while (vertex_i!=null){
                    Marks jMarks = markedVertices.get(vertex_j);
                    E e = (jMarks.getEdgeDirection()==Marks.FORWARD) ? graph.getEdge(vertex_i, vertex_j) : graph.getEdge(vertex_j, vertex_i);
                    if (TRACE) System.err.println(vertex_j + ": " + jMarks);
                    double oldFlow = graph.getEdgeFlow(e);
                    double newFlow = oldFlow+(jMarks.getEdgeDirection()*delta);
                    graph.setEdgeFlow(e, newFlow);
                    vertex_j=vertex_i;
                    vertex_i=markedVertices.get(vertex_j).getPrev();
                }
                markedVertices.clear();
                inspectedVertices.clear();
                verticesToInspect.clear();
                markedVertices.put(source, new Marks(Marks.FORWARD, null, Double.POSITIVE_INFINITY));
                verticesToInspect.add(source);
            }
        }
        //###########################
        //#4. Kein Vergrößernder Weg#
        //###########################
        if (TRACE) System.err.println(">>>>>>>>>>>>>>KeinVergrößernderWeg");
        double tmpFlow = 0.0;
        for (E edge : graph.outgoingEdgesOf(this.source)) {
            tmpFlow += graph.getEdgeFlow(edge);
        }
        maxFlow = tmpFlow;
        if (TRACE) System.err.println(verticesToInspect);
        if (TRACE) System.err.println(inspectedVertices);
        if (TRACE) System.err.println(markedVertices);
        if (TRACE) System.err.println(graph.getAllFlowsAsString());
    }
    

    public double maxFlow(){
        return maxFlow;
    }
    
    private V tempFehlersuche(){
        for (V v : graph.vertexSet()) {
            if (v.equals("Dortmund")) return v;
        }
        return null;
    }
    
    
//    private class Marks{
//        public static final int FORWARD = 1;
//        public static final int BACKWARD = -1;
//        private int edgeDirection;
//        private V prev;
//        private double delta;
//        
//        public Marks(int edgeDirection,V prev, double delta){
//            if ((edgeDirection!=FORWARD)&&(edgeDirection!=BACKWARD)){
//                throw new Error("edgeDirection has to be 1 or -1");
//            }
//            this.edgeDirection = edgeDirection;
//            this.prev = prev;
//            this.delta = delta;
//        }
//
//        public double getDelta() {
//            return delta;
//        }
//
//        public int getEdgeDirection() {
//            return edgeDirection;
//        }
//
//        public V getPrev() {
//            return prev;
//        }
//
//        public void setDelta(double delta) {
//            this.delta = delta;
//        }
//
//        public void setEdgeDirection(int edgeDirection) {
//            this.edgeDirection = edgeDirection;
//        }
//
//        public void setPrev(V prev) {
//            this.prev = prev;
//        }
//
//        @Override
//        public boolean equals(Object obj) {
//            if (obj == null) {
//                return false;
//            }
//            if (getClass() != obj.getClass()) {
//                return false;
//            }
//            final Marks other = (Marks) obj;
//            if (this.edgeDirection != other.edgeDirection) {
//                return false;
//            }
//            if (!Objects.equals(this.prev, other.prev)) {
//                return false;
//            }
//            if (Double.doubleToLongBits(this.delta) != Double.doubleToLongBits(other.delta)) {
//                return false;
//            }
//            return true;
//        }
//
//        @Override
//        public int hashCode() {
//            int hash = 5;
//            hash = 29 * hash + this.edgeDirection;
//            hash = 29 * hash + Objects.hashCode(this.prev);
//            hash = 29 * hash + (int) (Double.doubleToLongBits(this.delta) ^ (Double.doubleToLongBits(this.delta) >>> 32));
//            return hash;
//        }
//        
//        @Override
//        public String toString(){
//            return String.format("[Markierung: %s%s, %f]", (edgeDirection==FORWARD)?"+":"-",(prev==null)?"null":prev.toString(), delta);
//        }
//    }
}
