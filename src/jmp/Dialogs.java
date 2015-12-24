/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmp;

import javax.swing.*;

/**
 *
 * @author admin
 */
public class Dialogs {

    private static final String ABOUT_TITLE = "About JMP";
    private static final String ABOUT_MESSAGE = "JMP version 0.0.1, beta\n"
            + "A small VM that runs a simplified (and expanded!) subset of Intel-syntax assembly\n\n"
            + "TwoWayHashmap courtesy random StackOverflow answer\n"
            + "(Highly modified) syntax highlighting code courtesy the Java syntax highlighter\n" 
            + "by David Underhill aka dound, <http://dound.com/src/>\n\n"
            + "Code, design and everything else by Soham Chowdhury (mrkgnao)\n";

    public static void showAboutDialog() {
        JOptionPane.showMessageDialog(null, ABOUT_MESSAGE, ABOUT_TITLE, JOptionPane.PLAIN_MESSAGE);
    }
}
