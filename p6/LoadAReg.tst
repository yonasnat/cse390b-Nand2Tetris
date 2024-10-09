// File: projects/p6/LoadAReg.tst
// This file is part of the CSE 390B
// project series, adapted from the
// Nand2Tetris project.

load LoadAReg.hdl,
output-file LoadAReg.out,
compare-to LoadAReg.cmp,
output-list instruction%B1.16.1 out%B1.1.1;


set instruction %B1011010010101011,
eval,
output;

set instruction %B0011010010101011,
eval,
output;

set instruction %B0011010010001011,
eval,
output;

set instruction %B1011010010001011,
eval,
output;

set instruction %B1100101100010100,
eval,
output;

set instruction %B1011010010010100,
eval,
output;

set instruction %B1010010010001011,
eval,
output;