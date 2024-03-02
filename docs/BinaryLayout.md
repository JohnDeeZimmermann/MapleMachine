# Binary Layout
This is a description on how the binary layout of each instruction is supposed to look like. 
### General Layout
We have 64-bit available for each instruction. The general layout we look for is:
```
OPCODE (8) | OPTIONS (4) | RDEST (4) | ARG1 (23 + 1) | ARG2(23 + 1)
```
This is made for simplicity and it does have drawbacks to standardize it like this. 
Certain Operations will differ from this layout.

`OPTIONS` contains additional information on the instruction. For example which _compare_ command should be executed.
Each argument also contains an additional bit which determines whether the argument is stored in a register or directly.

##### Example
`ADDI r1, r2, #16` would be represented as
```
00000010 0000 0001 100000000000000000000010 000000000000000000010000
OPCODE   OPT  DEST ARG1 (As register)       ARG2 (Directly)
```