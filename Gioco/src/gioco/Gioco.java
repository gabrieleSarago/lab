package gioco;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import classifica.Classifica;
import finestra.Finestra;
import grafica.Risorse;
import stati.Stato;
import stati.StatoGioco;
import stati.StatoOpzioni;
import stati.StatoUtente;
import strumenti.CameraGioco;
import strumenti.CaricatoreImmagini;
import strumenti.GestioneInput;
import strumenti.Lingua;
import suoni.Suono;

/**
 * Crea l'oggetto Gioco.
 * Classe principale del progetto.
 */
public class Gioco{
	
	private Finestra f;
	private int larghezza,altezza;
	public String titolo;
	
	private Classifica c;
	
	//gestione stati
	//private boolean inCorso = false;
	private boolean inPausa = false;

	//private Thread thread;
	
	//private BufferStrategy bs;
	//private Graphics g;
	
	//lingua
	private Lingua lingua;
	private String linea;
	
	//Suonir
	private Suono suono;
	
	//Stati
	private static Stato stato;
	//Input
	private GestioneInput gi;
	 
	//Camera
	private CameraGioco cg;
	
	//Handler
	private Handler h;
	
	/**
	 * Costruttore di Gioco.
	 * @param titolo titolo della finestra del gioco.
	 * @param larghezza larghezza della finestra del gioco.
	 * @param altezza altezza della finestra del gioco.
	 */
	public Gioco(String titolo, int larghezza,int altezza){
		this.larghezza = larghezza;
		this.altezza = altezza;
		this.titolo = titolo;
		gi = new GestioneInput();
		
		inizializza();
		
		f.getFrame().setContentPane(new Pane());
		f.getFrame().setVisible(true);
	}
	
