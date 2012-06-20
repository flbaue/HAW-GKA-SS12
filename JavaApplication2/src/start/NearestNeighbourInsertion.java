/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package start;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jgrapht.Graph;

/**
 *
 * @author NED
 */
public class NearestNeighbourInsertion<V, E> {

    private final Graph<V, E> graph;
    private final V startVertex;
    private final List<E> allEdges;
    private final List<V> allVertex;
    private boolean LOG = false;
    private List<V> gebliebenenEcken;
    private Map<List<V>, Double> result;

    public NearestNeighbourInsertion(Graph<V, E> graph, V start) {
        this.graph = graph;
        this.startVertex = start;
        this.allEdges = new ArrayList(graph.edgeSet());
        this.allVertex = new ArrayList(graph.vertexSet());
        this.gebliebenenEcken = new ArrayList<>();

        // vollständig Graph prüfen: kanten Anzahl = (n.(n-1))/2
        final int kantenAnzahl = ((allVertex.size() * (allVertex.size() - 1)) / 2);
        if (kantenAnzahl == allEdges.size()) {
            // Algorithmus ausführen
            nearestInsertion(graph, start);
        } else {
            throw new Error("Dieser Graph ist nicht vollständig!!");
        }

    }

    private Map<List<V>, Double> nearestInsertion(Graph<V, E> graph, V start) {
        result = new HashMap<>();
        List<V> mainTour = new ArrayList<>();
        gebliebenenEcken = new ArrayList<>(allVertex);

        // Start Tour .. start Ecke zur kantenFolge hinzufügen
        mainTour.add(start);
        // gearbeitete Ecke entfernen
        gebliebenenEcken.remove(start);

        // loop while nicht alle Ecken in Tour enthalten
        while (mainTour.size() <= allVertex.size()) {

            //Kürzeste Kreis finden
            mainTour = findeKuerzesteKreis(mainTour);

            // save bis jetzt Ergebnisse, (Tour, distance) 
            result.put(new ArrayList<>(mainTour), kantenFolgeDistanceRechnen(mainTour));

            //Kreis abbauen, in Tour umwandeln. (Endecke entfernen List[size-1])
            mainTour.remove(mainTour.size() - 1);

            //Dichteste Ecke vom Weg finden
            V dichtestenEcke = findeDichtestenEcke(mainTour);

            // Dichteste Ecke zum Tour hinzufügen
            mainTour.add(dichtestenEcke);

            // gearbeitete Ecke entfernen
            gebliebenenEcken.remove(dichtestenEcke);
        }
        return result;
    }

    // Kürzeste Kreis finden
    private List<V> findeKuerzesteKreis(List<V> mainTour) {
        // Kürzeste Kreis distance initialisieren
        double minDistance = Double.MAX_VALUE;

        // Die neugefügte dichteste Ecke speichern
        final V neuHinzugefuegteEcke = mainTour.get(mainTour.size() - 1);

        //Kreis Builden,Tour in Kreis umwandeln (. (Start Ecke hinzufügen List[0])
        mainTour.add(startVertex);
        double tempDistance = 0;

        // kürzeste Kreis initialisieren
        List<V> kuerzesteKreis = new ArrayList<>(mainTour);

        //Die neue hinzugefügte Ecke rumtaushen List[size-2]
        // alle Zyklen in Tour werden betrachtet
        for (int i = 0; i < mainTour.size() - 2; i++) {
            // Kantenfolge distance rechnen 
            tempDistance = kantenFolgeDistanceRechnen(mainTour);

            if (LOG) {
                System.out.println("Tour: " + mainTour + " Distance: " + tempDistance);
            }
            // distance und kreis ersetzen wenn kleiner gefunden
            if (tempDistance <= minDistance) {
                minDistance = tempDistance;
                kuerzesteKreis = new ArrayList<>(mainTour);
            }

            //neue Ecke Platz tauschen, permütation
            // Bsp.: neue Ecke = E
            //[A,B,C,D,E,A]
            //[A,B,C,E,D,A]
            //[A,B,E,D,C,A]
            //[A,E,C,D,B,A]
            int eckeIndex = mainTour.indexOf(neuHinzugefuegteEcke);
            mainTour.remove(eckeIndex);
            mainTour.add(eckeIndex - 1, neuHinzugefuegteEcke);
        }
        if (LOG) {
            System.out.println("kürzeste Kreis: " + kuerzesteKreis + " ,Distance = " + (minDistance == Double.MAX_VALUE ? tempDistance : minDistance));
        }
        return kuerzesteKreis;
    }

    // kantenFolge Distance rechnen
    private double kantenFolgeDistanceRechnen(List<V> mainTour) {
        // distance initialisieren
        double distance = 0;

        // loop über alle Ecken, und addiere Gewicht aller Kanten
        for (int i = 0; i <= mainTour.size() - 2; i++) {

            // wenn FirstEcke und SecondEcke gleich sind => distance = 0
            if (mainTour.get(i) != mainTour.get(i + 1)) {
                distance += graph.getEdgeWeight(graph.getEdge(mainTour.get(i), mainTour.get(i + 1)));
            } else {
                return distance;
            }
        }
        return distance;
    }

    // finde die dichtesten Ecke
    private V findeDichtestenEcke(List<V> mainTour) {
        // check korrektheit
        if (mainTour.size() + gebliebenenEcken.size() == allVertex.size()) {

            // dichtesten Ecke Entfernung intiliasieren
            double minEntfernung = Double.MAX_VALUE;
            V dichtestenEcke = null;

            //gücke nach alle kanten in der nähe vom Tour 
            for (V ecke : mainTour) {
                for (V nachbarEcke : gebliebenenEcken) {
                    double entfernung = graph.getEdgeWeight(graph.getEdge(ecke, nachbarEcke));
                    // merke die dichtesten Ecke und ihre Entfernung 
                    if (entfernung <= minEntfernung) {
                        minEntfernung = entfernung;
                        dichtestenEcke = nachbarEcke;
                    }
                }
            }
            if (LOG) {
                System.out.println("nächste dichteste Ecke = " + dichtestenEcke + "  ");
            }
            return dichtestenEcke;
        } else {
            System.err.println("fehlende Ecke oder verdoppelte Ecke");
            return null;
        }
    }

    // Suche nach dem Entry der den kompleten Kreis enthält
    private Map.Entry<List<V>, Double> getTour() {
        for (Map.Entry<List<V>, Double> entry : result.entrySet()) {
            
            if (entry.getKey().size() - 1 == allVertex.size()) {
                return entry;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String result;
        result = String.format("Kürzeste Kreis = " + getTour().getKey() + " ,Distance = " + getTour().getValue());
        return result;
    }
}
