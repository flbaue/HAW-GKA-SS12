/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Tobi
 */
public interface FlowGraph<V,E> {
    public void setEdgeCapacity(E e, double capacity);
    public void setEdgeFlow(E e, double flow);
    public double getEdgeCapacity(E e);
    public double getEdgeFlow(E e);
}
