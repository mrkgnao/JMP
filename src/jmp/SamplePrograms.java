package jmp;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class SamplePrograms {
    
    public static final List<Program> PROGRAMS = new ArrayList<Program>();
    
    public static final Program INFINITE_FIBONACCI_LOOP = new Program(
            "An infinite fibonacci loop",
            new int[]{
                Opcode.MOV_R_V.getIntVal(), Register.A.toId(), 0x0001,
                Opcode.MOV_R_V.getIntVal(), Register.B.toId(), 0x0001,
                Opcode.MOV_R_V.getIntVal(), Register.C.toId(), 0x0002,
                Opcode.MOV_R_R.getIntVal(), Register.A.toId(), Register.B.toId(),
                Opcode.MOV_R_R.getIntVal(), Register.B.toId(), Register.C.toId(),
                Opcode.ADD_R_R.getIntVal(), Register.C.toId(), Register.A.toId(),
                Opcode.JMP.getIntVal(), 0x0009
            });
    
    static {
        PROGRAMS.add(INFINITE_FIBONACCI_LOOP);
    }    
    
    public static final int NUM_SAMPLE_PROGRAMS = PROGRAMS.size();
}
