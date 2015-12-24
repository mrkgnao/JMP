package jmp;

import javax.swing.*;
import java.awt.*;

public class RegisterTable extends JPanel {

    private final MainMemory memMgr;
    private boolean showAsDecimal;


    // Register labels
    private JLabel[] gprText;

    private JLabel ipText;
    private JLabel spText;

    private JLabel zeroText;
    private JLabel carryText;
    private JLabel faultText;

    // Buttons
    private JButton[] gprBtn;

    private JButton ipBtn;
    private JButton spBtn;

    private JButton zeroBtn;
    private JButton carryBtn;
    private JButton faultBtn;
    /**
     * Creates new form RegisterTable
     */
    public RegisterTable(MainMemory mem) {
        this.showAsDecimal = false;
        initComponents();
        this.memMgr = mem;
        updateTable();
    }
    // UI updateTable functions -----------------------------------------------------
    // Register ---------------------------------------------------
    public void updateGPR(Register ix, int val) {
        gprBtn[ix.ordinal()].setText(Utils.hex(val));
    }
    public void updateGPR() {
        int addr;
        for (int i = 0; i < 4; i++) {
            addr = memMgr.getRegisterAddr(i);
            gprBtn[i].setText(showAsDecimal ? (addr + "") : Utils.hex(addr));
        }
    }
    // Pointers ----------------------------------------------
    public void updateIP() {
        ipBtn.setText(Utils.hex(memMgr.getIP()));
    }
    public void updateSP() {
        spBtn.setText(Utils.hex(memMgr.getSP()));
    }
    // Flags -------------------------------------------------
    public void updateZero() {
        boolean isSet = memMgr.zeroFlagSet();
        zeroBtn.setText(Utils.flagText(isSet));
        if (isSet) {
            zeroBtn.setBackground(VMConstants.FLAG_ON_COLOR);
        } else {
            zeroBtn.setBackground(VMConstants.FLAG_OFF_COLOR);
        }
    }
    public void updateCarry() {
        boolean isSet = memMgr.carryFlagSet();
        carryBtn.setText(Utils.flagText(isSet));
        if (isSet) {
            carryBtn.setBackground(VMConstants.FLAG_ON_COLOR);
        } else {
            carryBtn.setBackground(VMConstants.FLAG_OFF_COLOR);
        }
    }
    public void updateFault() {
        boolean isSet = memMgr.faultFlagSet();
        faultBtn.setText(Utils.flagText(isSet));
        if (isSet) {
            faultBtn.setBackground(VMConstants.FLAG_ON_COLOR);
        } else {
            faultBtn.setBackground(VMConstants.FLAG_OFF_COLOR);
        }
    }
    // Nuke it all -------------------------------------------
    public void updateTable() {
        updateGPR();
        updateIP();
        updateSP();
        updateCarry();
        updateFault();
        updateZero();
    }
    // -------------------------------------------------------------------------
    // This function is a crime against humanity -------------------------------
    // -------------------------------------------------------------------------
    
