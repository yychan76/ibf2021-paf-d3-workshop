package ibf.paf.orderapp.repositories;

import static ibf.paf.orderapp.repositories.SQL.SQL_INSERT_LINE_ITEMS;
import static ibf.paf.orderapp.repositories.SQL.SQL_INSERT_ORDER;
import static ibf.paf.orderapp.repositories.SQL.SQL_SELECT_PURCHASE_ORDER_SUMMARY;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import ibf.paf.orderapp.models.LineItem;
import ibf.paf.orderapp.models.OrderSummary;
import ibf.paf.orderapp.models.PurchaseOrder;

@Repository
public class OrderRepository {

    @Autowired
    private JdbcTemplate template;

    public List<OrderSummary> getAllOrders() {
        List<OrderSummary> result = new LinkedList<>();
        final SqlRowSet rs = template.queryForRowSet(SQL_SELECT_PURCHASE_ORDER_SUMMARY);
        while (rs.next()) {
            result.add(OrderSummary.create(rs));
        }
        return result;
    }

    public int addOrder(PurchaseOrder order) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_INSERT_ORDER,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, order.getName());
            ps.setString(2, order.getEmail());
            return ps;
        }, keyHolder);

        try {
            final List<LineItem> items = order.getLineItems();
            int oId = keyHolder.getKey().intValue();
            template.batchUpdate(SQL_INSERT_LINE_ITEMS,
                    new BatchPreparedStatementSetter() {

                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setString(1, items.get(i).getProductName());
                            ps.setInt(2, items.get(i).getQuantity());
                            ps.setFloat(3, items.get(i).getUnitPrice());
                            ps.setInt(4, oId);
                        }

                        public int getBatchSize() {
                            return items.size();
                        }

                    });
            return oId;
        } catch (NullPointerException e) {
            return -1;
        }

    }
}
