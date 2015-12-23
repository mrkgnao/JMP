package jmp;

import java.awt.Color;
import java.awt.Font;


/**
 *
 * @author admin
 */
public class VMConstants {
    
    public static final int RAM_TABLE_COL_LEN = 32;
    public static final int RAM_TABLE_ROW_LEN = 32;
    public static final int NUM_ADDRS = RAM_TABLE_ROW_LEN * RAM_TABLE_COL_LEN;
    public static final int MAX_CELL_VALUE = 16 * 16 * 16 * 16;
    public static final int NUM_GPR = 4;

    // Colors
    public static final Color[] GPR_COLOR = new Color[]{
        Color.decode("0xccccff"), 
        Color.decode("0x99ff99"), 
        Color.decode("0xff66d8"), 
        Color.decode("0xffa366")
    };
    
    public static final Color IP_COLOR = Color.decode("0xffff80");
    public static final Color SP_COLOR = Color.decode("0x99ccff");
    public static final Color DEFAULT_RAM_CELL_COLOR = Color.decode("0xf0f0f0");
    public static final Color NO_TEXT_RAM_CELL_COLOR = Color.LIGHT_GRAY;
    public static final Color FLAG_OFF_COLOR = Color.LIGHT_GRAY;
    public static final Color FLAG_ON_COLOR = Color.decode("0xff9999");
    
    // Fonts
    
    public static final Font SMALL_MONOSPACE_FONT = new Font("Source Code Pro", Font.PLAIN, 11);
    public static final Font BIG_MONOSPACE_FONT = new Font("Source Code Pro", Font.PLAIN, 14);
    public static final int REGISTER_VALUE_FONT_SIZE = 12;
    public static final String RAM_EMPTY_STRING = "  ";
}