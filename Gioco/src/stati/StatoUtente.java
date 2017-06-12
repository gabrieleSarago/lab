package stati;

import java.awt.Graphics;

import finestra.FinestraUtente;
import gioco.Handler;
import suoni.Suono;

public class StatoUtente  extends Stato {

	private FinestraUtente f;
	private Suono suono;
	
	public StatoUtente(Handler h) {
		super(h);
		f = new FinestraUtente(h);
	}

	public StatoUtente(Handler h, Suono suono) {
		super(h);
		this.suono = suono;
		f = new FinestraUtente(h, suono);
		f.setVisible(true);
 	
	}
	
	@Override
	public void aggiorna() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disegna(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	

}
