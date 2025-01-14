// File: projects/2/FullAdder.tst
// This file is part of the CSE 390B
// project series, adapted from the
// Nand2Tetris project.

load FullAdder.hdl,
output-file FullAdder.out,
compare-to FullAdder.cmp,
output-list a%B3.1.3 b%B3.1.3 c%B3.1.3 sum%B3.1.3 carry%B3.1.3;

set a 0,
set b 0,
set c 0,
eval,
output;

set c 1,
eval,
output;

set b 1,
set c 0,
eval,
output;

set c 1,
eval,
output;

set a 1,
set b 0,
set c 0,
eval,
output;

set c 1,
eval,
output;

set b 1,
set c 0,
eval,
output;

set c 1,
eval,
output;
