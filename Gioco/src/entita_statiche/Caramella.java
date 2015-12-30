package entita_statiche;

import java.awt.Graphics;

import gfx.Risorse;
import gioco.Handler;
import tiles.Tile;

public class Caramella  extends EntitaStatica {

	public Caramella(Handler h, float x, float y) {
		super(h, x, y,Tile.TILE_LARGHEZZA, Tile.TILE_ALTEZZA);
	}

	@Override
	public void aggiorna() {
	}

	@Override
	public void disegna(Graphics g) {
		g.drawImage(Risorse.caramella, (int) (x - h.getCameraGioco().getxOffset()), 
				(int) (y - h.getCameraGioco().getyOffset()), larghezza, altezza, null);
		
	}

}
