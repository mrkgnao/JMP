package jmp;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.*;
import javax.swing.text.*;

/**
 * Highlights syntax in a DefaultStyledDocument
 *
 * @author camickr
 * @author David Underhill
 */
class AsmHighlighter extends DefaultStyledDocument {

    public static final String DEFAULT_FONT_FAMILY = "Source Code Pro";
    public static final int DEFAULT_FONT_SIZE = 14;
    public static final SimpleAttributeSet DEFAULT_NORMAL;
    public static final SimpleAttributeSet DEFAULT_REGISTER;
    public static final SimpleAttributeSet DEFAULT_COMMENT;
    public static final SimpleAttributeSet DEFAULT_STRING;
    public static final SimpleAttributeSet DEFAULT_KEYWORD;
    public static final SimpleAttributeSet DEFAULT_NUMBER;

    public Map<Integer, ArrayList<Integer>> tokenList;
    public int progElem;
    public boolean newProgElem;

    static {
        DEFAULT_NORMAL = new SimpleAttributeSet();
        StyleConstants.setForeground(DEFAULT_NORMAL, Color.BLACK);
        StyleConstants.setFontFamily(DEFAULT_NORMAL, DEFAULT_FONT_FAMILY);
        StyleConstants.setFontSize(DEFAULT_NORMAL, DEFAULT_FONT_SIZE);
        
        DEFAULT_REGISTER = new SimpleAttributeSet();
        StyleConstants.setBold(DEFAULT_REGISTER, true);
        StyleConstants.setUnderline(DEFAULT_REGISTER, true);
        StyleConstants.setItalic(DEFAULT_REGISTER, true);
        StyleConstants.setForeground(DEFAULT_REGISTER, Color.BLACK);
        StyleConstants.setFontFamily(DEFAULT_REGISTER, DEFAULT_FONT_FAMILY);
        StyleConstants.setFontSize(DEFAULT_REGISTER, DEFAULT_FONT_SIZE);

        DEFAULT_COMMENT = new SimpleAttributeSet();
        StyleConstants.setForeground(DEFAULT_COMMENT, new java.awt.Color(50, 50, 50)); //dark green
        StyleConstants.setFontFamily(DEFAULT_COMMENT, DEFAULT_FONT_FAMILY);
        StyleConstants.setItalic(DEFAULT_COMMENT, true);
        StyleConstants.setFontSize(DEFAULT_COMMENT, DEFAULT_FONT_SIZE);

        DEFAULT_STRING = new SimpleAttributeSet();
        StyleConstants.setForeground(DEFAULT_STRING, new java.awt.Color(153, 0, 107)); //dark pink
        StyleConstants.setBold(DEFAULT_STRING, true);
        StyleConstants.setFontFamily(DEFAULT_STRING, DEFAULT_FONT_FAMILY);
        StyleConstants.setFontSize(DEFAULT_STRING, DEFAULT_FONT_SIZE);

        DEFAULT_KEYWORD = new SimpleAttributeSet();
        StyleConstants.setForeground(DEFAULT_KEYWORD, new java.awt.Color(0, 0, 153)); //dark blue
        StyleConstants.setBold(DEFAULT_KEYWORD, true);
        StyleConstants.setFontFamily(DEFAULT_KEYWORD, DEFAULT_FONT_FAMILY);
        StyleConstants.setFontSize(DEFAULT_KEYWORD, DEFAULT_FONT_SIZE);

        DEFAULT_NUMBER = new SimpleAttributeSet();
        StyleConstants.setForeground(DEFAULT_NUMBER, new java.awt.Color(204, 82, 0));
        StyleConstants.setBold(DEFAULT_NUMBER, true);
        StyleConstants.setFontFamily(DEFAULT_NUMBER, DEFAULT_FONT_FAMILY);
        StyleConstants.setFontSize(DEFAULT_NUMBER, DEFAULT_FONT_SIZE);
    }

    private final DefaultStyledDocument doc;
    private final Element rootElement;
    private final MutableAttributeSet normal = DEFAULT_NORMAL;
    private final MutableAttributeSet register = DEFAULT_REGISTER;
    private final MutableAttributeSet keyword = DEFAULT_KEYWORD;
    private final MutableAttributeSet comment = DEFAULT_COMMENT;
    private final MutableAttributeSet quote = DEFAULT_STRING;
    private final MutableAttributeSet number = DEFAULT_NUMBER;

