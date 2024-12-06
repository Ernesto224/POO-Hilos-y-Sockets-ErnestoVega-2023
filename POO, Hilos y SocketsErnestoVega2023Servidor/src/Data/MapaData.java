/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Data;

import Domain.Mapa;
import Utility.Ruta;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ernesto
 */
public class MapaData {

    private final File archivo;
    private ObjectOutputStream escritor;
    private ObjectInputStream lector;

    public MapaData() {
        this.archivo = new File(Ruta.RUTA_MAPAS);
    }//constructor

    public boolean registrarMapa(Mapa nuevoMapa) throws FileNotFoundException, IOException, ClassNotFoundException {
        List<Mapa> mapas = new ArrayList<>();//crea la lista de almacenamiento
        if (this.archivo.exists() && this.archivo.isFile()) {//si el archivo existe y esta en buen estado se llama a recuperar los jugadores ya existentes
            mapas = (ArrayList<Mapa>) cargarUsuarios();//carga la lista
        }
        mapas.add(nuevoMapa);//se agrega el nuevo jugador
        this.escritor = new ObjectOutputStream(new FileOutputStream(archivo));//habre el flujo de datos
        escritor.writeUnshared(mapas);//vuelca la informacion
        escritor.close();//cierra el flujo de datos
        return true;//retorna verdadero
    }//registrar un nuevo mapa

    public Mapa recuperarMapa(String nombreUsuario, String nombreMapa) throws IOException, FileNotFoundException, ClassNotFoundException {
        List<Mapa> mapas = (ArrayList<Mapa>) cargarUsuarios();
        if (mapas != null) {
            for (int i = 0; i < mapas.size(); i++) {
                if (mapas.get(i).getNombreCreador().equals(nombreUsuario)
                        && mapas.get(i).getNombreMapa().equals(nombreMapa)) {//verifica que el mapa que se esta analizando sea el mapa buscado
                    
                    return mapas.get(i);//devuelve el mapa buscado
                } else if (mapas.get(i).getNombreMapa().equals(nombreMapa) && (nombreMapa.equals("predeterminado1")
                        || nombreMapa.equals("predeterminado2"))) {//por defecto toma en cuenta los mapas predeterminados
                    
                    return mapas.get(i);//devuelve el mapa buscado
                }
            }//recorre la lista de mapas
        }//verifica si existen mapas
        return null;
    }//metodo para recuperar un mapa
    
    public String listaMapas(String nombreUsuario) throws IOException, FileNotFoundException, ClassNotFoundException {
        List<Mapa> mapas = (ArrayList<Mapa>) cargarUsuarios();
        String mapasLista = "";
        if (mapas != null) {
            for (int i = 0; i < mapas.size(); i++) {
                if (mapas.get(i).getNombreCreador().equals(nombreUsuario)) {//verifica que el mapa que se esta analizando sea el mapa buscado
                    mapasLista += mapas.get(i).getNombreMapa() + ",";//devuelve el mapa buscado
                } else if (mapas.get(i).getNombreMapa().equals("predeterminado1")
                        || mapas.get(i).getNombreMapa().equals("predeterminado2")) {//por defecto toma en cuenta los mapas predeterminados
                    mapasLista += mapas.get(i).getNombreMapa() + ",";//devuelve el mapa buscado
                }
            }//recorre la lista de mapas
        }//verifica si existen mapas
        return mapasLista;
    }//metodo para recuperar un mapa

    private List<Mapa> cargarUsuarios() throws FileNotFoundException, IOException, ClassNotFoundException {
        List<Mapa> mapas = new ArrayList<>();//crea la lista de almacenamiento
        if (this.archivo.exists() && this.archivo.isFile()) {
            this.lector = new ObjectInputStream(new FileInputStream(this.archivo));//habre el flujo de datos
            Object aux = lector.readObject();//lee el archivo
            mapas = (List< Mapa>) aux;//carga los jugadores
            this.lector.close();//cierra el flujo de datos
            return mapas;//retorna los mapas
        }//si el achivo existe y esta en bues estado carga el contenido
        return null;
    }//metodo para recuperar informacion

}//class
