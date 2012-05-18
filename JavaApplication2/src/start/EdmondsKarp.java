/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package start;

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

    EdmondsKarp(FlowGraph<V, E> flowGraph, V source, V target) {
        this.graph=flowGraph;
        this.source=source;
        this.target=target;
        
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
