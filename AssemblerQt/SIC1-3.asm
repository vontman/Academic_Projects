p                             START   0000
         LDX                            #0
         LDT               MAXLEN
         LDCH    CHAR
         RMO     A,S
LOOP     LDCH    STR,X
         COMPR   A,S
         JEQ                 OUT
         TIXR    T
         JLT     LOOP
.         LDA     =X'FFFFFF'
lda memory,x
MEMORY              WORD                  1,2
MEMORY2              WORD                  1,2,2 
MEMORY3              WORD                  1,2,3,4
LDA          MEMORY,         X
         J       *
         SHIFTL  A,3
OUT      LDA     #STR
         ADDR    X,A
EXIT     J       EXIT
STR      BYTE    C'a b c d'
CHAR     BYTE    C'd'
MAXLEN   WORD    7
         END     p