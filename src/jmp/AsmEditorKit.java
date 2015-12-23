/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmp;

import java.awt.Color;
import java.awt.Font;
import java.util.HashSet;
import javax.swing.text.*;

/**
 *
 * @author admin
 */
public class AsmEditorKit extends StyledEditorKit {

    public AsmEditorKit() {
        super();
    }

    @Override
    public Document createDefaultDocument() {
        HashSet keywords = new HashSet();
        keywords.add("mov");
        keywords.add("cmp");
        keywords.add("jmp");
        keywords.add("jnz");
        keywords.add("hlt");
        keywords.add("ret");
        keywords.add("call");

        AsmHighlighter doc = new AsmHighlighter(keywords);

        //setup how the syntax will be colored
        doc.setAttributeColor(AsmHighlighter.ATTR_TYPE.plainText, Color.black);               //black
        doc.setAttributeColor(AsmHighlighter.ATTR_TYPE.comment, new Color(50, 50, 50));   //dark green
        doc.setAttributeColor(AsmHighlighter.ATTR_TYPE.opcode, new Color(0, 0, 153));    //dark blue
        doc.setAttributeColor(AsmHighlighter.ATTR_TYPE.string, new Color(153, 0, 107));  //dark pink

        //setup how the font for the syntax
        doc.setAttributeFont(AsmHighlighter.ATTR_TYPE.plainText, Font.PLAIN);
        doc.setAttributeFont(AsmHighlighter.ATTR_TYPE.comment, Font.ITALIC);
        doc.setAttributeFont(AsmHighlighter.ATTR_TYPE.opcode, Font.BOLD);
        doc.setAttributeFont(AsmHighlighter.ATTR_TYPE.string, Font.PLAIN);

        //setup tab size (in characters)
        doc.setTabs(4);

        return doc;
    }
};
