package jmp;

/**
 * It's not Haskell, I know.
 * @author admin
 */
public enum Register {
    A, B, C, D, SP;
    
    int toId() {
        return ordinal();
    }
    
    static Register toRegister(int i) {
        return values()[i];
    }
}
