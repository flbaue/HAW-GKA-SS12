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
public class IntSquareMatrix {
    int[] values;
    int dim;
    
    public IntSquareMatrix(int m){
        values=new int[m*m];
        dim = m;
    }
    
    public IntSquareMatrix(int m, int defaultValue){
        values=new int[m*m];
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
    
    public int get(int i, int j){
        return values[cP(i, j)];
    }
    
    public void set(int i, int j, int newValue){
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
