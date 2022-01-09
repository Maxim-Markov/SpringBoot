Create Table roles (
 id bigint primary key not null auto_increment,
  role VARCHAR(45)
);

Create Table users (
  id bigint primary key not null auto_increment,
  age TINYINT,
  name VARCHAR(255) UNIQUE,
  password VARCHAR(255),
  persist_date timestamp,
  last_redaction_date timestamp,
  email VARCHAR(255),
  about TEXT,
  city VARCHAR(255),
  link_site VARCHAR(255),
  link_github VARCHAR(255),
  link_vk VARCHAR(255),
  is_enabled boolean,
  is_deleted boolean,
  image_link VARCHAR(255),
  nickname VARCHAR(255)
);

Create Table users_roles (
    user_id bigint not null,
    role_id bigint not null,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);
