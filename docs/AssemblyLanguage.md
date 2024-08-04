# Assembly Language
### Overview
The _Maple Assembly Language_ (**MAL**) is used to more easily write code for the Maple machine. It is stored in `.masm` (_Maple Assembly_) files. 

The langauge is not case sensitive as all the cases will be matched during parsing. 
### Instructions
Instructions follow the following format: 

`INSTR PARAM, PARAM, PARAM`

whereas `PARAM` can be a register, denoted by their names, or they can be a direct value, denoted by a `#`, followed by the number, starting with `0b` for binary,`0x` for hexadecimal, or without any prefix for decimal values. 
### Comments
Comments can be created with `//` followed by the comment. The comment's contents will be stripped during parsing. 

### Labels
Labels can be created using `.`, followed by the label's name (e.g. `.LABEL`). 

To jump to a label, reference the label using `@`, follwed by the label's name (e.g.
`@LABEL`)

Label's will be stored as an offset to the program start, with the first line to be assumed as an offset of `0`.

Using Pre-Assembly instructions, it is possible to export labels in order to be called by other files, which include the current file. To call exported labels of other files, the other file has to be named during inclusion. The label will then be referenced by the name of the file, followed by `.`, followed by the name of the label. For example: `@FILE.LABEL`

### Pre-Assembly Instructions
To instruct the Pre-Assembler, instructions will have to denoted with a `$: `. 

All instructions need to occur towards the top of the file. 

The following instructions are available: 

- `$: import FILE_PATH as NAME`
  - Appends a given file to the current one. `NAME` may be used as reference later. Labels of included file will be obfuscated, except for exported labels.
- `$: include FILE_PATH`
  - Appends a given file to the current one. 
- `$: file NAME`
  - Identify the current file with a name to avoid endless inclusion loops. Cannot be called after `include` instructions.
- `$: export LABEL_NAME`
  - Exports the name of a label to be used by files, which include the 