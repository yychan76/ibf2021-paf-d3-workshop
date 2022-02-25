package ibf.paf.orderapp.repositories;

public class SQL {
    public static final String SQL_INSERT_ORDER = "insert into purchase_order (name, email) values (?, ?);";
    public static final String SQL_INSERT_LINE_ITEMS = "insert into line_item (product_name, quantity, unit_price, o_id) values (?, ?, ?, ?);";
    public static final String SQL_SELECT_PURCHASE_ORDER_SUMMARY = """
            select p.o_id, name, email, sum(quantity * unit_price) as total_cost
            from purchase_order p
            join line_item i
            ON p.o_id = i.o_id
            group by p.o_id
            """;
}
