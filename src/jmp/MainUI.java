/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmp;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
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

    private JScrollPane codePane;
    private JScrollPane outputPane;
    private JMenuBar menuBar;
    private JLabel outputArea;
    private JProgressBar progressBar;
    private JLabel statusLine;

    private ExecControls execControls;
    JTextPane codeArea;

    private boolean askOnExit;
    private boolean pauseOnLostFocus, wasPausedOnLostFocus;

    public void setWasPausedOnLostFocus(boolean wasPausedOnLostFocus) {
        this.wasPausedOnLostFocus = wasPausedOnLostFocus;
    }

    public void flipWasPausedOnLostFocus() {
        this.wasPausedOnLostFocus = !wasPausedOnLostFocus;
    }

    public void flipPauseOnLostFocus() {
        this.pauseOnLostFocus = !pauseOnLostFocus;
    }

    public void flipAskOnExit() {
        askOnExit = !askOnExit;
    }

    /**
     * Creates new form MainUI
     */
    public MainUI() {
        this.wasPausedOnLostFocus = false;
        this.pauseOnLostFocus = false;
        this.askOnExit = true;
        proc = new Processor(this);
        mem = proc.mainMemory();
        ram = mem.ramTable();
        reg = mem.registerTable();
        execControls = new ExecControls(proc, this);
        initComponents();
        setExtendedState(Frame.MAXIMIZED_BOTH);

        ram.update();
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

    private void initComponents() {

        this.setTitle(VMConstants.DEFAULT_WINDOW_TITLE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void windowClosing(WindowEvent e) {
                doControlledExit();
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                if (pauseOnLostFocus && proc.isExecuting()) {
                    proc.pauseExecution();
                    wasPausedOnLostFocus = true;
                }
            }

            @Override
            public void windowActivated(WindowEvent e) {
                if (pauseOnLostFocus && wasPausedOnLostFocus && !proc.isExecuting()) {
                    proc.startExecution();
                    wasPausedOnLostFocus = false;
                }
            }
        });

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

    void doControlledExit() {
        if (!askOnExit || JOptionPane.showConfirmDialog(null,
                "Are you really sure, etc. etc. about this?",
                "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION) {
            System.out.println("Exiting with status 0.");
            System.exit(0);
        }
    }

}
