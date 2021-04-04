/**
 * Nand2Tetris Hack assembler implementation in Java.
 * @author Harish Pal Chauhan
 * @version 1.0
 * @since 2021-04-03
 */

package hack.assembler;

import java.io.*;

public class Files {

    private File fileObj;
    private String inFile;
    private String midFile;
    private String outFile;

    public Files(String filePath){
        /**
         * @param filePath -> relative path of .asm file
         * @params inFile, midFile, outFile hold canonical path of 
         * input file, middle file and output file.
         * Middle file gets created after first pass and I have intentionally not 
         * deleted it just to visualise/analyse the code after first pass.   
         */
        
        this.fileObj = new File(filePath);
        try{
            // get absolute path and strip the extension
            this.inFile = this.fileObj.getCanonicalPath().split("\\.")[0];
            this.midFile = this.inFile + ".mid";
            this.outFile = this.inFile + ".hack";
            this.inFile = this.inFile  + ".asm";
        }catch (IOException e){
            System.out.println("Error in file path/name.");
            e.printStackTrace();
        }  
                
    }

    public String getInfilePath(){
        return inFile;
    }

    public String getMidfilePath(){
        return midFile;
    }

    public String getOutfilePath(){
        return outFile;
    }   
    
}
