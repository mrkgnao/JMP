package jmp;

import java.util.ArrayList;
import java.util.List;

/**
 * R = register, O = register offset, A = value at address, V = value (in that
 * order)
 * OPCODE_DEST_SRC
 */
public enum Opcode {

    NONE(0),
    // MOVs
    MOV_R_R(1, 2), MOV_R_O(2, 2), MOV_R_A(3, 2), MOV_R_V(4, 2),
    MOV_A_R(5, 2), MOV_A_O(6, 2), MOV_A_A(7, 2), MOV_A_V(8, 2),
    MOV_O_R(9, 2), MOV_O_O(10, 2), MOV_O_A(11, 2), MOV_O_V(12, 2),
    // Math
    ADD_R_R(17, 2), ADD_R_O(18), ADD_R_A(19), ADD_R_V(20),
    ADD_A_R(21), ADD_A_O(22), ADD_A_A(23), ADD_A_V(24),
    ADD_O_R(25), ADD_O_O(26), ADD_O_A(27), ADD_O_V(28),
    SUB_R_R(33), SUB_R_O(34), SUB_R_A(35), SUB_R_V(36),
    SUB_A_R(37), SUB_A_O(38), SUB_A_A(39), SUB_A_V(40),
    SUB_O_R(41), SUB_O_O(42), SUB_O_A(43), SUB_O_V(44),
    MUL_R_R(49), MUL_R_O(50), MUL_R_A(51), MUL_R_V(52),
    MUL_A_R(52), MUL_A_O(53), MUL_A_A(54), MUL_A_V(55),
    MUL_O_R(56), MUL_O_O(57), MUL_O_A(58), MUL_O_V(59),
    DIV_R_R(65), DIV_R_O(66), DIV_R_A(67), DIV_R_V(68),
    DIV_A_R(69), DIV_A_O(70), DIV_A_A(71), DIV_A_V(72),
    DIV_O_R(73), DIV_O_O(74), DIV_O_A(75), DIV_O_V(76),
    MOD_R_R(81), MOD_R_O(82), MOD_R_A(83), MOD_R_V(84),
    MOD_A_R(85), MOD_A_O(86), MOD_A_A(87), MOD_A_V(87),
    MOD_O_R(88), MOD_O_O(89), MOD_O_A(90), MOD_O_V(91),
    // Stack ops
    PUSH_R(97), PUSH_O(98), PUSH_A(99), PUSH_V(100),
    POP_R(101), POP_O(102), POP_A(103),
    // Jumping
    JMP(0x71, 1),
    JC(0x72), JNC(0x73),
    JZ(0x74), JNZ(0x75),
    JA(0x76), JAE(0x77),
    JB(0x78), JBE(0x79),
    JE(0x7a), JNE(0x7b),
    // Compare
    CMP_R_R(0x140, 3),
    INVALID(0xffff);

    private final int value;
    private final int arity;

    static List<String> opcodeNameList;

    public static List<String> getOpcodeNameList() {
        if (opcodeNameList == null) {
            Opcode[] vals = values();
            opcodeNameList = new ArrayList<String>(vals.length);
            for (int i = 0; i < vals.length; i++) {
                opcodeNameList.add(i, vals[i].name());
            }
        }
        return opcodeNameList;
    }

    private Opcode(int v) {
        this.value = v;
        this.arity = -1;
    }

    private Opcode(int value, int arity) {
        this.value = value;
        this.arity = arity;
        System.out.println(this);
    }

    public int getIntVal() {
        return value;
    }

    public int getArity() {
        return arity;
    }

    public boolean isEqualTo(Opcode o) {
        return o.value == value;
    }
}
