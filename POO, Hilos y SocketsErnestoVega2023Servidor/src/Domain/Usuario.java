/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

import java.util.ArrayList;

/**
 *
 * @author Ernesto
 */
public class Usuario {

    private final String nombre;
    private String contrasenia;
    private int diamantes;
    private final ArrayList<Amigo> amigos;
    private final ArrayList<SolicitudAmistad> solicitudes;

    public Usuario(String nombreUsuario, String contrasenia) {
        this.nombre = nombreUsuario;
        this.contrasenia = contrasenia;
        this.diamantes = 0;
        this.amigos = new ArrayList<>();
        this.solicitudes = new ArrayList<>();
    }//constructor 

    //metodos de acceso 
    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public int getDiamantes() {
        return diamantes;
    }

    public void setDiamantes(int diamantes) {
        this.diamantes = diamantes;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Amigo> getAmigos() {
        return amigos;
    }

    public ArrayList<SolicitudAmistad> getSolicitudes() {
        return solicitudes;
    }

    public void agregarAmigo(Amigo amigoNuevo) {
        if (!this.verificarAmistadExitente(amigoNuevo)) {
            this.amigos.add(amigoNuevo);
        }
    }

    private boolean verificarAmistadExitente(Amigo amigoNuevo) {
        //se utiliza un recorrido para verificar que ya existe la relacion de amistad
        if (this.amigos.isEmpty()) {
            return false;
        }
        for (int i = 0; i < this.amigos.size(); i++) {
            if (this.amigos.get(i).getUserName().equals(amigoNuevo.getUserName())) {
                return true;
            }
        }
        return false;
    }

    public void agregarSolicitud(SolicitudAmistad solicitudAmistadNueva) {
        if (!this.verificarSolicitudExitente(solicitudAmistadNueva) && !this.verificarYaEsAmigo(solicitudAmistadNueva)) {
            this.solicitudes.add(solicitudAmistadNueva);
        }
    }

    private boolean verificarSolicitudExitente(SolicitudAmistad solicitudAmistadNueva) {
        //se utiliza un recorrido para verificar que ya existe la solicitud de amistad
        if (this.solicitudes.isEmpty()) {
            return false;
        }
        for (int i = 0; i < this.solicitudes.size(); i++) {
            if (this.solicitudes.get(i).getSender().equals(solicitudAmistadNueva.getSender())) {
                return true;
            }
        }
        return false;
    }

    private boolean verificarYaEsAmigo(SolicitudAmistad solicitudAmistadNueva) {
        //se utiliza un recorrido para verificar que ya existe la solicitud de amistad
        if (this.amigos.isEmpty()) {
            return false;
        }
        for (int i = 0; i < this.amigos.size(); i++) {
            if (this.amigos.get(i).getUserName().equals(solicitudAmistadNueva.getSender())) {
                return true;
            }
        }
        return false;
    }
    
    public void quitarSolicitud(String solicitud) {
        //elimina una solicitud de amistad cuando esta es aceptada o rechazada
        for (int i = 0; i < this.solicitudes.size(); i++) {
            if (this.solicitudes.get(i).getSender().equals(solicitud)) {
                this.solicitudes.remove(i);
            }
        }
    }

}//class
