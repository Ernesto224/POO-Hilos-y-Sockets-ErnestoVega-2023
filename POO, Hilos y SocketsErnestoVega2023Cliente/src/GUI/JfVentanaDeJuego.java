/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import javax.swing.JFrame;

/**
 *
 * @author Laboratorios
 */
public class JfVentanaDeJuego extends JFrame{
    
    private final JPanelJuego jPanelJuego;

    public JfVentanaDeJuego() {
        this.jPanelJuego=new JPanelJuego();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.add(this.jPanelJuego);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }//constructor

}//class 5/26/2023
