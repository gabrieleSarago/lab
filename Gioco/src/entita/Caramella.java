package entita;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import gioco.Handler;
import grafica.Risorse;
import grafica.Tile;

/**
 * Costruisce l'oggetto Caramella.
 * Implementato all'interno del gioco aumenta il tempo/vita del giocatore.
 */

public class Caramella  extends EntitaStatica{

	private static final long serialVersionUID = 1L;
	
	public Caramella(){}// per esternalizzazione
	/**
	 * Costruttore di Caramella.
	 * @param h oggetto Handler per curare la gestione con le altre classi.
	 * @param x cordinata X dell'oggetto Caramella nel gioco.
	 * @param y corinata Y dell'oggetto Caramella nel gioco.
	 */
	public Caramella(Handler h, float x, float y) {
		super(h, x, y,Tile.TILE_LARGHEZZA, Tile.TILE_ALTEZZA);
		bounds = new Rectangle(0, 14, Tile.TILE_LARGHEZZA, Tile.TILE_ALTEZZA/2+2);
	}

	@Override
	public void aggiorna() {
	}

	/**
	 * Disegna l'oggetto Caramella mediante un oggetto Graphics
	 * sulla finestra di gioco.
	 */
	@Override
	public void disegna(Graphics g) {
		
		//g.fillRect((int)(x + bounds.x+5 - h.getCameraGioco().getxOffset()),
								//(int)(y + bounds.y+10 - h.getCameraGioco().getyOffset()),
				 				//bounds.width-10, bounds.height-20);
		
		g.drawImage(Risorse.caramella, (int) (x - h.getCameraGioco().getxOffset()), 
				(int) (y - h.getCameraGioco().getyOffset()), larghezza, altezza, null);
		
	}
	
	/**
	 * Metodo utile per il salvataggio delle posizioni degli oggetti Caramella.
	 */
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		super.writeExternal(out);
	}
	
	
	/**
	 * Metodo utile per il caricare gli oggetti Caramella nelle loro posizioni. 
	 */
	@Override
	public void readExternal(ObjectInput in) throws IOException,
	ClassNotFoundException {
		super.readExternal(in);
		bounds = new Rectangle(0, 14, Tile.TILE_LARGHEZZA, Tile.TILE_ALTEZZA/2+2);
	}
	
	

}
