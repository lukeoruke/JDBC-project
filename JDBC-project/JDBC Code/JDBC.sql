--the Writing Groups table holds all relevant information for a writing group
--a writing group consists of 1+ authors but in our table we only record the head author
--the primary key, GroupName, uniquely identifies a writing group and copies into the Books table
CREATE TABLE WritingGroups
(
    --primarykey for 
    GroupName       VARCHAR(50) NOT NULL,
    HeadWriter      VARCHAR(50),
    YearFormed      INT,
    Subject         VARCHAR(50),
    CONSTRAINT pk_WritingGroups PRIMARY KEY (GroupName)
);

--The books table hold information about a particular book in our DB
--it tells us the book title, year published and number of pages, as well as
--the groupname that wrote it and the publisher name that published it
--both the groupname and publisher name are foreign keys
CREATE TABLE Books
(
      GroupName         VARCHAR(50) NOT NULL,
      BookTitle         VARCHAR(50) NOT NULL,
      PublisherName     VARCHAR(50) NOT NULL,
      YearPublished     INT,
      NumberPages       INT,
    CONSTRAINT Books_WritingGroups_fk FOREIGN KEY (GroupName)
        REFERENCES WritingGroups(GroupName),
 CONSTRAINT Books_Publishers_fk FOREIGN KEY(PublisherName)
      REFERENCES Publishers(PublisherName),
      CONSTRAINT pk_Books PRIMARY KEY(GroupName, BookTitle)   
);

--the publishers table holds all information about Publishing houses of the books
--in our database
--we need to know their name, address, phone, and email, but just their name
--is enough to uniquely identify them
CREATE TABLE Publishers
(
    PublisherName       VARCHAR(50) NOT NULL,
    PublisherAddress    VARCHAR(50),
    PublisherPhone      VARCHAR(20),
    PublisherEmail      VARCHAR(50),
    CONSTRAINT pk_Publishers PRIMARY KEY(PublisherName)
);

INSERT INTO WritingGroups (GroupName, HeadWriter, YearFormed, Subject)
    VALUES  ('The Ampersands','Jane Austen',2000,'Classic'),
            ('Writers Collective','James Pottersby',2000,'Fiction'),
            ('Ghost Bars','Drake',2000,'Poetry'),
            ('Bio Hazards','S.E. Hilton',2000,'Non-Fiction'),
            ('Capturing Space and Time','Isaac Asimov',2000,'Science Fiction'),
            ('Nat20 Stories','Gary Gygax',1980,'Gaming Modules'),
            ('Creative Collaborative','Jean-Eric Rhyters',2000,'Novels'),
            ('Gang of Fiction', 'Erichard Gammahelm and Raljean Vissides',1994,'Fiction');

INSERT INTO Publishers(PublisherName, PublisherAddress, PublisherPhone, PublisherEmail)
    VALUES  ('Penguin House','818 Arctic Blvd','+8 84 646 991','PenguinHouse@PolarExpress.ice'),
            ('TOR','9177 Andromeda Lane','562-414-9177','RocketMan7@rocketmail.com'),
            ('Capitol Records','123 Sunset Blvd Los Angeles CA','323-979-8414','SikBeatsXx@OGmail.com'),
            ('McRaw Hill Edumacation','666 Seventh Circle Ave','666-911-BURN','complaint_dept@netscape.net'),
            ('Wizards Of the Coast','16 Baldurs Ave','919-888-4235','Tiamat@fireball.com');



INSERT INTO Books (BookTitle, PublisherName, YearPublished, NumberPages,GroupName)
    VALUES  ('Curse of Strahd','Wizards Of the Coast',2016,242,'Nat20 Stories'),
            ('Larry Hobbler and the Chalice of Malice','Penguin House',2021,617,'Writers Collective'),
            ('Pride and Prejudice','Penguin House',1813,408,'The Ampersands'),
            ('Began Near the Foundation Now Ive Arrived','Capitol Records',2013,12,'Ghost Bars'),
            ('Genetics Analysis and Principles','McRaw Hill Edumacation',1860,384,'Bio Hazards'),
            ('Leviathan Wakes','TOR',2016,534,'Capturing Space and Time'),
            ('History of Coffee','McRaw Hill Edumacation',2000,95,'Creative Collaborative'),
            ('The Graveyard Book','Penguin House',2008,313,'Writers Collective'),
            ('The Restaurant at the End of the Universe','TOR',1980,208,'Capturing Space and Time'),
            ('Tashas Cauldron of Everything','Wizards Of the Coast',2020,347,'Nat20 Stories'),
            ('Decent Kid A.a.ngry City','Capitol Records',2014,12,'Ghost Bars'),
            ('Rubber Duckies and Their Uses','TOR',2000,666,'Gang of Fiction'),          
            ('The Woman In White','TOR',1859,800,'The Ampersands'),
            ('Regenerative Studies','McRaw Hill Edumacation',1994,352,'Bio Hazards'),
            ('Decorating Facade Factories','TOR',1998,137,'Gang of Fiction'),
            ('How to DnD Applies to Everything','Wizards Of the Coast',2014,274,'Nat20 Stories'),
            ('What to Always Expect When You Least Expect It','Penguin House',2007,162,'Writers Collective');

SELECT * FROM Publishers;

SELECT * FROM WritingGroups;

SELECT * FROM Books;

