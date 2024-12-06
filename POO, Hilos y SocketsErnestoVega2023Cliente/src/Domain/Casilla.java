/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

import Utility.Disenio;
import Utility.GestionXML;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author Ernesto
 */
public abstract class Casilla {

    protected Punto2D posicion;
    protected final Rectangle colicion;
    protected BufferedImage textura;
    protected ObjetoDecorativo objetoDecorativo;
    protected Diamante diamante;
    protected double limitacionMovimiento;

    public Casilla(Punto2D posicion) {
        this.posicion = posicion;
        this.colicion = new Rectangle((int) this.posicion.x, (int) this.posicion.y, Disenio.CASILLA, Disenio.CASILLA);
        this.limitacionMovimiento = 0;
    }//constructor

    //metodos de colicion
    public ObjetoDecorativo getObjetoDecorativo() {
        return objetoDecorativo;
    }

    public void setObjetoDecorativo(ObjetoDecorativo objetoDecorativo) {
        this.objetoDecorativo = objetoDecorativo;
    }

    public Punto2D getPosicion() {
        return posicion;
    }

    public double getLimitacionMovimiento() {
        return limitacionMovimiento;
    }

    public void setDiamante(int id) {
        this.diamante = new Diamante(id, this.posicion);
    }

    public void quitar() {
        this.diamante = null;
    }

    public void quitarDimante() {
        this.diamante = null;
    }

    public Rectangle getColicion() {
        return colicion;
    }

    public void actualizarClicion() {
        this.colicion.setLocation((int) this.posicion.getX(), (int) this.posicion.getY());
        if (this.objetoDecorativo != null) {//actualiza la poscion de las coliciones de los objetos decorativos
            this.objetoDecorativo.getColicion().setLocation((int) this.posicion.getX(), (int) this.posicion.getY());
        } else if (this.diamante != null) {
            this.diamante.actualizar(this.posicion);
        }//actualiza la poscion de las coliciones de los  diamantes
    }//actualiza las coliciones de la casilla

    public abstract void aplicarEfecto(Personaje personaje);//aplica un efecto sobre un personje

    public boolean contienePersonaje(Personaje personaje) {
        return this.colicion.contains(personaje.getPosicion());
        //calcula si el personage en cuestion esta dentro de este objeto o lo esta tocando
    }//esta casilla fue seleccionada

    public boolean negarAcceso(Personaje personaje) {
        //verifica si existe colicion    
        return this.objetoDecorativo != null && this.objetoDecorativo.colicion(personaje);
    }//verifica si existe una colicion con un jugador

    public boolean aceptarDiamante() {
        return this.objetoDecorativo == null;
    }//varifica si esta casilla puede tener un diamante

    public void tomarDiamante(Personaje personaje) {
        if (this.diamante != null && this.diamante.jugadorTomaDiamante(personaje)) {
            ClienteSingleton.getInstance().enviarDatos(GestionXML.xmlToString(GestionXML.agregarAccionSimple(Solicitud.TOMAR_DIAMANTE.name(),
                    String.valueOf(this.diamante.getId()))));
            this.diamante = null;
        }//envia un mensaje al servidor para tratar de adquirir este diamante
    }//permite a un jugador tomar un diamante

    public boolean esSeleccionada(Punto2D posicion) {
        return this.colicion.contains(posicion);//se utiliza para verificar que la casilla fue seleccionada
    }//esta casilla fue seleccionada

    public abstract int retornarTipo();//retorna el tipo de la carta solo para enviarla al servidor y almacenar la informacion 

    public void dibujar(Graphics g) {
        g.drawImage(this.textura, (int) this.posicion.x, (int) this.posicion.y, Disenio.CASILLA, Disenio.CASILLA, null);
        if (this.objetoDecorativo != null) {
            this.objetoDecorativo.dibujar(g);
        } else if (this.diamante != null) {//valida que exista el objeto decorativo
            this.diamante.dibujar(g);
        }//dibuja el diamante de esta casilla
    }//metodo de dibujado

}//class
