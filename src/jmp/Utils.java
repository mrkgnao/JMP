/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmp;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class Utils {

    public static String hex(int x) {
        return String.format("%04x", x);
    }
    
    public static String fullHex(int x) {
        return String.format("0x%04x", x);
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

    public static boolean isNumeric(String str) {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }

    public static List<Integer> toIntList(int[] arr) {
        List<Integer> list = new ArrayList<Integer>(arr.length);
        for (int i : arr) {
            list.add(i);
        }
        return list;
    }

    public static int decodeOffset(int memoryValue) {
        return (memoryValue / 8) - 4095;
    }

    public static int encodeMemoryValue(int regIndex, int offset) {
        return regIndex + (offset + 4095) * 8;
    }

    public static int encodeMemoryValue(Register reg, int offset) {
        return reg.toId() + (offset + 4095) * 8;
    }

    public static int encodeMemoryValue(int regIndex) {
        return encodeMemoryValue(regIndex, 0);
    }

    public static int encodeMemoryValue(Register reg) {
        return encodeMemoryValue(reg, 0);
    }
}
