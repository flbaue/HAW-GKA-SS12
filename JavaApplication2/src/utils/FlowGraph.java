/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.Serializable;
import java.util.*;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;

/**
 *
 * @author Tobi
 */
public interface FlowGraph<V,E> extends Graph<V, E>, WeightedGraph<V, E>,  DirectedGraph<V, E>,Cloneable, Serializable {
    public void setEdgeCapacity(E e, double capacity);
    public void setEdgeFlow(E e, double flow);
    public double getEdgeCapacity(E e);
    public double getEdgeFlow(E e);
    public void resetAllFlowsTo(double newFlowValueForAllGraphEdges);
}
