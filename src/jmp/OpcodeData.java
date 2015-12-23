package jmp;

/**
 * A singleton class for mapping opcodes and "intvals" or memory representations
 * back and forth.
 * @author admin
 */
public class OpcodeData extends TwoWayHashmap<Integer, Opcode> {

    private static final OpcodeData DEFAULT_OPCODE_MAP = new OpcodeData();
    private static final int NUM_OPCODES = Opcode.values().length;

    private OpcodeData() {
        super();
        for (int i = 0; i < Opcode.values().length; i++) {
            add(Opcode.values()[i].getIntVal(), Opcode.values()[i]);
        }
    }

    public static Opcode opcodeOf(int i) {
        Opcode ret = DEFAULT_OPCODE_MAP.getValue(i);
        return ret == null ? Opcode.INVALID : ret;
    }

    public static int intValOf(Opcode op) {
        Integer ret = DEFAULT_OPCODE_MAP.getKey(op);
        return ret == null ? -1 : ret;
    }
}
