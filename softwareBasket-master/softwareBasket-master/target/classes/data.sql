insert into software_available values (1,1,'software1');
insert into software_details values (1,'0',false,1,'1');
insert into software_details values (2,'20',true,1,'2');
insert into software_available values (2,2,'software2');
insert into software_details values (3,'0',false,2,'1');
insert into software_details values (4,'20',true,2,'2');
ALTER TABLE SELECTED_SOFTWARE_ABS_DB DROP COLUMN ID;
ALTER TABLE SELECTED_SOFTWARE_ABS_DB ADD ID INT IDENTITY(1,1) PRIMARY KEY 
