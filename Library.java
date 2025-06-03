/**
 * 
 * Provides a menu-driven console interface for managing a library catalog.
 * Features include adding books, displaying the catalog, borrowing, returning books, and exiting the program.
 * 
 */

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The Library class is the entry point for the Library Management System.
 */
public class Library {

    private static Library library = new Library();
    static Scanner input = new Scanner(System.in);

    /**
     * Displays the main menu options.
     */
    public static void displayMenu() {
        System.out.println("\nPlease select one of the following:");
        System.out.println("1: Add Book to Library");
        System.out.println("2: Display Current Library Catalog");
        System.out.println("3: Borrow Book(s)");
        System.out.println("4: Return Book(s)");
        System.out.println("5: Exit");
        System.out.print("> ");
    }

    /**
     * Main method that runs the menu loop.
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        boolean exit = false;

        while (!exit) {
            displayMenu();
            try {
                int choice = input.nextInt();
                input.nextLine(); // consume newline
                switch (choice) {
                    case 1:
                        library.addBook(input);
                        break;
                    case 2:
                        System.out.println(library);
                        break;
                    case 3:
                        library.borrowBook(input);
                        break;
                    case 4:
                        library.returnBook(input);
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        System.out.println("Program by Chaitanya Patel");
                        exit = true;
                        break;
                    default:
                        System.out.println("Incorrect value entered\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("Incorrect value entered\n");
                input.nextLine(); // clear invalid input
            }
        }
    }
}


/**
 * Represents a general Book with properties and methods for library management.
 * 
 */

import java.util.Scanner;

/**
 * The Book class represents a generic book in the library system.
 * Provides functionality to add books, borrow and return books,
 * and check for equality based on book code.
 */
public class Book {

    protected int bookCode;
    protected String title;
    protected String author;
    protected String genre;
    protected int quantityInStock;
    private int borrowedQuantity = 0;

    /** Default constructor */
    public Book() {}

    /** Returns a string representation of the Book */
    public String toString() {
        return "Book: " + bookCode + " " + title + " " + quantityInStock +
               " Author: " + author + " Genre: " + genre;
    }

    /**
     * Updates the quantity of the book in stock.
     * @param amount positive for returns, negative for borrowing
     * @return true if update is successful; false otherwise
     */
    public boolean updateQuantity(int amount) {
        if (amount < 0) {
            int borrowAmount = -amount;
            if (borrowAmount > quantityInStock) return false;
            quantityInStock -= borrowAmount;
            borrowedQuantity += borrowAmount;
            return true;
        } else if (amount > 0) {
            if (amount > borrowedQuantity) return false;
            quantityInStock += amount;
            borrowedQuantity -= amount;
            return true;
        }
        return true;
    }

    /** Checks if two books are equal based on book code */
    public boolean isEqual(Book other) {
        return this.bookCode == other.bookCode;
    }

