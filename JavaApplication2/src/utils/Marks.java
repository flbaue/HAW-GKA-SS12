/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Objects;

/**
 *
 * @author Tobi
 */
public class Marks<V> {
    public static final int FORWARD = 1;
        public static final int BACKWARD = -1;
        private int edgeDirection;
        private V prev;
        private double delta;
        
        public Marks(int edgeDirection,V prev, double delta){
            if ((edgeDirection!=FORWARD)&&(edgeDirection!=BACKWARD)){
                throw new Error("edgeDirection has to be 1 or -1");
            }
            this.edgeDirection = edgeDirection;
            this.prev = prev;
            this.delta = delta;
        }

        public double getDelta() {
            return delta;
        }

        public int getEdgeDirection() {
            return edgeDirection;
        }

        public V getPrev() {
            return prev;
        }

        public void setDelta(double delta) {
            this.delta = delta;
        }

        public void setEdgeDirection(int edgeDirection) {
            this.edgeDirection = edgeDirection;
        }

        public void setPrev(V prev) {
            this.prev = prev;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Marks other = (Marks) obj;
            if (this.edgeDirection != other.edgeDirection) {
                return false;
            }
            if (!Objects.equals(this.prev, other.prev)) {
                return false;
            }
            if (Double.doubleToLongBits(this.delta) != Double.doubleToLongBits(other.delta)) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 29 * hash + this.edgeDirection;
            hash = 29 * hash + Objects.hashCode(this.prev);
            hash = 29 * hash + (int) (Double.doubleToLongBits(this.delta) ^ (Double.doubleToLongBits(this.delta) >>> 32));
            return hash;
        }
        
        @Override
        public String toString(){
            return String.format("[Markierung: %s%s, %f]", (edgeDirection==FORWARD)?"+":"-",(prev==null)?"null":prev.toString(), delta);
        }
}
