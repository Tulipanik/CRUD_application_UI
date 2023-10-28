package org.example.UI;

import org.example.Services.HttpRequestService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Services.NotesService;
import org.example.Services.NotesServiceInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.*;

public class UI {

    private final NotesServiceInterface requests = new NotesService();
    private String groupId = "";

    public void Start() {
        System.out.println("Witamy w aplikacji Notatki!");
        System.out.println("Wpisz id grupy:");
        groupId = readFromUser();
        System.out.println("Id grupy:" + groupId);

        startMenu();

    }

    private void startMenu() {
        System.out.println("Wybierz operację którą chcesz wykonać:");
        System.out.println("1- Wyświetlanie");
        System.out.println("2- Tworzenie");
        System.out.println("3- Modyfikacja");
        System.out.println("4- Usuwanie");
        System.out.println("5- Zmień grupę");
        System.out.println("6- Wyłącz aplikację");

        String mode = readFromUser();
        choosingStartStage(mode);
    }

    private void choosingStartStage(String mode) {
        String newMode = "";

        switch (mode) {
            case "1" -> {
                System.out.println("1- Wyświetl wszystkie notatki");
                System.out.println("2- Wyświetl notatkę o wybranym id");
                System.out.println("3- Wyświetl wszystkie notatki grupy");

                newMode = readFromUser();
                choosingReadStage(newMode);
            }
            case "2" -> {
                System.out.println("1- Dodaj 1 notatkę");
                System.out.println("2- Dodaj wiele notatek");

                newMode = readFromUser();
                choosingCreateStage(newMode);
            }
            case "3" -> {
                System.out.println("Podaj id notatki, którą chcesz edytować:");
                String id = readFromUser();

                System.out.println("Twoja notatka przed edycją: ");
                printJsonAsTable("[" + requests.getTitleNote("/" + id)+ "]");

                choosingModify(id);
            }
            case "4" -> {
                System.out.println("1 - Wpisz id notatki, którą chcesz usunąć");
                System.out.println("2 - Usuń wszystkie notatki");
                System.out.println("3- Usuń wszystkie notatki grupy");

                newMode = readFromUser();
                choosingDeleteStage(newMode);
            }
            case "5" -> {
                System.out.println("Podaj nowe id grupy");
                groupId = readFromUser();
                System.out.println("Zmieniono id grupy na:" + groupId);
                startMenu();
            }
            case "6" -> {
                System.exit(0);
            }
            default -> {
                System.out.println("Podałeś nieprawidłową operację!");
                startMenu();
            }
        }
    }

    private void choosingModify(String id) {
        System.out.println("Podaj nowy tytuł notatki: ");
        String newTitle = readFromUser();
        System.out.println("Podaj nową treść notatki: ");
        String newContent = readFromUser();
        requests.updateNote(id, newTitle, newContent, groupId);
        System.out.println("Twoja notatka po edycji: ");
        printJsonAsTable("[" + requests.getTitleNote("/" + id) + "]");
        startMenu();
    }

    private void choosingReadStage(String mode) {

        switch (mode) {
            case "1" -> {
                System.out.println("To są wszystkie notatki");
                printJsonAsTable(requests.getAllNotes());
                startMenu();
            }
            case "2" -> {
                System.out.println("Wprowadź id notatki");
                String id = readFromUser();
                printJsonAsTable("[" +requests.getTitleNote("/" + id) + "]");
                startMenu();
            }
            case "3" -> {
                System.out.println("To są wszystkie notatki grupy");
                printJsonAsTable(requests.getGroupNotes(groupId));
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
            }

        }
    }

    private void addNote () {
        System.out.println("Podaj tytuł notatki:");
        String title = readFromUser();

        System.out.println("Podaj treść notatki:");
        String content = readFromUser();

        try {
            printJsonAsTable(requests.add(title, content, groupId));
            System.out.println("Pomyślnie dodano nową notatkę :D");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private void choosingDeleteStage(String mode) {

        switch (mode) {
            case "1" -> {
                System.out.println("Podaj id notatki, którą chcesz usunąć:");
                String id = readFromUser();
                boolean ok = requests.deleteChosenNote(id);
                if (ok) {
                    System.out.println("Pomyślnie usunięto notatkę");
                    startMenu();
                }
                System.out.println("Coś zrobiłeś źle drogi użytkownku");
                startMenu();
            }
            case "2" -> {
                boolean ok = requests.deleteAllNotes();
                if (ok) {
                    System.out.println("Pomyślnie usunięto wszystkie notatki");
                    startMenu();
                }
                System.out.println("Coś zrobiłeś źle drogi użytkownku");
                startMenu();
            }
            case "3" -> {
                boolean ok = requests.deleteAllGroupNotes(groupId);
                if (ok) {
                    System.out.println("Usunięto wszystkie notatki w grupie " + groupId);
                    startMenu();
                }
                System.out.println("Coś zrobiłeś źle drogi użytkownku");
                startMenu();
            }
            default -> {
                System.out.println("Wybrano niepoprawną opcję!");
                startMenu();
            }
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

    private static void printJsonAsTable(String jsonData) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonData);

            if (rootNode.isArray() && rootNode.size() > 0) {
                JsonNode firstObject = rootNode.get(0);
                List<String> headers = new ArrayList<>();
                for (Iterator<Map.Entry<String, JsonNode>> fields = firstObject.fields(); fields.hasNext(); ) {
                    Map.Entry<String, JsonNode> field = fields.next();
                    headers.add(field.getKey());
                }

                List<List<String>> rows = new ArrayList<>();

                for (JsonNode node : rootNode) {
                    List<String> row = new ArrayList<>();
                    for (String header : headers) {
                        JsonNode value = node.get(header);
                        row.add(value != null ? value.asText() : "");
                    }
                    rows.add(row);
                }

                int[] columnWidths = new int[headers.size()];
                Arrays.fill(columnWidths, 0);

                for (List<String> row : rows) {
                    for (int i = 0; i < row.size(); i++) {
                        int length = row.get(i).length();
                        if (length > columnWidths[i]) {
                            columnWidths[i] = length;
                        }
                    }
                }

                for (int i = 0; i < headers.size(); i++) {
                    System.out.printf("%-" + (columnWidths[i] + 2) + "s", headers.get(i));
                }
                System.out.println();

                for (List<String> row : rows) {
                    for (int i = 0; i < row.size(); i++) {
                        System.out.printf("%-" + (columnWidths[i] + 2) + "s", row.get(i));
                    }
                    System.out.println();
                }
            } else {
                System.out.println("Nie ma takich notatek");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 }
 
