package finestra;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import gioco.Handler;

/**
 * Costruisce l'oggetto Frame del gioco.
 */
public class Finestra {
	
	private JFrame f;
	//private Canvas canvas;
	private Handler h;
	
	private String titolo;
	private int larghezza,altezza;
	
	/**
	 * Costruttore dell'oggetto Finestra
	 * @param titolo titolo della finestra
	 * @param larghezza larghezza della finestra
	 * @param altezza altezza della finestra
	 * @param h oggetto Handler utile nella gestione con altre classi.
	 */
	public Finestra(String titolo, int larghezza, int altezza, Handler h){
		this.titolo = titolo;
		this.larghezza = larghezza;
		this.altezza = altezza;
		this.h = h;
		
		creaFinestra();
	}
	
	/**
	 * Crea una Finestra mediante un JFrame delle librerie di Java.
	 */
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
		
		/*f.setVisible(true);
		
		canvas = new Canvas();
		canvas.setPreferredSize(d);
		canvas.setMaximumSize(d);
		canvas.setMinimumSize(d);
		canvas.setFocusable(false);
		
		f.add(canvas);
		f.pack();*/
		
		
	}
	
	/**
	 * Ritorna il frame del gioco.
	 * @return il frame del gioco.
	 */
	public JFrame getFrame(){
		return f;
	}
}
