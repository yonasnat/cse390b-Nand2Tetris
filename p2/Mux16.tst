// File: projects/1/Mux16.tst
// This file is part of the CSE 390B
// project series, adapted from the
// Nand2Tetris project.

load Mux16.hdl,
output-file Mux16.out,
compare-to Mux16.cmp,
output-list a%B1.16.1 b%B1.16.1 sel%D2.1.2 out%B1.16.1;

set a 0,
set b 0,
set sel 0,
eval,
output;

set sel 1,
eval,
output;

set a %B0000000000000000,
set b %B0001001000110100,
set sel 0,
eval,
output;

set sel 1,
eval,
output;

set a %B1001100001110110,
set b %B0000000000000000,
set sel 0,
eval,
output;

set sel 1,
eval,
output;

set a %B1010101010101010,
set b %B0101010101010101,
set sel 0,
eval,
output;

set sel 1,
eval,
output;