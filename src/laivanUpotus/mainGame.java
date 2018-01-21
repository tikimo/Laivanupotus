package laivanUpotus;

import laivanUpotus.GameControls;
import laivanUpotus.LautaToimi;
import laivanUpotus.GameLogic;
import laivanUpotus.GameState;
import java.util.Scanner;

/**
 * 
 * @author Tijam Moradi
 * @version 1.2
 *
 */

public class mainGame {	

	public static void main(String[] args) {
		
		@SuppressWarnings("resource")
		Scanner lukija = new Scanner(System.in);
		String[][] lauta = new String[8][8];
		int raketit = 10, osumat = 0;
		
		if (GameState.checkSave()) {
			System.out.println("Tallennus loytyi, ladataanko? (Y/N)");
			String chosen = lukija.nextLine();
			switch (chosen) {
				case "y": 
				case "Y":
					osumat = GameState.loadOsumat();
					raketit = GameState.loadRaketit();
					GameState.loadBoard(lauta);
					LautaToimi.tietokoneNakyma(lauta);
					System.out.println("r:"+raketit+"\no:"+osumat);
					break;
				default:
					LautaToimi.mallinnaLauta(lauta);
					GameControls.luoLaiva(lauta, 4);
					break;
			}
		} else {
			LautaToimi.mallinnaLauta(lauta);
			GameControls.luoLaiva(lauta, 4);
			
			/**
			* @debug LautaToimi.tietokoneNakyma(lauta);
			*/
		} 
		

		
		aloitaPeli(lauta, raketit, osumat);
		
	}

	private static void aloitaPeli(String[][] lauta, int raketit, int osumat) {
		boolean onTallennettu = false;
		System.out.println("Peli on alkanut! Onnea! Voit tallentaa pelin missa tahansa vaiheessa syottamalla \n"
				+ "riviksi tai sarakkeeksi numero 1111.");
			while (raketit > 0 && osumat < 4) {
				LautaToimi.pelaajaNakyma(lauta);
				try {
					osumat = GameControls.ammuRaketti(lauta, osumat, raketit);
				} catch (saveException e) { 	
					/**
					 * SaveExceptionilla saadaan katevasti katkaistua GameControls.ammuRaketti
					 * ja jatkamaan sovellusta paaohjelmassa.
					 * @throws saveException
					 */
					System.out.println("Peli tallennettu! Nakemiin!");
					onTallennettu = true;
					e.printStackTrace();
					break;
				}
				raketit--;
			}
			if (!onTallennettu) {
				GameLogic.LoppuTulos(osumat, raketit, lauta);
			}
	}
	
}
