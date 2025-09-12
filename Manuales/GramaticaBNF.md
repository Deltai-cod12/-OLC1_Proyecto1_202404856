# Gramatica BNF
### Nombre: Angel Emanuel Rodriguez Corado
### Carnet: 202404856

# Gramatica 

En este documento se encuentra toda la gramatica del proyecto realizado, se encuentran los terminales, como los no terminales, como las diferentes producciones utilizadas

# TERMINALES
- MENOR
- MAYOR
- MENOR_DIAGONAL
- AFD
- AP
- NOMBRE
- TRANSICIONES
- VER_AUTOMATAS
- DESC
- IGUAL
- LLAVE_IZQ
- LLAVE_DER
- PAREN_IZQ
- PAREN_DER
- COMA
- PUNTO_COMA
- DOS_PUNTOS
- FLECHA
- OR
- ID
- NUMERO
- CADENA
- LAMBDA
- STACKTOP

# NO TERMINALES
- programa
- contenido
- elemento
- automata
- afd
- ap
- estados
- alfabeto
- alfabeto_pila
- estado
- estado_inicial
- estados_aceptacion
- transiciones
- transiciones_afd
- transiciones_ap
- transicion_afd
- transicion_ap
- opciones_afd
- opciones_ap
- opcion_afd
- opcion_afd
- opcion_afd
- opcion_ap
- comando
- simbolo

# PRODUCCIONES
```
programa ::= contenido

contenido ::= 
    | contenido elemento

elemento ::= 
    automata
    | comando

automata ::= 
    afd
    | ap

afd ::= 
    MENOR AFD NOMBRE IGUAL CADENA MAYOR
        ID IGUAL LLAVE_IZQ estados LLAVE_DER PUNTO_COMA
        ID IGUAL LLAVE_IZQ alfabeto LLAVE_DER PUNTO_COMA
        ID IGUAL LLAVE_IZQ estado_inicial LLAVE_DER PUNTO_COMA
        ID IGUAL LLAVE_IZQ estados_aceptacion LLAVE_DER PUNTO_COMA
        TRANSICIONES DOS_PUNTOS transiciones_afd
        MENOR_DIAGONAL AFD MAYOR
    | MENOR AFD NOMBRE IGUAL CADENA error

ap ::= 
    MENOR AP NOMBRE IGUAL CADENA MAYOR
        ID IGUAL LLAVE_IZQ estados LLAVE_DER PUNTO_COMA
        ID IGUAL LLAVE_IZQ alfabeto LLAVE_DER PUNTO_COMA
        ID IGUAL LLAVE_IZQ alfabeto_pila LLAVE_DER PUNTO_COMA
        ID IGUAL LLAVE_IZQ estado_inicial LLAVE_DER PUNTO_COMA
        ID IGUAL LLAVE_IZQ estados_aceptacion LLAVE_DER PUNTO_COMA
        TRANSICIONES DOS_PUNTOS transiciones_ap
        MENOR_DIAGONAL AP MAYOR
    | MENOR AP NOMBRE IGUAL CADENA error

estados ::= 
    estado
    | estados COMA estado

estado ::= 
    ID
    | NUMERO

alfabeto ::= 
    ID
    | NUMERO
    | alfabeto COMA ID
    | alfabeto COMA NUMERO

alfabeto_pila ::= 
    simbolo
    | alfabeto_pila COMA simbolo
    | error COMA simbolo
    | alfabeto_pila error simbolo

simbolo ::= 
    ID
    | LAMBDA
    | STACKTOP
    | error

estado_inicial ::= 
    estado
    | error

estados_aceptacion ::= 
    estado
    | estados_aceptacion COMA estado
    | error COMA estado
    | estados_aceptacion error estado

transiciones_afd ::= 
    transicion_afd
    | transiciones_afd transicion_afd
    | error transicion_afd
    | transiciones_afd error transicion_afd

transicion_afd ::= 
    estado FLECHA opciones_afd PUNTO_COMA
    | error FLECHA opciones_afd PUNTO_COMA
    | estado error opciones_afd PUNTO_COMA
    | estado FLECHA error PUNTO_COMA

opciones_afd ::= 
    opcion_afd
    | opciones_afd OR opcion_afd
    | error OR opcion_afd
    | opciones_afd error opcion_afd

opcion_afd ::= 
    ID COMA estado
    | NUMERO COMA estado
    | error COMA estado
    | ID error estado

transiciones_ap ::= 
    transicion_ap
    | transiciones_ap transicion_ap
    | error transicion_ap
    | transiciones_ap error transicion_ap

transicion_ap ::= 
    estado PAREN_IZQ simbolo PAREN_DER FLECHA PAREN_IZQ simbolo PAREN_DER COMA estado DOS_PUNTOS PAREN_IZQ simbolo PAREN_DER PUNTO_COMA
    | error PAREN_IZQ simbolo PAREN_DER FLECHA PAREN_IZQ simbolo PAREN_DER COMA estado DOS_PUNTOS PAREN_IZQ simbolo PAREN_DER PUNTO_COMA
    | estado error simbolo PAREN_DER FLECHA PAREN_IZQ simbolo PAREN_DER COMA estado DOS_PUNTOS PAREN_IZQ simbolo PAREN_DER PUNTO_COMA

comando ::= 
    VER_AUTOMATAS PAREN_IZQ PAREN_DER PUNTO_COMA
    | DESC PAREN_IZQ ID PAREN_DER PUNTO_COMA
    | ID PAREN_IZQ CADENA PAREN_DER PUNTO_COMA
    | error PAREN_IZQ PAREN_DER PUNTO_COMA
    | error PAREN_IZQ ID PAREN_DER PUNTO_COMA
    | error PAREN_IZQ CADENA PAREN_DER PUNTO_COMA

```