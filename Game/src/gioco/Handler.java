package gioco;

import gfx.CameraGioco;
import input.GestioneInput;
import livelli.Livello;

public class Handler {
	
	private Gioco gioco;
	private Livello l;
	
	public Handler(Gioco gioco){
		this.gioco = gioco;
	}
	
	public int getLarghezza(){
		return gioco.getLarghezza();
	}
	
	public int getAltezza(){
		return gioco.getAltezza();
	}
	
	public CameraGioco getCameraGioco(){
		return gioco.getCameraGioco();
	}
	
	public GestioneInput getGestioneInput(){
		return gioco.getGestioneInput();
	}

	public Gioco getGioco() {
		return gioco;
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
}
