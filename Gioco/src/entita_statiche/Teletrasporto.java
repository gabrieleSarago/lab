package entita_statiche;

import gfx.Risorse;
import gioco.Handler;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import tiles.Tile;

public class Teletrasporto extends EntitaStatica{

	private float destinazioneX, destinazioneY;
	private boolean attivo;
	public Teletrasporto(Handler h, float x, float y, float destinazioneX, float destinazioneY) {
		super(h, x, y, Tile.TILE_LARGHEZZA, Tile.TILE_LARGHEZZA);
		attivo = true;
		this.destinazioneX = destinazioneX;
		this.destinazioneY = destinazioneY;
	}

	@Override
	public void aggiorna() {		
	}

	@Override
	public void disegna(Graphics g) {
		g.drawImage(getAnimazioneCorrente(), (int) (x - h.getCameraGioco().getxOffset()), 
				(int) (y - h.getCameraGioco().getyOffset()), larghezza, altezza, null);
		
	}
	
	private BufferedImage getAnimazioneCorrente(){
		if(attivo)
			return Risorse.teletrasporto_attivo;
		else
			return Risorse.teletrasporto_inattivo;
		
	}

	public float getDestinazioneX() {
		return destinazioneX;
	}

	public void setDestinazioneX(float destinazioneX) {
		this.destinazioneX = destinazioneX;
	}

	public float getDestinazioneY() {
		return destinazioneY;
	}

	public void setDestinazioneY(float destinazioneY) {
		this.destinazioneY = destinazioneY;
	}
	
	public boolean eAttivo() {
		return attivo;
	}

	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}
	
	public void cambiaAttivo() {
		this.attivo = !attivo;
	}
	
}
