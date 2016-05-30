package tiles;

import gfx.Risorse;
/**
 * Crea l'oggetto TileMuro.
 */
public class TileMuro extends Tile {

	/**
	 * Costruisce l'oggetto TileMuro.
	 * @param id intero identificativo.
	 */
	public TileMuro(int id) {
		super(Risorse.muro, id);
	}
	
	/**
	 * Differenzia oggetti solidi da oggetti non solidi.
	 * Utile per le collisioni.
	 */
	@Override
	public boolean eSolido(){
		return true;
	}
}