    private int fontSize = DEFAULT_FONT_SIZE;
    private String fontName = DEFAULT_FONT_FAMILY;

    public AsmHighlighter() {
        this.progElem = 0;
        this.newProgElem = false;
        this.tokenList = new HashMap<Integer, ArrayList<Integer>>();
        doc = this;
        rootElement = doc.getDefaultRootElement();
        putProperty(DefaultEditorKit.EndOfLineStringProperty, "\n");
    }

    public enum ATTR_TYPE {
        plainText, comment, opcode, string, number, register;
    }

    /**
     * Sets the font of the specified attribute
     *
     * @param attr  the attribute to apply this font to (normal, comment,
     *              string)
     * @param style font style (Font.BOLD, Font.ITALIC, Font.PLAIN)
     */
    public void setAttributeFont(ATTR_TYPE attr, int style) {
        Font f = new Font(fontName, style, fontSize);
        if (null != attr) {
            switch (attr) {
                case comment:
                    setAttributeFont(comment, f);
                    break;
                case opcode:
                    setAttributeFont(keyword, f);
                    break;
                case string:
                    setAttributeFont(quote, f);
                    break;
                case number:
                    setAttributeFont(number, f);
                    break;
                default:
                    setAttributeFont(normal, f);
                    break;
            }
        }
    }

    /**
     * Sets the font of the specified attribute
     *
     * @param attr attribute to apply this font to
     * @param f    the font to use
     */
    public static void setAttributeFont(MutableAttributeSet attr, Font f) {
        StyleConstants.setBold(attr, f.isBold());
        StyleConstants.setItalic(attr, f.isItalic());
        StyleConstants.setFontFamily(attr, f.getFamily());
        StyleConstants.setFontSize(attr, f.getSize());
    }

    /**
     * Sets the foreground (font) color of the specified attribute
     *
     * @param attr the attribute to apply this font to (normal, comment,
     *             keyword, string)
     * @param c    the color to use
     */
    public void setAttributeColor(ATTR_TYPE attr, Color c) {
        if (null != attr) {
            switch (attr) {
                case comment:
                    setAttributeColor(comment, c);
                    break;
                case opcode:
                    setAttributeColor(keyword, c);
                    break;
                case string:
                    setAttributeColor(quote, c);
                    break;
                default:
                    setAttributeColor(normal, c);
                    break;
            }
        }
    }

    /**
     * Sets the foreground (font) color of the specified attribute
     *
     * @param attr attribute to apply this color to
     * @param c    the color to use
     */
    public static void setAttributeColor(MutableAttributeSet attr, Color c) {
        StyleConstants.setForeground(attr, c);
    }

    /**
     * sets the number of characters per tab
     */
    public void setTabs(int charactersPerTab) {
        Font f = new Font(fontName, Font.PLAIN, fontSize);
        FontMetrics fm = java.awt.Toolkit.getDefaultToolkit().getFontMetrics(f);
        int charWidth = fm.charWidth('w');
        int tabWidth = charWidth * charactersPerTab;
        TabStop[] tabs = new TabStop[35];
        for (int j = 0; j < tabs.length; j++) {
            int tab = j + 1;
            tabs[j] = new TabStop(tab * tabWidth);
        }
        TabSet tabSet = new TabSet(tabs);
        SimpleAttributeSet attributes = new SimpleAttributeSet();
        StyleConstants.setTabSet(attributes, tabSet);
        int length = this.getLength();
        this.setParagraphAttributes(0, length, attributes, false);
    }

