package grafica;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URISyntaxException;
import strumenti.CaricatoreImmagini;

/**
 * Oggetto che identifica le risorse del gioco.
 */

public class Risorse {
	
	private static final int larghezza = 24, altezza = 32;
	
	public static BufferedImage sink_sopra_fermo, caramella, trofeo, ok, titoloPausa;
	public static BufferedImage strada, muro;
	public static BufferedImage [] voci_pausa, voci_pausa_off,
	voci_classifica,voci_classifica_off,voci_menu, voci_menu_off, voci_opzioni, voci_opzioni_off, voci_musica, lingue;// tempo;
	public static BufferedImage voce_utente, voce_utente_off;
	public static BufferedImage[] sink_sotto, sink_sopra, sink_sinistra, sink_destra;
	public static BufferedImage[] sink_sotto_fermo, sink_sinistra_fermo, sink_destra_fermo;
	public static BufferedImage porta_chiusa;
	public static BufferedImage[] porta_media_aperta, porta_media_chiusa;
	public static BufferedImage porta_aperta;
	public static BufferedImage interruttore_acceso_destra, interruttore_acceso_sinistra, interruttore_spento;
	public static BufferedImage teletrasporto_inattivo;
	public static BufferedImage[] teletrasporto_attivo;
	public static BufferedImage eye_sopra_fermo, eye_sotto_fermo, eye_destra_fermo, eye_sinistra_fermo; 
	public static BufferedImage[] eye_sopra, eye_sotto, eye_destra, eye_sinistra; 
	
	public static BufferedImage sfondo_popup, stato_info;
	public static BufferedImage voce_uscita, voce_azzera, voce_vittoria, voce_sconfitta, voce_no_punteggio,
	voce_no_carica, voce_salva, voce_si, voce_no, voce_si_off, voce_no_off, voce_annulla, voce_annulla_off, voce_nome,
	voce_ok, voce_ok_off, voce_salvataggio, voce_no_salvataggio, voce_no_nominativo, voce_azzera_classifica;
	

	public static String utenteCorrente;
	public static String JAR_PATH = System.getProperty("java.class.path"); //TODO
	public static final String SEPARATORE = System.getProperty("file.separator");
	public static final String PATH_INTERNO_LIVELLI = "res/livelli"; //il percorso e' interno al .jar
	public static final String PATH_RISORSE = pathEsterno();
	public static final String PATH_ESTERNO_LIVELLI = PATH_RISORSE + Risorse.SEPARATORE + "livelli";
	//public static final String USER_DIR = System.getProperty("user.dir");
	
