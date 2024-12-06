/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utility;

import Domain.Amigo;
import Domain.ClienteDelServidor;
import Domain.Diamante;
import Domain.Jugador;
import Domain.Mapa;
import Domain.SolicitudAmistad;
import Domain.Usuario;
import java.io.IOException;
import java.io.StringReader;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author Nelson
 */
public class GestionXML {

    public static Element stringToXML(String stringMensaje) throws JDOMException, IOException {
        SAXBuilder saxBuilder = new SAXBuilder();
        StringReader stringReader = new StringReader(stringMensaje);
        Document doc = saxBuilder.build(stringReader);
        return doc.getRootElement();
    } // stringToXML  combierte un string en un xml

    public static String xmlToString(Element element) {
        XMLOutputter outputter = new XMLOutputter(Format.getCompactFormat());
        String xmlStringElement = outputter.outputString(element);
        xmlStringElement = xmlStringElement.replace("\n", "");
        return xmlStringElement;
    } // xmlToString convierte un xml en string

    public static Element crearMensajeProtocolo(String accion) {
        Element eProtocolo = new Element("Protocolo");
        eProtocolo.setAttribute("accion", accion);
        return eProtocolo;
    } // retorna una accion de protocolo sin el dato solo la accion

    public static Element agregarAccionSimple(String accion, String dato) {
        Element eDato = new Element("dato");
        eDato.addContent(dato);
        Element eProtocolo = crearMensajeProtocolo(accion);
        eProtocolo.addContent(eDato);
        return eProtocolo;
    } //retorna un xml con la accion a ejecutar de protocolo y el dato a enviar

    public static Element agregarAccionSimple(String accion, Element dato) {
        Element eProtocolo = crearMensajeProtocolo(accion);
        eProtocolo.addContent(dato);
        return eProtocolo;
    } //retorna un xml con la accion a ejecutar de protocolo y el dato a enviar

    public static Element enviarMapa(String accion, String mapa) {
        Element eProtocolo = crearMensajeProtocolo(accion);
        eProtocolo.addContent(mapa);
        return eProtocolo;
    } //retorna un xml con la accion a ejecutar de protocolo y el dato a enviar

    public static Element enviarInformacionPartida(String accion, ArrayList<Amigo> amigos, String listaMapas) {
        Element eProtocolo = crearMensajeProtocolo(accion);
        Element listaAmigos = new Element("amigos");
        for (Amigo amigo : amigos) {
            listaAmigos.addContent(amigo.getUserName() + ",");
        }//recorre la lista de amigos
        Element mapas = new Element("mapas");
        mapas.addContent(listaMapas);
        eProtocolo.addContent(listaAmigos);
        eProtocolo.addContent(mapas);
        return eProtocolo;
    } //retorna un xml con la accion a ejecutar de protocolo y el dato a enviar

    public static Element enviarSolicitudes(String accion, ArrayList<SolicitudAmistad> solicitudAmistads) {
        Element eProtocolo = crearMensajeProtocolo(accion);
        Element solicitudes = new Element("Solicitudes");
        for (SolicitudAmistad remitente : solicitudAmistads) {
            solicitudes.addContent(remitente.getSender() + ",");
        }//recorre la lista de solicitudes gardadas del usuario
        eProtocolo.addContent(solicitudes);
        return eProtocolo;
    } //retorna un xml con la accion a ejecutar de protocolo y el dato a enviar

    public static Element enviarDiamantes(String accion, ArrayList<Diamante> diamantes) {
        Element eProtocolo = crearMensajeProtocolo(accion);
        Element listaDiamantes = new Element("diamantes");
        for (Diamante diamante : diamantes) {
            Element diamanteEnvio = new Element("diamante");
            diamanteEnvio.setAttribute("id", String.valueOf(diamante.getId()));
            Element fila = new Element("fila");
            fila.addContent(String.valueOf(diamante.getFila()));
            Element columna = new Element("columna");
            columna.addContent(String.valueOf(diamante.getColumna()));
            diamanteEnvio.addContent(fila);
            diamanteEnvio.addContent(columna);
            listaDiamantes.addContent(diamanteEnvio);
        }//recorre la lista de diamantes
        eProtocolo.addContent(listaDiamantes);
        return eProtocolo;
    } //envia los diamantes al cliente para que este los verifique

    public static Element eliminarDiamante(String accion, Diamante diamante) {
        Element eProtocolo = crearMensajeProtocolo(accion);
        Element diamanteEnvio = new Element("diamante");
        Element fila = new Element("fila");
        fila.addContent(String.valueOf(diamante.getFila()));
        Element columna = new Element("columna");
        columna.addContent(String.valueOf(diamante.getColumna()));
        diamanteEnvio.addContent(fila);
        diamanteEnvio.addContent(columna);
        eProtocolo.addContent(diamanteEnvio);
        return eProtocolo;
    } //envia los diamantes al cliente para que este los verifique

