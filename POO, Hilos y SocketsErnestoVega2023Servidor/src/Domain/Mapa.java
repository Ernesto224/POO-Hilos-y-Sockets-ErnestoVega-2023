/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

import java.io.Serializable;

/**
 *
 * @author Ernesto
 */
public class Mapa implements Serializable{
    
    private final String nombreCreador;
    private final String nombreMapa;
    private final int filas;
    private final int columnasa;
    private final String mapa;//guarda el caracter con la informacion del mapa 

    public Mapa(String nombreCreador, String nombreMapa, int filas, int columnasa, String mapa) {
        this.nombreCreador = nombreCreador;
        this.nombreMapa = nombreMapa;
        this.filas = filas;
        this.columnasa = columnasa;
        this.mapa = mapa;
    }//constructor

    //metodos de acceso
    public String getNombreCreador() {
        return nombreCreador;
    }

    public String getNombreMapa() {
        return nombreMapa;
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnasa() {
        return columnasa;
    }

    public String getMapa() {
        return mapa;
    }
    
}//class
