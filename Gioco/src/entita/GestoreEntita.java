package entita;

import java.awt.Graphics;
import java.util.ArrayList;

import entita_statiche.Caramella;
import entita_statiche.InterruttorePressione;
import entita_statiche.Teletrasporto;
import entita_statiche.Trofeo;
import personaggio.Personaggio;
import personaggio.Sink;
import stati.StatoGioco; // aggiunto
import gioco.Handler;

public class GestoreEntita {

	private Handler h;
	private Sink sink; 
	private ArrayList<Entita> entita;
	private Entita ultimaCollisione_sink;
	
	public GestoreEntita(Handler h, Sink sink)
	{
		this.h = h;
		this.sink = sink;
		entita = new ArrayList<Entita>();
		aggiungiEntita(sink);
	}
	public void aggiorna()
	{
		for(int i=0; i < entita.size(); i++)
		{
			Entita e = entita.get(i);
			e.aggiorna();
			if(e instanceof Personaggio )
			{
				gestisciCollisioni((Personaggio)e);
			}
		}
	}
	
	public void disegna(Graphics g){
		for(Entita e : entita)
		{
			e.disegna(g);
		}
	}
	
	public void aggiungiEntita(Entita e)
	{
		entita.add(e);
	}
	
	public void rimuoviEntita(Entita e)
	{
		entita.remove(e);
	}
	
	
	// metodi di get e set 
	public Handler getH() {
		return h;
	}
	public void setH(Handler h) {
		this.h = h;
	}
	public Sink getSink() {
		return sink;
	}
	public void setSink(Sink sink) {
		this.sink = sink;
	}
	public ArrayList<Entita> getEntita() {
		return entita;
	}
	public void setEntita(ArrayList<Entita> entita) {
		this.entita = entita;
	}
	
	//---- gestisce le collisioni
	public void gestisciCollisioni(Personaggio p)
	{
		if(p.getUltimaEntita() == null) {
			ultimaCollisione_sink = null;
			return;
		}
		if(p instanceof Sink){
			collisioniConSink((Sink) p);
		}
		p.setUltimaEntita(null);
		
	}
	
	private void collisioniConSink(Sink s)
	{
		if(s.getUltimaEntita() instanceof Caramella){
			s.setTempo(s.getTempo()+5);
			rimuoviEntita(s.getUltimaEntita());
		}
		if(s.getUltimaEntita() instanceof Trofeo) {
			((StatoGioco)h.getGioco().getStato()).setVittoria(true);
			
		}
		
		if(s.getUltimaEntita() instanceof InterruttorePressione)
			if(!(ultimaCollisione_sink instanceof InterruttorePressione) || 
					!(ultimaCollisione_sink.equals(s.getUltimaEntita()))) 
				((InterruttorePressione)s.getUltimaEntita()).funzione();
		
		if(s.getUltimaEntita() instanceof Teletrasporto){
				s.setX(((Teletrasporto)s.getUltimaEntita()).getDestinazioneX());
				s.setY(((Teletrasporto)s.getUltimaEntita()).getDestinazioneY());
		}
		
		ultimaCollisione_sink = s.getUltimaEntita();
	}
	
}
