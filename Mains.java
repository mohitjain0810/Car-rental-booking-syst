import java.util.*;

// Car class represents a car available for rent.
class Car {
   private String carId;
   private String brand;
   private String model;
   private double basePricePerDay;
   private boolean isAvailable;

   // Constructor initializes the car details and sets availability to true.
   public Car(String carId, String brand, String model, double basePricePerDay) {
      this.carId = carId;
      this.brand = brand;
      this.model = model;
      this.basePricePerDay = basePricePerDay;
      this.isAvailable = true;
   }

   // Getter methods for car attributes.
   public String getCarId() {
      return carId;
   }

   public String getBrand() {
      return brand;
   }

   public String getModel() {
      return model;
   }

   // Calculate the rental price based on the number of days.
   public double calculatePrice(int rentalDays) {
      return basePricePerDay * rentalDays;
   }

   // Check if the car is available for rent.
   public boolean isAvailable() {
      return isAvailable;
   }

   // Rent the car.
   public void rent() {
      isAvailable = false;
   }

   // Return the car.
   public void returnCar() {
      isAvailable = true;
   }
}

// Customer class represents a customer who wants to rent a car.
class Customer {
   private String customerId;
   private String name;

   // Constructor initializes customer details.
   public Customer(String customerId, String name) {
      this.customerId = customerId;
      this.name = name;
   }

   // Getter methods for customer attributes.
   public String getCustomerId() {
      return customerId;
   }

   public String getName() {
      return name;
   }
}

// Rental class represents a rental transaction between a customer and a car.
class Rental {
   private Car car;
   private Customer customer;
   private int days;

   // Constructor initializes the rental details.
   public Rental(Car car, Customer customer, int days) {
      this.car = car;
      this.customer = customer;
      this.days = days;
   }

   // Getter methods for rental attributes.
   public Car getCar() {
      return car;
   }

   public Customer getCustomer() {
      return customer;
   }

   public int getDays() {
      return days;
   }
}

// CarRentalSystem class manages the car rental system operations.
class CarRentalSystem {
   private List<Car> cars;
   private List<Customer> customers;
   private List<Rental> rentals;

   // Constructor initializes lists to store cars, customers, and rentals.
   public CarRentalSystem() {
      cars = new ArrayList<>();
      customers = new ArrayList<>();
      rentals = new ArrayList<>();
   }

   // Add a new car to the system.
   public void addCar(Car car) {
      cars.add(car);
   }

   // Add a new customer to the system.
   public void addCustomer(Customer customer) {
      customers.add(customer);
   }

   // Rent a car to a customer for a specified number of days.
   public void rentCar(Car car, Customer customer, int days) {
      if (car.isAvailable()) {
         car.rent();
         rentals.add(new Rental(car, customer, days));
      } else {
         System.out.println("Car is not available for rent.");
      }
   }

   // Return a rented car back to the system.
   public void returnCar(Car car) {
      car.returnCar();
      Rental rentalToRemove = null;
      for (Rental rental : rentals) {
         if (rental.getCar() == car) {
            rentalToRemove = rental;
            break;
         }
      }
      if (rentalToRemove != null) {
         rentals.remove(rentalToRemove);
      } else {
         System.out.println("Car was not rented.");
      }
   }

   // Display a menu to interact with the car rental system.
   public void menu() {
      Scanner scanner = new Scanner(System.in);

      while (true) {
         System.out.println("----- Car Rental System -----");
         System.out.println("1. Rent a Car");
         System.out.println("2. Return a Car");
         System.out.println("3. Exit");
         System.out.println("Enter your Choice: ");

         int Choice = scanner.nextInt();
         scanner.nextLine(); // Consume newLine

         if (Choice == 1) {
            System.out.println("\n--- Rent a Car ----\n");
            System.out.println("Enter your name: ");
            String customerName = scanner.nextLine();

            System.out.println("\n Available Cars: ");
            // iterates the car in Cars arraylist by for each loop
            for (Car car : cars) {
               if (car.isAvailable()) {
                  System.out.println(car.getCarId() + " - " + car.getBrand() + " - " + car.getModel());
               }
            }
            System.out.println("\nEnter the car ID you want to rent:  ");
            String carId = scanner.nextLine();

            System.out.println("Enter the number of days for rental: ");
            int rentalDays = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // object
            Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
            addCustomer(newCustomer);

            Car selectedCar = null;
            for (Car car : cars) {
               if (car.getCarId().equals(carId) && car.isAvailable()) {
                  selectedCar = car;
                  break;
               }
            }
            if (selectedCar != null) {
               double totalPrice = selectedCar.calculatePrice(rentalDays);
               System.out.println("\n --- Rental Information ---\n");
               System.out.println("Customer ID: " + newCustomer.getCustomerId());
               System.out.println("Customer Name: " + newCustomer.getName());
               System.out.println("Car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
               System.out.println("Rental Days: " + rentalDays);
               System.out.printf("Total Price: $%.2f%n", totalPrice);

               System.out.println("\nConfirm rental (Y/N): ");
               String confirm = scanner.nextLine();

               if (confirm.equalsIgnoreCase("Y")) {
                  rentCar(selectedCar, newCustomer, rentalDays);
                  System.out.println("\nCar rented Successfully");
               } else {
                  System.out.println("\nRental Canceled");
               }
            } else {
               System.out.println("Invalid car selection or car not available for rent.");
            }
         } else if (Choice == 2) {
            System.out.println("\n--- Return a Car ---\n");
            System.out.println("Enter the car ID you want to return: ");
            String carId = scanner.nextLine();

            Car carToReturn = null;
            for (Car car : cars) {
               if (car.getCarId().equals(carId) && !car.isAvailable()) {
                  carToReturn = car;
                  break;
               }
            }
            if (carToReturn != null) {
               Customer customer = null;
               for (Rental rental : rentals) {
                  if (rental.getCar() == carToReturn) {
                     customer = rental.getCustomer();
                     break;
                  }
               }
               if (customer != null) {
                  returnCar(carToReturn);
                  System.out.println("Car returned successfully by " + customer.getName());
               } else {
                  System.out.println("Car was not rented or rental information is missing.");
               }
            } else {
               System.out.println("Invalid car ID or car is not rented.");
            }
         } else if (Choice == 3) {
            break;
         } else {
            System.out.println("Invalid choice. Please enter a valid option.");
         }
      }
      System.out.println("\nThank you for using the Car Rental System!");
   }
}

// Main class to run the Car Rental System.
public class Mains {
   public static void main(String[] args) {
      CarRentalSystem rentalSystem = new CarRentalSystem();

      // Add cars to the system.
      rentalSystem.addCar(new Car("C001", "Toyota", "Camry", 60.0)); // Different base price per day for each car
      rentalSystem.addCar(new Car("C002", "Honda", "Accord", 70.0));
      rentalSystem.addCar(new Car("C003", "Mahindra", "Thar", 150.0));

      // Start the menu of the car rental system.
      rentalSystem.menu();
   }
}
