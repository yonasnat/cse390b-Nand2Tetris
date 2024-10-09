// File: projects/1/Not.tst
// This file is part of the CSE 390B
// project series, adapted from the
// Nand2Tetris project.

load Not.hdl,
output-file Not.out,
compare-to Not.cmp,
output-list in%B3.1.3 out%B3.1.3;

set in 0,
eval,
output;

set in 1,
eval,
output;
