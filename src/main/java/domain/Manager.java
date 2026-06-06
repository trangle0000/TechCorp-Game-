cat > src/main/java/domain/Manager.java << 'EOF'
package domain;

public class Manager extends Employee {
    public Manager(String name, int skill) { super(name, skill); }
    public Manager(String name, int skill, double salary) { super(name, skill, salary); }

    @Override protected double calculateSalary() { return skill * 7000.0; }
    @Override public String getRole() { return "Manager"; }
    @Override public double getProductivity() { return 0.8; }
}
EOF