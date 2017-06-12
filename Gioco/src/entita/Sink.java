package entita;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.concurrent.TimeUnit;

import gioco.Handler;
import grafica.Animazione;
import grafica.Risorse;
/**
 * Crea l'oggetto Sink (Giocatore) del gioco.
 */
public class Sink extends Personaggio {
	private static final long serialVersionUID = 11L;

	private static final int X = 12;
	private static final int Y = 40;
	private static final int LARGHEZZA = 36;
	private static final int ALTEZZA = 20;

	private int tempo;
	private int maxTempo;
	private boolean sconfitta = false;

	//per regolarizzare i movmenti
	int fps = 60;
	double tempoDiAggiornamento = 1000000000 / fps;
	double delta = 0;
	long ora;
	long ultimoTempo = System.nanoTime();
	long timer = 0;

	public Sink(){} // necessario per esternalizzazione
	/**
	 * Costruisce l'oggetto Sink.
	 * @param h oggetto Handler utile per la gestione con le altre classi.
	 * @param x coordinata X del giocatore.
	 * @param y coordinata Y del giocatore.
	 * @param tempo tempo(vita) del giocatore.
	 */
	public Sink(Handler h, float x, float y, int tempo) {
		super(h, x, y, Personaggio.DEFAULT_LARGHEZZA_PERSONAGGIO, Personaggio.DEFAULT_ALTEZZA_PERSONAGGIO);
		this.tempo = tempo;

		maxTempo = tempo;

		bounds.x = X; // = 8
		bounds.y = Y;
		bounds.width = LARGHEZZA; //= 44
		bounds.height = ALTEZZA; // = 32

		//Animazioni
		personaggioSotto = new Animazione(30, Risorse.sink_sotto);
		personaggioSopra = new Animazione(30, Risorse.sink_sopra);
		personaggioSinistra = new Animazione(30, Risorse.sink_sinistra);
		personaggioDestra = new Animazione(30, Risorse.sink_destra);

		//AnimazioniFermo
		personaggioSottoFermo = new Animazione(300, Risorse.sink_sotto_fermo);
		personaggio_sopra_fermo = Risorse.sink_sopra_fermo; 		personaggioSinistraFermo = new Animazione(300, Risorse.sink_sinistra_fermo);
		personaggioDestraFermo = new Animazione(300, Risorse.sink_destra_fermo);
		attraversabile = false;
	}

	/**
	 * Aggiorna le dinamiche del giocatore (vita/tempo,GestioneInput,etc.)
	 */
	@Override
	public void aggiorna() {
		super.aggiorna();
		//gestione pausa
		if(!h.getGioco().getPausa()){

			//se si prende una caramella e il tempo e' > 95 supera i 100 secondi.
			if(tempo > maxTempo){
				tempo = maxTempo;
			}

			if(tempo <= 0){
				h.aggiornaStat(Handler.Statistiche.SCONFITTE);
				sconfitta = true;
			}

			//Movimento
			getInput();
			muovi();

			h.getCameraGioco().centra(this);
			ora = System.nanoTime();
			delta +=(ora - ultimoTempo) / tempoDiAggiornamento;
			timer += ora - ultimoTempo;
			ultimoTempo = ora;

			if(timer > TimeUnit.SECONDS.toNanos(1)){
				tempo--;
				timer = 0;
			}
		}
		else{
			ultimoTempo = System.nanoTime();
		}

	}

	private void getInput() {
		dx = 0;
		dy = 0;

		if(h.getGestioneInput().up)
			dy = -velocita;
		if(h.getGestioneInput().down)
			dy = velocita;
		if(h.getGestioneInput().left)
			dx = -velocita;
		if(h.getGestioneInput().right)
			dx = velocita;
	}

