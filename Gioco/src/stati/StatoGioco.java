package stati;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import entita.Entita;
import entita_statiche.Caramella;
import entita_statiche.EntitaStatica;
import entita_statiche.Trofeo;
import gioco.Handler;
import livelli.Livello;
import personaggio.Sink;

public class StatoGioco extends Stato {
	
	private Sink s;
	private Livello l;
	private int x;
	private int y;
	private int tempo;
	private ArrayList<EntitaStatica> entitaStatiche;	
	private boolean vittoria = false;
	
	public StatoGioco(Handler h){
		super(h);
		l = new Livello(h, "res/livelli/livello1.txt");
		h.setLivello(l);
		x = l.sinkX;
		y = l.sinkY;
		tempo = l.tempo;
		entitaStatiche = l.entitaStatiche;
		s = new Sink(h, x, y, tempo);
	}
	
	public StatoGioco(Handler h, String file){
		super(h);
		l = new Livello(h, file);
		h.setLivello(l);
		x = l.sinkX;
		y = l.sinkY;
		tempo = l.tempo;
		entitaStatiche = l.entitaStatiche;
		s = new Sink(h, x, y, tempo);
	}

	@Override
	public void aggiorna() {
		if(tempo > 100)
			tempo = 100;
		
		if(!(h.getGioco().getPausa())){
			getInput();
			controlla();
			l.aggiorna();
			for(int i = 0; i < entitaStatiche.size(); i++){
				EntitaStatica c = entitaStatiche.get(i);
				c.aggiorna();
			}
			s.aggiorna();
			if (s.getTempo()<0){
				h.setStato(new StatoMenu(h));
			}
		}
	}

	@Override
	public void disegna(Graphics g) {
		l.disegna(g);
		
		for(int i = 0; i < entitaStatiche.size(); i++){
			EntitaStatica c = entitaStatiche.get(i);
			c.disegna(g);
		}
		s.disegna(g);
		if (vittoria){
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.BOLD,125));
			g.drawString("YOU WIN", 200, 300);
		}
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
	
	public void controlla(){
		for (int i = 0; i < entitaStatiche.size(); i++){
			Entita e = entitaStatiche.get(i);
			if ((e instanceof Caramella) && s.getX()<e.getX() && s.getX()>(e.getX()-(e.getLarghezza()/2)) &&
					s.getY()<e.getY() && s.getY()>(e.getY()-(e.getAltezza()/2))){
				s.setTempo(s.getTempo()+5);
				entitaStatiche.remove(e);
				}
			if ((e instanceof Trofeo) && s.getX()<e.getX() && s.getX()>(e.getX()-(e.getLarghezza()/2)) &&
					s.getY()<e.getY() && s.getY()>(e.getY()-(e.getAltezza()/2))){
				vittoria =true;
			}
	    }
	
    }
	
}
