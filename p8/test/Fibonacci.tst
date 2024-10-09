load Fibonacci.asm,
output-file Fibonacci.out,
compare-to Fibonacci.cmp,
output-list RAM[16385]%D2.10.2 RAM[16386]%D2.10.2 RAM[16387]%D2.10.2 RAM[16389]%D2.10.2
RAM[16392]%D2.10.2 RAM[16397]%D2.10.2 RAM[16405]%D2.10.2 RAM[16418]%D2.10.2 
RAM[16439]%D2.10.2 RAM[16473]%D2.10.2 RAM[16528]%D2.10.2 RAM[16617]%D2.10.2 RAM[16761]%D2.10.2;

set RAM[16385] 0,
set RAM[16386] 0,
set RAM[16387] 0,
set RAM[16389] 0,
set RAM[16392] 0,
set RAM[16397] 0,
set RAM[16405] 0,
set RAM[16418] 0,
set RAM[16439] 0,
set RAM[16473] 0,
set RAM[16528] 0,
set RAM[16617] 0,
set RAM[16761] 0,
repeat 5000 {
  ticktock;
}
output;