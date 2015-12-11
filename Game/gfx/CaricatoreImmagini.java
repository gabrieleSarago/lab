package gfx;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CaricatoreImmagini {
	
	public static BufferedImage caricaImmagine(String path){
		
		try {
			return ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("errore");
			System.exit(0);
		}
		return null;
	}
	
}
