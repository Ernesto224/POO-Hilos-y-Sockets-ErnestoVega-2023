/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Data;

import Domain.Amigo;
import Domain.Usuario;
import Domain.SolicitudAmistad;
import Utility.Ruta;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author Ernesto
 */
public class UsuarioData {

    private final Document documento;
    private final Element raiz;
    private final String ruta;

    public UsuarioData() throws JDOMException, IOException {
        this.ruta = Ruta.RUTA_USUARIOS;
        File file = new File(this.ruta);//crea un nuevo archivo
        if (file.exists()) {
            SAXBuilder sAXBuilder = new SAXBuilder(); //nos permite construir o recuperar la informacion a nivel de documento recuperar la meta data
            sAXBuilder.setIgnoringBoundaryWhitespace(true);//IGNORA CONTENIDOS COMO ESPACIOS 
            this.documento = sAXBuilder.build(this.ruta);//construya el documento tomando como base el documento en esta ruta
            this.raiz = this.documento.getRootElement();//documento  eme su rais y guardelo aqui guarda la base de los documentos 
        } else {
            this.raiz = new Element(Ruta.RUTA_USUARIOS);//crea un nuevo elemento raiz
            this.documento = new Document(this.raiz);//Carga la raiz en memoria
            guardarXML();
        }//si existe leeo la raiz y la estructura y si no la creo
    }//constructor

    private void guardarXML() throws FileNotFoundException, IOException {
        XMLOutputter xMLOutputter = new XMLOutputter();
        //permite hacer output de xml
        xMLOutputter.output(this.documento, new PrintWriter(this.ruta));//inicia el escritor en la ruta
        //volco el archivo xml ya lo tendria guardo        
    }//vuelca la informacion

    public void registrar(Usuario usuarioNuevo) throws NoSuchAlgorithmException, IOException {
        //se crea un nuevo usuarioNuevo colocando como atributo la contrasenia
        Element usuario = new Element("usuario");
        usuario.setAttribute("contrasenia", usuarioNuevo.getContrasenia());

        //se agrega un nombre de usuarioNuevo
        Element nombre = new Element("nombre");
        nombre.addContent(usuarioNuevo.getNombre());
        usuario.addContent(nombre);

        //se agrega la cantidad de diamantes
        Element diamantes = new Element("diamantes");
        diamantes.addContent(String.valueOf(usuarioNuevo.getDiamantes()));
        usuario.addContent(diamantes);

        //se agregan todos los amigos
        Element amigos = new Element("amigos");
        if (!usuarioNuevo.getAmigos().isEmpty()) {
            for (int i = 0; i < usuarioNuevo.getAmigos().size(); i++) {
                Element amigo = new Element("amigo");
                amigo.addContent(usuarioNuevo.getAmigos().get(i).getUserName());
                amigos.addContent(amigo);
            }//recorre la lista de amigos del usurio
        }//guarda a los amigos de una usuario
        usuario.addContent(amigos);
        
        //se agregan las solicitudes de amistad de otros usuarios
        Element solicitudes = new Element("solicitudes");
        if (!usuarioNuevo.getSolicitudes().isEmpty()) {
            for (int i = 0; i < usuarioNuevo.getSolicitudes().size(); i++) {
                Element solicitud = new Element("solicitud");
                solicitud.setAttribute("remitente", usuarioNuevo.getSolicitudes().get(i).getSender());
                solicitud.setAttribute("destinatario", usuarioNuevo.getSolicitudes().get(i).getAddressee());
                solicitudes.addContent(solicitud);
            }//recorre la lista de solicitudes del usurio
        }//guarda a las solicitudes de una usuario
        usuario.addContent(solicitudes);

        this.raiz.addContent(usuario);//se agrega el usuarioNuevo a la raiz
        this.guardarXML();
    }//metodo para registrar un nuevo usuario

