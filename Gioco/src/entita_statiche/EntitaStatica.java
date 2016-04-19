package entita_statiche;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import entita.Entita;
import gioco.Handler;

public abstract class EntitaStatica extends Entita{

	public EntitaStatica(){} //necessario per esternalizzazione
	public EntitaStatica(Handler h, float x, float y, int larghezza, int altezza) {
		super(h, x, y, larghezza, altezza);
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
	}
	
	
}
