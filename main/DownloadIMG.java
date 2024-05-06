package main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DownloadIMG
{
	public static String downloadFolder = "C:\\\\Users\\\\darks\\\\Downloads\\\\imgs\\\\";	//enter the download folder address here
    public static void main(String[] args) {
        //Replace "" with path to all-cards.json
		String jsonString = "";
        
	    JSONParser parser = new JSONParser();
	    
	    try (Reader reader = new FileReader(jsonString)) {
	    	JSONArray cardArray = null;
			try {
				cardArray = (JSONArray) parser.parse(reader);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			JSONObject card;
			JSONObject uris;

	    	InputStream in;
	    	
	    	String fixedName;
	    	
	    	for (int i = 0; i < cardArray.size(); i++) {
	    		card = (JSONObject) cardArray.get(i);
	    		
	    		if (!card.get("lang").toString().equalsIgnoreCase("en"))
	    			continue;
	    		
				uris = (JSONObject) card.get("image_uris");
				if (uris == null)
					continue;
					
				fixedName = (String) card.get("name");
				fixedName = fixedName.replace("//", "---");
				fixedName = fixedName.replace(":", "--");
				fixedName = fixedName.replace("\"", "'");
				fixedName = fixedName.replace("?", "");
				fixedName = fixedName.replace("!", "");
				
	    		if ((String) uris.get("normal") != null) {
	    			//Get the small img and download
			    	if (!Files.exists(Paths.get(downloadFolder + "(" + card.get("set") + "#" + card.get("collector_number") + ")" + fixedName + "-normal.jpg"), LinkOption.NOFOLLOW_LINKS)) {
			    		in = new URL((String) uris.get("normal")).openStream();
			    		Files.copy(in, Paths.get(downloadFolder + "(" + card.get("set") + "#" + card.get("collector_number") + ")" + fixedName + "-normal.jpg"));
			    	}
	    		}
	    		
	    		if ((String) uris.get("large") != null) {
	    			//Get the large img and download
			    	if (!Files.exists(Paths.get(downloadFolder + "(" + card.get("set") + "#" + card.get("collector_number") + ")" + fixedName + "-large.jpg"), LinkOption.NOFOLLOW_LINKS)) {
			    		in = new URL((String) uris.get("large")).openStream();
			    		Files.copy(in, Paths.get(downloadFolder + "(" + card.get("set") + "#" + card.get("collector_number") + ")" + fixedName + "-large.jpg"));
			    	}
	    		}
	    	}
	    } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}