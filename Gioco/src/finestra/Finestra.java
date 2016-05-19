package finestra;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import gioco.Handler;

public class Finestra {
	
	private JFrame f;
	private Canvas canvas;
	private Handler h;
	
	private String titolo;
	private int larghezza,altezza;
	
	public Finestra(String titolo, int larghezza, int altezza, Handler h){
		this.titolo = titolo;
		this.larghezza = larghezza;
		this.altezza = altezza;
		this.h = h;
		
		creaFinestra();
	}
	
	private void creaFinestra(){
		Dimension d = new Dimension(larghezza, altezza);
		
		f = new JFrame(titolo);
		f.setSize(d);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		WindowListener exitListener = new WindowAdapter() {

		    @Override
		    public void windowClosing(WindowEvent e) {
		       new FinestraUscita(h);
		    }
		};
		f.addWindowListener(exitListener);
		
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
