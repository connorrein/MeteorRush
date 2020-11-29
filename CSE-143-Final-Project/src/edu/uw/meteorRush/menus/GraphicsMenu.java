package edu.uw.meteorRush.menus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * This class has an icon background and holds buttons
 * by clicking a button, an option is chosen
 * @author marko.p.milovanovic
 */
public class GraphicsMenu extends Panel implements ActionListener {

    private GraphicsMenuListener menuListener;
    private ArrayList<GraphicsMenuItem> graphicsMenuItems;

    /**
     * constructor
     * @param graphicsMenuItems: keeps track of menu items locations in a list
     */
    public GraphicsMenu(ArrayList<GraphicsMenuItem> graphicsMenuItems) {
        super( null);
        this.graphicsMenuItems = graphicsMenuItems;
    }

    /**
     * sets menu listener
     * @param menuListener: listens for option clicks
     */
    public void setMenuListener(GraphicsMenuListener menuListener) {
        this.menuListener = menuListener;
    }

    /**
     * sets the background, creates transparent buttons on top of every menu item
     */
    public void configure(){
        JLabel background  = new JLabel(new ImageIcon("src/res/MainMenuBackground.jpg"));
        background.setBounds(0, 0, 900, 500);
        this.add(background);
        this.setComponentZOrder(background, 0);

        for(GraphicsMenuItem menuItem : graphicsMenuItems) {
            JButton button = new JButton();
            button.setBounds(
                    menuItem.getTOP_LEFT_X(),
                    menuItem.getTOP_LEFT_Y(),
                    menuItem.getFIXED_WIDTH(),
                    menuItem.getFIXED_LENGTH()
            );
            button.setContentAreaFilled(false);
            button.addActionListener(this);
            button.setName(menuItem.getMENU_ITEM());
            this.add(button);
        }
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        this.menuListener.menuSelected(source.getName());
    }


}