	/**
	 * Main del progetto.
	 */
	public static void main (String[]args){
		//Gioco gioco = new Gioco("The Labyrinth of Zelda",1200, 700);
		Dimension schermo = Toolkit.getDefaultToolkit().getScreenSize();
		int larghezza = (int) schermo.getWidth();
		int altezza = (int) schermo.getHeight();
		if(larghezza < 1200 || altezza < 700)
			JOptionPane.showMessageDialog(null, "La risoluzione dello schermo non supporta i requisiti minimi.");
		else{
			new Gioco("The Labyrinth of Zelda",1200, 700);
	    }
	}
	/**
	 * Inizializza le risorse del gameplay e dei menu di scorrimento.
	 */
	private void inizializza(){
		
		
		Risorse.inizializza();
		
		h = new Handler(this);
		f = new Finestra(titolo, larghezza, altezza, h);
		f.getFrame().addKeyListener(gi);
		f.getFrame().setIconImage(CaricatoreImmagini.caricaImmagine("res/img/sprite/icon_link.png"));
		
		suono = new Suono();
		
		lingua = new Lingua();
		linea = lingua.getLingua();
		
		if(linea == null){
			lingua.setLingua("ENGLISH");
			Risorse.inizializzaENG();
		}
		
		else{
			switch(linea){
			case "ITALIANO" : Risorse.inizializzaITA(); break;
			case "DEUTSCH" : Risorse.inizializzaDEU(); break;
			default : Risorse.inizializzaENG(); break;
			}
		}
		
		this.c = new Classifica();
		File f = new File(Risorse.CLASSIFICA);
		
		try {
			if(!f.exists()){
				f.createNewFile();
			}
			c.carica(Risorse.CLASSIFICA);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		cg = new CameraGioco(h, 0, 0);
		
		suono.carica();
		
		if(linea == null)
			stato = new StatoOpzioni(h, suono, true);
		else
			stato = new StatoUtente(h, suono);
		
		Stato.setStato(stato);

	}
	
	/**
	 * Modifica lo stato corrente.
	 * @param stati il nuovo stato da creare.
	 */
	public void setStato(Stato stati){
		stato = stati;
	}
	/**
	 * Ritorna lo stato corrente.
	 * @return lo stato corrente.
	 */
	public Stato getStato(){return stato;}
	
	/**
	 * Modifica lo stato pauusa.
	 * @param pausa un valore boolean.
	 */
	public void setPausa(boolean pausa){
		inPausa = pausa;
	}
	/**
	 * Ritorna true se si ci trova in pausa.
	 * @return boolean.
	 */
	public boolean getPausa(){return inPausa;}
	
	public Classifica getClassifica(){
		return c;
	}
	
	/**
	 * Aggiorna i listener e gli stati associati.
	 */
	private void aggiorna(){
				
		if ( Stato.getStato() != null){
			Stato.getStato().aggiorna();
			Stato.setStato(stato);
		}
	}
	
	/*private void disegna(){
		bs = f.getCanvas().getBufferStrategy();
		if(bs == null){
			f.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		//cancella il precedente
		g.clearRect(0, 0, larghezza, altezza);
		//inzia a disegnare qua!
		
		
		if(Stato.getStato() != null)
			Stato.getStato().disegna(g);
		
		// finisce a disegnare qua!
		bs.show();
		g.dispose()
	}*/
	/**
	 * L'oggetto gioco viene lanciato da un thread.
	 * Run lancia il thread della classe Gioco.
	 */
	/*public void run(){
		
		int fps = 60;
		double tempoDiAggiornamento = 1000000000 / fps;
		double tempoDiAggiornamentoMenu = 1000000000 / 55;
		double delta = 0;
		long ora;
		long ultimoTempo = System.nanoTime();
		double ultimoCiclo = 0;
		
		while(inCorso){
			ora = System.nanoTime();
			//frame dinamico
			if(h.getGioco().getStato() instanceof StatoGioco)
				ultimoCiclo = (ora - ultimoTempo) / tempoDiAggiornamento;
			else
				ultimoCiclo = (ora - ultimoTempo) / tempoDiAggiornamentoMenu;
			delta+=ultimoCiclo;
			ultimoTempo = ora;
			if(delta >= 1){
				aggiorna();
				//disegna();
				delta--;
				
				//Se l'errore accumulato si avvicina a 1 viene ribassato
				//alla quantitia' ultimoCiclo. Tutto cio' serve a evitare due
				//aggiornamenti consecutivi del gioco.
				
				if(delta+ultimoCiclo >= 1)
					 delta = ultimoCiclo;
				
			}
		}
		
		stop();
		
	}*/
	
	/**
	 * Metodo accessore necessario per la sincronizzazione dei listener.
	 * @return
	 */
	public GestioneInput getGestioneInput(){
		return gi;
	}
	
	//public Thread getThread(){return thread;}
	
	public CameraGioco getCameraGioco(){
		return cg;
	}
	
	public int getLarghezza() {
		return larghezza;
	}
	
	public int getAltezza() {
		return altezza;
	}

	/**
	 * Fa partire l'esecuzione della classe Gioco.
	 */
	/*public synchronized void start(){
		if(inCorso)
			return;
		inCorso = true;
		thread = new Thread(this);
		thread.start();
	}*/
	
	/**
	 * Ferma l'esecuzione della classe gioco.
	 *
	 */
	/*public synchronized void stop(){
		if(!inCorso)
			return;
		inCorso = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}*/	
	
	public JFrame getFrame(){
		return f.getFrame();
	}
	
	public Lingua getLingua(){return lingua;}
	
	public Suono getSuono(){
		return suono;
	}
	
	class Pane extends JPanel{
		
		private static final long serialVersionUID = 1L;
		
		long tempoCorrente = System.currentTimeMillis();

		public Pane(){
			//Game-Loop
			Timer timer = new Timer(15, new ActionListener() {
				long tempoFine;
				
				@Override
				public void actionPerformed(ActionEvent e){
					aggiorna();
					repaint();
					tempoFine = System.currentTimeMillis();
					if(!(stato instanceof StatoUtente)){
						if(tempoFine-tempoCorrente >= 1000){
							h.aggiornaStat(Handler.Statistiche.TEMPO_TOTALE);
							Stato attuale = Stato.getStato();
							if(attuale instanceof StatoGioco){
								h.aggiornaStat(Handler.Statistiche.TEMPO_GIOCO);
							}
							else 
								h.aggiornaStat(Handler.Statistiche.TEMPO_MENU);
							tempoCorrente = System.currentTimeMillis();
						}
					}
				}
			});
			
			timer.setRepeats(true);
			timer.setCoalesce(true);
			timer.start();
		}

		
		@Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.clearRect(0, 0, larghezza, altezza);
            if(Stato.getStato() != null)
    			Stato.getStato().disegna(g);
            g.dispose();
        }
	}
	
}
