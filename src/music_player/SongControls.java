package music_player;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
	import javax.sound.sampled.LineUnavailableException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


@SuppressWarnings("serial")
public class SongControls extends JPanel {

	private JButton play = new JButton("Play");
	private JButton stop = new JButton("Pause");
	private JLabel songName = new JLabel("No song selected");
	private JLabel songTime = new JLabel("0:00:00/0:00:00");
	private JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
	private Song loadedSong = null;
	private Clip activeSong = null;
	
	public SongControls(JButton prev, JButton next) throws LineUnavailableException {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		activeSong = AudioSystem.getClip();
		new Timer(500, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(activeSong != null && (activeSong.isActive())) {
					slider.setValue((int)activeSong.getFramePosition());
				} else if(activeSong.getMicrosecondLength() - activeSong.getMicrosecondPosition() < 1000000) {
					slider.setValue(activeSong.getFrameLength());
				}
			}
			
		}).start();

		JPanel topPanel = new JPanel(new FlowLayout());
		topPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
		topPanel.add(songName);
		topPanel.add(slider);
		topPanel.add(songTime);
		
		JPanel bottomPanel = new JPanel(new FlowLayout());		
		play.setPreferredSize(new Dimension(100, 40));
		play.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(activeSong.getFrameLength() == activeSong.getFramePosition()) {
					activeSong.setFramePosition(0);
				}
				activeSong.start();
			}
			
		});
		bottomPanel.add(play);
				
		stop.setPreferredSize(new Dimension(100, 40));
		stop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				activeSong.stop();
			}
			
		});
		bottomPanel.add(stop);
		
		prev.setPreferredSize(new Dimension(100, 40));
		bottomPanel.add(prev);
		
		next.setPreferredSize(new Dimension(100, 40));
		bottomPanel.add(next);
		
		slider.setPreferredSize(new Dimension(200, 10));
		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(1);
		slider.setSnapToTicks(true);
		slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				activeSong.setFramePosition(slider.getValue());
				songTime.setText(microsecondsToHMS(activeSong.getMicrosecondPosition())+
						         "/"+microsecondsToHMS(activeSong.getMicrosecondLength()));
			}

		});

		this.add(topPanel);
		this.add(bottomPanel);	
	}
	
	protected void loadSong(Song song) {
		try {
			loadedSong = song;
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(song.getFile());
			songName.setText(song.getFile().getName());
			activeSong.close();
			activeSong.open(audioStream);
			slider.setMaximum(activeSong.getFrameLength());
			songTime.setText(microsecondsToHMS(activeSong.getMicrosecondPosition())+
							 "/"+microsecondsToHMS(activeSong.getMicrosecondLength()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected Song getSong() {
		return loadedSong;
	}
	
	private String microsecondsToHMS(long microseconds) {
		long totalSeconds = microseconds / 1000000;
		long hours = totalSeconds / 3600;
		long minutes = (totalSeconds / 60) % 60;
		long seconds = totalSeconds % 60;
		DecimalFormat formatter = new DecimalFormat("00");
		return formatter.format(hours) + ":" + formatter.format(minutes) + ":" + formatter.format(seconds);
	}
}
