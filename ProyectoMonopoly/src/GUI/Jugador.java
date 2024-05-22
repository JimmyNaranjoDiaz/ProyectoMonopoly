/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Color;
import javax.swing.JOptionPane;

/**
 *
 * @author JimmyNaranjo
 */

public class Jugador {
    private String nombre;
    private int ficha;
    private int posicion;
    private Color color;
    private int saldo; 
    private int dinero;

    public Jugador(String nombre, int ficha, Color color) {
        this.nombre = nombre;
        this.ficha = ficha;
        this.posicion = 0;
        this.color = color;
        this.saldo = 1500;
        this.dinero = saldo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getFicha() {
        return ficha;
    }

    public void setFicha(int ficha) {
        this.ficha = ficha;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void comprarPropiedad(int precio) {
        if (dinero >= precio) {
            dinero -= precio;
            JOptionPane.showMessageDialog(null, "Has comprado la propiedad por $" + precio + ". Te quedan $" + dinero);
        } else {
            JOptionPane.showMessageDialog(null, "No tienes suficiente dinero para comprar esta propiedad.");
        }
    }

    public int getDinero() {
        return dinero;
    }

    public void pagarTarifa(int tarifa) {
    dinero -= tarifa;
    }
}
