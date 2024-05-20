import java.util.Scanner;

/*
                                          --------------------------------
                                        * Documentation for SRTF Algorithm *
                                          --------------------------------

                        * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
                        * Completion Time for every process                                   *
                        * Already BT Time for each process before Completion Time             *
                        * { Waiting time for each process = CT - AT - Temp_BT }     *
                        * Average Waiting Time = (Total WT)/(number_of_processes)             *
                        * WT : Waiting Time                                                   *
                        * AT : Arrival Time                                                   *
                        * BT : Burst Time                                                     *
                        * CT : Completion Time                                                *
                        * BTb: Already BT Time before                                         *
                        * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

*/

public class main {
    static long[] BTb = new long[100000]; // Already BT Time before

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Gantt Chart
        // waiting time : 'sol.1' = end_of_process "CT" - arrival_time "AT" - burst_time "Temp_BT"
        //                'sol.2' = start_of_the_last_execution_in_process - arrival_time - amount_executed_before_from_process (Section)

        System.out.print("Enter the number of processes: ");
        int number_of_processes = scanner.nextInt();

        long[] AT = new long[number_of_processes];
        long[] BT = new long[number_of_processes];
        long[] Temp_BT = new long[number_of_processes];
        long[] CT = new long[number_of_processes];
        long Total_BT = 0; // Summation for Burst Time

        // Loop for Reading Inputs
        for (int i = 0; i < number_of_processes; i++) {
            System.out.print("Enter process " + (i + 1) + " Arrival time: ");
            AT[i] = scanner.nextLong();
            System.out.print("Enter process " + (i + 1) + " Burst time: ");
            BT[i] = scanner.nextLong();
            Temp_BT[i] = BT[i];
            Total_BT += BT[i];
        }

        System.out.println("Process       " + "  Arrival.T     " + "  Brust.T             ");
        for (int i = 0; i < number_of_processes; i++) {
            System.out.println("    " + (i + 1) + "                " + AT[i] + "             " + BT[i]);
        }

        long timer = 0;
        int remaining_processes = number_of_processes;

        // For Calculate Completion Time for each process
        while (remaining_processes > 0) {
            int min_index = -1;
            long min_value = Long.MAX_VALUE;

            // Find the process with the shortest remaining time
            for (int i = 0; i < number_of_processes; i++) {
                if (AT[i] <= timer && BT[i] < min_value && BT[i] > 0) {
                    min_value = BT[i];
                    min_index = i;
                }
            }

            // If no process is found, increment the timer
            if (min_index == -1) {
                timer++;
            } else {
                // Execute the process
                BT[min_index]--;
                timer++;

                // Check if the process is completed
                if (BT[min_index] == 0) {
                    CT[min_index] = timer;
                    BTb[min_index] = CT[min_index] - BT[min_index] - AT[min_index];
                    remaining_processes--;
                }
            }
        }

        long Total_WT = 0;
        for (int i = 0; i < number_of_processes; i++) {
            long WT = CT[i] - AT[i] - Temp_BT[i];
            Total_WT += WT;
        }
        System.out.println("Average Waiting Time: " + String.format("%.1f", (double) Total_WT / number_of_processes));
    }
}

