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
	
	public StatoGioco(Handler h){
		super(h);
		l = new Livello(h, "res/livelli/livello1.txt");
		h.setLivello(l);
		x = l.sinkX;
		y = l.sinkY;
		tempo = l.tempo;
		s = new Sink(h, x, y, tempo);
	}
	
	public StatoGioco(Handler h, String file){
		super(h);
		l = new Livello(h, file);
		h.setLivello(l);
		x = l.sinkX;
		y = l.sinkY;
		tempo = l.tempo;
		s = new Sink(h, x, y, tempo);
	}

	@Override
	public void aggiorna() {
		if(!(h.getGioco().getPausa())){
			getInput();
			l.aggiorna();
			s.aggiorna();
			if (s.getTempo()<0){
				h.setStato(new StatoMenu(h));
			}
		}
	}

	@Override
	public void disegna(Graphics g) {
		l.disegna(g);
		s.disegna(g);		
	}
	
	private void getInput(){
		if(h.getGestioneInput().esc){
			h.getGioco().setPausa(true);
			h.getGioco().setStato(new StatoPausa(h, this));

		}
	}
	
	public Sink getSink(){
		return s;
	}
	
	
}
