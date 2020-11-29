package edu.uw.meteorRush.menus;

import edu.uw.meteorRush.common.Display;
import java.util.ArrayList;

/**
 * The Menu display holds the GraphicsMenu
 *
 * @author marko.p.milovanovic
 */
public class MenuDisplay extends Display {

    private GraphicsMenu menu;

    public MenuDisplay(String title, int width, int height){
        super(title,width,height);
        initialize();
    }

    /**
     * Sets menu listener
     * @param listener: listens for option clicks
     */
    public void SetMenuListener(GraphicsMenuListener listener){
        menu.setMenuListener(listener);
    }

    /**
     * Initializes graphics menu
     */
    private void initialize(){
        ArrayList<GraphicsMenuItem> menuItems = new ArrayList<>();
        GraphicsMenuItem item;
        //play option
        item = new GraphicsMenuItem("Play", 280, 210, 30, 120);
        menuItems.add(item);
        //settings option
        item = new GraphicsMenuItem("Settings Menu", 400, 175, 30, 200);
        menuItems.add(item);

        // TODO
        // add more options

        //add options to menu
        menu = new GraphicsMenu(menuItems);
        menu.configure();
        window.add(menu);
        window.setVisible(true);
    }



}
