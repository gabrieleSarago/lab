package finestra;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Finestra {
	
	private JFrame f;
	private Canvas canvas;
	
	private String titolo;
	private int larghezza,altezza;
	
	public Finestra(String titolo, int larghezza, int altezza){
		this.titolo = titolo;
		this.larghezza = larghezza;
		this.altezza = altezza;
		
		creaFinestra();
	}
	
	private void creaFinestra(){
		Dimension d = new Dimension(larghezza, altezza);
		
		f = new JFrame(titolo);
		f.setSize(d);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.setVisible(true);
		
		canvas = new Canvas();
		canvas.setPreferredSize(d);
		canvas.setMaximumSize(d);
		canvas.setMinimumSize(d);
		canvas.setFocusable(false);
		
		f.add(canvas);
		f.pack();
	}
	
	public Canvas getCanvas(){
		return canvas;
	}	
	
	public JFrame getFrame(){
		return f;
	}
}
