package gioco;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import grafica.Risorse;
import livelli.Livello;
import strumenti.CameraGioco;
import strumenti.GestioneInput;
import strumenti.Lingua;
import suoni.Suono;

/**
 * Oggetto "manipolatore" del gioco.
 * Serve a gestire la comunicazione tra le diverse classi del progetto.
 */
public class Handler {
	
	private Gioco gioco;
	private Livello l;
	private static int[] stat; //
	
	public static enum Statistiche{
		
		CARAMELLE, TEMPO_TOTALE, TEMPO_MENU, TEMPO_GIOCO,
		NUM_INTERRUTORI, END_GAMES, SCONFITTE, VITTORIE,
		PARTITE_INIZIATE, TELETRASPORTI, VITA_SOTTRATTA;
		
		//Italiano
		static String [] ITA = {"Caramelle" , "Tempo Totale" , "Tempo Menu" , "Tempo Gioco" , "Interruttori", "Partite Completate",
				"Sconfitte" , "Vittorie" , "Partite Iniziate" , "Teletrasporti" , "Vita Sotratta"};
		
		static String [] DEU = {"Bonbons" , "Gesamtzeit" , "Menuezeit" , "Spielzeit" , "Schalter" , "Spiele Vervollstaendigt" , 
				"Niederlage" , "Siege" , "Begonnene Spiele"	 , "Teletransporte"	, "Abgezogenes Leben"};
		
		static String[] ENG = {"Candies" , "Total Time" , "Menu Time" , "Game Time" , "Switches" , "Completed Games" , "Lost" ,
				"Won", "Started Games" , "Teleports" , "Decreased Life" };
		
		public static String[] getNomi (String lingua){
			String[] stats;
			switch(lingua){
			case "ITALIANO" : stats  = ITA; break;
			case "DEUTSCH" : stats = DEU; break;
			default : stats = ENG; break;
			}
			return stats;
		}
	}
	
	public Handler(Gioco gioco){
		this.gioco = gioco;
		stat = new int[Statistiche.values().length];
	}
	
	public CameraGioco getCameraGioco(){
		return gioco.getCameraGioco();
	}
	
	public GestioneInput getGestioneInput(){
		return gioco.getGestioneInput();
	}
	
	public Suono getSuono(){
		return gioco.getSuono();
	}
	public int getLarghezza(){
		return gioco.getLarghezza();
	}
	
	public int getAltezza(){
		return gioco.getAltezza();
	}
	
	public Gioco getGioco() {
		return gioco;
	}
	
	public Lingua getLingua(){
		return gioco.getLingua();
	}

	public void setGioco(Gioco gioco) {
		this.gioco = gioco;
	}

	public Livello getLivello() {
		return l;
	}

	public void setLivello(Livello l) {
		this.l = l;
	}
	
	public void aggiornaStat(Statistiche statistica){
		int scelta= statistica.ordinal();
		stat[scelta]++;
	}
	
	public int [] getStatistiche(){
		return stat;
	}
	
	public void salvaStatistiche(){
		File f = new File(Risorse.STATISTICA);
		try{
			if(!f.exists())
				f.createNewFile();
			PrintWriter pw = new PrintWriter(new FileOutputStream(f));
			for(int i = 0; i < stat.length; i++){
				pw.println(stat[i]);
			}
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void caricaStatistiche(){		
		File f = new File(Risorse.STATISTICA);
		try {
			
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			String statisticaCorrente = br.readLine();
			int i = 0;
			while(statisticaCorrente != null){
				stat[i] = Integer.parseInt(statisticaCorrente);
				statisticaCorrente = br.readLine();
				i++;
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
