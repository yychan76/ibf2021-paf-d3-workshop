package ibf.paf.orderapp.models;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class PurchaseOrder {
    private int oId;
    private String name;
    private String email;
    private List<LineItem> lineItems;

    public int getoId() {
        return oId;
    }

    public void setoId(int oId) {
        this.oId = oId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public static PurchaseOrder create(String jsonString) {
        try (JsonReader reader = Json.createReader(new StringReader(jsonString))) {
            JsonObject json = reader.readObject();
            final PurchaseOrder po = new PurchaseOrder();
            try {
                po.setoId(json.getInt("oId", -1));
            } catch (Exception e) {
            }
            po.setName(json.getString("name"));
            po.setEmail(json.getString("email"));
            JsonArray arr = json.getJsonArray("lineItems");
            List<LineItem> lineItems = new LinkedList<>();
            arr.forEach(v -> lineItems.add(LineItem.create((JsonObject) v)));
            po.setLineItems(lineItems);
            return po;
        } catch (Exception e) {
        }
        return null;
    }

    public JsonObject toJson() {
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        lineItems.stream()
                .forEach(v -> arrBuilder.add(v.toJson()));
        return Json.createObjectBuilder()
                .add("oId", oId)
                .add("name", name)
                .add("email", email)
                .add("lineItems", arrBuilder)
                .build();
    }

    @Override
    public String toString() {
        return "PurchaseOrder [email=" + email + ", name=" + name + ", oId=" + oId + "]";
    }

}
