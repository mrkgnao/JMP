/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmp;

import java.awt.*;
import java.util.HashSet;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import jmp.MainMemory;
import jmp.Processor;
import jmp.VMConstants;
import static javax.swing.GroupLayout.PREFERRED_SIZE;
import javax.swing.text.*;

/**
 *
 * @author admin
 */
public class MainUI extends JFrame {

    private final Processor proc;
    private final RamTable ram;
    private final RegisterTable reg;
    private final MainMemory mem;

    /**
     * Creates new form MainUI
     */
    public MainUI() {
        proc = new Processor(this);
        mem = proc.mainMemory();
        ram = mem.ramTable();
        reg = mem.registerTable();
        execControls = new ExecControls(proc);
        initComponents();
        setExtendedState(Frame.MAXIMIZED_BOTH);

        ram.update();
    }

    private void initComponents() {

        this.setTitle(VMConstants.DEFAULT_WINDOW_TITLE);

        codePane = new JScrollPane();
        codeArea = new JTextPane();

        outputPane = new JScrollPane();
        outputArea = new JLabel();

        statusLine = new JLabel();
        progressBar = new JProgressBar();

        EditorKit editorKit = new AsmEditorKit();

        codeArea.setEditorKitForContentType("text/java", editorKit);
        codeArea.setContentType("text/java");
        menuBar = MenuBarFactory.createMenuBar(proc, this);
        setJMenuBar(menuBar);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        outputArea.setFont(VMConstants.BIG_MONOSPACE_FONT);

        codePane.setViewportView(codeArea);
        outputPane.setViewportView(outputArea);

        statusLine.setText("this is meant to be a rad little scrollbar sometime later.");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(layout
                .createParallelGroup(Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                .addGroup(Alignment.CENTER, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(Alignment.CENTER)
                                                .addComponent(execControls, PREFERRED_SIZE, 311, PREFERRED_SIZE)
                                                .addComponent(codePane))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(Alignment.TRAILING)
                                                .addComponent(reg)
                                                .addComponent(ram)
                                                .addComponent(outputPane))
                                ))
                        .addContainerGap())
        );

        layout.setVerticalGroup(layout
                .createParallelGroup(Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(execControls, PREFERRED_SIZE, 30, PREFERRED_SIZE)
                                                .addGap(10)
                                                .addComponent(codePane)
                                        ))
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(reg)
                                        .addComponent(ram)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(outputPane, PREFERRED_SIZE, 90, PREFERRED_SIZE)))
                        .addContainerGap())
        );

        pack();
    }

    /**
     * @param args the command line arguments
     */
    public static void createAndDisplay(String args[]) {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainUI ui = new MainUI();
                ui.setVisible(true);
            }
        });
    }

    JTextPane codeArea;
    private JScrollPane codePane;
    private JScrollPane outputPane;
    private JMenuBar menuBar;
    private JLabel outputArea;
    private JProgressBar progressBar;
    private JLabel statusLine;

    private ExecControls execControls;

}
