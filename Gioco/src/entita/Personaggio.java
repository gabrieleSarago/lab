package entita;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import gioco.Handler;
import grafica.Animazione;
import grafica.Tile;
/**
 * Crea l'oggetto Personaggio.
 */
public abstract class Personaggio extends Entita {
	private static final long serialVersionUID = 9L;
	
	public static final int DEFAULT_VITA = 100;
	public static final float DEFAULT_VELOCITA = 6.0f;
	public static final int DEFAULT_LARGHEZZA_PERSONAGGIO = 64;
	public static final int DEFAULT_ALTEZZA_PERSONAGGIO = 64;
	
	protected int vita;
	protected float velocita;
	protected float dx, dy;
	protected Entita ultimaEntita;
	
	protected enum Movimento{SOPRA, SOTTO, DESTRA, SINISTRA}
	
	protected boolean muoviX = false, muoviY = false;
	
	//Animazione
	protected Animazione personaggioSotto, personaggioSopra, personaggioSinistra, personaggioDestra;
	
	//AnimazioneFermo
	protected Animazione personaggioSottoFermo, personaggioSinistraFermo, personaggioDestraFermo;
	protected BufferedImage personaggio_sopra_fermo, personaggio_sotto_fermo, personaggio_destra_fermo, personaggio_sinistra_fermo;
	protected Movimento ultimoMovimento = Movimento.SOTTO;
	
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
		ultimaEntita = null;
	}
	
	/**
	 * Muove il personaggio.
	 */
	public void muovi(){
		muoviX = false;
		muoviY = false;
		Entita temp; // entita solo temporanea
		// se temp e' null significa che non c e' nessuna collisione
		temp = controllaCollisioni(dx, 0f);
		if(temp == null)
			muoviX();
		else{
			ultimaEntita = temp;
			if(temp.eAttraversabile())
				muoviX();
		}
		temp = controllaCollisioni(0f, dy);
		if(temp == null)
			muoviY();
		else{
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
				muoviX = true;
			}else{
				x = tx * Tile.TILE_LARGHEZZA - bounds.x - bounds.width - 1;//il -1 serve altrimenti non si puo' andare ne sopra e ne sotto
				muoviX = false;
			}
				
		}else if(dx < 0){
			int tx = (int) (x + dx + bounds.x ) / Tile.TILE_LARGHEZZA;
			
			if(!collisioneConTile(tx, (int) (y + bounds.y) / Tile.TILE_ALTEZZA) &&
				!collisioneConTile(tx, ((int) y + bounds.y + bounds.height) / Tile.TILE_ALTEZZA)){
				x += dx;
				muoviX = true;
			}else{
				x = tx * Tile.TILE_LARGHEZZA + Tile.TILE_LARGHEZZA - bounds.x;
				muoviX = false;
			}
		}
	}
	
	public void muoviY(){
		if(dy < 0){
			int ty = (int) (y + dy + bounds.y) / Tile.TILE_ALTEZZA;
			
			if(!collisioneConTile((int) (x + bounds.x ) / Tile.TILE_LARGHEZZA, ty) && 
					!collisioneConTile((int) (x + bounds.x + bounds.width ) / Tile.TILE_LARGHEZZA, ty)){
				y += dy;
				muoviY = true;
			}else{
				y = ty * Tile.TILE_ALTEZZA + Tile.TILE_ALTEZZA - bounds.y;
				muoviY = false;
			}
			
		}else if(dy > 0){
			int ty = (int) (y + dy + bounds.y + bounds.height) / Tile.TILE_ALTEZZA;
			
			if(!collisioneConTile((int) (x + bounds.x ) / Tile.TILE_LARGHEZZA, ty) && 
					!collisioneConTile((int) (x + bounds.x + bounds.width ) / Tile.TILE_LARGHEZZA, ty)){
				y += dy;
				muoviY = true;
			}else{
				y = ty * Tile.TILE_ALTEZZA - bounds.y - bounds.height - 1;
				muoviY = false;
			}
		}
	}
	
	public void aggiorna(){
		//Miglioramenti aggiornamenti animazioni.
		 if(dx < 0)
			 personaggioSinistra.aggiorna();
		 else if(dx > 0)
			 personaggioDestra.aggiorna();
		 else if(dy < 0)
			 personaggioSopra.aggiorna();
		 else if(dy > 0)
			 personaggioSotto.aggiorna();
		 else{
			 switch(ultimoMovimento){
			 case SOTTO: personaggioSottoFermo.aggiorna();break;
			 case SINISTRA: personaggioSinistraFermo.aggiorna(); break;
			 case DESTRA: personaggioDestraFermo.aggiorna();break;
			 case SOPRA: break;
			 }
		}
	}
	
	protected BufferedImage getFrameAnimazioneCorrente(){
		if (dx < 0){
			ultimoMovimento = Movimento.SINISTRA;
			if(!(muoviX || muoviY)){
				return personaggioSinistraFermo.getFrameCorrente();
			}
			return personaggioSinistra.getFrameCorrente();
		}else if(dx > 0){
			ultimoMovimento = Movimento.DESTRA;
			if(!(muoviX || muoviY)){
				return personaggioDestraFermo.getFrameCorrente();
			}
			return personaggioDestra.getFrameCorrente();
		}else if (dy < 0){
			ultimoMovimento = Movimento.SOPRA;
			if(!(muoviX || muoviY)){
				return personaggio_sopra_fermo;
			}
			return personaggioSopra.getFrameCorrente();
		}else if(dy >0){
			ultimoMovimento = Movimento.SOTTO;
			if(!(muoviX || muoviY)){
				return personaggioSottoFermo.getFrameCorrente();
			}
			return personaggioSotto.getFrameCorrente();
		}else{
			if(ultimoMovimento == Movimento.SINISTRA){
				return personaggioSinistraFermo.getFrameCorrente();
			}else if(ultimoMovimento == Movimento.DESTRA){
				return personaggioDestraFermo.getFrameCorrente();
			}else if(ultimoMovimento == Movimento.SOTTO){
				return personaggioSottoFermo.getFrameCorrente();
			}else{
				return personaggio_sopra_fermo;
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
	
	public Entita getUltimaEntita(){
		return ultimaEntita;
	}
	
	public void setUltimaEntita(Entita ultimaEntita){
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
