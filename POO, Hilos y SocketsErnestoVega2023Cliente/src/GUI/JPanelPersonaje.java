/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import Domain.ClienteSingleton;
import Domain.Usuario;
import Utility.ConexionInterfaz;
import Utility.Disenio;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Ernesto
 */
//esta clase es meramente para cargar los personajes a la ora de que se loogen
public class JPanelPersonaje extends JPanel implements ActionListener {

    private final Usuario usuario;
    private final JFrame jFrame;
    private BufferedImage imagen1;
    private BufferedImage imagen2;
    private BufferedImage imagen3;
    private BufferedImage imagen4;
    private BufferedImage imagen5;
    private JButton jbtnIniciar;
    private JButton jbtnOpcion1;
    private JButton jbtnOpcion2;
    private JButton jbtnOpcion3;
    private JButton jbtnOpcion4;

    public JPanelPersonaje(Usuario usuario, JFrame jFrame) {
        this.usuario = usuario;
        this.jFrame = jFrame;
        this.setPreferredSize(new Dimension(Disenio.WIDTH_JPANEL_PERSONAJE, Disenio.HEIGTH_JPANEL_PERSONAJE));
        this.setLayout(null);
        this.init();
    }//constructor

    private void init() {
        this.jbtnIniciar = new JButton("Iniciar");
        this.jbtnIniciar.setBounds(260, 250, 100, 20);
        this.jbtnIniciar.addActionListener(this);
        this.jbtnIniciar.setEnabled(false);
        this.jbtnIniciar.setBackground(Color.BLACK);
        this.jbtnIniciar.setForeground(Color.YELLOW);
        this.jbtnIniciar.setFont(Disenio.FORMATO_BOTONES);
        this.add(this.jbtnIniciar);
        this.jbtnOpcion1 = new JButton("Avatar 1");
        this.jbtnOpcion1.setBounds(40, 180, 100, 20);
        this.jbtnOpcion1.addActionListener(this);
        this.jbtnOpcion1.setBackground(Color.BLACK);
        this.jbtnOpcion1.setForeground(Color.YELLOW);
        this.jbtnOpcion1.setFont(Disenio.FORMATO_BOTONES);
        this.add(this.jbtnOpcion1);
        this.jbtnOpcion2 = new JButton("Avatar 2");
        this.jbtnOpcion2.setBounds(180, 180, 100, 20);
        this.jbtnOpcion2.addActionListener(this);
        this.jbtnOpcion2.setBackground(Color.BLACK);
        this.jbtnOpcion2.setForeground(Color.YELLOW);
        this.jbtnOpcion2.setFont(Disenio.FORMATO_BOTONES);
        this.add(this.jbtnOpcion2);
        this.jbtnOpcion3 = new JButton("Avatar 3");
        this.jbtnOpcion3.setBounds(340, 180, 100, 20);
        this.jbtnOpcion3.addActionListener(this);
        this.jbtnOpcion3.setBackground(Color.BLACK);
        this.jbtnOpcion3.setForeground(Color.YELLOW);
        this.jbtnOpcion3.setFont(Disenio.FORMATO_BOTONES);
        this.add(this.jbtnOpcion3);
        this.jbtnOpcion4 = new JButton("Avatar 4");
        this.jbtnOpcion4.setBounds(480, 180, 100, 20);
        this.jbtnOpcion4.addActionListener(this);
        this.jbtnOpcion4.setBackground(Color.BLACK);
        this.jbtnOpcion4.setForeground(Color.YELLOW);
        this.jbtnOpcion4.setFont(Disenio.FORMATO_BOTONES);
        this.add(this.jbtnOpcion4);

        try {
            this.imagen1 = ImageIO.read(getClass().getResource("/assets/seleccion1.png"));
            this.imagen2 = ImageIO.read(getClass().getResource("/assets/seleccion2.png"));
            this.imagen3 = ImageIO.read(getClass().getResource("/assets/seleccion3.png"));
            this.imagen4 = ImageIO.read(getClass().getResource("/assets/seleccion4.png"));
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(JPanelPersonaje.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }//trate de cargar las imagenes//trate de cargar las imagenes
    }//init

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Disenio.COLOR_FONDO);
        g.fillRect(0, 0, Disenio.WIDTH_JPANEL_PERSONAJE, Disenio.HEIGTH_JPANEL_PERSONAJE);
        g.drawImage(this.imagen1, 60, 50, Disenio.IMAGEN_PERFIL, Disenio.IMAGEN_PERFIL, null);
        g.drawImage(this.imagen2, 200, 50, Disenio.IMAGEN_PERFIL, Disenio.IMAGEN_PERFIL, null);
        g.drawImage(this.imagen3, 360, 50, Disenio.IMAGEN_PERFIL, Disenio.IMAGEN_PERFIL, null);
        g.drawImage(this.imagen4, 500, 50, Disenio.IMAGEN_PERFIL, Disenio.IMAGEN_PERFIL, null);
        g.drawImage(this.imagen5, 660, 50, Disenio.IMAGEN_PERFIL, Disenio.IMAGEN_PERFIL, null);
    }//metodo que dibuja sprite para que seleccione el jugador

    private void colocarImagen(String ruta) {
        //coloca el avatar al personaje
        ClienteSingleton.getInstance().getJugadorCliente().getPersonaje().setAvatar(ruta);
    }//metodo que le quita 1 a la cantidad de jugadores que faltan de obtener una imagen

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.jbtnOpcion1) {
            this.jbtnOpcion1.setEnabled(false);
            this.jbtnIniciar.setEnabled(true);
            this.colocarImagen("/assets/personaje1.png");
        }//seleccion de avatar

        if (e.getSource() == this.jbtnOpcion2) {
            this.jbtnOpcion2.setEnabled(false);
            this.jbtnIniciar.setEnabled(true);
            this.colocarImagen("/assets/personaje2.png");
        }//seleccion de avatar

        if (e.getSource() == this.jbtnOpcion3) {
            this.jbtnOpcion3.setEnabled(false);
            this.jbtnIniciar.setEnabled(true);
            this.colocarImagen("/assets/personaje3.png");
        }//seleccion de avatar

        if (e.getSource() == this.jbtnOpcion4) {
            this.jbtnOpcion4.setEnabled(false);
            this.jbtnIniciar.setEnabled(true);
            this.colocarImagen("/assets/personaje4.png");
        }//seleccion de avatar

        if (e.getSource() == jbtnIniciar) {
            JFInterfazUsuario jFInterfazUsuario = new JFInterfazUsuario(this.usuario);
            ConexionInterfaz.setjFInterfazUsuario(jFInterfazUsuario);
            jFInterfazUsuario.setVisible(true);
            this.jFrame.dispose();
        }//abre la ventana principal de opciones
    }//metodo para activar la acciones de los botones

}//class 5/22/2023
