/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gComponents;

import java.util.Objects;

/**
 *
 * @author Tobi
 */
class EdgeImpl implements Edge{

    private int value,  flow;
    private String name;
    private static int counter;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EdgeImpl other = (EdgeImpl) obj;
        if (this.value != other.value) {
            return false;
        }
        if (this.flow != other.flow) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public String toString() {
        return "Edge{"+value +";" + flow + ";" + name + '}';
    }
    
    static Edge create(int value, String name) {
        return create(value, name, 0);
    }
    
    static Edge create(int value) {
        return create(value,String.format("edge%d",counter++));
    }
    
    static Edge create(int value, String name, int flow) {
        return new EdgeImpl(value, name, flow);
    }
   
    

    private EdgeImpl(int value, String name, int flow) {
        this.value=value;
        this.name=name;
        this.flow=flow;
    }

    @Override
    public int value() {
        return value;
    }

    @Override
    public int flow() {
        return flow;
    }

    @Override
    public String name() {
        return new String(name);
    }
    
}
