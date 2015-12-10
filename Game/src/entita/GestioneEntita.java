package entita;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;

import gioco.Handler;
import personaggio.Sink;

public class GestioneEntita {
	
	private Handler h;
	private Sink s;
	private ArrayList<Entita> entita;
	
	private Comparator<Entita> comparaDisegno = new Comparator<Entita>(){

		@Override
		public int compare(Entita a, Entita b) {
			if(a.getY() + a.getAltezza() < b.getY() + b.getAltezza()) return -1;
			return 1;
		}
		
	};
	
	public GestioneEntita(Handler h, Sink s){
		this.h = h;
		this.s = s;
		entita = new ArrayList<>();
		addEntita(s);
	}
	
	public void aggiorna(){
		for(int i = 0; i<entita.size(); i++){
			Entita e = entita.get(i);
			e.aggiorna();
		}
		entita.sort(comparaDisegno);
		s.aggiorna();
	}
	
	public void disegna(Graphics g){
		for(Entita e : entita){
			e.disegna(g);
		}
		s.disegna(g);
	}
	
	public void addEntita(Entita e){
		entita.add(e);
	}

	public Handler getHandler() {
		return h;
	}

	public void setHandler(Handler h) {
		this.h = h;
	}

	public Sink getSink() {
		return s;
	}

	public void setSink(Sink s) {
		this.s = s;
	}

	public ArrayList<Entita> getEntita() {
		return entita;
	}

	public void setEntita(ArrayList<Entita> entita) {
		this.entita = entita;
	}
}
