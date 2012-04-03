/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gComponents;

/**
 *
 * @author Tobi
 */
public class GFactory {
    
    public static Edge edge(int value){
        return EdgeImpl.create(value);
    }
    public static Edge edge(int value, String name){
        return EdgeImpl.create(value, name);
    }
    
    public static Edge edge(int value, String name, int flow){
        return EdgeImpl.create(value, name, flow);
    }
    
    public static Vertex vertex(String name){
        return VertexImpl.create(name);
    }
    
    public static Vertex vertex(String name, int value){
        return VertexImpl.create(name, value);
    }
}
