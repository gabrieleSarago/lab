package entita_statiche;

import java.awt.Graphics;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import entita_statiche.funzione.Funzione;
import gioco.Handler;

public class InterruttoreInvisibile extends Interruttore{

	public InterruttoreInvisibile() {}// necessario per esternalizzazione
	public InterruttoreInvisibile(Handler h, float x, float y, int larghezza, int altezza, Funzione... funzione) {
		super(h, x, y, larghezza, altezza, funzione);
		
	}

	@Override
	public void aggiorna() {
	}

	@Override
	public void disegna(Graphics g) {
		/*g.setColor(Color.RED);
		g.fillRect((int)(x + bounds.x - h.getCameraGioco().getxOffset()),
				(int)(y + bounds.y - h.getCameraGioco().getyOffset()),
				bounds.width, bounds.height);*/
	}
		
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		super.writeExternal(out);
	}
	@Override
	public void readExternal(ObjectInput in) throws IOException,
	ClassNotFoundException {
		super.readExternal(in);
	}
	

}

