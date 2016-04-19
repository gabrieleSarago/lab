package entita_statiche;

import java.awt.Graphics;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import gfx.Risorse;
import gioco.Handler;
import tiles.Tile;

public class Trofeo extends EntitaStatica {

	public Trofeo(){}// per esternalizzazione
	public Trofeo(Handler h, float x, float y) {
		super(h, x, y, Tile.TILE_LARGHEZZA, Tile.TILE_ALTEZZA);
	}
	
	@Override
	public void aggiorna() {		
	}

	@Override
	public void disegna(Graphics g) {
		g.drawImage(Risorse.trofeo, (int) (x - h.getCameraGioco().getxOffset()), 
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
	}

}
