load SimpleAddition.asm,
output-file SimpleAddition.out,
compare-to SimpleAddition.cmp,
output-list RAM[256]%D2.10.2;

set RAM[256] 0,
repeat 80 {
  ticktock;
}
output;
