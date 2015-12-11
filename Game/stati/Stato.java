package stati;

import java.awt.Graphics;

import gioco.Handler;

public abstract class Stato {
	
	private static Stato statoCorrente = null;
	
	public static void setStato(Stato stato){
		statoCorrente = stato;
	}
	
	public static Stato getStato(){
		return statoCorrente;
	}
	
	//CLASS
	
	protected Handler h;
	
	public Stato (Handler h){
		this.h = h;
	}
	public abstract void aggiorna();
	
	public abstract void disegna (Graphics g);

}
