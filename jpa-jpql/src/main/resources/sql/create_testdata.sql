INSERT INTO "AUTHOR" ("ID", "FIRSTNAME", "LASTNAME", "VITA") VALUES (1000, 'Boris', 'Glogger', '2002 führte Boris Gloger sein erstes Scrum-Team beim österreichischen Mobilfunker ONE zum Erfolg.')
INSERT INTO "AUTHOR" ("ID", "FIRSTNAME", "LASTNAME", "VITA") VALUES (1001, 'Niels', 'Pfläging', 'Niels Pfläging ist Unternehmer, Beeinflusser und leidenschaftlicher Fürsprecher einer neuen, zeitgemäßen Führung.')

INSERT INTO "PUBLISHER" ("ID", "NAME") VALUES (2000, 'Carl Hanser Verlag GmbH & Co. KG')
INSERT INTO "PUBLISHER" ("ID", "NAME") VALUES (2001, 'Redline Verlag')

INSERT INTO "BOOK" ("ID", "TITLE", "PUBLISHER_ID") VALUES (3000, 'Komplexithoden: Clevere Wege zur (Wieder)Belebung von Unternehmen und Arbeit in Komplexität', 2001)
INSERT INTO "BOOK" ("ID", "TITLE", "PUBLISHER_ID") VALUES (3001, 'Scrum: Produkte zuverlässig und schnell entwickeln', 2000)
INSERT INTO "BOOK" ("ID", "TITLE", "PUBLISHER_ID") VALUES (3010, 'Selbstorganisation braucht Führung: Die einfachen Geheimnisse agilen Managements', 2000)

INSERT INTO "AUTHOR_BOOK" ("AUTHOR_ID", "BOOK_ID") VALUES (1000, 3000)
INSERT INTO "AUTHOR_BOOK" ("AUTHOR_ID", "BOOK_ID") VALUES (1000, 3001)
INSERT INTO "AUTHOR_BOOK" ("AUTHOR_ID", "BOOK_ID") VALUES (1001, 3010)

INSERT INTO "PHONEBOOK" ("ID", "FIRSTNAME", "LASTNAME", "PHONENUMBER") VALUES (5000, 'Boris', 'Glogger', '+49 23467763')
INSERT INTO "PHONEBOOK" ("ID", "FIRSTNAME", "LASTNAME", "PHONENUMBER") VALUES (5001, 'Bill', 'Gates', '+49 27499643')