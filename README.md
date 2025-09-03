# SO-2025-02 - Simulador de Escalonamento de Processos - Round Robin com Feedback
**Universidade Feevale | Curso de Sistemas Operacionais – Grupo 03**

**Integrantes:** Herik Rafael Peter da Silva, Mateus Piva e Augusto Barcelos Seibel

Simulador de Escalonamento de Processos baseado no algoritmo Round Robin com Feedback, desenvolvido em Java (sem orientação a objetos). Simula múltiplas filas de prioridade, operações de I/O e preempção de processos conforme regras de escalonamento.

---

## Descrição do Projeto

Este projeto consiste em um simulador de escalonamento de processos baseado no algoritmo **Round Robin com Feedback**, implementado em Java sem utilizar orientação a objetos. O simulador gerencia processos em múltiplas filas de prioridade, incluindo filas para operações de I/O (Disco, Fita Magnética e Impressora), e simula a execução dos processos considerando tempos de CPU e I/O variados e aleatórios.

O objetivo é aplicar os conceitos de gerenciamento de processos e escalonamento estudados em sistemas operacionais, explorando preempção, filas de prioridade e realimentação (feedback).

---

## Funcionalidades

- Criação de até 5 processos com tempos de CPU e tipos de I/O aleatórios.
- Execução de processos com fatia de tempo (quantum) fixo de 4 unidades.
- Gerenciamento de três filas: alta prioridade, baixa prioridade e fila de I/O.
- Preempção: processos que excedem quantum vão para fila de baixa prioridade.
- Operações de I/O que bloqueiam processos e os movem para fila específica conforme o tipo de I/O.
- Retorno dos processos de I/O para filas de acordo com a regra definida:
  - Disco → fila baixa
  - Fita magnética e impressora → fila alta
- Finalização automática quando todos os processos terminam a execução.

---

## Premissas adotadas

| Item                    | Valor / Descrição                       |
|-------------------------|---------------------------------------|
| Limite máximo de processos | 5 processos                         |
| Quantum                  | 4 unidades de tempo                   |
| Tempo CPU por processo   | Aleatório entre 5 e 10 unidades       |
| Tipos de I/O por processo| Aleatório entre disco, fita magnética e impressora |
| Tempo de I/O             | Disco: 6, Fita: 8, Impressora: 10    |
| Filas de escalonamento   | Alta prioridade, baixa prioridade, I/O |
| Ordem de entrada         | Novos processos → fila alta<br>Preempção → fila baixa<br>I/O → conforme tipo |

---

## Como Executar

1. Clone o repositório:

```bash
git clone https://github.com/hrpeter/SO-2025/02.git
```

2. Navegue até a pasta do projeto:

```bash
cd seu_repositorio
```

3. Compile o programa:

```bash
javac SimuladorRRFeedback.java
```

4. Execute o simulador:

```bash
java SimuladorRRFeedback
```

-> Exemplo de saída:
```bash
[CICLO 0]
Processo PID 1 criado com 9 unidades de CPU. I/O: disco
Processo PID 2 criado com 6 unidades de CPU. I/O: fita
Processo PID 3 criado com 7 unidades de CPU. I/O: impressora
Processo PID 4 criado com 8 unidades de CPU. I/O: disco
Processo PID 5 criado com 10 unidades de CPU. I/O: fita

[CICLO 1]
Executando processo PID 1 (prioridade: alta)
Processo PID 1 requisitou I/O (disco)

[CICLO 2]
Executando processo PID 2 (prioridade: alta)
Processo PID 2 preemptado e movido para fila de baixa prioridade.

...

[FIM]
Todos os processos finalizados em X ciclos.
```

---

## Considerações Finais

Este projeto reforçou a compreensão dos conceitos de escalonamento de processos e gerência de sistema operacional, além de proporcionar prática com estruturas de dados e manipulação de filas. A dinâmica de preempção e a influência dos tempos de I/O foram exploradas e compreendidas na prática.

---

## Contato

Para dúvidas ou sugestões, entre em contato com:  
**Herik Rafael Peter da Silva** — [herikpeter123@gmail.com](mailto:herikpeter123@gmail.com)

---

## Licença

Este projeto é para fins acadêmicos e **não possui licença para uso comercial**.
