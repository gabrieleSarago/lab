package gfx;

import entita.Entita;
import gioco.Handler;
import tiles.Tile;

public class CameraGioco {
	
	private Handler h;
	private float xOffset, yOffset;
	
	public CameraGioco(Handler h, float xOffset, float yOffset){
		this.h = h;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	public void inquadra(){
		if (xOffset < 0){
			xOffset = 0;
		}else if(xOffset > h.getLivello().getLarghezza() * Tile.TILE_LARGHEZZA - h.getLarghezza())
			xOffset = h.getLivello().getLarghezza() * Tile.TILE_LARGHEZZA - h.getLarghezza();
		if (yOffset < 0){
			yOffset = 0;
		}else if(yOffset > h.getLivello().getAltezza() * Tile.TILE_ALTEZZA - h.getAltezza())
			yOffset = h.getLivello().getAltezza() * Tile.TILE_ALTEZZA - h.getAltezza();
	}
	
	public void centra(Entita e){
		xOffset = e.getX() - h.getLarghezza() / 2 + e.getLarghezza() / 2;
		yOffset = e.getY() - h.getAltezza() / 2 + e.getAltezza() / 2;
		inquadra();
	}
	
	public void muovi(float xAmt, float yAmt){
		xOffset += xAmt;
		yOffset += yAmt;
		inquadra();
	}

	public float getxOffset() {
		return xOffset;
	}

	public void setxOffset(float xOffset) {
		this.xOffset = xOffset;
	}

	public float getyOffset() {
		return yOffset;
	}

	public void setyOffset(float yOffset) {
		this.yOffset = yOffset;
	}
	
}
