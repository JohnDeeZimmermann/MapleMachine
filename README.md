# Virtual Playground Machine
Die **Virtual Playground Machine** (*VPM*) ist eine virtuelle Maschine zu bildenden Zwecken. Das Ziel besteht darin, mehr über CPUs zu lernen und es möglich zu machen, mit I/O zu interagieren und eine einfache Grafikeinheit zu bedienen. Langfristig soll die Entwicklung eines Betriebssystems ermöglicht werden.
Sie ist sehr nah an die ARM-Assembly Sprachen angelehnt. Es handelt sich hierbei um eine Von-Neumann-Maschine.

**Dieses Projekt soll enthalten:**
- Einen Compiler (Assembly -> Objekt-Dateien)
- Linker
- Assembler
- Interpreter


### Speicher
Der Speicher beinhaltet 64-Bit Werte. In diesem befinden sich sowohl Programmcode, wie auch Datenwerte. Der *Stack* schrumpft dabei.
##### Controlled Access Region (CAR)
Der Speicherzugriff kann über sogenannte CARs beschränkt werden.

Dabei kann ein Befehl innerhalb eines CARs nur auf den Speicher innerhalb des CARs oder des Stacks zugreifen. Eine CAR kann nur außerhalb eines CARs definiert oder gelöscht werden.
Folgende Befehle können dazu verwendet werden:

| Name                    | Befehl               | Zweck                                                                                     | OpCode      |
| ----------------------- | -------------------- | ----------------------------------------------------------------------------------------- | ----------- |
| CAR Define              | `CARD ID START, END` | Definiert eine Region im Speicher mit einer ID                                            | `1000 0000` |
| CAR Remove              | `CARR ID`            | Entfernt eine Region mit gegebener ID                                                     | `1000 0001` |
| CAR Stack Region Define | `CASD START END`     | Definiert den Stack und dessen Größe. Der Stack Pointer wird nicht automatisch verändert. | `1000 0010` |

### Register
Wir haben mehrere General-Purpose Register `r0` bis `r9`. Diese können für alles mögliche verwendet werden. `r0` ist dabei standardmäßig unser Rückgaberegister.

| Register      | Definition                                                                                                         | Numerischer Wert  |
| ------------- | ------------------------------------------------------------------------------------------------------------------ | ----------------- |
| `r0`          | Return Register                                                                                                    | `0000`            |
| `r1` bis `r9` | General-Purpose Register                                                                                           | `0001` bis `0101` |
| `sp`          | Stack Pointer                                                                                                      | `0110`            |
| `pc`          | Programmzähler                                                                                                     | `0111`            |
| `dl`          | Dynamic Link - Dieser zeigt auf eine Instruktion (Meist zum Rücksprung genutzt)                                    | `1000`            |
| `cr`/`mr`     | Compare oder Math Result: Dieser Register beinhaltet die Flags, die bei mathematischen Operationen gesetzt werden. | `1001`            |
| `iop`         | IOPointer: Zeigt auf ein IO-Device, mit welchem kommuniziert werden soll                                           | `1010`            |
| `ps`          | Program Start  - Zeigt auf das Anfang des Programms. Dient dem dynamischen Laden von Programmen                    | `1011`            |
| `pl`          | Program Length - Die Länge in Wörtern, die für das Programm vorgesehen sind.                                       | `1100`            |
| `fp`          | Frame Pointer - Zeigt auf den Anfang des verwendbaren Speicherbereichs.                                            | `1001`            |
| `h0` bis `h1` | Hardware Register - Diese Register sind für bestimmte Hardware-Funktionen reserviert.                              | `1110` bis `1111` |

