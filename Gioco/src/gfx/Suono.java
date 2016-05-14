package gfx;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;

public class Suono extends JFrame{
	
	private static final long serialVersionUID = 1L;

	public enum suoni{GIOCO,CARAMELLA,INTERRUTTORE_APERTO, INTERRUTTORE_CHIUSO,SCORRI,CONFERMA, MENU};
	//A ogni clip è associata una riproduzione, se la clip viene sovrascritta la riproduzione
	//precedente continua e non può essere fermata.
	private Clip clip_StatoGioco, clip_StatoMenu, clip;
	private AudioInputStream audioCaramella,audioScorri,audioConferma;
	private AudioInputStream audioInterruttore1, audioInterruttore2, audioGioco, audioMenu;
	
	private boolean muto;
	
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
	
	public void setMuto(boolean muto){
		this.muto = muto;
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
	
}
