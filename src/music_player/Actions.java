package music_player;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JSlider;

public class Actions {
	
	@SuppressWarnings("serial")
	public static void setAction(JButton button, JSlider slider, Action action) {
		if(action == Action.PREV) {
			button.addActionListener(new AbstractAction("Prev") {
				@Override
				public void actionPerformed(ActionEvent e) {
					int new_value = slider.getValue()-1;
					System.out.println(new_value);
					if(new_value < slider.getMinimum()) {
						return;
					}
					slider.setValue(new_value);
					System.out.println(slider.getValue());
				}
			});
		} else if(action == Action.NEXT) {
			button.addActionListener(new AbstractAction("Next") {
				@Override
				public void actionPerformed(ActionEvent e) {
					int new_value = slider.getValue()+1;
					System.out.println(new_value);
					if(new_value > slider.getMaximum()) {
						return;
					}
					slider.setValue(new_value);
					System.out.println(slider.getValue());
				}
			});			
		} else if(action == Action.PLAY) {
			button.addActionListener(new AbstractAction("Play") {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
		} else if(action == Action.STOP) {
			button.addActionListener(new AbstractAction("Stop") {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub	
				}
			});
		} else {
			System.err.println("Unknown action");
		}
	}
}