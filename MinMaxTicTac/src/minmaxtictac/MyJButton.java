/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minmaxtictac;

/**
 *
 * @author Dany Glez
 */
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

public class MyJButton extends JButton implements MouseListener {

	private TicTacToe juego;
	int fila, col, bandera;

	public MyJButton(int fila, int col, TicTacToe juego) {
		this.fila = fila; this.col = col; this.juego = juego;
		bandera = 0;
		addMouseListener(this);
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (this.isEnabled()) {
			juego.botonClicked(this);
			this.setEnabled(false);
			int respuesta = juego.verificarGanador(juego.estadoPartida);
			if (respuesta != 0) {
				juego.finPartida(respuesta);
			} 
			if (juego.total == 9) {
				juego.finPartida(0);
			}
			if (juego.turno == 1 && juego.vsMaquina) {
				juego.tiradaMaquina();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}


