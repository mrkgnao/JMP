/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmp;

import java.util.Arrays;
import java.util.List;
import static jmp.VMConstants.RAM_TABLE_ROW_LEN;

/**
 *
 * @author admin
 */
public class MainMemory {

    int[][] mem;

    private int[] registers; // the 5th element is the SP
    private int ip;
    private boolean z, c, f;

    private RamTable ram;
    private RegisterTable reg;

    public MainMemory() {
        mem = new int[RAM_TABLE_ROW_LEN][RAM_TABLE_ROW_LEN];

        registers = new int[]{0, 0, 0, 0, 0};

        ip = 0;

        z = false;
        c = false;
        f = false;

        // This is the important bit
        this.ram = new RamTable(this);
        this.reg = new RegisterTable(this);
    }

    void resetAll() {
        mem = new int[RAM_TABLE_ROW_LEN][RAM_TABLE_ROW_LEN];
        registers = new int[]{0, 0, 0, 0, 0};

        ip = 0;

        z = false;
        c = false;
        f = false;

        ram.update();
        reg.updateTable();
    }

    void resetRam() {
        mem = new int[RAM_TABLE_ROW_LEN][RAM_TABLE_ROW_LEN];
        ram.update();
    }

    void resetRegisters() {
        registers = new int[]{0, 0, 0, 0, 0};

        ip = 0;

        z = false;
        c = false;
        f = false;

        reg.updateTable();
        ram.update();
    }

    public void load(List<Integer> arr) {
        initializeFromArray(arr);
        ram.update();
    }

    private void initializeFromArray(List<Integer> arr) {
        if (arr.size() > VMConstants.NUM_ADDRS) {
            System.out.println("Error: don\'t have that much memory!");
        } else {
            for (int i = 0; i < arr.size(); i++) {
                this.setRamByte(i, arr.get(i));
            }
        }
    }

    public RamTable ramTable() {
        return ram;
    }

    public RegisterTable registerTable() {
        return reg;
    }

    // -------------------------------------------------------------------------
    // RAM methods -------------------------------------------------------------
    // -------------------------------------------------------------------------
    void setRamByte(int addr, int val) {
        val = Utils.toValue(val);
        mem[addr % RAM_TABLE_ROW_LEN][addr / RAM_TABLE_ROW_LEN] = val;
        ram.setRamByte(addr, val);
    }

    void setRamByte(int r, int c, int val) {
        val = Utils.toValue(val);
        mem[r][c] = val;
        ram.setRamByte(r, c, val);
    }

    int getRamByte(int r, int c) {
        return mem[r][c];
    }

    int getRamByte(int addr) {
        addr = Utils.toAddr(addr);
        return mem[addr % RAM_TABLE_ROW_LEN][addr / RAM_TABLE_ROW_LEN];
    }

    /* 
     * -------------------------------------------------------------------------
     * Register-related methods ------------------------------------------------
     * -------------------------------------------------------------------------
     */
    // General-purpose registers *and SP*
    public int getRegisterAddr(int i) {
        return registers[i];
    }

    public int getRegisterAddr(Register g) {
        return registers[g.toId()];
    }

    /** 
     * This is surprisingly useful.
     */
    public int getRegisterOffsetAddr(int i, int offset) {
        return registers[i] + offset;
    }

    public int getRegisterOffsetAddr(Register g, int offset) {
        return registers[g.toId()] + offset;
    }

    public int getRegisterValue(int i) {
        return getRamByte(registers[i]);
    }

    public int getRegisterValue(Register g) {
        return getRamByte(registers[g.toId()]);
    }

    public int getRegisterOffsetValue(int i, int offset) {
        return getRamByte(registers[i] + offset);
    }

    public int getRegisterOffsetValue(Register g, int offset) {
        return getRamByte(registers[g.toId()] + offset);
    }

    public void setRegisterAddr(int ix, int val) {
        this.registers[ix] = Utils.toAddr(val);
        reg.updateTable();
        ram.updateRegisterCellColors();
    }

    public void setRegisterAddr(Register g, int val) {
        this.registers[g.toId()] = Utils.toAddr(val);
        reg.updateTable();
        ram.updateRegisterCellColors();
    }

    public void setRegisterValue(int ix, int val) {
        setRamByte(registers[ix], Utils.toValue(val));
    }

    public void setRegisterValue(Register g, int val) {
        setRamByte(registers[g.toId()], Utils.toValue(val));
    }

    // Pointers ----------------------------------------------
    public int getIP() {
        return ip;
    }

    public int getIPValue() {
        return getRamByte(ip);
    }

    public int getIPOffsetValue(int offset) {
        return getRamByte(ip + offset);
    }

    public void setIP(int ip) {
        this.ip = Utils.toAddr(ip);
        reg.updateIP();
        ram.updateRegisterCellColors();
    }

    public void setIPValue(int val) {
        setRamByte(ip, val);
    }

    public void incIP() {
        ip = Utils.toAddr(ip + 1);
        reg.updateIP();
        ram.updateRegisterCellColors();
    }

    public void incIPBy(int inc) {
        ip = Utils.toAddr(ip + inc);
        reg.updateIP();
        ram.updateRegisterCellColors();
    }

    public void decIP() {
        ip = Utils.toAddr(ip - 1);
        reg.updateIP();
        ram.updateRegisterCellColors();
    }

    public int getSP() {
        return registers[VMConstants.NUM_GPR];
    }

    public int getSPValue() {
        return getRamByte(registers[VMConstants.NUM_GPR]);
    }

    public void setSP(int sp) {
        this.registers[VMConstants.NUM_GPR] = Utils.toAddr(sp);
        reg.updateSP();
        ram.updateRegisterCellColors();
    }

    public void setSPValue(int val) {
        setRamByte(registers[VMConstants.NUM_GPR], val);
    }

    public void incSP() {
        registers[VMConstants.NUM_GPR] = Utils.toAddr(registers[VMConstants.NUM_GPR] + 1);
        reg.updateTable();
        ram.updateRegisterCellColors();
    }

    public void decSP() {
        registers[VMConstants.NUM_GPR] = Utils.toAddr(registers[VMConstants.NUM_GPR] - 1);
        reg.updateSP();
        ram.updateRegisterCellColors();
    }

    // Flags -------------------------------------------------
    public boolean zeroFlagSet() {
        return z;
    }

    public void setZ(boolean z) {
        this.z = z;
        reg.updateTable();
    }

    public void flipZ() {
        this.z = !z;
        reg.updateTable();
    }

    public boolean carryFlagSet() {
        return c;
    }

    public void setC(boolean c) {
        this.c = c;
        reg.updateTable();
    }

    public void flipC() {
        this.c = !c;
        reg.updateTable();
    }

    public boolean faultFlagSet() {
        return f;
    }

    public void setF(boolean f) {
        this.f = f;
        reg.updateTable();
    }

    public void flipF() {
        this.f = !f;
        reg.updateTable();
    }
}
