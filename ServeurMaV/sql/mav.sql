drop database if exists mav;
create database mav;

use mav;

create table bureau(
	idBureau BIGINT(5) not null auto_increment,
	nom CHAR(40) not null,
	primary key (idBureau)
);

create table canton(
	idCanton BIGINT(5) not null auto_increment,
	nom CHAR(40) not null,
	primary key (idCanton)
);

create table circonscription(
	idCirconscription BIGINT(5) not null auto_increment,
	nom CHAR(40) not null,
	primary key (idCirconscription)
);

create table dept(
	idDept BIGINT(5) not null,
	nom CHAR(40) not null,
	primary key (idDept)
);

create table lieu(
	idBureau BIGINT(5) not null,
	idCanton BIGINT(5) not null,
	idCirconscription BIGINT(5) not null,
	idDept BIGINT(5) not null,
	primary key (idBureau),
	foreign key (idBureau) references bureau(idBureau),
	foreign key (idCanton) references bureau(idCanton),
	foreign key (idCirconscription) references bureau(idCirconscription),
	foreign key (idDept) references bureau(idDept),
	unique (idCanton, idCirconscription),
	unique (idCirconscription, idDept)
);

create table electeur(
	insee BIGINT(10) not null,
	code CHAR(5) not null,
	nom CHAR(40) not null,
	prenom CHAR(40) not null,
	aVote BOOLEAN default false not null,
	idBureau BIGINT(5) not null,
	primary key (insee),
	foreign key (idBureau) references bureau(idBureau)
);

create table candidat(
	idCandidat BIGINT(10) not null auto_increment,
	nom CHAR(40) not null,
	prenom CHAR(40) not null,
	age BIGINT(2) not null,
	profession CHAR(40) not null,
	primary key (idCandidat)
);

create table mandat(
	idMandat BIGINT(10) not null auto_increment,
	titre CHAR(40) not null,
	annee CHAR(4) not null,
	primary key (idMandat)
);

create table brigue(
	idMandat BIGINT(10) not null,
	idCandidat BIGINT(10) not null,
	primary key (idMandat, idCandidat),
	foreign key (idMandat) references mandat(idMandat),
	foreign key (idCandidat) references candidat(idCandidat)
);

create table vote(
	idCandidat 	BIGINT(10) not null,
	idBureau BIGINT(5) not null,
	nbVotes BIGINT(10) default 0 not null,
	primary key (idBureau, idCandidat),
	foreign key (idBureau) references bureau(idBureau),
	foreign key (idCandidat) references candidat(idCandidat)
);

create table admin(
	idAdmin BIGINT(5) not null,
	login 	char(8) not null,
	password char(10) not null,
	primary key (idAdmin)
)


insert into candidat (nom, prenom, age, profession) values ("Bla", "Plop", 10, "Bebe");
insert into candidat (nom, prenom, age, profession) values ("Mamie", "Moujeau", 100, "Retraite");

insert into mandat (titre, annee) values ("Maire de Toulouse", "2003");
insert into mandat (titre, annee) values ("Maire de Paris", "2003");
insert into mandat (titre, annee) values ("Maire de Lyon", "2005");

insert into brigue(idMandat, idCandidat) values (1,1);
insert into brigue(idMandat, idCandidat) values (2,1);
insert into brigue(idMandat, idCandidat) values (3,2);

insert into bureau(nom) values("U3 108");
insert into canton(nom) values("U3");
insert into circonscription(nom) values("UPS");
insert into dept(idDept, nom) values (31, "Haute Garonne");
insert into lieu(idBureau, idCanton, idCirconscription, idDept) values(1,1,1,31);
insert into electeur(insee, code, nom, prenom, idBureau) values (123, 456, "moi", "non", 1);
