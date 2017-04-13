package gioco;

import livelli.Livello;
import stati.Stato;
import strumenti.CameraGioco;
import strumenti.GestioneInput;
import strumenti.Lingua;
import suoni.Suono;

/**
 * Oggetto "manipolatore" del gioco.
 * Serve a gestire la comunicazione tra le diverse classi del progetto.
 */
public class Handler {
	
	private Gioco gioco;
	private Livello l;
	private Stato s;
	
	public Handler(Gioco gioco){
		this.gioco = gioco;
	}
	
	public CameraGioco getCameraGioco(){
		return gioco.getCameraGioco();
	}
	
	public GestioneInput getGestioneInput(){
		return gioco.getGestioneInput();
	}
	
	public Suono getSuono(){
		return gioco.getSuono();
	}
	public int getLarghezza(){
		return gioco.getLarghezza();
	}
	
	public int getAltezza(){
		return gioco.getAltezza();
	}
	
	public Gioco getGioco() {
		return gioco;
	}
	
	public Lingua getLingua(){
		return gioco.getLingua();
	}

	public void setGioco(Gioco gioco) {
		this.gioco = gioco;
	}

	public Livello getLivello() {
		return l;
	}

	public void setLivello(Livello l) {
		this.l = l;
	}
	
	public Stato getStato(){
		return s;
	}
	
	public void setStato(Stato s){
		this.s = s;
		gioco.setStato(s);
	}
}
