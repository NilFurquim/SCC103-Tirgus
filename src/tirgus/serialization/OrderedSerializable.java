package tirgus.serialization;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class OrderedSerializable
{
    private int id;

    public OrderedSerializable()
    {
        id = hashCode();
    }

    public List<String> outputData()
    {
        List<String> data = new ArrayList<>();
        data.add(Integer.toString(id));
        data.addAll(customOutputData());

        return data;
    }

    protected abstract List<String> customOutputData();

    public void inputData(List<String> data)
    {
        Iterator<String> itr = data.iterator();
        id = Integer.parseInt(itr.next());
        customInputData(itr);
    }

    public abstract void customInputData(Iterator<String> itr);

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }
}
