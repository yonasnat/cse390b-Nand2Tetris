load NestedAddition.asm,
output-file NestedAddition.out,
compare-to NestedAddition.cmp,
output-list RAM[16384]%D2.10.2;

set RAM[16384] 0,
repeat 120 {
  ticktock;
}
output;