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
import stati.Stato;
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
	private Stato s;
	private static int[] stat; //
	
	public static enum Statistiche{
		
		CARAMELLE("Caramelle"), TEMPO_TOTALE("Tempo Totale"), TEMPO_MENU("Tempo Menu"), TEMPO_GIOCO("Tempo Gioco"),
		NUM_INTERRUTORI("Interruttori Utilizzati"), LIVELLI_COMPLETATI("Livelli Completati"), END_GAMES("Partite Completate"), SCONFITTE("Sconfitte"),
		PARTITE_INIZIATE("Partite Iniziate"), TELETRASPORTI("Teletrasporti Utilizzati"), VITA_SOTTRATTA("Vita Sottratta Dai Nemici");
		
		private String nome;
		
		Statistiche(String nome){
			this.nome = nome;
		}
		
		public String toString(){
			return nome;
		}
		
	}
	
	public Handler(Gioco gioco){
		this.gioco = gioco;
		stat = new int[Statistiche.values().length];
		caricaStatistiche();
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
	
	public Stato getStato(){
		return s;
	}
	
	public void setStato(Stato s){
		this.s = s;
		gioco.setStato(s);
	}
	
	public void aggiornaStat(Statistiche statistica){
		int scelta= statistica.ordinal();
		stat[scelta]++;
		System.out.println(statistica.toString() + " " + stat[scelta]);
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
	
	private void caricaStatistiche(){
		File f = new File(Risorse.DIR_UTENTE);
		if(!f.exists()){
			f.mkdirs();
		}
		
		f = new File(Risorse.STATISTICA);
		try {
			if(!f.exists())
				f.createNewFile();
			
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
