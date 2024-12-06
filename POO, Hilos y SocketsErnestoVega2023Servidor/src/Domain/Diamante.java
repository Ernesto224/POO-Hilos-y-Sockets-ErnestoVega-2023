/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

/**
 *
 * @author Ernesto
 */
public class Diamante {
    
    private int id;
    private int fila;
    private int columna;

    public Diamante(int id) {
        this.id=id;
    }//constructor

    //metodo de acceso
    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public int getId() {
        return id;
    }

    public void generarPosicion(int filaMapa, int columnaMapa){
      this.fila=(int) (Math.random()*filaMapa);
      this.columna=(int) (Math.random()*columnaMapa);
    }//genera una fila y una columna para el diamante

}//class
