package controller;

public class Managers {
    private static InMemoryTasksManager defaultManager;

    //Получение Менеджера задач по умолчанию
    public static InMemoryTasksManager getDefault(){
        //Создать экземпляр если его ещё нет
        if (defaultManager == null){
            defaultManager = new InMemoryTasksManager();
        }
        return defaultManager;
    }
}
