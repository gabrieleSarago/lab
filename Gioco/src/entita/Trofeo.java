package entita;

import java.awt.Graphics;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import gioco.Handler;
import grafica.Risorse;
import grafica.Tile;
/**
 * Crea l'oggetto Trofeo che identifica la vittoria del giocatore
 * ed e' un entita' statica.
 */
public class Trofeo extends EntitaStatica {
	private static final long serialVersionUID = 13L;
	
	public Trofeo(){}// per esternalizzazione
	/**
	 * Costruttore dell'oggetto Trofeo.
	 * @param h
	 * @param x
	 * @param y
	 */
	public Trofeo(Handler h, float x, float y) {
		super(h, x, y, Tile.TILE_LARGHEZZA, Tile.TILE_ALTEZZA);
	}
	
	@Override
	public void aggiorna() {}

	/**
	 * Disegna l'oggetto Trofeo mediante un oggetto Graphics.
	 */
	@Override
	public void disegna(Graphics g) {
		g.drawImage(Risorse.trofeo, (int) (x - h.getCameraGioco().getxOffset()), 
				(int) (y - h.getCameraGioco().getyOffset()), larghezza, altezza, null);
	}
	
	/**
	 * Salva la posizione dell'oggetto Trofeo.
	 */
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		super.writeExternal(out);		
	}
	/**
	 * Carica l'oggetto Trofeo nella sua posizione.
	 */
	@Override
	public void readExternal(ObjectInput in) throws IOException,
	ClassNotFoundException {
		super.readExternal(in);	
	}
}