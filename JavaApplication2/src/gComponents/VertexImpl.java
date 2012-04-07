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
class VertexImpl implements Vertex{

    static Vertex create(String name) {
        return create(name, 0);
    }

    private int value;
    private String name;
    static Vertex create(String name, int value) {
        return new VertexImpl(name, value);
    }

    private VertexImpl(String name, int value) {
        this.name=name;
        this.value=value;
    }

    @Override
    public int value() {
        return this.value;
    }

    @Override
    public String name() {
        return new String(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VertexImpl other = (VertexImpl) obj;
        if (this.value != other.value) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public String toString() {
        return "Vertex{n="+name+", v="+value+"}";
    }
    
    
    
}
