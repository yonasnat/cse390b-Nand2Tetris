// File: projects/p6/JumpLogic.tst
// This file is part of the CSE 390B
// project series, adapted from the
// Nand2Tetris project.

load JumpLogic.hdl,
output-file JumpLogic.out,
compare-to JumpLogic.cmp,
output-list instruction%B1.16.1 aluzr%B2.1.2 alung%B2.1.2 load%B2.1.2 inc%B2.1.2;

// j = 000
set instruction %B1011010010101000,
set aluzr 1,
set alung 0,
eval,
output;

set instruction %B1011010010101000,
set aluzr 0,
set alung 0,
eval,
output;

set instruction %B1011010010101000,
set aluzr 0,
set alung 1,
eval,
output;

// j = 001
set instruction %B1101000110101001,
set aluzr 1,
set alung 0,
eval,
output;

set instruction %B1101000110101001,
set aluzr 0,
set alung 0,
eval,
output;

set instruction %B1101000110101001,
set aluzr 0,
set alung 1,
eval,
output;

// j = 010
set instruction %B1011100110101010,
set aluzr 1,
set alung 0,
eval,
output;

set instruction %B1011100110101010,
set aluzr 0,
set alung 0,
eval,
output;

set instruction %B1011100110101010,
set aluzr 0,
set alung 1,
eval,
output;

// j = 011
set instruction %B1000000100111011,
set aluzr 1,
set alung 0,
eval,
output;

set instruction %B1000000100111011,
set aluzr 0,
set alung 0,
eval,
output;

set instruction %B1000000100111011,
set aluzr 0,
set alung 1,
eval,
output;

// j = 100
set instruction %B1100100110001100,
set aluzr 1,
set alung 0,
eval,
output;

set instruction %B1100100110001100,
set aluzr 0,
set alung 0,
eval,
output;

set instruction %B1100100110001100,
set aluzr 0,
set alung 1,
eval,
output;

// j = 101
set instruction %B1101110100100101,
set aluzr 1,
set alung 0,
eval,
output;

set instruction %B1101110100100101,
set aluzr 0,
set alung 0,
eval,
output;

set instruction %B1101110100100101,
set aluzr 0,
set alung 1,
eval,
output;

// j = 110
set instruction %B1001011100000110,
set aluzr 1,
set alung 0,
eval,
output;

set instruction %B1001011100000110,
set aluzr 0,
set alung 0,
eval,
output;

set instruction %B1001011100000110,
set aluzr 0,
set alung 1,
eval,
output;

// j = 111
set instruction %B1101110010011111,
set aluzr 1,
set alung 0,
eval,
output;

set instruction %B1101110010011111,
set aluzr 0,
set alung 0,
eval,
output;

set instruction %B1101110010011111,
set aluzr 0,
set alung 1,
eval,
output;

// Make sure jumps only happen on C instructions

// j = 001
set instruction %B0101000110101001,
set aluzr 0,
set alung 0,
eval,
output;

// j = 010
set instruction %B0011100110101010,
set aluzr 1,
set alung 0,
eval,
output;

// j = 011
set instruction %B0000000100111011,
set aluzr 1,
set alung 0,
eval,
output;

set instruction %B0000000100111011,
set aluzr 0,
set alung 0,
eval,
output;

// j = 100
set instruction %B0100100110001100,
set aluzr 0,
set alung 1,
eval,
output;

// j = 101
set instruction %B0101110100100101,
set aluzr 0,
set alung 0,
eval,
output;

set instruction %B0101110100100101,
set aluzr 0,
set alung 1,
eval,
output;

// j = 110
set instruction %B0001011100000110,
set aluzr 1,
set alung 0,
eval,
output;

set instruction %B0001011100000110,
set aluzr 0,
set alung 1,
eval,
output;

// j = 111
set instruction %B0101110010011111,
set aluzr 1,
set alung 0,
eval,
output;

set instruction %B0101110010011111,
set aluzr 0,
set alung 0,
eval,
output;

set instruction %B0101110010011111,
set aluzr 0,
set alung 1,
eval,
output;