package ibf.paf.orderapp.models;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class OrderSummary {
    private int oId;
    private String name;
    private String email;
    private float totalCost;

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

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public static OrderSummary create(SqlRowSet rs) {
        final OrderSummary summary = new OrderSummary();
        summary.setoId(rs.getInt("o_id"));
        summary.setName(rs.getString("name"));
        summary.setEmail(rs.getString("email"));
        summary.setTotalCost(rs.getFloat("total_cost"));
        return summary;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("oId", oId)
                .add("name", name)
                .add("email", email)
                .add("totalCost", totalCost)
                .build();
    }
}
