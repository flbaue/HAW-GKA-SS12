/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.List;
import java.util.Objects;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.GraphPathImpl;

/**
 *
 * @author Tobi
 */
public class Circle<V, E> {

    private final GraphPath<V, E> path;
    private final String toString;
    
    public Circle(Graph<V, E> graph, V startEndVertex, List<E> edgeList){
        path = new GraphPathImpl(graph, startEndVertex, startEndVertex, edgeList, 0);
        StringBuilder sb = new StringBuilder();
        sb.append("Circle={").append(edgeList.toString()).append("}");
        toString=sb.toString();
    }

    public Graph<V, E> getGraph() {
        return path.getGraph();
    }

    public V getStartEndVertex() {
        return path.getStartVertex();
    }

    public List<E> getEdgeList() {
        return path.getEdgeList();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Circle<V, E> other = (Circle<V, E>) obj;
        if (!Objects.equals(this.path, other.path)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.path);
        return hash;
    }

    @Override
    public String toString() {
        return this.toString;
    }
    
    
}
