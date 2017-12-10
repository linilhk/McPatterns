import java.util.*;
import java.io.*;

class MenuModel {
    // Add your implementation for Menu Items here.
    // Determine what data you want to store for each item.
	
	 public static final String NO_OPEN_FILE = "Unable to open menu file.";
	 public static final String BAD_FORMAT = "Menu file format not recognised.";
	 
	 private Map<String,Float> menu;

	 class MenuModelException extends Exception {
		 private String message;

	     MenuModelException(String msg) {
	         super(msg);
	     }
	 }

	 private MenuModel() {}

	 MenuModel(String[] filenames) throws MenuModelException {
	     menu = new HashMap<String, Float>();
	     try {
	         for (int i = 0; i < filenames.length; i++) {
	             Scanner scan = new Scanner(new File(filenames[i]));
	             while (scan.hasNext()) {
	                 String line = scan.nextLine();
	                 String[] words = line.split("\\|");
	                 menu.put(words[0], Float.parseFloat(words[1]));
	             }
	             scan.close();
	         }
	     }
	     catch (FileNotFoundException e) {
	         throw new MenuModelException(NO_OPEN_FILE + "\n" + e.getMessage());
	     }
	     catch (ArrayIndexOutOfBoundsException e) {
	         throw new MenuModelException(BAD_FORMAT);
	     }
	     
	     catch (NumberFormatException e) {
	         throw new MenuModelException(BAD_FORMAT);
	     }
	 }

	 Set<String> getItemsList() {
	     if (menu.size() == 0)
	         return new HashSet<String>();
	     return menu.keySet();
	 }

	 Float getPrice(String itemName) {
	     Float result = menu.get(itemName);
	     if (result == null)
	         return new Float(0f);
	     return result;
	 }
}