cat > src/main/java/domain/Developer.java << 'EOF'
package domain;

public class Developer extends Employee {
    public Developer(String name, int skill) { super(name, skill); }
    public Developer(String name, int skill, double salary) { super(name, skill, salary); }

    @Override protected double calculateSalary() { return skill * 5000.0; }
    @Override public String getRole() { return "Developer"; }
    @Override public double getProductivity() { return 1.2; }
}
EOF
