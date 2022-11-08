package zhrfrd.entities;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Entity extends JLabel {
    private static final long serialVersionUID = -2678484620304652158L;
    ImageIcon entityIcon;   // Place holder for super class Entity
    protected double posX, posY;
    protected BufferedImage bufferedImage;
    protected JPanel panelBattlefield;
    protected Dimension size;
    protected boolean wallHit = false;
    
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
    
    /**
     * Get the width of the battlefield.
     */
    private int getBattleFieldWidth() {
	return panelBattlefield.getWidth();
    }

    /**
     * Get the height of the battlefield.
     */
    private int getBattleFieldHeight() {
	return panelBattlefield.getHeight();
    }

    
    /**
    * Get the X position of the entity.
    */
   protected int getAbsolutePosX() {
	return (int) (this.posX * (this.getBattleFieldWidth() - this.size.width) / 100);
   }

   /**
    * Get the Y position of the entity.
    */
   protected int getAbsolutePosY() {
	return (int) (this.posY * (this.getBattleFieldHeight() - this.size.height) / 100);
   }

   /**
    * Get the X position of the entity in percentage to the field width.
    */
   public double getPosX() {
	return this.posX;
   }

   /**
    * Get the Y position of the entity in percentage to the field height.
    */
   public double getPosY() {
	return this.posY;
   } 
   
   /**
    * Handle drawing of the entity to the battlefield.
    */
   public void draw() {
       this.panelBattlefield.add(this);
       this.setBounds(this.getAbsolutePosX(), this.getAbsolutePosY(), this.size.width, this.size.height);
   }
}
