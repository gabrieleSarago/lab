package gioco;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JOptionPane;

public class Launcher {
	
	public static void main (String[]args){
		Gioco gioco = new Gioco("The Labyrinth of Zelda",1200, 700);
		Dimension schermo = Toolkit.getDefaultToolkit().getScreenSize();
		int larghezza = (int) schermo.getWidth();
		int altezza = (int) schermo.getHeight();
		if(larghezza < 1200 || altezza < 700)
			JOptionPane.showMessageDialog(null, "La risoluzione dello schermo non supporta i requisiti minimi.");
		else
			gioco.start();
	}
}
