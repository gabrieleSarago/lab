package gfx;

import java.awt.image.BufferedImage;

public class Risorse {
	
	private static final int size = 38;
	//private static final int lifeh = 15, lifew = 96;
	public static BufferedImage strada, muro, sink_sopra_fermo, caramella, trofeo, ok, titoloPausa;
	public static BufferedImage [] voci_pausa, voci_pausa_off,
	voci_classifica,voci_classifica_off,voci_menu, voci_menu_off, voci_opzioni, voci_opzioni_off, lingue;// tempo;
	public static BufferedImage[] sink_sotto, sink_sopra, sink_sinistra, sink_destra;
	public static BufferedImage[] sink_sotto_fermo, sink_sinistra_fermo, sink_destra_fermo;
	// ----
	public static BufferedImage sbarra_aperta, sbarra_chiusa;
	public static BufferedImage interruttore_acceso_destra, interruttore_acceso_sinistra, interruttore_spento;
	public static BufferedImage teletrasporto_attivo, teletrasporto_inattivo;
	public static BufferedImage nemico;
	
	public static BufferedImage sfondo_popup, stato_info;
	public static BufferedImage voce_uscita, voce_azzera, voce_vittoria, voce_sconfitta, voce_no_punteggio,
	voce_salva, voce_si, voce_no, voce_si_off, voce_no_off, voce_annulla, voce_annulla_off, voce_nome,
	voce_ok, voce_ok_off, voce_salvataggio, voce_no_salvataggio, voce_no_nominativo, voce_azzera_classifica;
	
	public static void inizializza(){
		Sprite s = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/sprite/sink.png"));
		
		sink_sotto = new BufferedImage[10];
		sink_sopra = new BufferedImage[10];
		sink_sinistra = new BufferedImage[10];
		sink_destra = new BufferedImage[10];
		sink_sotto_fermo = new BufferedImage[3];
		sink_sinistra_fermo = new BufferedImage[3];
		sink_destra_fermo = new BufferedImage[3];
		
		for(int i = 0; i < 10; i++){
			sink_sotto[i] = s.prendiSprite(3+i * 37, 166, size, size);
		}
		for(int i = 0; i < 10; i++){
			sink_sopra[i] = s.prendiSprite(3+i * 37, 246, size, size);
		}
		for(int i = 0; i < 10; i++){
			int x = 0;
			if(i == 9 || i == 8)
				x = 3;
			if(i == 6) x = 4;
			if(i == 1) x = 1;
			sink_sinistra[i] = s.prendiSprite(x+i * 37, 206, size, size);
		}
		for(int i = 0; i < 10; i++){
			int x = 0;
			if(i == 9 || i == 8)
				x = 3;
			if(i == 7) x = 4;
			if(i == 5) x = 5;
			sink_destra[i] = s.prendiSprite(x+i * 37, 287, size, size);
		}
		for(int i = 0; i < 3; i++){
			int x = 0;
			if(i == 2) x -= 1;
			sink_sotto_fermo[i] = s.prendiSprite(x+i * size, 4, size, size);
		}
		for(int i = 0; i < 3; i++){
			int x = 3;
			if(i == 2) x -= 1;
			sink_sinistra_fermo[i] = s.prendiSprite(x+i*size, 46, size, size);
		}
		for(int i = 0; i < 3; i++){
			int x = 0;
			if(i == 1) x -= 2;
			if(i == 2) x -= 1;
			sink_destra_fermo[i] = s.prendiSprite(x+i*size, 128, size-1, size-1);
		}
		
		sink_sopra_fermo = s.prendiSprite(3, 246, size, size);
		
		voci_pausa = new BufferedImage[4];
		voci_pausa_off = new BufferedImage[4];
		
		voci_menu = new BufferedImage[6];
		voci_menu_off = new BufferedImage[6];
		
		voci_opzioni = new BufferedImage[3];
		voci_opzioni_off = new BufferedImage[3];
		
		voci_classifica = new BufferedImage[2];
		voci_classifica_off = new BufferedImage[2];
		
		lingue = new BufferedImage[3];
		
		Sprite s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/lingue.png"));
		
		lingue[0] = s1.prendiSprite(0, 3, 239, 40);
		lingue[1] = s1.prendiSprite(0, 47, 227, 40);
		lingue[2] = s1.prendiSprite(0, 91, 245, 40);
		
		sfondo_popup = CaricatoreImmagini.caricaImmagine("res/img/sfondi/popup.png");
		
		strada = s.prendiSprite(116, 7, 32, 32);
		muro = s.prendiSprite(116+32, 7, 32, 32);
		
		Sprite c = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/sprite/caramella.png"));
		caramella = c.prendiSprite(0, 0, 40, 29);
		
		Sprite d = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/sprite/Trofeo_d'oro.png"));
		trofeo = d.prendiSprite(0, 0, 40, 40);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_conferma.png"));
		voce_ok = s1.prendiSprite(0, 110, 40, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_conferma_off.png"));
		voce_ok_off = s1.prendiSprite(0, 110, 40, 22);
		
		//----
		Sprite teletrasporto = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/sprite/TestTeletrasporto.png"));
		teletrasporto_attivo = teletrasporto.prendiSprite(588, 449, 76, 60);
		teletrasporto_inattivo = teletrasporto.prendiSprite(588, 259, 76, 60);
		
		Sprite sbarra = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/sprite/TestSbarra.png"));
		sbarra_aperta = sbarra.prendiSprite(192, 192, 32, 32);
		sbarra_chiusa = sbarra.prendiSprite(192, 128, 32, 32);
		
		Sprite interruttore = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/sprite/lever1.png"));
		//interruttore_acceso = interruttore.prendiSprite(8, 15, 138, 140);
		//interruttore_spento = interruttore.prendiSprite(151, 15, 138, 140);
		interruttore_acceso_sinistra = interruttore.prendiSprite(99, 10, 26, 20);
		interruttore_acceso_destra = interruttore.prendiSprite(99, 74, 26, 20);
		interruttore_spento = interruttore.prendiSprite(99, 106, 26, 20);
		Sprite enemy = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/sprite/nemico.png"));
		nemico = enemy.prendiSprite(200, 20, 1000, 1100);
	}
	
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
		
		Sprite s = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_menu.png"));
		Sprite s0 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_menu_off.png"));
		
		voci_menu[0] = s.prendiSprite(0, 3, 408, 40);
		voci_menu[1] = s.prendiSprite(0, 47, 424, 40);
		voci_menu[2] = s.prendiSprite(0, 91, 313, 40);
		voci_menu[3] = s.prendiSprite(0, 136, 212, 40);
		voci_menu[4] = s.prendiSprite(0, 179, 126, 40);
		voci_menu[5] = s.prendiSprite(0, 222, 126, 40);
		
		voci_menu_off[0] = s0.prendiSprite(0, 3, 408, 40);
		voci_menu_off[1] = s0.prendiSprite(0, 47, 424, 40);
		voci_menu_off[2] = s0.prendiSprite(0, 91, 313, 40);
		voci_menu_off[3] = s0.prendiSprite(0, 136, 212, 40);
		voci_menu_off[4] = s0.prendiSprite(0, 179, 126, 40);
		voci_menu_off[5] = s0.prendiSprite(0, 222, 126, 40);
		
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
		
		stato_info = CaricatoreImmagini.caricaImmagine("res/img/sfondi/stato_info_ita.png");
		
	}
	
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
		
