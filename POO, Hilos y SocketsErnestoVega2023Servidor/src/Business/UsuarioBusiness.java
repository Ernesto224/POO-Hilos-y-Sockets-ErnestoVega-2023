/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business;

import Data.UsuarioData;
import Domain.Usuario;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.JDOMException;

/**
 *
 * @author Ernesto
 */
public class UsuarioBusiness {

    private UsuarioData usuarioData;

    public UsuarioBusiness() {
        try {
            this.usuarioData = new UsuarioData();
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(UsuarioBusiness.class.getName()).log(Level.SEVERE, null, ex);
        }//captura excepciones de la calse jugador data//captura excepciones de la calse jugador data
    }//constructor

    public void registrarJugador(Usuario jugador) throws NoSuchAlgorithmException, IOException {
        //se crea un nuevo jugador colocando como atributo la contrasenia
        this.usuarioData.registrar(jugador);//llama a data para registrar un jugador
    }//metodo para registrar un  nuevo jugador

    public Usuario recuperarUsurio(String nombreUsurio) {
        return this.usuarioData.recuperar(nombreUsurio);
    }//recupera la lista de todos los jugadores realizados

    public boolean usuarioUnico(Usuario jugador) {
        return this.usuarioData.usuarioUnico(jugador);//llama a verificar al jugador y retornara un string con el erro que presente o con la correccion
    }//metodo exclusivo de data para verificar nombres de usuario no repetidos

    public boolean usuarioInicia(Usuario usuario) {
        return this.usuarioData.usuarioInicia(usuario);
    }//metodo exclusivo de data para verificar nombres de usuario no repetidos y contrasenia

    public synchronized void actualizar(Usuario usuario) throws IOException, NoSuchAlgorithmException {
        this.usuarioData.actualizar(usuario);//actualiza los datos de un usuario
    }//metodo que actualiza la cantidad de lingotes de un jugador

    public String ranking() throws IOException, NoSuchAlgorithmException {
        return this.usuarioData.ranking();
    }//ordena a toda la lista de jugadores segun los lingotes que pocen

}//class
