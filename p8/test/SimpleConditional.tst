load SimpleConditional.asm,
output-file SimpleConditional.out,
compare-to SimpleConditional.cmp,
output-list RAM[16384]%D2.10.2;

set RAM[16384] 0,
repeat 120 {
  ticktock;
}
output;