	@Override
	public void disegna(Graphics g) {

		//Rettangolo collisioni


		g.drawImage(getFrameAnimazioneCorrente(), (int) (x - h.getCameraGioco().getxOffset()), 
				(int) (y - h.getCameraGioco().getyOffset()), larghezza, altezza, null);

		//Disegna la barra del tempo.
		g.setColor(Color.black);
		g.setFont(new Font ("Arial", Font.BOLD,15));
		g.drawString(tempo+"", 180, 52);
		g.fillRect(68, 38, 104 , 14);
		g.setColor(Color.red);
		g.fillRect(70, 40, 100 , 10);
		g.setColor(Color.green);
		if(tempo > maxTempo)
			g.fillRect(70, 40, 100, 10);
		else
			g.fillRect(70, 40, (int)((float)tempo/(float)maxTempo*100), 10);

		/*g.setColor(Color.RED);
		g.fillRect((int)(x + bounds.x - h.getCameraGioco().getxOffset()), //+5
  				(int)(y + bounds.y - h.getCameraGioco().getyOffset()),
				bounds.width, bounds.height); //-15*/
	}

	/**
	 * muove il personaggio
	 * controllando che ci sia un nemico o meno
	 */ 
	@Override
	public void muovi(){ 
		muoviX = false; 
		muoviY = false; 
		Entita temp; // entita solo temporanea 
		// se temp e' null significa che non c'e' nessuna collisione 
		temp = controllaCollisioni(dx, 0f);
		if(temp == null)
			muoviX();
		else{ 
			ultimaEntita = temp;
			if(temp.eAttraversabile()) 
				muoviX();
			else if(temp instanceof Nemico){
				Nemico n = (Nemico) temp;
				Entita temp1 = n.controllaCollisioni(dx, 0f);
				if(temp1 != null && !(temp1.equals(this))){
					if(getTempo() - 1 > 0){
						setTempo(getTempo()-1);
						h.aggiornaStat(Handler.Statistiche.VITA_SOTTRATTA);
					}
				}
			}
		}
		temp = controllaCollisioni(0f, dy); 
		if(temp == null) 
			muoviY(); 
		else{ 
			ultimaEntita = temp; 
			System.out.println(temp.getClass().getName()); 
			if(temp.eAttraversabile()) 
				muoviY(); 
			else if(temp instanceof Nemico){ 
				Nemico n = (Nemico) temp;
				Entita temp1 = n.controllaCollisioni(0f, dy);
				if(temp1 != null && !(temp1.equals(this))){
					if(getTempo() - 1 > 0){
						setTempo(getTempo()-1);
						h.aggiornaStat(Handler.Statistiche.VITA_SOTTRATTA);
					}
				}
			} 
		}
	} 

	public int getTempo() {
		return tempo;
	}

	public void setTempo(int tempo) {
		this.tempo = tempo;
	}

	public boolean getSconfitta(){
		return sconfitta;
	}

	/**
	 * Carica l'oggetto Sink da file.
	 */
	@Override
	public void readExternal(ObjectInput in) throws IOException,
	ClassNotFoundException {
		super.readExternal(in);
		tempo=in.readInt(); 
		maxTempo = in.readInt();

		bounds.x = X; // = 8
		bounds.y = Y;
		bounds.width = LARGHEZZA; //= 44
		bounds.height = ALTEZZA; // = 32

		//Animazioni
		personaggioSotto = new Animazione(30, Risorse.sink_sotto);
		personaggioSopra = new Animazione(30, Risorse.sink_sopra);
		personaggioSinistra = new Animazione(30, Risorse.sink_sinistra);
		personaggioDestra = new Animazione(30, Risorse.sink_destra);

		//AnimazioniFermo
		personaggioSottoFermo = new Animazione(300, Risorse.sink_sotto_fermo);
		personaggio_sopra_fermo = Risorse.sink_sopra_fermo; 		personaggioSinistraFermo = new Animazione(300, Risorse.sink_sinistra_fermo);
		personaggioDestraFermo = new Animazione(300, Risorse.sink_destra_fermo);
		attraversabile = false;
	}

	/**
	 * Salva l'oggetto Sink su file.
	 */
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		super.writeExternal(out);
		out.writeInt(tempo);
		out.writeInt(maxTempo);
	}
}
