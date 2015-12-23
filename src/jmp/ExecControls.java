/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author admin
 */
public class ExecControls extends JPanel {

    private final Processor proc;

    public ExecControls(Processor proc) {
        this.proc = proc;
        initComponents();
    }

    private void initComponents() {

        openFileBtn.setIcon(new ImageIcon(getClass().getResource("/jmp/folder_open_icon&16.png")));
        openFileBtn.setToolTipText("Open assembly file");
        openFileBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        openFileBtn.setPreferredSize(new java.awt.Dimension(30, 25));
        add(openFileBtn);

        saveFileBtn.setIcon(new ImageIcon("C:\\Users\\admin\\Downloads\\cc_mono_icon_set\\cc\\black\\png\\save_icon&16.png"));
        saveFileBtn.setToolTipText("Save code to file");
        saveFileBtn.setPreferredSize(new java.awt.Dimension(30, 25));
        add(saveFileBtn);

        deleteTextBtn.setIcon(new ImageIcon("C:\\Users\\admin\\Downloads\\cc_mono_icon_set\\cc\\black\\png\\delete_icon&16.png"));
        deleteTextBtn.setToolTipText("Clear code area");
        deleteTextBtn.setPreferredSize(new java.awt.Dimension(30, 25));
        add(deleteTextBtn);

        sep.setBackground(java.awt.Color.gray);
        sep.setForeground(java.awt.Color.gray);
        sep.setMinimumSize(new java.awt.Dimension(5, 50));
        sep.setOpaque(true);
        sep.setPreferredSize(new java.awt.Dimension(1, 25));
        add(sep);

        assembleBtn.setIcon(new ImageIcon("C:\\Users\\admin\\Downloads\\cc_mono_icon_set\\cc\\black\\png\\cog_icon&16.png"));
        assembleBtn.setToolTipText("Assemble code");
        assembleBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        assembleBtn.setPreferredSize(new java.awt.Dimension(30, 25));
        add(assembleBtn);

        runBtn.setIcon(new ImageIcon("C:\\Users\\admin\\Downloads\\cc_mono_icon_set\\cc\\black\\png\\playback_play_icon&16.png"));
        runBtn.setToolTipText("Run code");
        runBtn.setPreferredSize(new java.awt.Dimension(30, 25));
        add(runBtn);

        stopBtn.setIcon(new ImageIcon("C:\\Users\\admin\\Downloads\\cc_mono_icon_set\\cc\\black\\png\\playback_stop_icon&16.png"));
        stopBtn.setToolTipText("Stop execution");
        stopBtn.setPreferredSize(new java.awt.Dimension(30, 25));
        add(stopBtn);

        stepBtn.setIcon(new ImageIcon("C:\\Users\\admin\\Downloads\\cc_mono_icon_set\\cc\\black\\png\\redo_icon&16.png"));
        stepBtn.setToolTipText("Step forward");
        stepBtn.setPreferredSize(new java.awt.Dimension(30, 25));
        stepBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                proc.step();
            }
        });
        add(stepBtn);

        resetBtn.setIcon(new ImageIcon("C:\\Users\\admin\\Downloads\\cc_mono_icon_set\\cc\\black\\png\\refresh_icon&16.png"));
        resetBtn.setToolTipText("Reset VM state");
        resetBtn.setPreferredSize(new java.awt.Dimension(30, 25));
        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                proc.pauseExecution();
                proc.resetState();
            }
        });
        add(resetBtn);
    }// </editor-fold>                        

    // Variables declaration - do not modify                     
    private final JButton openFileBtn = new JButton(),
            saveFileBtn = new JButton(),
            deleteTextBtn = new JButton(),
            assembleBtn = new JButton(),
            runBtn = new JButton(),
            stopBtn = new JButton(),
            stepBtn = new JButton(),
            resetBtn = new JButton();

    private final JSeparator sep = new JSeparator();
    // End of variables declaration                   
}
