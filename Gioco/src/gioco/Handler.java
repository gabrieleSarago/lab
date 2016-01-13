package gioco;

import gfx.CameraGioco;
import input.GestioneInput;
import lingue.Lingua;
import livelli.Livello;
import stati.Stato;

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
