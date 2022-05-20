package controller;

public class Managers {

    private Managers() {

    }
    //Получение Менеджера задач по умолчанию
    public static InMemoryTasksManager getDefault(){
        return new InMemoryTasksManager();
    }
    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
