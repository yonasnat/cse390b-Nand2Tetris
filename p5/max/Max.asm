// File: projects/p5/max/Max.asm
// This file is part of the CSE 390B
// project series, adapted from the
// Nand2Tetris project.

// Compares R0 and R1 and stores the max value in R2 and
// the address of the max value in R3.
// If the two values in R0 and R1 are equal, then R0 should be considered the max.
// (R0, R1, R2, R3 refer to RAM[0], RAM[1], and RAM[2], RAM[3] respectively.)

@R0
D=M
@R1
D=D-M
@jump
D;JGT
@R1
D=M
@R2
M=D
@R1
D=A
@R3
M=D
@END
(jump)
@R0
D=M
@R2
M=D
@R0
D=A
@R3
M=D
(END)
