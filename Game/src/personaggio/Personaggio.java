package personaggio;

import entita.Entita;
import gioco.Handler;
import tiles.Tile;

public abstract class Personaggio extends Entita{
	
	public static final int DEFAULT_VITA = 100;
	public static final float DEFAULT_VELOCITA = 3.0f;
	public static final int DEFAULT_LARGHEZZA_PERSONAGGIO = 64,
							DEFAULT_ALTEZZA_PERSONAGGIO = 64;
	
	
	protected int vita;
	protected float velocita;
	protected float dx, dy;
	
	public Personaggio(Handler h, float x, float y, int larghezza, int altezza) {
		super(h, x, y, larghezza, altezza);
		vita = DEFAULT_VITA;
		velocita = DEFAULT_VELOCITA;
		dx = 0;
		dy = 0;
	}
	
	public void muovi(){
		if(!verificaCollisioniEntita(dx, 0f))
			muoviX();
		if(!verificaCollisioniEntita(0f, dy))
			muoviY();
	}
	
	public void muoviX(){
		if(dx > 0){// muovi a destra
			
			int tx = (int) (x + dx + bounds.x + bounds.width) / Tile.LARGHEZZA_TILE;
			if(!collisioneConTile(tx, (int)(y + bounds.y) / Tile.ALTEZZA_TILE) &&
					!collisioneConTile(tx, (int) (y + bounds.y + bounds.height) / Tile.ALTEZZA_TILE)){
				x += dx;
			}else{
				x = tx * Tile.LARGHEZZA_TILE - bounds.x - bounds.width -1;
			}
			
		}else if(dx < 0){//muovi a sinistra
			int tx = (int) (x + dx + bounds.x) / Tile.LARGHEZZA_TILE;
			if(!collisioneConTile(tx, (int)(y + bounds.y) / Tile.ALTEZZA_TILE) &&
					!collisioneConTile(tx, (int) (y + bounds.y + bounds.height ) / Tile.ALTEZZA_TILE)){
				x += dx;
			}
			else{
				x = tx*Tile.LARGHEZZA_TILE + Tile.LARGHEZZA_TILE - bounds.x;
			}
		}
	}
	
	public void muoviY(){
		if(dy <0){
			int ty = (int) (y + dy + bounds.y)/Tile.ALTEZZA_TILE;
			if(!collisioneConTile((int) (x + bounds.x) / Tile.LARGHEZZA_TILE, ty) &&
					!collisioneConTile((int) (x + bounds.x + bounds.width) / Tile.LARGHEZZA_TILE, ty)){
				y += dy;
			}else{
				y = ty * Tile.LARGHEZZA_TILE + Tile.LARGHEZZA_TILE - bounds.y;
			}
		}else if(dy > 0){
			int ty = (int) (y + dy + bounds.y + bounds.height)/Tile.ALTEZZA_TILE;
			if(!collisioneConTile((int) (x + bounds.x) / Tile.LARGHEZZA_TILE, ty) &&
					!collisioneConTile((int) (x + bounds.x + bounds.width) / Tile.LARGHEZZA_TILE, ty)){
				y += dy;
			}else{
				y = ty * Tile.ALTEZZA_TILE - bounds.y - bounds.height -1;
			}
		}
	}
	
	protected boolean collisioneConTile(int x, int y){
		return h.getLivello().getTile(x, y).eSolido();
	}
	
	//getters-setters
	
	public int getVita() {
		return vita;
	}

	public void setVita(int vita) {
		this.vita = vita;
	}

	public float getVelocita() {
		return velocita;
	}

	public void setVelocita(float velocita) {
		this.velocita = velocita;
	}
	
	public float getDx() {
		return dx;
	}

	public void setDx(float dx) {
		this.dx = dx;
	}

	public float getDy() {
		return dy;
	}

	public void setDy(float dy) {
		this.dy = dy;
	}
	
	
}
