/**
 * HAW Hamburg - GKA SS 2012 - Gruppe 2 - Team 04
 *
 * @author Tobi
 * @author Nidal
 */
package utils;

import gComponents.GFactory;
import gComponents.Vertex;
import java.io.*;
import java.util.*;
import org.jgrapht.*;
import org.jgrapht.graph.*;

/**
 *
 * @author Tobi
 */
public class GraphParser {

    private GraphParser() {
    };
    
    /**
     * This methode should be called to create a Graph, based on a File, that 
     *   should be parsed
     * 
     * @param graphFile: path of the file, that should be parsed
     * @return graph with weighted edges
     */
    public static Graph<String, DefaultWeightedEdge> parse(String graphFile) {
        Graph result = null;    //retur-value
        Set<Vertex> vertices = new HashSet<>(); //collection of String-Names for vertices
        Set<String[]> edges = new HashSet<>();  //collection of String-Data for edges
        boolean isDigraph = true;
        try {
            FileInputStream fstream = new FileInputStream(graphFile);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine; //current line
            String firstLine = br.readLine(); //first line
            if (firstLine.matches("#ungerichtet")) {    //create undirected graph
                result = new ListenableDirectedWeightedGraph(DefaultWeightedEdge.class);
                isDigraph = false;
            } else if (firstLine.matches("#gerichtet")) { //create digraph
                result = new DefaultDirectedWeightedGraph(DefaultWeightedEdge.class);
                isDigraph = true;
            } else {
                throw new Exception("Fehler in Datei: Erste Zeile enthält nicht korrekten Graph-Typ.");
            }
            int lineNo = 2;
            while ((strLine = br.readLine()) != null) { //Read File Line By Line
                //Split String by ","-limiter and put Substrings to array
                // e.g.:
                //  "Augsburg,München,70" -> array["Augsburg","München","70"]
                //  array[0] = start; array[1] = end; array[2] = value;
                String[] line = strLine.split(",");
                if (line.length!=3) throw new Exception(String.format("Fehler in Datei: Zeile %d hat %d statt 3 Werte.", lineNo,line.length));
//                vertices.add(line[0]);
//                vertices.add(line[1]);
//                if (!isDigraph) {};
//                edges.add(line);
                Vertex a = GFactory.vertex(line[0]);
                Vertex b = GFactory.vertex(line[1]);
                result.addVertex(b);
                result.addEdge(a, b, GFactory.edge((a.name()+"-"b.name()), Integer.parseInt(line[2])));
                System.out.println(strLine);
                lineNo++;
            }
            in.close(); //Close the input stream
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
        
        //Add all vertices to graph
        for (Vertex vertex : vertices) {
            result.addVertex(vertex);
        }
        
        //Add all edges to graph
        for (String[] edge : edges) {
            if (!result.addEdge(edge[0], edge[1], GFactory.edge(Integer.parseInt(edge[2])))) {
                System.out.println(Arrays.toString(edge));
            }
        }
        if (!isDigraph) for (String[] edge : edges) {
            System.out.println(result.addEdge(edge[1], edge[0], GFactory.edge(Integer.parseInt(edge[2]))));
        }
        return result;
    }
}
