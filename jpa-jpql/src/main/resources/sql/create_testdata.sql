INSERT INTO "AUTHOR" ("ID", "FIRSTNAME", "LASTNAME", "VITA") VALUES (1000, 'Boris', 'Gloger', '2002 führte Boris Gloger sein erstes Scrum-Team beim österreichischen Mobilfunker ONE zum Erfolg.')
INSERT INTO "AUTHOR" ("ID", "FIRSTNAME", "LASTNAME", "VITA") VALUES (1001, 'Niels', 'Pfläging', 'Niels Pfläging ist Unternehmer, Beeinflusser und leidenschaftlicher Fürsprecher einer neuen, zeitgemäßen Führung.')

INSERT INTO "PUBLISHER" ("ID", "NAME") VALUES (2000, 'Carl Hanser Verlag GmbH & Co. KG')
INSERT INTO "PUBLISHER" ("ID", "NAME") VALUES (2001, 'Redline Verlag')

INSERT INTO "BOOK" ("ID", "TITLE", "PUBLISHER_ID") VALUES (3000, 'Komplexithoden: Clevere Wege zur (Wieder)Belebung von Unternehmen und Arbeit in Komplexität', 2001)
INSERT INTO "BOOK" ("ID", "TITLE", "PUBLISHER_ID") VALUES (3001, 'Organisation für Komplexität: Wie Arbeit wieder lebendig wird - und Höchstleistung entsteht', 2001)
INSERT INTO "BOOK" ("ID", "TITLE", "PUBLISHER_ID") VALUES (3010, 'Selbstorganisation braucht Führung: Die einfachen Geheimnisse agilen Managements', 2000)
INSERT INTO "BOOK" ("ID", "TITLE", "PUBLISHER_ID") VALUES (3011, 'Scrum: Produkte zuverlässig und schnell entwickeln', 2000)
INSERT INTO "BOOK" ("ID", "TITLE", "PUBLISHER_ID") VALUES (3012, 'Der agile Festpreis: Leitfaden für wirklich erfolgreiche IT-Projekt-Verträge', 2000)
INSERT INTO "BOOK" ("ID", "TITLE", "PUBLISHER_ID") VALUES (3013, 'Wie schätzt man in agilen Projekten: - oder wieso Scrum-Projekte erfolgreicher sind', 2000)

INSERT INTO "AUTHOR_BOOK" ("AUTHOR_ID", "BOOK_ID") VALUES (1001, 3000)
INSERT INTO "AUTHOR_BOOK" ("AUTHOR_ID", "BOOK_ID") VALUES (1001, 3001)
INSERT INTO "AUTHOR_BOOK" ("AUTHOR_ID", "BOOK_ID") VALUES (1000, 3010)
INSERT INTO "AUTHOR_BOOK" ("AUTHOR_ID", "BOOK_ID") VALUES (1000, 3011)
INSERT INTO "AUTHOR_BOOK" ("AUTHOR_ID", "BOOK_ID") VALUES (1000, 3012)
INSERT INTO "AUTHOR_BOOK" ("AUTHOR_ID", "BOOK_ID") VALUES (1000, 3013)

INSERT INTO "PHONEBOOK" ("ID", "FIRSTNAME", "LASTNAME", "PHONENUMBER") VALUES (5000, 'Boris', 'Gloger', '+49 23467763')
INSERT INTO "PHONEBOOK" ("ID", "FIRSTNAME", "LASTNAME", "PHONENUMBER") VALUES (5001, 'Bill', 'Gates', '+49 27499643')