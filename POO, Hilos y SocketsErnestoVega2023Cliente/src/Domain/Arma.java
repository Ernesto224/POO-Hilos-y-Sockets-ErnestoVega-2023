/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Ernesto
 */
public abstract class Arma {

    protected Punto2D posicion;
    protected BufferedImage skin;
    protected int distancia;
    protected int danio;

    public Arma() {
        this.posicion = new Punto2D(0, 0);
        this.danio = 0;
    }//constructor

    //metodos de acceso    
    public Punto2D getPosicion() {
        return posicion;
    }

    public int getDanio() {
        return danio;
    }

    public void setPosiciones(Personaje personaje) {
        this.getPosicion().x = personaje.getColicion().getX() + (personaje.getColicion().getWidth() * personaje.getDireccionX());
        this.getPosicion().y = personaje.getColicion().getY() + (personaje.getColicion().getHeight() * personaje.getDireccionY());
    }//coloca las armas en una poscion respecto al jugador

    public abstract void realizarDanio(Personaje personaje); //metodo abtracto para realizar danio a un jugador

    public void dibujar(Graphics g) {
        g.drawImage(this.skin, (int) this.posicion.getX(), (int) this.posicion.getY(), null);
    }//metodo de dibujado

}//class
