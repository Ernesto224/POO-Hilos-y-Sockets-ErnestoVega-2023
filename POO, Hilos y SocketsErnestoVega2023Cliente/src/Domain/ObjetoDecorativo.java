/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

import Utility.Disenio;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author Ernesto
 */
public abstract class ObjetoDecorativo {

    protected Punto2D posicion;
    protected Rectangle colicion;
    protected BufferedImage textura;

    public ObjetoDecorativo(Punto2D posicion) {
        this.posicion = posicion;
        this.colicion = new Rectangle((int) this.posicion.x, (int) this.posicion.y, Disenio.OBJETOS_DECORATIVOS, Disenio.OBJETOS_DECORATIVOS);
    }//contractor

    //metodos de colicion
    public Rectangle getColicion() {
        return colicion;
    }

    public boolean colicion(Personaje personaje) {
        //si hay colicion con el objeto decorativo niega el acceso
        return this.colicion.intersects(personaje.getColicion().getX() + (5 * personaje.getDireccionX()),
                personaje.getColicion().getY() + (5 * personaje.getDireccionY()),
                personaje.getColicion().getHeight() + (5 * personaje.getDireccionX()),
                personaje.getColicion().getHeight() + (5 * personaje.getDireccionY()));
    }//metodo verifica si las coliciones de un objeto intercecan con el objeto decorativo

    public boolean seleccionado(Punto2D posicionMouse) {
        return this.colicion.contains(posicionMouse);//calcula si el punto en cuestion esta dentro de este objeto
    }//objeto fue seleccionada

    public abstract String retornarTipo();//retorna el tipo de la carta solo para enviarla al servidor y almacenar la informacion 

    public void dibujar(Graphics g) {
        g.drawImage(this.textura, (int) this.posicion.x, (int) this.posicion.y, Disenio.OBJETOS_DECORATIVOS, Disenio.OBJETOS_DECORATIVOS, null);
    }//metodo de dibujado

}//class
