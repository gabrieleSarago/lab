package livelli;

import java.awt.Graphics;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.StringTokenizer;

import entita.Caramella;
import entita.Entita;
import entita.Funzionalita;
import entita.Funzione;
import entita.Interruttore;
import entita.InterruttoreInvisibile;
import entita.InterruttorePressione;
import entita.Nemico;
import entita.Personaggio;
import entita.Sbarra;
import entita.Sink;
import entita.Teletrasporto;
import entita.Trofeo;
import gioco.Handler;
import grafica.Risorse;
import grafica.Tile;
import stati.StatoGioco;
import strumenti.Utils;
import suoni.Suono;

/**
 * Crea l'oggetto Livello.
 * Vengono salvati mediante dei numeri id le entita' da inserire.
 * Il livello viene caricato da un file di interi.
 */

public class Livello{
	
	private Handler h;
	private int larghezza, altezza;
	private int [][] tiles;
	private ArrayList<Entita> array_entita;
	
	private Comparator<Entita> ordineDisegno = new Comparator<Entita>(){
		@Override
		public int compare(Entita a, Entita b) {
			if(a.getY()+ a.getAltezza() <= b.getY()+b.getAltezza()) return -1;
			return 1;
		}
		
	};
	
	
	
	private Entita ultimaCollisione_sink;
	
	
	/**
	 * Costruisce l'oggetto livello.
	 * @param h oggetto handler utile per la gestione con le altre classi.
	 * @param path nome del file da cui viene caricato il livello.
	 */
	public Livello (Handler h, String path){
		this.h = h;
		carica(path);
		/*
		per Sostituire i file:
		1) commenta carica(path) e caccia i commenti dal codice seguente
		2) assicurati che il percorso in salva e' corretto
		3) cambia il metodo salva in modo che in
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(pathLivello));
			pathLivello deve corrispondere a "C:\\Users\\Angelo\\workspace\\Gioco3\\bin\\res\\livelli\\livello1.txt1"
		4) avvia il gioco e fai nuova partita ed esci
		5) commenta di nuovo questo codice e caccia i commenti da carica
		6) NOTA BENE: ricorda di cambiare il file livello1.entita anche in resources/res/livelli
			altrimenti in eclipse ti funziona e quando fai ill jar no
		array_entita = new ArrayList<>();
		array_entita.add(new Sink(h, 75, 75, 100));
		caricaEntita();
		salva("C:\\Users\\Angelo\\workspace\\Gioco3\\bin\\res\\livelli\\livello1.entita");*/
	}
	
	/**
	 * Aggiorna le dinamiche del livello.
	 */
	public void aggiorna(){	
		
		for(int i=0; i < array_entita.size(); i++){
			Entita e = array_entita.get(i);
			e.aggiorna();
			if(e instanceof Personaggio){
				gestisciCollisioni((Personaggio)e);
			}
		}
	}
	
	public void disegna (Graphics g){
		
		int xStart = (int) Math.max(0, h.getCameraGioco().getxOffset() / Tile.TILE_LARGHEZZA);
		int xEnd = (int) Math.min(larghezza, (h.getCameraGioco().getxOffset() + h.getLarghezza())/Tile.TILE_LARGHEZZA +1);
		int yStart = (int) Math.max(0, h.getCameraGioco().getyOffset() / Tile.TILE_ALTEZZA);
		int yEnd = (int) Math.min(altezza, (h.getCameraGioco().getyOffset() + h.getLarghezza())/Tile.TILE_ALTEZZA +1);
				
		for (int y =  yStart; y < yEnd; y++)
			for(int x = xStart; x < xEnd ; x++){
				getTile(x,y).disegna(g, (int) (x * Tile.TILE_LARGHEZZA - h.getCameraGioco().getxOffset()),
						(int) (y * Tile.TILE_ALTEZZA - h.getCameraGioco().getyOffset()));
			}
		
		array_entita.sort(ordineDisegno);
		for(Entita e : array_entita){
			e.disegna(g);
		}
	}
	
	public Tile getTile(int x, int y){
		if(x < 0 || y < 0 || x >= larghezza || y >= altezza)
			return Tile.strada;
		Tile t = Tile.tiles[tiles[x][y]];
		if(t==null)
			return Tile.muro;
		return t;
	}
	
