/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.Serializable;
import java.util.*;
import org.jgrapht.DirectedGraph;
import org.jgrapht.EdgeFactory;
import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;

/**
 *
 * @author Tobi
 */
public class DefaultDirectedWeightedFlowGraph<V, E> implements WeightedGraph<V, E>, FlowGraph<V, E>, DirectedGraph<V, E>, Graph<V, E>, Cloneable, Serializable {

    private final Map<E, Double> flow = new TreeMap<>();
    private final DefaultDirectedWeightedGraph<V, E> graph;
    //~ Static fields/initializers ---------------------------------------------
    private static final long serialVersionUID = 1L;
    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new directed weighted flow graph.
     *
     * @param edgeClass class on which to base factory for edges
     */
    public DefaultDirectedWeightedFlowGraph(Class<? extends E> edgeClass) {
        this(new ClassBasedEdgeFactory<V, E>(edgeClass));
    }

    /**
     * Creates a new directed weighted flow graph with the specified edge factory.
     *
     * @param ef the edge factory of the new graph.
     */
    public DefaultDirectedWeightedFlowGraph(EdgeFactory<V, E> ef) {
        this.graph = new DefaultDirectedWeightedGraph(ef);

    }

    /**
     * Initialis Kapazität .
     *
     */
    public void initialiskapazitaet(Graph<V, E> graph) {
        List<E> edgesList = new ArrayList(graph.edgeSet());

        for (E edge : edgesList) {

            this.setEdgeCapacity(edge, graph.getEdgeWeight(edge));
        }
    }

    /**
     * Creates a new directed weighted flow graph by wrapping an 
     * DefaultDirectedWeightedGraph<V,E>
     *
     * @param dWG the graph, which should be wrapped
     */
    public DefaultDirectedWeightedFlowGraph(DefaultDirectedWeightedGraph<V, E> dWG) {
        this.graph = dWG;

    }

    //~ Methods ----------------------------------------------------------------
    @Override
    public void setEdgeWeight(E e, double weight) {
        graph.setEdgeWeight(e, weight);
    }

    @Override
    public Set<E> getAllEdges(V sourceVertex, V targetVertex) {
        return graph.getAllEdges(sourceVertex, targetVertex);
    }

    @Override
    public E getEdge(V sourceVertex, V targetVertex) {
        return graph.getEdge(sourceVertex, targetVertex);
    }

    @Override
    public EdgeFactory<V, E> getEdgeFactory() {
        return graph.getEdgeFactory();
    }

    @Override
    public E addEdge(V sourceVertex, V targetVertex) {
        return graph.addEdge(sourceVertex, targetVertex);
    }

    @Override
    public boolean addEdge(V sourceVertex, V targetVertex, E e) {
        return graph.addEdge(sourceVertex, targetVertex, e);
    }

    @Override
    public boolean addVertex(V v) {
        return graph.addVertex(v);
    }

    @Override
    public boolean containsEdge(V sourceVertex, V targetVertex) {
        return graph.containsEdge(sourceVertex, targetVertex);
    }

    @Override
    public boolean containsEdge(E e) {
        return graph.containsEdge(e);
    }

    @Override
    public boolean containsVertex(V v) {
        return graph.containsVertex(v);
    }

    @Override
    public Set<E> edgeSet() {
        return graph.edgeSet();
    }

    @Override
    public Set<E> edgesOf(V vertex) {
        return graph.edgesOf(vertex);
    }

    @Override
    public boolean removeAllEdges(Collection<? extends E> edges) {
        return graph.removeAllEdges(edges);
    }

    @Override
    public Set<E> removeAllEdges(V sourceVertex, V targetVertex) {
        return graph.removeAllEdges(sourceVertex, targetVertex);
    }

    @Override
    public boolean removeAllVertices(Collection<? extends V> vertices) {
        return graph.removeAllVertices(vertices);
    }

    @Override
    public E removeEdge(V sourceVertex, V targetVertex) {
        return graph.removeEdge(sourceVertex, targetVertex);
    }

    @Override
    public boolean removeEdge(E e) {
        return graph.removeEdge(e);
    }

    @Override
    public boolean removeVertex(V v) {
        return graph.removeVertex(v);
    }

    @Override
    public Set<V> vertexSet() {
        return graph.vertexSet();
    }

    @Override
    public V getEdgeSource(E e) {
        return graph.getEdgeSource(e);
    }

    @Override
    public V getEdgeTarget(E e) {
        return graph.getEdgeTarget(e);
    }

    @Override
    public double getEdgeWeight(E e) {
        return graph.getEdgeWeight(e);
    }

    @Override
    public int inDegreeOf(V vertex) {
        return graph.inDegreeOf(vertex);
    }

    @Override
    public Set<E> incomingEdgesOf(V vertex) {
        return graph.incomingEdgesOf(vertex);
    }

    @Override
    public int outDegreeOf(V vertex) {
        return graph.outDegreeOf(vertex);
    }

    @Override
    public Set<E> outgoingEdgesOf(V vertex) {
        return graph.outgoingEdgesOf(vertex);
    }

    @Override
    public void setEdgeCapacity(E e, double capacity) {
        this.setEdgeWeight(e, capacity);
    }

    @Override
    public void setEdgeFlow(E e, double flow) {
        this.flow.put(e, flow);
    }

    @Override
    public double getEdgeCapacity(E e) {
        return this.getEdgeWeight(e);
    }

    @Override
    public double getEdgeFlow(E e) {
        return flow.get(e);
    }

    @Override
    public String toString() {
        return graph.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DefaultDirectedWeightedFlowGraph<V, E> other = (DefaultDirectedWeightedFlowGraph<V, E>) obj;
        if (!Objects.equals(this.flow, other.flow)) {
            return false;
        }
        if (!Objects.equals(this.graph, other.graph)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.flow);
        hash = 79 * hash + Objects.hashCode(this.graph);
        return hash;
    }

    @Override
    public void resetAllFlowsTo(double newFlowValueForAllGraphEdges) {
        flow.clear();
        for (E edge : graph.edgeSet()) {
            setEdgeFlow(edge, newFlowValueForAllGraphEdges);
        }
    }
}
