package entita_statiche.funzione;

import entita_statiche.EntitaStatica;
import entita_statiche.Interruttore;
import entita_statiche.Sbarra;
import entita_statiche.Teletrasporto;
//implementa tutte le possibili funzioni di un iterruttore

public class Funzione {
	
	private EntitaStatica collegamento;	
	private Funzionalita funzione;
	
	public Funzione(EntitaStatica collegamento, Funzionalita funzione)
	{
		if(collegamento == null || funzione == null) throw new IllegalArgumentException("parametri null");
		this.collegamento = collegamento;
		this.funzione = funzione;
	}
	
	public Funzione(Funzione f)
	{
		this.collegamento = f.collegamento;
		this.funzione = f.funzione;
	}
	

	public void eseguiFunzione()
	{
		//TO-DO aggiungere tutte le possibili azioni
		switch(funzione) {
			case APRI_SBARRA:
				if(!(collegamento instanceof Sbarra)) throw new IllegalArgumentException("aspettata Sbarra");
					((Sbarra) collegamento).apri();
				break;
			case CHIUDI_SBARRA:
				if(!(collegamento instanceof Sbarra)) throw new IllegalArgumentException("aspettata Sbarra");
					((Sbarra)collegamento).chiudi();
				break;
			case CAMBIA_SBARRA:
				if(!(collegamento instanceof Sbarra)) throw new IllegalArgumentException("aspettata Sbarra");
					((Sbarra)collegamento).cambia();
				break;
			case ATTIVA_INTERRUTTORE:
				if(!(collegamento instanceof Interruttore)) throw new IllegalArgumentException("aspettato Interruttore");
					((Interruttore)collegamento).setAttivo(true);
				break;
			case DISATTIVA_INTERRUTTORE:
				if(!(collegamento instanceof Interruttore)) throw new IllegalArgumentException("aspettato Interruttore");
					((Interruttore)collegamento).setAttivo(false);
				break;
			case CAMBIA_INTERRUTTORE:
				if(!(collegamento instanceof Interruttore)) throw new IllegalArgumentException("aspettato Interruttore");
					((Interruttore)collegamento).cambiaAttivo();
				break;
			case ATTIVA_TELETRASPORTO:
				if(!(collegamento instanceof Teletrasporto)) throw new IllegalArgumentException("aspettato Teletrasporto");
					((Teletrasporto)collegamento).setAttivo(true);
				break;
			case DISATTIVA_TELETRASPORTO:
				if(!(collegamento instanceof Teletrasporto)) throw new IllegalArgumentException("aspettato Teletrasporto");
					((Teletrasporto)collegamento).setAttivo(false);
				break;
			case CAMBIA_TELETRASPORTO:
				if(!(collegamento instanceof Teletrasporto)) throw new IllegalArgumentException("aspettato Teletrasporto");
					((Teletrasporto)collegamento).cambiaAttivo();
				break;
			default:
				throw new IllegalArgumentException("funzione non gestita");
				
			}//chiudo switch			
	}

	public Funzione getFunzioni() {
		return new Funzione(collegamento, funzione);
	}

	public void setFunzione(Funzione funzione) {
		this.funzione = funzione.funzione;
		this.collegamento = funzione.collegamento;
	}
	
	
}