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

Create Table question (
  id bigint primary key not null auto_increment,
  title VARCHAR(255) not null,
  description TEXT not null,
  persist_date timestamp,
  view_count INT,
  user_id bigint not null,
  is_deleted boolean,
  last_redaction_date timestamp,
  FOREIGN KEY (user_id) REFERENCES users(id)
);

Create Table answer (
  id bigint primary key not null auto_increment,
  user_id bigint,
  question_id bigint,
  html_body TEXT,
  persist_date timestamp,
  is_helpful boolean,
  is_deleted boolean,
  is_deleted_by_moderator boolean,
  date_accept_time timestamp,
  update_date timestamp,
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (question_id) REFERENCES question(id)
);

Create Table votes_on_answers (
  id bigint primary key not null auto_increment,
  user_id bigint not null,
  answer_id bigint not null,
  persist_date timestamp,
  vote varchar(255),
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (answer_id) REFERENCES answer(id)
);

Create Table votes_on_questions (
  id bigint primary key not null auto_increment,
  user_id bigint not null,
  question_id bigint not null,
  persist_date timestamp,
  vote varchar(255),
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (question_id) REFERENCES question(id)
);

Create Table tag (
  id bigint primary key not null auto_increment,
  name VARCHAR(45),
  description TEXT,
  persist_date timestamp
);

Create Table question_has_tag (
  tag_id bigint not null,
  question_id bigint not null,
  FOREIGN KEY (tag_id) REFERENCES tag(id),
  FOREIGN KEY (question_id) REFERENCES question(id)
);

Create Table reputation (
  id bigint primary key not null auto_increment,
  count int,
  persist_date timestamp,
  type INT,
  author_id bigint,
  sender_id bigint,
  question_id bigint,
  answer_id bigint,
  FOREIGN KEY (question_id) REFERENCES question(id),
  FOREIGN KEY (author_id) REFERENCES users(id),
  FOREIGN KEY (sender_id) REFERENCES users(id),
  FOREIGN KEY (answer_id) REFERENCES answer(id)
);