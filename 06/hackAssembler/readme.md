The folder src contains hack assembler implementation in Java. You will also see a folder assembly inside which I used as one stop shop for reading assembly files and generating hack files. The assembler is not tied to this folder. you can keep your assembly files folder anywhere, all you have to do is provide its path when invoking the assembler (App.java) and hack files will be created in the same folder. Assembler also generates .mid files which is the outcome of first pass (hack assembler is a 2 pass process). I have intentionally created this mid file to analyse the outcome after first pass.  