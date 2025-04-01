# Maple Machine

The **Maple Machine** (*MapMac*) is a virtual machine for educational purposes. The goal is to learn more about CPUs and
to enable interaction with I/O and the operation of a simple graphics unit. In the long term, the development of an
operating system should be enabled.
It is closely aligned with ARM assembly languages. This is a Von-Neumann machine.

**This project should include:**

- A compiler (Assembly → Object files)
- Linker
- Assembler
- Interpreter

## Memory

The memory contains 64-bit values. This includes both program code and data values. The *Stack* shrinks in this context.
##### Controlled Access Region (CAR)

Memory access can be restricted through so-called CARs.

A command within a CAR can only access memory within the CAR or the Stack. A CAR can only be defined or removed outside
of a CAR.
The following commands can be used for this:

| Name                    | Command              | Purpose                                                                         | OpCode      |
|-------------------------|----------------------|---------------------------------------------------------------------------------|-------------|
| CAR Define              | `CARD ID START, END` | Defines a region in memory with an ID                                           | `1000 0000` |
| CAR Remove              | `CARR ID`            | Removes a region with a given ID                                                | `1000 0001` |
| CAR Stack Region Define | `CASD START END`     | Defines the Stack and its size. The Stack Pointer is not automatically changed. | `1000 0010` |

## Registers

We have several general-purpose registers `r0` to `r9`. These can be used for anything. `r0` is the default return
register.

| Register     | Definition                                                                                 | Numerical Value  |
|--------------|--------------------------------------------------------------------------------------------|------------------|
| `r0`         | Return Register                                                                            | `0000`           |
| `r1` to `r5` | General-Purpose Register                                                                   | `0001` to `0101` |
| `sp`         | Stack Pointer                                                                              | `0110`           |
| `pc`         | Program Counter                                                                            | `0111`           |
| `dl`         | Dynamic Link - This points to an instruction (usually used for return)                     | `1000`           |
| `cr`/`mr`    | Compare or Math Result: This register holds the flags set by comparison or math operations | `1001`           |
| `iop`        | IOPointer: Points to an IO device to communicate with                                      | `1010`           |
| `ps`         | Program Start - Points to the beginning of the program. Used for dynamic program loading   | `1011`           |
| `pl`         | Program Length - The length in words allocated for the program.                            | `1100`           |
| `fp`         | Frame Pointer - Points to the beginning of the usable memory area.                         | `1001`           |
| `h0` to `h1` | Hardware Register - These registers are reserved for specific hardware functions.          | `1110` to `1111` |

## Instructions

