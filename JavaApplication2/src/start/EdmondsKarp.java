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
    private final double maxFlow;
    private final Set<V> a;
    private final Set<V> aComplement;
    private final Set<E> minCut;
    private final Set<E> minCutOutgoing;
    private final Set<E> minCutIncomming;
    private final double minCutCapacity;
    

    EdmondsKarp(FlowGraph<V, E> flowGraph, final V source, final V target) {
        this.graph=flowGraph;
        this.source=source;
        this.target=target;
        
        final Map<V, Marks<V>> markedVertices = new HashMap<>();
        final Queue<V> verticesToInspect = new LinkedList<>();
        final Set<V> inspectedVertices = new HashSet();
        
        
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
            if (TRACE) System.err.println(String.format("Inspecting %s:", verticesToInspect.element().toString()));
//            (b) Wähle aus der Queue die markierte, aber noch nicht inspizierte Ecke v_i
//                und inspiziere sie wie folgt (Berechnung des Inkrements)
            V v_i = verticesToInspect.remove();
            
            
//            (Vorwärtskante) Für jede Kante e_ij ∈ O(v_i ) mit unmarkierter
//            Ecke v_j und f(e_ij ) < c(e_ij ) markiere v_j mit (+v_i , δ_j ), wobei δ_j
//            die kleinere der beiden Zahlen c(e_ij ) − f(e_ij ) und δ_i ist.
            Set<E> forwardEdges = graph.outgoingEdgesOf(v_i); //O(v_i )
            if (TRACE) System.err.println(" Forward: "+forwardEdges);
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
            if (TRACE) System.err.println(" Backward: "+backwardEdges);//I(v_i )
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
                if (TRACE) System.err.println(String.format("Change flow about %f",delta));
                
                while (vertex_i!=null){
                    Marks jMarks = markedVertices.get(vertex_j);
                    E e = (jMarks.getEdgeDirection()==Marks.FORWARD) ? graph.getEdge(vertex_i, vertex_j) : graph.getEdge(vertex_j, vertex_i);
                    if (TRACE) System.err.println(String.format(" %s: %s",vertex_j.toString(),jMarks.toString()));
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
        if (TRACE) System.err.println("####Kein Vergrößernder Weg! Ende!####");
        double tmpFlow = 0.0;
        
        //Setzen von Instanz-Variablen
        
        // maxFlow:
        for (E edge : graph.outgoingEdgesOf(this.source)) {
            tmpFlow += graph.getEdgeFlow(edge);
        }
        this.maxFlow = tmpFlow;
        
        // a:
        this.a = new HashSet<>(markedVertices.keySet());

        // aComplement:
        this.aComplement = new HashSet<>(graph.vertexSet());
        this.aComplement.removeAll(a);
        
        // minCutIncomming
        this.minCutIncomming = calcMinCutIncoming();
        
        // minCutIncomming
        this.minCutOutgoing = calcMinCutOutgoing();
        
        // minCut:
        this.minCut = calcMinCut();
        
        // minCutCapacity
        this.minCutCapacity = calcMinCutCapacity();
        
        //Berechnung von MaxFlow, basierend auf dem minimalen Schnitt (dient eher der Kontrolle)
        double maxFlowOfCut = calcMaxFlowBasedOnMinCut();
        
        isMaxFlowMinCutTheoremfulfilled();
        
        if (TRACE) System.err.println(" Zu inspizierende Ecken: "+verticesToInspect);
        if (TRACE) System.err.println(" Inspizierte Ecken: "+inspectedVertices);
        if (TRACE) System.err.println(" Markierte Ecken: "+markedVertices);
        if (TRACE) System.err.println(" Alle Fluss-Werte: "+graph.getAllFlowsAsString());
        if (TRACE) System.err.println(" MinCut: " + minCut);
        if (TRACE) System.err.println(" MinCutOutgoing: " + minCutOutgoing);
        if (TRACE) System.err.println(" MinCutIncoming: " + minCutIncomming);
        if (TRACE) System.err.println(" MinCutCapacity: "+ minCutCapacity);
        if (TRACE) System.err.println(" MaxFlow, calculated based on source outgoing flow: "+ maxFlow);
        if (TRACE) System.err.println(" MinFlow, calculated based on minCut: "+maxFlowOfCut);
    }
    

    public double getMaxFlow(){
        return maxFlow;
    }

    public Set<V> getA() {
        return new HashSet<>(a);
    }

    public Set<V> getaComplement() {
        return new HashSet<>(aComplement);
    }

    public Set<E> getMinCut() {
        return new HashSet<>(minCut);
    }

    public double getMinCutCapacity() {
        return minCutCapacity;
    }

    public Set<E> getMinCutIncomming() {
        return new HashSet<>(minCutIncomming);
    }

    public Set<E> getMinCutOutgoing() {
        return new HashSet<>(minCutOutgoing);
    }

    public V getSource() {
        return source;
    }

    public V getTarget() {
        return target;
    }

    private Set<E> calcMinCut() {
        Set<E> result = new HashSet<>(minCutOutgoing);
        result.addAll(minCutIncomming);
        return result;
    }

    private Set<E> calcMinCutIncoming() {
        Set<E> result = new HashSet<>();
        for (V v1 : a) {
            Set<E> v1In = new HashSet<>(graph.incomingEdgesOf(v1));
            for (V v2 : aComplement) {
                Set<E> v2Out = new HashSet<>(graph.outgoingEdgesOf(v2));
                v2Out.retainAll(v1In);
                result.addAll(v2Out);
            }
        }
        return result;
    }

    private Set<E> calcMinCutOutgoing() {
        Set<E> result = new HashSet<>();
        for (V v1 : a) {
            Set<E> v1Out = new HashSet<>(graph.outgoingEdgesOf(v1));
            for (V v2 : aComplement) {
                Set<E> v2In = new HashSet<>(graph.incomingEdgesOf(v2));
                v2In.retainAll(v1Out);
                result.addAll(v2In);
            }
        }
        return result;
    }

    private double calcMinCutCapacity() {
        double result = 0.0;
        for (E e : minCutOutgoing) {
            result+= graph.getEdgeCapacity(e);
        }
        return result;
    }

    private double calcMaxFlowBasedOnMinCut() {
        double result;
        double f_aPlus = 0.0;
        for (E e : minCutOutgoing) {
            f_aPlus+= graph.getEdgeFlow(e);
        }
        double f_aMinus = 0.0;
        for (E e : minCutIncomming) {
            f_aMinus+= graph.getEdgeFlow(e);
        }
        result = f_aPlus - f_aMinus;
        return result;
    }

    private void isMaxFlowMinCutTheoremfulfilled() {
        boolean conditionResult = (maxFlow == minCutCapacity);
        if (!conditionResult){
            throw new Error("Maximaler-Fluss-Minimaler-Schnitt-Theorem ist nicht erfüllt. Werte sind nicht gleich!");
        }
    }
}
