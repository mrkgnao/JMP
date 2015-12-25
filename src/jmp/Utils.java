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

    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception e) {
            try {
                Integer.decode(str);
                return true;
            } catch (Exception e2) {
                return false;
            }
        }
    }

    public static boolean isHex(String str) {
        try {
            Integer.decode(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static int getNumericValue(String str) {
        return isHex(str) ? Integer.decode(str) : Integer.parseInt(str);
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
