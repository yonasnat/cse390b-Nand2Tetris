load NestedLoopsAndConditionals.asm,
output-file NestedLoopsAndConditionals.out,
compare-to NestedLoopsAndConditionals.cmp,
output-list RAM[16384]%D2.10.2 RAM[16385]%D2.10.2 RAM[16386]%D2.10.2
RAM[16416]%D2.10.2 RAM[16417]%D2.10.2 RAM[16418]%D2.10.2
RAM[16448]%D2.10.2 RAM[16449]%D2.10.2  RAM[16450]%D2.10.2
RAM[16480]%D2.10.2 RAM[16481]%D2.10.2 RAM[16482]%D2.10.2
RAM[16512]%D2.10.2 RAM[16513]%D2.10.2 RAM[16514]%D2.10.2
RAM[16544]%D2.10.2 RAM[16545]%D2.10.2 RAM[16546]%D2.10.2;

set RAM[16384] 0,
set RAM[16385] 0,
set RAM[16386] 0,
set RAM[16387] 0,
set RAM[16416] 0,
set RAM[16417] 0,
set RAM[16418] 0,
set RAM[16419] 0,
set RAM[16448] 0,
set RAM[16449] 0,
set RAM[16450] 0,
set RAM[16451] 0,
set RAM[16480] 0,
set RAM[16481] 0,
set RAM[16482] 0,
set RAM[16483] 0,
set RAM[16512] 0,
set RAM[16513] 0,
set RAM[16514] 0,
set RAM[16515] 0,
set RAM[16544] 0,
set RAM[16545] 0,
set RAM[16546] 0,
set RAM[16547] 0,
set RAM[16576] 0,
repeat 10000 {
  ticktock;
}
output;