package jmp;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author admin
 */
public class Processor {

    private final MainMemory mem;
    private boolean executing;

    public boolean isExecuting() {
        return executing;
    }
    
    private final MainUI parent;
    private int clockSpeedMilliHz = 4000;

    ScheduledExecutorService periodicRunner = Executors.newScheduledThreadPool(5);
    ScheduledFuture<Object> task;

    public Processor(MainUI parent) {
        this.mem = new MainMemory();
        this.parent = parent;
    }

    public MainMemory mainMemory() {
        return mem;
    }
    
    public void load(Program prog) {
        parent.setTitle(VMConstants.DEFAULT_WINDOW_TITLE + " - " + prog.name);
        mem.load(prog.code);
    }

    /*
     * Execution. 
     */
    void step() {
        int ipVal = mem.getIPValue();
        Opcode op = OpcodeData.opcodeOf(ipVal);
        Register dest, src;
        int arg, val1, val2, destOffset, offset, destAddr;
        switch (op) {
            case NONE:
                System.out.println("Encountered " + op.toString() + ".");
                mem.incIP(); // TODO sure?
                break;

            /*
             * --------------------
             * MOVs of every flavor
             * --------------------
             */
            case MOV_R_R:
                dest = Register.toRegister(mem.getIPOffsetValue(1));
                src = Register.toRegister(mem.getIPOffsetValue(2));
                movRR(dest, src);
                mem.incIPBy(3);
                System.out.println("MOV " + dest.toString() + ", " + src.toString());
                break;
            case MOV_R_O:
                dest = Register.toRegister(mem.getIPOffsetValue(1));
                // The first three (is it? not sure) bits store the source register
                src = Register.toRegister(mem.getIPOffsetValue(2) % 8);
                offset = (mem.getIPOffsetValue(2) / 8) - 4095;
                movRO(dest, src, offset);
                mem.incIPBy(3);
                System.out.println("MOV " 
                        + dest 
                        + ", [" 
                        + src + (offset < 0 ? "" : "+") + offset 
                        + "]");
                break;
            case MOV_R_A:
                dest = Register.toRegister(mem.getIPOffsetValue(1));
                arg = mem.getIPOffsetValue(2);
                movRA(dest, arg);
                mem.incIPBy(3);
                System.out.println("MOV " 
                        + dest 
                        + ", " 
                        + Utils.hex(arg));
                break;
            case MOV_R_V:
                dest = Register.toRegister(mem.getIPOffsetValue(1));
                arg = mem.getIPOffsetValue(2);
                movRV(dest, arg);
                mem.incIPBy(3);
                System.out.println("MOV " 
                        + dest 
                        + ", " 
                        + Utils.hex(arg));
                break;
            case MOV_A_R:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case MOV_A_O:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case MOV_A_A:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case MOV_A_V:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case MOV_O_R:
                dest = Register.toRegister(mem.getIPOffsetValue(1) % 8);
                destOffset = (mem.getIPOffsetValue(1) / 8) - 4095;
                destAddr = mem.getRegisterAddr(dest) + destOffset;
                
                src = Register.toRegister(mem.getIPOffsetValue(2));                
                movAR(destAddr, src);
                
                mem.incIPBy(3);
                System.out.println("MOV [" 
                        + dest + (destOffset < 0 ? "" : "+") + destOffset 
                        + "], " + src.toString());
                break;
            case MOV_O_O:
                dest = Register.toRegister(mem.getIPOffsetValue(1) % 8);
                destOffset = (mem.getIPOffsetValue(1) / 8) - 4095;
                destAddr = mem.getRegisterAddr(dest) + destOffset;
                
                src = Register.toRegister(mem.getIPOffsetValue(2) % 8);
                offset = (mem.getIPOffsetValue(2) / 8) - 4095;
                movAO(destAddr, src, offset);
                
                mem.incIPBy(3);
                System.out.println("MOV [" 
                        + dest + (destOffset < 0 ? "" : "+") + destOffset 
                        + "], [" 
                        + src + (offset < 0 ? "" : "+") + offset 
                        + "]");
                break;
            case MOV_O_A:                
                dest = Register.toRegister(mem.getIPOffsetValue(1) % 8);
                destOffset = (mem.getIPOffsetValue(1) / 8) - 4095;
                destAddr = mem.getRegisterAddr(dest) + destOffset;
                
                arg = mem.getIPOffsetValue(2);
                
                movAA(destAddr, arg);
                mem.incIPBy(3);
                System.out.println("MOV [" 
                        + dest + (destOffset < 0 ? "" : "+") + destOffset 
                        + "], " + Utils.hex(arg));
                break;
            case MOV_O_V:                
                dest = Register.toRegister(mem.getIPOffsetValue(1) % 8);
                destOffset = (mem.getIPOffsetValue(1) / 8) - 4095;
                destAddr = mem.getRegisterAddr(dest) + destOffset;
                
                arg = mem.getIPOffsetValue(2);
                movAV(destAddr, arg);
                
                mem.incIPBy(3);
                System.out.println("MOV [" 
                        + dest + (destOffset < 0 ? "" : "+") + destOffset 
                        + "], " + Utils.hex(arg));
                break;

            /*
             * ------------
             * Math opcodes
             * ------------
             */
 /*
             * Addition opcodes
             */
            case ADD_R_R:
                dest = Register.toRegister(mem.getIPOffsetValue(1));
                src = Register.toRegister(mem.getIPOffsetValue(2));
                addRR(dest, src);
                mem.incIPBy(3);
                System.out.println("ADD " + dest.toString() + ", " + src.toString());
                break;
            case ADD_R_O:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case ADD_R_A:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case ADD_R_V:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case ADD_A_R:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case ADD_A_O:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case ADD_A_A:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case ADD_A_V:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case ADD_O_R:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case ADD_O_O:
                dest = Register.toRegister(mem.getIPOffsetValue(1) % 8);
                destOffset = (mem.getIPOffsetValue(1) / 8) - 4095;
                destAddr = mem.getRegisterOffsetAddr(dest, destOffset);
                
                src = Register.toRegister(mem.getIPOffsetValue(2) % 8);
                offset = (mem.getIPOffsetValue(2) / 8) - 4095;
                addAO(destAddr, src, offset);
                
                mem.incIPBy(3);
                System.out.println("ADD [" 
                        + dest + (destOffset < 0 ? "" : "+") + destOffset 
                        + "], [" 
                        + src + (offset < 0 ? "" : "+") + offset 
                        + "]");
                break;
            case ADD_O_A:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case ADD_O_V:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case SUB_R_R:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case SUB_R_O:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case SUB_R_A:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case SUB_R_V:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case SUB_A_R:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case SUB_A_O:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case SUB_A_A:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case SUB_A_V:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case SUB_O_R:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case SUB_O_O:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case SUB_O_A:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case SUB_O_V:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case MUL_R_R:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case MUL_R_O:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case MUL_R_A:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case MUL_R_V:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case MUL_A_R:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case MUL_A_O:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case MUL_A_A:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case MUL_A_V:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case MUL_O_R:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case MUL_O_O:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case MUL_O_A:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case MUL_O_V:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case DIV_R_R:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case DIV_R_O:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case DIV_R_A:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case DIV_R_V:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case DIV_A_R:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case DIV_A_O:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case DIV_A_A:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case DIV_A_V:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case DIV_O_R:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case DIV_O_O:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case DIV_O_A:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case DIV_O_V:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case MOD_R_R:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case MOD_R_O:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case MOD_R_A:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case MOD_R_V:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case MOD_A_R:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case MOD_A_O:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case MOD_A_A:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case MOD_A_V:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case MOD_O_R:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case MOD_O_O:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case MOD_O_A:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case MOD_O_V:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case PUSH_R:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case PUSH_O:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case PUSH_A:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case PUSH_V:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case POP_R:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case POP_O:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case POP_A:
                System.out.println("Encountered " + op.toString() + "; not supported yet");
                break;
            case CMP_R_R:
                val1 = mem.getRegisterValue(Register.toRegister(mem.getIPOffsetValue(1)));
                val2 = mem.getRegisterValue(Register.toRegister(mem.getIPOffsetValue(2)));
                mem.setZ(val1 == val2);
                mem.setC(val1 < val2);
                mem.incIPBy(3);
                break;

            case JMP:
                jmp(mem.getIPOffsetValue(1));
                break;
            case JC:
                if (mem.carryFlagSet()) {
                    jmp(mem.getIPOffsetValue(1));
                }
                break;
            case JNC:
                if (!mem.carryFlagSet()) {
                    jmp(mem.getIPOffsetValue(1));
                }
                break;
            case JE:
            case JZ:
                if (mem.zeroFlagSet()) {
                    jmp(mem.getIPOffsetValue(1));
                }
                break;
            case JNE:
            case JNZ:
                if (!mem.zeroFlagSet()) {
                    jmp(mem.getIPOffsetValue(1));
                }
                break;
            case JA:
                break;
            case JAE:
                break;
            case JB:
                break;
            case JBE:
                break;
            default:
                System.out.println("Encountered unknown op of code " + ipVal);
        }
    }

