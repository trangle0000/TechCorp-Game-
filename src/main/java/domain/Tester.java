cat > src/main/java/domain/Tester.java << 'EOF'
package domain;

public class Tester extends Employee {
    public Tester(String name, int skill) { super(name, skill); }
    public Tester(String name, int skill, double salary) { super(name, skill, salary); }

    @Override protected double calculateSalary() { return skill * 3000.0; }
    @Override public String getRole() { return "Tester"; }
    @Override public double getProductivity() { return 0.9; }
}
EOF