	/**
	 * Carica il livello da file.
	 * per far capire che si intende caricare un livello precedentemente salvato
	 * bisogna mettere _S alla fine del nome. Il nome deve essere SENZA estensione
	 * @param livello il nome del file.
	 */
	
	private void carica(String livello){
		StringTokenizer st = new StringTokenizer(livello, "_");
		if(st.countTokens() > 2 || st.countTokens() <=0) throw new IllegalArgumentException("stringa livello mal formata");
		String pathLivelloEntita = null;
		String pathLivelloTile = null;
		array_entita = new  ArrayList<Entita>();
		array_entita.clear(); //per sicurezza
		ObjectInputStream in;
		try{
			if(st.countTokens() == 1){
				pathLivelloTile = Risorse.PATH_INTERNO_LIVELLI + "/" + livello + ".txt";
				pathLivelloEntita = "/"+Risorse.PATH_INTERNO_LIVELLI + "/" + livello + ".entita"; 
				InputStream is = Livello.class.getResourceAsStream(pathLivelloEntita);
				in = new ObjectInputStream(is);
			}
			else{
				String nextToken = st.nextToken();
				pathLivelloTile = Risorse.PATH_INTERNO_LIVELLI + "/" + nextToken + ".txt";
				pathLivelloEntita = Risorse.DIR_UTENTE + Risorse.SEPARATORE + nextToken + ".salvataggio";
				in = new ObjectInputStream(new FileInputStream(pathLivelloEntita));
			}
			supportoCarica(in);
		}catch(IOException e){
			e.printStackTrace();
		}
		//carica i Tile cioe' il prato e i muri
		//String file = Utils.caricaFileComeString("res/livelli/livello1.txt");
		String file = Utils.caricaFileComeString(pathLivelloTile);
		String[] tokens = file.split("\\s+");
		larghezza = Utils.parseInt(tokens[0]);
		altezza = Utils.parseInt(tokens[1]);
		int c = 3;
		tiles = new int[larghezza][altezza];
		for (int y = 0; y < altezza; y++)
			for (int x = 0; x < larghezza; x++)
				tiles[x][y] = Utils.parseInt(tokens[(x + (y * larghezza)) + c]);
	}
	/**
	 * si occupa di caricare le entita' e impostargli l' handler
	 * @param in
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void supportoCarica(ObjectInputStream in) throws FileNotFoundException, IOException{
		Entita en = null;
		for(;;){
			try {	
				en = (Entita) in.readObject();
				en.setHandler(h);
				array_entita.add(en);
			}catch (ClassNotFoundException e1){
				e1.printStackTrace();
				in.close();
				throw new IOException();
			}catch(ClassCastException e2){
				e2.printStackTrace();
				in.close();
				throw new IOException();
			}catch(EOFException e3){break;}
		}//fine for	
		in.close();
}

	/**
	 * Salva le dinamiche correnti del livello su un file.
	 * Utile quando si vuole salvare una partita.
	 * @param livello nome del livello CON estensione su cui salvare i progressi.
	 */
	public void salva(String livello){
		
		String pathLivello = Risorse.DIR_UTENTE + Risorse.SEPARATORE + livello;
		File f = new File(pathLivello);
		f.delete();

		try{
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(pathLivello));
			for(Entita temp: array_entita){
				oos.writeObject(temp);
			}
			oos.close();
			}catch(IOException e){e.printStackTrace();}
	}
	
	public int getLarghezza(){
		return larghezza;
	}
	
	public int getAltezza(){
		return altezza;
	}
	
	/**
	 * restituisce l' oggetto Sink
	 * @return Sink
	 */
	public Sink getSink()
	{
		for(int i=0; i < array_entita.size(); i++){
			if(array_entita.get(i) instanceof Sink)
				return ((Sink)array_entita.get(i));
		}
		System.out.println("sink non trovato");
		return null;
	}
	
	public void gestisciCollisioni(Personaggio p){
		if(p instanceof Sink)
			if(p.getUltimaEntita() == null) {
			ultimaCollisione_sink = null;
			return;
		} else	collisioniConSink((Sink) p);	
		p.setUltimaEntita(null);
	}
	
	private void collisioniConSink(Sink s){
		if(s.getUltimaEntita() instanceof Caramella){
			s.setTempo(s.getTempo()+5);
			array_entita.remove(s.getUltimaEntita());
			h.getSuono().riproduci(Suono.suoni.CARAMELLA);
			h.aggiornaStat(Handler.Statistiche.CARAMELLE);
		}
		if(s.getUltimaEntita() instanceof Trofeo) {
			h.aggiornaStat(Handler.Statistiche.LIVELLI_COMPLETATI);
			//TODO modificare quando si aggiungono più livelli
			h.aggiornaStat(Handler.Statistiche.END_GAMES);
			((StatoGioco)h.getGioco().getStato()).setVittoria(true);
		}
		
		if(s.getUltimaEntita() instanceof Teletrasporto && ((Teletrasporto)s.getUltimaEntita()).eAttivo()){
			s.setX(((Teletrasporto)s.getUltimaEntita()).getDestinazioneX());
			s.setY(((Teletrasporto)s.getUltimaEntita()).getDestinazioneY());
			h.aggiornaStat(Handler.Statistiche.TELETRASPORTI);
		}
		
		/*if(s.getUltimaEntita() instanceof Nemico && ((Nemico)s.getUltimaEntita()).eVivo()){
			if(s.getTempo() - 1 > 0){
				s.setTempo(s.getTempo()-1);
				h.aggiornaStat(Handler.Statistiche.VITA_SOTTRATTA);
			}
		}*/
		
		if(s.getUltimaEntita() instanceof Interruttore){
			if(!(ultimaCollisione_sink instanceof Interruttore) || 
						!(ultimaCollisione_sink.equals(s.getUltimaEntita()))){
					((Interruttore)s.getUltimaEntita()).funzione(h);
					h.aggiornaStat(Handler.Statistiche.NUM_INTERRUTORI);
			}
		}
	
		ultimaCollisione_sink = s.getUltimaEntita();
	}
	
	public ArrayList<Entita> getArray_entita(){
		return array_entita;
	}
	
	@SuppressWarnings("unused")
	private void caricaEntita(){
		// primo quadrante
		array_entita.add(new Sbarra(h, Tile.TILE_LARGHEZZA*6, Tile.TILE_ALTEZZA*4, false)); //false
		array_entita.add(new Sbarra(h, Tile.TILE_LARGHEZZA*13, Tile.TILE_ALTEZZA*3, true));
		array_entita.add(new Sbarra(h, Tile.TILE_LARGHEZZA*15, Tile.TILE_ALTEZZA*3, true));
		array_entita.add(new Sbarra(h, Tile.TILE_LARGHEZZA*2, Tile.TILE_ALTEZZA*4, false));//false
		array_entita.add(new InterruttorePressione(h, Tile.TILE_LARGHEZZA*10, Tile.TILE_ALTEZZA*2, 
				new Funzione(array_entita, 1, Funzionalita.CAMBIA_SBARRA),
				new Funzione(array_entita, 3, Funzionalita.CAMBIA_SBARRA)
				));
		array_entita.add(new InterruttorePressione(h, Tile.TILE_LARGHEZZA*2, Tile.TILE_ALTEZZA*6, 
				new Funzione(array_entita, 2, Funzionalita.CAMBIA_SBARRA),
				new Funzione(array_entita, 4, Funzionalita.CAMBIA_SBARRA)
		));
		
		// secondo quadrante
		array_entita.add(new Sbarra(h, Tile.TILE_LARGHEZZA*18, Tile.TILE_ALTEZZA*18, false)); //7 false
		array_entita.add(new Sbarra(h, Tile.TILE_LARGHEZZA*20, Tile.TILE_ALTEZZA*18, false));// false
		array_entita.add(new Sbarra(h, Tile.TILE_LARGHEZZA*22, Tile.TILE_ALTEZZA*18, false));// false
		array_entita.add(new Sbarra(h, Tile.TILE_LARGHEZZA*24, Tile.TILE_ALTEZZA*18, false));// false
		array_entita.add(new Teletrasporto(h, Tile.TILE_LARGHEZZA*26, Tile.TILE_ALTEZZA*2,
				Tile.TILE_LARGHEZZA*26, Tile.TILE_ALTEZZA*24, false));//false
		array_entita.add(new InterruttorePressione(h, Tile.TILE_LARGHEZZA*21, Tile.TILE_ALTEZZA*4, 
				new Funzione(array_entita, 10, Funzionalita.APRI_SBARRA),
				new Funzione(array_entita, 11, Funzionalita.ATTIVA_TELETRASPORTO)
				));
		array_entita.add(new InterruttorePressione(h, Tile.TILE_LARGHEZZA*24, Tile.TILE_ALTEZZA*10, 
				new Funzione(array_entita, 9, Funzionalita.CAMBIA_SBARRA)
		));
		
		array_entita.add(new Nemico(h, 15*Tile.TILE_LARGHEZZA, 22*Tile.TILE_ALTEZZA, false)); //14
		array_entita.add(new Nemico(h, 25*Tile.TILE_LARGHEZZA, 24*Tile.TILE_ALTEZZA, false));
		array_entita.add(new Nemico(h, 15*Tile.TILE_LARGHEZZA, 27*Tile.TILE_ALTEZZA, false));//16
		array_entita.add(new InterruttoreInvisibile(h, Tile.TILE_LARGHEZZA*18, Tile.TILE_ALTEZZA*21,
				Tile.TILE_LARGHEZZA*2, Tile.TILE_ALTEZZA*8,
				new Funzione(array_entita, 14, Funzionalita.ATTIVA_NEMICO),
				new Funzione(array_entita, 15, Funzionalita.ATTIVA_NEMICO),
				new Funzione(array_entita, 16, Funzionalita.ATTIVA_NEMICO)
				));
		array_entita.add(new Teletrasporto(h, Tile.TILE_LARGHEZZA*12, Tile.TILE_ALTEZZA*25, //18
				Tile.TILE_LARGHEZZA*11, Tile.TILE_ALTEZZA*15, false));
		array_entita.add(new InterruttorePressione(h, Tile.TILE_LARGHEZZA*13, Tile.TILE_ALTEZZA*23, 
				new Funzione(array_entita, 18, Funzionalita.ATTIVA_TELETRASPORTO)
		));
		
		array_entita.add(new Sbarra(h, Tile.TILE_LARGHEZZA*26, Tile.TILE_ALTEZZA*15, false)); // false
		array_entita.add(new InterruttorePressione(h, Tile.TILE_LARGHEZZA*17, Tile.TILE_ALTEZZA*15, 
				new Funzione(array_entita, 20, Funzionalita.APRI_SBARRA),
				new Funzione(array_entita, 7, Funzionalita.APRI_SBARRA),
				new Funzione(array_entita, 8, Funzionalita.APRI_SBARRA)
		));
		//quadrante 3
		array_entita.add(new Sbarra(h, Tile.TILE_LARGHEZZA*8, Tile.TILE_ALTEZZA*22, false)); //-1 22
		array_entita.add(new Sbarra(h, Tile.TILE_LARGHEZZA*6, Tile.TILE_ALTEZZA*20, true)); //-2
		array_entita.add(new Sbarra(h, Tile.TILE_LARGHEZZA*8, Tile.TILE_ALTEZZA*28, false));//-3
		array_entita.add(new Sbarra(h, Tile.TILE_LARGHEZZA, Tile.TILE_ALTEZZA*11, false)); //-4
		array_entita.add(new Sbarra(h, Tile.TILE_LARGHEZZA*8, Tile.TILE_ALTEZZA*10, true)); //-5
		
		array_entita.add(new Teletrasporto(h, Tile.TILE_LARGHEZZA*3, Tile.TILE_ALTEZZA*16,
				Tile.TILE_LARGHEZZA*11, Tile.TILE_ALTEZZA*9, false));
		array_entita.add(new InterruttorePressione(h, Tile.TILE_LARGHEZZA*2, Tile.TILE_ALTEZZA*20, 
				new Funzione(array_entita, 27, Funzionalita.CAMBIA_TELETRASPORTO),
				new Funzione(array_entita, 25, Funzionalita.CHIUDI_SBARRA)
		));//-1
		array_entita.add(new InterruttorePressione(h, Tile.TILE_LARGHEZZA*5, Tile.TILE_ALTEZZA*23, 
				new Funzione(array_entita, 25, Funzionalita.CAMBIA_SBARRA)
		));//-2
		array_entita.add(new InterruttorePressione(h, Tile.TILE_LARGHEZZA*2, Tile.TILE_ALTEZZA*28, 
				new Funzione(array_entita, 22, Funzionalita.CAMBIA_SBARRA),
				new Funzione(array_entita, 23, Funzionalita.CAMBIA_SBARRA)
		));//-3
		
		array_entita.add(new InterruttorePressione(h, Tile.TILE_LARGHEZZA*10, Tile.TILE_ALTEZZA*19, true, 
				new Funzione(array_entita, 24, Funzionalita.APRI_SBARRA),
				new Funzione(array_entita, 26, Funzionalita.CHIUDI_SBARRA)
		));
		//trofeo e caramelle
		array_entita.add(new Trofeo(h, Tile.TILE_LARGHEZZA*16, Tile.TILE_ALTEZZA*9)); 
		array_entita.add(new Caramella(h, Tile.TILE_LARGHEZZA*7, Tile.TILE_ALTEZZA*7));
		array_entita.add(new Caramella(h, Tile.TILE_LARGHEZZA*8, Tile.TILE_ALTEZZA*7));
		array_entita.add(new Caramella(h, Tile.TILE_LARGHEZZA*7, Tile.TILE_ALTEZZA*8));
		array_entita.add(new Caramella(h, Tile.TILE_LARGHEZZA*8, Tile.TILE_ALTEZZA*8));
		array_entita.add(new Caramella(h, Tile.TILE_LARGHEZZA*7, Tile.TILE_ALTEZZA*9));
		array_entita.add(new Caramella(h, Tile.TILE_LARGHEZZA*8, Tile.TILE_ALTEZZA*9));
		array_entita.add(new Caramella(h, Tile.TILE_LARGHEZZA*14, Tile.TILE_ALTEZZA));
		array_entita.add(new Caramella(h, Tile.TILE_LARGHEZZA*21, Tile.TILE_ALTEZZA));
		array_entita.add(new Caramella(h, Tile.TILE_LARGHEZZA*24, Tile.TILE_ALTEZZA*8));
		array_entita.add(new Caramella(h, Tile.TILE_LARGHEZZA*23, Tile.TILE_ALTEZZA*19));
		array_entita.add(new Caramella(h, Tile.TILE_LARGHEZZA*21, Tile.TILE_ALTEZZA*17));
		array_entita.add(new Caramella(h, Tile.TILE_LARGHEZZA*19, Tile.TILE_ALTEZZA*19));
		array_entita.add(new Caramella(h, Tile.TILE_LARGHEZZA*28, Tile.TILE_ALTEZZA*21));
		array_entita.add(new Caramella(h, Tile.TILE_LARGHEZZA*14, Tile.TILE_ALTEZZA*21));
		array_entita.add(new Caramella(h, Tile.TILE_LARGHEZZA*14, Tile.TILE_ALTEZZA*28));
		array_entita.add(new Caramella(h, Tile.TILE_LARGHEZZA*28, Tile.TILE_ALTEZZA*28));
		array_entita.add(new Caramella(h, Tile.TILE_LARGHEZZA*14, Tile.TILE_ALTEZZA*15));
		array_entita.add(new Caramella(h, Tile.TILE_LARGHEZZA*16, Tile.TILE_ALTEZZA*10));
		array_entita.add(new Caramella(h, Tile.TILE_LARGHEZZA*16, Tile.TILE_ALTEZZA*8));
		array_entita.add(new Caramella(h, Tile.TILE_LARGHEZZA*15, Tile.TILE_ALTEZZA*9));
		array_entita.add(new Caramella(h, Tile.TILE_LARGHEZZA*17, Tile.TILE_ALTEZZA*9));
	}
}
