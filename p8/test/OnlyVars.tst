load OnlyVars.asm,
output-file OnlyVars.out,
compare-to OnlyVars.cmp,
output-list RAM[1]%D2.10.2;

set RAM[1] 0,
repeat 20 {
  ticktock;
}
output;
