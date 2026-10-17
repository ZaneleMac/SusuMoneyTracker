
import java.util.*;

public class SavingsGroup {

    private String groupName;
    private double weeklyContribution;
    private int currentWeek;
    private List<Member> members;
    private Map<Integer, Map<String, Double>> weeklyPayments;
    private List<String> payoutHistory;

    public SavingsGroup(String groupName, double weeklyContribution) {
        if (weeklyContribution <= 0) {
        throw new IllegalArgumentException("Fixed weekly contribution amount must be greater than zero.");
    }
        this.groupName = groupName;
        this.weeklyContribution = weeklyContribution;
        this.currentWeek = 1;
        this.members = new ArrayList<>();
        this.weeklyPayments = new HashMap<>();
        this.payoutHistory = new ArrayList<>();
    }

    public void addMember(String id, String name) {
        if (id == null || id.trim().isEmpty() || name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("ID and Name cannot be empty.");
        }
        for (Member m : members) {
            if (m.getId().equals(id)) {
                throw new IllegalArgumentException("Member already exists.");
            }
            if (m.getName().equalsIgnoreCase(name)) {
                throw new IllegalArgumentException("Name already exists. Try a different name .");
            }

            if (m.getName().equalsIgnoreCase(name)) {
                throw new IllegalArgumentException("Name already exists. Try a different name .");
            }
        }
        members.add(new Member(id, name));
    }

    public void recordContribution(String memberId, int week, double amount) {
        Member member = null;
        for (Member m : members) {
            if (m.getId().equals(memberId)) {
                member = m;
                break;
            }
        }
        if (member == null) {
            throw new IllegalArgumentException("Unknown member.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }

        weeklyPayments.putIfAbsent(week, new HashMap<>());
        Map<String, Double> paymentsThisWeek = weeklyPayments.get(week);

        double alreadyPaid = paymentsThisWeek.getOrDefault(memberId, 0.0);
        double remainingDue = weeklyContribution - alreadyPaid;

        if (amount > remainingDue) {
            throw new IllegalArgumentException("Payment exceeds remaining balance: " + remainingDue);
        }

        paymentsThisWeek.put(memberId, alreadyPaid + amount);
    }

    public boolean closeCurrentWeek() {
        Map<String, Double> paymentsThisWeek = weeklyPayments.get(currentWeek);
        if (paymentsThisWeek == null) {
            return false;
        }

        for (Member member : members) {
            double paid = paymentsThisWeek.getOrDefault(member.getId(), 0.0);
            if (paid < weeklyContribution) {
                return false; // Someone owes money, week cannot close
            }
        }

        Member nextRecipient = null;
        for (Member m : members) {
            if (!m.isHasReceivedPot()) {
                nextRecipient = m;
                break;
            }
        }

        if (nextRecipient != null) {
            nextRecipient.setHasReceivedPot(true);
            double totalPot = weeklyContribution * members.size();
            payoutHistory.add("Week " + currentWeek + ": Pot of " + totalPot + " paid to " + nextRecipient.getName());
        }

        currentWeek++;
        return true;
    }

    public Map<String, Double> getOutstandingBalances(int week) {
        Map<String, Double> outstanding = new HashMap<>();
        Map<String, Double> paymentsThisWeek = weeklyPayments.getOrDefault(week, new HashMap<>());

        for (Member member : members) {
            double paid = paymentsThisWeek.getOrDefault(member.getId(), 0.0);
            double owed = weeklyContribution - paid;
            if (owed > 0) {
                outstanding.put(member.getName(), owed);
            }
        }
        return outstanding;
    }

    public int getCurrentWeek() {
        return currentWeek;
    }

    public List<String> getPayoutHistory() {
        return payoutHistory;
    }
}
