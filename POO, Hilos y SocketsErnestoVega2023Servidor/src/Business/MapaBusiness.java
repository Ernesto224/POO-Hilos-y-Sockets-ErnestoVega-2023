/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business;

import Data.MapaData;
import Domain.Mapa;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Ernesto
 */
public class MapaBusiness {
    
    private final MapaData mapaData;
    
    public MapaBusiness() {
        this.mapaData = new MapaData();
    }//constructor
    
    public synchronized boolean registrarMapa(Mapa nuevoMapa) throws FileNotFoundException, IOException, ClassNotFoundException {
        return this.mapaData.registrarMapa(nuevoMapa);
    }//registrar un nuevo mapa

    public Mapa recuperarMapa(String nombreUsuario, String nombreMapa) throws IOException, FileNotFoundException, ClassNotFoundException {
        return this.mapaData.recuperarMapa(nombreUsuario, nombreMapa);
    }//metodo para recuperar un mapa
    
    public String listaMapas(String nombreUsuario) throws IOException, FileNotFoundException, ClassNotFoundException {
        return this.mapaData.listaMapas(nombreUsuario);
    }//metodo para recuperar un mapa
    
}//class
