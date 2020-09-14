package task4CSVparse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class task4 {

	public static String COMMA_DELIMITER = ",";

	public static void main(String[] args) {

		List<List<String>> records = new ArrayList<>();

		try (Scanner scanner = new Scanner(new File("sample.csv"));) {

			while (scanner.hasNextLine()) {

				records.add(getRecordFromLine(scanner.nextLine()));

			}

		} catch (FileNotFoundException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		// for (List<String> rad : records) {

//			System.out.println(rad);

//		}

		int ignoreFirstRow = 0; // int som ignorerar  första raden i dokumentet

		int numberofa = 0; // int som räknar antal a´n

		int android = 0; // int som räknar antal personer som använder a

		for (List<String> b : records) { // b är en rad i dokumentet

			if (ignoreFirstRow != 0) {
				

				if (b.get(1).contains("a")) { // hämtar hur många a som finns i första rad med namn
					numberofa++;
				}

				if (b.get(2).contains("a")) { // hämtar antal a i andra raden med namn.
					numberofa++;
				}

				if (b.get(6).contains("Android")) { // hämtar antal med androider i rad 6
					android++;

				}

			} else {
				ignoreFirstRow++;
			}

		}
		System.out.println("How many a in names " + numberofa); // Skriver ut antal a i namet
		System.out.println("number person with androids " + android); // skriver ut antal med android
		ignoreFirstRow = 0; // för att radera första raden
		
		List<String> time = new ArrayList<String>();
		for (List<String> b : records) { // skapar min unika egna lista med timestamps,
			//man kan tänka ungeför som en ny speciell rad i mitt block.

			if (ignoreFirstRow != 0) {

				if (!time.contains(b.get(0))) { // räknar timestampsen

					time.add(b.get(0)); // lägger på listan
				}
			} else {
				ignoreFirstRow++;

			}
		}
		ignoreFirstRow = 0;
		for (String ts : time) { // min egna timestamp.
			String names = ts + " "; // bygger upp för utskrift
			for (List<String> b : records) { // b är listan eller rad

				if (ignoreFirstRow != 0) {

					if (ts.equals(b.get(0))) {
						names += b.get(1) + " " + b.get(2) + " "; // Lägger på samma tid på användare.

					}

				} else {
					ignoreFirstRow++;

				}
			}
			System.out.println(" "  + names); // skriver ut de som har samma tid samt deras namn.

		}
		ignoreFirstRow = 0;
		String names = " ";
		for (List<String> b : records) {

			if (ignoreFirstRow != 0) {

				names += " " + b.get(1) + " " + b.get(3) + " "; //kollar om det är likadana mail.

				if (getemail(b.get(3), records) > 1) { // likadana mail = dubbel
					names += " Double ";
				}
				names += "\n " + b.get(2) + " " + b.get(4) + " "; // Hämtar info från kolum 4 är det mer än två plussas den på.
				// \n = lika ny rad

				if (getemail(b.get(4), records) > 1) { // Hämtar info från kolum 4 är det mer än två plussas den på.
					names += " Double ";
				}
			} else {
				ignoreFirstRow++;

			}
		}
		System.out.println(names); // Skriver ut namnen med likadan mail.
		
		// names = variabel

	}

	private static List<String> getRecordFromLine(String line) { // fick vi på köpet inför denna uppgift. Bygger upp raderna i filen 
		List<String> values = new ArrayList<String>();
		try (Scanner rowScanner = new Scanner(line)) {
			rowScanner.useDelimiter(COMMA_DELIMITER);
			while (rowScanner.hasNext()) {
				values.add(rowScanner.next()); // Bygger upp raderna
			}
		}

		return values;
	}

	private static int getemail(String email, List<List<String>> records) { // <-metod

		int counter = 0;
		int ignore = 0;

		for (List<String> b : records) {

			if (ignore != 0 && !b.get(4).isEmpty()) { // använder detta för att ta bort de tomma raderna i slutet på
				
				// ! = inte & betyder att bägge måste vara samma dokumentet.

				if (b.get(3).equals(email)) {
					counter++; // denna plussar på räknaren vid samma mail.
					
				}
				if (b.get(4).equals(email)) { //denna plussar på räknaren vid samma mail.
					counter++; 
				}

			} else {
				ignore++;

			}

		}

		return counter;
	}
}
