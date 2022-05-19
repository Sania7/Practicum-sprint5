package controller;

import base.Epic;
import base.SubTask;
import base.Task;

import java.util.ArrayList;
import java.util.HashMap;

public interface TaskManager {
    //Получение списка всех задач (Эпики + Задачи + Подзадачи).
    HashMap<Integer, Task> getAllTasksList();

    //Получение списка задач (Эпики + Задачи).
    ArrayList<Task> getTasksList();

    //Получение списка всех Эпиков.
    ArrayList<Task> getEpics();

    //Получение списка всех подзадач определённого эпика.
    ArrayList<SubTask> getSubTasks(Epic epic);

    //Получение задачи по идентификатору.
    Task getTask(int num);

    //Добавление новой Задачи, Эпика и Подзадачи. Сам объект должен передаваться в качестве параметра.
    void addTask(Task newTask);

    //Обновление задачи любого типа по идентификатору. Новая версия объекта передаётся в виде параметра.
    void updateTask(Task newTask);

    //Удаление ранее добавленных задач — всех и по идентификатору.
    void delTask(Integer num);

    //Формирование идентификатора задачи
    int calcNewNum();

    //Возвращает последние 10 просмотренных задач.
    // (полученных через getTask(), изменённых updateTask() или удалённых delTask()).
    ArrayList<Task> history();
}
