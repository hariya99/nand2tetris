/**
 * Nand2Tetris Hack assembler implementation in Java.
 * @author Harish Pal Chauhan
 * @version 1.0
 * @since 2021-04-03
 */

package hack.assembler;

import java.io.*;

public class Assembler {
    private String token;
    private File inFile;
    private FileWriter midFile;
    private String midFilePath;
    private FileWriter outFile;

    private static final Code codeObj = new Code();
    private SymbolTable symbl = new SymbolTable();
    private static final String prefix = "111";
    private int varAddress = 16;
    private int commandCounter = 0;

    public Assembler(String inFilePath, String midFilePath, String outFilePath){
      try{
        this.inFile      = new File(inFilePath);
        this.midFile     = new FileWriter(midFilePath);
        this.outFile     = new FileWriter(outFilePath);
        this.midFilePath = midFilePath;
      }catch(IOException e ){
        System.out.println("Error in file path/name.");
        e.printStackTrace();
      }
      
    }

    public void firstPass(){
      /**
       * Reads assembly code, removes comments, whitespace and generates a mix of binary code, 
       * variables and symbols. The output goes to a midFile which becomes input file to second
       * pass.
       */
      Parser parser = new Parser(inFile);
      while(parser.hasMoreCommands()){
        parser.advance();
        token = "";
        
        if(parser.line.length()>0){

          if(parser.commandType() == Parser.A_COMMAND){
            processACommand(parser);
            writeMidFile(token);
          }
          else if(parser.commandType() == Parser.L_COMMAND ){
            processLCommand(parser);
          }
          else if(parser.line.startsWith("/")){
            continue;
          }
          else{
            // it is a c command
            processCCommand(parser);
            writeMidFile(token);
          }  
        }
        //System.out.println(Integer.toString(commandCounter) + ": " + token);
        
      }
      closeMidFile();
    }

    private String getCBinary(String comp, String dest, String jump){
      /**
       * Converts C command to its corresponding binary command.
       */
      return prefix + codeObj.comp(comp) + codeObj.dest(dest) + codeObj.jump(jump);
    }

    private String getBinary(int item){
      /**
       * Returns 16 bit binary value of integers 
       */
      return String.format("%16s", Integer.toBinaryString(item)).replaceAll(" ", "0");
    }

    private boolean isDigit(char c){
      /**
       * Checks whethere the character is a digit or not
       */
      return c >= '0' && c <= '9'; 
    }

    private void processACommand(Parser parser){
      /**
       * Processes A Command. A command like @100 gets directly converted to its binary 
       * equivalent but variables like  @i does not get processed here.
       */
      commandCounter++;
      token = parser.symbol();
      // convert to binary directly
      if(isDigit(token.charAt(0))){
        token = getBinary(Integer.parseInt(token));
      }
      else{
        // It can be a variable or a call to a label so do not convert it in first pass,
        // Write it as is in mid file. Assign address to it directly in second pass. 
        ;
      }
    }

    private void processLCommand(Parser parser){
      /**
       * Processes L command. Symbols defined by L command are stored in symbol table 
       * along with their line addresses (the line at which they occur) if they do not exist in 
       * symbol table already otherwise their binary code is generated. 
       */
      
      token = parser.symbol();
      if(symbl.contains(token)){
        token = getBinary(symbl.getAddress(token));
      }
      else{
        symbl.addEntry(token, commandCounter);
      }

    }

    private void processCCommand(Parser parser){
      /**
       * Processes C command and generates its binary equivalent 
       */
      commandCounter++;
      if(parser.line.contains("=")){
        token = getCBinary(parser.comp(), parser.dest(), "null");
      } 
      else if(parser.line.contains(";")){
        token = getCBinary(parser.comp(), "null", parser.jump());
      }

    }

    public void secondPass(){
      /**
       * Second pass processes midFile and replaces symbols (variables) and labels with their 
       * binary equivalent. At this stage all the labes have their address stored in sybol table, 
       * only the address for variables is missing from symbol table. 
       * So, second pass: 
       *    1) generates address for variables 
       *    2) stores them in symbol table so that their address is reproduced when they are
       *       encountered next in the file.
       *    3) Writes binary commands to final output file. 
       */
      
      Parser parser = new Parser(new File(midFilePath));
      while(parser.hasMoreCommands()){
        parser.advance();
        token = "";
        // if it is a binary code line 
        if (parser.line.startsWith("0") || parser.line.startsWith("1")){
          token = parser.line;
        }
        else{
          token = parser.line.replaceAll("\\R", "").strip();
          // if it is a call to a label, it will be found in symbl table 
          if (symbl.contains(token)){
            token = getBinary(symbl.getAddress(token));  
          }
          else{
            // add an entry to symbl table so that it can be retrieved if this varibale is called
            // again.
            symbl.addEntry(token, varAddress);
            token = getBinary(varAddress);
            varAddress++;
          }
          
        }
        writeOutFile(token);
      }  
      closeOutFile();
    }

    private void writeMidFile(String line){
      /**
       * Writes a line to midFile
       */
      try{
        midFile.write(line + "\n");
      }catch(IOException e ){
        System.out.println("Error while writing to midFile");
        System.out.println(line);
        e.printStackTrace();        
      }
    }

    private void closeMidFile(){
      /**
       * Closes midFile
       */
      try{
        midFile.close();
      }catch(IOException e ){
        System.out.println("Error while closing midFile");
        e.printStackTrace();        
      }
    }

    private void writeOutFile(String line){
      /**
       * Writes a line to final output file 
       */
      try{
        outFile.write(line + "\n");
      }catch(IOException e ){
        System.out.println("Error while writing to outFile");
        System.out.println(line);
        e.printStackTrace();        
      }
    }
    private void closeOutFile(){
      /**
       * Closes outFile
       */
      try{
        outFile.close();
      }catch(IOException e ){
        System.out.println("Error while closing outFile");
        e.printStackTrace();        
      }
    }
}

