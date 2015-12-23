/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import javax.swing.*;
import jmp.Register;
import jmp.Processor;

/**
 *
 * @author admin
 */
public class MenuBarFactory {

    private final JMenuBar menuBar = new JMenuBar();

    private final JMenu debugMenu = new JMenu(),
            prefsMenu = new JMenu(),
            vmMenu = new JMenu("VM"),
            clockSpeedPopup = new JMenu("Clock speed");

    private final JMenuItem regreg = new JMenuItem(),
            regval = new JMenuItem(),
            addrval = new JMenuItem(),
            flipCarryFlag = new JMenuItem(),
            incrementSP = new JMenuItem(),
            exit = new JMenuItem(),
            askOnExit = new JCheckBoxMenuItem(),
            dumpCode = new JMenuItem();

    private final ButtonGroup clockSpeedGroup = new ButtonGroup();
    
    private final JRadioButtonMenuItem 
            clockSpeed1Hz = new JRadioButtonMenuItem("1 Hz"),
            clockSpeed2Hz = new JRadioButtonMenuItem("2 Hz"),
            clockSpeed4Hz = new JRadioButtonMenuItem("4 Hz"),
            clockSpeed8Hz = new JRadioButtonMenuItem("8 Hz"),
            clockSpeed16Hz = new JRadioButtonMenuItem("16 Hz"),
            clockSpeed32Hz = new JRadioButtonMenuItem("32 Hz"),
            clockSpeedCustom = new JRadioButtonMenuItem("Custom speed");

    private final JMenuItem showRamValues = new JCheckBoxMenuItem();

    private final Processor proc;
    private final MainUI ui;

    public MenuBarFactory(Processor proc, MainUI ui) {
        this.proc = proc;
        this.ui = ui;
    }

    /**
     * Creates new form MenuBarFactory
     *
     * @param proc
     * @param ui
     *
     * @return The initialized JMenuBar.
     */
    public static JMenuBar createMenuBar(Processor proc, MainUI ui) {
        MenuBarFactory bar = new MenuBarFactory(proc, ui);
        bar.initializeMenuBar();
        return bar.menuBar;
    }

    private void initializeMenuBar() {
        initDebugMenu();
        initPrefsMenu();
        initVmMenu();
    }

    private void initPrefsMenu() {
        prefsMenu.setText("Prefs");

        showRamValues.setText("Show RAM cell values");
        showRamValues.setSelected(true);
        showRamValues.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                proc.mainMemory().ramTable().toggleShowValues();
            }
        });

        prefsMenu.add(showRamValues);

        menuBar.add(prefsMenu);
    }

    private void initDebugMenu() {
        debugMenu.setText("Debug");

        regreg.setText("Do a reg-reg MOV (set C to D)");
        regreg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                proc.movRR(Register.C, Register.D);
            }
        });
        debugMenu.add(regreg);

        regval.setText("Do a reg-val MOV (set D to 0xff)");
        regval.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                proc.movRV(Register.D, 0xff);
            }
        });
        debugMenu.add(regval);

        addrval.setText("Do an addr-val MOV (set 0xcf to 0xffff)");
        addrval.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                proc.movAV(0xcf, 0xffff);
            }
        });
        debugMenu.add(addrval);

        debugMenu.addSeparator();

        flipCarryFlag.setText("Flip the carry flag");
        flipCarryFlag.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                proc.mainMemory().flipC();
            }
        });
        debugMenu.add(flipCarryFlag);

        incrementSP.setText("Increment stack pointer");
        incrementSP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                proc.mainMemory().incSP();
            }
        });
        debugMenu.add(incrementSP);

        debugMenu.addSeparator();

        askOnExit.setText("Ask for exit confirmation");
        askOnExit.setSelected(true);
        debugMenu.add(askOnExit);

        debugMenu.addSeparator();

        dumpCode.setText("Dump code to console");
        dumpCode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                System.out.println(ui.codeArea.getText());
            }
        });
        debugMenu.add(dumpCode);

        exit.setText("Kill VM and exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (!askOnExit.isSelected() || JOptionPane.showConfirmDialog(null,
                        "Are you really sure, etc. etc. about this?",
                        "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION) {
                    System.out.println("Exiting with status 0.");
                    System.exit(0);
                }
            }
        });
        debugMenu.add(exit);

        // Set keyboard shortcuts
        flipCarryFlag.setAccelerator(KeyStroke.getKeyStroke('K', InputEvent.CTRL_DOWN_MASK));
        incrementSP.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        exit.setAccelerator(KeyStroke.getKeyStroke('Q', InputEvent.CTRL_DOWN_MASK));
        dumpCode.setAccelerator(KeyStroke.getKeyStroke('D', InputEvent.ALT_DOWN_MASK));

        menuBar.add(debugMenu);
    }

    private void initVmMenu() {
        clockSpeed4Hz.setSelected(true);
        
        clockSpeedGroup.add(clockSpeed1Hz);
        clockSpeedGroup.add(clockSpeed2Hz);
        clockSpeedGroup.add(clockSpeed4Hz);
        clockSpeedGroup.add(clockSpeed8Hz);
        clockSpeedGroup.add(clockSpeed16Hz);
        clockSpeedGroup.add(clockSpeed32Hz);
        clockSpeedGroup.add(clockSpeedCustom);
        
        clockSpeedPopup.add(clockSpeed1Hz);
        clockSpeedPopup.add(clockSpeed2Hz);
        clockSpeedPopup.add(clockSpeed4Hz);
        clockSpeedPopup.add(clockSpeed8Hz);
        clockSpeedPopup.add(clockSpeed16Hz);
        clockSpeedPopup.add(clockSpeed32Hz);
        clockSpeedPopup.add(clockSpeedCustom);
        
        vmMenu.add(clockSpeedPopup);
        
        menuBar.add(vmMenu);
    }
}
