INSERT INTO vets VALUES (1, 'James', 'Carter');
INSERT INTO vets VALUES (2, 'Helen', 'Leary');
INSERT INTO vets VALUES (3, 'Linda', 'Douglas');
INSERT INTO vets VALUES (4, 'Rafael', 'Ortega');
INSERT INTO vets VALUES (5, 'Henry', 'Stevens');
INSERT INTO vets VALUES (6, 'Sharon', 'Jenkins');
ALTER SEQUENCE vets_seq RESTART WITH 7;

INSERT INTO specialties VALUES (1, 'radiology');
INSERT INTO specialties VALUES (2, 'surgery');
INSERT INTO specialties VALUES (3, 'dentistry');
ALTER SEQUENCE specialties_seq RESTART WITH 3;

INSERT INTO vet_specialties (id, vet_id, specialty_id) VALUES (1, 2, 1);
INSERT INTO vet_specialties (id, vet_id, specialty_id) VALUES (2, 3, 2);
INSERT INTO vet_specialties (id, vet_id, specialty_id) VALUES (3, 3, 3);
INSERT INTO vet_specialties (id, vet_id, specialty_id) VALUES (4, 4, 2);
INSERT INTO vet_specialties (id, vet_id, specialty_id) VALUES (5, 5, 1);
ALTER SEQUENCE vet_specialties_seq RESTART WITH 6;