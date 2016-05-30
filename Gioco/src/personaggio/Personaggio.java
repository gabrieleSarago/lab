package personaggio;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import entita.Entita;
import gioco.Handler;
import tiles.Tile;
/**
 * Crea l'oggetto Personaggio.
 */
public abstract class Personaggio extends Entita {
	
	public static final int DEFAULT_VITA = 100;
	public static final float DEFAULT_VELOCITA = 6.0f;
	public static final int DEFAULT_LARGHEZZA_PERSONAGGIO = 64;
	public static final int DEFAULT_ALTEZZA_PERSONAGGIO = 64;
	
	protected int vita;
	protected float velocita;
	protected float dx, dy;
	//---- per gestire le collisioni. Dice l' ultima entita con cui la creatura è andata a collidere
	protected Entita ultimaEntita;
	
	public Personaggio(){} // necessario per esternzalizzazione
	/**
	 * Costruisce l'oggetto Personaggio.
	 * @param h oggetto Handler utile per la gestione con le altre classi.
	 * @param x coordinata X del personaggio.
	 * @param y coordinata Y del personaggio.
	 * @param larghezza larghezza del personaggio.
	 * @param altezza altezza del personaggio.
	 */
	public Personaggio(Handler h, float x, float y, int larghezza, int altezza) {
		super(h, x, y, larghezza, altezza);
		vita = DEFAULT_VITA;
		velocita = DEFAULT_VELOCITA;
		dx = 0;
		dy = 0;
		//----
		ultimaEntita = null;
	}
	
	/**
	 * Muove il personaggio.
	 */
	public void muovi(){
		Entita temp; // entita solo temporanea
		// se temp è null significa che non c è nessuna collisione
		temp = controllaCollisioni(dx, 0f);
		if(temp == null)
			muoviX();
		else
		{
			ultimaEntita = temp;
			if(temp.eAttraversabile())
				muoviX();
		}
		temp = controllaCollisioni(0f, dy);
		if(temp == null)
			muoviY();
		else
		{
			ultimaEntita = temp;
			if(temp.eAttraversabile())
				muoviY();
		}
	}
	
	public void muoviX(){
		if(dx > 0){
			int tx = (int) (x + dx + bounds.x + bounds.width) / Tile.TILE_LARGHEZZA;
			
			if(!collisioneConTile(tx, (int) (y + bounds.y) / Tile.TILE_ALTEZZA) &&
				!collisioneConTile(tx, ((int) y + bounds.y + bounds.height) / Tile.TILE_ALTEZZA)){
				x += dx;
			}else{
				x = tx * Tile.TILE_LARGHEZZA - bounds.x - bounds.width - 1;//il -1 serve altrimenti non si può andare ne sopra e ne sotto
			}
				
		}else if(dx < 0){
			int tx = (int) (x + dx + bounds.x ) / Tile.TILE_LARGHEZZA;
			
			if(!collisioneConTile(tx, (int) (y + bounds.y) / Tile.TILE_ALTEZZA) &&
				!collisioneConTile(tx, ((int) y + bounds.y + bounds.height) / Tile.TILE_ALTEZZA)){
				x += dx;
			}else{
				x = tx * Tile.TILE_LARGHEZZA + Tile.TILE_LARGHEZZA - bounds.x;
			}
		}
	}
	
	public void muoviY(){
		if(dy < 0){
			int ty = (int) (y + dy + bounds.y) / Tile.TILE_ALTEZZA;
			
			if(!collisioneConTile((int) (x + bounds.x ) / Tile.TILE_LARGHEZZA, ty) && 
					!collisioneConTile((int) (x + bounds.x + bounds.width ) / Tile.TILE_LARGHEZZA, ty)){
				y += dy;
			}else{
				y = ty * Tile.TILE_ALTEZZA + Tile.TILE_ALTEZZA - bounds.y;
			}
			
		}else if(dy > 0){
			int ty = (int) (y + dy + bounds.y + bounds.height) / Tile.TILE_ALTEZZA;
			
			if(!collisioneConTile((int) (x + bounds.x ) / Tile.TILE_LARGHEZZA, ty) && 
					!collisioneConTile((int) (x + bounds.x + bounds.width ) / Tile.TILE_LARGHEZZA, ty)){
				y += dy;
			}else{
				y = ty * Tile.TILE_ALTEZZA - bounds.y - bounds.height - 1;
			}
		}
	}
	
	protected boolean collisioneConTile(int x, int y){
		return h.getLivello().getTile(x, y).eSolido();
	}
	
	//GETTERS SETTERS

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
	
	//---- aggiunto
	public Entita getUltimaEntita()
	{
		return ultimaEntita;
	}
	
	public void setUltimaEntita(Entita ultimaEntita)
	{
		this.ultimaEntita = ultimaEntita;
	}
	
	//per esternalizzazione
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		super.writeExternal(out);
	}
	@Override
	public void readExternal(ObjectInput in) throws IOException,
	ClassNotFoundException {
		super.readExternal(in);
		vita = DEFAULT_VITA;
		velocita = DEFAULT_VELOCITA;
		dx = 0;
		dy = 0;
		ultimaEntita = null;
	}
	
	
	
}
