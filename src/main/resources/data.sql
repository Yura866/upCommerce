  INSERT INTO USERS (name, last_name, email, enabled, password) VALUES
  ('Rok', 'Gupta', 'r.gupta@gmail.com', true, 'sdfasuhñaEFjsf'),
  ('Toro', 'Manos', 'tmanos@gmail.com', true, 'sdfasSADFFjsf'), 
  ('Vasya', 'Dub', 'vdub@gmail.com', true, 'jyrtujdtyj');
  
  INSERT INTO ROLES (role_name) VALUES
  ('ROLE_ADMIN'),
  ('ROLE_USER'),
  ('ROLE_MODERATOR') ;
  
  INSERT INTO USERS_ROLES(user_id, role_id) VALUES 
  (1,3),
  (2,2),
  (3,1);