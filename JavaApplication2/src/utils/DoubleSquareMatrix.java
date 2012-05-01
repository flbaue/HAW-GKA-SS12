/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;


import java.util.Arrays;


/**
 *
 * @author Tobi
 */
public class DoubleSquareMatrix {
    double[] values;
    int dim;
    
    public DoubleSquareMatrix(int m){
        values=new double[m*m];
        dim = m;
    }
    
    public DoubleSquareMatrix(int m, double defaultValue){
        values=new double[m*m];
        dim = m;
        Arrays.fill(values, defaultValue);
    }   
    //calculate Position
    private int cP(int i, int j){
        int result = dim*i+j;
        if (result>=values.length){
            throw new IndexOutOfBoundsException();
        }
        return result;
    }
    
    public double get(int i, int j){
        return values[cP(i, j)];
    }
    
    public void set(int i, int j, double newValue){
        values[cP(i, j)] = newValue;
    }
    
    public int getDimension(){
        return dim;
    }
    
    @Override
    public String toString(){
        String result = "";
        for (int m = 0; m < dim; m++) {
            result += m+Arrays.toString(Arrays.copyOfRange(values, m*dim, (m*dim)+dim))+'\n';
        }
        return result;
    }
}
