package gfx;

import entita.Entita;
import gioco.Handler;
import tiles.Tile;

public class CameraGioco {
	
	private Handler h;
	private float xOffset, yOffset;
	
	public CameraGioco(Handler gioco, float xOffset, float yOffset){
		this.h = gioco;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	public void verificaSpazioVuoto(){
		if(xOffset < 0){
			xOffset = 0;
		}else if(xOffset > h.getLivello().getLarghezza()* Tile.LARGHEZZA_TILE - h.getLarghezza()){
			xOffset = h.getLivello().getLarghezza()* Tile.LARGHEZZA_TILE - h.getLarghezza();
		}
		if(yOffset < 0){
			yOffset = 0;
		}else if(yOffset > h.getAltezza()* Tile.ALTEZZA_TILE - h.getAltezza()){
			yOffset = h.getAltezza()* Tile.ALTEZZA_TILE - h.getAltezza();
		}
		
	}
	
	public void centra(Entita e){
		xOffset = e.getX() - h.getLarghezza() / 2 + e.getLarghezza() / 2;
		yOffset = e.getY() - h.getAltezza() / 2 + e.getAltezza() / 2;
		verificaSpazioVuoto();
	}
	
	public void muovi(float xAmt, float yAmt){
		xOffset += xAmt;
		yOffset += yAmt;
		verificaSpazioVuoto();
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