    public static Mapa leerMapa(Element datos) {
        Element informacionMapa = datos.getChild("Mapa");
        return new Mapa(informacionMapa.getAttributeValue("creador"), informacionMapa.getAttributeValue("nombre"),
                Integer.parseInt(informacionMapa.getAttributeValue("filas")), Integer.parseInt(informacionMapa.getAttributeValue("filas")),
                GestionXML.xmlToString(informacionMapa));
    }//guarda un mapa de un jugador

    public static Usuario leerUsurio(Element datos) {
        Element usurioResivido = datos.getChild("Usurio");
        return new Usuario(usurioResivido.getAttributeValue("Nombre"), usurioResivido.getAttributeValue("Contrasenia"));
    }//metodo para cargar el usurio que viene en una solicitud

    public static Jugador leerJugador(Element datos) {
        Element jugador = datos.getChild("jugador");
        Jugador jugadorNuevo = new Jugador(jugador.getAttributeValue("personaje"), jugador.getChildText("posX"), jugador.getChildText("posY"));
        return jugadorNuevo;
    }//metodo para leer la infomacion de un jugador

    public static Jugador leerJugadorActualizado(Element datos) {
        Element jugador = datos.getChild("jugador");
        Jugador jugadorNuevo = new Jugador(jugador.getChildText("posX"), jugador.getChildText("posY"),
                Integer.parseInt(jugador.getAttributeValue("puntosVida")));
        return jugadorNuevo;
    }//metodo para leer la infomacion de un jugador

    public static Element enviarOponente(String accion, ClienteDelServidor clientesServidor) throws NoSuchAlgorithmException {
        //se crea una nueva lista de jugadores para enviarlos en un unico paquete para los juegos de la partida
        Element eProtocolo = crearMensajeProtocolo(accion);
        Element oponente = new Element("oponente");
        Element nombre = new Element("nombre");
        nombre.addContent(clientesServidor.getUsurioCliente().getNombre());

        Element posX = new Element("posX");
        posX.addContent(clientesServidor.getJugadorCliente().getPosX());

        Element posY = new Element("posY");
        posY.addContent(clientesServidor.getJugadorCliente().getPosY());

        oponente.addContent(nombre);
        oponente.addContent(posX);
        oponente.addContent(posY);

        eProtocolo.addContent(oponente);
        return eProtocolo;
    }//retorna un xml con la accion a ejecutar de protocolo y el dato a enviar

    public static Element enviarOponentes(String accion, ClienteDelServidor excluir, ArrayList<ClienteDelServidor> clientesServidor) throws NoSuchAlgorithmException {
        //se crea una nueva lista de jugadores para enviarlos en un unico paquete para los juegos de la partida
        Element eProtocolo = crearMensajeProtocolo(accion);
        Element oponentes = new Element("oponentes");
        for (ClienteDelServidor clienteDelServidor : clientesServidor) {
            if (!clienteDelServidor.equals(excluir)) {

                Element oponente = new Element("oponente");
                Element nombre = new Element("nombre");
                nombre.addContent(clienteDelServidor.getUsurioCliente().getNombre());

                oponente.setAttribute("personaje", clienteDelServidor.getJugadorCliente().getPersonaje());

                Element posX = new Element("posX");
                posX.addContent(clienteDelServidor.getJugadorCliente().getPosX());

                Element posY = new Element("posY");
                posY.addContent(clienteDelServidor.getJugadorCliente().getPosY());

                oponente.addContent(nombre);
                oponente.addContent(posX);
                oponente.addContent(posY);
                oponentes.addContent(oponente);

            }//valida no enviar la informacion del cliente que solicitud de oponentes
        }//prepara toda la lista de oponentes
        eProtocolo.addContent(oponentes);
        return eProtocolo;
    }//retorna un xml con la accion a ejecutar de protocolo y el dato a enviar

    public static Element invitacionSolicitud(String accion, String destinatario, String remitente) {
        Element destinata = new Element("destinatario");
        destinata.addContent(destinatario);
        Element remitent = new Element("remitente");
        remitent.addContent(remitente);
        Element eProtocolo = crearMensajeProtocolo(accion);
        eProtocolo.addContent(destinata);
        eProtocolo.addContent(remitent);
        return eProtocolo;
    }//retorna un xml con la accion a ejecutar de protocolo y el dato a enviar

}//class
