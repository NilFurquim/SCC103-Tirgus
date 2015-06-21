package tirgus.serialization;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CSVSerializer
{
    public static <T extends OrderedSerializable> void write(T object, OutputStream stream)
    {
        //get object outputdata
        List<String> dataList = object.outputData();

        //write csv-formatted data
        Iterator<String> itr = dataList.iterator();
        PrintWriter writer = new PrintWriter(stream);
        while (itr.hasNext())
        {
            String data = itr.next();
            if (itr.hasNext())
            {
                writer.print(data + ", ");
            } else
            {
                writer.println(data);
            }
        }

        writer.flush();
    }

    public static <T extends OrderedSerializable> String write(T object)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        write(object, stream);
        try
        {
            return stream.toString("UTF-8");
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return stream.toString();
    }

    public static <T extends OrderedSerializable> T read(String line, Class<T> c)
    {
        //read line and split by commas
        String[] data = line.split(",\\s");

        //return instance of object
        T object = null;
        try
        {
            object = c.newInstance();
            object.inputData(Arrays.asList(data));
        } catch (InstantiationException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return object;
    }

    public static <T extends OrderedSerializable> List<T> read(InputStream stream, Class<T> c) throws IOException
    {
        List<T> items = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream)))
        {
            reader.lines().forEach(line -> items.add(read(line, c)));
        }
        return items;
    }
}
