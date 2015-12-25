/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmp;

import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.util.HashSet;
import javax.swing.text.*;

/**
 *
 * @author admin
 */
public class AsmEditorKit extends StyledEditorKit {
    public AsmHighlighter doc;
    
    public AsmEditorKit() {
        super();
    }

    @Override
    public Document createDefaultDocument() {
        doc = new AsmHighlighter();
        //setup tab size (in characters)
        doc.setTabs(4);
        return doc;
    }
};
