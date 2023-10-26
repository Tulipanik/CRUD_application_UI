package UI;

import UI.HttpRequest.HttpRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

public class UI {

    private final HttpRequest requests = new HttpRequest();

    public void Start() {
        System.out.println("Witamy w aplikacji Notatki!");
        startMenu();

    }

    private void startMenu() {
        System.out.println("Wybierz operację którą chcesz wykonać:");
        System.out.println("1- Wyświetlanie");
        System.out.println("2- Tworzenie");
        System.out.println("3- Modyfikacja");
        System.out.println("4- Usuwanie");

        String mode = readFromUser();
        choosingStartStage(mode);
    }

    private void choosingStartStage(String mode) {
        String newMode = "";

        switch (mode) {
            case "1":
                System.out.println("1- Wyświetl wszystkie notatki");
                System.out.println("2- Wyświetl notatkę o wybranym id");

                newMode = readFromUser();
                choosingReadStage(newMode);

                break;
            case "2":
                System.out.println("1- Dodaj 1 notatkę");
                System.out.println("2- Dodaj wiele notatek");

                newMode = readFromUser();
                choosingCreateStage(newMode);

                break;
            case "3":
                System.out.println("Podaj id notatki, którą chcesz edytować:");
                String id = readFromUser();

                break;
            case "4":
                System.out.println("1 - Wpisz tytuł notatki, którą chcesz usunąć");
                System.out.println("2 - Usuń wszystkie notatki");

                newMode = readFromUser();
                choosingDeleteStage(newMode);

                break;
            default:
                System.out.println("Podałeś nieprawidłową operację!");
                startMenu();
                newMode = readFromUser();
                choosingStartStage(newMode);

                break;
        }
    }

    private void choosingReadStage(String mode) {

        switch (mode) {
            case "1" -> {
                System.out.println("To są wszystkie notatki");
                System.out.println(requests.getAllNotes());
                startMenu();
            }
            case "2" -> {
                System.out.println("Wprowadź id notatki");
                String id = readFromUser();
                System.out.println(requests.getTitleNote("/" + id));
                startMenu();
            }
            default -> {
                System.out.println("Podałeś nieprawidłową operację!");
                startMenu();
            }
        }
    }

    private void choosingCreateStage(String mode) {

        switch (mode) {
            case "1" -> {
                addNote();
                startMenu();
            }
            case "2" -> {
                boolean next = true;
                while (next) {
                    addNote();
                    System.out.println("Chcesz dodać następną?\n1- tak \n2- nie");
                    String isNext = readFromUser();
                    if (isNext.equals("2")) {
                        next = false;
                    }
                }
                System.out.println("Dzięki za dodanie notatek");
                startMenu();
            }
            default -> {
                System.out.println("Podałeś nieprawidłową operację!");
                startMenu();
                break;
            }

        }
    }

    private void addNote () {
        System.out.println("Podaj tytuł notatki:");
        String title = readFromUser();

        System.out.println("Podaj treść notatki:");
        String content = readFromUser();

        try {
            System.out.println(requests.add(title, content));
            System.out.println("Pomyślnie dodano nową notatkę :D");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private void choosingDeleteStage(String mode) {

        switch (mode) {
            case "1":
                System.out.println("Podaj id notatki, którą chcesz usunąć:");
                String id = readFromUser();


            case "3":


            default:
                System.out.println("Wybrano niepoprawną opcję!");
                startMenu();
                String newMode = readFromUser();
                choosingStartStage(newMode);
        }
    }

    private String readFromUser() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
