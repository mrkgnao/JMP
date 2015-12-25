package jmp;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class SamplePrograms {
    
    public static final List<BinaryProgram> PROGRAMS = new ArrayList<BinaryProgram>();
    
    public static final BinaryProgram INFINITE_FIBONACCI_LOOP_REG = new BinaryProgram(
            "An infinite fibonacci loop (with registers)",
            new int[]{
                Opcode.MOV_R_V.getIntVal(), Register.A.toId(), 0x0001,
                Opcode.MOV_R_V.getIntVal(), Register.B.toId(), 0x0001,
                Opcode.MOV_R_V.getIntVal(), Register.C.toId(), 0x0002,
                
                Opcode.MOV_R_R.getIntVal(), Register.A.toId(), Register.B.toId(),
                Opcode.MOV_R_R.getIntVal(), Register.B.toId(), Register.C.toId(),
                Opcode.ADD_R_R.getIntVal(), Register.C.toId(), Register.A.toId(),
                
                Opcode.JMP.getIntVal(), 0x0009
            });
    
    public static final BinaryProgram INFINITE_FIBONACCI_LOOP_VAL = new BinaryProgram(
            "An infinite fibonacci loop (with values)",
            new int[]{
                Opcode.MOV_R_V.getIntVal(), Register.A.toId(), 64,
                Opcode.MOV_R_V.getIntVal(), Register.B.toId(), 65,
                Opcode.MOV_R_V.getIntVal(), Register.C.toId(), 66,
                
                Opcode.MOV_O_V.getIntVal(), Utils.encodeMemoryValue(Register.A), 0x0001,
                Opcode.MOV_O_V.getIntVal(), Utils.encodeMemoryValue(Register.B), 0x0001,
                Opcode.MOV_O_V.getIntVal(), Utils.encodeMemoryValue(Register.C), 0x0002,
                
                Opcode.MOV_O_O.getIntVal(), Utils.encodeMemoryValue(Register.A), Utils.encodeMemoryValue(Register.B),
                Opcode.MOV_O_O.getIntVal(), Utils.encodeMemoryValue(Register.B), Utils.encodeMemoryValue(Register.C),
                Opcode.ADD_O_O.getIntVal(), Utils.encodeMemoryValue(Register.C), Utils.encodeMemoryValue(Register.A),
                
                Opcode.JMP.getIntVal(), 0x0012
            });
    
    static {
        PROGRAMS.add(INFINITE_FIBONACCI_LOOP_REG);
        PROGRAMS.add(INFINITE_FIBONACCI_LOOP_VAL);
    }    
    
    public static final int NUM_SAMPLE_PROGRAMS = PROGRAMS.size();
}
