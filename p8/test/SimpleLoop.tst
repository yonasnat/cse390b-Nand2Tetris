load SimpleLoop.asm,
output-file SimpleLoop.out,
compare-to SimpleLoop.cmp,
output-list RAM[256]%D2.10.2;

set RAM[256] 0,
repeat 600 {
  ticktock;
}
output;
