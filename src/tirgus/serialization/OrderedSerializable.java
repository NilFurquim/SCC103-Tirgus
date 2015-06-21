package tirgus.serialization;

import java.util.List;

public interface OrderedSerializable
{
    List<String> outputData();

    void inputData(List<String> itr);
}
