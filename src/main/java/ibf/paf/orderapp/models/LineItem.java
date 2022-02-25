package ibf.paf.orderapp.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class LineItem {
    private int itemId;
    private String productName;
    private int quantity;
    private float unitPrice;
    private int oId;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getoId() {
        return oId;
    }

    public void setoId(int oId) {
        this.oId = oId;
    }

    public static LineItem create(JsonObject json) {
        LineItem lineItem = new LineItem();
        try {
            lineItem.setItemId(json.getInt("itemId", -1));
        } catch (Exception e) {
        }
        lineItem.setProductName(json.getString("productName"));
        lineItem.setQuantity(json.getInt("quantity"));
        lineItem.setUnitPrice((float) json.getJsonNumber("unitPrice").doubleValue());
        try {
            lineItem.setoId(json.getInt("oId", -1));
        } catch (Exception e) {
        }
        return lineItem;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("itemId", itemId)
                .add("productName", productName)
                .add("quantity", quantity)
                .add("unitPrice", unitPrice)
                .add("oId", oId)
                .build();
    }

    @Override
    public String toString() {
        return "LineItem [itemId=" + itemId + ", oId=" + oId + ", productName=" + productName + ", quantity=" + quantity
                + ", unitPrice=" + unitPrice + "]";
    }


}
