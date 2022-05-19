package base;

import util.TaskStatus;
import util.TaskType;

public class Task {
    private String name;        //Название
    private String details;     //Описание/дополнение
    private Integer num;        //Уникальный идентификационный номер задачи
    private TaskStatus status;  //Статус задачи
    private TaskType taskType;//Тмп задачи

    //Конструктор класса
    public Task(Integer num, String name, String details) {
        this.num = num;
        this.name = name;
        this.details = details;
        status = TaskStatus.NEW;
        this.taskType = TaskType.TASK;
    }

    //Конструктор класса
    public Task(Integer num, String name, String details, TaskType taskType) {
        this.num = num;
        this.name = name;
        this.details = details;
        status = TaskStatus.NEW;
        this.taskType = taskType;
    }

    //Конструктор
    public Task(String name, String details, TaskType taskType) {
        this.name = name;
        this.details = details;
        status = TaskStatus.NEW;
        this.taskType = taskType;
    }

    //Получение имени задачи
    public String getName() {
        return name;
    }

    //Задание имени задачи
    public void setName(String name) {
        this.name = name;
    }
    //Получение описания
    public String getDetails() {
        return details;
    }

    //Задание описания
    public void setDetails(String details) {
        this.details = details;
    }

    //Получние номера задачи
    public Integer getNum() {
        return num;
    }

    //Задание номера задачи
    public void setNum(Integer num) {
        this.num = num;
    }

    //Получение статуса
    public TaskStatus getStatus() {
        return status;
    }

    //Задание статуса
    public void setStatus(TaskStatus status) {
        this.status = status;
    }
    //Получение типа задачи
    public TaskType getType(){
        return taskType;
    }

    //Отображение задачи
    @Override
    public String toString() {
        return getNum() + "," + getType() + "," + getName() + "," + getStatus() + "," + getDetails() + ",";
    }
}
