# Expressões Regulares (Analíse Léxica)

## Aqui serão definidas as expressões para identificação de tokens

### Obs.: Para facilitar a escrita iremos utilizar algumas atribuições básicas tais como:

- letra => [a-z]
- numero => [0-9]
- opRelacional => (>|<|=|<=|>=|==|<>)
- opAritmetico => (+|-|*|/|%)
- opAtribuicao => (=)
- caracterEspecial => (,|;|(|)|}|{)
- palavrasReservadas => (int|float|char|while|main|if|else)

| Tipo | Expressão regular |
| ------| ------|
| Inteiro | (numero)+ |
| Real | (numero)+((.)(numero))* |
| Char | (')(numero|letra)(') |
| Identificador | (letra)(numero|letra)* |
| Operador Relacional | opRelacional |
| Operador Aritmético | opAritmetico |
| Operador Atribuição | opAtribuicao |
| Caracter Especial | caracterEspecial |
| Palavras Reservadas | palavrasReservadas |

Inteiro e Real

![image](https://user-images.githubusercontent.com/43259452/191248751-29fd073c-232a-4392-8377-0b518f2e60f9.png)

Char

![image](https://user-images.githubusercontent.com/43259452/191248896-ff15f3c3-157d-4f96-8f6c-31623a43a04f.png)

Identificador

![image](https://user-images.githubusercontent.com/43259452/191248995-b04d49fa-6eb9-42b3-84f4-c02c9401a8c4.png)

Operador Relacional

![image](https://user-images.githubusercontent.com/43259452/191250344-59e23e1f-48c9-49e9-a3a0-07f3d19550d4.png)

Operador aritmético

![image](https://user-images.githubusercontent.com/43259452/191250792-2386e137-0dd0-4f05-b451-e136872214d3.png)

Operador atribuição

![image](https://user-images.githubusercontent.com/43259452/191250866-0f4cff87-4187-4895-b011-3039b9fa7d26.png)

Caracter Especial

![image](https://user-images.githubusercontent.com/43259452/191251136-bb321d1c-403a-4d21-b8a1-090a377e4371.png)

Palavras reservadas

![image](https://user-images.githubusercontent.com/43259452/191251397-92cdfb7b-5b2b-4996-9981-abbf8f5c76b9.png)

Automato completo e refinado

![img.png](img.png)