    /** Prompts the user to input a valid book code */
    public boolean inputCode(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Enter the code for the book: ");
                bookCode = Integer.parseInt(scanner.nextLine());
                return true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid code.");
            }
        }
    }

    /**
     * Prompts the user to input book details and adds the book.
     * @return true if successful; false otherwise
     */
    public boolean addBook(Scanner scanner) {
        if (!inputCode(scanner)) return false;
        try {
            System.out.print("Enter the title of the book: ");
            title = scanner.nextLine();

            System.out.print("Enter the author of the book: ");
            author = scanner.nextLine();

            while (true) {
                System.out.print("Enter the quantity of the book: ");
                try {
                    quantityInStock = Integer.parseInt(scanner.nextLine());
                    if (quantityInStock < 0) {
                        System.out.println("Invalid entry");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid entry");
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println("Invalid entry");
            return false;
        }
    }
}

/**
 * Extends Book to represent a Fiction Book with specific genre handling.
 */

import java.util.Scanner;

/**
 * FictionBook is a subclass of Book that represents books in the Fiction category.
 * It ensures the genre is always set to "Fiction" when the book is added.
 */
public class FictionBook extends Book {

    /** Default constructor that sets genre to Fiction */
    public FictionBook() {
        super();
        this.genre = "Fiction";
    }

    /** Adds a Fiction book and ensures the genre remains Fiction */
    @Override
    public boolean addBook(Scanner scanner) {
        boolean result = super.addBook(scanner);
        if (result) {
            this.genre = "Fiction";
        }
        return result;
    }

    /** Returns a string representation of the Fiction book */
    @Override
    public String toString() {
        return super.toString();
    }
}

/**
 * Extends Book to represent a Non-Fiction Book with a field of study.
 * 
 */

import java.util.Scanner;

/**
 * Represents a Non-Fiction book in the library.
 * Inherits from Book and adds a field of study.
 */
public class NonFictionBook extends Book {

    /** Field of study for the non-fiction book */
    private String fieldOfStudy;

    /** Constructor that sets genre to Non-Fiction */
    public NonFictionBook() {
        super();
        this.genre = "Non-Fiction";
    }

    /** Prompts user to enter book details including field of study */
    @Override
    public boolean addBook(Scanner scanner) {
        boolean result = super.addBook(scanner);
        if (result) {
            this.genre = "Non-Fiction";
            System.out.print("Enter the field of study: ");
            fieldOfStudy = scanner.nextLine().trim();
            while (fieldOfStudy.isEmpty()) {
                System.out.println("Please enter field of study:");
                fieldOfStudy = scanner.nextLine().trim();
            }
        }
        return result;
    }

    /** Returns string representation including field of study */
    @Override
    public String toString() {
        return super.toString() + " Field of Study: " + fieldOfStudy;
    }
}

/**
 * Extends Book to represent a Reference Book with a specific edition.
 * 
 */

import java.util.Scanner;

/**
 * Represents a Reference book in the library.
 * Cannot be borrowed and includes edition information.
 */
public class ReferenceBook extends Book {

    /** Edition of the reference book */
    private String edition;

    /** Constructor sets genre to Reference */
    public ReferenceBook() {
        super();
        this.genre = "Reference";
    }

    /** Prompts user to enter book details including edition */
    @Override
    public boolean addBook(Scanner scanner) {
        boolean result = super.addBook(scanner);
        if (result) {
            this.genre = "Reference";
            System.out.print("Enter the edition of the book: ");
            edition = scanner.nextLine().trim();
            while (edition.isEmpty()) {
                System.out.println("Please enter edition of the book:");
                edition = scanner.nextLine().trim();
            }
        }
        return result;
    }

    /** Returns string representation including edition */
    @Override
    public String toString() {
        return super.toString() + " Edition: " + edition;
    }
}


/**
 * Purpose: Manages a collection of Book objects and provides operations for library management.
 */

import java.util.Scanner;

/**
 * The Library class represents a collection of books in a catalog.
 * It allows adding books, borrowing and returning them, and displaying the catalog.
 * 
 * @author Chaitanya Patel
 * @version 01/06/2025
 */
public class Library {

    /** Array to store up to 20 Book objects. */
    private Book[] catalog;

    /** Tracks the number of books currently in the catalog. */
    private int numBooks;

    /** Constructs a new Library with a fixed-size catalog of 20 books. */
    public Library() {
        catalog = new Book[20];
        numBooks = 0;
    }

    /** Returns a string representation of the library catalog. */
    @Override
    public String toString() {
        if (numBooks == 0) {
            return "Library Catalog is empty";
        }

        String result = "Library Catalog:\n";
        for (int i = 0; i < numBooks; i++) {
            result += catalog[i].toString() + "\n";
        }

        return result;
    }

    /**
     * Checks if a book with the same title and author exists.
     * 
     * @param book the book to search for
     * @return index if found, -1 otherwise
     */
    public int alreadyExists(Book book) {
        for (int i = 0; i < numBooks; i++) {
            if (catalog[i].isEqual(book)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Adds a book to the catalog.
     * 
     * @param scanner Scanner object for input
     * @return true if the book is added successfully; false otherwise
     */
    public boolean addBook(Scanner scanner) {
        if (numBooks == catalog.length) {
            System.out.println("Library is full. Cannot add more books.");
            return false;
        }

        String choice;
        while (true) {
            System.out.print("Do you wish to add a Fiction(f), Non-Fiction(n), or Reference(r) book? ");
            choice = scanner.nextLine().trim().toLowerCase();
            if (choice.equals("f") || choice.equals("n") || choice.equals("r")) {
                break;
            } else {
                System.out.println("Invalid entry");
            }
        }

        Book newBook;
        switch (choice) {
            case "f":
                newBook = new FictionBook();
                break;
            case "n":
                newBook = new NonFictionBook();
                break;
            case "r":
                newBook = new ReferenceBook();
                break;
            default:
                System.out.println("Invalid entry");
                return false;
        }

        if (!newBook.addBook(scanner)) {
            return false;
        }

        for (int i = 0; i < numBooks; i++) {
            if (catalog[i].bookCode == newBook.bookCode) {
                System.out.println("Book code already exists.");
                return false;
            }
        }

        int i = alreadyExists(newBook);
        if (i != -1) {
            catalog[i].updateQuantity(newBook.quantityInStock);
        } else {
            catalog[numBooks] = newBook;
            numBooks++;
        }

        return true;
    }

    /**
     * Allows the user to borrow a book by code and quantity.
     * 
     * @param scanner Scanner object
     * @return true if successful, false otherwise
     */
    public boolean borrowBook(Scanner scanner) {
        if (numBooks == 0) {
            System.out.println("Error...could not borrow book");
            return false;
        }

        int code;
        try {
            System.out.print("Enter the code for the book: ");
            code = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error...could not borrow book");
            return false;
        }

        int index = -1;
        for (int i = 0; i < numBooks; i++) {
            if (catalog[i].bookCode == code) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            System.out.println("Error...book not in the catalog.");
            return false;
        }

        Book book = catalog[index];
        if (book.genre.equals("Reference")) {
            System.out.println("Reference books cannot be borrowed.");
            return false;
        }

        System.out.print("Enter valid quantity to borrow: ");
        int qty = Integer.parseInt(scanner.nextLine().trim());
        if (qty < 1) {
            System.out.println("Invalid quantity...");
            return false;
        }

        if (book.updateQuantity(-qty)) {
            return true;
        } else {
            System.out.println("Error...trying to borrow more than available quantity");
            return false;
        }
    }

    /**
     * Allows the user to return a borrowed book by code and quantity.
     * 
     * @param scanner Scanner object
     * @return true if returned successfully, false otherwise
     */
    public boolean returnBook(Scanner scanner) {
        if (numBooks == 0) {
            System.out.println("Error...could not return book");
            return false;
        }

        int code;
        try {
            System.out.print("Enter the code for the book: ");
            code = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error...could not return book");
            return false;
        }

        int index = -1;
        for (int i = 0; i < numBooks; i++) {
            if (catalog[i].bookCode == code) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            System.out.println("Error...could not return book");
            return false;
        }

        Book book = catalog[index];
        System.out.print("Enter valid quantity to return: ");
        int qty = Integer.parseInt(scanner.nextLine().trim());

        if (qty < 1) {
            System.out.println("Invalid quantity...");
            return false;
        }

        if (book.updateQuantity(qty)) {
            return true;
        } else {
            System.out.println("Error...Trying to return more than checkout quantity");
            return false;
        }
    }
}

