/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

/**
 *
 * @author Kenneth
 */
public class SolicitudAmistad {
    
    private String addressee;
    private String sender;

    public SolicitudAmistad(String addressee, String sender) {
        this.addressee = addressee;
        this.sender = sender;
    }// constructor 

    //acces method
    public String getAddressee() {
        return addressee;
    }

    public void setAddressee(String addressee) {
        this.addressee = addressee;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

}//class
