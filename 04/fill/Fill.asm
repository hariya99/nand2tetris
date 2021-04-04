// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Put your code here.

(LOOP)
@SCREEN
D=A
// set i to base address
@i
M=D
// SET NUMBER OF ITERATIONS TO itr
@8192
D=A
@itr
M=D

@KBD
D=M
@BLACK
D;JGT
@WHITE
D;JEQ
@LOOP
0;JMP

(BLACK)
// in one pass 16 pixels can be made black and it should be done 32 times to fill one row
// and 256 times to fill all the rows so it should be done 32*256=8192 times to fill entire 
// screen
@i
A=M
M=-1
//@16
//D=A
@i
M=M+1   // next pixel
@itr
D=M-1
M=D
@BLACK
D;JGT
@LOOP
D;JEQ

(WHITE)
// in one pass 16 pixels can be made black and it should be done 32 times to fill one row
// and 256 times to fill all the rows so it should be done 32*256=8192 times to fill entire 
// screen
@i
A=M
M=0
//@16
//D=A
@i
M=M+1   // next pixel
@itr
D=M-1
M=D
@WHITE
D;JGT
@LOOP
D;JEQ