### Instruktionen
| Name                                | Verwendung                                                                   | Beschreibung                                                                                                          | OpCode      |
| ----------------------------------- | ---------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------- | ----------- |
| Move                                | `MOV rdest src`                                                              | Kopiert den Wert von `src` nach `rdest`, wobei `rdest` ein Register sein muss.                                        | `0000 0000` |
| Move Not                            | `MVN rdest src`                                                              | Wie `MOV`, nur werden hier die Bits negiert.                                                                          | `0000 0001` |
| Add Integer                         | `ADDI regDest reg1` / `ADDI reg1, reg2, regDest`                             | Addiert die Integer Werte in `regDest` und `reg1`, bzw. in `reg1` und `reg2` und Speichert diese in `regDest`         | `0000 0010` |
| Subtract Integer                    | `SUBI regDest reg1` / `SUBI reg1, reg2, regDest`                             | Subtrahiert die Integer Werte in `regDest` und `reg1`, bzw. in `reg1` und `reg2` und Speichert diese in `regDest`     | `0000 0011` |
| Multiply Integer                    | `MULI regDest reg1` / `MULI reg1, reg2, regDest`                             | Multipliziert die Integer Werte in `regDest` und `reg1`, bzw. in `reg1` und `reg2` und Speichert diese in `regDest`   | `0000 0100` |
| Divide Integer                      | `DIVI regDest reg1` / `DIVI reg1, reg2, regDest`                             | Dividiert die Integer Werte in `regDest` und `reg1`, bzw. in `reg1` und `reg2` und Speichert diese in `regDest`.      | `0000 0101` |
| Add Float                           | `ADDF regDest reg1` / `ADDF reg1, reg2, regDest`                             | Addiert die Float Werte in `regDest` und `reg1`, bzw. in `reg1` und `reg2` und Speichert diese in `regDest`           | `0000 0110` |
| Subtract Float                      | `SUBF regDest reg1` / `SUBF reg1, reg2, regDest`                             | Subtrahiert die Float Werte in `regDest` und `reg1`, bzw. in `reg1` und `reg2` und Speichert diese in `regDest`       | `0000 0111` |
| Multiply Float                      | `MULF regDest reg1` / `MULF reg1, reg2, regDest`                             | Multipliziert die Float Werte in `regDest` und `reg1`, bzw. in `reg1` und `reg2` und Speichert diese in `regDest`     | `0000 1000` |
| Divide Float                        | `DIVF regDest reg1` / `DIVF reg1, reg2, regDest`                             | Dividiert die Float Werte in `regDest` und `reg1`, bzw. in `reg1` und `reg2` und Speichert diese in `regDest`.        | `0000 1001` |
| Conditional Skip                    | `SGE` / `SEQ` / ...                                                          | Überspringt die nächste Instruktion, sollte der Wert `true` sein.                                                     | `0000 1010` |
| Compare Int                         | `CMPI reg1 reg2`                                                             | Vergleicht die Integer Werte der Register `reg1`und `reg2`. Setzt den `cr`-Register entsprechend des Vergleichs       | `0000 1011` |
| Compare Float                       | `CMPF reg1 reg2`                                                             | Vergleicht die Float Werte der Register `reg1`und `reg2`. Setzt den `cr`-Register entsprechend des Vergleichs         | `0000 1100` |
| Compare Results                     | `rge reg1`, `rle reg1`, `req reg1`, `rnq reg1`, `rgt reg1`, `rlt reg1`       | Betrachtet den in `cr` abgelegten Vergleich und legt den Wert `true` (= 1), bzw. `false` (= 0) in Register `reg1` ab. | `0000 1101` |
| Conditional Branch                  | `bge reg+o`, `ble reg+o`, `beg reg+o`, `bnq reg+o`, `bgt reg+o`, `blt reg+o` | Führt basierend auf dem `cr` Register einen Sprung aus, zu einer Adresse + Offset                                     | `0000 1110` |
| Branch                              | `b reg+o`                                                                    | Führt einen Sprung aus                                                                                                | `0000 1111` |
| Branch and Link                     | `b reg+o`                                                                    | Führt einen Sprung aus und setzt den `dl` Register auf die nach dem Befehl folgende Adresse.                          | `0001 0000` |
| Logic Shift Left, Logic Shift Right | `lsl rdest rsource shift`                                                    | Führt einen logischen Shift aus.                                                                                      | `0001 0001` |
| And, Or, Xor                        | `AND/ORR/XOR rdest ra rb`                                                    | Führt den entsprechenden Befehl aus und speichert diesen in `rdest`.                                                  | `0001 0010` |
| Load to Register                    | `LDR reg, raddress, offset`                                                  | Lädt den im Speicher befindlichen Wert in das Register.                                                               | `0001 0011` |
| Store to Memory from Register       | `STR reg, raddress, offset`                                                  | Speichert den im Register befindlichen Wert in den Speicher                                                           | `0001 0100` |
| Pop / Push                          | `POP reg` / `PUSH reg`                                                       | Pushes the value stored in `reg` to the stack / Pops the value on the stack and stores it to `reg`                    | `0001 0101` |



