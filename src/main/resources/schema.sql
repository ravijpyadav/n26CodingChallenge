DROP TABLE IF EXISTS transaction;

CREATE TABLE transaction (
  id BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  amount DECIMAL(10,2) DEFAULT '0.00',
  timestamp BIGINT(20) UNSIGNED,
  created_timestamp TIMESTAMP NOT NULL,
  last_modified_timestamp TIMESTAMP NOT NULL,
  PRIMARY KEY (id)
);