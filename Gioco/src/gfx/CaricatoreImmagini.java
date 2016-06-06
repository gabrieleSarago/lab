package gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Oggetto utile per il caricamento di immagini.
 */

public class CaricatoreImmagini {
	
	/**
	 * Carica l'immagine da file.
	 * @param path il percorso (path) del file.
	 * @return l'immagine.
	 */
	public static BufferedImage caricaImmagine(String path){
		
		try {
			/*Per convertire in jar bisogna creare una source folder, resource, che conterrà la cartella res
			privata della build path. Questo perchè la cartella res non viene esportata nel jar s viene intesa
			come source folder ma esporta solo le sue sottocartelle.
			Si aggiunge "/" al path poichè altrimenti la ricerca del file inizierebbe
			dalla classe in cui si chiama getResourceAsStream, ovvero CaricatoreImmagini.
			In questo modo parte da res a cercare.
			*/
			return ImageIO.read(CaricatoreImmagini.class.getResourceAsStream("/"+path));
			//return ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(path);
			System.out.println("errore");
			System.exit(0);
		}
		return null;
	}
	
}
