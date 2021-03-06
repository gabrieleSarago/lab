package suoni;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;

import grafica.Risorse;
/**
 * Crea l'oggetto suono del gioco.
 */
public class Suono extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	/**
	* A ogni clip � associata una riproduzione, se la clip viene sovrascritta la riproduzione
	* precedente continua e non pu� essere fermata.
	*/
	
	public enum suoni{GIOCO, INFO, CARAMELLA,INTERRUTTORE_APERTO, INTERRUTTORE_CHIUSO,SCORRI,CONFERMA, MENU};
	private Clip clip_StatoGioco, clip_StatoMenu, clip_StatoInfo , clip;
	private AudioInputStream audioCaramella,audioScorri,audioConferma, audioInfo;
	private AudioInputStream audioInterruttore1, audioInterruttore2, audioGioco, audioMenu;
	
	private boolean muto;
	private String linea;
	
	private File f = new File(Risorse.SUONO);
	
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
		InputStream file;
		try {
			clip = AudioSystem.getClip();
			
		switch(s){
		case GIOCO:{
			clip_StatoGioco =  AudioSystem.getClip();
			file = Suono.class.getResourceAsStream("/res/suoni/game_sound.wav");
			BufferedInputStream gioco = new BufferedInputStream(file);
			//File gioco =  new File("res/suoni/game_sound.wav");
			audioGioco = AudioSystem.getAudioInputStream(gioco);
			clip_StatoGioco.open(audioGioco);
			clip_StatoGioco.loop(Clip.LOOP_CONTINUOUSLY);
			break;
		}
		case INFO:
			clip_StatoInfo =  AudioSystem.getClip();
			//File info =  new File("res/suoni/info.wav");
			file = Suono.class.getResourceAsStream("/res/suoni/info.wav");
			BufferedInputStream info = new BufferedInputStream(file);
			audioInfo = AudioSystem.getAudioInputStream(info);
			clip_StatoInfo.open(audioInfo);
			clip_StatoInfo.loop(Clip.LOOP_CONTINUOUSLY);
			break;
		case CARAMELLA:{
			file = Suono.class.getResourceAsStream("/res/suoni/LOZ_Get_Item.wav");
			BufferedInputStream caramella = new BufferedInputStream(file);
			//File caramella = new File("res/suoni/LOZ_Get_Item.wav");
			audioCaramella = AudioSystem.getAudioInputStream(caramella);
			clip.open(audioCaramella);
			break;
		}
		case INTERRUTTORE_APERTO:{
			file = Suono.class.getResourceAsStream("/res/suoni/LOZ_Get_Heart.wav");
			BufferedInputStream interruttore_aperto = new BufferedInputStream(file);
			//File interruttore_aperto =  new File("res/suoni/LOZ_Get_Heart.wav");
			audioInterruttore1 = AudioSystem.getAudioInputStream(interruttore_aperto);
			clip.open(audioInterruttore1);
			break;
		}
		case INTERRUTTORE_CHIUSO:{
			file = Suono.class.getResourceAsStream("/res/suoni/LOZ_Arrow.wav");
			BufferedInputStream interruttore_chiuso = new BufferedInputStream(file);
			//File interruttore_chiuso = new File("res/suoni/LOZ_Arrow.wav");
			audioInterruttore2 = AudioSystem.getAudioInputStream(interruttore_chiuso);
			clip.open(audioInterruttore2);
			break;
		}
		case SCORRI:{
			file = Suono.class.getResourceAsStream("/res/suoni/LOZ_LowHealth.wav");
			BufferedInputStream scorri = new BufferedInputStream(file);
			//File scorri=new File("res/suoni/LOZ_LowHealth.wav");
			audioScorri = AudioSystem.getAudioInputStream(scorri);
			clip.open(audioScorri);
			break;
		}
		case CONFERMA:{
			file = Suono.class.getResourceAsStream("/res/suoni/LOZ_Get_Heart.wav");
			BufferedInputStream conferma = new BufferedInputStream(file);
			//File conferma=new File("res/suoni/LOZ_Get_Heart.wav");
			audioConferma= AudioSystem.getAudioInputStream(conferma);
			clip.open(audioConferma);
			break;
		}
		case MENU:{
			 clip_StatoMenu =  AudioSystem.getClip();
			 file = Suono.class.getResourceAsStream("/res/suoni/menu_intro.wav");
			 BufferedInputStream menu = new BufferedInputStream(file);
			 //File menu = new File("res/suoni/menu_intro.wav");
			 audioMenu = AudioSystem.getAudioInputStream(menu);
			 clip_StatoMenu.open(audioMenu);
			 clip_StatoMenu.loop(Clip.LOOP_CONTINUOUSLY);
			 break;
		}
		}
		} catch (Exception e) {e.printStackTrace();}
		//Se clip � gi� attivo lo blocca e ne libera le risorse per poterlo riusare.
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
			BufferedReader br = new BufferedReader(new FileReader(Risorse.SUONO));
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
			PrintWriter pw = new PrintWriter(new FileWriter(Risorse.SUONO));
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
