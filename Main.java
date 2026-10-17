
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Susu Group Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Fixed Weekly Contribution Amount: ");
        double contribution = scanner.nextDouble();
        scanner.nextLine();

        SavingsGroup group = new SavingsGroup(name, contribution);

        boolean running = true;
        while (running) {
            System.out.println("\n--- " + name.toUpperCase() + " (Week " + group.getCurrentWeek() + ") ---");
            System.out.println("1. Add Member");
            System.out.println("2. Record Contribution");
            System.out.println("3. Close Current Week");
            System.out.println("4. View History & Outstanding");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        boolean memberAdded = false;
                        String mId = "";
                        String mName = "";
                        while (!memberAdded) {
                            System.out.print("Enter Member ID: ");
                            mId = scanner.nextLine();
                            System.out.print("Enter Member Name: ");
                            mName = scanner.nextLine();
                            try {
                                group.addMember(mId, mName);
                                memberAdded = true;
                            } catch (IllegalArgumentException e) {
                                System.out.println("Error: " + e.getMessage());
                            }
                        }
                        group.addMember(mId, mName);
                        System.out.println("Member added successfully!");

                        break;
                    case "2":
                        System.out.print("Enter Member ID: ");
                        String payId = scanner.nextLine();
                        System.out.print("Enter Amount to Pay: ");
                        double amt = scanner.nextDouble();
                        scanner.nextLine();
                        group.recordContribution(payId, group.getCurrentWeek(), amt);
                        System.out.println("Contribution recorded!");
                        break;
                    case "3":
                        if (group.closeCurrentWeek()) {
                            System.out.println("Week closed successfully! Payout distributed.");
                        } else {
                            System.out.println("Cannot close week! Outstanding balances remain:");
                            Map<String, Double> owes = group.getOutstandingBalances(group.getCurrentWeek());
                            for (Map.Entry<String, Double> entry : owes.entrySet()) {
                                System.out.println("- " + entry.getKey() + " owes: " + entry.getValue());
                            }
                        }
                        break;
                    case "4":
                        System.out.println("\n--- Payout History ---");
                        for (String h : group.getPayoutHistory()) {
                            System.out.println(h);
                        }

                        System.out.println("\n--- Current Outstanding ---");
                        Map<String, Double> currentOwes = group.getOutstandingBalances(group.getCurrentWeek());
                        if (currentOwes.isEmpty()) {
                            System.out.println("Everyone is fully paid up for this week!");
                        } else {
                            for (Map.Entry<String, Double> entry : currentOwes.entrySet()) {
                                System.out.println("- " + entry.getKey() + " owes: " + entry.getValue());
                            }
                        }
                        break;
                    case "5":
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        scanner.close();
    }
}
