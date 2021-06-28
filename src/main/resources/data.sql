CREATE TABLE users (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  birthday date NULL
);

INSERT INTO users (id, name, birthday) VALUES
  (1,'Leon Eduardo', CURRENT_TIMESTAMP),
  (2,'Vic', CURRENT_TIMESTAMP),
  (3,'Celso', CURRENT_TIMESTAMP);