CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE projects (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    start_date DATE,
    owner_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (owner_id) REFERENCES users(id)
);

CREATE TABLE project_members (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    project_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    role ENUM('ADMIN', 'MEMBER', 'OBSERVER') NOT NULL,
    joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (project_id) REFERENCES projects(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE tasks (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    project_id BIGINT NOT NULL,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    priority ENUM('LOW', 'MEDIUM', 'HIGH') DEFAULT 'MEDIUM',
    status ENUM('TODO', 'IN_PROGRESS', 'DONE') DEFAULT 'TODO',
    due_date DATE,
    end_date DATE,
    assigned_to BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (project_id) REFERENCES projects(id),
    FOREIGN KEY (assigned_to) REFERENCES users(id)
);

CREATE TABLE task_history (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    task_id BIGINT NOT NULL,
    user_id BIGINT,
    action VARCHAR(255),
    old_value TEXT,
    new_value TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (task_id) REFERENCES tasks(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE notifications (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    message TEXT NOT NULL,
    type VARCHAR(50),
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- ============================================================================
# Données de test
-- ============================================================================

-- Utilisateurs
INSERT INTO users (username, email, password) VALUES
('alice', 'alice@test.com', 'password'),
('bob', 'bob@test.com', 'password'),
('charlie', 'charlie@test.com', 'password');

-- Projets
INSERT INTO projects (name, description, start_date, owner_id) VALUES
('Projet Alpha', 'Premier projet de démonstration', '2024-01-10', 1),
('Projet Bêta', 'Application mobile interne', '2024-02-01', 2);

-- Membres des projets
INSERT INTO project_members (project_id, user_id, role) VALUES
(1, 1, 'ADMIN'),
(1, 2, 'MEMBER'),
(1, 3, 'OBSERVER'),
(2, 2, 'ADMIN'),
(2, 1, 'MEMBER');

-- Tâches
INSERT INTO tasks (project_id, title, description, priority, status, due_date, end_date, assigned_to) VALUES
(1, 'Concevoir la base de données', 'Modélisation physique du schéma', 'HIGH', 'DONE', '2024-01-15', '2024-01-12', 1),
(1, 'Développer l API REST', 'Endpoints Spring Boot', 'HIGH', 'IN_PROGRESS', '2024-02-15', NULL, 2),
(1, 'Tests unitaires backend', 'JUnit + Mockito', 'MEDIUM', 'TODO', '2024-02-28', NULL, 3),
(2, 'Maquettes UI/UX', 'Conception des écrans Angular', 'MEDIUM', 'IN_PROGRESS', '2024-02-10', NULL, 1),
(2, 'Intégration frontend', 'Composants Angular', 'HIGH', 'TODO', '2024-03-01', NULL, 2);

-- Historique des tâches
INSERT INTO task_history (task_id, user_id, action, old_value, new_value) VALUES
(2, 1, 'STATUS_CHANGED', 'TODO', 'IN_PROGRESS'),
(3, 2, 'ASSIGNED', NULL, 'charlie');

-- Notifications
INSERT INTO notifications (user_id, message, type, is_read) VALUES
(1, 'Vous avez été assigné à une nouvelle tâche', 'TASK', FALSE),
(2, 'Le projet Alpha a été mis à jour', 'PROJECT', TRUE);