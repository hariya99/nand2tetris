/**
 * Nand2Tetris Hack assembler implementation in Java.
 * @author Harish Pal Chauhan
 * @version 1.0
 * @since 2021-04-03
 */

package hack.assembler;

import java.util.HashMap;                                                                                                     
import java.util.Map;

public class SymbolTable {
    /**
     * Symbol table holds addresses of pre-defined symbols as well as user defined 
     * symbols(variavles)/labels.
     */
    private static Map<String, Integer> symbols;

    static{
        symbols = new HashMap<>();
        symbols.put("SP",   0 );
        symbols.put("LCL",  1 );
        symbols.put("ARG",  2 );
        symbols.put("THIS", 3 );
        symbols.put("THAT", 4 );
        symbols.put("R0",   0 );
        symbols.put("R1",   1 );
        symbols.put("R2",   2 );
        symbols.put("R3",   3 );
        symbols.put("R4",   4 );
        symbols.put("R5",   5 );
        symbols.put("R6",   6 );
        symbols.put("R7",   7 );
        symbols.put("R8",   8 );
        symbols.put("R9",   9 );
        symbols.put("R10",  10 );
        symbols.put("R11",  11 );
        symbols.put("R12",  12 );
        symbols.put("R13",  13 );
        symbols.put("R14",  14 );
        symbols.put("R15",  15 );
        symbols.put("SCREEN", 16384 );
        symbols.put("KBD",    24576 );
    }

    public void addEntry(String item, int addr){
        symbols.put(item, addr);
    }

    public boolean contains(String item){
        return symbols.containsKey(item);
    }
    
    public Integer getAddress(String item){
        return symbols.get(item);
    }
}
