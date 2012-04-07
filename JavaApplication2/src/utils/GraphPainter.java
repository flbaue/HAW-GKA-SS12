/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import gComponents.Edge;
import gComponents.Vertex;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import org.jgraph.JGraph;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultWeightedEdge;

/**
 *
 * @author Tobi
 */
public class GraphPainter extends JApplet {
    
    private static JGraph pGraph;
    private static final Dimension DEFAULT_SIZE = new Dimension(1280, 840);
    private static final Color DEFAULT_BG_COLOR = Color.decode("#FAFBFF");
    
 
    
    public static void paint(Graph<Vertex, Edge> graph){
        pGraph = new JGraph( new JGraphModelAdapter(graph));
        JScrollPane scrollPane = new JScrollPane(pGraph);
        
        GraphPainter applet = new GraphPainter();
        applet.init();
        
        JFrame frame = new JFrame();
        frame.getContentPane().add(applet);
        frame.setTitle("Graphentheretische Konzeptte und Algorithmen - HAW HAmburg - SS2012");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        
         
    }
    
    @Override
    public void init(){
        adjustDisplaySettings(pGraph);
        getContentPane().add(pGraph);
        resize(DEFAULT_SIZE);
    }
    
    private void adjustDisplaySettings(JGraph jg)
    {
        System.err.println(jg);
        jg.setPreferredSize(DEFAULT_SIZE);

        Color c = DEFAULT_BG_COLOR;
        String colorStr = null;

        try {
            colorStr = getParameter("bgcolor");
        } catch (Exception e) {
        }

        if (colorStr != null) {
            c = Color.decode(colorStr);
        }

        jg.setBackground(c);
    }
}
