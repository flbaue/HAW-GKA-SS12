/**
 * HAW Hamburg - GKA SS 2012 - Gruppe 2 - Team 04
 *
 * @author Tobi
 * @author Nidal
 */
package start;


import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import utils.GraphParser;


public class Start {

    public static void main(String[] args) {
        
        Graph<String, DefaultWeightedEdge> myGraph = GraphParser.parse("src/graphFiles/graph_01.graph");
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
