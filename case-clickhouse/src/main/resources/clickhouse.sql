CREATE DATABASE IF NOT EXISTS tutorial;

DROP TABLE tutorial.quant_stock_minute5_quotation;

CREATE TABLE IF NOT EXISTS tutorial.quant_stock_minute5_quotation
(
    symbol     String,
    date       DateTime64(3),
    adjustment Decimal(8, 4),
    open       Decimal(6, 2),
    close      Decimal(6, 2),
    low        Decimal(6, 2),
    high       Decimal(6, 2),
    volume     UInt32
)
    engine = MergeTree()
    ORDER BY  (date)


