package laivanUpotus;

import java.util.Scanner;
import laivanUpotus.saveException;

public class GameControls {
	
	public static void luoLaiva(String[][] lauta, int koko){
		/**
		 * @author Niklas Kiuru
		 * @param koko = laivan koko
		 * 
		 * Tassa kaytetaan Math.random, jolla arvotaan tuleeko
		 * laiva pysty- vai vaakasuuntaan.
		 * 
		 */
		
		if (Math.random() < 0.5) {	// Pystysuunta
			int rivi = (int)(Math.random()*7);
			int sarake = (int)(Math.random()*5);
			
			for (int i = 0; i < koko; i++) {
				lauta[rivi][sarake+i] = "L";
			}
		} else {	// Vaakasuunta
			int rivi = (int)(Math.random()*5);
			int sarake = (int)(Math.random()*7);
			
			for (int i = 0; i < koko; i++) {
				lauta[rivi+i][sarake] = "L";
			}
		}
	}
	
	public static int ammuRaketti(String[][] lauta, int osumat, int raketit) throws saveException {
		/**
		 * 
		 * @author Tijam Moradi
		 * @params osumat, jotta voidaan lisata laskuriin jos tuli osuma.
		 * @params raketit, jotta voidaan tulostaa, montako rakettia jaljella.
		 * 
		 */
		@SuppressWarnings("resource")
		
		Scanner lukija = new Scanner(System.in);
		int rivi = 1,sarake = 1;
		
		System.out.println(raketit + " rakettia jaljella.\n"
				+ "Mika rivi? (1-8)");
		
		if (lukija.hasNextInt()) {
			do {
				rivi = lukija.nextInt();
				if (rivi == 1111) {		// Jos kayttaja syottaa 1111, niin peli tallentuu
					GameState.saveState(osumat, raketit, lauta);
					throw new saveException("[SAVE] Tallennusvipu heitetty!");
				}
			} while (rivi > 8 || rivi < 1);
		} else {
			rivi = 1;
		}
		
		System.out.println("Sarake? (1-8)");
		
		if (lukija.hasNextInt()) {
			do {
				sarake = lukija.nextInt();
				if (sarake == 1111) {
					GameState.saveState(osumat, raketit, lauta);
					throw new saveException("[SAVE] Tallennusvipu heitetty!");
				}
			} while (sarake < 1 || sarake > 8);
		} else {
			sarake = 1;
		}
		
		
		
		if (lauta[rivi-1][sarake-1].equals("L")) {
			osumat++;
			System.out.println("OSUMA!");
			lauta[rivi-1][sarake-1] = "!";
		} else {
			System.out.println("HUTI!");
			lauta[rivi-1][sarake-1] = "H";
		}
		
		/**
		* @exception lukija.close()	- Sallitaan pieni muistivuoto ohittamalla Scannerin sulkeminen, 
		* 	jotta InputStream ei sulkeudu. Softaan asetettu @SuppressWarnings
		* @throws IOException: stream closed
		* @see IOException
		*/
		
		return osumat;
	}
	

}
