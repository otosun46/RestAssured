/**
 * @Author:Otosun Tarih :08/12/2020
 */
package pojo;

import java.util.List;

public class Task06 {
    private String listName;
    private List<Task06_Pojo> task06_pojos;

    public List<Task06_Pojo> getTask06_pojos() {
        return task06_pojos;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public void setTask06_pojos(List<Task06_Pojo> task06_pojos) {
        this.task06_pojos = task06_pojos;
    }

    @Override
    public String toString() {
        return "Task06{" +
                "task06_pojos=" + task06_pojos +
                '}';
    }
}
