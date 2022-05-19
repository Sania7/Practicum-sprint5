package controller;

import base.Task;

import java.util.List;

public interface HistoryManager {
    //Добавление нового просмотра задачи
    void add(Task task);

    //Удаление просмотра из истории
    void remove(int id);

    //Получение истории последних просмотров
    List<Task> getHistory();
}
