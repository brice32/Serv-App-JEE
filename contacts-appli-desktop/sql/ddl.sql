--<ScriptOptions statementTerminator=";"/>

CREATE TABLE compte (
	IdCompte INT NOT NULL,
	Pseudo VARCHAR(25) NOT NULL,
	MotDePasse VARCHAR(25) NOT NULL,
	Email VARCHAR(100) NOT NULL,
	PRIMARY KEY (IdCompte)
) ENGINE=InnoDB;

CREATE TABLE categorie (
	IdCategorie INT NOT NULL,
	Libelle VARCHAR(25) NOT NULL,
	PRIMARY KEY (IdCategorie)
) ENGINE=InnoDB;

CREATE TABLE vue_compte_role (
	Pseudo VARCHAR(25) NOT NULL,
	Role ENUM NOT NULL
);

CREATE TABLE role (
	IdCompte INT NOT NULL,
	Role ENUM NOT NULL,
	PRIMARY KEY (IdCompte,Role)
) ENGINE=InnoDB;

CREATE TABLE telephone (
	IdTelephone INT NOT NULL,
	IdPersonne INT NOT NULL,
	Libelle VARCHAR(25) NOT NULL,
	Numero VARCHAR(25) NOT NULL,
	PRIMARY KEY (IdTelephone)
) ENGINE=InnoDB;

CREATE TABLE personne (
	IdPersonne INT NOT NULL,
	IdCategorie INT NOT NULL,
	Nom VARCHAR(25) NOT NULL,
	Prenom VARCHAR(25) NOT NULL,
	PRIMARY KEY (IdPersonne)
) ENGINE=InnoDB;

CREATE INDEX fk_telephone_personne ON telephone (IdPersonne ASC);

CREATE UNIQUE INDEX idx_compte_pseudo ON compte (Pseudo ASC);

CREATE INDEX fk_personne_categorie ON personne (IdCategorie ASC);

