package entita;

import java.awt.Graphics;
import java.awt.Rectangle;

import gioco.Handler;

public abstract class Entita {
	
	protected Handler h;
	protected float x, y;
	protected int larghezza, altezza;
	protected Rectangle bounds;
	
	public Entita(Handler h, float x, float y, int larghezza, int altezza){
		this.h = h;
		this.x = x;
		this.y = y;
		this.altezza = altezza;
		this.larghezza = larghezza;
		
		bounds = new Rectangle(0, 0, larghezza, altezza);
	}
	
	public boolean verificaCollisioniEntita(float xOffset, float yOffset){
		for(Entita e : h.getLivello().getGestioneEntita().getEntita()){
			if(e.equals(this))
				continue;
			if(e.getMarginiCollisione(0f, 0f).intersects(getMarginiCollisione(xOffset, yOffset)))
				return true;
		}
		return false;
	}
	
	public Rectangle getMarginiCollisione(float xOffset, float yOffset){
		return new Rectangle((int)(x + bounds.x + xOffset), (int)(y + bounds.y + yOffset), bounds.width, bounds.height);
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getLarghezza() {
		return larghezza;
	}

	public void setLarghezza(int larghezza) {
		this.larghezza = larghezza;
	}

	public int getAltezza() {
		return altezza;
	}

	public void setAltezza(int altezza) {
		this.altezza = altezza;
	}

	public abstract void aggiorna();
	
	public abstract void disegna(Graphics g);
	
}
