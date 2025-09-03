import java.util.*;

public class manualSimuladorRRFeedback {
    
    static int QUANTUM;
    static int MAX_PROCESSOS;
    static int CICLOS_MAX;

    static final int TEMPO_IO_DISCO = 6;
    static final int TEMPO_IO_FITA = 8;
    static final int TEMPO_IO_IMPRESSORA = 10;

    static int tempoGlobal = 0;
    static int proximoPID = 1;

    static class PCB {
        int pid;
        int prioridade;
        int tempoRestanteCPU;
        String tipoIO;
        int tempoIO;
        String estado;
    }

    static Queue<PCB> filaAlta = new LinkedList<>();
    static Queue<PCB> filaBaixa = new LinkedList<>();
    static Queue<PCB> filaIO = new LinkedList<>();
    static List<PCB> finalizados = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o número máximo de processos: ");
        MAX_PROCESSOS = scanner.nextInt();

        System.out.print("Digite o quantum da CPU: ");
        QUANTUM = scanner.nextInt();

        System.out.print("Digite o número de ciclos para simulação: ");
        CICLOS_MAX = scanner.nextInt();

        gerarProcessosIniciais();

        while (tempoGlobal < CICLOS_MAX && finalizados.size() < MAX_PROCESSOS) {
            System.out.println("\n[CICLO " + tempoGlobal + "]");
            
            executarIO();
            executarCPU();

            tempoGlobal++;
        }

        System.out.println("\nSimulação encerrada em " + tempoGlobal + " ciclos.");
        System.out.println("Processos finalizados: " + finalizados.size() + "/" + MAX_PROCESSOS);
    }

    static void gerarProcessosIniciais() {
        Random rand = new Random();

        for (int i = 0; i < MAX_PROCESSOS; i++) {
            PCB p = new PCB();
            p.pid = proximoPID++;
            p.prioridade = 1;
            p.tempoRestanteCPU = rand.nextInt(6) + 5;
            p.estado = "pronto";
            p.tipoIO = gerarTipoIOAleatorio(rand);
            p.tempoIO = 0;
            filaAlta.add(p);
            System.out.println("Processo PID " + p.pid + " criado com " + p.tempoRestanteCPU + " unidades de CPU. I/O: " + p.tipoIO);
        }
    }

    static String gerarTipoIOAleatorio(Random rand) {
        int tipo = rand.nextInt(3);
        if (tipo == 0) return "disco";
        if (tipo == 1) return "fita";
        return "impressora";
    }

    static void executarCPU() {
        PCB processo = null;

        if (!filaAlta.isEmpty()) {
            processo = filaAlta.poll();
        } else if (!filaBaixa.isEmpty()) {
            processo = filaBaixa.poll();
        }

        if (processo == null) {
            System.out.println("Nenhum processo pronto para executar.");
            return;
        }

        processo.estado = "executando";
        System.out.println("Executando processo PID " + processo.pid + " (prioridade: " + (processo.prioridade == 1 ? "alta" : "baixa") + ")");

        int tempoExecutado = Math.min(QUANTUM, processo.tempoRestanteCPU);
        processo.tempoRestanteCPU -= tempoExecutado;

        if (processo.tempoRestanteCPU <= 0) {
            processo.estado = "finalizado";
            finalizados.add(processo);
            System.out.println("Processo PID " + processo.pid + " finalizado.");
        } else {
            boolean requisitaIO = new Random().nextBoolean();
            if (requisitaIO) {
                processo.estado = "bloqueado";
                processo.tempoIO = tempoDeIO(processo.tipoIO);
                filaIO.add(processo);
                System.out.println("Processo PID " + processo.pid + " requisitou I/O (" + processo.tipoIO + ")");
            } else {
                processo.estado = "pronto";
                processo.prioridade = 0;
                filaBaixa.add(processo);
                System.out.println("Processo PID " + processo.pid + " preemptado e movido para fila de baixa prioridade.");
            }
        }
    }

    static void executarIO() {
        List<PCB> prontos = new ArrayList<>();

        for (PCB p : filaIO) {
            p.tempoIO--;
            if (p.tempoIO <= 0) {
                prontos.add(p);
            }
        }

        for (PCB p : prontos) {
            filaIO.remove(p);
            p.estado = "pronto";

            if (p.tipoIO.equals("disco")) {
                p.prioridade = 0;
                filaBaixa.add(p);
            } else {
                p.prioridade = 1;
                filaAlta.add(p);
            }

            System.out.println("Processo PID " + p.pid + " concluiu I/O (" + p.tipoIO + ") e foi movido para fila " + (p.prioridade == 1 ? "alta" : "baixa"));
        }
    }

    static int tempoDeIO(String tipoIO) {
        switch (tipoIO) {
            case "disco": return TEMPO_IO_DISCO;
            case "fita": return TEMPO_IO_FITA;
            case "impressora": return TEMPO_IO_IMPRESSORA;
        }
        return 6;
    }
}