    // -------------------------------------------------------------------------
    // Instructions ------------------------------------------------------------
    // -------------------------------------------------------------------------
    /* 
     * Move instructions. 
     * R = register, A = address, V = value
     * Two characters: destination (either R or A), and source (anything).
     */
    /**
     * Moves the value of a source register into the destination register.
     */
    public void movRR(Register dest, Register src) {
        mem.setRegisterAddr(dest, mem.getRegisterAddr(src));
    }
    
    /**
     * Moves the value of a source register into the destination register.
     */
    public void movRO(Register dest, Register src, int offset) {
        mem.setRegisterAddr(dest, mem.getRegisterOffsetValue(src, offset));
    }

    /**
     * Moves the value of a RAM address into the destination register.
     */
    public void movRA(Register dest, int addr) {
        mem.setRegisterAddr(dest, mem.getRamByte(addr));
    }

    /**
     * Moves a value into the destination register.
     */
    public void movRV(Register dest, int value) {
        mem.setRegisterAddr(dest, value);
    }

    /**
     * Moves the value of a source register into RAM at a specified address.
     */
    public void movAR(int dest, Register src) {
        mem.setRamByte(dest, mem.getRegisterAddr(src));
    }
    
    /**
     * Moves the value of a source register into RAM at a specified address.
     */
    public void movAO(int dest, Register src, int offset) {
        mem.setRamByte(dest, mem.getRegisterOffsetValue(src, offset));
    }

