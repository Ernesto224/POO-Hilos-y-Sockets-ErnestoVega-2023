/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import Domain.Jugador;
import Domain.Usuario;
import java.io.IOException;
import javax.swing.JFrame;

/**
 *
 * @author Ernesto
 */
public class JFSeleccionPersonaje extends JFrame{

    private final JPanelPersonaje panelpersonaje;

    public JFSeleccionPersonaje(Usuario usuario) throws IOException {
        super("Seleccionar personaje");
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.panelpersonaje=new JPanelPersonaje(usuario,this);
        this.add(this.panelpersonaje);
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }//constructor

}//class 5/22/2023
