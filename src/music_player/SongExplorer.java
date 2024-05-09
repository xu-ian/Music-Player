package music_player;

import java.awt.*;
import java.awt.event.*;


import javax.swing.filechooser.*;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;

@SuppressWarnings("serial")
public class SongExplorer extends JPanel {
	
	private DefaultListModel<Song> model = new DefaultListModel<>();
	
	private JList<Song> songList = new JList<>(model);
	
	private SongControls songControls;
		
	public SongExplorer() throws LineUnavailableException {
		
		JButton prev = new JButton("Prev");
		prev.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = model.indexOf(songControls.getSong());
				if (index == 0) {
					index = model.size();
				}
				songControls.loadSong(model.get(index - 1));
			}
		});

		JButton next = new JButton("Next");
		next.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int index = model.indexOf(songControls.getSong());
				if(index + 1 == model.size()) {
					index = -1;
				}
				songControls.loadSong(model.get(index + 1));
			}
			
		});
			
		songControls = new SongControls(prev, next);
		JButton addMP3 = new JButton("Add new song");
		addMP3.setAlignmentX(Component.CENTER_ALIGNMENT);
		addMP3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileFilter wavfilter = new FileNameExtensionFilter("WAV File","wav");
				FileFilter aufilter = new FileNameExtensionFilter("AU File","au");
				FileFilter aifffilter = new FileNameExtensionFilter("AIFF File","aiff");
				chooser.setFileFilter(aufilter);
				chooser.setFileFilter(aifffilter);
				chooser.setFileFilter(wavfilter);
				chooser.setAcceptAllFileFilterUsed(false);

				int returnVal = chooser.showOpenDialog(addMP3);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					Song song = new Song(chooser.getSelectedFile());
					String[] pathParts = song.getFile().getPath().split("\\.");
					if(!validFileType(pathParts[(pathParts.length - 1)])) {
						System.out.println("Invalid File Type");
						return;
					}
					model.addElement(song);
				}
			}	
		});
		JPanel listOptions = new JPanel(new FlowLayout());
		listOptions.setAlignmentX(Component.CENTER_ALIGNMENT);

		JButton remove = new JButton("Remove Song");
		remove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(songList.getSelectedValue() != null) {
					model.removeElement(songList.getSelectedValue());

				}
			}
		});
		
		JButton select = new JButton("Select Song");
		select.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(songList.getSelectedValue() != null) {
					songControls.loadSong(songList.getSelectedValue());
				}
			}
			
		});
		
		listOptions.add(addMP3);
		listOptions.add(remove);
		listOptions.add(select);
		
		songList.setVisibleRowCount(5);
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.add(songControls);
		this.add(listOptions);
		this.add(new JScrollPane(songList));
	}	
	
	private boolean validFileType(String str) {
		return str.equals("wav") || str.equals("au") || str.equals("aiff");		
	}
	
}
