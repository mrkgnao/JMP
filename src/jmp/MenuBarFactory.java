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
            clockSpeedPopup = new JMenu("Clock speed"),
            loadSample = new JMenu("Load sample program");

    private final JMenuItem regreg = new JMenuItem(),
            regval = new JMenuItem(),
            addrval = new JMenuItem(),
            flipCarryFlag = new JMenuItem(),
            incrementSP = new JMenuItem(),
            exit = new JMenuItem(),
            showAsDecimal = new JCheckBoxMenuItem("Show register values in decimal"),
            askOnExit = new JCheckBoxMenuItem(),
            whatIsAllThis = new JMenuItem(),
            showAbout = new JMenuItem(),
            pauseWhenFocusLost = new JCheckBoxMenuItem("Pause execution when window loses focus"),
            dumpCode = new JMenuItem();


    private JRadioButtonMenuItem selectedClockSpeed = new JRadioButtonMenuItem();
    
    private final JMenuItem[] sampleProgramList;

    private final ButtonGroup clockSpeedGroup = new ButtonGroup();

    private final JRadioButtonMenuItem[] clockSpeeds
            = new JRadioButtonMenuItem[VMConstants.NUM_CLOCK_SPEEDS];
    private final JRadioButtonMenuItem clockSpeedCustom
            = new JRadioButtonMenuItem("Custom speed");

    private final JMenuItem showRamValues = new JCheckBoxMenuItem();

    private final Processor proc;
    private final MainUI parent;

    public MenuBarFactory(Processor proc, MainUI ui) {
        this.proc = proc;
        this.parent = ui;
        this.sampleProgramList = new JMenuItem[SamplePrograms.NUM_SAMPLE_PROGRAMS];
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
        bar.initMenuBar();
        return bar.menuBar;
    }

    private void initMenuBar() {
        setupFileMenu();
        setupDebugMenu();
        setupPrefsMenu();
        setupVmMenu();
        setupHelpMenu();
    }

    private void setupPrefsMenu() {
        pauseWhenFocusLost.setSelected(false);
        pauseWhenFocusLost.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.flipPauseOnLostFocus();
            }
        });
        
        showRamValues.setText("Show RAM cell values");
        showRamValues.setSelected(true);
        showRamValues.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                proc.mainMemory().ramTable().toggleShowValues();
            }
        });

        showAsDecimal.setSelected(false);
        showAsDecimal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                proc.mainMemory().registerTable().toggleShowAsDecimal();
            }
        });
        
        prefsMenu.add(pauseWhenFocusLost);
        prefsMenu.addSeparator();
        prefsMenu.add(showRamValues);
        prefsMenu.add(showAsDecimal);

        menuBar.add(prefsMenu);
    }

    private void setupFileMenu() {
        for (int i = 0; i < sampleProgramList.length; i++) {
            final BinaryProgram prog = SamplePrograms.PROGRAMS.get(i);
            sampleProgramList[i] = new JMenuItem(prog.name);
            sampleProgramList[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    proc.load(prog);
                }
            });

            loadSample.add(sampleProgramList[i]);
        }

        askOnExit.setText("Ask for exit confirmation");
        askOnExit.setSelected(true);

        exit.setText("Kill VM and exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                parent.flipAskOnExit();
                parent.doControlledExit();
            }
        });
        exit.setAccelerator(KeyStroke.getKeyStroke('Q', InputEvent.CTRL_DOWN_MASK));

        fileMenu.add(loadSample);
        fileMenu.addSeparator();
        fileMenu.add(askOnExit);
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

        dumpCode.setText("Dump assembled code to console");
        dumpCode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                System.out.println(parent.getAssembledCode());
            }
        });
        debugMenu.add(dumpCode);

        // Set keyboard shortcuts
        flipCarryFlag.setAccelerator(KeyStroke.getKeyStroke('K', InputEvent.CTRL_DOWN_MASK));
        incrementSP.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        dumpCode.setAccelerator(KeyStroke.getKeyStroke('D', InputEvent.ALT_DOWN_MASK));

        menuBar.add(debugMenu);
    }

    private void setSelectedClockSpeed(JRadioButtonMenuItem item) {
        this.selectedClockSpeed.setSelected(false);
        this.selectedClockSpeed = item;
        item.setSelected(true);
    }
    
    private void setupVmMenu() {
        for (int i = 0; i < VMConstants.NUM_CLOCK_SPEEDS; i++) {
            final int speed = VMConstants.CLOCK_SPEEDS[i];
            clockSpeeds[i] = new JRadioButtonMenuItem(speed / 1000.0 + " Hz");
            
            final JRadioButtonMenuItem item = clockSpeeds[i];
            
            clockSpeeds[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    proc.changeClockSpeed(speed);
                    setSelectedClockSpeed(item);
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
                String input = "";
                String msg = "Enter the new clock speed in millihertz: ";
                String error = "You entered an invalid input.\n";
                boolean failed = false, isNum = false;
                int newClock = 0;
                
                while (failed || !isNum) {
                    input = JOptionPane.showInputDialog(
                            failed ? (error + msg)
                                    : msg);
                    try {
                        isNum = Utils.isNumeric(input);
                    } catch (NullPointerException ex) {
                        // User must have pressed cancel
                        setSelectedClockSpeed(selectedClockSpeed);
                        return;
                    }

                    try {
                        if (input == "" || !isNum) {
                            failed = true;
                        }
                        newClock = Integer.parseInt(input);
                        setSelectedClockSpeed(clockSpeedCustom);
                        proc.changeClockSpeed(newClock);
                    } catch (Exception ex) {
                        failed = true;
                        setSelectedClockSpeed(selectedClockSpeed);
                    }
                };
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
