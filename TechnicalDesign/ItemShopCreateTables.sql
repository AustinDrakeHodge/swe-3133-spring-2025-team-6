
create table User (
	UserID		INTEGER AUTOINCREMENT,
	Username	nvarchar(32) not null,
	Password	nvarchar(64) not null,
	Email		nvarchar(255) not null,
	isAdmin		boolean not null
	PRIMARY KEY (UserID)
);


create table InventoryItem (
	ItemID		INTEGER AUTOINCREMENT,
	UserID		INTEGER,
	ItemName	nvarchar(128) not null,
	Price		money(32) not null,
	Description 	nvarchar(500),
	Type 		nvarchar(20) not null,
	isSold		boolean not null
	PRIMARY KEY (ItemID)
	FOREIGN KEY (UserID) REFERENCES User(UserID)
);

create table ItemPicture (
	PictureID	INTEGER AUTOINCREMENT,
	ItemID		INTEGER,
	FilePath	nvarchar(256) not null,
	PRIMARY KEY (PictureID)
);
