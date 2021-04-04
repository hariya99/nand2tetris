// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)

// Put your code here.

// initialization
@R2
M=0
// load R1 and check whether it is 0 or not, go to multiply only if it is > 0
@R1
D=M

// END IF R1 WAS = 0 
@END
D;JEQ

@MULTIPLY
D;JGT


(MULTIPLY)
// LOAD R0
@R0
D=M

// LOAD R2
@R2
// R2+R1
M=D+M 

// LOAD R1 AND SUBTRACT 1  
@R1
D=M-1
M=D

@MULTIPLY
D;JGT   // JUMP TO MULTIPLY IF D>0

@END
D;JEQ

(END)
0;JMP