import domain.Company;
import domain.Developer;
import domain.Manager;
import domain.Tester;
import domain.Project;
import exceptions.InsufficientFundsException;
import exceptions.InvalidProjectException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class CompanyTest {

    private Company company;

    @BeforeEach
    void setUp() {
        company = new Company("TechCorp", 100_000);
    }

    @Test
    void testInitialState() {
        assertEquals("TechCorp", company.getName());
        assertEquals(100_000, company.getCash(), 0.01);
        assertEquals(0, company.getTotalReputation(), 0.01);
        assertEquals(0, company.getEmployeeCount());
        assertEquals(0, company.getProjectCount());
    }

    @Test
    void testHireEmployee() {
        Developer dev = new Developer("Alice", 5);
        company.hireEmployee(dev);
        assertEquals(1, company.getEmployeeCount());
    }

    @Test
    void testDeductCash() throws InsufficientFundsException {
        company.deductCash(30_000);
        assertEquals(70_000, company.getCash(), 0.01);
    }

    @Test
    void testDeductCashInsufficientFunds() {
        assertThrows(InsufficientFundsException.class, () -> company.deductCash(200_000));
    }

    @Test
    void testAddCashAndReputation() {
        company.addCash(50_000);
        company.addReputation(10);
        assertEquals(150_000, company.getCash(), 0.01);
        assertEquals(10, company.getTotalReputation(), 0.01);
    }

    @Test
    void testScore() {
        company.addReputation(5);
        double expected = company.getCash() + 5 * 100;
        assertEquals(expected, company.getScore(), 0.01);
    }

    @Test
    void testAddProject() throws InvalidProjectException {
        Project p = new Project("Test Project", 5000, 15000, Project.ProjectDifficulty.EASY, true, 3);
        company.addProject(p);
        assertEquals(1, company.getProjectCount());
    }

    @Test
    void testNullProjectThrows() {
        assertThrows(InvalidProjectException.class, () -> company.addProject(null));
    }

    @Test
    void testCompanyConstructorInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> new Company("", 1000));
        assertThrows(IllegalArgumentException.class, () -> new Company("X", -1));
    }

    @Test
    void testDeveloperProductivity() {
        Developer dev = new Developer("Bob", 3);
        assertEquals(1.2, dev.getProductivity(), 0.001);
        assertEquals("Developer", dev.getRole());
    }

    @Test
    void testManagerProductivity() {
        Manager mgr = new Manager("Carol", 4);
        assertEquals(0.8, mgr.getProductivity(), 0.001);
        assertEquals("Manager", mgr.getRole());
    }

    @Test
    void testTesterProductivity() {
        Tester tester = new Tester("Dave", 2);
        assertEquals(0.9, tester.getProductivity(), 0.001);
        assertEquals("Tester", tester.getRole());
    }
}
