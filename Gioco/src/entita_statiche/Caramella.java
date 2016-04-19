package entita_statiche;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import gfx.Risorse;
import gioco.Handler;
import tiles.Tile;

public class Caramella  extends EntitaStatica {

	public Caramella(){}// per esternalizzazione
	public Caramella(Handler h, float x, float y) {
		super(h, x, y,Tile.TILE_LARGHEZZA, Tile.TILE_ALTEZZA);
		bounds = new Rectangle(0, 14, Tile.TILE_LARGHEZZA, Tile.TILE_ALTEZZA/2+2);
	}

	@Override
	public void aggiorna() {
	}

	@Override
	public void disegna(Graphics g) {
		g.drawImage(Risorse.caramella, (int) (x - h.getCameraGioco().getxOffset()), 
				(int) (y - h.getCameraGioco().getyOffset()), larghezza, altezza, null);
		
	}
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		super.writeExternal(out);
	}
	@Override
	public void readExternal(ObjectInput in) throws IOException,
	ClassNotFoundException {
		super.readExternal(in);
		bounds = new Rectangle(0, 14, Tile.TILE_LARGHEZZA, Tile.TILE_ALTEZZA/2+2);
	}
	
	

}
