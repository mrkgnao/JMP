/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmp;

/**
 *
 * @author admin
 */
public class Utils {

    public static String hex(int x) {
        return String.format("%04x", x);
    }

    // four chars. that's why.
    // sorry.
    public static String flagText(boolean b) {
        return b ? "YUSS" : "NOPE";
    }

    public static char toValue(int x) {
        return (char) (x % VMConstants.MAX_CELL_VALUE);
    }

    public static int toAddr(int x) {
        return (x % VMConstants.NUM_ADDRS);
    }
    
    public static boolean isKeyword(String s) {
        return s.indexOf("mov") != -1;
    }
}
