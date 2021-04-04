/**
 * Nand2Tetris Hack assembler implementation in Java.
 * @author Harish Pal Chauhan
 * @version 1.0
 * @since 2021-04-03
 */

import hack.assembler.Assembler;
import hack.assembler.Files;

public class App {
    public static void main(String[] args) throws Exception {
        if (args.length > 1 || args.length == 0) {                                   
            System.out.println("Usage: Assembler [file-path] ");            
            System.exit(64); 
          } else {
            Files files = new Files(args[0]);
            // System.out.println(files.getInfilePath());
            // System.out.println(files.getMidfilePath());
            // System.out.println(files.getOutfilePath());
            Assembler assembler = new Assembler(files.getInfilePath(),
            files.getMidfilePath(), files.getOutfilePath());    
            assembler.firstPass();
            assembler.secondPass();                                                               
          }                                                       
  
    }
}
