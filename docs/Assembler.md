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

### Pre-Processor Instructions
These are instructions, which are aimed at making files work together. They can be executed using `$: CMD`. 

These must all be placed towards the top of the file to work properly. 

#### Name
Using the `$: NAME name` instruction, the currently parsed name will be named. This is necessary for exports and to avoid recursive includes/imports.  
#### Include
When including (`$: INCLUDE file_path`), the included file will be appended to the end of the file. The included file will be parsed recursively. When seeing a file that has already been _included_ (based on its specified name property), we will terminate.
#### Import
Importing (`$: IMPORT file_path`) appends the imported file to the current file. It works recursively as well. All labels will be obfuscated by appending an identifier in front of each label, based on a counter, starting at an arbitrary value. So a label `.MAIN` of the imported file may now be called: `.4578_MAIN`.

This obfuscation is different from include as it avoids name-space collisions. 

In case a label name is pre-marked with `EXPORT`, the label's name will get the name of the file, followed by `.`, followed by the previous label name. So a label named `.MAIN`, followed by a `$: NAME file` would be renamed to `.file_MAIN`.
#### Export
Exporting (`$: EXPORT label_name`) a label name will inform the Pre-Assembler to treat the given label name differently when using imports. 
