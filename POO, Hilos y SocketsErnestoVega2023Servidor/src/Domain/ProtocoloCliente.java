/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Domain;

import Utility.GestionXML;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Element;

/**
 *
 * @author Ernesto
 */
public enum ProtocoloCliente {

    VERIFICACION_INICIO {
        @Override
        public void ejecutarProtocolo(ClienteDelServidor clienteDelServidor, Element datos) {
            try {
                Usuario usurioNuevo = GestionXML.leerUsurio(datos.getChild("dato"));
                if (ServidorSingleton.getInstance().getUsuarioBusines().usuarioInicia(usurioNuevo)
                        && !ServidorSingleton.getInstance().usuarioEnLinea(usurioNuevo)) {

                    clienteDelServidor.setUsurioCliente(ServidorSingleton.getInstance().getUsuarioBusines().recuperarUsurio(usurioNuevo.getNombre()));
                    clienteDelServidor.enviar(GestionXML.xmlToString(GestionXML.crearMensajeProtocolo(Respuesta.INICIO_EXITOSO.name())));
                } else {
                    clienteDelServidor.enviar(GestionXML.xmlToString(GestionXML.crearMensajeProtocolo(Respuesta.REGISTRO_FALLIDO_IINICIO_FALLIDO.name())));
                }//verificacion inicio de seccion
            } catch (IOException ex) {
                Logger.getLogger(ProtocoloCliente.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }, REGISTRAR_NUEVO {
        @Override
        public void ejecutarProtocolo(ClienteDelServidor clienteDelServidor, Element datos) {
            try {
                Usuario usurioNuevo = GestionXML.leerUsurio(datos.getChild("dato"));
                if (!ServidorSingleton.getInstance().getUsuarioBusines().usuarioUnico(usurioNuevo)) {

                    ServidorSingleton.getInstance().getUsuarioBusines().registrarJugador(usurioNuevo);
                    clienteDelServidor.setUsurioCliente(ServidorSingleton.getInstance().getUsuarioBusines().recuperarUsurio(usurioNuevo.getNombre()));
                    clienteDelServidor.enviar(GestionXML.xmlToString(GestionXML.crearMensajeProtocolo(Respuesta.REGISTRO_EXITOSO.name())));
                } else {
                    clienteDelServidor.enviar(GestionXML.xmlToString(GestionXML.crearMensajeProtocolo(Respuesta.REGISTRO_FALLIDO_IINICIO_FALLIDO.name())));
                }//verifica lo necesario para registrar a un usurio nuevo
            } catch (IOException | NoSuchAlgorithmException ex) {
                Logger.getLogger(ProtocoloCliente.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }, ENVIAR_PUBLICACION {
        @Override
        public void ejecutarProtocolo(ClienteDelServidor clienteDelServidor, Element datos) {
            try {
                ServidorSingleton.getInstance().notificarAlResto(clienteDelServidor, datos.getChild("dato"));
            } catch (IOException ex) {
                Logger.getLogger(ProtocoloCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }//hace lectura del mensage y lo renvia a todos los usurios de todos los clientes
    }, CERRAR_SESSION {
        @Override
        public void ejecutarProtocolo(ClienteDelServidor clienteDelServidor, Element datos) {
            Element instruccion = GestionXML.crearMensajeProtocolo(Respuesta.CERRAR_SESSION.name());//envia al jugador la instruccion de cerrar session
            clienteDelServidor.enviar(GestionXML.xmlToString(instruccion));
            clienteDelServidor.terminarConexion();
            clienteDelServidor.cerrarFlujo();
        }//cierra la conexion con un cliente
    }, INICIAR_PARTIDA {
        @Override
        public void ejecutarProtocolo(ClienteDelServidor clienteDelServidor, Element datos) {
            //notifica a los clientes que despliguen su interfaz de juego
            clienteDelServidor.getPartidaJugador().iniciarPartida(GestionXML.xmlToString(GestionXML.crearMensajeProtocolo(Respuesta.INICIAR_PARTIDA.name())));
        }
    }, SOLICITAR_OPONENTES {
        @Override
        public void ejecutarProtocolo(ClienteDelServidor clienteDelServidor, Element datos) {
            clienteDelServidor.getPartidaJugador().enviarOponentes(clienteDelServidor);
        }
    },QUITAR_SOLICITUD {
        @Override
        public void ejecutarProtocolo(ClienteDelServidor clienteDelServidor, Element datos) {
            clienteDelServidor.quitarSolicitud(datos.getChildText("dato"));
        }
    }
    , ACEPTAR_SOLICITUD_GUARDADA {
        @Override
        public void ejecutarProtocolo(ClienteDelServidor clienteDelServidor, Element datos) {
            try {
                clienteDelServidor.aceptarSolicitudGuardada(new Amigo(datos.getChildText("dato")));
            } catch (IOException ex) {
                Logger.getLogger(ProtocoloCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }, SOLICITUD_ACEPTADA {
        @Override
        public void ejecutarProtocolo(ClienteDelServidor clienteDelServidor, Element datos) {
            try {
                clienteDelServidor.aceptarSolicitud(new Amigo(datos.getChildText("dato")));
            } catch (IOException ex) {
                Logger.getLogger(ProtocoloCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }, SOLICITUD_AMISTAD {
        @Override
        public void ejecutarProtocolo(ClienteDelServidor clienteDelServidor, Element datos) {
            try {
                SolicitudAmistad solicitudAmistadNueva = new SolicitudAmistad(datos.getChildText("dato"), clienteDelServidor.getUsurioCliente().getNombre());
                if (!ServidorSingleton.getInstance().enviarSolicitudAmistad(solicitudAmistadNueva)) {

                    if (ServidorSingleton.getInstance().guardarSolicitudAmistad(solicitudAmistadNueva)) {
                        clienteDelServidor.enviar(GestionXML.xmlToString(GestionXML.crearMensajeProtocolo(Respuesta.SOLICITUD_GURDADA.name())));
                    } else {
                        clienteDelServidor.enviar(GestionXML.xmlToString(GestionXML.crearMensajeProtocolo(Respuesta.USUARIO_NO_EXISTE.name())));
                    }//dependiendo de lo que se necesite envia un mensaje
                }//verifica si el usuariode la solicitud esta conectado
            } catch (IOException ex) {
                Logger.getLogger(ProtocoloCliente.class.getName()).log(Level.SEVERE, null, ex);
            }//notifica la solicitud de amistad
        }
    }, ACTUALIZAR_ESTADO {
        @Override
        public void ejecutarProtocolo(ClienteDelServidor clienteDelServidor, Element datos) {
            try {
                //actualiza la informacion del jugaro en el cliente
                Jugador jugadorActualizado = GestionXML.leerJugadorActualizado(datos.getChild("dato"));
                clienteDelServidor.actualizarJugadorCliente(jugadorActualizado);
                if (!clienteDelServidor.getPartidaJugador().determinarPerdedor(clienteDelServidor)) {
                    //notifica los cambios a los demas oponentes
                    String notificacion = GestionXML.xmlToString(GestionXML.enviarOponente(Respuesta.ACTUALIZAR_OPONENTE.name(), clienteDelServidor));
                    clienteDelServidor.getPartidaJugador().usuarioNotificaTodos(clienteDelServidor, notificacion);
                }//verifica si el jugador perdio
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(ProtocoloCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }, ENVIAR_MAPA_JUEGO {
        @Override
        public void ejecutarProtocolo(ClienteDelServidor clienteDelServidor, Element datos) {
            clienteDelServidor.getPartidaJugador().enviarMapaJugadores(datos.getChildText("dato"));
        }
    }, UNIRSE_A_PARTIDA {
        @Override
        public void ejecutarProtocolo(ClienteDelServidor clienteDelServidor, Element datos) {
            try {
                ServidorSingleton.getInstance().unirPartida(clienteDelServidor, datos.getChildText("dato"));
            } catch (IOException ex) {
                Logger.getLogger(ProtocoloCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }, INVITAR_AMIGO {
        @Override
        public void ejecutarProtocolo(ClienteDelServidor clienteDelServidor, Element datos) {
            try {
                ServidorSingleton.getInstance().invitarPartida(clienteDelServidor, datos.getChildText("dato"));
            } catch (IOException ex) {
                Logger.getLogger(ProtocoloCliente.class.getName()).log(Level.SEVERE, null, ex);
            }//ingresa a los usuarios a la partida
        }
    }, ENVIAR_ESTADO_JUGADOR {
        @Override
        public void ejecutarProtocolo(ClienteDelServidor clienteDelServidor, Element datos) {
            Jugador jugador = GestionXML.leerJugador(datos.getChild("dato"));
            clienteDelServidor.setJugadorCliente(jugador);
        }
    }, FLECHA_LANZADA {
        @Override
        public void ejecutarProtocolo(ClienteDelServidor clienteDelServidor, Element datos) {
            //envia a crear la felcha en todos los clientes
            clienteDelServidor.getPartidaJugador().usuarioNotificaTodos(clienteDelServidor, GestionXML.xmlToString(datos));
        }
    }, GUARDAR_MAPA {
        @Override
        public void ejecutarProtocolo(ClienteDelServidor clienteDelServidor, Element datos) {
            try {
                ServidorSingleton.getInstance().getMapaBusiness().registrarMapa(GestionXML.leerMapa(datos));
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(ProtocoloCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }, SOLICITAR_LISTA_AMIGOS {
        @Override
        public void ejecutarProtocolo(ClienteDelServidor clienteDelServidor, Element datos) {
            try {
                //solicita toda la informacion necesaria para que un jugador pueda crear una partida
                //lista de amigos y mapas
                String listaMapas = ServidorSingleton.getInstance().getMapaBusiness().listaMapas(clienteDelServidor.getUsurioCliente().getNombre());
                Element respuesta = GestionXML.enviarInformacionPartida(Respuesta.LISTA_AMIGOS.name(),clienteDelServidor.getUsurioCliente().getAmigos(),listaMapas);
                clienteDelServidor.enviar(GestionXML.xmlToString(respuesta));
                clienteDelServidor.getPartidaJugador().unirCreadorPartida(clienteDelServidor);
            } catch (ClassNotFoundException | IOException ex) {
                Logger.getLogger(ProtocoloCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }, RANKING_JUGADORES {
        @Override
        public void ejecutarProtocolo(ClienteDelServidor clienteDelServidor, Element datos) {
            try {
                String tabla = ServidorSingleton.getInstance().getUsuarioBusines().ranking();
                clienteDelServidor.enviar(GestionXML.xmlToString(GestionXML.agregarAccionSimple(Respuesta.RANKING_JUGADORES.name(), tabla)));
            } catch (IOException | NoSuchAlgorithmException ex) {
                Logger.getLogger(ProtocoloCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }, NOTIFICAR_ESPADAZO {
        @Override
        public void ejecutarProtocolo(ClienteDelServidor clienteDelServidor, Element datos) {
            clienteDelServidor.getPartidaJugador().usuarioNotificaTodos(clienteDelServidor, GestionXML.xmlToString(datos));
        }
    }, SOLICITAR_DIAMANTES {
        @Override
        public void ejecutarProtocolo(ClienteDelServidor clienteDelServidor, Element datos) {
            clienteDelServidor.getPartidaJugador().enviarDiamantesVerificar(clienteDelServidor);
        }
    }, CAMBIAR_CASILLA_DIAMANTE {
        @Override
        public void ejecutarProtocolo(ClienteDelServidor clienteDelServidor, Element datos) {
            clienteDelServidor.getPartidaJugador().recargarPoscion(Integer.parseInt(datos.getChildText("dato")));
        }
    }, DISTRIBUCION_CORRECTA {
        @Override
        public void ejecutarProtocolo(ClienteDelServidor clienteDelServidor, Element datos) {
            clienteDelServidor.getPartidaJugador().enviarDiamantes();
        }
    }, TOMAR_DIAMANTE {
        @Override
        public void ejecutarProtocolo(ClienteDelServidor clienteDelServidor, Element datos) {
            clienteDelServidor.getPartidaJugador().tomarDimante(clienteDelServidor, Integer.parseInt(datos.getChildText("dato")));
        }
    }, ROBAR_DIAMANTES {
        @Override
        public void ejecutarProtocolo(ClienteDelServidor clienteDelServidor, Element datos) {
            clienteDelServidor.getJugadorCliente().restarDiamante(Integer.parseInt(datos.getChildText("cantidad")));
            clienteDelServidor.getPartidaJugador().notificarJugador(datos.getChildText("creador"),
                    GestionXML.xmlToString(GestionXML.agregarAccionSimple(Respuesta.SUMAR_DIAMANTE.name(), datos.getChildText("cantidad"))));
        }
    }, SOLICITAR_SOLICITUDES {
        @Override
        public void ejecutarProtocolo(ClienteDelServidor clienteDelServidor, Element datos) {
            clienteDelServidor.enviar(GestionXML.xmlToString(GestionXML.enviarSolicitudes(Respuesta.ENVIAR_SOLICITUDES.name(),
                     clienteDelServidor.getUsurioCliente().getSolicitudes())));
        }
    },NOTIFICAR_VICTORIA {
        @Override
        public void ejecutarProtocolo(ClienteDelServidor clienteDelServidor, Element datos) {
            clienteDelServidor.getJugadorCliente().setDiamantes(Integer.parseInt(datos.getChildText("dato")));
            clienteDelServidor.getPartidaJugador().determinarGanador(clienteDelServidor);
        }
    };

    public abstract void ejecutarProtocolo(ClienteDelServidor clienteDelServidor, Element datos);

}//enum
