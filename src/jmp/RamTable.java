/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmp;

import java.awt.Color;
import java.awt.GridBagLayout;
import javax.swing.*;
import jmp.Utils;
import jmp.VMConstants;
import jmp.MainMemory;
import static jmp.VMConstants.RAM_TABLE_ROW_LEN;

/**
 *
 * @author admin
 */
public class RamTable extends JPanel {

    private final MainMemory memMgr;
    private boolean showValues;

    public RamTable(MainMemory mem) {
        this.showValues = true;
        this.memMgr = mem;
        lastGPR = new int[4];
        initComponents();
        update();
    }

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        GridBagLayout panelLayout = new GridBagLayout();
        panelLayout.columnWidths = new int[65];
        panelLayout.rowHeights = new int[65];
        for (int i = 0; i < 65; ++i) {
            panelLayout.columnWidths[i] = 4 * ((i + 1) % 2);
            panelLayout.rowHeights[i] = 2 * ((i + 1) % 2);
        }

        setLayout(panelLayout);

        // Now for the real stuff.
        locs = new JLabel[RAM_TABLE_ROW_LEN][RAM_TABLE_ROW_LEN];

        for (int row = 0; row < RAM_TABLE_ROW_LEN; row++) {
            for (int col = 0; col < RAM_TABLE_ROW_LEN; col++) {
                locs[col][row] = new JLabel();
                locs[col][row].setFont(VMConstants.SMALL_MONOSPACE_FONT);
                locs[col][row].setOpaque(true);
                locs[col][row].setBackground(showValues ? VMConstants.DEFAULT_RAM_CELL_COLOR : VMConstants.NO_TEXT_RAM_CELL_COLOR);

                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 2 * col + 1;
                gridBagConstraints.gridy = 2 * row + 1;

                add(locs[col][row], gridBagConstraints);
            }
        }
    }

    private void clearAllCells() {
        for (int row = 0; row < VMConstants.RAM_TABLE_ROW_LEN; row++) {
            for (int col = 0; col < VMConstants.RAM_TABLE_COL_LEN; col++) {
                locs[col][row].setBackground(showValues ? VMConstants.DEFAULT_RAM_CELL_COLOR : VMConstants.NO_TEXT_RAM_CELL_COLOR);
            }
        }
    }

    void update() {
        int count = -1;
        Opcode op;
        int addr;
        int byteAtPos;
        for (int row = 0; row < RAM_TABLE_ROW_LEN; row++) {
            for (int col = 0; col < RAM_TABLE_ROW_LEN; col++) {
                locs[col][row].setText(showValues ? Utils.hex(memMgr.getRamByte(col, row)) : VMConstants.RAM_EMPTY_STRING);
                addr = row * RAM_TABLE_ROW_LEN + col;
                byteAtPos = memMgr.getRamByte(addr);
                op = OpcodeData.opcodeOf(byteAtPos);
                
                if(count >= 0) {
                    count--;
                }
                
                if(!(op.isEqualTo(Opcode.INVALID) || op.isEqualTo(Opcode.NONE)) && (count == -1)) {
                    locs[col][row].setToolTipText("addr " + Utils.hex(addr) + " (" + addr + ")" + ", opcode " + op.toString());
                    count = op.getArity();
                } else {
                    locs[col][row].setToolTipText("addr " + Utils.hex(addr) + " (" + addr + ")");
                }
            }
        }
        clearAllCells();
        updateRegisterCellColors();
    }

    void toggleShowValues() {
        showValues = !showValues;
        update();
    }

    void setRamByte(int addr, int val) {
        locs[addr % RAM_TABLE_ROW_LEN][addr / RAM_TABLE_ROW_LEN].setText(showValues ? Utils.hex(val) : VMConstants.RAM_EMPTY_STRING);
    }

    void setRamByte(int c, int r, int val) {
        locs[c][r].setText(Utils.hex(val));
    }

    void setCellColor(int addr, Color row) {
        locs[addr % RAM_TABLE_ROW_LEN][addr / RAM_TABLE_ROW_LEN].setBackground(row);
    }

    void updateRegisterCellColors() {
        setCellColor(lastIP, showValues ? VMConstants.DEFAULT_RAM_CELL_COLOR : VMConstants.NO_TEXT_RAM_CELL_COLOR);
        setCellColor(lastSP, showValues ? VMConstants.DEFAULT_RAM_CELL_COLOR : VMConstants.NO_TEXT_RAM_CELL_COLOR);
        for (int i = 0; i < VMConstants.NUM_GPR; i++) {
            setCellColor(lastGPR[i], showValues ? VMConstants.DEFAULT_RAM_CELL_COLOR : VMConstants.NO_TEXT_RAM_CELL_COLOR);
        }

        setCellColor(memMgr.getSP(), VMConstants.SP_COLOR);
        for (int i = 0; i < VMConstants.NUM_GPR; i++) {
            setCellColor(memMgr.getRegisterAddr(i), VMConstants.GPR_COLOR[i]);
        }
        // IP has highest priority
        setCellColor(memMgr.getIP(), VMConstants.IP_COLOR);

        lastSP = memMgr.getSP();
        lastIP = memMgr.getIP();
        for (int i = 0; i < VMConstants.NUM_GPR; i++) {
            lastGPR[i] = memMgr.getRegisterAddr(i);
        }
    }

    private final int[] lastGPR;
    private int lastSP;
    private int lastIP;
    JLabel[][] locs;
}
