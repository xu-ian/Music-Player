package music_player;

import java.awt.Component;
import java.awt.FlowLayout;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Song extends JPanel{
	private File file = null;
	private JButton button = null;
	
	public Song(File file) {
		this.setLayout(new FlowLayout());
		this.file = file;
		this.button = new JButton(this.file.getName());
		this.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(button);
	}
	
	public File getFile() {
		return file;
	}
	
	@Override
	public String toString(){
		return file.getName();
	}
}
