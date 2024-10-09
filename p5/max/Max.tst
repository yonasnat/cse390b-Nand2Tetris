// File: projects/p5/max/Max.tst
// This file is part of the CSE 390B
// project series, adapted from the
// Nand2Tetris project.

load Max.hack,
output-file Max.out,
compare-to Max.cmp,
output-list RAM[0]%D2.6.2 RAM[1]%D2.6.2 RAM[2]%D2.6.2 RAM[3]%D2.6.2;

set RAM[0] 0,
set RAM[1] 0,
set RAM[2] -1,
set RAM[3] -1;
repeat 20 {
  ticktock;
}
set RAM[0] 0,
set RAM[1] 0;
output;

set PC 0,
set RAM[0] 3,
set RAM[1] 4,
set RAM[2] -1,
set RAM[3] -1;
repeat 20 {
  ticktock;
}
set RAM[0] 3,
set RAM[1] 4;
output;

set PC 0,
set RAM[0] 4,
set RAM[1] 3,
set RAM[2] -1,
set RAM[3] -1;
repeat 20 {
  ticktock;
}
set RAM[0] 4,
set RAM[1] 3;
output;

set PC 0,
set RAM[0] 8,
set RAM[1] 0,
set RAM[2] -1,
set RAM[3] -1;
repeat 20 {
  ticktock;
}
set RAM[0] 8,
set RAM[1] 0;
output;

set PC 0,
set RAM[0] 2,
set RAM[1] 9,
set RAM[2] -1,
set RAM[3] -1;
repeat 20 {
  ticktock;
}
set RAM[0] 2,
set RAM[1] 9;
output;

set PC 0,
set RAM[0] 10,
set RAM[1] 10,
set RAM[2] -1,
set RAM[3] -1;
repeat 20 {
  ticktock;
}
set RAM[0] 10,
set RAM[1] 10;
output;
