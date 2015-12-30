package entita_statiche;

import entita.Entita;
import gioco.Handler;

public abstract class EntitaStatica extends Entita{

	public EntitaStatica(Handler h, float x, float y, int larghezza, int altezza) {
		super(h, x, y, larghezza, altezza);
	}
	
}
