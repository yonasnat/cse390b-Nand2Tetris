load MultipleExpressions.asm,
output-file MultipleExpressions.out,
compare-to MultipleExpressions.cmp,
output-list RAM[16384]%D2.10.2 RAM[16385]%D2.10.2 RAM[16386]%D2.10.2 RAM[16387]%D2.10.2 RAM[16388]%D2.10.2 RAM[16389]%D2.10.2 RAM[16390]%D2.10.2;

set RAM[16384] 0,
set RAM[16385] 0,
set RAM[16386] 0,
set RAM[16387] 0,
set RAM[16388] 0,
set RAM[16389] 0,
set RAM[16390] 0,
repeat 1000 {
  ticktock;
}
output;