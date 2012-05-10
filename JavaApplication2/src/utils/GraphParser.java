/**
 * HAW Hamburg - GKA SS 2012 - Gruppe 2 - Team 04
 *
 * @author Tobi
 * @author Nidal
 */
package utils;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;

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
    public static <E> DefaultDirectedWeightedGraph<String, E> parseDirected(String graphFile) {
        DefaultDirectedWeightedGraph result = null;    //retur-value
        try {
            FileInputStream fstream = new FileInputStream(graphFile);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine; //current line
            String firstLine = br.readLine(); //first line
            if (firstLine.matches("#gerichtet")) { //create digraph
                result = new DefaultDirectedWeightedGraph(DefaultWeightedEdge.class);
            } else {
                throw new Exception("Fehler in Datei: Erste Zeile enthält nicht korrekten Graph-Typ '#gerichtet'.");
            }
            int lineNo = 2;
            while ((strLine = br.readLine()) != null) { //Read File Line By Line
                //Split String by ","-limiter and put Substrings to array
                // e.g.:
                //  "Augsburg,München,70" -> array["Augsburg","München","70"]
                //  array[0] = start; array[1] = end; array[2] = value;
                String[] line = strLine.split(",");
                if (line.length!=3) throw new Exception(String.format("Fehler in Datei: Zeile %d hat %d statt 3 Werte.", lineNo,line.length));

                String v1 = line[0];
                result.addVertex(v1);

                String v2 = line[1];
                result.addVertex(v2);

                DefaultWeightedEdge edge = new DefaultWeightedEdge();
                result.addEdge(v1, v2, edge);
                result.setEdgeWeight(edge, Integer.parseInt(line[2]));

//                System.out.println(strLine);
                lineNo++;
            }
            in.close(); //Close the input stream
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
        
        return result;
    }
    
    public static <E> ListenableUndirectedWeightedGraph<String, E> parseUndirected(String graphFile) {
        ListenableUndirectedWeightedGraph result = null;    //retur-value
        try {
            FileInputStream fstream = new FileInputStream(graphFile);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine; //current line
            String firstLine = br.readLine(); //first line
            if (firstLine.matches("#ungerichtet")) {    //create undirected graph
                result = new ListenableUndirectedWeightedGraph(DefaultWeightedEdge.class);
            } else {
                throw new Exception("Fehler in Datei: Erste Zeile enthält nicht korrekten Graph-Typ '#ungerichtet'.");
            }
            int lineNo = 2;
            while ((strLine = br.readLine()) != null) { //Read File Line By Line
                //Split String by ","-limiter and put Substrings to array
                // e.g.:
                //  "Augsburg,München,70" -> array["Augsburg","München","70"]
                //  array[0] = start; array[1] = end; array[2] = value;
                String[] line = strLine.split(",");
                if (line.length!=3) throw new Exception(String.format("Fehler in Datei: Zeile %d hat %d statt 3 Werte.", lineNo,line.length));

                String v1 = line[0];
                result.addVertex(v1);

                String v2 = line[1];
                result.addVertex(v2);

                DefaultWeightedEdge edge = new DefaultWeightedEdge();
                result.addEdge(v1, v2, edge);
                result.setEdgeWeight(edge, Integer.parseInt(line[2]));

//                System.out.println(strLine);
                lineNo++;
            }
            in.close(); //Close the input stream
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
        
        return result;
    }
}
