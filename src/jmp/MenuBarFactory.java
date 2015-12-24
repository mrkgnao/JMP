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
    
    private final JMenu fileMenu = new JMenu("File"),
            helpMenu = new JMenu("Halp"),
            debugMenu = new JMenu("Debug"),
            prefsMenu = new JMenu("Prefs"),
            vmMenu = new JMenu("VM"),
            clockSpeedPopup = new JMenu("Clock speed");
    
    private final JMenuItem regreg = new JMenuItem(),
            regval = new JMenuItem(),
            addrval = new JMenuItem(),
            flipCarryFlag = new JMenuItem(),
            incrementSP = new JMenuItem(),
            exit = new JMenuItem(),
            askOnExit = new JCheckBoxMenuItem(),
            whatIsAllThis = new JMenuItem(),
            showAbout = new JMenuItem(),
            dumpCode = new JMenuItem();
    
    private final ButtonGroup clockSpeedGroup = new ButtonGroup();
    
    private final JRadioButtonMenuItem[] clockSpeeds
            = new JRadioButtonMenuItem[VMConstants.NUM_CLOCK_SPEEDS];
    private final JRadioButtonMenuItem clockSpeedCustom
            = new JRadioButtonMenuItem("Custom speed");
    
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
        bar.setupializeMenuBar();
        return bar.menuBar;
    }
    
    private void setupializeMenuBar() {
        setupFileMenu();
        setupDebugMenu();
        setupPrefsMenu();
        setupVmMenu();
        setupHelpMenu();
    }
    
    private void setupPrefsMenu() {
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
    
    private void setupFileMenu() {
        askOnExit.setText("Ask for exit confirmation");
        askOnExit.setSelected(true);
        fileMenu.add(askOnExit);
        
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
        exit.setAccelerator(KeyStroke.getKeyStroke('Q', InputEvent.CTRL_DOWN_MASK));
        fileMenu.add(exit);        
        
        menuBar.add(fileMenu);
    }
    
    private void setupDebugMenu() {
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
        
        dumpCode.setText("Dump code to console");
        dumpCode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                System.out.println(ui.codeArea.getText());
            }
        });
        debugMenu.add(dumpCode);

        // Set keyboard shortcuts
        flipCarryFlag.setAccelerator(KeyStroke.getKeyStroke('K', InputEvent.CTRL_DOWN_MASK));
        incrementSP.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        dumpCode.setAccelerator(KeyStroke.getKeyStroke('D', InputEvent.ALT_DOWN_MASK));
        
        menuBar.add(debugMenu);
    }
    
    private void setupVmMenu() {
        for (int i = 0; i < VMConstants.NUM_CLOCK_SPEEDS; i++) {
            final int speed = VMConstants.CLOCK_SPEEDS[i];
            clockSpeeds[i] = new JRadioButtonMenuItem(speed / 1000.0 + " Hz");
            clockSpeeds[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    proc.changeClockSpeed(speed);
                }
            });
            if (speed == 4000) {
                clockSpeeds[i].setSelected(true);
            }
            clockSpeedGroup.add(clockSpeeds[i]);
            clockSpeedPopup.add(clockSpeeds[i]);
        }
        
        clockSpeedCustom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input;
                boolean failed = false, isNum = false;
                do {
                    input = JOptionPane.showInputDialog(
                            (failed ? "You entered an invalid input" : "")
                            + "Enter the new clock speed in millihertz");
                    isNum = Utils.isNumeric(input);
                    if (!isNum) {
                        failed = true;
                    }
                } while (!isNum);
                proc.changeClockSpeed(Integer.parseInt(input));
            }
        });
        clockSpeedGroup.add(clockSpeedCustom);
        clockSpeedPopup.add(clockSpeedCustom);
        
        vmMenu.add(clockSpeedPopup);
        
        menuBar.add(vmMenu);
    }
    
    private void setupHelpMenu() {
        whatIsAllThis.setText("What is all this?");
        whatIsAllThis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        helpMenu.add(whatIsAllThis);
        
        showAbout.setText("About JMP");
        showAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dialogs.showAboutDialog();
            }
        });
        helpMenu.add(showAbout);
        
        menuBar.add(helpMenu);
    }
}
