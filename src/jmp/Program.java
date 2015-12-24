/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmp;

import java.util.List;

/**
 *
 * @author admin
 */
public class Program {
    
    final String name;
    final List<Integer> code;

    public Program(String name, List<Integer> code) {
        this.code = code;
        this.name = name;
    }
        
    public Program(String name, int[] code) {
        this.code = Utils.toIntList(code);
        this.name = name;
    }
}