	//FIXME i seguenti percorsi dovranno esssere final
	public static final String LINGUA = PATH_RISORSE+ Risorse.SEPARATORE + "lingua.txt";
	public static final String SUONO = PATH_RISORSE + Risorse.SEPARATORE + "suono.txt";
	public static final String CLASSIFICA = PATH_RISORSE + Risorse.SEPARATORE + "classificaPunteggio.txt";
	public static String DIR_UTENTI= PATH_RISORSE + Risorse.SEPARATORE + "Utenti";
	public static String DIR_UTENTE;
	public static String STATISTICA;
	
	
	//public static String LIVELLO = PATH + "\\livelloS.txt"; eliminato
	/**
	 * il metodo restituisce il percorso (esterno al .jar) della cartelle risorse
	 * @return una stringa con percorso assoluto dove si trovano i livelli
	 */
	private static String pathEsterno(){
		// in linux
		if(System.getProperty("os.name").equals("Linux")){
			try {
				JAR_PATH = Risorse.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		
		File f = new File(JAR_PATH);
		//prende il percorso del file .jar "sale sopra" di un livello
		String percorso = f.getParent() + Risorse.SEPARATORE + "Risorse";
		
		return percorso;
	}
	
	/*private static String pathUtente(){
		String path = null;
		try{
			File f = new File(UTENTE);
			if(!f.exists()){
				f.createNewFile();
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			path = br.readLine();
			br.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(UTENTE);
		System.out.println(path);
		return path;
	}*/
	
	/**
	 * Inizializza le risorse del gameplay.
	 */
	public static void inizializza(){
		/* TODO
		if(PATH.startsWith("/")){
			PATH = System.getProperty("user.home") + "/Risorse";
			LINGUA = PATH + "/lingua.txt";
			SUONO = PATH + "/suono.txt";
			CLASSIFICA = PATH + "/classificaPunteggio.txt";
			//LIVELLO = PATH + "/livelloS.txt"; eliminato
		}*/
		Sprite t = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/sprite/tiles1.png"));
		//Sprite t1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/sprite/tiles.png"));
		Sprite s = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/sprite/sink.png"));
		
		sink_sotto = new BufferedImage[24];
		sink_sopra = new BufferedImage[24];
		sink_sinistra = new BufferedImage[24];
		sink_destra = new BufferedImage[24];
		sink_sotto_fermo = new BufferedImage[3];
		sink_sinistra_fermo = new BufferedImage[3];
		sink_destra_fermo = new BufferedImage[3];
		strada = t.prendiSprite(0, 0, altezza, altezza);
		muro = t.prendiSprite(altezza, 0, altezza, altezza);
		
		sink_sopra_fermo = s.prendiSprite(0, 0, larghezza, altezza);
		
		for(int i = 0; i < 3; i++){
			sink_sinistra_fermo[i] = s.prendiSprite(i*larghezza, altezza, larghezza, altezza);
		}
		
		for(int i = 0; i < 3; i++){
			sink_destra_fermo[i] = s.prendiSprite(i*larghezza, altezza*2, larghezza, altezza);
		}
		
		for(int i = 0; i < 3; i++){
			sink_sotto_fermo[i] = s.prendiSprite(i*larghezza, altezza*3, larghezza, altezza);
		}
		
		for(int i = 0; i < 12; i++){
			sink_sopra[i] = s.prendiSprite(i * larghezza, altezza*4, larghezza, altezza);
		}
		for(int i = 12, p = 0; i < 24 && p < 12; i++, p++){
			sink_sopra[i] = s.prendiSprite(p * larghezza, altezza*8, larghezza, altezza);
		}
		
		for(int i = 0; i < 12; i++){
			sink_destra[i] = s.prendiSprite(i * larghezza, altezza*5, larghezza, altezza);
		}
		
		for(int i = 12, p = 0; i < 24 && p < 12; i++, p++){
			sink_destra[i] = s.prendiSprite(p * larghezza, altezza*9, larghezza, altezza);
		}
		
		for(int i = 0; i < 12; i++){
			sink_sotto[i] = s.prendiSprite(i*larghezza, altezza*6, larghezza, altezza);
		}
		
		for(int i = 12, p = 0; i < 24 && p < 12; i++, p++){
			sink_sotto[i] = s.prendiSprite(p * larghezza, altezza*10, larghezza, altezza);
		}
		
		for(int i = 0; i < 12; i++){
			sink_sinistra[i] = s.prendiSprite(i*larghezza, altezza*7, larghezza, altezza);
		}
		
		for(int i = 12, p = 0; i < 24 && p < 12; i++, p++){
			sink_sinistra[i] = s.prendiSprite(p * larghezza, altezza*11, larghezza, altezza);
		}
		
		Sprite e = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/sprite/eyegore.png")); 


		 
		eye_sotto_fermo = e.prendiSprite(0, 0, altezza, altezza); 
		eye_sopra_fermo = e.prendiSprite(0, altezza, altezza, altezza); 
		eye_sinistra_fermo = e.prendiSprite(0, altezza*2, altezza, altezza); 
		eye_destra_fermo = e.prendiSprite(0, altezza*3, altezza, altezza); 
		eye_sotto = new BufferedImage[4]; 
		eye_sopra = new BufferedImage[4]; 
		eye_sinistra = new BufferedImage[4]; 
		eye_destra = new BufferedImage[4]; 
		for(int i = 0; i < 4; i++){ 
			eye_sotto[i] = e.prendiSprite(i*altezza, altezza*4, altezza, altezza); 
			eye_sopra[i] = e.prendiSprite(i*altezza, altezza*5, altezza, altezza); 
			eye_sinistra[i] = e.prendiSprite(i*altezza, altezza*6, altezza, altezza); 
			eye_destra[i] = e.prendiSprite(i*altezza, altezza*7, altezza, altezza); 
		} 
		
		voci_pausa = new BufferedImage[4];
		voci_pausa_off = new BufferedImage[4];
		
		voci_menu = new BufferedImage[7];
		voci_menu_off = new BufferedImage[7];
		
		voci_opzioni = new BufferedImage[3];
		voci_opzioni_off = new BufferedImage[3];
		
		voci_musica = new BufferedImage[2];
		
		voci_classifica = new BufferedImage[2];
		voci_classifica_off = new BufferedImage[2];
		
		
		lingue = new BufferedImage[3];
		
		Sprite s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/lingue.png"));
		
		lingue[0] = s1.prendiSprite(0, 3, 239, 40);
		lingue[1] = s1.prendiSprite(0, 47, 227, 40);
		lingue[2] = s1.prendiSprite(0, 91, 245, 40);
		
		sfondo_popup = CaricatoreImmagini.caricaImmagine("res/img/sfondi/popup.png");
		
		Sprite c = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/sprite/caramella1.png"));
		caramella = c.prendiSprite(0, 0, altezza, altezza);
		
		Sprite d = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/sprite/Trofeo_d'oro.png"));
		trofeo = d.prendiSprite(0, 0, 40, 40);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_conferma.png"));
		voce_ok = s1.prendiSprite(0, 110, 40, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_conferma_off.png"));
		voce_ok_off = s1.prendiSprite(0, 110, 40, 22);
		
		Sprite teletrasporto = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/sprite/teletrasporto.png"));
		teletrasporto_attivo = new BufferedImage[3];
		for(int i = 0; i < teletrasporto_attivo.length; i++){
			teletrasporto_attivo[i] = teletrasporto.prendiSprite(i*altezza, 0, altezza, altezza); 		}
		teletrasporto_inattivo = teletrasporto.prendiSprite(96, 0, altezza, altezza); 		
		Sprite p = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/sprite/porta.png"));
		porta_chiusa = p.prendiSprite(0, 0, altezza, altezza);
		porta_aperta = p.prendiSprite(0, 3*altezza, altezza, altezza);
		porta_media_aperta = new BufferedImage[3];
		porta_media_chiusa = new BufferedImage[3];
		porta_media_aperta[0] = p.prendiSprite(0, altezza, altezza, altezza);
		porta_media_aperta[1] = p.prendiSprite(0, 2*altezza, altezza, altezza);
		porta_media_aperta[2] = porta_aperta;
		
		porta_media_chiusa[0] = p.prendiSprite(0, 2*altezza, altezza, altezza);
		porta_media_chiusa[1] = p.prendiSprite(0, altezza, altezza, altezza);
		porta_media_chiusa[2] = porta_chiusa;
		
		Sprite interruttore = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/sprite/lever1.png"));
		interruttore_acceso_sinistra = interruttore.prendiSprite(99, 10, 26, 20);
		interruttore_acceso_destra = interruttore.prendiSprite(99, 74, 26, 20);
		interruttore_spento = interruttore.prendiSprite(99, 106, 26, 20);
		
		//se non esistono crea le cartelle Risorse e livelli
		File f = new File(PATH_RISORSE);
		if(!f.exists()) {
			f.mkdirs();
		}
		File f1 = new File(PATH_ESTERNO_LIVELLI);
		if(!f1.exists()){
			f1.mkdirs();
		}
		
		File f2 = new File(DIR_UTENTI);
		if(!f2.exists()){
			f2.mkdirs();
		}
		
	}
	
	/**
	 * Inizializza le voci di scorrimento per gli stati
	 * di selezione in lingua italiana.
	 */
	public static void inizializzaITA(){
		
		Sprite s5 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voce_classifica.png"));
		Sprite s6 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voce_classifica_off.png"));
		
		voci_classifica[0] = s5.prendiSprite(0, 0, 533, 44);
		voci_classifica[1] = s5.prendiSprite(0, 44, 403, 44);
		
		voci_classifica_off[0] = s6.prendiSprite(0, 0, 533, 44);
		voci_classifica_off[1] = s6.prendiSprite(0, 44, 403, 44);
		
		Sprite s3 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_opzioni.png"));
		Sprite s4 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_opzioni_off.png"));
		
		voci_opzioni[0] = s3.prendiSprite(0, 3, 191, 40);
		voci_opzioni[1] = s3.prendiSprite(0, 47, 196, 40);
		voci_opzioni[2] = s3.prendiSprite(0, 91, 403, 40);
		
		voci_opzioni_off[0] = s4.prendiSprite(0, 3, 191, 40);
		voci_opzioni_off[1] = s4.prendiSprite(0, 47, 196, 40);
		voci_opzioni_off[2] = s4.prendiSprite(0, 91, 403, 40);
		
		s3 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_musica.png"));
	
		voci_musica[0] = s3.prendiSprite(0, 3, 74, 40);
		voci_musica[1] = s3.prendiSprite(0, 47, 109, 40);
		
		Sprite s = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_menu.png"));
		Sprite s0 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_menu_off.png"));
		
		voci_menu[0] = s.prendiSprite(0, 3, 408, 40);
		voci_menu[1] = s.prendiSprite(0, 47, 424, 40);
		voci_menu[2] = s.prendiSprite(0, 91, 313, 40);
		voci_menu[3] = s.prendiSprite(0, 136, 339, 40);
		voci_menu[4] = s.prendiSprite(0, 179, 212, 40);
		voci_menu[5] = s.prendiSprite(0, 222, 126, 40);
		voci_menu[6] = s.prendiSprite(0, 266, 126, 40);
		
		voci_menu_off[0] = s0.prendiSprite(0, 3, 408, 40);
		voci_menu_off[1] = s0.prendiSprite(0, 47, 424, 40);
		voci_menu_off[2] = s0.prendiSprite(0, 91, 313, 40);
		voci_menu_off[3] = s0.prendiSprite(0, 136, 339, 40);
		voci_menu_off[4] = s0.prendiSprite(0, 179, 212, 40);
		voci_menu_off[5] = s0.prendiSprite(0, 222, 126, 40);
		voci_menu_off[6] = s0.prendiSprite(0, 266, 126, 40);
		
		titoloPausa = CaricatoreImmagini.caricaImmagine("res/img/titoli/titoloPausa.png");

		
		Sprite s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_pausa.png"));
		Sprite s2 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_pausa_off.png"));
		
		voci_pausa[0] = s1.prendiSprite(0, 0, 250, 40);
		voci_pausa[1] = s1.prendiSprite(0, 63, 215, 40);
		voci_pausa[2] = s1.prendiSprite(0, 128, 175, 40);
		voci_pausa[3] = s1.prendiSprite(0, 190, 404, 40);
		ok = s1.prendiSprite(186, 128, 103, 40);

		voci_pausa_off[0] = s2.prendiSprite(0, 0, 250, 40);
		voci_pausa_off[1] = s2.prendiSprite(0, 63, 215, 40);
		voci_pausa_off[2] = s2.prendiSprite(0, 128, 175, 40);
		voci_pausa_off[3] = s2.prendiSprite(0, 190, 404, 40);
		
		//Voci popup
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_uscita.png"));
		voce_uscita = s1.prendiSprite(0, 0, 400, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_conferma.png"));
		voce_si = s1.prendiSprite(0, 0, 30, 22);
		voce_no = s1.prendiSprite(0, 22, 40, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_conferma_off.png"));
		voce_si_off = s1.prendiSprite(0, 0, 30, 22);
		voce_no_off = s1.prendiSprite(0, 22, 40, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_azzera.png"));
		voce_azzera = s1.prendiSprite(0, 0, 557, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_vittoria.png"));
		voce_vittoria = s1.prendiSprite(0, 0, 139, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_no_punteggio.png"));
		voce_no_punteggio = s1.prendiSprite(0, 0, 277, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_carica_salvataggio.png"));
		voce_no_carica = s1.prendiSprite(0, 0, 308, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_salva.png"));
		voce_salva = s1.prendiSprite(0, 0, 601, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_sconfitta.png"));
		voce_sconfitta = s1.prendiSprite(0, 0, 630, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_annulla.png"));
		voce_annulla = s1.prendiSprite(0, 0, 126, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_annulla_off.png"));
		voce_annulla_off = s1.prendiSprite(0, 0, 126, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_nome.png"));
		voce_nome = s1.prendiSprite(0, 0, 74, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_salvataggio.png"));
		voce_salvataggio = s1.prendiSprite(0, 0, 374, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_no_salvataggio.png"));
		voce_no_salvataggio = s1.prendiSprite(0, 0, 533, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_no_nominativo.png"));
		voce_no_nominativo = s1.prendiSprite(0, 0, 448, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_azzera_classifica.png"));
		voce_azzera_classifica = s1.prendiSprite(0, 0, 316, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_utente.png"));
		voce_utente = s1.prendiSprite(0, 3, 437, 40);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_utente_off.png"));
		voce_utente_off = s1.prendiSprite(0, 3, 437, 40);
		
		stato_info = CaricatoreImmagini.caricaImmagine("res/img/sfondi/stato_info_ita.png");
		
	}
	
	
	/**
	 * Inizializza le voci di scorrimento per gli stati
	 * di selezione in lingua inglese.
	 */
	public static void inizializzaENG(){		
		
		Sprite s5 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voce_classifica_eng.png"));
		Sprite s6 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voce_classifica_eng_off.png"));
		
		voci_classifica[0] = s5.prendiSprite(0, 0, 482, 44);
		voci_classifica[1] = s5.prendiSprite(0, 44, 277, 44);
		
		voci_classifica_off[0] = s6.prendiSprite(0, 0, 482, 44);
		voci_classifica_off[1] = s6.prendiSprite(0, 44, 277, 44);
		
		Sprite s3 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_opzioni_eng.png"));
		Sprite s4 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_opzioni_eng_off.png"));
		
		voci_opzioni[0] = s3.prendiSprite(0, 3, 280, 40);
		voci_opzioni[1] = s3.prendiSprite(0, 47, 161, 40);
		voci_opzioni[2] = s3.prendiSprite(0, 91, 277, 40);
		
		voci_opzioni_off[0] = s4.prendiSprite(0, 3, 280, 40);
		voci_opzioni_off[1] = s4.prendiSprite(0, 47, 161, 40);
		voci_opzioni_off[2] = s4.prendiSprite(0, 91, 277, 40);
		
		s3 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_musica.png"));
		
		voci_musica[0] = s3.prendiSprite(0, 3, 74, 40);
		voci_musica[1] = s3.prendiSprite(0, 47, 109, 40);
		
		Sprite s = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_menu_eng.png"));
		Sprite s0 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_menu_eng_off.png"));
		
		voci_menu[0] = s.prendiSprite(0, 3, 261, 40);
		voci_menu[1] = s.prendiSprite(0, 47, 291, 40);
		voci_menu[2] = s.prendiSprite(0, 91, 215, 40);
		voci_menu[3] = s.prendiSprite(0, 136, 171, 40);
		voci_menu[4] = s.prendiSprite(0, 179, 258, 40);
		voci_menu[5] = s.prendiSprite(0, 222, 126, 40);
		voci_menu[6] = s.prendiSprite(0, 266, 121, 40);
		
		voci_menu_off[0] = s0.prendiSprite(0, 3, 261, 40);
		voci_menu_off[1] = s0.prendiSprite(0, 47, 291, 40);
		voci_menu_off[2] = s0.prendiSprite(0, 91, 215, 40);
		voci_menu_off[3] = s0.prendiSprite(0, 136, 171, 40);
		voci_menu_off[4] = s0.prendiSprite(0, 179, 258, 40);
		voci_menu_off[5] = s0.prendiSprite(0, 222, 126, 40);
		voci_menu_off[6] = s0.prendiSprite(0, 266, 121, 40);
		
		
		titoloPausa = CaricatoreImmagini.caricaImmagine("res/img/titoli/pause.png");
		
		Sprite s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_pausa_eng.png"));
		Sprite s2 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_pausa_eng_off.png"));
		
		voci_pausa[0] = s1.prendiSprite(0, 3, 264, 40);
		voci_pausa[1] = s1.prendiSprite(0, 48, 243, 40);
		voci_pausa[2] = s1.prendiSprite(0, 92, 146, 40);
		voci_pausa[3] = s1.prendiSprite(0, 136, 276, 40);
		ok = s1.prendiSprite(161, 92, 103, 40);
		
		voci_pausa_off[0] = s2.prendiSprite(0, 3, 264, 40);
		voci_pausa_off[1] = s2.prendiSprite(0, 48, 243, 40);
		voci_pausa_off[2] = s2.prendiSprite(0, 92, 146, 40);
		voci_pausa_off[3] = s2.prendiSprite(0, 136, 276, 40);
		
		//Voci popup
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_uscita.png"));
		voce_uscita = s1.prendiSprite(0, 22, 320, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_conferma.png"));
		voce_si = s1.prendiSprite(0, 44, 60, 22);
		voce_no = s1.prendiSprite(0, 22, 40, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_conferma_off.png"));
		voce_si_off = s1.prendiSprite(0, 44, 60, 22);
		voce_no_off = s1.prendiSprite(0, 22, 40, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_azzera.png"));
		voce_azzera = s1.prendiSprite(0, 22, 502, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_vittoria.png"));
		voce_vittoria = s1.prendiSprite(0, 22, 114, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_no_punteggio.png"));
		voce_no_punteggio = s1.prendiSprite(0, 22, 372, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_carica_salvataggio.png"));
		voce_no_carica = s1.prendiSprite(0, 22, 125, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_salva.png"));
		voce_salva = s1.prendiSprite(0, 22, 601, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_sconfitta.png"));
		voce_sconfitta = s1.prendiSprite(0, 22, 628, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_annulla.png"));
		voce_annulla = s1.prendiSprite(0, 22, 110, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_annulla_off.png"));
		voce_annulla_off = s1.prendiSprite(0, 22, 110, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_nome.png"));
		voce_nome = s1.prendiSprite(0, 22, 74, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_salvataggio.png"));
		voce_salvataggio = s1.prendiSprite(0, 22, 250, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_no_salvataggio.png"));
		voce_no_salvataggio = s1.prendiSprite(0, 22, 597, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_no_nominativo.png"));
		voce_no_nominativo = s1.prendiSprite(0, 22, 424, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_azzera_classifica.png"));
		voce_azzera_classifica = s1.prendiSprite(0, 22, 296, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_utente.png"));
		voce_utente = s1.prendiSprite(0, 47, 357, 40);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_utente_off.png"));
		voce_utente_off = s1.prendiSprite(0, 47, 357, 40);
		
		stato_info = CaricatoreImmagini.caricaImmagine("res/img/sfondi/stato_info_eng.png");
		
	}
	
	/**
	 * Inizializza le voci di scorrimento per gli stati
	 * di selezione in lingua tedesca.
	 */
	public static void inizializzaDEU(){
		
		Sprite s5 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voce_classifica_deu.png"));
		Sprite s6 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voce_classifica_deu_off.png"));
		
		voci_classifica[0] = s5.prendiSprite(0, 0, 579, 44);
		voci_classifica[1] = s5.prendiSprite(0, 44, 250, 44);
		
		voci_classifica_off[0] = s6.prendiSprite(0, 0, 579, 44);
		voci_classifica_off[1] = s6.prendiSprite(0, 44, 250, 44);
		
		Sprite s3 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_opzioni_deu.png"));
		Sprite s4 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_opzioni_deu_off.png"));
		
		voci_opzioni[0] = s3.prendiSprite(0, 3, 250, 40);
		voci_opzioni[1] = s3.prendiSprite(0, 47, 160, 40);
		voci_opzioni[2] = s3.prendiSprite(0, 91, 250, 40);
		
		voci_opzioni_off[0] = s4.prendiSprite(0, 3, 250, 40);
		voci_opzioni_off[1] = s4.prendiSprite(0, 47, 160, 40);
		voci_opzioni_off[2] = s4.prendiSprite(0, 91, 250, 40);
		
		s3 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_musica.png"));
		
		voci_musica[0] = s3.prendiSprite(0, 3, 74, 40);
		voci_musica[1] = s3.prendiSprite(0, 47, 109, 40);
		
		Sprite s = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_menu_deu.png"));
		Sprite s0 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_menu_deu_off.png"));
		
		voci_menu[0] = s.prendiSprite(0, 3, 342, 40);
		voci_menu[1] = s.prendiSprite(0, 47, 305, 40);
		voci_menu[2] = s.prendiSprite(0, 91, 293, 40);
		voci_menu[3] = s.prendiSprite(0, 136, 341, 40);
		voci_menu[4] = s.prendiSprite(0, 179, 263, 40);
		voci_menu[5] = s.prendiSprite(0, 222, 290, 40);
		voci_menu[6] = s.prendiSprite(0, 266, 250, 40);
		
		voci_menu_off[0] = s0.prendiSprite(0, 3, 342, 40);
		voci_menu_off[1] = s0.prendiSprite(0, 47, 305, 40);
		voci_menu_off[2] = s0.prendiSprite(0, 91, 293, 40);
		voci_menu_off[3] = s0.prendiSprite(0, 136, 341, 40);
		voci_menu_off[4] = s0.prendiSprite(0, 179, 263, 40);
		voci_menu_off[5] = s0.prendiSprite(0, 222, 290, 40);
		voci_menu_off[6] = s0.prendiSprite(0, 266, 250, 40);
		
		titoloPausa = CaricatoreImmagini.caricaImmagine("res/img/titoli/pause.png");

		
		Sprite s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_pausa_deu.png"));
		Sprite s2 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_pausa_deu_off.png"));
		
		voci_pausa[0] = s1.prendiSprite(0, 3, 414, 40);
		voci_pausa[1] = s1.prendiSprite(0, 48, 292, 40);
		voci_pausa[2] = s1.prendiSprite(0, 92, 302, 40);
		voci_pausa[3] = s1.prendiSprite(0, 136, 276, 40);
		ok = s1.prendiSprite(316, 92, 103, 40);
		
		voci_pausa_off[0] = s2.prendiSprite(0, 3, 414, 40);
		voci_pausa_off[1] = s2.prendiSprite(0, 48, 292, 40);
		voci_pausa_off[2] = s2.prendiSprite(0, 92, 302, 40);
		voci_pausa_off[3] = s2.prendiSprite(0, 136, 276, 40);
		
		//Voci popup
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_uscita.png"));
		voce_uscita = s1.prendiSprite(0, 44, 487, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_conferma.png"));
		voce_si = s1.prendiSprite(0, 66, 40, 22);
		voce_no = s1.prendiSprite(0, 88, 64, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_conferma_off.png"));
		voce_si_off = s1.prendiSprite(0, 66, 40, 22);
		voce_no_off = s1.prendiSprite(0, 88, 64, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_azzera.png"));
		voce_azzera = s1.prendiSprite(0, 44, 620, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_vittoria.png"));
		voce_vittoria = s1.prendiSprite(0, 44, 154, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_no_punteggio.png"));
		voce_no_punteggio = s1.prendiSprite(0, 44, 256, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_salva.png"));
		voce_salva = s1.prendiSprite(0, 44, 678, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_sconfitta.png"));
		voce_sconfitta = s1.prendiSprite(0, 44, 787, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_annulla.png"));
		voce_annulla = s1.prendiSprite(0, 44, 128, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_annulla_off.png"));
		voce_annulla_off = s1.prendiSprite(0, 44, 128, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_nome.png"));
		voce_nome = s1.prendiSprite(0, 22, 74, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_salvataggio.png"));
		voce_salvataggio = s1.prendiSprite(0, 44, 360, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_no_salvataggio.png"));
		voce_no_salvataggio = s1.prendiSprite(0, 44, 692, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_carica_salvataggio.png"));
		voce_no_carica = s1.prendiSprite(0, 44, 318, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_no_nominativo.png"));
		voce_no_nominativo = s1.prendiSprite(0, 44, 448, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_azzera_classifica.png"));
		voce_azzera_classifica = s1.prendiSprite(0, 44, 296, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_utente.png"));
		voce_utente = s1.prendiSprite(0, 90, 366, 40);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_utente_off.png"));
		voce_utente_off = s1.prendiSprite(0, 90, 366, 40);
		
		stato_info = CaricatoreImmagini.caricaImmagine("res/img/sfondi/stato_info_deu.png");
	}

}
