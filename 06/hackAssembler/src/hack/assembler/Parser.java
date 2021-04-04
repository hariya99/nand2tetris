/**
 * Nand2Tetris Hack assembler implementation in Java.
 * @author Harish Pal Chauhan
 * @version 1.0
 * @since 2021-04-03
 */

package hack.assembler;
import java.util.Scanner;   // Import the Scanner class to read text files
import java.io.*;           // Import io for I/O operations 


class Parser {
    private Scanner fileReader;
    public String line;
    public int lineCounter;
    public static final int A_COMMAND = 0, C_COMMAND = 1, L_COMMAND = 2;
    public int command;
    private int start, end;

    // constructor
    Parser(File inFile){

        try {
            this.fileReader = new Scanner(inFile);
            this.command = 99;
            this.line = "";
            this.start = 0;
            this.end = 0;

          } catch (FileNotFoundException e) {
            System.out.println("Error in file path/name.");
            e.printStackTrace();
          }
    }
    
    public boolean hasMoreCommands(){
        /**
         * returns whether file has more lines to process or not.
         */
        return fileReader.hasNextLine();
    }

    public void advance(){
        /**
         * Reads the next line
         */
        line = fileReader.nextLine().stripLeading().replaceAll("\\R", "");
    }

    public int commandType(){
        /**
         * Determines the command type -> Hack assembly language has three type of commands:
         * A Command: It either gives you a static value or the address of a variable. 
         * L Command: Signifies that it is a label.
         * C Command: The command which enables CPU to compute something.
         * Note: Call this method only when you are sure that the current line
         * represents a command 
         */

        if (line.startsWith("@")){
            return A_COMMAND;
        }
        else
        if (line.stripLeading().startsWith("(")){
            return L_COMMAND;
        }
        else{
            return C_COMMAND;
        }
    }

    public String symbol(){
        /**
         * Extracts variableName/staticValue/labelName
         */
        start = 1; 
        end = 2;       
        // A command
        if (line.startsWith("@")){
            setTokenLength();
        }
        else{
            // L command
            while(line.charAt(end) != ')') end++;
        }
        return line.substring(start, end);

    }
    
    public String dest(){
        /**
         * Returns Dest mnemonic of C command
         */
        start = 0; end = 0;
        end = line.indexOf("=");
        return line.substring(start, end);
    }

    public String comp(){
        /**
         * Returns Comp mnemonic of C command 
         */
        if(line.contains("=")){
            start = end = line.indexOf("=") + 1;
            setTokenLength();
        }
        else{
            // it contains semicolon
            start = 0;
            end = line.indexOf(";"); 
        }
        return line.substring(start, end);
    }
    
    public String jump(){
        /**
         * Returns Jump mnemonic of C command
         */
        start = end = line.indexOf(";") + 1;
        setTokenLength();
        return line.substring(start, end);
    }

    private void setTokenLength(){
        /**
         * Returns mnemonic length
         */
        while(end < line.length() && !Character.isWhitespace(line.charAt(end))) end++;
    }
}
