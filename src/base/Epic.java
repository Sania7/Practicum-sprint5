package base;

import util.TaskStatus;
import util.TaskType;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<SubTask> subTasks;

    public Epic(Integer num, String name, String details) {
        super(num, name, details, TaskType.EPIC);
        subTasks = new ArrayList<>();
    }

    //Получение статуса Эпика
    public TaskStatus getStatus(){
        TaskStatus result;
        int statusSum = TaskStatus.NEW.ordinal();

        if (subTasks.size() == 0){  //Если нет подзадач
            result = TaskStatus.NEW;  //Для пустого эпика вернуть статус NEW
        } else {  //Статус непустых эпиков определяется статусами подзадач
            for (SubTask subTask : subTasks)
                statusSum += subTask.getStatus().ordinal();

            if (statusSum == TaskStatus.NEW.ordinal()){ //Все подзадачи новые
                result = TaskStatus.NEW;
            } else if (statusSum == (TaskStatus.DONE.ordinal() * subTasks.size())){  //Все подзадачи выполнены
                result = TaskStatus.DONE;
            } else {
                result = TaskStatus.IN_PROGRESS;
            }
        }
        return result;
    }

    //Получение списка всех подзадач
    public ArrayList<SubTask> getSubTasks() {
        return subTasks;
    }

    //Отображение Эпика
    @Override
    public String toString() {
        return getNum() + "," + getType() + "," + getName() + "," + getStatus() + "," + getDetails() + ",";
    }
}