| Name                                    | Usage                                                                         | Description                                                                                                                                                                             | OpCode      |
|-----------------------------------------|-------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------|
| NOP                                     | `NOP`                                                                         | Skips the current line.                                                                                                                                                                 | `0000 0000` |
| Move / Move Not                         | `MOV rdest src` / `MVN rdest src`                                             | Copies the value from `src` to `rdest`, where `rdest` must be a register. For Move Not, a NOT operation is additionally performed.                                                      | `0000 0001` |
| Add Integer                             | `ADDI rdest, a, b`                                                            | Adds the integer values in `regDest` and `reg1`, or in `reg1` and `reg2`, and stores the result in `regDest`                                                                            | `0000 0010` |
| Subtract Integer                        | `SUBI rdest, a, b`                                                            | Subtracts the integer values in `regDest` and `reg1`, or in `reg1` and `reg2`, and stores the result in `regDest`                                                                       | `0000 0011` |
| Multiply Integer                        | `MULI rdest, a, b`                                                            | Multiplies the integer values in `regDest` and `reg1`, or in `reg1` and `reg2`, and stores the result in `regDest`                                                                      | `0000 0100` |
| Divide Integer                          | `DIVI rdest, a, b`                                                            | Divides the integer values in `regDest` and `reg1`, or in `reg1` and `reg2`, and stores the result in `regDest`.                                                                        | `0000 0101` |
| Add Float                               | `ADDF rdest, ra, rb`                                                          | Adds the float values in `regDest` and `reg1`, or in `reg1` and `reg2`, and stores the result in `regDest`                                                                              | `0000 0110` |
| Subtract Float                          | `SUBF rdest, ra, rb`                                                          | Subtracts the float values in `regDest` and `reg1`, or in `reg1` and `reg2`, and stores the result in `regDest`                                                                         | `0000 0111` |
| Multiply Float                          | `MULF rdest, ra, rb`                                                          | Multiplies the float values in `regDest` and `reg1`, or in `reg1` and `reg2`, and stores the result in `regDest`                                                                        | `0000 1000` |
| Divide Float                            | `DIVF rdest, ra, rb`                                                          | Divides the float values in `regDest` and `reg1`, or in `reg1` and `reg2`, and stores the result in `regDest`.                                                                          | `0000 1001` |
| Conditional Skip                        | `SGE` / `SEQ` / ...                                                           | Skips the next instruction if the value is `true`.                                                                                                                                      | `0000 1010` |
| Compare Int                             | `CMPI a b`                                                                    | Compares the integer values of registers `a` and `b`. Sets the `cr` register according to the comparison.                                                                               | `0000 1011` |
| Compare Float                           | `CMPF reg1 reg2`                                                              | Compares the float values of registers `reg1` and `reg2`. Sets the `cr` register according to the comparison.                                                                           | `0000 1100` |
| Compare Results                         | `rge reg1`, `rle reg1`, `req reg1`, `rnq reg1`, `rgt reg1`, `rlt reg1`        | Evaluates the comparison stored in `cr` and sets the value `true` (= 1), or `false` (= 0) in register `reg1`.                                                                           | `0000 1101` |
| Conditional Branch                      | `bge reg+o`, `ble reg+o`, `beg reg+o`, `bnq reg+o`, `bgt reg+o`, `blt reg+o`  | Performs a jump based on the `cr` register to an address + Offset                                                                                                                       | `0000 1110` |
| Branch                                  | `b reg, offset`                                                               | Performs a jump                                                                                                                                                                         | `0000 1111` |
| Branch and Link                         | `bl reg, offset`                                                              | Performs a jump and sets the `dl` register to the address following the command.                                                                                                        | `0001 0000` |
| Logical Shift Left, Logical Shift Right | `lsl rdest rsource shift`                                                     | Performs a logical shift.                                                                                                                                                               | `0001 0001` |
| And, Or, Xor                            | `AND/ORR/XOR rdest ra rb`                                                     | Performs the corresponding operation and stores the result in `rdest`.                                                                                                                  | `0001 0010` |
| Load to Register                        | `LDR reg, raddress, offset`                                                   | Loads the value from memory into the register.                                                                                                                                          | `0001 0011` |
| Store to Memory from Register           | `STR reg, raddress, offset`                                                   | Stores the value from the register into memory.                                                                                                                                         | `0001 0100` |
| Pop / Push                              | `POP reg` / `PUSH reg`                                                        | Pushes the value stored in `reg` to the stack / Pops the value from the stack and stores it in `reg`                                                                                    | `0001 0101` |
| Exit                                    | `EXIT`                                                                        | Powers off the processor. Not possible within a CAR.                                                                                                                                    | `0001 0110` |
| IO Read / Write                         | `IOR rdevice_dest host_source length` / `IOW rhost_dest device_source length` | Writes or reads to an IO device at a certain location from a certain location for a certain length. For certain IO devices, such as storage devices, this is not possible within a CAR. | `0001 0111` |

Other instructions in the future: `SWI`

## Things to keep in mind
#### Floats

When performing float operations, the values must first be moved into registers.
The float operators do not support direct value inputs but must instead rely on the register values.

This is due to a limitation with floats, specifically that they need to fit into 32-bit.
