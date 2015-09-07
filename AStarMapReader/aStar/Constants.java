/**
 * 
 */
package aStar;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Reading the map variables from constants file
 *
 */
public class Constants {
	int mapHt=0;
	int mapWd=0;
	public void getConstants(){
		Properties props = new Properties();
		FileInputStream file;
		try {
			//Change the constants file path according to the local execution machine
			file = new FileInputStream("/AStarMapReader/aStar/Constants.txt");
			//Loading constants from properties file
			props.load(file);
			mapHt = Integer.parseInt(props.getProperty("MAPHEIGHT"));
			mapWd = Integer.parseInt(props.getProperty("MAPWIDTH"));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
