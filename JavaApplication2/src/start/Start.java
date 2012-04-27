/**
 * HAW Hamburg - GKA SS 2012 - Gruppe 2 - Team 04
 *
 * @author Tobi
 * @author Nidal
 */
package start;


import java.util.ArrayList;
import java.util.List;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.FloydWarshallShortestPaths;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.GraphPathImpl;
import utils.GraphParser;


public class Start {

    public static void main(String[] args) {
        
        //Graphen
        Graph<String, DefaultWeightedEdge> p1_undirected_cities = GraphParser.parse("src/graphFiles/graph_01.graph");
        Graph<String, DefaultWeightedEdge> p2_undirected_cities = GraphParser.parse("src/graphFiles/graph_02.graph");
        Graph<String, DefaultWeightedEdge> p2_directed = GraphParser.parse("src/graphFiles/graph_03.graph");
        Graph<String, DefaultWeightedEdge> p2_undirected_negCircle = GraphParser.parse("src/graphFiles/graph_04.graph");
        Graph<String, DefaultWeightedEdge> p2_undirected_negCircle_2components = GraphParser.parse("src/graphFiles/graph_05.graph");
        Graph<String, DefaultWeightedEdge> p2_directed_negEdges = GraphParser.parse("src/graphFiles/graph_06.graph");
        Graph<String, DefaultWeightedEdge> p2_directed_negCircle = GraphParser.parse("src/graphFiles/graph_07.graph");
        
        //Praktikum 1
        System.out.println(new Breitensuche(p1_undirected_cities));
        System.out.println(new Tiefensuche(p1_undirected_cities));
        
        //Praktikum 2
        
        
        
        
        //Sandbox
//        System.out.println(p2_directed_negEdges.getEdgeWeight(p2_directed_negEdges.getEdge("v7", "v8")));
//        System.out.println(p2_directed_negEdges.getEdge("v8", "v7"));
        

//        FloydWarshallShortestPaths fws = new FloydWarshallShortestPaths(p1_undirected_positiv_cities);
//        System.out.println(fws.getShortestPath("Hamburg", "Lüneburg"));
//        List<DefaultWeightedEdge> vList = new ArrayList<>();
//        vList.add(p1_undirected_positiv_cities.getEdge("Hamburg", "Lüneburg"));
//        GraphPath x = new GraphPathImpl(p1_undirected_positiv_cities, "Hamburg", "Lüneburg", vList , p1_undirected_positiv_cities.getEdgeWeight(vList.get(0)));
//        System.out.println(x.toString());
//        System.out.println(x.getWeight());
    }
}
