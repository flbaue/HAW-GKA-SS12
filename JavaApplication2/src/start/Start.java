/**
 * HAW Hamburg - GKA SS 2012 - Gruppe 2 - Team 04
 *
 * @author Tobi
 * @author Nidal
 */
package start;

import gComponents.Edge;
import gComponents.GFactory;
import gComponents.Vertex;
import java.util.Iterator;
import utils.GraphParser;
import org.jgraph.JGraph;
import org.jgrapht.Graph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.alg.KruskalMinimumSpanningTree;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.traverse.BreadthFirstIterator;
import utils.GraphPainter;


public class Start {

    public static void main(String[] args) {
        
        Graph<Vertex, Edge> myGraph = GraphParser.parse("src/graphFiles/graph_01.graph");
      
//        int i = 0;
//        System.out.println("Breitensuche");
//        for (Iterator it = new BreadthFirstIterator<Vertex, Edge>(myGraph); it.hasNext();) {
//            System.out.println(i++);
//            System.out.println(it.next());
//        }
      
        
        System.out.println(new Breitensuche(myGraph));
        System.out.println(new Tiefensuche(myGraph));

    }
}
