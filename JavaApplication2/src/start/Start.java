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
import utils.GraphParser;
import org.jgraph.JGraph;
import org.jgrapht.Graph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultWeightedEdge;
import utils.GraphPainter;


public class Start {

    public static void main(String[] args) {
        
        Graph<Vertex, Edge> myGraph = GraphParser.parse("src/graphFiles/graph_01.graph");
        System.err.println(myGraph);
        //GraphPainter.paint(myGraph);
        //Graph<String, DefaultWeightedEdge> myGraph = GraphParser.parse("src/graphFiles/graph_01_directed.graph");
        //Graph<String, DefaultWeightedEdge> myGraph = GraphParser.parse("src/graphFiles/graph_01_error1.graph");
        //Graph<String, DefaultWeightedEdge> myGraph = GraphParser.parse("src/graphFiles/graph_01_error2.graph");
        //Graph<String, DefaultWeightedEdge> myGraph = GraphParser.parse("src/graphFiles/graph_01_error3.graph");
        System.err.println("H->L "+DijkstraShortestPath.findPathBetween(myGraph, GFactory.vertex("Hamburg"), GFactory.vertex("Lüneburg")));
        System.err.println("L->H "+DijkstraShortestPath.findPathBetween(myGraph, GFactory.vertex("Lüneburg"), GFactory.vertex("Hamburg")));
        
        
//        DirectedGraph<Integer, DefaultEdge> myGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
//        myGraph.addVertex(1);
//        myGraph.addVertex(2);
//        myGraph.addVertex(3);
//        myGraph.addVertex(4);
//        myGraph.addVertex(5);
//        myGraph.addVertex(6);
//        myGraph.addVertex(7);
//        myGraph.addVertex(8);
//        
//        myGraph.addEdge(1, 2);
//        myGraph.addEdge(2, 3);
//        myGraph.addEdge(3, 2);
//        myGraph.addEdge(3, 4);
//        myGraph.addEdge(1, 4);
//        myGraph.addEdge(4, 7);
//        myGraph.addEdge(7, 2);
//        myGraph.addEdge(8, 3);
//        myGraph.addEdge(5, 2);
//        myGraph.addEdge(2, 5);
//        myGraph.addEdge(5, 8);
//        
//        System.out.println(myGraph);
//        
//        // constructs a directed graph with the specified vertices and edges
//        DirectedGraph<String, DefaultEdge> directedGraph =
//                new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
//        directedGraph.addVertex("a");
//        directedGraph.addVertex("b");
//        directedGraph.addVertex("c");
//        directedGraph.addVertex("d");
//        directedGraph.addVertex("e");
//        directedGraph.addVertex("f");
//        directedGraph.addVertex("g");
//        directedGraph.addVertex("h");
//        directedGraph.addVertex("i");
//        directedGraph.addEdge("a", "b");
//        directedGraph.addEdge("b", "d");
//        directedGraph.addEdge("d", "c");
//        directedGraph.addEdge("c", "a");
//        directedGraph.addEdge("e", "d");
//        directedGraph.addEdge("e", "f");
//        directedGraph.addEdge("f", "g");
//        directedGraph.addEdge("g", "e");
//        directedGraph.addEdge("h", "e");
//        directedGraph.addEdge("i", "h");
//
//        // computes all the strongly connected components of the directed graph
//        StrongConnectivityInspector sci =
//                new StrongConnectivityInspector(directedGraph);
//        List stronglyConnectedSubgraphs = sci.stronglyConnectedSubgraphs();
//
//        // prints the strongly connected components
//        System.out.println("Strongly connected components:");
//        for (int i = 0; i < stronglyConnectedSubgraphs.size(); i++) {
//            System.out.println(stronglyConnectedSubgraphs.get(i));
//        }
//        System.out.println();
//
//        // Prints the shortest path from vertex i to vertex c. This certainly
//        // exists for our particular directed graph.
//        System.out.println("Shortest path from i to c:");
//        List path =
//                DijkstraShortestPath.findPathBetween(directedGraph, "i", "c");
//        System.out.println(path + "\n");
//
//        // Prints the shortest path from vertex c to vertex i. This path does
//        // NOT exist for our particular directed graph. Hence the path is
//        // empty and the variable "path" must be null.
//        System.out.println("Shortest path from c to i:");
//        path = DijkstraShortestPath.findPathBetween(directedGraph, "c", "i");
//        System.out.println(path);
    }
}
