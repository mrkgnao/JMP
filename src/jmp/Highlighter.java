package jmp;

import java.awt.Color;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

public class Highlighter implements DocumentListener {

    private JTextPane codeArea;

    public Highlighter(JTextPane codeArea) {
        this.codeArea = codeArea;
    }

    public void insertUpdate(final DocumentEvent e) {
        highlight(e.getDocument(), e.getOffset(), e.getLength());
    }

    public void removeUpdate(DocumentEvent e) {
        highlight(e.getDocument(), e.getOffset(), 0);
    }

    public void changedUpdate(DocumentEvent e) {
    }

    private void highlight(final Document doc, final int offset, final int length) {
        //Edit the color only when the EDT is ready
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    //The impacted text is the edition + the surrounding part words.
                    int start = Utilities.getWordStart(codeArea, offset);
                    int end = Utilities.getWordEnd(codeArea, offset + length);
                    String impactedText = doc.getText(start, end - start);
                    applyHighlighting(doc, impactedText, offset);
                } catch (BadLocationException ex) {
                    Logger.getLogger(Highlighter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void applyHighlighting(Document doc, String text, int offset) {
        //we review each word and color them if needed.
        System.out.println("Reviewing " + text);
        StringTokenizer tokenizer = new StringTokenizer(text, " \t\n\r\f,.:;?![]'()");
        int start = 0;
        while (tokenizer.hasMoreTokens()) {
            String word = tokenizer.nextToken();
            start = text.indexOf(word, start + 1);
            try {
                if (Utils.isKeyword(word)) {
                    changeColor(codeArea, word, Color.BLACK, start, word.length());
                } else if (offset == 0 || !tokenizer.hasMoreTokens()) {
                    changeColor(codeArea, word, Color.BLUE, start, word.length());
                }
            } catch (BadLocationException e) {
                ;
            }
        }
    }

    private void changeColor(JTextPane tp, String msg, Color c, int beginIndex, int length) throws BadLocationException {
        SimpleAttributeSet sas = new SimpleAttributeSet();
        StyleConstants.setForeground(sas, c);
        StyledDocument doc = (StyledDocument) tp.getDocument();
        doc.setCharacterAttributes(beginIndex, length, sas, false);
        sas = new SimpleAttributeSet();
        StyleConstants.setForeground(sas, Color.BLACK);
        tp.setCharacterAttributes(sas, false);
    }
}
