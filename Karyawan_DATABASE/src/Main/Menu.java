package Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

class Employee {
	private static final Random random = new Random(); //Like a struct data
    String id;
    String name;
    String gender;
    String position;
    double salary;

    public Employee(String name, String gender, String position) {
        this.id = generateRandomid();
        this.name = name;
        this.gender = gender;
        this.position = position;
        this.salary = getBaseSalary(position);
    }
    private double getBaseSalary(String position) {
        switch (position) {
            case "Manager": return 8000000;
            case "Supervisor": return 6000000;
            case "Admin": return 4000000;
            default: throw new IllegalArgumentException("Invalid position: " + position);
        }
    }
    public String getPosition1() {
        return position;
    }
    
    public void addBonus(double percentage) {
        salary += salary * (percentage / 100);
    }
    
    private String generateRandomid() {
        char letter1 = (char) ('A' + random.nextInt(26));
        char letter2 = (char) ('A' + random.nextInt(26));
        int number = random.nextInt(10000); 

        return String.format("%c%c-%04d", letter1, letter2, number);
}
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getGender() {
        return gender;
    }
    
    public String getPosition() {
        return position;
    }
    
    public double getSalary() {
        return salary;
    }


public class Menu {
	private static final List<Employee> employees = new ArrayList<>();
	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		while (true) {
			System.out.println("Employee database: \n1.Insert Data\n2.View Data\n3.Update Data\n4.Delete Data\n5.Exit\n>>");
			int option = scan.nextInt();
			scan.nextLine();
			switch (option) {
			case 1: 
				insertData();
				break;
			case 2:
				viewData();
				break;
			case 3:
				updateData(scan);
				break;
			case 4:
				deleteData(scan);
				break;
			case 5:
				System.out.println("Exiting...");
				return;
			default:
				System.out.println("Invalid Input!\n");
				scan.nextLine();
			}
		}

	}
	public static void insertData() {
		System.out.println("Input Name [>=3]: ");
		String name = scan.nextLine();
		if (name.length() < 3 || !name.matches("[a-zA-Z ]+")) {
            System.out.println("Invalid name format!");
            return;
        }
		System.out.println("Input Gender [Male | Female] (Case Sensitive): ");
		String gender = scan.nextLine();
		if (!gender.equals("Male") && !gender.equals("Female")) {
            System.out.println("Invalid gender!");
            return;
        }
		System.out.println("Input Position [Admin | Supervisor | Manager] (Case Sensitive): ");
		String position = scan.nextLine();
		if (!Arrays.asList("Manager", "Supervisor", "Admin").contains(position)) {
            System.out.println("Invalid position!");
            return;
        }
		Employee newEmployee = new Employee(name, gender, position);
        employees.add(newEmployee);
        checkAndApplyBonuses();
        
        System.out.println("Employee added successfully!");
        scan.nextLine();
}
	public static void viewData() {
		if (employees.isEmpty()) {
		   System.out.println("No employees found.");
		   return;
		}

		employees.sort(Comparator.comparing(Employee::getName));
		
		System.out.println("+-------+------------+----------------+------------+-------------+------------+");
		System.out.println("| No	| ID         | Name           | Gender     | Position    | Salary     |");
		System.out.println("+-------+------------+----------------+------------+-------------+------------+");
		int i = 1;
		for (Employee e : employees) {
			
		    System.out.printf("| %-2s	| %-10s | %-14s | %-10s | %-11s | Rp %7.0f |\n",
		    i,e.getId(), e.getName(), e.getGender(), e.getPosition1(), e.getSalary());
		    i++;
		}

		    System.out.println("+-------+------------+----------------+------------+-------------+------------+");

	}
	private static void updateData(Scanner scanner) {
        System.out.println("Enter Employee ID to update: ");
        String id = scanner.nextLine();
        for (Employee e : employees) {
            if (e.getId().equals(id)) {
                System.out.println("Enter new name[>=3]: ");
                String newName = scanner.nextLine();
                if (!newName.equals("0") && newName.length() >= 3) {
					e.setName(newName);
				}
                System.out.println("Enter new gender[Male or Female] (Case Sensitive): ");
                String newGender = scanner.nextLine();
                if (!newGender.equals("0") && (newGender.equals("Laki-Laki") || newGender.equals("Perempuan"))) {
					e.setGender(newGender);
				}
                System.out.println("Enter new position [Admin | Supervisor | Manager] (Case Sensitive): ");
                String newPosition = scanner.nextLine();
                if (!newPosition.equals("0") && Arrays.asList("Admin", "Supervisor", "Manager").contains(newPosition)) {
					e.setPosition(newPosition);
				}
                System.out.println("Employee updated successfully!");
                return;
            }
        }
        System.out.println("Employee not found!");
    }
	private static void deleteData(Scanner scanner) {
        System.out.print("Enter Employee ID to delete: ");
        String id = scanner.nextLine();
        if (id == "0") {
			return;
		}
        employees.removeIf(e -> e.getId().equals(id));
        System.out.println("Employee deleted successfully!");
    }
	private static void checkAndApplyBonuses() {
        Map<String, Integer> positionCount = new HashMap<>();
        
        for (Employee e : employees) {
            positionCount.put(e.getPosition1(), positionCount.getOrDefault(e.getPosition1(), 0) + 1);
        }
        
        for (String position : positionCount.keySet()) {
            int count = positionCount.get(position);
            if (count % 4 == 0) {
                applyBonus(position);
            }
        }
    }
	private static void applyBonus(String position) {
        double bonusPercentage = switch (position) {
            case "Manager" -> 10;
            case "Supervisor" -> 7.5;
            case "Admin" -> 5;
            default -> 0;
        };
        
        int count = 0;
        for (Employee e : employees) {
            if (e.getPosition1().equals(position)) {
                e.addBonus(bonusPercentage);
                count++;
                if (count == 3) break;
            }
        }
        System.out.println("Bonus applied to first 3 " + position + " employees!");
    }
}


public void setName(String name) {
    this.name = name;
}
public void setGender(String gender) {
	this.gender = gender;
}
public void setPosition(String position) {
    this.position = position;
    this.salary = getBaseSalary(position);
}
}
