/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package start;

import org.jgrapht.Graph;

/**
 *
 * @author Tobi
 */
public class Hierholzer<V, E> {
    
    private Graph<V, E> graph;
    
    
    public Hierholzer(Graph<V, E> g){
        
        //Pruefen, ob es Ecken gibt, die einen ungeraden Eckengrad haben,
        // wenn dies zutrifft, existiert kein Eulerkreis und der Algorithmus
        // funktioniert nicht
        for (V vertex : g.vertexSet()) {
            if (g.edgesOf(vertex).size()%2!=0) {
                
            }
        }
        this.graph = g;
    }
    
}
