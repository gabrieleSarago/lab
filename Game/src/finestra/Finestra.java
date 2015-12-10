package finestra;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Finestra {
	
	private JFrame f;
	
	private int larghezza, altezza;
	private String titolo;
	
	private Canvas canvas;
	
	public Finestra(String titolo, int larghezza, int altezza){
		this.titolo = titolo;
		this.larghezza = larghezza;
		this.altezza = altezza;
		
		creaFinestra();
	}
	
	private void creaFinestra(){
		f = new JFrame(titolo);
		f.setSize(larghezza, altezza);
		f.setResizable(false);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		canvas = new Canvas();
		Dimension d = new Dimension(larghezza, altezza);
		canvas.setPreferredSize(d);
		canvas.setMaximumSize(d);
		canvas.setMinimumSize(d);
		//manda il canvas in secondo piano
		canvas.setFocusable(false);
		
		f.add(canvas);
		f.pack();
	}
	
	public Canvas getCanvas(){return canvas;}
	
	public JFrame getFrame(){return f;}
}