    public Usuario recuperar(String nombre) {

        java.util.List listaJugadores = this.raiz.getChildren();
        for (Object jugadorRecuperado : listaJugadores) {
            Element usuario = (Element) jugadorRecuperado;

            if (usuario.getChildText("nombre").equals(nombre)) {

                Usuario usurioCargado = new Usuario(usuario.getChildText("nombre"), usuario.getAttributeValue("contrasenia"));
                usurioCargado.setDiamantes(Integer.parseInt(usuario.getChildText("diamantes")));

                List listaAmigos = usuario.getChild("amigos").getChildren();
                if (listaAmigos != null) {
                    for (Object listaAmigo : listaAmigos) {
                        Element amigo = (Element) listaAmigo;
                        usurioCargado.getAmigos().add(new Amigo(amigo.getValue()));//extrae el usuario amigo
                    }//recorre los amigos guardados
                }//si el usuario tiene una lista de amigos que recuperar

                List listaSolicitudes = usuario.getChild("solicitudes").getChildren();
                if (listaSolicitudes != null) {
                    for (Object listaSolicitud : listaSolicitudes) {
                        Element solicitud = (Element) listaSolicitud;
                        usurioCargado.getSolicitudes().add(new SolicitudAmistad(solicitud.getAttributeValue("destinatario")
                                ,solicitud.getAttributeValue("remitente")));//extrae las solocitudes
                    }//recorre las solicitudes guardadas
                }//si el usuario tiene una lista de solicitudes que recuperar

                return usurioCargado;

            }//valida que sea el usurio buscado

        }//recorre la lista de jugadores

        return null;
    }//recupera la lista de todos los jugadores realizados

    public boolean usuarioUnico(Usuario usuario) {

        java.util.List listaJugadores = this.raiz.getChildren();
        if (listaJugadores == null) {
            return false;
        }//valida que el primer registro no de errores
        for (Object jugadorRecuperado : listaJugadores) {
            Element usuarioRecuperado = (Element) jugadorRecuperado;
            if (usuarioRecuperado.getChildText("nombre").equals(usuario.getNombre())) {
                return true;
            }//valida que sea el usurio buscado
        }//recorre la lista para saber si existe un usuario un la informacion en cuestion

        return false;
    }//metodo exclusivo de data para verificar nombres de usuario no repetidos
    
    public boolean usuarioInicia(Usuario usuario) {

        java.util.List listaJugadores = this.raiz.getChildren();
        if (listaJugadores == null) {
            return false;
        }//valida que el primer registro no de errores
        for (Object jugadorRecuperado : listaJugadores) {
            Element usuarioRecuperado = (Element) jugadorRecuperado;
            if (usuarioRecuperado.getChildText("nombre").equals(usuario.getNombre())
                    && usuarioRecuperado.getAttributeValue("contrasenia").equals(usuario.getContrasenia())) {
                return true;
            }//valida que sea el usurio buscado
        }//recorre la lista para saber si existe un usuario un la informacion en cuestion

        return false;
    }//metodo exclusivo de data para verificar nombres de usuario no repetidos y contrasenia

    public void actualizar(Usuario usuario) throws IOException, NoSuchAlgorithmException {

        int contador = 0;
        java.util.List listaJugadores = this.raiz.getChildren();
        for (Object jugadorRecuperado : listaJugadores) {
            Element usuarioRecuperado = (Element) jugadorRecuperado;

            if (usuarioRecuperado.getChildText("nombre").equals(usuario.getNombre())) {
                break;
            }//valida que sea el usurio buscado
            contador++;
        }//recorre la lista de jugadores

        this.raiz.removeContent(contador);
        this.registrar(usuario);
    }//metodo que actualiza la cantidad de lingotes de un usuario

    public String ranking() throws IOException, NoSuchAlgorithmException {
        ArrayList<Usuario> usurios = this.recuperarLista();
        Usuario aux;
        String ranking = "";
        for (int i = 0; i < usurios.size() - 1; i++) {
            for (int j = 0; j < usurios.size() - i - 1; j++) {
                if (usurios.get(j + 1).getDiamantes() > usurios.get(j).getDiamantes()) {
                    aux = usurios.get(j + 1);//se extrae el elemto del arreglo
                    usurios.set(j + 1, usurios.get(j));//coloca el valor menor en la posicion del valor extraido
                    usurios.set(j, aux);//coloca el elemto extraido en la posicion del elemto menor
                }//compara y cambia los objetos
            }//fin for
        }//fin for
        for (Usuario usurio : usurios) {
            ranking += "Nombre usuario = " + usurio.getNombre() + " Diamantes = " + usurio.getDiamantes() + ",";
        } //for que manda a volver a escribir a todos los jugadores ya ordenados por cantidad de diamantes
        return ranking;
    }//ordena a toda la lista de jugadores segun los lingotes que pocen

    private ArrayList<Usuario> recuperarLista() {

        ArrayList<Usuario> jugadoresRegistrados = new ArrayList<>();
        java.util.List listaJugadores = this.raiz.getChildren();
        for (Object jugadorRecuperado : listaJugadores) {
            Element usuario = (Element) jugadorRecuperado;
            jugadoresRegistrados.add(this.recuperar(usuario.getChildText("nombre")));//recupera la informacion guardada del usuario
        }//recorre la lista de jugadores

        return jugadoresRegistrados;
    }//recupera la lista de todos los jugadores realizados

}//class
