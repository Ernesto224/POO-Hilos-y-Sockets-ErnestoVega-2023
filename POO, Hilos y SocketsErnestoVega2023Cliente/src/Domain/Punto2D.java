/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

/**
 *
 * @author Ernesto
 */
public class Punto2D extends java.awt.geom.Point2D{
    
    public double x;
    public double y;
    
    public Punto2D(double x, double y) {
        this.x = x;
        this.y = y;
    }//constructor
   
    @Override
    public double getX() {
       return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
}//class 5/16/2023
