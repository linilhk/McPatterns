
public class McPatterns {
	public static void main(String[] args){
		
		//testing with puting the menu.txt in C:/
		/*String[] abc = new String[1];
		abc[0] = "C:/menu.txt";
        McPatternsGUI gui = new McPatternsGUI(new McPatternsPresenter(abc));*/
		
		McPatternsGUI gui = new McPatternsGUI(new McPatternsPresenter(args));
    }
}
