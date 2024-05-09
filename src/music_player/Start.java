package music_player;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Start{

	public JFrame frame = new JFrame();	
	
	public Start() throws LineUnavailableException {
        JPanel explorer = new SongExplorer();

		frame.add(explorer);
		frame.setSize(600, 400);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) throws LineUnavailableException {
		new Start();		
	}
	
}
