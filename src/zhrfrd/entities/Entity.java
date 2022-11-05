package zhrfrd.entities;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Entity extends JLabel {
    private static final long serialVersionUID = -2678484620304652158L;
    ImageIcon entityIcon;
    
    /**
     * Set the entity's starting position.
     */
    public void setStartingPosition() {}
    
    /**
     * Get the icon of the entity from the res folder.
     */
    public ImageIcon initializeIcon() {
	return this.entityIcon;
    }
}
