# Assembler
## General
### Explanation
An assembler is generally used to translate given assembly instructions into machine code. Here, we usually don't resolve references to external packages. That job is usually done by a _linker_. 

However, to simplify things for this project, we include the linker's job as a pre-processing step of the assembler. 
So the order is as follows: 
- **Pre-Assembler** - Loads all the referenced assembly files and merges them into a single file.
- **Assembler** - Compiles the given file into Maple-Instructions

## Pre-Assembler
This does accept any `.masm` (_maple assembly_) files. 
It is used to handle Pre-Assembler instructions. 

The output file type will be `.pmasm` (_preprocessed maple assembly_) used by the _assembler_ itself. 
### File Pre-Processing
All the file's contents will be changed to be upper case. 

Also, all comments will be removed, as well as all leading and trailing whitespaces. 
### Name
Using the `NAME` instruction, the currently parsed name will be named. This is necessary for exports and to avoid recursive includes/imports.  
### Include
When including, the included file should be appended to the end of the file. The included file will be parsed recursively. Wheen seeing a file that has already been _included_ (based on name), we will terminate.
### Import
Importing appends the imported file to the current file. It works recursively as well. All labels will be obfuscated by appending an identifier infront of each label, based on a counter, starting at a random value. So a label `.MAIN` of the imported file may now be called: `.4578_MAIN`.

In case a label name is pre-marked with `EXPORT`, the label's name will get the name of the file, followed by `.`, followed by the previous label name. So a label named `.MAIN`, followed by a `$: NAME file` would be renamed to.
### Export
Exporting a label name will inform the Pre-Assembler to treat the given label name differently when using imports. 