/**
 * HAW Hamburg - GKA SS 2012 - Gruppe 2 - Team 04
 *
 * @author Tobi
 * @author Nidal
 */
package start;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import utils.DefaultDirectedWeightedFlowGraph;
import utils.GraphParser;

public class Start {

    public static void main(String[] args) {

        //Graphen
        WeightedGraph<String, DefaultWeightedEdge> p1_undirected_cities = GraphParser.parseUndirected("src/graphFiles/graph_01.graph");
        WeightedGraph<String, DefaultWeightedEdge> p2_undirected_cities = GraphParser.parseUndirected("src/graphFiles/graph_02.graph");
        WeightedGraph<String, DefaultWeightedEdge> p2_directed = GraphParser.parseDirected("src/graphFiles/graph_03.graph");
        WeightedGraph<String, DefaultWeightedEdge> p2_undirected_negCircle = GraphParser.parseUndirected("src/graphFiles/graph_04.graph");
        WeightedGraph<String, DefaultWeightedEdge> p2_undirected_negCircle_2components = GraphParser.parseUndirected("src/graphFiles/graph_05.graph");
        WeightedGraph<String, DefaultWeightedEdge> p2_directed_negEdges = GraphParser.parseDirected("src/graphFiles/graph_06.graph");
        WeightedGraph<String, DefaultWeightedEdge> p2_directed_negCircle = GraphParser.parseDirected("src/graphFiles/graph_07.graph");
        DefaultDirectedWeightedFlowGraph<String, DefaultWeightedEdge> p3_directed_cities = new DefaultDirectedWeightedFlowGraph<>(GraphParser.<DefaultWeightedEdge>parseDirected("src/graphFiles/graph_08.graph"));
        DefaultDirectedWeightedFlowGraph<String, DefaultWeightedEdge> p3_directed_cities2 = new DefaultDirectedWeightedFlowGraph<>(GraphParser.<DefaultWeightedEdge>parseDirected("src/graphFiles/graph_08.graph"));
        
       // System.out.println(p3_directed_cities);

        //Praktikum 1
//        System.out.println(new Breitensuche(p1_undirected_cities));
//        System.out.println(new Tiefensuche(p1_undirected_cities));

        //Praktikum 2

        
//        String startV;
//        long startTime, endTime;
//        //Graph 01
//        Set<String> vertices01 = p1_undirected_cities.vertexSet();
//        startV="Augsburg";
//        System.out.println("Graph 01\n###############");
//        startTime = System.currentTimeMillis();
//        FloydWarshall fw01 = new FloydWarshall(p1_undirected_cities);
//        for (String endVertex : vertices01) {
//            System.out.println(fw01.getShortestPath(startV, endVertex));
//        }
//        endTime = System.currentTimeMillis()-startTime;
//        System.out.println(">>>>FloydWarshall benötigt "+endTime+" ms von "+startV+" zu allen Zielen");
//        
//        startTime = System.currentTimeMillis();
//        Dijkstra ds01 = new Dijkstra(p1_undirected_cities,startV);
//        for (String endVertex : vertices01) {
//            System.out.println(ds01.getShortestPathTo(endVertex));
//        }
//        endTime = System.currentTimeMillis()-startTime;
//        System.out.println(">>>>Dijkstra benötigt "+endTime+" ms von "+startV+" zu allen Zielen");    
//        
//        //Graph 02
//        Set<String> vertices02 = p2_undirected_cities.vertexSet();
//        startV="München";
//        System.out.println("Graph 02\n###############");
//        startTime = System.currentTimeMillis();
//        FloydWarshall fw02 = new FloydWarshall(p2_undirected_cities);
//        for (String endVertex : vertices02) {
//            System.out.println(fw02.getShortestPath(startV, endVertex));
//        }
//        endTime = System.currentTimeMillis()-startTime;
//        System.out.println(">>>>FloydWarshall benötigt "+endTime+" ms von "+startV+" zu allen Zielen");
//        
//        startTime = System.currentTimeMillis();
//        Dijkstra ds02 = new Dijkstra(p2_undirected_cities,startV);
//        for (String endVertex : vertices02) {
//            System.out.println(ds02.getShortestPathTo(endVertex));
//        }
//        endTime = System.currentTimeMillis()-startTime;
//        System.out.println(">>>>Dijkstra benötigt "+endTime+" ms von "+startV+" zu allen Zielen");
//        
//        //Graph 03
//        Set<String> vertices03 = p2_directed.vertexSet();
//        startV="s";
//        System.out.println("Graph 03\n###############");
//        startTime = System.currentTimeMillis();
//        FloydWarshall fw03 = new FloydWarshall(p2_directed);
//        for (String endVertex : vertices03) {
//            System.out.println(fw03.getShortestPath(startV, endVertex));
//        }
//        endTime = System.currentTimeMillis()-startTime;
//        System.out.println(">>>>FloydWarshall benötigt "+endTime+" ms von "+startV+" zu allen Zielen");
//        
//        startTime = System.currentTimeMillis();
//        Dijkstra ds03 = new Dijkstra(p2_directed,startV);
//        for (String endVertex : vertices03) {
//            System.out.println(ds03.getShortestPathTo(endVertex));
//        }
//        endTime = System.currentTimeMillis()-startTime;
//        System.out.println(">>>>Dijkstra benötigt "+endTime+" ms von "+startV+" zu allen Zielen");    
//         
//        //Graph 05
//        Set<String> vertices05 = p2_undirected_negCircle_2components.vertexSet();
//        startV="v1";
//        System.out.println("Graph 05\n###############");
//        startTime = System.currentTimeMillis();
//        FloydWarshall fw05 = new FloydWarshall(p2_undirected_negCircle_2components);
//        for (String endVertex : vertices05) {
//            System.out.println(fw05.getShortestPath(startV, endVertex));
//            
//        }
//        endTime = System.currentTimeMillis()-startTime;
//        System.out.println(">>>>FloydWarshall benötigt "+endTime+" ms von "+startV+" zu allen Zielen");
//        
//        startTime = System.currentTimeMillis();
//        Dijkstra ds05 = new Dijkstra(p2_undirected_negCircle_2components,startV);
//        for (String endVertex : vertices05) {
//            System.out.println(ds05.getShortestPathTo(endVertex));
//        }
//        endTime = System.currentTimeMillis()-startTime;
//        System.out.println(">>>>Dijkstra benötigt "+endTime+" ms von "+startV+" zu allen Zielen");    

        
        //Praktikum 3
        
        System.out.println(new Ford_Fulkerson(p3_directed_cities));
        EdmondsKarp ek1 = new EdmondsKarp(p3_directed_cities2, "Rostock", "München");
        System.out.println(ek1.getMaxFlow());
        


        //Sandbox

    }
}
