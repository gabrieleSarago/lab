package gfx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;
/**
 * Crea l'oggetto suono del gioco.
 */
public class Suono extends JFrame{
	
	private static final long serialVersionUID = 1L;

	public enum suoni{GIOCO, INFO, CARAMELLA,INTERRUTTORE_APERTO, INTERRUTTORE_CHIUSO,SCORRI,CONFERMA, MENU};
	//A ogni clip è associata una riproduzione, se la clip viene sovrascritta la riproduzione
	//precedente continua e non può essere fermata.
	private Clip clip_StatoGioco, clip_StatoMenu, clip_StatoInfo , clip;
	private AudioInputStream audioCaramella,audioScorri,audioConferma, audioInfo;
	private AudioInputStream audioInterruttore1, audioInterruttore2, audioGioco, audioMenu;
	
	private boolean muto;
	private String linea;
	
	private File f = new File(Risorse.PATH+"\\suono.txt");
	
	/**
	 * Costruisce il suono del gioco
	 */
	public Suono() {
		try {
			clip_StatoMenu = AudioSystem.getClip();
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Riproduce l'audio del suono specifico.
	 * @param s il suono da riprodurre.
	 */
	public void riproduci(suoni s){
		if(!muto){
		try {
			clip = AudioSystem.getClip();
			
		switch(s){
		case GIOCO:{
			clip_StatoGioco =  AudioSystem.getClip();
			File gioco =  new File("res/suoni/game_sound.wav");
			audioGioco = AudioSystem.getAudioInputStream(gioco);
			clip_StatoGioco.open(audioGioco);
			clip_StatoGioco.loop(Clip.LOOP_CONTINUOUSLY);
			break;
		}
		case INFO:
			clip_StatoInfo =  AudioSystem.getClip();
			File info =  new File("res/suoni/info.wav");
			audioInfo = AudioSystem.getAudioInputStream(info);
			clip_StatoInfo.open(audioInfo);
			clip_StatoInfo.loop(Clip.LOOP_CONTINUOUSLY);
			break;
		case CARAMELLA:{
			File caramella = new File("res/suoni/LOZ_Get_Item.wav");
			audioCaramella = AudioSystem.getAudioInputStream(caramella);
			clip.open(audioCaramella);
			break;
		}
		case INTERRUTTORE_APERTO:{
			File interruttore_aperto =  new File("res/suoni/LOZ_Get_Heart.wav");
			audioInterruttore1 = AudioSystem.getAudioInputStream(interruttore_aperto);
			clip.open(audioInterruttore1);
			break;
		}
		case INTERRUTTORE_CHIUSO:{
			File interruttore_chiuso = new File("res/suoni/LOZ_Arrow.wav");
			audioInterruttore2 = AudioSystem.getAudioInputStream(interruttore_chiuso);
			clip.open(audioInterruttore2);
			break;
		}
		case SCORRI:{
			File scorri=new File("res/suoni/LOZ_LowHealth.wav");
			audioScorri = AudioSystem.getAudioInputStream(scorri);
			clip.open(audioScorri);
			break;
		}
		case CONFERMA:{
			File conferma=new File("res/suoni/LOZ_Get_Heart.wav");
			audioConferma= AudioSystem.getAudioInputStream(conferma);
			clip.open(audioConferma);
			break;
		}
		case MENU:{
			 clip_StatoMenu =  AudioSystem.getClip();
			 File menu = new File("res/suoni/menu_intro.wav");
			 audioMenu = AudioSystem.getAudioInputStream(menu);
			 clip_StatoMenu.open(audioMenu);
			 clip_StatoMenu.loop(Clip.LOOP_CONTINUOUSLY);
			 break;
		}
		}
		} catch (Exception e) {e.printStackTrace();}
		//Se clip è già attivo lo blocca e ne libera le risorse per poterlo riusare.
		if(clip.isRunning()){
			clip.stop();
			clip.close();
		}
		
		clip.start();
		}
	}
	
	/**
	 * Carica il suono da file.
	 */
	public void carica(){
		try{
			if(!f.exists()){
				f.createNewFile();
			}
			BufferedReader br = new BufferedReader(new FileReader(Risorse.PATH+"\\suono.txt"));
			linea = br.readLine();
			br.close();
		}catch(IOException e){
		}
		if(linea == null){
			linea = "ON";
		}
		switch(linea){
		case "OFF": setMuto(true); break;
		case "ON": setMuto(false); break;
		default : setMuto(false); break;
		}
	}
	
	/**
	 * Ferma il suono.
	 * @param muto booleano che identifica lo stato di muto.
	 */
	public void setMuto(boolean muto){
		this.muto = muto;
		
		try{
			PrintWriter pw = new PrintWriter(new FileWriter(Risorse.PATH+"\\suono.txt"));
			if(muto){
				pw.write("OFF");
			}
			else{
				pw.write("ON");
			}
			pw.close();
		}catch(IOException e){}
		
		if(muto){
			clip_StatoMenu.stop();
			clip.stop();
		}
		else{
			clip_StatoMenu.start();
			clip.start();
		}
	}
	
	public boolean getMuto(){
		return muto;
	}
	
	public void ferma(){
		clip.stop();
		clip.close();
	}
	
	public Clip getClipGioco(){
		return clip_StatoGioco;
	}
	
	public Clip getClipStatoMenu(){
		 return clip_StatoMenu;
	}
	
	public Clip getClipStatoInfo(){
		return clip_StatoInfo;
	}
	
}