    /**
     * Moves the value of the source RAM address into the specified RAM address.
     */
    public void movAA(int dest, int addr) {
        mem.setRamByte(dest, mem.getRamByte(addr));
    }

    /**
     * Moves a value into the specified RAM address.
     */
    public void movAV(int dest, int value) {
        mem.setRamByte(dest, value);
    }
    
    /**
     * Moves the value of a source register into RAM at a specified address offset from a register.
     */
    public void movOR(int dest, int destOffset, Register src) {
        mem.setRamByte(dest + destOffset, mem.getRegisterAddr(src));
    }
    
    /**
     * Moves the value of a source register into RAM at a specified address offset.
     */
    public void movOO(int dest, int destOffset, Register src, int offset) {
        mem.setRamByte(dest + destOffset, mem.getRegisterOffsetValue(src, offset));
    }

    /**
     * Moves the value of the source RAM address into the specified register offset.
     */
    public void movOA(int dest, int destOffset, int addr) {
        mem.setRamByte(dest + destOffset, mem.getRamByte(addr));
    }

    /**
     * Moves a value into the specified RAM register offset.
     * @param dest
     * @param destOffset
     * @param value
     */
    public void movOV(int dest, int destOffset, int value) {
        mem.setRamByte(dest + destOffset, value);
    }

    /**
     * Adds the value of a source register to the destination register.
     */
    public void addRR(Register dest, Register src) {
        mem.setRegisterValue(dest, mem.getRegisterValue(dest) + mem.getRegisterValue(src));
    }

    /**
     * Adds the value of a RAM address to the destination register.
     */
    public void addRA(Register dest, int addr) {
        mem.setRegisterValue(dest, mem.getRegisterValue(dest) + mem.getRamByte(addr));
    }

    /**
     * Adds a value to the destination register.
     */
    public void addRV(Register dest, int value) {
        mem.setRegisterValue(dest, mem.getRegisterValue(dest) + value);
    }

    /**
     * Adds the value of a source register into RAM at a specified address.
     */
    public void addAR(int dest, Register src) {
        mem.setRamByte(dest, mem.getRamByte(dest) + mem.getRegisterAddr(src));
    }
    
    /**
     * Adds the value of a source register into RAM at a specified address.
     */
    public void addAO(int dest, Register src, int offset) {
        System.out.println("Setting " + Utils.fullHex(dest));
        mem.setRamByte(dest, mem.getRamByte(dest) + mem.getRegisterOffsetValue(src, offset));
    }

    /**
     * Moves the value of the source RAM address into the specified RAM address.
     */
    public void addAA(int dest, int addr) {
        mem.setRamByte(dest, mem.getRamByte(dest) + mem.getRamByte(addr));
    }

    /**
     * Moves a value into the specified RAM address.
     */
    public void addAV(int dest, int value) {
        mem.setRamByte(dest, mem.getRamByte(dest) + value);
    }

    public void jmp(int addr) {
        mem.setIP(addr);
    }

    public void startExecution() {
        System.out.println("Started execution");
        executing = true;
        task = (ScheduledFuture<Object>) periodicRunner.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                step();
            }
        }, 0, 1000000000000L / clockSpeedMilliHz, TimeUnit.NANOSECONDS);
    }

    void pauseExecution() {
        System.out.println("Paused execution");
        executing = false;
        task.cancel(true);
    }

    void resetState() {
        mainMemory().resetAll();
    }

    private void setClockSpeed(int milliHz) {
        this.clockSpeedMilliHz = milliHz;
    }

    public void changeClockSpeed(int newMilliHz) {
        if (executing) {
            pauseExecution();
            setClockSpeed(newMilliHz);
            startExecution();
        } else {
            setClockSpeed(newMilliHz);
        }
    }
}
