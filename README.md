# SusuMoneyTracker

A Java-based console application designed to manage a Rotating Savings and Credit Association  / "Susu" group .

## Features
- **Member Management**: Add members with unique IDs and names while preventing duplicate registrations.
- **Contribution Tracking**: Record fixed weekly contributions for members and prevent overpayments.
- **Strict Week-Closing Rules**: Enforces that a week cannot be closed and payouts are paused if any member has an outstanding balance.
- **Automated Payout Rotation**: Automatically distributes the total pot to the next eligible member once a week is successfully closed.
- **Reporting**: View historical payouts and current weekly outstanding balances.

---

## Project File Structure
- `Member.java`: Blueprint representing a group member and their payout status.
- `SavingsGroup.java`: Core logic engine handling contributions, validation, and week-closing rules.
- `Main.java`: Interactive command-line interface  for running the application.
- `SavingsGroupTest.java`: Automated JUnit test suite covering core business rules.