    // <editor-fold defaultstate="collapsed" desc="Initialize form components">
    private void initComponents() {
        GridBagConstraints gridBagConstraints;

        ipBtn = new JButton();
        ipText = new JLabel();
        faultBtn = new JButton();
        faultText = new JLabel();
        carryText = new JLabel();
        carryBtn = new JButton();
        zeroBtn = new JButton();
        zeroText = new JLabel();
        spText = new JLabel();
        spBtn = new JButton();

        gprBtn = new JButton[4];
        gprText = new JLabel[4];

        for (int i = 0; i < 4; i++) {
            gprText[i] = new JLabel();
            gprBtn[i] = new JButton();
            gprBtn[i].setEnabled(false);
            gprBtn[i].setContentAreaFilled(false);
            gprBtn[i].setOpaque(true);
            gprBtn[i].setBackground(VMConstants.GPR_COLOR[i]);
            gprBtn[i].setForeground(Color.BLACK);
            gprBtn[i].setFont(VMConstants.BIG_MONOSPACE_FONT);
        }

        ipBtn.setEnabled(false);
        ipBtn.setContentAreaFilled(false);
        ipBtn.setOpaque(true);
        ipBtn.setBackground(VMConstants.IP_COLOR);
        ipBtn.setForeground(Color.BLACK);
        ipBtn.setFont(VMConstants.BIG_MONOSPACE_FONT);

        spBtn.setEnabled(false);
        spBtn.setContentAreaFilled(false);
        spBtn.setOpaque(true);
        spBtn.setBackground(VMConstants.SP_COLOR);
        spBtn.setForeground(Color.BLACK);
        spBtn.setFont(VMConstants.BIG_MONOSPACE_FONT);

        zeroBtn.setContentAreaFilled(false);
        zeroBtn.setOpaque(true);
        zeroBtn.setEnabled(false);
        zeroBtn.setForeground(Color.BLACK);
        zeroBtn.setBackground(Color.LIGHT_GRAY);
        zeroBtn.setFont(VMConstants.BIG_MONOSPACE_FONT);

        carryBtn.setContentAreaFilled(false);
        carryBtn.setOpaque(true);
        carryBtn.setEnabled(false);
        carryBtn.setForeground(Color.BLACK);
        carryBtn.setBackground(Color.LIGHT_GRAY);
        carryBtn.setFont(VMConstants.BIG_MONOSPACE_FONT);

        faultBtn.setContentAreaFilled(false);
        faultBtn.setOpaque(true);
        faultBtn.setEnabled(false);
        faultBtn.setForeground(Color.BLACK);
        faultBtn.setBackground(Color.LIGHT_GRAY);
        faultBtn.setFont(VMConstants.BIG_MONOSPACE_FONT);

        setLayout(new GridBagLayout());

        gprText[1].setHorizontalAlignment(SwingConstants.CENTER);
        gprText[1].setText("B");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 51;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.insets = new Insets(0, 6, 0, 0);
        add(gprText[1], gridBagConstraints);

        gprBtn[1].setText("0000");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.insets = new Insets(6, 6, 0, 0);
        add(gprBtn[1], gridBagConstraints);

        gprBtn[2].setText("0000");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.insets = new Insets(6, 6, 0, 0);
        add(gprBtn[2], gridBagConstraints);

        gprText[2].setHorizontalAlignment(SwingConstants.CENTER);
        gprText[2].setText("C");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.insets = new Insets(0, 6, 0, 0);
        add(gprText[2], gridBagConstraints);

        ipBtn.setText("0000");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.insets = new Insets(6, 18, 0, 0);
        add(ipBtn, gridBagConstraints);

        ipText.setHorizontalAlignment(SwingConstants.CENTER);
        ipText.setText("IP");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 47;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.insets = new Insets(0, 18, 0, 0);
        add(ipText, gridBagConstraints);

        gprText[3].setHorizontalAlignment(SwingConstants.CENTER);
        gprText[3].setText("D");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.insets = new Insets(0, 6, 0, 0);
        add(gprText[3], gridBagConstraints);

        gprBtn[3].setText("0000");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.insets = new Insets(6, 6, 0, 0);
        add(gprBtn[3], gridBagConstraints);

        faultBtn.setText(Utils.flagText(false));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.insets = new Insets(6, 6, 0, 0);
        add(faultBtn, gridBagConstraints);

        faultText.setHorizontalAlignment(SwingConstants.CENTER);
        faultText.setText("F");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 51;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.insets = new Insets(0, 6, 0, 0);
        add(faultText, gridBagConstraints);

        carryText.setHorizontalAlignment(SwingConstants.CENTER);
        carryText.setText("C");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.insets = new Insets(0, 6, 0, 0);
        add(carryText, gridBagConstraints);

        carryBtn.setText(Utils.flagText(false));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.insets = new Insets(6, 6, 0, 0);
        add(carryBtn, gridBagConstraints);

        zeroBtn.setText(Utils.flagText(false));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.insets = new Insets(6, 18, 0, 0);
        add(zeroBtn, gridBagConstraints);

        zeroText.setHorizontalAlignment(SwingConstants.CENTER);
        zeroText.setText("Z");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 51;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.insets = new Insets(0, 18, 0, 0);
        add(zeroText, gridBagConstraints);

        spText.setHorizontalAlignment(SwingConstants.CENTER);
        spText.setText("SP");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 45;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.insets = new Insets(0, 6, 0, 0);
        add(spText, gridBagConstraints);

        spBtn.setText("0000");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.insets = new Insets(6, 6, 0, 0);
        add(spBtn, gridBagConstraints);

        gprText[0].setHorizontalAlignment(SwingConstants.CENTER);
        gprText[0].setText("A");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        add(gprText[0], gridBagConstraints);

        gprBtn[0].setText("0000");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.insets = new Insets(6, 0, 0, 0);
        add(gprBtn[0], gridBagConstraints);
    }// </editor-fold>

    void toggleShowAsDecimal() {
        showAsDecimal = !showAsDecimal;
    }
}
