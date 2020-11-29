package edu.uw.meteorRush.menus;

/**
 * Configuration for a single graphic menu item
 * @author marko.p.milovanovic
 */
public class GraphicsMenuItem {

    private final String MENU_ITEM;
    private final int TOP_LEFT_X;
    private final int TOP_LEFT_Y;
    private final int FIXED_LENGTH;
    private final int FIXED_WIDTH;

    /**
     *
     * @param menuItem : the name of the menu item
     * @param topLeftX: the location of the top left corner in the x axis
     * @param topLeftY: the location of the top left corner in the y axis
     * @param fixedLength: the length
     * @param fixedWidth: the width
     */
    public GraphicsMenuItem(String menuItem, int topLeftX, int topLeftY, int fixedLength, int fixedWidth) {
        MENU_ITEM = menuItem;
        TOP_LEFT_X = topLeftX;
        TOP_LEFT_Y = topLeftY;
        FIXED_LENGTH = fixedLength;
        FIXED_WIDTH = fixedWidth;
    }

    public String getMENU_ITEM() {
        return MENU_ITEM;
    }

    public int getTOP_LEFT_X() {
        return TOP_LEFT_X;
    }

    public int getTOP_LEFT_Y() {
        return TOP_LEFT_Y;
    }

    public int getFIXED_LENGTH() {
        return FIXED_LENGTH;
    }

    public int getFIXED_WIDTH() {
        return FIXED_WIDTH;
    }

}
