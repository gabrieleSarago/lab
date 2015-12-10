package stati;

import java.awt.Graphics;

import gioco.Handler;
import livelli.Livello;
import personaggio.Sink;
import statici.Pietra;

public class StatoGioco extends Stato{
	
	private Livello l;
	
	public StatoGioco(Handler h){
		super(h);
		l = new Livello(h, "res/livelli/livello1.txt");
		h.setLivello(l);
	}
	
	@Override
	public void aggiorna() {
		l.aggiorna();
	}

	@Override
	public void disegna(Graphics g) {
		l.disegna(g);
	}

}
