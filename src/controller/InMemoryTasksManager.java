package controller;

import base.Epic;
import base.SubTask;
import base.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTasksManager implements TaskManager {
    protected HashMap<Integer, Task> taskList;    //Список всех задач
    protected InMemoryHistoryManager history;

    //Конструктор
    public InMemoryTasksManager() {
        taskList = new HashMap<>();
        history = new InMemoryHistoryManager();
    }

    //Получение списка всех задач (Эпики + Задачи + Подзадачи).
    @Override
    public HashMap<Integer, Task> getAllTasksList(){
        return taskList;
    }

    //Получение списка задач верхнего уровня (Эпики + Задачи).
    @Override
    public ArrayList<Task> getTasksList(){
        ArrayList<Task> result = new ArrayList<>();

        for (Integer key : taskList.keySet()) {
            if (taskList.get(key).getClass() != SubTask.class)
                result.add(taskList.get(key));
        }
        return result;
    }
    //Получение списка всех Эпиков.
    @Override
    public ArrayList<Task> getEpics(){
        ArrayList<Task> result = new ArrayList<>();

        for (Task task : taskList.values()) {
            if (task instanceof Epic)  //Если Эпик - добавить к результату
                result.add(task);
        }
        return result;
    }

    //Получение списка всех подзадач определённого Эпика.
    @Override
    public ArrayList<SubTask> getSubTasks(Epic epic){
        for (SubTask subTask : epic.getSubTasks()) {
            history.add(subTask);    //Сохранение обращения к Подзадачам в истории обращений
        }
        return epic.getSubTasks();
    }

    //Получение задачи по идентификатору.
    @Override
    public Task getTask(int num){
        //Сохранение обращения к задаче в истории обращений
        history.add(taskList.get(num));

        return taskList.get(num);
    }
    //Добавление новой Задачи, Эпика и Подзадачи. Сам объект должен передаваться в качестве параметра.
    @Override
    public void addTask(Task newTask){
        if (taskList.containsKey(newTask.getNum())){    //Проверка занятости идентификатора
            System.out.println("Задача с номером #" + newTask.getNum() + " уже существует!");
            return;
        }

        //Если номер задачи не задан вручную - сгенерировать его автоматически
        if (newTask.getNum() == null) {
            newTask.setNum(calcNewNum());
        }

        taskList.put(newTask.getNum(), newTask);    //Вставить Задачу в список менеджера
        if (newTask instanceof SubTask){
            ((SubTask)newTask).getEpic().getSubTasks().add((SubTask)newTask); //Присоединить Подзадачу к Эпику
        }
    }
    //Обновление задачи любого типа по идентификатору. Новая версия объекта передаётся в виде параметра.
    @Override
    public void updateTask(Task newTask){
        Task oldTask = getTask(newTask.getNum());       //Получение изменяемой задачи по идентификатору новой
        taskList.put(newTask.getNum(), newTask);        //Вставить задачу в список менеджера

        if (newTask.getClass() != oldTask.getClass()){
            System.out.println("Не совпал тип обновляемой задачи(" + oldTask.getClass()
                    + ") с типом задачи для обновления (" + newTask.getClass() + ")!");
            return;
        }

        if (newTask instanceof Epic){         //Обновление для Эпика
            for (SubTask subTask : ((Epic)oldTask).getSubTasks()) {
                //Заполнение списка Подзадач в новом Эпике
                ((Epic)newTask).getSubTasks().add(subTask);
                //Обновление ссылки на Эпик в старых Подзадачах
                subTask.setEpic((Epic) newTask);
            }
        } else if (newTask instanceof SubTask){ //Обновление для Подзадачи
            SubTask subTask = (SubTask)newTask;
            subTask.setEpic(((SubTask)oldTask).getEpic());          //Подключить к старому Эпику
            subTask.getEpic().getSubTasks().add(subTask);           //Добавить новую версию к Эпику
            subTask.getEpic().getSubTasks().remove(oldTask);        //Удалить из Эпика старую версию
        }
    }
    //Удаление ранее добавленных задач — всех и по идентификатору.
    @Override
    public void delTask(Integer num){
        if (num == null){   //Если идентификатор пустой - удаляем всё
            taskList.clear();
            history.clear();
        } else {
            Task delTask = getTask(num);                    //Получение экземпляра удаляемой задачи
            if (delTask instanceof SubTask){
                //Подзадачу - удалить из Эпика
                ((SubTask)delTask).getEpic().getSubTasks().remove(delTask);
            } else if (delTask instanceof Epic){
                //Подзадачи Эпика тоже нужно удалить из списка
                for (SubTask subTask : ((Epic) delTask).getSubTasks()) {
                    taskList.remove(subTask.getNum());
                    history.remove(subTask.getNum());
                }
            }
            taskList.remove(num);
            history.remove(num);
        }
    }

    //Формирование идентификатора задачи
    @Override
    public int calcNewNum(){
        int result = 0;
        //Поиск первого не занятого идентификатора
        for (int i = 1; i <= (taskList.size() + 1); i++) {
            if (!taskList.containsKey(i)){
                result = i;
                break;
            }
        }
        return result;
    }

    //Возвращает историю просмотренных задач.
    @Override
    public ArrayList<Task> history(){
        return (ArrayList)history.getHistory();
    }
}
