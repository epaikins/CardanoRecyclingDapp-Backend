USE cardanorecyclingdappdb;
INSERT INTO customer_type (id, name, is_active) VALUES (1, "Household", True);
INSERT INTO customer_type (id, name, is_active) VALUES (2, "Recycling Company", True);
INSERT INTO customer_type (id, name, is_active) VALUES (3, "Institution/Enterprise", True);

INSERT INTO identity_type(id, name, is_active) VALUES (1, "Passport", True);
INSERT INTO identity_type (id, name, is_active) VALUES (2, "Drivers Licence", True);
INSERT INTO identity_type (id, name, is_active) VALUES (3, "Ghana Card", True);

INSERT INTO role (id, name, is_active) VALUES (1, "User", True);
INSERT INTO role (id, name, is_active) VALUES (2, "Admin", True);