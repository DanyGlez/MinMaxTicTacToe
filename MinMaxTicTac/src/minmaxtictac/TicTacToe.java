package minmaxtictac;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class TicTacToe extends JFrame implements ActionListener {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new TicTacToe();
			}
		});
	}

	
	private MyJButton[][] boton;
	public int[][] estadoPartida;
	private JPanel tablero = new JPanel();
	private JLabel turnosLabel;
	private JMenuBar menubar;
	private JMenu primerTurnoMenu, rivalMenu;
	private JMenuItem jugador1, jugador2, usuario, maquina;
	int turno, total = 0;
	Random random = new Random();
	String nombreJ1 = "Usuario";
	String nombreJ2 = "Maquina";
	private String letraJ1 = "X";
	private String letraJ2 = "O";
	public boolean vsMaquina = true;
	Object reinicio = "¿Quieres reiniciar el juego?";
	
	public TicTacToe() {
		super("Tic Tac Toe");
		setVisible(true);
		setSize(400, 450);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		jugador1 = new JMenuItem("Turno de "+nombreJ1);
		jugador1.addActionListener(this);
		jugador2 = new JMenuItem("Turno de "+nombreJ2);
		jugador2.addActionListener(this);
		primerTurnoMenu = new JMenu("turno");primerTurnoMenu.setMnemonic(KeyEvent.VK_U);
		primerTurnoMenu.add(jugador1);primerTurnoMenu.add(jugador2);
		usuario = new JMenuItem(" contra Usuario ", KeyEvent.VK_H);
		usuario.addActionListener(this);
		maquina = new JMenuItem(" contra maquina ", KeyEvent.VK_C);
		maquina.addActionListener(this);
		rivalMenu = new JMenu(" vs ");rivalMenu.setMnemonic(KeyEvent.VK_E);
		rivalMenu.add(usuario);rivalMenu.add(maquina);
		menubar = new JMenuBar();
		menubar.add(primerTurnoMenu);menubar.add(rivalMenu);
		setJMenuBar(menubar);
		inicializarTablero();
		iniciarJuego();
		setLocationRelativeTo(null);
	}

	private void inicializarTablero() {
		total = 0;
		setLayout(new BorderLayout());
		tablero.setBorder(new EmptyBorder(20, 20, 10, 20));
		add(tablero, BorderLayout.CENTER);
		tablero.setLayout(new GridLayout(3, 3));
		boton = new MyJButton[3][3];estadoPartida = new int[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				boton[i][j] = new MyJButton(i, j, this);
				estadoPartida[i][j] = 0;
				tablero.add(boton[i][j]);
			}
		}
		turnosLabel = new JLabel();
		turnosLabel.setBorder(new EmptyBorder(0, 20, 0, 20));
		add(turnosLabel, BorderLayout.SOUTH);
	}

	private void iniciarJuego() {
		if (turno == 0) {
			letraJ1 = "X";letraJ2 = "O";
			turnosLabel.setText(nombreJ1 + " turno" + " (" + letraJ1 + ")");
		} else {
			letraJ2 = "X";letraJ1 = "O";
			turnosLabel.setText(nombreJ2 + " turno" + " (" + letraJ2 + ")");
			if (vsMaquina) {
				tiradaMaquina();
			}
		}
	}

	public void botonClicked(MyJButton b) {
		total++;
		if (turno == 0) {
			b.setText(letraJ1);
			estadoPartida[b.fila][b.col] = -1;
			b.bandera = -1;
			turnosLabel.setText(nombreJ2 + " turno" + " (" + letraJ2 + ")");
		} else {
			b.setText(letraJ2);
			estadoPartida[b.fila][b.col] = 1;
			b.bandera = 1;
			turnosLabel.setText(nombreJ1 + " turno" + " (" + letraJ1 + ")");
		}
		turno = 1 - turno;
	}

	public int verificarGanador(int[][] estado) {
		for (int i = 0, contadorFila = 0, contadorColumna = 0; i < 3; i++, contadorFila = 0, contadorColumna = 0) {
			for (int j = 0; j < 3; j++) {
				contadorFila += estado[i][j];
				contadorColumna += estado[j][i];
			}
			if (contadorFila == -3 || contadorColumna == -3) {
				return -1;
			}
			if (contadorFila == 3 || contadorColumna == 3) {
				return 1;
			}
		}
		if (estado[1][1] == -1) {
			if ((estado[0][0] == -1 && estado[2][2] == -1) || (estado[0][2] == -1 && estado[2][0] == -1)) {
				return -1;
			}
		}
		if (estado[1][1] == 1) {
			if ((estado[0][0] == 1 && estado[2][2] == 1) || (estado[0][2] == 1 && estado[2][0] == 1)) {
				return 1;
			}
		}
		return 0;
	}

	public void finPartida(int respuesta) {
		switch (respuesta) {
		case -1:
			ganador(" Gano "+nombreJ1 );
			break;
		case 1: 
			ganador(" Gano "+nombreJ2);
			break;
		default:
			ganador("Empate");
			break;
		}
	}

	public void ganador(String mensaje) {
		turnosLabel.setText(mensaje);
		JOptionPane.showMessageDialog(this, mensaje);
		if (letraJ1 == "X") {
			turno = 0;
		} else {
			turno = 1;
		}
		reiniciarJuego();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int seleccion = JOptionPane.showConfirmDialog(this, reinicio);
		if (seleccion == JOptionPane.YES_OPTION) {
			if (e.getSource() == jugador1) {
				turno = 0;
				
			} else if (e.getSource() == jugador2) {
				turno = 1;
			} else if (e.getSource() == usuario) {
				vsMaquina = false;
				nombreJ2 = " Jugador 2 ";
			} else if (e.getSource() == maquina) {
				vsMaquina = true;
				nombreJ2 = " Maquina";
			}
			jugador2.setText("Turno de "+nombreJ2);
			reiniciarJuego();
		}
	}

	private void reiniciarJuego() {
		tablero.removeAll();
		remove(turnosLabel);
		inicializarTablero();
		iniciarJuego();
	}

	public void tiradaMaquina() {
		int profundidad = 9-total, alpha = -10, beta = 10;
		ResultSet result = alphabeta(estadoPartida, profundidad, alpha, beta, true);
		boton[result.paso.x][result.paso.y].mouseClicked(null);
	}

	private ResultSet alphabeta(int[][] estadoPartida, int profundidad, int alpha, int beta,
			boolean jugadorMax) {
		ResultSet result = new ResultSet(verificarGanador(estadoPartida));
		if (result.value != 0) {
			result.value = (Math.abs(result.value)+profundidad)*result.value;
			return result;
		} 
		if (profundidad == 0) {
			result.value = 0;
			return result;
		}	
		if (jugadorMax) {
			result.value = -10;
			List<Integer> ListaNumeros = new ArrayList<Integer>();
			for (int i = 0; i < 9; ListaNumeros.add(i++));
			for (int i = 9; i > 0; i--) {
				int aleatorio = ListaNumeros.remove(random.nextInt(i));
				int fila = aleatorio/3, col = aleatorio%3;
				if (estadoPartida[fila][col] == 0) {
					estadoPartida[fila][col] = 1;
					ResultSet hijoResult = alphabeta(estadoPartida, profundidad-1, alpha, beta, false);
					if (hijoResult.value > result.value) {
						result.value = hijoResult.value;
						result.paso.setLocation(fila, col);
					}
					alpha = Math.max(alpha, result.value);
					estadoPartida[fila][col] = 0;
					if (alpha >= beta) {
						break;
					}
				}
			}
		} else {
			result.value = 10;
			List<Integer> ListaNumeros = new ArrayList<Integer>();
			for (int i = 0; i < 9; ListaNumeros.add(i++));
			for (int i = 9; i > 0; i--) {
				int aleatorio = ListaNumeros.remove(random.nextInt(i));
				int fila = aleatorio/3, col = aleatorio%3;
				if (estadoPartida[fila][col] == 0) {
					estadoPartida[fila][col] = -1;
					ResultSet hijoResult = alphabeta(estadoPartida, profundidad-1, alpha, beta, true);
					if (hijoResult.value < result.value) {
						result.value = hijoResult.value;
						result.paso.setLocation(fila, col);
					}
					beta = Math.min(beta, result.value);
					estadoPartida[fila][col] = 0;
					if (beta <= alpha) {
						break;
					}
				}
			}
		}
		return result;
	}

}

class ResultSet {

	public int value;
	public Point paso;
	
	public ResultSet(int value) {
		this.value = value;
		paso = new Point();
	}
	
}