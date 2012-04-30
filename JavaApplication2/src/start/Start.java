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
        
        System.out.println(fW02.getShortestPath("Kaiserslautern", "Rostock"));
        FloydWarshallShortestPaths buildInFW = new FloydWarshallShortestPaths(p2_undirected_cities);
        System.out.println(buildInFW.getShortestPath("Kaiserslautern", "Rostock"));
        
        System.out.println(fW02.getShortestPath("Augsburg", "Hamburg"));
        System.out.println(buildInFW.getShortestPath("Augsburg", "Hamburg"));
        
        System.out.println(fW02.getShortestPath("Karlsruhe", "Nürnberg"));
        System.out.println(buildInFW.getShortestPath("Karlsruhe", "Nürnberg"));

        
        
        
        
        
        
        
        
        
        //Sandbox
//        Graph<String, DefaultWeightedEdge> fWGraph = GraphParser.parse("src/graphFiles/FW.graph");
//        FloydWarshall fW= new FloydWarshall(fWGraph);
//        FloydWarshallShortestPaths buildInFW = new FloydWarshallShortestPaths(fWGraph);
//        System.out.println(fWGraph);
//        System.out.println(fW.getShortestPath("v1", "v1"));
//        System.out.println(buildInFW.getShortestPath("v1", "v1"));
//        System.out.println(fW.getShortestPath("v1", "v2"));
//        System.out.println(buildInFW.getShortestPath("v1", "v2"));
//        System.out.println(fW.getShortestPath("v1", "v3"));
//        System.out.println(buildInFW.getShortestPath("v1", "v3"));
//        System.out.println(fW.getShortestPath("v1", "v4"));
//        System.out.println(buildInFW.getShortestPath("v1", "v4"));
//        System.out.println("Von v2 nach v1");
//        System.out.println(fW.getShortestPath("v2", "v1"));
//        System.out.println(buildInFW.getShortestPath("v2", "v1"));
//        System.out.println(fW.getShortestPath("v2", "v2"));
//        System.out.println(buildInFW.getShortestPath("v2", "v2"));
//        System.out.println(fW.getShortestPath("v2", "v3"));
//        System.out.println(buildInFW.getShortestPath("v2", "v3"));
//        System.out.println(fW.getShortestPath("v2", "v4"));
//        System.out.println(buildInFW.getShortestPath("v2", "v4"));
//        System.out.println(fW.getShortestPath("v3", "v1"));
////        System.out.println(buildInFW.getShortestPath("v3", "v1"));
//        System.out.println(fW.getShortestPath("v3", "v2"));
//        System.out.println(buildInFW.getShortestPath("v3", "v2"));
//        System.out.println(fW.getShortestPath("v3", "v3"));
//        System.out.println(buildInFW.getShortestPath("v3", "v3"));
//        System.out.println(fW.getShortestPath("v3", "v4"));
//        System.out.println(buildInFW.getShortestPath("v3", "v4"));
//        System.out.println(fW.getShortestPath("v4", "v1"));
//        System.out.println(buildInFW.getShortestPath("v4", "v1"));
//        System.out.println(fW.getShortestPath("v4", "v2"));
//        System.out.println(buildInFW.getShortestPath("v4", "v2"));
//        System.out.println(fW.getShortestPath("v4", "v3"));
//        System.out.println(buildInFW.getShortestPath("v4", "v3"));
//        System.out.println(fW.getShortestPath("v4", "v4"));
//        System.out.println(buildInFW.getShortestPath("v4", "v4"));
        
//        System.out.println(p2_directed_negEdges.getEdgeWeight(p2_directed_negEdges.getEdge("v7", "v8")));
//        System.out.println(p2_directed_negEdges.getEdge("v8", "v7"));
//        FloydWarshallShortestPaths buildInFW = new FloydWarshallShortestPaths(p2_directed);

//        FloydWarshallShortestPaths fws = new FloydWarshallShortestPaths(p1_undirected_positiv_cities);
//        System.out.println(fws.getShortestPath("Hamburg", "Lüneburg"));
//        List<DefaultWeightedEdge> vList = new ArrayList<>();
//        vList.add(p1_undirected_positiv_cities.getEdge("Hamburg", "Lüneburg"));
//        GraphPath x = new GraphPathImpl(p1_undirected_positiv_cities, "Hamburg", "Lüneburg", vList , p1_undirected_positiv_cities.getEdgeWeight(vList.get(0)));
//        System.out.println(x.toString());
//        System.out.println(x.getWeight());
    }
}