		Sprite s = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_menu_eng.png"));
		Sprite s0 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_menu_eng_off.png"));
		
		voci_menu[0] = s.prendiSprite(0, 3, 261, 40);
		voci_menu[1] = s.prendiSprite(0, 47, 291, 40);
		voci_menu[2] = s.prendiSprite(0, 91, 215, 40);
		voci_menu[3] = s.prendiSprite(0, 136, 258, 40);
		voci_menu[4] = s.prendiSprite(0, 179, 126, 40);
		voci_menu[5] = s.prendiSprite(0, 222, 121, 40);
		
		voci_menu_off[0] = s0.prendiSprite(0, 3, 261, 40);
		voci_menu_off[1] = s0.prendiSprite(0, 47, 291, 40);
		voci_menu_off[2] = s0.prendiSprite(0, 91, 215, 40);
		voci_menu_off[3] = s0.prendiSprite(0, 136, 258, 40);
		voci_menu_off[4] = s0.prendiSprite(0, 179, 126, 40);
		voci_menu_off[5] = s0.prendiSprite(0, 222, 121, 40);
		
		
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
		
		stato_info = CaricatoreImmagini.caricaImmagine("res/img/sfondi/stato_info_eng.png");
		
	}
	
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
		
		Sprite s = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_menu_deu.png"));
		Sprite s0 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_menu_deu_off.png"));
		
		voci_menu[0] = s.prendiSprite(0, 3, 342, 40);
		voci_menu[1] = s.prendiSprite(0, 47, 305, 40);
		voci_menu[2] = s.prendiSprite(0, 91, 293, 40);
		voci_menu[3] = s.prendiSprite(0, 136, 263, 40);
		voci_menu[4] = s.prendiSprite(0, 179, 290, 40);
		voci_menu[5] = s.prendiSprite(0, 222, 250, 40);
		
		voci_menu_off[0] = s0.prendiSprite(0, 3, 342, 40);
		voci_menu_off[1] = s0.prendiSprite(0, 47, 305, 40);
		voci_menu_off[2] = s0.prendiSprite(0, 91, 293, 40);
		voci_menu_off[3] = s0.prendiSprite(0, 136, 263, 40);
		voci_menu_off[4] = s0.prendiSprite(0, 179, 290, 40);
		voci_menu_off[5] = s0.prendiSprite(0, 222, 250, 40);
		
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
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_no_nominativo.png"));
		voce_no_nominativo = s1.prendiSprite(0, 44, 448, 22);
		
		s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_menu/voci_azzera_classifica.png"));
		voce_azzera_classifica = s1.prendiSprite(0, 44, 296, 22);
		
		stato_info = CaricatoreImmagini.caricaImmagine("res/img/sfondi/stato_info_deu.png");
	}

}
