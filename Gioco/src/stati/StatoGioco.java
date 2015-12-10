package stati;

import java.awt.Graphics;

import gioco.Handler;
import livelli.Livello;
import personaggio.Sink;

public class StatoGioco extends Stato {
	
	private Sink s;
	private Livello l;
	private int x;
	private int y;
	private int tempo;
	private Stato sm;
	
	public StatoGioco(Handler h){
		super(h);
		l = new Livello(h, "res/livelli/livello1.txt");
		h.setLivello(l);
		x = l.sinkX;
		y = l.sinkY;
		tempo = l.tempo;
		s = new Sink(h, x, y, tempo);
	}

	@Override
	public void aggiorna() {
		l.aggiorna();
		s.aggiorna();
		if (s.getTempo()<0){
			sm = new StatoMenu(h);
			h.setStato(sm);
		}
	}

	@Override
	public void disegna(Graphics g) {
		l.disegna(g);
		s.disegna(g);		
	}
	
}
