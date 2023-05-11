package ru.mingazov.services;

import com.google.gson.*;
import ru.mingazov.models.Fiber;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

public class FiberGsonSerializer implements JsonSerializer<Fiber> {

    @Override
    public JsonElement serialize(Fiber fiber, Type type, JsonSerializationContext context) {

        JsonObject jsonObject = new JsonObject();
        Gson gson = new Gson();
        String files = gson.toJson(fiber.getFiles());

        JsonArray jsonFilesArray = new JsonArray();

        if (fiber.getFiles() != null)
            fiber.getFiles()
                .stream()
                .map(file -> {
                    JsonObject temp = new JsonObject();
                    temp.addProperty("id", file.getId());
                    temp.addProperty("name", file.getName());
                    return temp;
                }).forEach(jsonFilesArray::add);

        jsonObject.add("files", jsonFilesArray);
        jsonObject.addProperty("id", fiber.getId());
        jsonObject.addProperty("comment", fiber.getCommentTo());
        jsonObject.addProperty("section", fiber.getSection());
        jsonObject.addProperty("creation_date", fiber.creationDateToString());

        return jsonObject;
    }

}
