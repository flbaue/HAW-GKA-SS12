/**
 * HAW Hamburg - GKA SS 2012 - Gruppe 2 - Team 04
 *
 * @author Tobi
 * @author Nidal
 */
package start;


import org.jgrapht.Graph;
import org.jgrapht.alg.FloydWarshallShortestPaths;
import org.jgrapht.graph.DefaultWeightedEdge;
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
//        System.out.println(new Breitensuche(p1_undirected_cities));
//        System.out.println(new Tiefensuche(p1_undirected_cities));
        
        //Praktikum 2
        FloydWarshall fW01= new FloydWarshall(p1_undirected_cities); 
        FloydWarshall fW02= new FloydWarshall(p2_undirected_cities);
        FloydWarshall fW03= new FloydWarshall(p2_directed);
//        FloydWarshall fW04= new FloydWarshall(p2_undirected_negCircle); //Error, negativer Kreis
//        FloydWarshall fW05= new FloydWarshall(p2_undirected_negCircle_2components); //Error, negativer Kreis
        FloydWarshall fW06= new FloydWarshall(p2_directed_negEdges);
//        FloydWarshall fW07= new FloydWarshall(p2_directed_negCircle); //Error, negativer Kreis
        
//        fW03.getShortestDistance("a", "b"); //Error, a existier nicht
//        fW03.getShortestDistance("s", "b"); //Error, b existier nicht
        
        FloydWarshallShortestPaths buildInFW = new FloydWarshallShortestPaths(p2_undirected_cities);
        
        System.out.println(fW02.getShortestPath("Kaiserslautern", "Rostock"));
        System.out.println(buildInFW.getShortestPath("Kaiserslautern", "Rostock"));
        
        System.out.println(fW02.getShortestPath("Augsburg", "Hamburg"));
        System.out.println(buildInFW.getShortestPath("Augsburg", "Hamburg"));
        
        System.out.println(fW02.getShortestPath("Karlsruhe", "Nürnberg"));
        System.out.println(buildInFW.getShortestPath("Karlsruhe", "Nürnberg"));

        
        FloydWarshallShortestPaths buildInFW6 = new FloydWarshallShortestPaths(p2_directed_negEdges);
        System.out.println(fW06.getShortestPath("v1", "v9"));
        System.out.println(buildInFW6.getShortestPath("v1", "v9"));
        
     
        
        
        
        //Sandbox

    }
}
