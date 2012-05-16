/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package start;

import java.util.*;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.GraphPathImpl;
import utils.FlowGraph;

/**
 *
 * @author Nidal
 */
public class Ford_Fulkerson<V, E> {

    final FlowGraph<V, E> flowGraph;

    public Ford_Fulkerson(FlowGraph<V, E> g) {

        //################
        //# VORBEREITUNG #
        //################

        //
        this.flowGraph = g;

    }

    
    
    public List<Double> getEdgesKapazitat() {
        List<E> edges = new ArrayList(this.flowGraph.edgeSet());
        List<Double> result = new ArrayList();

        for (E edge : edges) {
            
            result.add(this.flowGraph.getEdgeCapacity(edge));
        }
        return result;
    }

    @Override
    public String toString() {
        String result = "";
        result = this.flowGraph.toString();
        return result;
    }
}
