insert into "RECIPE" ("ID", "LASTUPDATE", "NOOFPERSON", "PREAMBLE", "PREPARATION", "TITLE", "IMAGE_DESCRIPTION", "IMAGE_URL") values(10001, '2014-01-22 23:03:20', 2, 'Preample', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque convallis lacus at sagittis scelerisque. Donec at mauris ac mi pharetra.', 'Dies und Das', null, null)
insert into "INGREDIENT" ("ID", "ANNOTATION", "DESCRIPTION", "QUANTITY", "RECIPE_ID") values(100, null, 'Pfeffer', 'etwas', 10001)
insert into "INGREDIENT" ("ID", "ANNOTATION", "DESCRIPTION", "QUANTITY", "RECIPE_ID") values(101, 'ev. Mehrsalz', 'Salz', 'priese', 10001)
insert into "INGREDIENT" ("ID", "ANNOTATION", "DESCRIPTION", "QUANTITY", "RECIPE_ID") values(102, 'kalt gepresst', 'Olivenöl', '2 TL', 10001)
insert into "INGREDIENT" ("ID", "ANNOTATION", "DESCRIPTION", "QUANTITY", "RECIPE_ID") values(103, '', 'Ingwer', 'nach Lust & Laune', 10001)
insert into "TAGS" ("RECIPE_ID", "TAGS") values(10001, 'vegetarisch')
insert into "TAGS" ("RECIPE_ID", "TAGS") values(10001, 'hauptgericht')
insert into "RECIPE" ("ID", "LASTUPDATE", "NOOFPERSON", "PREAMBLE", "PREPARATION", "TITLE", "IMAGE_DESCRIPTION", "IMAGE_URL") values(10002, '2014-01-28 23:03:20', 4, 'Rezept für Hungrige', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque convallis lacus at sagittis scelerisque. Donec at mauris ac mi pharetra.', 'Pasta für alle', null, null)
insert into "INGREDIENT" ("ID", "ANNOTATION", "DESCRIPTION", "QUANTITY", "RECIPE_ID") values(200, null, 'Pasta', '250g', 10002)
insert into "INGREDIENT" ("ID", "ANNOTATION", "DESCRIPTION", "QUANTITY", "RECIPE_ID") values(201, 'ev. Mehrsalz', 'Salz', '5EL', 10002)
insert into "INGREDIENT" ("ID", "ANNOTATION", "DESCRIPTION", "QUANTITY", "RECIPE_ID") values(202, 'kalt gepresst', 'Olivenöl', '2 TL', 10002)
insert into "INGREDIENT" ("ID", "ANNOTATION", "DESCRIPTION", "QUANTITY", "RECIPE_ID") values(203, null, 'Pelati', 'eine Dose', 10002)
insert into "TAGS" ("RECIPE_ID", "TAGS") values(10002, 'fleisch')
insert into "TAGS" ("RECIPE_ID", "TAGS") values(10002, 'vorspeise')
insert into "RECIPE" ("ID", "LASTUPDATE", "NOOFPERSON", "PREAMBLE", "PREPARATION", "TITLE", "IMAGE_DESCRIPTION", "IMAGE_URL") values(10003, '2014-01-31 12:00:00', 4, 'Rezept für alle', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque convallis lacus at sagittis scelerisque. Donec at mauris ac mi pharetra.', 'was für ein sinnvoller Text', null, null)
insert into "INGREDIENT" ("ID", "ANNOTATION", "DESCRIPTION", "QUANTITY", "RECIPE_ID") values(300, null, 'Pasta', '300g', 10003)
insert into "INGREDIENT" ("ID", "ANNOTATION", "DESCRIPTION", "QUANTITY", "RECIPE_ID") values(301, 'ev. Mehrsalz', 'Salz', '1EL', 10003)
insert into "INGREDIENT" ("ID", "ANNOTATION", "DESCRIPTION", "QUANTITY", "RECIPE_ID") values(302, 'kalt gepresst', 'Olivenöl', '3 TL', 10003)
insert into "INGREDIENT" ("ID", "ANNOTATION", "DESCRIPTION", "QUANTITY", "RECIPE_ID") values(303, null, 'Pelati', 'grosse Dose', 10003)
insert into "TAGS" ("RECIPE_ID", "TAGS") values(10003, 'fleischlos')
insert into "RECIPE" ("ID", "LASTUPDATE", "NOOFPERSON", "PREAMBLE", "PREPARATION", "TITLE", "IMAGE_DESCRIPTION", "IMAGE_URL") values(10004, '2014-01-31 12:00:00', 4, null, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque convallis lacus at sagittis scelerisque. Donec at mauris ac mi pharetra.', 'Pfannkuchen', null, null)
insert into "INGREDIENT" ("ID", "ANNOTATION", "DESCRIPTION", "QUANTITY", "RECIPE_ID") values(400, null, 'Mehl', '300g', 10004)
insert into "INGREDIENT" ("ID", "ANNOTATION", "DESCRIPTION", "QUANTITY", "RECIPE_ID") values(401, 'ev. Mehrsalz', 'Salz', '1EL', 10004)
insert into "INGREDIENT" ("ID", "ANNOTATION", "DESCRIPTION", "QUANTITY", "RECIPE_ID") values(402, null, 'Zucker', '3 TL', 10004)
insert into "TAGS" ("RECIPE_ID", "TAGS") values(10004, 'fleischlos')
insert into "RECIPE" ("ID", "LASTUPDATE", "NOOFPERSON", "PREAMBLE", "PREPARATION", "TITLE", "IMAGE_DESCRIPTION", "IMAGE_URL") values(10005, '2015-01-31 12:00:00', 4, null, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque convallis lacus at sagittis scelerisque. Donec at mauris ac mi pharetra.', 'Fast fertig', null, null)
insert into "INGREDIENT" ("ID", "ANNOTATION", "DESCRIPTION", "QUANTITY", "RECIPE_ID") values(500, null, 'Mehl', '300g', 10005)
insert into "TAGS" ("RECIPE_ID", "TAGS") values(10004, 'vorspeise')