    /*
     *  Override to apply syntax highlighting after the document has been updated
     */
    @Override
    public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
        if (str.equals("[")) {
            str = "[]";
        }
        if (str.equals("\"")) {
            str = "\"\"";
        }
        super.insertString(offset, str, a);
        processChangedLines(offset, str.length());
    }

    /*
     *  Override to apply syntax highlighting after the document has been updated
     */
    @Override
    public void remove(int offset, int length) throws BadLocationException {
        super.remove(offset, length);
        processChangedLines(offset, 0);
    }

    /*
     *  Determine how many lines have been changed,
     *  then apply highlighting to each line
     */
    public void processChangedLines(int offset, int length)
            throws BadLocationException {
        String content = doc.getText(0, doc.getLength());
        //  The lines affected by the latest document update
        int startLine = rootElement.getElementIndex(offset);
        int endLine = rootElement.getElementIndex(offset + length);
        //  Do the actual highlighting
        for (int i = startLine; i <= endLine; i++) {
            applyHighlighting(content, i);
        }
    }

    /*
     *  Parse the line to determine the appropriate highlighting
     */
    private void applyHighlighting(String content, int lineNo)
            throws BadLocationException {
        int startOffset = rootElement.getElement(lineNo).getStartOffset();
        int endOffset = rootElement.getElement(lineNo).getEndOffset() - 1;
        int lineLength = endOffset - startOffset;
        int contentLength = content.length();
        if (endOffset >= contentLength) {
            endOffset = contentLength - 1;
        }
        //  set normal attributes for the line
        doc.setCharacterAttributes(startOffset, lineLength, normal, true);
        //  check for single line comment
        int index = content.indexOf(getCommentDelimiter(), startOffset);
        if ((index > -1) && (index < endOffset)) {
            doc.setCharacterAttributes(index, endOffset - index + 1, comment, false);
            endOffset = index - 1;
        }
        //  check for tokens
        checkForTokens(content, startOffset, endOffset, lineNo);
    }

    /*
     *	Parse the line for tokens to highlight
     */
    private void checkForTokens(String content, int startOffset, int endOffset, int lineNo) {
        ArrayList<Integer> curTok = new ArrayList<Integer>();
        while (startOffset <= endOffset) {
            //  skip the delimiters to find the start of a new token
            while (isDelimiter(content.substring(startOffset, startOffset + 1))) {
                if (startOffset < endOffset) {
                    startOffset++;
                } else {
                    return;
                }
            }
            //  Extract and process the entire token
            if (isQuoteDelimiter(content.substring(startOffset, startOffset + 1))) {
                startOffset = getQuoteToken(content, startOffset, endOffset);
            } else {
                startOffset = getOtherToken(content, startOffset, endOffset, lineNo);
            }
            if (newProgElem) {
                curTok.add(progElem);
                System.out.println(lineNo + ": " + curTok);
                tokenList.put(lineNo, curTok);
            }
        }
    }

    /*
     *
     */
    private int getQuoteToken(String content, int startOffset, int endOffset) {
        String quoteDelimiter = content.substring(startOffset, startOffset + 1);
        String escapeString = getEscapeString(quoteDelimiter);
        int index;
        int endOfQuote = startOffset;
        //  skip over the escape quotes in this quote
        index = content.indexOf(escapeString, endOfQuote + 1);
        while ((index > -1) && (index < endOffset)) {
            endOfQuote = index + 1;
            index = content.indexOf(escapeString, endOfQuote);
        }
        // now find the matching delimiter
        index = content.indexOf(quoteDelimiter, endOfQuote + 1);
        if ((index < 0) || (index > endOffset)) {
            endOfQuote = endOffset;
        } else {
            endOfQuote = index;
        }
        doc.setCharacterAttributes(startOffset, endOfQuote - startOffset + 1, quote, false);
        return endOfQuote + 1;
    }

    private int getOtherToken(String content, int startOffset, int endOffset, int lineNo) {
        newProgElem = false;
        int endOfToken = startOffset + 1;
        while (endOfToken <= endOffset) {
            if (isDelimiter(content.substring(endOfToken, endOfToken + 1))) {
                break;
            }
            endOfToken++;
        }
        String token = content.substring(startOffset, endOfToken);
        if (isKeyword(token)) {
            progElem = (Opcode.valueOf(token).getIntVal());
            newProgElem = true;
            doc.setCharacterAttributes(startOffset, endOfToken - startOffset, keyword, false);
        } else if (Utils.isNumeric(token)) {
            progElem = (Utils.getNumericValue(token));
            newProgElem = true;
            doc.setCharacterAttributes(startOffset, endOfToken - startOffset, number, false);
        } else if (Utils.isRegister(token)) {
            progElem = Register.valueOf(token).toId();
            newProgElem = true;
            doc.setCharacterAttributes(startOffset, endOfToken - startOffset, register, false);
        } else if (token.startsWith("[") && token.endsWith("]")) {
            token.replaceAll("\\s", "");
            String off = token.substring(1, token.length() - 1);
            int idx = 0, sign = 1;
            if (token.contains("+") || token.contains("-")) {
                idx = off.indexOf("+");
                sign = 1;
                if (idx == -1) {
                    idx = off.indexOf("-");
                    sign = -1;
                }
                String gprText = off.substring(0, idx);
                String offsetText = off.substring(idx + 1);
                // Let's abuse exception handling!
                try {
                    Register reg = Register.valueOf(gprText);
                    int offset = Utils.getNumericValue(offsetText) * sign;
                    progElem = Utils.encodeMemoryValue(reg, offset);
                    newProgElem = true;
                    doc.setCharacterAttributes(startOffset + 1, idx, register, false);                    
                    doc.setCharacterAttributes(startOffset + idx + 2, off.length() - idx - 1, number, false);                    
                } catch (Exception e) {
                }
            } else {
                // (again)
                try {
                    Register reg = Register.valueOf(off);
                    progElem = Utils.encodeMemoryValue(reg, 0);
                    newProgElem = true;
                    doc.setCharacterAttributes(startOffset + 1, off.length(), register, false);
                } catch (Exception e) {
                }
            }

        }
        return endOfToken + 1;
    }

    /*
     *  Assume the needle will the found at the start/end of the line
     */
    private int indexOf(String content, String needle, int offset) {
        int index;
        while ((index = content.indexOf(needle, offset)) != -1) {
            String text = getLine(content, index).trim();
            if (text.startsWith(needle) || text.endsWith(needle)) {
                break;
            } else {
                offset = index + 1;
            }
        }
        return index;
    }

    /*
     *  Assume the needle will the found at the start/end of the line
     */
    private int lastIndexOf(String content, String needle, int offset) {
        int index;
        while ((index = content.lastIndexOf(needle, offset)) != -1) {
            String text = getLine(content, index).trim();
            if (text.startsWith(needle) || text.endsWith(needle)) {
                break;
            } else {
                offset = index - 1;
            }
        }
        return index;
    }

    private String getLine(String content, int offset) {
        int line = rootElement.getElementIndex(offset);
        Element lineElement = rootElement.getElement(line);
        int start = lineElement.getStartOffset();
        int end = lineElement.getEndOffset();
        return content.substring(start, end - 1);
    }

    protected boolean isDelimiter(String character) {
        String operands = ",;:{}()/%<=>!&|^~*";
        return Character.isWhitespace(character.charAt(0))
                || operands.contains(character);
    }

    protected boolean isQuoteDelimiter(String character) {
        String quoteDelimiters = "\"";
        return quoteDelimiters.contains(character);
    }

    protected boolean isKeyword(String token) {
        return VMConstants.keywords.contains(token);
    }

    protected String getCommentDelimiter() {
        return ";";
    }

    protected String getEscapeString(String quoteDelimiter) {
        return "\\" + quoteDelimiter;
    }

    public ArrayList<Integer> getTokenList() {
        ArrayList<Integer> arr = new ArrayList<Integer>();
        for(ArrayList<Integer> line : tokenList.values()) {
            arr.addAll(line);
        }
        System.out.println(arr);
        return arr;
    }
    
    /**
     * gets the current font size
     */
    public int getFontSize() {
        return fontSize;
    }

    /**
     * sets the current font size (affects all built-in styles)
     */
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
        StyleConstants.setFontSize(normal, fontSize);
        StyleConstants.setFontSize(quote, fontSize);
        StyleConstants.setFontSize(comment, fontSize);
    }

    /**
     * gets the current font family
     */
    public String getFontName() {
        return fontName;
    }

    /**
     * sets the current font family (affects all built-in styles)
     */
    public void setFontName(String fontName) {
        this.fontName = fontName;
        StyleConstants.setFontFamily(normal, fontName);
        StyleConstants.setFontFamily(quote, fontName);
        StyleConstants.setFontFamily(comment, fontName);
    }
}
