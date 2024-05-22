/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author JimmyNaranjo
 */

public class Juego extends JFrame {
    private static final int BOARD_SIZE = 700;
    private static final int CELL_SIZE = BOARD_SIZE / 11;
    private ArrayList<Jugador> jugadores = new ArrayList<>();
    private int turnoActual = 0;
    private JButton lanzarDadoButton;
    private Random random = new Random();
    private boolean[] casillasCompradas = new boolean[40]; 

    public Juego(int numJugadores) {
        setTitle("Monopoly");
        setSize(BOARD_SIZE + 100, BOARD_SIZE + 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        for (int i = 1; i <= numJugadores; i++) {
            jugadores.add(new Jugador("Jugador " + i, i, getColorForPlayer(i)));
        }

        lanzarDadoButton = new JButton("Lanzar dado");
        lanzarDadoButton.setBounds((BOARD_SIZE + 100) / 2 - 50, BOARD_SIZE + 20, 100, 30);
        lanzarDadoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                siguienteTurno();
            }
        });
        add(lanzarDadoButton);

        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Insets insets = getInsets();
        int top = insets.top;
        int left = insets.left;

        for (int i = 0; i < 40; i++) {
            int x = 0, y = 0;

            if (i < 11) {
                x = left + i * CELL_SIZE;
                y = BOARD_SIZE - CELL_SIZE + top - insets.bottom;
            } else if (i < 20) {
                x = BOARD_SIZE - CELL_SIZE + left - insets.right;
                y = BOARD_SIZE - (i - 10) * CELL_SIZE - CELL_SIZE + top - insets.bottom;
            } else if (i < 31) {
                x = BOARD_SIZE - (i - 20) * CELL_SIZE - CELL_SIZE + left - insets.right;
                y = top;
            } else {
                x = left;
                y = (i - 30) * CELL_SIZE + top;
            }

            g.drawRect(x, y, CELL_SIZE, CELL_SIZE);
            if (i == 1 || i == 3 || i == 5 || i == 7 || i == 9 || i == 11 || i == 13 || i == 15 || i == 17 || i == 19 || i == 21 || i == 23 || i == 25 || i == 27 || i == 29 || i == 31 || i == 33 || i == 35 || i == 37 || i == 39) {
                g.drawString("Casilla " + i, x + 10, y + 20);
                g.drawString("Propiedad", x + 10, y + 40); 
            } else {
                g.drawString("Casilla " + i, x + 10, y + 20);
            }
        }

        for (Jugador jugador : jugadores) {
            int posicion = jugador.getPosicion();
            int x = 0, y = 0;

            if (posicion < 11) {
                x = left + posicion * CELL_SIZE + CELL_SIZE / 4;
                y = BOARD_SIZE - CELL_SIZE + top - insets.bottom + CELL_SIZE / 4;
            } else if (posicion < 20) {
                x = BOARD_SIZE - CELL_SIZE + left - insets.right + CELL_SIZE / 4;
                y = BOARD_SIZE - (posicion - 10) * CELL_SIZE - CELL_SIZE + top - insets.bottom + CELL_SIZE / 4;
            } else if (posicion < 31) {
                x = BOARD_SIZE - (posicion - 20) * CELL_SIZE - CELL_SIZE + left - insets.right + CELL_SIZE / 4;
                y = top + CELL_SIZE / 4;
            } else {
                x = left + CELL_SIZE / 4;
                y = (posicion - 30) * CELL_SIZE + top + CELL_SIZE / 4;
            }

            g.setColor(jugador.getColor());
            g.fillOval(x, y, CELL_SIZE / 2, CELL_SIZE / 2);
        }
    }



    private void siguienteTurno() {
        Jugador jugadorActual = jugadores.get(turnoActual);

        int dado = random.nextInt(6) + 1;
        int nuevaPosicion = (jugadorActual.getPosicion() + dado) % 40;
        jugadorActual.setPosicion(nuevaPosicion);

        JOptionPane.showMessageDialog(this, jugadorActual.getNombre() + " ha lanzado el dado y obtuvo un " + dado);

        if (casillasCompradas[nuevaPosicion]) {
            jugadorActual.pagarTarifa(100); 
            JOptionPane.showMessageDialog(this, "Has caído en una propiedad de otro jugador. Pagas una tarifa de $100.");
        } else {
            if (nuevaPosicion == 1 || nuevaPosicion == 3 || nuevaPosicion == 5 || nuevaPosicion == 7 || nuevaPosicion == 9 || nuevaPosicion == 11 || nuevaPosicion == 13 || nuevaPosicion == 15 || nuevaPosicion == 17 || nuevaPosicion == 19 || nuevaPosicion == 21 || nuevaPosicion == 23 || nuevaPosicion == 25 || nuevaPosicion == 27 || nuevaPosicion == 29 || nuevaPosicion == 31 || nuevaPosicion == 33 || nuevaPosicion == 35 || nuevaPosicion == 37 || nuevaPosicion == 39) {
                int respuesta = JOptionPane.showConfirmDialog(this, "¿Desea comprar la propiedad en esta casilla?");
                if (respuesta == JOptionPane.YES_OPTION) {
                    jugadorActual.comprarPropiedad(200); 
                    casillasCompradas[nuevaPosicion] = true; 
                    JOptionPane.showMessageDialog(this, "Has comprado la propiedad. Te quedan $" + jugadorActual.getDinero());
                }
            }
        }

        if (turnoActual < jugadores.size() - 1) {
            turnoActual++;
        } else {
            turnoActual = 0;
        }

        repaint();
    }

    private Color getColorForPlayer(int playerNumber) {
        switch (playerNumber) {
            case 1:
                return Color.RED;
            case 2:
                return Color.BLUE;
            case 3:
                return Color.GREEN;
            case 4:
                return Color.YELLOW;
            case 5:
                return Color.ORANGE;
            case 6:
                return Color.MAGENTA;
            case 7:
                return Color.CYAN;
            case 8:
                return Color.PINK;
                default:
                return Color.BLACK;
        }
        
    }
    public void reiniciarJuego() {
    for (Jugador jugador : jugadores) {
        jugador.setPosicion(0);
    }
    
    for (int i = 0; i < 40; i++) {
        if (i == 1 || i == 3 || i == 5 || i == 7 || i == 9 || i == 11 || i == 13 || i == 15 || i == 17 || i == 19 || i == 21 || i == 23 || i == 25 || i == 27 || i == 29 || i == 31 || i == 33 || i == 35 || i == 37 || i == 39) {

            casillasCompradas[i] = false;
        }
    }   
    turnoActual = 0;
    repaint();
}


public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        int numJugadores = 0;
        while (numJugadores < 2 || numJugadores > 8) {
            String input = JOptionPane.showInputDialog(null, "Ingrese el numero de jugadores (2-8):");
            if (input != null) {
                try {
                    numJugadores = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Por favor ingrese un numero valido (2-8).");
                }
            } else {
                System.exit(0);
            }
        }

        Juego juego = new Juego(numJugadores);
        juego.reiniciarJuego(); 
    });
    }
}






